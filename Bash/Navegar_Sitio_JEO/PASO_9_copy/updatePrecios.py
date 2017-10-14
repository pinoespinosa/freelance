import requests 				# pip install requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4

import urllib.request
from urllib.error import HTTPError
from urllib.error import URLError 

from datetime import datetime
from socket import timeout
from requests.exceptions import ConnectionError

import json, errno, os, time, glob, sys, codecs



def loadFileCodGrales():
	try:
		file = open("radec_scan.csv", "r") 
		for line in file:
				hojaGrales.append(line.replace("\"","").strip())
		file.close();
	except (FileNotFoundError):
		print('');
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


loadFileCodGrales();


tipo='';

for valor in hojaGrales:

	codigo = valor.split(',')[0].strip()
	nombre = valor.split(',')[1].strip()
	web = valor.split(',')[2].strip()

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

