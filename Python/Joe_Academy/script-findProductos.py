import sys
sys.path.append('../STANDAR_LIBRARIES')

from URL_Lib import descargarResultadoData, descargarResultado, descargarResultadoDataSinBeautiful
from File_Lib import saveFile, saveFileExc, loadFile
import re
import requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4

import http.client 
http.client._MAXHEADERS = 1000


#
#
# ********************************** Programa principal **********************************
#
#


def descargarCategorias():

	url = "http://catalogo.marmomac.it/ajax/getListEspositori.php"

	payload = "start=0&count=1669&idenficAttivo=47"
	headers = {
    'cookie': "PHPSESSID=sk653tbt78l7vqrfh32imdgs02; _ga=GA1.2.709519879.1506900857; _gid=GA1.2.1844892756.1506900857; __atuvc=1%7C40; __atuvs=59d17b799e1461e7000; _hjIncludedInSample=1",
    'origin': "http://catalogo.marmomac.it",
    'accept-encoding': "gzip, deflate",
    'accept-language': "es-ES,es;q=0.8,en;q=0.6",
    'user-agent': "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.91 Safari/537.36",
    'content-type': "application/x-www-form-urlencoded; charset=UTF-8",
    'accept': "text/html, */*; q=0.01",
    'referer': "http://catalogo.marmomac.it/en/cat/3t-utensili-diamantati-sas-di-tognini-nicola-849",
    'x-requested-with': "XMLHttpRequest",
    'connection': "keep-alive",
    'cache-control': "no-cache",
    'postman-token': "c030ca44-bd20-56ae-52e7-fa7d9404d27e"
    }

	response = requests.request("POST", url, data=payload, headers=headers)
	pagina = response.text.split("class=\"elemento open-espositore \"");

	for stan in pagina:
		if 'data-codesp="' in stan:

#			print(stan);
			url = "http://catalogo.marmomac.it/ajax/getSchedaElemento.php"
			payload = 'identific=' + stan.split('data-identific="')[1].split('"')[0] + '"'
			response = requests.request("POST", url, data=payload, headers=headers)
			
			try:
				titulo = response.text.split('id="nomeElemento">')[1].split("<")[0].replace('"','').replace(',',';').strip();
			except:
				titulo='';

			try:
				actividad = response.text.split('<div class="scroller-attivita">')[1].split("</div>")[0].replace('"','').replace(',',';').strip();
				actividad = actividad.replace("<br />","").replace("&bull;","*");
			except:
				actividad='';

			try:
				pais = response.text.split('<div class="testo">')[1].split("</div>")[0].split('<br>')[2].replace('"','').replace(',',';').strip();
			except:
				pais='';

			try:
				direccion = response.text.split('<div class="testo">')[1].split("</div>")[0].replace('<br>',';').replace('"','').replace(',',';').strip();
			except:
				direccion='';

			try:
				telefono = response.text.split('<div class="titolo">Phone</div>')[1].split("</div>")[0].split('class="testo">')[1].replace('"','').replace(',',';').strip();
			except:
				telefono='';

			try:
				email = response.text.split('<div class="titolo">Email</div>')[1].split("</div>")[0].split('target="_blank">')[1].split('</a>')[0].replace('"','').replace(',',';').strip();
			except:
				email='';


			print(titulo);
			print(pais);
			print(actividad);
			print(direccion);
			print(telefono);
			print(email);

			print('')
			lista.append('"'+titulo+'";' + '"'+pais+'";' + '"'+actividad+'";' +'"'+direccion+'";' + '"'+telefono+'";' + '"'+email+'"')

	return;


def escanearPagina(url):



	pag = 1;
	cant = 1;
	while (cant>0):

		conn = http.client.HTTPSConnection("www.academy.com")
		headers = {    }

		print(url)
		cant =0
		conn.request("GET", url + "?beginIndex="+str(pag), headers=headers)
		res = conn.getresponse()
		data = res.read()

		try:
			aaa = data.decode("utf-8")
			pagina = BeautifulSoup(aaa, 'html.parser');
			valores = pagina.find_all('li');

			for tt in valores:
				if ('catalogEntry_img' in tt.prettify()):
					linkPagina = tt.find_all('a')[0]['href'] 
		
					if (linkPagina not in listaProductos):	
						cant = cant + 1;
						listaProductos.append(linkPagina);


		except:
			aaa = ''

#		pagina = descargarResultadoData(url+str(pag), 360, 10, payload, headers)
#		pagina = pagina.find_all(class_='type-product');

#		for link in pagina:
#			linkPagina = link.prettify().strip().split('href="')[1].split('"')[0];

#			if (linkPagina not in listaProductos):	
#				cant = cant + 1;
#				listaProductos.append(linkPagina);

		print('Productos encontrados:' + str(len(listaProductos)))
		pag = pag + 48
		saveFile('productos_links.csv', listaProductos)

	print('Escaneado ... ' +url)

	return;










# Escanear las paginas para obtener los links a los productos
listaLinksIniciales = [];

conn = http.client.HTTPSConnection("www.academy.com")
headers = {    }
conn.request("GET", "/", headers=headers)
res = conn.getresponse()
data = res.read()
aaa = data.decode("utf-8")
pagina = BeautifulSoup(aaa, 'html.parser');

for y in pagina.find_all('a'):
	try:
		if ( ('/shop/browse/' in y['href']) ):

			testo = y.prettify();
			if ( ('mm-sub-cat-title' not in testo) and ('z-btn z-btn-link' not in testo) and ('rh-mainmenu-primary-inner-title' not in testo) and ('aso-focus' not in testo)):
				liink = y['href'].split('?')[0]
				if (liink not in listaLinksIniciales and len(liink.split('/'))>4 ):
					listaLinksIniciales.append(liink)

	except:
		h =' '

for yy in listaLinksIniciales:
	print(yy)

# listaLinksIniciales = [ 'http://www.vianala.com/catalogo/bolsas/crossbody/'];







listaProductos = []
for pagina in listaLinksIniciales:
	escanearPagina(pagina);

