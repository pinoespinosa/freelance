import requests 				# pip install requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4


from socket import timeout
from requests.exceptions import ConnectionError
import http.client

import json, sys

def descargarResultadoData(URLBASE, AA,  TIMEOUT, INTENTS, DATA, HEADERS):


	tries=0;
	pagina='';
        
	if not AA:
		return '';

	while tries<11:
		try:

			conn = http.client.HTTPSConnection(URLBASE)

			headers = {
    		}

			conn.request("GET", AA, headers=headers)

			res = conn.getresponse()
			data = res.read()
			pagina = BeautifulSoup(data.decode("utf-8"), 'html.parser');



			tries = 12;
		except KeyboardInterrupt:
			print('The user abort the script.')
			sys.exit()
		except HTTPError:
#			print('No hay mas links.')
			tries = 12;

	
	return pagina


def descargarResultadoData2(URLBASE, AA,  TIMEOUT, INTENTS, DATA, HEADERS):


	url = "http://www.metrocuadrado.com/search/list/ajax"


	headers = {
	    'cache-control': "no-cache",
    	'postman-token': "73225db5-3e89-e5f2-1cc6-a99ac7626bd6"
    	}

	response = requests.request("POST", url, headers=headers, params=AA)


	pagina = BeautifulSoup(response.text, 'html.parser');
	return pagina

def descargarResultadoData3(URLBASE, AA,  TIMEOUT, INTENTS, DATA, HEADERS):

	url = URLBASE.strip()

	payload = "identific=849"
	headers = {
	    }

	response = requests.request("GET", url, data=payload, headers=headers)

	pagina = BeautifulSoup(response.text, 'html.parser');
	return pagina