from pettingzoo.test import render_test
from pettingzoo.butterfly import pistonball_v6

env_func = pistonball_v6.env
render_test(env_func)

custom_tests = {
    "svg": lambda render_result: isinstance(render_result, str)
}

render_test(env_func, custom_tests=custom_tests)
