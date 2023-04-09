from typing import Union

import gymnasium as gym
import numpy as np

from numpy import ndarray
from storage import SavedAgent, load, dump

from agent import Agent
from learning import learn

env = gym.make('MountainCar-v0', render_mode='human')

common_step_number = 100_000
num_steps = 5

learning_rate = 0.01
start_epsilon = 1.0
epsilon_decay = 1.5 * start_epsilon / common_step_number  # reduce the exploration over time
final_epsilon = 0.1


class CarAgent(Agent):
    @staticmethod
    def _transform_obs(obs: ndarray[float, float]) -> tuple[int, int]:
        return int(obs[0] * 10), int(obs[1] * 1000)

    def __init__(self, learning_rate: float, initial_epsilon: float, epsilon_decay: float, final_epsilon: float,
                 default_q_value_factory, existent_q_values: [tuple[int, int], ndarray], action_factory):
        super().__init__(learning_rate, initial_epsilon, epsilon_decay, final_epsilon, default_q_value_factory,
                         existent_q_values, action_factory)

    def update(self, obs: ndarray[float, float], action: int, reward: float, terminated: bool,
               next_obs: [int, int, bool]):
        Agent.update(self, CarAgent._transform_obs(obs), action, reward, terminated, CarAgent._transform_obs(next_obs))

    def get_action(self, obs: ndarray[float, float]) -> int:
        return Agent.get_action(self, CarAgent._transform_obs(obs))


def learn_saved_agent(saved_agent: SavedAgent):
    agent = CarAgent(
        learning_rate=learning_rate,
        initial_epsilon=saved_agent.last_epsilon,
        epsilon_decay=epsilon_decay,
        final_epsilon=final_epsilon,
        default_q_value_factory=lambda: np.zeros(env.action_space.n),
        existent_q_values=saved_agent.q_values,
        action_factory=env.action_space.sample)

    steps_to_learn = num_steps if saved_agent.last_learnt_step + num_steps <= common_step_number \
        else common_step_number - saved_agent.last_learnt_step

    learn(env, agent, steps_to_learn)
    env.close()
    dump(saved_agent.last_learnt_step + steps_to_learn, agent)


def start_learning():
    agent = CarAgent(
        learning_rate=learning_rate,
        initial_epsilon=start_epsilon,
        epsilon_decay=epsilon_decay,
        final_epsilon=final_epsilon,
        default_q_value_factory=lambda: np.zeros(env.action_space.n),
        existent_q_values=dict(),
        action_factory=env.action_space.sample)
    learn(env, agent, num_steps)
    env.close()
    dump(num_steps, agent)


saved_agent: Union[SavedAgent, None] = load()

if saved_agent is not None:
    learn_saved_agent(saved_agent)
else:
    start_learning()
