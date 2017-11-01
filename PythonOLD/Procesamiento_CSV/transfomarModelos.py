import requests 				# pip install requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4

import urllib.request
from urllib.error import HTTPError
from urllib.error import URLError 

from datetime import datetime
from socket import timeout
from requests.exceptions import ConnectionError

import json, errno, os, time, glob, sys, codecs


def loadModelos():
	try:
		file = open("modelos.txt", "r") 
		for line in file:
				lista.append(line);
		file.close();
	except (FileNotFoundError):
		print('');
	return


def loadModelosManual():
	try:
		file = open("modelosManual.txt", "r") 
		for line in file:
				listaManual.append(line);
		file.close();
	except (FileNotFoundError):
		print('');
	return

def loadRellenable():
	try:
		file = open("rellenable.txt", "r") 
		for line in file:
				listaParaLlenar.append(line);
		file.close();
	except (FileNotFoundError):
		print('');
	return

def updateFile():
	file = open('resultado.csv',"w") 
	for valor in listaRellenada:
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

lista = [];
listaManual = [];


listaParaLlenar = [];
listaRellenada = [];

hojaAct = [];

hojaData = {};


loadModelos();
loadRellenable();
loadModelosManual();

marcas = [ 'ACURA', 'ALFA ROMEO', 'AUDI', 'BMW', 'BUICK', 'CADILLAC', 'CHEVROLET', 'CHRYSLER', 'DATSUN', 'DINA', 'DODGE', 'EAGLE', 'FIAT', 'FORD', 'FREIGHTLINER', 'GMC', 'HINO', 'HONDA', 'HUMMER', 'HYUNDAI', 'INFINITI', 'INTERNATIONAL', 'ISUZU', 'JAGUAR', 'JEEP', 'KENWORTH', 'KIA', 'LAND ROVER', 'LEXUS', 'LINCOLN', 'MAN', 'MAZDA', 'MERCEDES BENZ', 'MERCURY', 'MINI', 'MITSUBISHI', 'NISSAN', 'OLDSMOBILE', 'PETERBILT', 'PEUGEOT', 'PLYMOUTH', 'PONTIAC', 'PORSCHE', 'RAMBLER', 'RENAULT', 'SAAB', 'SATURN', 'SEAT', 'SMART', 'SUBARU', 'SUZUKI', 'TOYOTA', 'VOLKSWAGEN', 'VOLVO' ];
for valor in lista:		
	nuevoValor = valor.replace(" 19"," ").replace("-19","-").replace(" 20"," ").replace("-20","-").replace(",","");
	for marca in marcas:
		if (marca in nuevoValor):
			hojaData[' ' + nuevoValor.replace(marca,"").strip() + ' '] = marca;

for valor in listaManual:		
	hojaData[' ' +valor.split(",")[0].replace("\"","").strip() + ' '] = valor.split(",")[1].replace("\"","").strip();


for valor in listaParaLlenar:
	lleno='';
	for text in hojaData.keys():
		if (text in valor) and (not lleno):
			listaRellenada.append(' ' + valor.replace(text, hojaData[text] + ' ' +text).strip() + '                Modificado');
			lleno='true';
	if (not lleno):
		listaRellenada.append(valor.strip());

updateFile();
