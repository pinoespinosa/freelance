import numpy as np
import pandas as pd #pip3 install pandas #pip3 install tables

filename = '/home/pino/freelance/Python/JesusHoras/Parte2/test.h5'



valor = "NFLX,2016-09-01 10:30:00,NFLX,2016-09-02,50.000,C,0.0000,0.0000,0.0000,0.0000,0,15,46.7500,14,47.5500,97.1700,97.1900,11,1,1,46.3500,1,48.0000,65,2,45.1500,2,49.3500,4,3,46.6000,2,47.8000,5,18,46.7000,9,47.6500,7,8,46.5500,7,48.0000,9,24,46.7000,16,47.9500,42,14,46.7500,14,47.5500,43,1,46.7500,11,47.6500,11,1,45.2000,10,49.5000,60,1,46.5000,2,48.0000,47,2,45.2000,15,49.5000"


# Append more data
df2 = pd.DataFrame(valor.split(","))
df2.to_hdf(filename, 'data', append=True)

print(pd.read_hdf(filename, 'data'))