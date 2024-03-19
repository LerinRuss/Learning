from pettingzoo.test import parallel_api_test
from pettingzoo.butterfly import pistonball_v6
env = pistonball_v6.parallel_env()
parallel_api_test(env, num_cycles=1000)
