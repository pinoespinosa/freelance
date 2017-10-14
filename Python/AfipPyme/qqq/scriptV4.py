import requests 				# pip install requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4

import urllib.request
from urllib.error import HTTPError
from urllib.error import URLError 

from datetime import datetime
from socket import timeout
from requests.exceptions import ConnectionError
import ssl
import json, errno, os, time, glob, sys, codecs


def editarArchivosExcel(path, key):
	try:
		with codecs.open(path, "r",encoding='iso-8859-1', errors='ignore') as f:
			webpage = f.read();

		webpage = webpage.split("<table")[1];
		xls_orig = BeautifulSoup(webpage, 'html.parser');
		xls_orig = xls_orig.find_all('tr');
		xls_modif =[];
		xls_modif.append("<table border=\"1\" cellpadding=\"3\" cellspacing=\"1\" bgcolor=\"#999999\">")

		pos=0;
		for fila in xls_orig:
			xls_modif.append('<tr class="negro">')
			pos=0;
			for text in fila.find_all('td'):
				if ( (pos > 8 and pos<23) or (pos>32 and pos<35)):
					xls_modif.append(str(text).replace("\"left\"></td>","\"left\">0</td>"));
				else:
					xls_modif.append(str(text));
				pos=pos+1;
			xls_modif.append('</tr>')

		xls_modif.append('</table><br></body> </html>')

		file = codecs.open(path, 'w', 'iso-8859-1')
		for pal in xls_modif:
			s = pal.strip()+'\n'
			file.write( s ); 
		file.close();
		return 'ok';
	except:
		print("Error trying to filter " + path);
		errores.append("Error trying to filter " + path);
		return '';

def loadFileDescargados():
	try:
		file = open("descargados.csv", "r") 
		for line in file:
			if ('Nombre Empresa') not in line:
				empresa = line.split(';')[1].strip()
				anio = line.split(';')[2].strip()
				mes = line.split(';')[3].strip()
				valores_previos[empresa +'_'+ anio +'_'+ mes] = line.strip()
		file.close();
	except (FileNotFoundError):
		print('');
	return

def loadFileConfiguration():
	try:
		file = open("config.txt", "r")
		config['folder1'] = file.readline().split("___")[1].strip()
		config['folder2'] = file.readline().split("___")[1].strip()
		file.close();
	except (FileNotFoundError):
		config['folder1'] = 'descargas'
		config['folder2'] = 'descargas2'
		if not os.path.exists(config['folder1']):
			os.makedirs(config['folder1'] )
		if not os.path.exists(config['folder2']):
			os.makedirs(config['folder2'] )
		print('No se ha hallado el archivo config.txt. Se tomarán los valores por defecto');
		
	if not os.path.exists(config['folder1']):
		print('No se ha hallado el directorio "' + config['folder1'] + '". Por favor modifique el archivo config.txt o cree el directorio')
		saveReporteFile();
		sys.exit()

	if not os.path.exists(config['folder2']):
		print('No se ha hallado el directorio "' + config['folder2'] + '". Por favor modifique el archivo config.txt o cree el directorio')
		saveReporteFile();
		sys.exit()
	return

def updateFile():
	file = open('descargados.csv',"w") 
	file.write('Nombre Empresa; Codigo Empresa; Año; Mes; Estado; Cierre; Carga; Fecha; Nombre del archivo; Fecha de descarga\n'); 	 
	for valor in valores_previos.keys():
		file.write(valores_previos[valor]+ '\n'); 	 
	file.close();
	return


def saveReporteFile():
	file = open(fechaReporte.strftime('%Y%B%d_%H-%M') + '.txt',"w") 
	file.write('Reporte del ' + fechaReporte.strftime('%d %B %Y %H:%M') + '\n\n'); 
	file.write('\n')
	file.write('Archivos Nuevos\n'); 
	for valor in archivos_nuevos:
		file.write('	'+valor + '\n'); 	
	file.write('\n')
	file.write('Archivos Actualizados\n'); 
	for valor in archivos_actualizados:
		file.write('	'+valor + '\n'); 
	file.write('\n')
	file.write('Errores\n'); 
	for valor in errores:
		file.write('	'+valor + '\n'); 

	file.close();
	return


def getURL( URL, key, estado):
	tries=0;
	html='';

	headers = { 'Host': 'www.se.gob.ar',
				'User-Agent': 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:54.0) Gecko/20100101 Firefox/54.0',
				'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
				'Accept-Language': 'en-US,en;q=0.5',
				'Accept-Encoding': 'gzip, deflate, br',
				'Connection': 'keep-alive',
				'Upgrade-Insecure-Requests': 1
	 }
	
	if not URL:
		return '';
	req = urllib.request.Request( URL, data=None, headers=headers );

	while tries<11:
		try:
			response = urllib.request.urlopen(req, timeout=360)
			html = response.read()
			break
		except KeyboardInterrupt:
			print('The user abort the script.')
			saveReporteFile();
			sys.exit()
		except:
			tries += 1
			if tries > 10:
				if 'SIN DATOS' in estado:
					print(key + ' ... SIN DATOS')
				else:
					print(key + ' ... failed')
					errores.append("Error trying to download " + key + 'xls from ' + URL)
	return html


