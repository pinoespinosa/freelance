import requests 				# pip install requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4

import urllib.request
from urllib.error import HTTPError
from urllib.error import URLError 

from datetime import datetime
from socket import timeout
from requests.exceptions import ConnectionError

import json, errno, os, time, glob, sys, codecs

def loadRellenable():
	try:
		file = open("codGrales_act_tit_modif_version2.csv", "r") 
		for line in file:
				listaParaLlenar.append(line);
		file.close();
	except (FileNotFoundError):
		print('');
	return

def updateFile1():
	file = open('codGrales_act_tit_cortado_mas_60.csv',"w") 
	for valor in listaRellenada:
		valor = valor.replace("\"","").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		file.write(valor.strip()+ '\n'); 	 
	file.close();
	return



def updateFile2():
	file = open('codGrales_act_tit_cortado_menos_igual_60.csv',"w") 
	for valor in listaRellenadaOriginal:
		valor = valor.replace("\"","").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		file.write(valor.strip()+ '\n'); 	 
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

lista = [];
listaManual = [];


listaParaLlenar = [];
listaRellenada = [];
listaRellenadaOriginal = [];
hojaAct = [];

hojaData = {};
indice = [];


loadRellenable();

for valorfila in listaParaLlenar:
	valor = valorfila.split(",")[1].replace("\"","");
	marca = valorfila.split(",")[11].replace("\"","");

	if ( len(valor) > 60 ) :
		listaRellenada.append(valorfila.strip());
	else:
		listaRellenadaOriginal.append(valorfila.strip());

updateFile1();
updateFile2();
