import sys
sys.path.append('../STANDAR_LIBRARIES')

from URL_Lib import descargarResultadoData, descargarResultadoDataSinBeautiful
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


def escanearPagina(url):

	pag = 2;
	cant = 1;
	while (cant>0):

		print('Escaneado ... ' +url+ str(pag))

		cant = 0;
		payload = ""

		headers = {	'content-type': "application/x-www-form-urlencoded" }

		pagina = descargarResultadoData(url+str(pag), 360, 10, payload, headers)
		pagina = pagina.find_all(class_='spanMobile');

		for link in pagina:
			linkPagina = link.prettify().strip().split('href="')[1].split('"')[0];

			if (linkPagina not in listaProductos):	
				cant = cant + 1;
				listaProductos.append(linkPagina);

		print('Productos encontrados:' + str(len(listaProductos)))
		pag = pag + 1
		saveFile('resultadosIndiv.csv', listaProductos)

	print('Escaneado ... ' +url)

	cant = 0;
	payload = ""

	headers = {	'content-type': "application/x-www-form-urlencoded" }

	pagina = descargarResultadoData(url, 360, 10, payload, headers)
	pagina = pagina.find_all(class_='spanMobile');

	for link in pagina:
		linkPagina = link.prettify().strip().split('href="')[1].split('"')[0];
		if (linkPagina not in listaProductos):	
			cant = cant + 1;
			listaProductos.append(linkPagina);

	print('Productos encontrados:' + str(len(listaProductos)))


	return;

def escanearProducto(url):

	cant = 1;
	payload = ""
	headers = {	'content-type': "application/x-www-form-urlencoded" }

	html = descargarResultadoDataSinBeautiful(url, 360, 10, payload, headers)
	pagina = BeautifulSoup(html, 'html.parser');


	codigoSKU = pagina.text.split('SKU')[1].split('\n')[1]
	sitio = url;
	nombre = pagina.text.split('Nombre')[1].split('\n')[1]
	
	try:
		descripcion = pagina.text.split('Descripción')[1].split('\n')[1]
	except:
		descripcion = ''

	try:
		tipo = pagina.text.split('Tipo')[1].split('\n')[1]
	except:
		tipo = ''

	try:	
		categoria = pagina.text.split('Categoría')[1].split('\n')[1]
	except:
		categoria = ''

	try:	
		material = pagina.text.split('Material')[1].split('\n')[1]
	except:
		material = ''

	try:
		precio = '$ ' + str(html).split('<b>Precio</b>')[1].split('</tr>')[0].split('$')[1].split('\\')[0].split('<')[0].rstrip()
	except:
		precio = ''

	try:
		color = str(html).split('<td style="text-align:left">')[2]
		color = color.split('\\')[0].split('<')[0].rstrip()

	except:
		color = ''



	imagenes = [];

	for aa in pagina.find_all(class_='thumbnail'):

		if ( len(aa.find_all(class_='anchorImages')) > 0):
			imagenes.append(aa.find_all(class_='anchorImages')[0].find_all('img')[0])
			

	try:
		imagen1 = imagenes[0]['src'];
	except:
		imagen1 = ''

	try:
		imagen2 = imagenes[1]['src'];
	except:
		imagen2 = ''

	try:
		imagen3 = imagenes[2]['src'];
	except:
		imagen3 = ''

	try:
		imagen4 = imagenes[3]['src'];
	except:
		imagen4 = ''

	try:
		imagen5 = imagenes[4]['src'];
	except:
		imagen5 = ''

	try:
		imagen6 = imagenes[5]['src'];
	except:
		imagen6 = ''

	try:
		imagen7 = imagenes[6]['src'];
	except:
		imagen7 = ''

	try:
		imagen8 = imagenes[7]['src'];
	except:
		imagen8 = ''

	try:
		imagen9 = imagenes[8]['src'];
	except:
		imagen9 = ''

	try:
		imagen10 = imagenes[9]['src'];
	except:
		imagen10 = ''


	listaResultados.append( '"' + codigoSKU + '";"' + sitio + '";"' + nombre + '";"' + descripcion + '";"' + tipo + '";"' + categoria + '";"' + material + '";"' + precio + '";"' + color  + '";"' + imagen1 + ',' + imagen2 + ',' + imagen3 + ',' + imagen4 + ',' + imagen5 + ',' + imagen6 + ',' + imagen7 + ',' + imagen8 + ',' + imagen9 + ',' + imagen10 + '";');

	return;




# Escanear las paginas para obtener los links a los productos
listaLinksIniciales = [
'https://www.jennyfer.com.mx/Tienda/Dama/Bolsas//pagina/',
'https://www.jennyfer.com.mx/Tienda/Dama/Accesorios//pagina/',
'https://www.jennyfer.com.mx/Tienda/Dama/Equipaje//pagina/',
'https://www.jennyfer.com.mx/Tienda/Dama/Bolsas%20de%20Piel//pagina/',
'https://www.jennyfer.com.mx/Tienda/Dama/Porta%20Laptop//pagina/',
'https://www.jennyfer.com.mx/Tienda/Dama/Loncheras//pagina/',
'https://www.jennyfer.com.mx/Tienda/Dama/Cangureras//pagina/',
'https://www.jennyfer.com.mx/Tienda/Dama/Liquidacion//pagina/',
'https://www.jennyfer.com.mx/Tienda/Dama/Velas//pagina/',
'https://www.jennyfer.com.mx/Tienda/Dama/Catalogos//pagina/'];

listaProductos = []
for pagina in listaLinksIniciales:
	escanearPagina(pagina);

listaResultados = []
listaResultados.append( 'codigoSKU;sitio;nombre;descripcion;tipo;categoria;material;precio;color;imagen1;imagen2;imagen3;imagen4;imagen5;imagen6;imagen7;imagen8;imagen9;imagen10');

for prod in listaProductos:
	try:
		escanearProducto('https://www.jennyfer.com.mx/'+prod);
		saveFile('pino.csv',listaResultados);
	except:
		print('fallo')