import gymnasium as gym
import numpy as np
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
    def __init__(self, learning_rate: float, initial_epsilon: float, epsilon_decay: float, final_epsilon: float,
                 default_q_value_factory, action_factory, obs_transformer):
        super().__init__(learning_rate, initial_epsilon, epsilon_decay, final_epsilon, default_q_value_factory,
                         action_factory)
        self.obs_transformer = obs_transformer

    def update(self, obs: ndarray[float, float], action: int, reward: float, terminated: bool,
               next_obs: [int, int, bool]):
        Agent.update(self, self.obs_transformer(obs), action, reward, terminated, self.obs_transformer(next_obs))

    def get_action(self, obs: ndarray[float, float]) -> int:
        return Agent.get_action(self, self.obs_transformer(obs))


agent = CarAgent(
    learning_rate=learning_rate,
    initial_epsilon=start_epsilon,
    epsilon_decay=epsilon_decay,
    final_epsilon=final_epsilon,
    default_q_value_factory=lambda: np.zeros(env.action_space.n),
    action_factory=env.action_space.sample,
    obs_transformer=lambda obs: (int(obs[0] * 100), int(obs[1] * 1000)))

learn(env, agent, num_steps)

env.close()
