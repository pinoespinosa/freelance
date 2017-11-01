import requests 				# pip install requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4

import urllib.request
from urllib.error import HTTPError
from urllib.error import URLError 

from datetime import datetime
from socket import timeout
from requests.exceptions import ConnectionError

from selenium import webdriver
from selenium.common.exceptions import WebDriverException
from selenium.webdriver.common.keys import Keys
import json, errno, os, time, glob, sys, codecs

# ------------------------------------------ LINK LETRAS ------------------------------------------

def loadLetras():
	try:
		file = open("letras.csv", "r") 
		for line in file:
			letrasLista.append(line.strip())
		file.close();
	except (FileNotFoundError):
		print('');
	return

def saveLetras():
	file = open('letras.csv',"w") 
	for valor in letrasLista:
		if (valor not in hecha):
			file.write(valor + '\n'); 	 
	file.close();
	return

# ------------------------------------------ LINK PAGINAS CATEGORIA  ------------------------------------------

def loadPagesLiks():
	try:
		file = open("pageLinks.csv", "r") 
		for line in file:
			linkLista.append(line.strip())
		file.close();
	except (FileNotFoundError):
		print('');
	return

def savePagesLiks():
	file = open('pageLinks.csv',"w") 
	for valor in linkLista:
		if (valor not in hechaLinks):
			file.write(valor + '\n'); 	 
	file.close();
	return

# ------------------------------------------ LINK PAGINA INDIVIDUAL  ------------------------------------------

def loadPagesLiksIndiv():
	try:
		file = open("pageLinksIndiv.csv", "r") 
		for line in file:
			linksNegocios.append(line.strip())
		file.close();
	except (FileNotFoundError):
		print('');
	return

def savePagesLiksIndiv():
	file = open('pageLinksIndiv.csv',"w") 
	for valor in linksNegocios:
		if valor not in paginaEscaneada:
			file.write(valor + '\n'); 	 
	file.close();
	return


# ------------------------------------------ CSV FILES  ------------------------------------------


def saveResult():
	file = open('resultado.csv',"w") 
	for valor in resultado:
		file.write(valor + '\n'); 	 
	file.close();
	return





def loadStep():
	letra = '';
	try:
		file = open('step.csv', "r") 
		for line in file:
			letra = letra + line.strip();
		file.close();

		return json.loads(letra);

	except (FileNotFoundError):
		print('Error');
	return json.loads('{ "letrasDONE":"false", "paginaletrasDONE":"false", "paginasnegociosDONE":"false" }');


def saveStep():
	file = open('step.csv',"w") 
	file.write(json.dumps(jsonConfig)); 	 
	file.close();
	return








def descargarPaginasLetra():

	browser = webdriver.Firefox()
	browser.get('https://www.paginasamarillas.es/');
	contenido = browser.find_element_by_css_selector('li.l-floatleft:nth-child(2) > a:nth-child(1)').click();  # Pagina principal

	letras = browser.find_element_by_xpath('//*[@id="content-inner"]/div[2]/div[1]/ul');
	letras = letras.find_elements_by_xpath('//*[@id="content-inner"]/div[2]/div[1]/ul/li');

	for aa in letras:
		aa = aa.find_element_by_xpath('a');
		letrasLista.append(aa.get_attribute("href"));

	return

def descargarPaginasInicial(browser):

	browser.get('https://www.paginasamarillas.es/');
	contenido = browser.find_element_by_css_selector('li.l-floatleft:nth-child(2) > a:nth-child(1)').click();  # Pagina principal

	letras = browser.find_element_by_xpath('//*[@id="content-inner"]/div[2]/div[1]/ul');
	letras = letras.find_elements_by_xpath('//*[@id="content-inner"]/div[2]/div[1]/ul/li');

	for aa in letras:
		aa = aa.find_element_by_xpath('a');
		letrasLista.append(aa.get_attribute("href"));

	return

