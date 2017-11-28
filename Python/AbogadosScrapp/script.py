from URL_Lib import descargarResultadoData
from File_Lib import saveFile, saveFileExc, loadFile
import re
import requests
from bs4 import BeautifulSoup 	# pip install beautifulsoup4

import http.client
#
#
# ********************************** Programa principal **********************************
#
#


def descargarCategorias(lista):

	url1 = "http://www.coaib.org/es/directorio/colegiados--sociedades/arquitectos-ejercientes.aspx?page="
	url2 = "&tipoBusqueda=1&nombre=a&demarcacion=0&municipio=0"

	elementos = list(range(125));

	for aa in elementos:
		response = requests.request("GET", url1 + str(aa) + url2)
		descargarCategoriaEspecifica(response.text, lista)
		print (url1 + str(aa) + url2)
		saveFile('pino.txt',lista);

	return;


def descargarCategoriaEspecifica(response, lista):

	valores = response.split('<td class="table-cell">');


	for aa in valores:
		if ('El arquitecto NO AUTORIZA el uso de sus datos personales con fines de publicidad' not in aa ):
				
			dato = aa.split('</td>')[0];
			acumulado = ""

			datitos = dato.split('<br />');

			try:
				nombre = '"'+ " ".join(datitos[0].strip().split()).split("- Num.")[0].replace('(X) ','') + '",';
			except:
				nombre='';

			try:
				matricula = '"Num.'+ " ".join(datitos[0].strip().split()).split("- Num.")[1] + '",' ;
			except:
				matricula=',';


			try:
				habi = '"'+ " ".join(datitos[1].strip().split()) + '",';
			except:
				habi=',';

			try:
				dire1 = '"'+ " ".join(datitos[2].strip().split()) + '",';
			except:
				dire1=',';

			try:
				dire2 = '"'+ " ".join(datitos[3].strip().split()) + '",';
			except:
				dire2=',';


			try:
				tel = '"'+ " ".join(datitos[4].strip().split()) + '",';
				if ('fono' not in tel):
					tel=','
			except:
				tel=',';

			try:
				movil = '"'+ " ".join(datitos[4].strip().split()) + '",';
				
				if ('vil' not in movil):
					movil = '"'+ " ".join(datitos[5].strip().split()) + '",';

				if ('vil' not in movil):
					movil = ',';

			except:
				movil=',';


			try:
				fax = '"'+ " ".join(datitos[4].strip().split()) + '",';
				
				if ('Fax' not in fax):
					fax = '"'+ " ".join(datitos[5].strip().split()) + '",';

				if ('Fax' not in fax):
					fax = '"'+ " ".join(datitos[6].strip().split()) + '",';

				if ('Fax' not in fax):
					fax = ',';

			except:
				fax=',';


			try:
				email = '"'+ " ".join(datitos[4].strip().split()) + '",';
				
				if ('Email' not in email):
					email = '"'+ " ".join(datitos[5].strip().split()) + '",';

				if ('Email' not in email):
					email = '"'+ " ".join(datitos[6].strip().split()) + '",';
			
				if ('Email' not in email):
					email = '"'+ " ".join(datitos[7].strip().split()) + '",';

				if ('Email' not in email):
					email = ',';
				else:
					email1 = email.split('data-domain="')[1].split('"')[0]
					email2 = email.split('">')[1].split('<')[0]
					email3 = email.split('data-sufix="')[1].split('"')[0]
					email = email2 + '@' + email1 + '.' + email3;



			except:
				email=',';

			lista.append(nombre + matricula + habi + dire1 + dire2 + tel + movil + fax + email)
		
	return;






lista = [];

descargarCategorias(lista)
saveFile('pino.txt',lista);