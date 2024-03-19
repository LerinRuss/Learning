from pettingzoo.test import seed_test, parallel_seed_test
from pettingzoo.butterfly import pistonball_v6

env_fn = pistonball_v6.env
seed_test(env_fn, num_cycles=10)

# or for parallel environments
parallel_env_fn = pistonball_v6.parallel_env
parallel_seed_test(parallel_env_fn)
