from telegram.ext import Updater, CommandHandler
from game.game import Game, pair_up
from localization import *

import logging
import random
import os


token = os.environ['VITEKER_TELEGRAM_BOT_TOKEN']
game = Game()


def start(update, context):
    if game.is_started is True or game.is_created is False:
        return

    game.is_started = True
    players = list(game.room)
    pairs = pair_up(players)
    random.shuffle(pairs)
    players_text = ', '.join(players)
    game.curr = pairs.pop()
    context.bot.send_message(chat_id=update.effective_chat.id, text=START_TEXT % (players_text, game.curr))


def create(update, context):
    if game.is_created is True:
        return

    game.room.clear()
    game.is_created = True
    context.bot.send_message(chat_id=update.effective_chat.id, text=CREATE_ROOM_TEXT)


def connect(update, context):
    if game.is_created is not True:
        return

    user = update.message.from_user.username
    game.room[user] = 0
    context.bot.send_message(chat_id=update.effective_chat.id, text=CONNECTION_TEXT % {"user": user})


def stop(update, context):
    context.bot.send_message(chat_id=update.effective_chat.id,
                             text=GAME_OVER_TEXT % {'stats': game.build_stats()})

    game.room.clear()
    game.is_created = False
    game.is_started = False


def say(update, context):
    player = game.get_current_by_name(update.message.from_user.username)

    if player is None or player.answer is not None:
        return

    if len(context.args) == 0:
        context.bot.send_message(chat_id=update.effective_chat.id, text='BLALALALLALA')
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
    res = game.turn()

    if not res:
        return

    if len(game.pairs) == 0:
        context.bot.send_message(chat_id=update.effective_chat.id,
                                 text='Напиши в общий чат /stop.')
        return

    game.curr = game.pairs.pop()
    context.bot.send_message(chat_id=update.effective_chat.id,
                             text=SAY_NEXT_PAIR % {'pair': game.curr})


def about(update, context):
    context.bot.send_message(chat_id=update.effective_chat.id,
                             text=ABOUT_TEXT % {
                                      'believe': BELIEVE_WORD,
                                      'lie': LIE_WORD
                                  }
                             )


logging.basicConfig(format='%(asctime)s - %(name)s - %(levelname)s - %(message)s', level=logging.INFO)


updater = Updater(token=token, use_context=True)
dispatcher = updater.dispatcher

start_handler = CommandHandler('start', start)
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
