import gymnasium as gym
import numpy as np

from typing import Union, Tuple, Dict
from numpy import ndarray
from gymnasium import Env
from tqdm import tqdm

from src.utils.storage import SavedAgent, load, dump
from src.utils.agent import Agent

env = gym.make('MountainCar-v0', render_mode='human')

common_step_number = 100_000_000
num_steps = 5

learning_rate = 0.01
start_epsilon = 1.0
epsilon_decay = 1.5 * start_epsilon / common_step_number  # reduce the exploration over time
final_epsilon = 0.1


class CarAgent(Agent):

    # obs: ndarray[float, float]
    @staticmethod
    def _transform_obs(obs: ndarray) -> Tuple[int, int]:
        return int(obs[0] * 10), int(obs[1] * 1000)

    def __init__(self, learning_rate: float, initial_epsilon: float, epsilon_decay: float, final_epsilon: float,
                 default_q_value_factory, existent_q_values: Dict[Tuple[int, int], ndarray], action_factory):
        super().__init__(learning_rate, initial_epsilon, epsilon_decay, final_epsilon, default_q_value_factory,
                         existent_q_values, action_factory)

    # obs: ndarray[float, float]
    def update(self, obs: ndarray, action: int, reward: float, terminated: bool,
               next_obs: [int, int, bool]):
        Agent.update(self, CarAgent._transform_obs(obs), action, reward, terminated, CarAgent._transform_obs(next_obs))

    # obs: ndarray[float, float]
    def get_action(self, obs: ndarray) -> int:
        return Agent.get_action(self, CarAgent._transform_obs(obs))


def learn(env: Env, agent: Agent, n_episodes: int):
    if n_episodes <= 0:
        raise Exception(f'Number of episodes should be positive.')
    for episode in tqdm(range(n_episodes)):
        obs, _ = env.reset()
        done = False

        while not done:
            action = agent.get_action(obs)
            next_obs, reward, terminated, truncated, _ = env.step(action)

            agent.update(obs, action, reward, terminated, next_obs)

            done = terminated or truncated
            obs = next_obs
        agent.decay_epsilon()


def learn_saved_agent(saved_agent: SavedAgent):
    agent = CarAgent(
        learning_rate=saved_agent.learning_rate,
        initial_epsilon=saved_agent.last_epsilon,
        epsilon_decay=saved_agent.epsilon_decay,
        final_epsilon=saved_agent.final_epsilon,
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
