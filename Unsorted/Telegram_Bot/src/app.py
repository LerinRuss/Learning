from telegram.ext import Updater, CommandHandler, CallbackQueryHandler
from telegram import InlineKeyboardButton, InlineKeyboardMarkup
from game.game import Game, TurnResult, GameWord
from localization import *

import logging
import os


REGEX_CONNECT = 'connect'
REGEX_PLAY = 'play'

REGEX_BELIEVE = 'believe'
REGEX_LIE = 'lie'

logging.basicConfig(format='%(asctime)s - %(name)s - %(levelname)s - %(message)s', level=logging.INFO)
token = os.environ['VITEKER_TELEGRAM_BOT_TOKEN']
game = Game()


def play(update, context):
    if not game.is__created:
        context.bot.send_message(chat_id=update.effective_chat.id, text=CREATE_ROOM_WARN_TEXT)
        return
    if len(game.room) < 2:
        context.bot.send_message(chat_id=update.effective_chat.id, text=NOT_ENOUGH_PLAYERS_TEXT)
        return

    game.play()

    callback_query = update.callback_query
    keyboard_buttons = [[InlineKeyboardButton(BELIEVE_BUTTON_TEXT, callback_data=REGEX_BELIEVE)],
                        [InlineKeyboardButton(LIE_BUTTON_TEXT, callback_data=REGEX_LIE)]]

    players_text = ', '.join(list(game.room))
    callback_query.edit_message_text(
        PLAY_TEXT % {
            'players': players_text,
            'pair': game.curr},
        reply_markup=InlineKeyboardMarkup(keyboard_buttons))


def create(update, context):
    if not game.is__idle:
        context.bot.send_message(chat_id=update.effective_chat.id, text=CREATE_ROOM_ERROR_TEXT)
        return

    game.create_room()

    keyboard_buttons = [[InlineKeyboardButton(CONNECT_BUTTON_TEXT, callback_data=REGEX_CONNECT)],
                        [InlineKeyboardButton(PLAY_BUTTON_TEXT, callback_data=REGEX_PLAY)]]
    keyboard = InlineKeyboardMarkup(keyboard_buttons)
    context.bot.send_message(chat_id=update.effective_chat.id, text=CREATE_ROOM_TEXT, reply_markup=keyboard)


def connect(update, context):
    if not game.is__created:
        context.bot.send_message(chat_id=update.effective_chat.id, text=CONNECT_WARN_TEXT)
        return

    callback_query = update.callback_query
    user = callback_query.from_user.username
    callback_query.answer()
    if user in game.room:
        context.bot.send_message(chat_id=update.effective_chat.id, text=ALREADY_CONNECTED_TEXT % {'user': user})
        return

    game.join(user)
    callback_query.edit_message_text(CREATE_ROOM_TEXT + '\n' + CONNECTION_TEXT % {"players": ', '.join(list(game.room))},
                                     reply_markup=callback_query.message.reply_markup)


def believe(update, context):
    say_answer(update, context, GameWord.believe)


def lie(update, context):
    say_answer(update, context, GameWord.lie)


def say_answer(update, context, answer):
    callback_query = update.callback_query
    player = game.get_current_by_name(callback_query.from_user.username)

    if player is None:
        context.bot.send_message(chat_id=update.effective_chat.id, text=NOT_CURRENT_TEXT % {'name': player.name})
        return

    if player.answer is not None:
        context.bot.send_message(chat_id=update.effective_chat.id, text=ALREADY_ANSWERED_TEXT % {'name': player.name})
        return

    player.answer = answer
    turn_res = game.turn()

    if turn_res == TurnResult.keep_turn:
        return

    if turn_res == TurnResult.game_ended:
        callback_query.answer()
        stop(callback_query)
        return

    callback_query.answer()
    callback_query.edit_message_text(SAY_NEXT_PAIR % {'pair': game.curr},
                                     reply_markup=callback_query.message.reply_markup)


def stop(callback_query):
    callback_query.edit_message_text(GAME_OVER_TEXT % {'stats': game.build_stats()})
    game.stop()


def force_stop(update, context):
    context.bot.send_message(chat_id=update.effective_chat.id,
                             text=GAME_OVER_TEXT % {'stats': game.build_stats()})
    game.stop()


def about(update, context):
    context.bot.send_message(chat_id=update.effective_chat.id, text=ABOUT_TEXT)


updater = Updater(token=token, use_context=True)
dispatcher = updater.dispatcher

create_handler = CommandHandler('create', create)
force_stop_handler = CommandHandler('force_stop', force_stop)
about_handler = CommandHandler('about', about)

dispatcher.add_handler(create_handler)
dispatcher.add_handler(force_stop_handler)
dispatcher.add_handler(about_handler)
dispatcher.add_handler(CallbackQueryHandler(connect, pattern=REGEX_CONNECT))
dispatcher.add_handler(CallbackQueryHandler(play, pattern=REGEX_PLAY))
dispatcher.add_handler(CallbackQueryHandler(believe, pattern=REGEX_BELIEVE))
dispatcher.add_handler(CallbackQueryHandler(lie, pattern=REGEX_LIE))

updater.start_polling()
updater.idle()
