from gymnasium import Env
from tqdm import tqdm

from src.agent import Agent


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
