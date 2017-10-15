import tkinter as tk
from tkinter import filedialog
import os
import zipfile
from File_Lib import saveFile, loadFile
import numpy as np
import pandas as pd #pip3 install pandas #pip3 install tables

# Open select dialog windows
root = tk.Tk()
root.withdraw()
file_path = filedialog.askdirectory()

# Unzip proces
print('UnZipping ...')
for file in os.listdir(file_path):
	if file.endswith(".zip"):
		zip_ref = zipfile.ZipFile(file_path + '/'+ file, 'r')
		zip_ref.extractall(file_path + '/' + 'unziped')
		zip_ref.close()

# Listing the files to add to hdf5
toCompress = os.listdir(file_path + '/' + 'unziped');
toCompress.sort();

# Adding files
avance = 0;
for fileName in toCompress:
	lista = [];
	loadFile(file_path + '/' + 'unziped' + '/' + fileName, lista);
	print('Processing ' + fileName + ' ' + str( avance / len(toCompress) * 100 ) + '%')
	tam = 1;
	while (tam < len(lista)):
		df2 = pd.DataFrame(lista[tam].split(","))
		tam = tam + 1;
	avance = avance + 1
df2.to_hdf('compressData.h5', 'data', append=True)
