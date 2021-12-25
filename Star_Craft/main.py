import sc2
import random
import cv2
import numpy as np
import time

from sc2 import run_game, maps, Race, Difficulty, position
from sc2.player import Bot, Computer
from sc2.constants import NEXUS, PROBE, PYLON, ASSIMILATOR, GATEWAY, CYBERNETICSCORE, STALKER, STARGATE, VOIDRAY, \
    ZEALOT, OBSERVER, ROBOTICSFACILITY
from sc2.data import Result

MAX_WORKERS = 60
UNITS_DEFENCE_MIN = {ZEALOT: 4,
                     VOIDRAY: 2}
HEADLESS = True

class SimpleBot(sc2.BotAI):
    def __init__(self):
        self.do_something_after = 0
        self.train_data = []

    async def on_step(self, iteration: int):
        self.iteration = iteration
        await self.scout()
        await self.distribute_workers()
        await self.build_workers()
        await self.build_pylons()
        await self.build_offensive_force()
        await self.offensive_force_buildings()
        await self.build_assimilator()
        await self.expand()
        await self.attack()
        await self.intel()

    async def build_workers(self):
        if (len(self.units(NEXUS)) * 16) + 4 > len(self.units(PROBE)) and len(self.units(PROBE)) < MAX_WORKERS:
            for nexus in self.units(NEXUS).ready.noqueue:
                if self.can_afford(PROBE):
                    await self.do(nexus.train(PROBE))

    async def build_pylons(self):
        if self.supply_left < 5 and not self.already_pending(PYLON):
            nexuses = self.units(NEXUS).ready

            if nexuses.exists:
                if self.can_afford(PYLON):
                    await self.build(PYLON, near=nexuses.first)

    async def expand(self):
        can_expand = False
        for unit_type in UNITS_DEFENCE_MIN:
            if self.units(unit_type).amount > UNITS_DEFENCE_MIN[unit_type]:
                can_expand = True
                break

        if self.can_afford(NEXUS) and can_expand and not self.already_pending(NEXUS) and \
                self.units(PROBE).ready.amount >= self.units(NEXUS).amount * 16:
            await self.expand_now()

    async def build_assimilator(self):
        for nexus in self.units(NEXUS).ready:
            vaspenes = self.state.vespene_geyser.closer_than(20.0, nexus)
            for vaspene in vaspenes:
                if not self.can_afford(ASSIMILATOR):
                    break

                worker = self.select_build_worker(vaspene.position)
                if worker is None:
                    break
                if not self.units(ASSIMILATOR).closer_than(1.0, vaspene).exists:
                    await self.do(worker.build(ASSIMILATOR, vaspene))

    async def offensive_force_buildings(self):
        if not self.units(PYLON).ready.exists:
            return

        pylon = self.units(PYLON).ready.random

        if not self.units(CYBERNETICSCORE) and self.units(GATEWAY).exists and self._able_to_build(CYBERNETICSCORE):
            await self.build(CYBERNETICSCORE, near=pylon)
            return
        if len(self.units(ROBOTICSFACILITY)) < 1 and self._able_to_build(ROBOTICSFACILITY):
            await self.build(ROBOTICSFACILITY, near=pylon)
            return
        if self.units(GATEWAY).amount < 1 and self._able_to_build(GATEWAY):
            await self.build(GATEWAY, near=pylon)
            return
        if not self.units(STARGATE).exists or \
                self.units(STARGATE).amount < self.units(NEXUS).amount and self._able_to_build(STARGATE):
            await self.build(STARGATE, near=pylon)
            return

    async def build_offensive_force(self):
        for sg in self.units(STARGATE).ready.noqueue:
            if self._can_train(VOIDRAY):
                await self.do(sg.train(VOIDRAY))

        unit_type = ZEALOT
        for gw in self.units(GATEWAY).ready.noqueue:
            if self.units(unit_type).amount <= self.units(VOIDRAY).amount << 1 and self._can_train(unit_type):
                await self.do(gw.train(unit_type))

    async def attack(self):
        if len(self.units(VOIDRAY).idle) > 0:
            choice = random.randrange(0, 4)
            target = False
            if self.iteration > self.do_something_after:
                if choice == 0:
                    # no attack
                    wait = random.randrange(20, 165)
                    self.do_something_after = self.iteration + wait
                elif choice == 1:
                    #attack_unit_closest_nexus
                    if len(self.known_enemy_units) > 0:
                        target = self.known_enemy_units.closest_to(random.choice(self.units(NEXUS)))
                elif choice == 2:
                    #attack enemy structures
                    if len(self.known_enemy_structures) > 0:
                        target = random.choice(self.known_enemy_structures)
                elif choice == 3:
                    #attack_enemy_start
                    target = self.enemy_start_locations[0]
                if target:
                    for UNIT in UNITS_DEFENCE_MIN:
                        for unit in self.units(UNIT).idle:
                            await self.do(unit.attack(target))
                y = np.zeros(4)
                y[choice] = 1
                print(y)
                self.train_data.append([y, self.flipped])

    async def intel(self):
        draw_dict = {
            NEXUS: [15, (0, 255, 0)],
            PYLON: [3, (20, 235, 0)],
            PROBE: [1, (55, 200, 0)],
            ASSIMILATOR: [2, (55, 200, 0)],
            GATEWAY: [3, (200, 100, 0)],
            CYBERNETICSCORE: [3, (150, 150, 0)],
            STARGATE: [5, (255, 0, 0)],
            VOIDRAY: [3, (255, 100, 0)],
            ROBOTICSFACILITY: [5, (215, 155, 0)]
        }

        game_data = np.zeros((self.game_info.map_size[1], self.game_info.map_size[0], 3), np.uint8)
        self._draw_ratio(game_data)

        for unit_type in draw_dict:
            for unit in self.units(unit_type).ready:
                pos = unit.position
                cv2.circle(game_data, (int(pos[0]), int(pos[1])), draw_dict[unit_type][0], draw_dict[unit_type][1], -1)

        main_base_names = ["nexus", "commandcenter", "hatchery"]
        for enemy_building in self.known_enemy_structures:
            pos = enemy_building.position
            if enemy_building.name.lower() not in main_base_names:
                cv2.circle(game_data, (int(pos[0]), int(pos[1])), 5, (200, 50, 212), -1)
        for enemy_building in self.known_enemy_structures:
            pos = enemy_building.position
            if enemy_building.name.lower() in main_base_names:
                cv2.circle(game_data, (int(pos[0]), int(pos[1])), 15, (0, 0, 255), -1)
        worker_names = ["probe",
                        "scv",
                        "drone"]
        for enemy_unit in self.known_enemy_units:
            if not enemy_unit.is_structure:
                pos = enemy_unit.position
                if enemy_unit.name.lower() in worker_names:
                    cv2.circle(game_data, (int(pos[0]), int(pos[1])), 1, (55, 0, 155), -1)
                else:
                    cv2.circle(game_data, (int(pos[0]), int(pos[1])), 3, (50, 0, 215), -1)
        for obs in self.units(OBSERVER).ready:
            pos = obs.position
            cv2.circle(game_data, (int(pos[0]), int(pos[1])), 1, (255, 255, 255), -1)

        self.flipped = cv2.flip(game_data, 0)

        if not HEADLESS:
            resized = cv2.resize(self.flipped, dsize=None, fx=2, fy=2)
            cv2.imshow('Intel', resized)
            cv2.waitKey(1)

    def _draw_ratio(self, game_data):
        line_max = 50
        mineral_ratio = self.minerals / 1500
        if mineral_ratio > 1.0:
            mineral_ratio = 1.0
        vespene_ratio = self.vespene / 1500
        if vespene_ratio > 1.0:
            vespene_ratio = 1.0
        population_ratio = self.supply_left / self.supply_cap
        if population_ratio > 1.0:
            population_ratio = 1.0
        plausible_supply = self.supply_cap / 200.0
        military_weight = len(self.units(VOIDRAY)) / (self.supply_cap-self.supply_left)
        if military_weight > 1.0:
            military_weight = 1.0
        cv2.line(game_data, (0, 19), (int(line_max*military_weight), 19), (250, 250, 200), 3)  # worker/supply ratio
        cv2.line(game_data, (0, 15), (int(line_max*plausible_supply), 15), (220, 200, 200), 3)  # plausible supply (supply/200.0)
        cv2.line(game_data, (0, 11), (int(line_max*population_ratio), 11), (150, 150, 150), 3)  # population ratio (supply_left/supply)
        cv2.line(game_data, (0, 7), (int(line_max*vespene_ratio), 7), (210, 200, 0), 3)  # gas / 1500
        cv2.line(game_data, (0, 3), (int(line_max*mineral_ratio), 3), (0, 255, 25), 3)  # minerals minerals/1500

    async def scout(self):
        if len(self.units(OBSERVER)) > 0:
            scout = self.units(OBSERVER)[0]
            if scout.is_idle:
                enemy_location = self.enemy_start_locations[0]
                move_to = self.random_location_variance(enemy_location)
                print(move_to)
                await self.do(scout.move(move_to))
        else:
            for rf in self.units(ROBOTICSFACILITY).ready.noqueue:
                if self._can_train(OBSERVER) and self.units(VOIDRAY).amount > 10:
                    await self.do(rf.train(OBSERVER))

    def random_location_variance(self, enemy_start_location):
        x = enemy_start_location[0]
        y = enemy_start_location[1]
        x += ((random.randrange(-20, 20))/100) * enemy_start_location[0]
        y += ((random.randrange(-20, 20))/100) * enemy_start_location[1]
        if x < 0:
            x = 0
        if y < 0:
            y = 0
        if x > self.game_info.map_size[0]:
            x = self.game_info.map_size[0]
        if y > self.game_info.map_size[1]:
            y = self.game_info.map_size[1]
        go_to = position.Point2(position.Pointlike((x, y)))
        return go_to

    def find_target(self, state):
        if len(self.known_enemy_units) > 0:
            return random.choice(self.known_enemy_units)
        elif len(self.known_enemy_structures) > 0:
            return random.choice(self.known_enemy_structures)
        else:
            return self.enemy_start_locations[0]

    def _can_train(self, unit_type):
        return self.can_afford(unit_type) and self.supply_left > 0

    def _able_to_build(self, building_type):
        return self.can_afford(building_type) and not self.already_pending(building_type)

    def on_end(self, game_result):
        print('--- on_end called ---')
        print(game_result)
        if game_result == Result.Victory:
            np.save("train_data/{}.npy".format(str(int(time.time()))), np.array(self.train_data))


while True:
    run_game(maps.get("AbyssalReefLE"), [
        Bot(Race.Protoss, SimpleBot()),
        Computer(Race.Terran, Difficulty.MediumHard)
    ], realtime=False)
