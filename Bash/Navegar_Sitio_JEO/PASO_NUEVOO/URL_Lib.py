import requests 				# pip install requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4


import urllib.request
from urllib.error import HTTPError
from urllib.error import URLError 

import http.client

from socket import timeout
from requests.exceptions import ConnectionError

import json, sys

def descargarResultadoData(URL, TIMEOUT, INTENTS, DATA, HEADERS):


	tries=0;
	pagina='';
        
	if not URL:
		return '';

	while tries<11:
		try:
			req = urllib.request.Request( URL);
			response = urllib.request.urlopen(req, timeout=TIMEOUT)
			html = response.read().decode('utf-8')


			pagina = BeautifulSoup(html, 'html.parser');
			tries = 12;
		except KeyboardInterrupt:
			print('The user abort the script.')
			sys.exit()
		except HTTPError:
#			print('No hay mas links.')
			tries = 12;

	
	return pagina
