import requests 				# pip install requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4


import urllib.request
from urllib.error import HTTPError
from urllib.error import URLError 

from socket import timeout
from requests.exceptions import ConnectionError

import json, sys
import http.client


def descargarResultado5(URL, TIMEOUT, INTENTS):

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







def descargarResultado(URL, TIMEOUT, INTENTS):

	tries=0;
	pagina='';
        
	if not URL:
		return '';










	while tries<11:
			conn = http.client.HTTPConnection("www.radec.com.mx")

			headers = {
    'host': "www.radec.com.mx",
    'user-agent': "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:57.0) Gecko/20100101 Firefox/57.0",
    'accept': "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    'accept-language': "en-US,en;q=0.5",
    'cookie': "__utma=72389776.1460591499.1500578541.1513941049.1514450969.24; __utmz=72389776.1500578541.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); SESS721296bc8a9b4f397fb8b90d35e8c32d=pk7tp95lrreil8p5jl8htddkl3; has_js=1; boost-gzip=true; __utmb=72389776.9.10.1514450969; __utmc=72389776; DRUPAL_UID=1970; __utmt=1",
    'connection': "keep-alive",
    'upgrade-insecure-requests': "1",
    'if-modified-since': "Thu, 28 Dec 2017 09:15:02 GMT",
    'cache-control': "no-cache",
    'postman-token': "480881e3-be02-522d-c94b-9b2996aec559"
    }

			conn.request("GET", URL, headers=headers)

			res = conn.getresponse()
			html = res.read()			
			pagina = BeautifulSoup(html, 'html.parser');
			tries = 12;

	
	return str(html)


























