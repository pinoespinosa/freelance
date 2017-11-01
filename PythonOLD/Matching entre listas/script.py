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

def loadCodigos():
	try:
		file = open("codigos.csv", "r") 
		for line in file:
			codigoSap = line.split(',')[0].replace('"','').lower().strip(); 
			titulo = line.split(',')[1].replace('"','').lower().strip(); 

			codigos[titulo] = codigoSap;
		file.close();
	except (FileNotFoundError):
		print('');
	return

def loadImagenes():
	try:
		file = open("imagenes.txt", "r") 
		for line in file:
			listaImagenes.append(line.strip().lower())
		file.close();
	except (FileNotFoundError):
		print('');
	return

def save():
	file = open('resultado.csv',"w") 
	for valor in resultado:
		file.write(valor+ '\n'); 	 
	file.close();
	return


codigos = {};
listaImagenes = [];


resultado = [];

loadCodigos();
loadImagenes();

for aa in listaImagenes:
	if aa in codigos.keys():
		print('');
		resultado.append(codigos[aa] + ',' + aa + ',http://www.radec.com.mx/sites/all/files/imagecache/product_view/productos/' + codigos[aa] + '.jpg' );
	else:
		resultado.append(',' + aa + ',');
save();
