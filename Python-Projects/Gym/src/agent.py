from collections import defaultdict
from typing import Any
from numpy import ndarray

import numpy as np


class Agent:
    def __init__(self, learning_rate: float, initial_epsilon: float, epsilon_decay: float, final_epsilon: float,
                 default_q_value_factory, existent_q_values: [tuple[int, int], ndarray], action_factory,
                 discount_factor: float = 0.95):
        self.q_values = defaultdict(default_q_value_factory, existent_q_values)

        self.lr = learning_rate
        self.discount_factor = discount_factor

        self.epsilon = initial_epsilon
        self.epsilon_decay = epsilon_decay
        self.final_epsilon = final_epsilon

        self.training_error = []
        self.action_factory = action_factory

    def get_action(self, obs: Any) -> int:
        if np.random.random() < self.epsilon:
            return self.action_factory()

        return int(np.argmax(self.q_values[obs]))

    def update(self, obs: Any, action: int, reward: float, terminated: bool,
               next_obs: [int, int, bool]):
        future_q_value = (not terminated) * np.max(self.q_values[next_obs])
        temporal_difference = reward + self.discount_factor * future_q_value - self.q_values[obs][action]

        self.q_values[obs][action] = self.q_values[obs][action] + self.lr * temporal_difference
        self.training_error.append(temporal_difference)

    def decay_epsilon(self):
        self.epsilon = max(self.final_epsilon, self.epsilon - self.epsilon_decay)
