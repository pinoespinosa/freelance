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
		file = open("urls.csv", "r") 
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

#browser = webdriver.Firefox()


for url in lista_URL:
	iterar=iterar+1;
	response = urllib.request.urlopen(url, timeout=120)
	html = response.read()
	html = BeautifulSoup(html, 'html.parser');

	nombre = html.find_all(class_='heading_title')[0].text.strip();

	try:
		tel = html.find_all(class_='blEntry phone')[0].text.strip();
	except:
		tel =  ''

	try:
		dire = html.find_all(class_='address')[0].text.strip();
	except (FileNotFoundError):
		dire =  ''

	try:
		cuisines = html.find_all(class_='ui_column is-6 cuisines')[0].find_all(class_='text')[0].text.strip();
	except:
		cuisines =  ''

	horaComp =  ''
	try:
		horas = html.find_all(class_='hours content')[0].find_all(class_='detail');
		for textt in horas:
			horadia = '----'; 
			for thor in textt.find_all(class_='hoursRange'):
				horadia = horadia + ' / ' +  thor.text
			horadia = textt.find_all(class_='day')[0].text + ' ' + horadia; 
			horaComp = horaComp + horadia.replace("---- /","")  + '\n';
	except:
		horaComp =  ''

	filas = html.find_all(class_='details_tab')[0].find_all(class_='row');

	features =  ''
	try:
		for textt in filas:
			if "Restaurant features" in (str(textt)):
				features = textt.find_all(class_='content')[0].text.strip()
	except:
		features =  ''
	
	meals =  ''
	try:
		for textt in filas:
			if "Meals" in (str(textt)):
				meals = textt.find_all(class_='content')[0].text.strip()
	except:
		meals =  ''
	
	descri =  ''
	try:
		descripp = html.find_all(class_='additional_info');
		for textt in descripp:
			if "Description " in (str(textt)):
				descri = textt.find_all(class_='content')[0].text.strip()
	except:
		descri =  ''

	goodFor =  '';
	try:
		for textt in filas:
			if "Good for" in (str(textt)):
				goodFor = textt.find_all(class_='content')[0].text.strip()
	except:
		goodFor =  '';

	email = '';
	try:
		email2 = html.find_all(class_='detailsContent')[0].find_all('li');
		for textt in email2:
			if "E-mail" in (str(textt)):
				email = textt.find_all('a')[0]['href'].strip().replace("mailto:","")
	except :
		email = '';


	website = '';
#	try:
#
#		pino = '1';
#		for aa in browser.window_handles:
#			if (pino == '1'):
#				pino='true'
#			else:
#				browser.switch_to_window(aa)
#				browser.close()
#		browser.switch_to_window(browser.window_handles[0])
#		browser.get(url)
#		browser.find_element_by_css_selector('div.blEntry:nth-child(3)').click();
#		browser.switch_to_window(browser.window_handles[1])
#		website = browser.current_url
#		while( "about:blank" in website):
#			time.sleep(5)
#			website = browser.current_url
#		browser.close()
#
#	except :
#		website = '';
		
	print (nombre + ' ' + website)
	listaCSV.append( '"' + nombre.replace("\"","") +'","'+ descri.replace("\"","") +'","'+ tel.replace("\"","") +'","'+ dire.replace("\"","") +'","'+ website.replace("\"","") +'","'+ email.replace("\"","") +'","'+ cuisines.replace("\"","") +'","'+ horaComp.replace("\"","") +'","'+ features.replace("\"","") +'","'+ meals.replace("\"","") +'","'+ goodFor.replace("\"","") +'","'+ url.replace("\"","") + '"')

	if (iterar==50):
		archi = archi + 1;
		iterar = 0;
		createFileCSV('file' + str(archi));
createFileCSV('file' + str(archi));
