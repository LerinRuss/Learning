from typing import Any, Union
from numpy import ndarray

import os
import json
import numpy

BASE_PATH = 'Store/Car/Json/'
FILE_NAME = 'agent.json'

class SavedAgent:
    def __init__(self, last_learnt_step: int, last_epsilon: float, q_values: dict[tuple[int, int], ndarray]):
        self.last_learnt_step = last_learnt_step
        self.last_epsilon = last_epsilon
        self.q_values = q_values


class JsonEncoder(json.JSONEncoder):
    def default(self, o: SavedAgent) -> dict:
        fields: [str, Any] = o.__dict__.copy()
        q_values: dict[tuple[int, int], ndarray] = fields['q_values']

        fields['q_values'] = {str(k): v.tolist() for k, v in q_values.items()}

        return fields


def load() -> Union[SavedAgent, None]:
    try:
        if os.path.getsize(BASE_PATH + FILE_NAME) == 0:
            return None

        with open(BASE_PATH + FILE_NAME, 'r') as file:

            saved_agent = json.load(file)

        q_values = {tuple(map(int, k.strip("()").split(","))): numpy.array(v) for k, v in saved_agent['q_values']}
        return SavedAgent(saved_agent['last_learnt_step'], saved_agent['last_epsilon'], q_values)
    except FileNotFoundError:
        return None


def dump(last_learnt_step: int, last_epsilon: float, q_values: dict[tuple[int, int], ndarray]):
    os.makedirs(BASE_PATH, mode=0o006, exist_ok=True)
    with open(BASE_PATH + FILE_NAME, 'w') as file:
        json.dump(SavedAgent(last_learnt_step, last_epsilon,  q_values), file, cls=JsonEncoder, indent=2)
