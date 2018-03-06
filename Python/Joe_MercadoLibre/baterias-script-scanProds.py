import sys
sys.path.append('../STANDAR_LIBRARIES')

from URL_Lib import descargarResultadoData, descargarResultado, descargarResultadoDataSinBeautiful
from File_Lib import saveFile, saveFileExc, loadFile
import re
import requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4

import http.client 
http.client._MAXHEADERS = 1000
sys.setrecursionlimit(1500)

#
#
# ********************************** Programa principal **********************************
#
#



def escanearProducto(url):

		print( str(len(listaResultados)) + ' ' + str(len(listaResultados) / len(listaProductos) * 100) +  '  ' +url)


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
			if ('Marca' in car.text and 'bater' not in car.text):
				marca= car.text.split(':')[0].replace('Marca','').strip() + '\n'
		campos.append( '"' + marca + '"' )

		modelo = ''	
		for car in carater:
			if ('Modelo de la batería' in car.text):
				modelo= car.text.split(':')[0].replace('Modelo de la batería','').strip() + '\n'

		if (modelo == ''):		
			for car in carater:
				if ('Modelo' in car.text):
					modelo= car.text.split(':')[0].replace('Modelo','').strip() + '\n'


		campos.append( '"' + modelo + '"' )

		compat = ''	
		for car in carater:
			if ('Compatibilidad con notebooks' in car.text):
				compat= car.text.replace('Compatibilidad con notebooks','').replace('Compatible','').strip() + '\n'
		campos.append( '"' + compat + '"' )

		carga = ''	
		for car in carater:
			if ('Capacidad de la batería' in car.text):
				carga= car.text.split(':')[0].replace('Capacidad de la batería','').strip() + '\n'
		campos.append( '"' + carga + '"' )

		
		try:
			desc = pagina.find_all('div', class_='item-description__text')[0].prettify()
			desc = desc.strip()
			desc = desc.replace('<br>','\n')
			desc = desc.replace('</br>','');

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

		aux = desc;


		descSuma = ''

		for aa in desc.split('======================'):
			if ( 'Para saber si este producto es compatible' not in aa and
				 'NO ES NECESARIO CONFIRMAR' not in aa and
				 'AQUI PUEDES VER TODOS NUESTROS PRODUCTOS A' not in aa and 
				 'Nuestras baterias son %100 nuevas' not in aa and
				 'Para saber si esta bateria es compatible' not in aa  and
				 'Bateria Para ' not in aa and 
 				 ( 'COMPATIBLE CON ' in aa or 'Compatible con ' in aa or 'REEMPLAZA A LOS SIGUIENTES' in aa or 'Numeros de parte compatibles' in aa ) and 
 				 'Bateria Laptop' not in aa):
				descSuma += aa.strip() + '\n\n';
#			else:
#				print(aa.strip())
		
		if (descSuma.strip() == ''):
			descSuma = aux;

		campos.append('"' +  descSuma  + '"' );

		fotosAUX = pagina.find_all('img');
		fotos = []

		for aa in fotosAUX:
			if ('https://http2.mlstatic.com/' in aa.prettify() and 'NQ_NP' in aa['src']):
				campos.append('"' +  aa['src'] + '"'  );


		listaResultados.append(';'.join(campos))

		saveFile('BATERIAS-Escaneado.csv', listaResultados)


		return;











listaProductos = []
listaDone = []
loadFile('Baterias.csv', listaProductos)


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