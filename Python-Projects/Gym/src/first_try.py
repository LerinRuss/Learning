import gym

env = gym.make("LunarLander-v2")
observation = env.reset(seed=42)

def policy(observation):
    return 0

for _ in range(1000):
   env.render()
   action = policy(observation)  # User-defined policy function
   observation, reward, done, info = env.step(action)

   if done:
      observation = env.reset()
env.close()
