import numpy as np

input_signal = np.array([1, 2, 3])
filter_kernel = np.array([0.1, 0.5])

convolved_signal = np.convolve(input_signal, filter_kernel, mode='full')

print(convolved_signal)
