import requests 				# pip install requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4

import urllib.request
from urllib.error import HTTPError
from urllib.error import URLError 

from datetime import datetime
from socket import timeout
from requests.exceptions import ConnectionError

from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import json, errno, os, time, glob, sys, codecs

def loadFileDescargados():
	try:
		file = open("marca_item_index.csv", "r") 
		for line in file:
			lista_URL.append(line.strip())
		file.close();
	except (FileNotFoundError):
		print('');
	return

def createFileCSV(filename):
	file = open(filename +'.csv',"w") 
	file.write(	'Name,Description,Phone,Directions,Website,Email,Cuisines,Horarios,Feactures,Meals,GoodFor,Url\n')

	for valor in listaCSV:
		yourstring = valor.encode('ascii', 'ignore').decode('ascii')
		file.write(yourstring + '\n'); 	 
	file.close();
	return

			
#
#
# ********************************** Programa principal **********************************
#
#

lista_URL =[];
listaCSV =[];

loadFileDescargados()

iterar = 0;
archi = 0;

browser = webdriver.Firefox()

time.sleep(60)


for url in lista_URL:
		iterar=iterar+1;



		pino = '1';
		for aa in browser.window_handles:
			if (pino == '1'):
				pino='true'
			else:
				browser.switch_to_window(aa)
				browser.close()
		browser.switch_to_window(browser.window_handles[0])
		browser.get(url)
		

		file = open('nuevo/'+str(iterar) +'.txt',"w") 
		file.write(browser.page_source); 	 
		file.close();




#	print (nombre + ' ' + website)
#	listaCSV.append( '"' + nombre.replace("\"","") +'","'+ descri.replace("\"","") +'","'+ tel.replace("\"","") +'","'+ dire.replace("\"","") +'","'+ website.replace("\"","") +'","'+ email.replace("\"","") +'","'+ cuisines.replace("\"","") +'","'+ horaComp.replace("\"","") +'","'+ features.replace("\"","") +'","'+ meals.replace("\"","") +'","'+ goodFor.replace("\"","") +'","'+ url.replace("\"","") + '"')

