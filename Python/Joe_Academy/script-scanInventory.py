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



def escanearProducto(fila, tiendaSelect):
		


		parentSKU = fila.split(';')[0];
		sku = fila.split(';')[2];

		if (parentSKU not in stocks):
			conn = http.client.HTTPSConnection("www.academy.com")
			headers = {    }
			conn.request("GET", 'https://www.academy.com/shop/AYRStoreLocatorServiceCmd?lat=40.8201966&lon=-96.70047629999999&ParentSKU=' + parentSKU, headers=headers)
			res = conn.getresponse()
			data = res.read()

			aaa = data.decode("utf-8")
			paginaStock = BeautifulSoup(aaa, 'html.parser');

			stocks[parentSKU]=paginaStock;

		else:
			paginaStock = stocks[parentSKU];

		try:
			stockTiennda = paginaStock.prettify().split(tiendaSelect)[1].split('store-')[0].split(sku)[1].split('",')[0].replace('":"','').split('"')[0];
		except:
			stockTiennda = '0'
		
		porce = str ( (len(listaResultadosSave) / len(listaResultados))*100);
		print(   porce[0:4]+  '%  Escaneado ... ' + sku )
		

		listaResultadosSave.append(fila + ';' + str(stockTiennda) )

		saveFile('productos_con_stock.csv', listaResultadosSave)

		return;










stocks = {}

listaResultados = []
loadFile('productos_datos.csv', listaResultados)

listaTienda = []
loadFile('TIENDA.txt', listaTienda)
tiendaSelect = listaTienda[0];

listaResultadosSave = [];

for pagina in listaResultados:
	try:
		escanearProducto(pagina, tiendaSelect);

	except KeyboardInterrupt:
		print('The user abort the script.')
		sys.exit()

#for prod in listaProductos:
#	escanearProducto(prod);
#	saveFile('pino.csv',listaResultados);