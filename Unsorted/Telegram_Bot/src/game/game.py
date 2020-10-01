from localization import *


class Game:
    def __init__(self):
        self.room = dict()
        self.pairs = list()
        self.curr = None
        self.is_created = False
        self.is_started = False

    def get_current_by_name(self, player_name):
        if self.curr[0].name == player_name:
            return self.curr[0]

        if self.curr[1].name == player_name:
            return self.curr[1]

        return None

    def turn(self):
        first = self.curr[0]
        second = self.curr[1]

        if first.answer is None or second.answer is None:
            return False

        if first.answer == LIE_WORD and second.answer == LIE_WORD:
            return True

        if first.answer == BELIEVE_WORD and second.answer == BELIEVE_WORD:
            self.room[first.name] = self.room[first.name] + 1
            self.room[second.name] = self.room[second.name] + 1

            return True

        if first.answer == LIE_WORD:
            self.room[first.name] = self.room[first.name] + 2

        if second.answer == LIE_WORD:
            self.room[second.name] = self.room[second.name] + 2

        return True

    def build_stats(self):
        msg = ''
        for (name, score) in self.room.items():
            msg += '%s: %s\n' % (name, score)

        return msg


class Player:
    def __init__(self, name):
        self.name = name
        self.answer = None

    def __repr__(self):
        return self.name


def pair_up(players_arg):
        players = players_arg.copy()
        pairs = list()
        while players:
            last = players.pop()

            for curr in players:
                pairs.append((Player(last), Player(curr)))

        return pairs