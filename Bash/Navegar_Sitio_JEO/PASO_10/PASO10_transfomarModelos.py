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
		file = open("codGrales_act.csv", "r") 
		for line in file:
				listaParaLlenar.append(line);
		file.close();
	except (FileNotFoundError):
		print('');
	return

def updateFile():
	file = open('codGrales_act_tit_modif_version2.csv',"w") 
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

def updateFileRee():
	file = open('codGrales_act_tit_modif_version2_reemplazo.csv',"w") 
	for valor in listaRellenada:
		valor = valor.replace("\"","").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		valor = valor.replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ").replace("  "," ");
		

		anios = ['70', '71', '72', '73', '74', '75', '76', '77', '78', '79', '80', '81', '82', '83', '84', '85', '86', '87', '88', '89', '90', '91', '92', '93', '94', '95', '96', '97', '98', '99', '00', '01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20'];

		for anio1 in anios:
			for anio2 in anios:

				anio1full=0;
				if (int(anio1)>60):
					anio1full = '19' + anio1;
				else:
					anio1full = '20' + anio1;

				anio2full=0;
				if (int(anio2)>60):
					anio2full = '19' + anio2;
				else:
					anio2full = '20' + anio2;

				if ( int(anio1full) <  int(anio2full) ):
					valor = valor.replace( " "+anio1+"-"+anio2+" " , " "+anio1full+"-"+anio2full+" " );


		file.write(valor.strip()+ '\n'); 	 
	file.close();
	return


def updateFileNoModif():
	file = open('codGrales_act_tit_fail_version2.csv',"w") 
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


loadModelos();
loadRellenable();
loadModelosManual();

marcas = [ 'ACURA', 'ALFA ROMEO', 'AUDI', 'BMW', 'BUICK', 'CADILLAC', 'CHEVROLET', 'CHRYSLER', 'DATSUN', 'DINA', 'DODGE', 'EAGLE', 'FIAT', 'FORD', 'FREIGHTLINER', 'GMC', 'HINO', 'HONDA', 'HUMMER', 'HYUNDAI', 'INFINITI', 'INTERNATIONAL', 'ISUZU', 'JAGUAR', 'JEEP', 'KENWORTH', 'KIA', 'LAND ROVER', 'LEXUS', 'LINCOLN', 'MAN', 'MAZDA', 'MERCEDES BENZ', 'MERCURY', 'MINI', 'MITSUBISHI', 'NISSAN', 'OLDSMOBILE', 'PETERBILT', 'PEUGEOT', 'PLYMOUTH', 'PONTIAC', 'PORSCHE', 'RAMBLER', 'RENAULT', 'SAAB', 'SATURN', 'SEAT', 'SMART', 'SUBARU', 'SUZUKI', 'TOYOTA', 'VOLKSWAGEN', 'VOLVO' ];
for valor in lista:
	valor2 = valor.replace(",","").replace("\"","");		
	nuevoValor = valor2.replace(" 19"," ").replace("-19","-").replace(" 20"," ").replace("-20","-");
	for marca in marcas:
		if (marca in nuevoValor):
			hojaData[nuevoValor.replace(marca,"").replace("\"","").strip()] = marca + '_' + valor2.strip();
			indice.append(nuevoValor.replace(marca,"").replace("\"","").strip());

for valor in listaManual:		
	hojaData[valor.split(",")[0].replace("\"","").strip()] = valor.split(",")[1].replace("\"","").strip() + '_' + valor.split(",")[1].replace("\"","").strip() + ' ' + valor.split(",")[0].replace("\"","").strip();
	indice.append(valor.split(",")[0].replace("\"","").strip());

for valorfila in listaParaLlenar:
	valor = valorfila.split(",")[1].replace("\"","");
	marca = valorfila.split(",")[11].replace("\"","");

	lleno='';
	for text in indice:
		if ( (text in valor) or (valor.strip().endswith(text.strip())) ) and (not lleno):
#			print ('Reemplazo ' + text + ' por --- > ' + hojaData[text].split('_')[1] );
			listaRellenada.append(' ' + valorfila.replace(text.strip(), ' ' + hojaData[text].split('_')[1] + ' ',1).strip() + ', -- Modificado --');
			lleno='true';
	if (not lleno):
		listaRellenada.append(valorfila.strip());

updateFile();
updateFileRee();
updateFileNoModif();