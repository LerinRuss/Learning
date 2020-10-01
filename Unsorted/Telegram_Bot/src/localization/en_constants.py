ABOUT_TEXT = ('In this game you need to believe your opponent or not.\n'
              'If you both believe each other you both gain +1 score.\n'
              'If you lie to your opponent but opponent believe you so you gain +2 score.\n'
              'And if you both lie each other you gain nothing.\n'
              'Type /create to create new room.')
SAY_WRONG_TEXT = '%(name)s please write `%(believe)s` or `%(lie)s` without quotes'
SAY_NEXT_PAIR = 'Next pair:%(pair)s'
GAME_OVER_TEXT = 'Game is over. Stats:\n%(stats)s'
CONNECTION_TEXT = '%(user)s connected.'
CREATE_ROOM_TEXT = ("""Okay, room's created. Now type /connect command to join."""
                    """And when everyone are joint type /start""")
START_TEXT = ("""Game's started. Next players involved:\n%s\n\nFirst couple is %s. """
              """I accept only their messages.\nType /say <your_answer> to answer. """)

BELIEVE_WORD = 'believe'
LIE_WORD = 'lie'
