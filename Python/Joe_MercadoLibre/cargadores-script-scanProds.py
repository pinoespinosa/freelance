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
			if ('Marca del cargador' in car.text):
				marca= car.text.split(':')[0].replace('Marca del cargador','').strip() + '\n'
		campos.append( '"' + marca + '"' )

		modelo = ''	
		for car in carater:
			if ('Modelo del cargador' in car.text):
				modelo= car.text.split(':')[0].replace('Modelo del cargador','').strip() + '\n'
		campos.append( '"' + modelo + '"' )

		compat = ''	
		for car in carater:
			if ('Notebooks compatibles' in car.text):
				compat= car.text.replace('Notebooks compatibles','').strip() + '\n'
		campos.append( '"' + compat + '"' )

		carga = ''	
		for car in carater:
			if ('Potencia' in car.text):
				carga= car.text.split(':')[0].replace('Potencia','').strip() + '\n'
		campos.append( '"' + carga + '"' )

		cone = ''	
		for car in carater:
			if ('Conector de salida' in car.text):
				cone= car.text.split(':')[0].replace('Conector de salida','').strip() + '\n'
		campos.append( '"' + cone + '"' )



		try:
			desc = pagina.find_all('div', class_='item-description__text')[0].prettify().strip().replace('<br>','\n').replace('</br>','');
		except:
			try:
				desc = pagina.find_all('div', class_='item-description__html-text')[0].find_all('p')[0].text;
			except:
				desc = ''


		while ('    ' in desc):
			desc = desc.replace('    ',' ')
			print('.')

		while ('  ' in desc):
			desc = desc.replace('  ',' ')
			print(',')

		while ('\n \n' in desc):
			desc = desc.replace('\n \n','\n')
			print(';')


		while ('\n\n' in desc):
			desc = desc.replace('\n\n','\n')
			print(':')


		desc = desc.replace('<p>','').replace('</p>','').replace('</div>','');
		desc = desc.replace('"','\'\'');
		desc = desc.replace('<div class=\'\'item-description__text\'\'>','')


		aux = desc;

		try:
			desc = 'Disponible en ' + desc.split('- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -')[0].split('Disponible en')[1].strip()
		except:
			desc = desc

		
		list1 = desc.replace('==============================','--------------------------------------------------------').replace('======================','--------------------------------------------------------').split('--------------------------------------------------------');
		desc = ''
		for aa in list1:
			if ( 'Para saber si este producto es compatible' not in aa and
			 'NO ES NECESARIO CONFIRMAR' not in aa and
			 'AQUI PUEDES VER TODOS NUESTROS PRODUCTOS A' not in aa and 
			 'Nuestras baterias son %100 nuevas' not in aa and
			 'Para saber si esta bateria es compatible' not in aa  and
			 '¿QUÉ HAY EN LA CAJA?' not in aa 
			 #and ( 'Compatible con' in aa or 'COMPATIBLE CON' in aa or 'MODELOS DE LAPTOP COMPATIBLES' in aa or 'Compatibilidad' in aa  or 'Reemplaza a los siguiente numeros de parte:' in aa ) 
			 ):
				desc += aa.strip() + '\n\n';
			#else:
			#	print(aa.strip())

		if (desc.strip() == ''):
			desc = aux;
			print('vacio ' + aux)

		campos.append('"' +  desc  + '"' );

		fotosAUX = pagina.find_all('img');
		fotos = []

		for aa in fotosAUX:
			if ('https://http2.mlstatic.com/' in aa.prettify() and 'NQ_NP' in aa['src']):
				campos.append('"' +  aa['src'] + '"'  );


		listaResultados.append(';'.join(campos))

		saveFile('CARGADORES-Escaneado.csv', listaResultados)


		return;











listaProductos = []
listaDone = []
loadFile('Cargadores.csv', listaProductos)


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