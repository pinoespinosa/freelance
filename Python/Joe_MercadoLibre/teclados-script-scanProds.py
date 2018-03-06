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

		print(str(len(listaResultados) / len(listaProductos) * 100) +  '  ' +url)


		conn = http.client.HTTPSConnection("articulo.mercadolibre.com.mx")
		headers = {    }

		cant =0
		conn.request("GET", url, headers=headers)
		res = conn.getresponse()
		data = res.read()

	#	try:
		aaa = data.decode("utf-8")
		pagina = BeautifulSoup(aaa, 'html.parser');
			

		campos = []

		campos.append(url);


		try:
			campos.append('"' +  pagina.find_all('h1', class_='item-title__primary ')[0].text.strip().replace('"','\'\'') + '"');
		except:
			return

		campos.append('"' +  pagina.find_all('span', class_='price-tag')[0].find_all('span', class_='price-tag-symbol')[0]['content'].strip().replace('"','\'\'') + '"' );
		
		carater = pagina.find_all('li', class_='specs-item specs-item-primary') 

		marca = ''	
		for car in carater:
			if ('Marca del teclado' in car.text):
				marca= car.text.split(':')[0].replace('Marca del teclado','').strip() + '\n'
		campos.append( '"' + marca + '"' )

		modelo = ''	
		for car in carater:
			if ('Modelo del teclado' in car.text):
				modelo= car.text.split(':')[0].replace('Modelo del teclado','').strip() + '\n'
		campos.append( '"' + modelo + '"' )

		compat = ''	
		for car in carater:
			if ('Compatibilidad' in car.text):
				compat= car.text.split(':')[0].replace('Compatibilidad','').strip() + '\n'
		campos.append( '"' + compat + '"' )




		fotosAUX = pagina.find_all('img');
		fotos = []

		for aa in fotosAUX:
			if ('https://http2.mlstatic.com/' in aa.prettify() and 'NQ_NP' in aa['src']):
				campos.append('"' +  aa['src'] + '"'  );

		try:
			desc = pagina.find_all('div', class_='item-description__text')[0].prettify().strip().replace('<br>','\n').replace('</br>','');

			while ('    ' in desc):
				desc = desc.replace('    ',' ')

			while ('  ' in desc):
				desc = desc.replace('  ',' ')

			while ('\n \n' in desc):
				desc = desc.replace('\n \n','\n')

			while ('\n\n' in desc):
				desc = desc.replace('\n\n','\n')

			desc = desc.replace('<p>','').replace('</p>','').replace('</div>','');
			desc = desc.replace('"','\'\'');
			desc = desc.replace('<div class=\'\'item-description__text\'\'>','')
		except:
			desc = ''

		campos.append('"' +  desc.split('ntanos y con gusto te ayudaremos.')[1].split('_ _ _')[0].strip()  + '"' );

		listaResultados.append(';'.join(campos))

		saveFile('TECLADOS-Escaneado.csv', listaResultados)


		return;











listaProductos = []
listaDone = []
loadFile('Teclados.csv', listaProductos)


listaResultados = []

for pagina in listaProductos:
	try:
		escanearProducto(pagina);

	except KeyboardInterrupt:
		print('The user abort the script.')
		sys.exit()
#	except:
#		dd = ''
#for prod in listaProductos:
#	escanearProducto(prod);
#	saveFile('pino.csv',listaResultados);