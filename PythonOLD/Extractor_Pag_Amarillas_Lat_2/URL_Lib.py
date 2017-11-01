import requests 				# pip install requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4


import urllib.request
from urllib.error import HTTPError
from urllib.error import URLError 

from socket import timeout
from requests.exceptions import ConnectionError

import json, sys


def descargarResultado(URL, TIMEOUT, INTENTS):

	tries=0;
	pagina='';
        
	if not URL:
		return '';
	req = urllib.request.Request( URL );

	while tries<11:
		try:
			response = urllib.request.urlopen(req, timeout=TIMEOUT)
			html = response.read()
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


































