from skimage.io import imread

import os

import matplotlib.pyplot as plt
import tensorflow as tf
import numpy as np


def load_data(data_directory):
    directories = [d for d in os.listdir(data_directory)
                   if os.path.isdir(os.path.join(data_directory, d))]
    labels = []
    images = []
    for d in directories:
        label_directory = os.path.join(data_directory, d)
        file_names = [os.path.join(label_directory, f)
                      for f in os.listdir(label_directory)
                      if f.endswith(".ppm")]
        for f in file_names:
            images.append(imread(f))
            labels.append(int(d))
    return images, labels


ROOT_PATH = "C:/Users/harve/Projects/Datasets"

train_data_directory = os.path.join(ROOT_PATH, "TrafficSigns/Training")
test_data_directory = os.path.join(ROOT_PATH, "TrafficSigns/Testing")

images, labels = load_data(train_data_directory)

traffic_signs = [300, 2250, 3650, 4000]

unique_labels = set(labels)
plt.figure(figsize=(15, 15))
i = 1
for label in labels:
    image = images[labels.index(label)]
