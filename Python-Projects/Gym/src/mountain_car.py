from typing import Any

import gymnasium as gym
import numpy as np
import os
import json

from numpy import ndarray

from agent import Agent
from learning import learn

env = gym.make('MountainCar-v0', render_mode='human')

num_steps = 1500

learning_rate = 0.01
n_episodes = 100_000
start_epsilon = 1.0
epsilon_decay = start_epsilon / (n_episodes / 2)  # reduce the exploration over time
final_epsilon = 0.1


class CarAgent(Agent):
    @staticmethod
    def _transform_obs(obs: ndarray[float, float]) -> tuple[int, int]:
        return int(obs[0] * 100), int(obs[1] * 1000)

    def __init__(self, learning_rate: float, initial_epsilon: float, epsilon_decay: float, final_epsilon: float,
                 default_q_value_factory, action_factory):
        super().__init__(learning_rate, initial_epsilon, epsilon_decay, final_epsilon, default_q_value_factory,
                         action_factory)

    def update(self, obs: ndarray[float, float], action: int, reward: float, terminated: bool,
               next_obs: [int, int, bool]):
        Agent.update(self, CarAgent._transform_obs(obs), action, reward, terminated, CarAgent._transform_obs(next_obs))

    def get_action(self, obs: ndarray[float, float]) -> int:
        return Agent.get_action(self, CarAgent._transform_obs(obs))


agent = CarAgent(
    learning_rate=learning_rate,
    initial_epsilon=start_epsilon,
    epsilon_decay=epsilon_decay,
    final_epsilon=final_epsilon,
    default_q_value_factory=lambda: np.zeros(env.action_space.n),
    action_factory=env.action_space.sample)

learn(env, agent, num_steps)

env.close()


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


base_path = 'Store/Car/Json/'
os.makedirs(base_path, mode=0o006, exist_ok=True)
with open(base_path + 'agent.json', 'w') as file:
    json.dump(SavedAgent(num_steps, agent.epsilon, agent.q_values), file, cls=JsonEncoder, indent=2)

