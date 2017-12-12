import numpy as np
import cv2
from matplotlib import pyplot as plt

img = cv2.imread('12.jpg',0)

# Initiate STAR detector
star = cv2.xfeatures2d.StarDetector_create()

# Initiate BRIEF extractor
brief = cv2.xfeatures2d.BriefDescriptorExtractor_create()

# find the keypoints with STAR
kp = star.detect(img,None)

# compute the descriptors with BRIEF
kp, des = brief.compute(img, kp)

print (des)
#print (brief.getInt('bytes'))
print (des.shape)