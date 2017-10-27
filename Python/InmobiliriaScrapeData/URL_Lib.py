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
