from telegram.ext import Updater, CommandHandler
from game.game import Game, TurnResult
from localization import *

import logging
import os


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
    players_text = ', '.join(game.players)
    context.bot.send_message(chat_id=update.effective_chat.id, text=START_TEXT % (players_text, game.curr))


def create(update, context):
    if not game.is__idle:
        context.bot.send_message(chat_id=update.effective_chat.id, text=CREATE_ROOM_ERROR_TEXT)
        return

    game.create_room()
    context.bot.send_message(chat_id=update.effective_chat.id, text=CREATE_ROOM_TEXT)


def connect(update, context):
    if not game.is__created:
        context.bot.send_message(chat_id=update.effective_chat.id, text=CONNECT_WARN_TEXT)
        return

    user = update.message.from_user.username
    game.join(user)
    context.bot.send_message(chat_id=update.effective_chat.id, text=CONNECTION_TEXT % {"user": user})


def stop(update, context):
    context.bot.send_message(chat_id=update.effective_chat.id,
                             text=GAME_OVER_TEXT % {'stats': game.build_stats()})
    game.stop()


def say(update, context):
    player = game.get_current_by_name(update.message.from_user.username)

    if player is None:
        context.bot.send_message(chat_id=update.effective_chat.id, text=NOT_CURRENT_TEXT % {'name': player.name})
        return

    if player.answer is not None:
        context.bot.send_message(chat_id=update.effective_chat.id, text=ALREADY_ANSWERED_TEXT % {'name': player.name})
        return

    if len(context.args) == 0:
        context.bot.send_message(chat_id=update.effective_chat.id, text=SAY_ARGS_WARN_TEXT % {
            'name': player.name,
            'believe': BELIEVE_WORD,
            'lie': LIE_WORD
        })
        return

    said = context.args[0]
    if not said == BELIEVE_WORD and not said == LIE_WORD:
        context.bot.send_message(chat_id=update.effective_chat.id,
                                 text=SAY_WRONG_TEXT % {
                                          'name': player.name,
                                          'believe': BELIEVE_WORD,
                                          'lie': LIE_WORD
                                      })
        return

    player.answer = said
    turn_res = game.turn()

    if turn_res == TurnResult.keep_turn:
        return

    if turn_res == TurnResult.game_ended:
        stop(update, context)
        return

    context.bot.send_message(chat_id=update.effective_chat.id,
                             text=SAY_NEXT_PAIR % {'pair': game.curr})


def about(update, context):
    context.bot.send_message(chat_id=update.effective_chat.id,
                             text=ABOUT_TEXT % {
                                      'believe': BELIEVE_WORD,
                                      'lie': LIE_WORD
                                  }
                             )


updater = Updater(token=token, use_context=True)
dispatcher = updater.dispatcher

start_handler = CommandHandler('play', play)
create_handler = CommandHandler('create', create)
connect_handler = CommandHandler('connect', connect)
stop_handler = CommandHandler('stop', stop)
say_handler = CommandHandler('say', say)
about_handler = CommandHandler('about', about)

dispatcher.add_handler(start_handler)
dispatcher.add_handler(connect_handler)
dispatcher.add_handler(create_handler)
dispatcher.add_handler(connect_handler)
dispatcher.add_handler(stop_handler)
dispatcher.add_handler(say_handler)
dispatcher.add_handler(about_handler)

updater.start_polling()
