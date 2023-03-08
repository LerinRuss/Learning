from __future__ import annotations
from collections import defaultdict
from matplotlib.patches import Patch
from tqdm import tqdm

import matplotlib.pyplot as plt
import numpy as np
import seaborn as sns
import gymnasium as gym

env = gym.make("Blackjack-v1", sab=True)

done = False
reset_observation, reset_info = env.reset()

sample_action = env.action_space.sample()

observation, reward, terminated, truncated, info = env.step(sample_action)

if truncated or terminated:
    env.reset()

value_grid = 1, 2, True

print(f"""\
Reset observation is {reset_observation}.
Reset info is {reset_info}.
Sample action is {sample_action}.
Observation is {observation}.
Reward is {reward}.
Terminated is {terminated}.
Truncated is {truncated}.
Info is {info}.
action_space is {env.action_space}

{np.meshgrid(np.arange(11, 14), np.arange(1, 3))}
""")
