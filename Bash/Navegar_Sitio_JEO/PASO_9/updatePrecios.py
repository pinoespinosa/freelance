import requests 				# pip install requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4

import urllib.request
from urllib.error import HTTPError
from urllib.error import URLError 

from datetime import datetime
from socket import timeout
from requests.exceptions import ConnectionError

import json, errno, os, time, glob, sys, codecs


def loadFileHoja1():
	try:
		file = open("hoja1.csv", "r") 
		for line in file:
				hoja1.append(line.replace("\"",""));
		file.close();
	except (FileNotFoundError):
		print('');
	return

def loadFileHoja2():
	try:
		file = open("precios.csv", "r") 
		for line in file:
				codigo = line.replace("\"","").split(',')[0].strip()
				precio = line.replace("\"","").split(',')[1].strip()
				hoja2[codigo] = precio;
		file.close();
	except (FileNotFoundError):
		print('');
	return


def loadFileCodGrales():
	try:
		file = open("codGrales.csv", "r") 
		for line in file:
				hojaGrales.append(line.replace("\"","").strip())
		file.close();
	except (FileNotFoundError):
		print('');
	return


def updateFile():
	file = open('hoja1_con_precios_hoja2.csv',"w") 
	for valor in hojaAct:
		file.write(valor+ '\n'); 	 
	file.close();
	return


def updateFile2():
	file = open('codGrales_act.csv',"w") 
	for valor in hojaGralesProc:
		file.write(valor+ '\n'); 	 
	file.close();
	return

						
#
#
# ********************************** Programa principal **********************************
#
#	datosAutopartesActualizado - Hoja 1  ---> Tiene informacion del producto
#	datosAutopartesActualizado - Hoja 2  ---> Tiene los precios actualizados
#
#	CodigosGeneralActualizado            ---> Formato de salida a actualizar	
#

hoja1 = [];
hoja2 = {};

hojaAct = [];
hojaData = {};

hojaGrales = [];
hojaGralesProc = [];


loadFileHoja1();
loadFileHoja2();


for valor in hoja1:
	codigo = valor.split(',')[0].strip()

	if codigo in hoja2.keys(): 	 

		precioAct = hoja2[codigo].strip()
		modelo = valor.split(',')[3].strip()
		hojaAct.append(codigo + ',' + precioAct + ', ,' + modelo + ', ACT')
		hojaData[codigo] = codigo + ',' + precioAct + ', ,' + modelo + ', ACT'

		print('Esta ' + codigo + ' --> ' + precioAct + ' ' + valor.split(',')[1].strip())

	else:
		hojaAct.append(valor.strip())
		hojaData[codigo] = valor.strip()

updateFile();

loadFileCodGrales();

tipo='';

for valor in hojaGrales:

	codigo = valor.split(',')[0].strip()
	nombre = valor.split(',')[1].strip()
	web = valor.split(',')[5].strip()

	if 'FAMILIA' in codigo:
		tipo=nombre.replace("\"","");

	else:

		if 'MARCA' not in codigo:

			if codigo in hojaData.keys():
				precio = hojaData[codigo].split(',')[1].strip()
				modelo = hojaData[codigo].split(',')[3].replace("UNIVERSAL UNIVERSAL ALL YEARS","UNIVERSAL, PARA TODOS LOS AÑOS").replace("UNIVERSAL ALL YEARS","UNIVERSAL, PARA TODOS LOS AÑOS").replace("ALL YEARS","PARA TODOS LOS AÑOS").strip()
			else:
				precio= valor.split(',')[2].strip()
				modelo= valor.split(',')[3].strip() 
	
			marca = ''			

			if ' DEPO ' in nombre:
				marca = 'DEPO'

			if ' TYC ' in nombre:
				marca = 'TYC'


			hojaGralesProc.append(codigo + ',' + nombre + ',' + marca + ',' + tipo + ',,' + precio  + ',,,,' + web + ',,' + modelo.replace(",",";") )

updateFile2();

