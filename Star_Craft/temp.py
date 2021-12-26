import cv2

import numpy as np

def show_map(image):
    resized = cv2.resize(image, dsize=None, fx=2, fy=2)
    cv2.imshow('Map', resized)
    cv2.waitKey(1)

external = np.load('outside/1530807965.npy', allow_pickle=True)
first_train_data = np.load('train_data/1625667631.npy', allow_pickle=True)

# show_map(external[20][1])

print(first_train_data)
print(external)
