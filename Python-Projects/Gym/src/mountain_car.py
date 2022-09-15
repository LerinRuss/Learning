import gym

env = gym.make('MountainCar-v0')

num_steps = 1500

obs = env.reset()
print(f"Initial Observation {obs}")

for step in range(num_steps):
    random_action = env.action_space.sample()
    obs, reward, done, info = env.step(random_action)

    env.render()

    if done:
        env.reset()

env.close()
