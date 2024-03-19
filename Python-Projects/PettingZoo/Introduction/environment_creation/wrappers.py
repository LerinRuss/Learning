from pettingzoo.butterfly import pistonball_v6
from pettingzoo.utils import ClipOutOfBoundsWrapper

env = pistonball_v6.env()
wrapped_env = ClipOutOfBoundsWrapper(env)
# Wrapped environments must be reset before use
wrapped_env.reset()
