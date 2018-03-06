import requests 				# pip install requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4


import urllib.request
from urllib.error import HTTPError
from urllib.error import URLError 

import http.client

from socket import timeout
from requests.exceptions import ConnectionError

import json, sys


def descargarResultado(URL, TIMEOUT, INTENTS):

	print(URL)

	tries=0;
	pagina='';
        
	if not URL:
		return 'FALLE';
	req = urllib.request.Request( URL );

	while tries<11:
		try:
			response = urllib.request.urlopen(req, timeout=TIMEOUT)
			html = response.read()
			print('test')
			pagina = BeautifulSoup(html, 'html.parser');
			tries = 12;
		except KeyboardInterrupt:
			print('The user abort the script.')
			sys.exit()
		except HTTPError:
#			print('No hay mas links.')
			tries = 12;
		except :
			tries += 1
			print ('Failed.' + '  Error connecting to server. Retring...' );
			if (tries==11):
				print('The serching was canceled. Try again late.')
				sys.exit()
	
	return pagina


def descargarResultadoData(URL, TIMEOUT, INTENTS, DATA, HEADERS):

	tries=0;
	pagina='';
        
	if not URL:
		return '';

	while tries<11:
		try:
			req = urllib.request.Request( URL);
			response = urllib.request.urlopen(req, timeout=TIMEOUT)
			html = response.read()

			pagina = BeautifulSoup(html, 'html.parser');

			tries = 12;
		except KeyboardInterrupt:
			print('The user abort the script.')
			sys.exit()
	
	return pagina;



def descargarResultadoDataSinBeautiful(URL, TIMEOUT, INTENTS, DATA, HEADERS):

	tries=0;
	pagina='';
        
	if not URL:
		return '';

	while tries<11:
		try:
			req = urllib.request.Request( URL);
			response = urllib.request.urlopen(req, timeout=TIMEOUT)
			html = response.read()

			pagina = BeautifulSoup(html, 'html.parser');

			tries = 12;
		except KeyboardInterrupt:
			print('The user abort the script.')
			sys.exit()
	
	return html;



def descargarResultadoData2(URL, TIMEOUT, INTENTS, DATA, HEADERS):


	tries=0;
	pagina='';
        
	if not URL:
		return '';

	while tries<11:
		try:
			response = requests.request("POST", URL, data=DATA, headers=HEADERS)
			html = response.text

			print (pagina)


			pagina = BeautifulSoup(html, 'html.parser');
			tries = 12;
		except KeyboardInterrupt:
			print('The user abort the script.')
			sys.exit()
		except HTTPError:
#			print('No hay mas links.')
			tries = 12;
		except :
			tries += 1
			print ('Failed.' + '  Error connecting to server. Retring...' );
			if (tries==11):
				print('The serching was canceled. Try again late.')
				sys.exit()
	
	return pagina

































