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


def descargarCategoriaEspecifica():

	pag = 1;
	cant = 1;
	
	while (cant>0):
		cant = 0;
		payload = "lang=E&pagina="+str(pag)+"&arqSOC=arq"

		headers = {	'content-type': "application/x-www-form-urlencoded" }

		pagina = descargarResultadoData('https://www.arquitectes.cat/iframes/arquitectes/arquitectes/llistat.php', 360, 10, payload, headers)
		pagina = pagina.find_all(class_='item_list');

		for link in pagina:
			cant = cant + 1
			lista.append(link.prettify().strip().split("codi=")[1].split("&amp")[0]);

		print(pag)
		pag = pag + 1
		saveFile('resultadosIndiv.csv', lista)
	return;


# -------------  PASO 1 - Categorias ---------------

#categoryLinks = [];
#loadFile('categoryLinks.txt', categoryLinks);
#if (categoryLinks):
#	print('Se toman los datos de categoryLinks.txt');
#else:
#	descargarCategorias();


# -------------  PASO 2 - Empresas ---------------

#categoryLinksEscaneados = [];

#resultados = [];
#loadFile('resultados.csv', resultados);
#for cat in categoryLinks:
#	descargarCategoriaEspecifica(cat.strip())
#	categoryLinksEscaneados.append(cat.strip())
#	saveFileExc('categoryLinks.txt', categoryLinks, categoryLinksEscaneados)




lista = [];
#loadFile('numero.csv', lista);

#finales = [];
#for link in lista:
descargarCategorias()
saveFile('pino.txt',lista);