def getPage( ):
	tries=0;
	headers = { 'Host': 'www.se.gob.ar',
				'User-Agent': 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:54.0) Gecko/20100101 Firefox/54.0',
				'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
				'Accept-Language': 'en-US,en;q=0.5',
				'Accept-Encoding': 'gzip, deflate, br',
				'Connection': 'keep-alive',
				'Upgrade-Insecure-Requests': 1
	 }

	post_data = { 'idempresa': 'ANAR', 'idmes': '%', 'idanio': '2017', 'submit': 'Ver'}
	pagina='';

	while tries<11:
		try:								
			pagina = requests.get(url='https://www.se.gob.ar/datosupstream/consulta_avanzada/listado.php', data=post_data, headers=headers).text;
			pagina = BeautifulSoup(pagina, 'html.parser');
			break
		except KeyboardInterrupt:
			print('The user abort the script.')
			saveReporteFile();
			sys.exit()
		except:
			tries += 1
			print('Error connecting with the server. Retring...')

	if (tries==11):
		print('The serching was canceled. Try again late.')
		saveReporteFile();
		sys.exit()

	return pagina


def getNombresDeEmpresa(pagina):
	try:
		pagina = pagina.find_all('select')[0];
	except (AttributeError, timeout, HTTPError, URLError, ConnectionError, ValueError):
		print('Error connecting with the server in getNombresDeEmpresa. Retring...')
	
	return pagina


def getAniosEmpresa(pagina):
	try:
		pagina = pagina.find_all('select')[2];
	except (AttributeError, timeout, HTTPError, URLError, ConnectionError, ValueError):
		print('Error connecting with the server in getAniosEmpresa. Retring...')
	
	return pagina


def getEmpresa( empresa , anio):
	tries=0;
	print('Searching ' + str(empresa) + ' ' + anio.text );

	headers = { 'Host': 'www.se.gob.ar',
				'User-Agent': 'Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:54.0) Gecko/20100101 Firefox/54.0',
				'Accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8',
				'Accept-Language': 'en-US,en;q=0.5',
				'Accept-Encoding': 'gzip, deflate, br',
				'Connection': 'keep-alive',
				'Upgrade-Insecure-Requests': 1
	}

	while tries<11:
		try:
			post_data2 = { 'idempresa': empresa, 'idmes': '%', 'idanio': anio, 'submit': 'Ver'}
			post_response = requests.post(url='https://www.se.gob.ar/datosupstream/consulta_avanzada/listado.php', data=post_data2, headers=headers)
			text=post_response.text;

			soup = BeautifulSoup(text, 'html.parser');

			for text in soup.find_all(class_='boxblanco'):
				for webFila in text.find_all('tr'):

					if 'Empresa' not in webFila.get_text():
						empresaNombre = webFila.find_all('td')[0].get_text();
						mes = 			webFila.find_all('td')[1].get_text();
						anio = 			webFila.find_all('td')[2].get_text();
						estado = 		webFila.find_all('td')[3].get_text();
						cierre = 		webFila.find_all('td')[4].get_text();
						carga = 		webFila.find_all('td')[5].get_text();
						fecha = 		webFila.find_all('td')[6].get_text();
		
						url = '';
						if  len(webFila.find_all('a')) > 0:
							try:
								url = 		webFila.find_all('a')[0]['href'];
							except:
								print ('Fallo la url ' + webFila);	
						
						key = 		empresa + '_' + anio + '_' + mes;
						if (key in valores_previos.keys() and valores_previos[key].split(';')[7]==fecha):
							print(key + ' ... without changes.');
						else:
							if key in valores_previos.keys():
								archivos_actualizados.append(key+'.xls')
							else:
								archivos_nuevos.append(key+'.xls')
							response = getURL(url, key, estado);
							if response.strip():
								print(key + ' ... downloaded.');

								file = open(config['folder1']+'/'+key+'.xls',"wb") 
								file.write(response); 	 
								file.close();

								carpeta = empresaNombre.replace(',', '').replace('.', '')

								if not os.path.exists(config['folder2']+'/'+ carpeta ):
									os.makedirs(config['folder2']+'/'+ carpeta )
								file = open(config['folder2']+'/'+ carpeta +'/'+key+'.xls',"wb") 
								file.write(response); 	 
								file.close();
								edicion = editarArchivosExcel(config['folder2']+'/'+ carpeta +'/'+key+'.xls',key);
								if (edicion):
									valores_previos[key]=empresaNombre +';'+ empresa +';'+ anio +';'+ mes +';'+ estado +';'+ cierre +';'+ carga +';'+ fecha +';'+ key+'.xls' +';' + datetime.now().strftime('%d %B %Y %H:%M');
									saveReporteFile();
									updateFile();
			tries=12;
		except KeyboardInterrupt:
			print('The user abort the script.')
			saveReporteFile();
			sys.exit()
		except:
			tries += 1
			print('Broke page. Could be an server error or a year without data. Empresa: ' + empresa + ' Año: ' + anio + '. Retring...' );
			print("Unexpected error:", sys.exc_info()[0]);
	if (tries==11):
		print('The serching was canceled. Try again late.')
		saveReporteFile();

						
#
#
# ********************************** Programa principal **********************************
#
#

valores_previos = {}
datos_consultados = [];
config = {}

archivos_nuevos = [];
archivos_actualizados = [];
errores = [];
fechaReporte = datetime.now();


loadFileDescargados();
loadFileConfiguration();

sitePagina = getPage();
listaEmpresas=getNombresDeEmpresa(sitePagina);		
listaAnio = getAniosEmpresa(sitePagina);

if (listaEmpresas):
	for empresa in listaEmpresas.find_all('option'):
		for anio in listaAnio.find_all('option'):
			if 'Seleccione una Empresa' not in empresa.get_text():
				getEmpresa(empresa['value'],anio);
