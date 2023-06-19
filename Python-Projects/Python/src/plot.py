import numpy as np
import matplotlib.pyplot as plt

with open('Data/small.txt', 'r') as f:
    data = np.genfromtxt(f, dtype='datetime64[s],f,f,f', names=['date', 'doy', 'temp', 'solar'])

datetime = data['date']
dayofyear = data['doy']
temperature = data['temp']
solar = data['solar']

temp_low = np.convolve(temperature, np.ones(48)/48, mode='same')
solar_low = np.convolve(solar, np.ones(48)/48, mode='same')

print(temp_low)
