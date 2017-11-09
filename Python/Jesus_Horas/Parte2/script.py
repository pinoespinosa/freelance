
import sqlite3

import tkinter as tk
from tkinter import filedialog
import os
import zipfile
from File_Lib import saveFile, loadFile
import numpy as np
import pandas as pd #pip3 install pandas #pip3 install tables


db = sqlite3.connect(':memory:')
db = sqlite3.connect('mydb.db')

cursor = db.cursor()

cursor.execute("CREATE TABLE Cars(underlying_symbol TEXT, quote_datetime TEXT, root TEXT, expiration TEXT, strike INT, option_type TEXT, open INT, high INT,low INT,close INT,trade_volume INT,bid_size INT,bid INT,ask_size INT,ask INT,underlying_bid INT,underlying_ask INT,number_of_exchanges INT, exchange INT,bid_size_2 INT,bid_2 INT,ask_size_2 INT,ask_number_of_exchanges INT,x INT,y INT,z INT)")


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
	lista2 = [];
	lista = [];
	loadFile(file_path + '/' + 'unziped' + '/' + fileName, lista);
	print('Processing ' + fileName + ' ' + str( avance / len(toCompress) * 100 ) + '%')
	tam = 1;
	while (tam < len(lista)):
		a = lista[tam].split(",")[0:26];
		lista2.append(a);
		try:
			query = "INSERT INTO Cars VALUES('" + a[0] +"','"+ a[1] +"','"+ a[2] +"','"+ a[3] +"',"+ a[4] +",'"+ a[5] +"',"+ a[6] +","+ a[7] +","+ a[8] +","+ a[9] +","+ a[10] +","+ a[11] +","+ a[12] +","+ a[13] +","+ a[14] +","+ a[15] +"," +a[16] +","+ a[17] +","+ a[18] +","+ a[19] +","+ a[20] +","+ a[21] +","+ a[22] +","+ a[23] +","+ a[24] +","+ a[25]+ ")";
			cursor.execute( query );
		except:
			query=''
		tam = tam + 1;

	df2 = pd.DataFrame(lista2)
	df2.to_hdf('compressData.h5', 'data', append=True)
	avance = avance + 1

db.commit()
db.close()