def descargarPaginasLetras(link, browser):

	browser.get(link);
	activ = browser.find_elements_by_xpath('//*[@id="content-inner"]/div[2]/div[3]/ul/li');  # Pagina principal

	for bb in activ:
		bb = bb.find_element_by_xpath('a');
		linkpagees = bb.get_attribute("href");

		if linkpagees not in linkLista:
			linkLista.append(linkpagees);

	return activ;

def descargarPaginasEspecifica(link, browser):

	pagina=1;
	browser.get(link);
	negocios = browser.find_elements_by_xpath('/html/body/div[1]/div[1]/section[1]/ul/li');

	htmls = [];

	for ne in negocios:
		html = ne.get_attribute('innerHTML')
		html = BeautifulSoup(html, 'html.parser');
		html = html.find_all(class_='m-results-business--name')[0].find_all('a')[0]['href'];
	
		if html not in linksNegocios:
			htmls.append(html);
			linksNegocios.append(html);

	for lii in htmls:
		descargarResultado(lii,browser);

	while (len(negocios)>14):

		browser.get(link + str(pagina));
		negocios = browser.find_elements_by_xpath('/html/body/div[1]/div[1]/section[1]/ul/li');

		htmls = [];
		for ne in negocios:
			html = ne.get_attribute('innerHTML')
			html = BeautifulSoup(html, 'html.parser');
				
			try: 
				html = html.find_all(class_='m-results-business--name')[0].find_all('a')[0]['href'];
				if html not in linksNegocios:
					htmls.append(html);
					linksNegocios.append(html);
			except :
				html = ''
	
		for lii in htmls:
			descargarResultado(lii,browser);

		pagina=pagina +1;

	return;




def descargarResultado(link, browser):

	browser.get(link);

	valor = browser.page_source.split('data-business=')[1].split('data-power')[0];
	valor = valor.replace("&quot;","\"");
	valor = valor.replace("\"",'',1) + 'FINNN';
	valor = valor.replace('}}\" FINNN','}}');
	d = json.loads(valor);

	try: 
		email = d["customerMail"]
	except :
		email = ''

	try: 
		name = d["info"]["name"]
	except :
		name = ''

	try: 
		businessAddress = d["info"]["businessAddress"].replace(";",",")
	except :
		businessAddress = ''

	try: 
		phone = d["info"]["phone"]
	except :
		phone = ''	

	try: 
		activity = d["info"]["activity"]
	except :
		activity = ''	

	tupla = '"' + email + '";"' + name + '";"' + businessAddress + '";"' + phone + '";"' + activity + '"';

	resultado.append( tupla )
	saveResult();











#
#
# ********************************** Programa principal **********************************
#
#

letrasLista =[];

linkLista =[];
hecha =[];

hechaLinks=[];

linksNegocios=[];
paginaEscaneada = [];

resultado = [];



iterar = 0;
archi = 0;
browser = webdriver.Firefox()


jsonConfig = loadStep();
loadLetras();
loadPagesLiks();

print(jsonConfig);

if (jsonConfig["letrasDONE"] != 'true' ):
	descargarPaginasInicial(browser);
jsonConfig["letrasDONE"]= 'true';
saveStep();

if (jsonConfig["paginaletrasDONE"] != 'true' ):
	for aa in letrasLista:
		browser.get(aa);
		activ = browser.find_elements_by_xpath('//*[@id="content-inner"]/div[2]/div[3]/ul/li');  # Pagina principal
		pagina=1;
		while (len(activ)>14):
			try:
				activ = descargarPaginasLetras( aa.replace("_1","_"+str(pagina)),browser );
			except:
				activ = [];
			savePagesLiks();
			pagina=pagina +1;
		hecha.append(aa);
		saveLetras();
jsonConfig["paginaletrasDONE"]= 'true';
saveStep();

loadPagesLiksIndiv();
if (jsonConfig["paginasnegociosDONE"] != 'true' ):

	for bb in linkLista:
		try: 
			descargarPaginasEspecifica(bb, browser);
		except WebDriverException:
			print('');
		except:
			print('');
		savePagesLiksIndiv();
		hechaLinks.append(bb);
		savePagesLiks();
jsonConfig["paginasnegociosDONE"]= 'true';
saveStep();
