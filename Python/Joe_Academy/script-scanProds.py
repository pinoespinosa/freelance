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



def escanearProducto(url):

		if (url in listaDone):
			return

		porce = str( ( (len(listaDone) + len(listaFallo) ) / len(listaProductos))*100);
		print(   porce[0:4]+  '%  Escaneado ... ' +url )

		conn = http.client.HTTPSConnection("www.academy.com")
		headers = {    }

		cant =0
		conn.request("GET", url, headers=headers)
		res = conn.getresponse()
		data = res.read()

	#	try:
		#aaa = data.decode('unicode').encode('utf-8')
		#print(data)

		aaa = data.decode('utf8', 'replace')
		pagina = BeautifulSoup(aaa, 'html.parser');
		
		try:
			parentSKU = pagina.prettify().split('"parent_sku": "')[1].split('"')[0];
		except:
			listaFallo.append(url + '; No parentSKU')
			saveFile('productos_fail.csv', listaFallo)
			return
		titulo = pagina.find_all('h1', class_='rpdp-product-title')[0].text
		marca = pagina.prettify().split('itemprop="brand">')[1].split('<')[0].strip()
		descripcion = pagina.prettify().split('itemprop="description">')[1].split('<')[0].strip()
		
		categoria2 = pagina.prettify().split('id="brBreadCrum"')[1].split('value="')[1].split('"')[0].replace('&amp;','&');

		if (len(categoria2.split('|'))>3 ):
			categoria = categoria2.split('|')[1] + ' --> ' + categoria2.split('|')[2] + ' --> ' + categoria2.split('|')[3] 
		else:
			categoria = categoria2.replace('Academy|','');

#		conn.request("GET", 'https://www.academy.com/shop/AYRStoreLocatorServiceCmd?lat=40.8201966&lon=-96.70047629999999&ParentSKU=' + parentSKU, headers=headers)
#		res = conn.getresponse()
#		data = res.read()

#		aaa = data.decode("utf-8")
#		paginaStock = BeautifulSoup(aaa, 'html.parser');


		valores = pagina.prettify().split('catentry_id');
		for yyy in valores:
	
			if ('"Attributes" :' in yyy):

				resursos =  yyy.split('"Attributes" :	{')[1].split('}')[0] ;
				bbb = resursos.split(',');

				foto0 = yyy.split('ItemImage" : "//')[1].split('"')[0] ;



				fotos =  yyy.split('"DescAttributes" :	{')[1].split('}')[0] ;
				fotos = fotos.split(',');
				
				try:
					foto1 = fotos[0].split('"')[1].replace('image_','').split('#//')[1].strip();
				except:
					foto1 = ''

				try:
					foto2 = fotos[1].split('"')[1].replace('image_','').split('#//')[1].strip();
				except:
					foto2 = ''

				try:
					foto3 = fotos[2].split('"')[1].replace('image_','').split('#//')[1].strip();
				except:
					foto3 = ''


				item = yyy.split('mfPartNumber')[1].split(',')[0].replace('" : "','').replace('"','').strip() 
				sku = yyy.split('partNumber')[1].split(',')[0].replace('" : "','').replace('"','').strip()
				precio = yyy.split('listPrice')[1].split(',')[0].replace('" : "','').replace('"','').replace(':','').strip()

				try:
					color = bbb[0].split('"')[1].replace('Color_','').strip();
				except:
					color = ''

				try:
					tamanio = bbb[1].split('"')[1].replace('Size_','').strip();
				except:
					tamanio = ''

				listaResultados.append(parentSKU + ';' + item + ';' + sku + ';' + color + ';' + tamanio + ';' + titulo + ';' + marca + ';' + precio + ';' + descripcion + ';' + foto0 +';' + foto1 + ';' + foto2 + ';' + foto3 + ';' + categoria + ';' + url)
		

				
		saveFile('productos_datos.csv', listaResultados)
	
		listaDone.append(url)
		saveFile('productos_ya_escaneados.csv', listaDone)

		return;










listaProductos = []
listaProductosAux = []
loadFile('productos_links.csv', listaProductosAux)
for aa in listaProductosAux:
	if aa not in listaProductos:
		listaProductos.append(aa);


listaDone = []
listaResultados = []
listaFallo = []


loadFile('productos_ya_escaneados.csv', listaDone)
loadFile('productos_datos.csv', listaResultados)

for pagina in listaProductos:
	try:
		escanearProducto(pagina);
	except KeyboardInterrupt:
		print('The user abort the script.')
		sys.exit()
#	except UnicodeDecodeError:
#		print('Fallo ' + pagina)
#		listaFallo.append(pagina)
#		saveFile('fallo.csv', listaFallo)

#	except:
#		print('Fallo ' + pagina)
#		listaFallo.append(pagina)
#		saveFile('fallo.csv', listaFallo)

#for prod in listaProductos:
#	escanearProducto(prod);
#	saveFile('pino.csv',listaResultados);