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

	pag = 1;
	cant = 1;
	while (cant>0):

#		print('Escaneado ... ' +url+ str(pag))

		cant = 0;
		payload = ""

		headers = {	'content-type': "application/x-www-form-urlencoded" }

		daatoss = descargarResultadoData(url+str(pag), 360, 10, payload, headers)
		pagina = daatoss.find_all(class_='item product product-item');

		datosCategoria = daatoss.find_all('ul', class_='items')[0].find_all('li');
		datosCategoria = '****' + datosCategoria[2].text.strip() + '___' + datosCategoria[3].text.strip() + '___' + datosCategoria[4].text.strip()

		for link in pagina:
			linkPagina = link.find_all('a')[0].prettify().strip().split('href="')[1].split('"')[0] + datosCategoria;

			if ( (linkPagina not in listaProductos) ):	
				cant = cant + 1;
				listaProductos.append(linkPagina);

		print('Productos encontrados:' + str(len(listaProductos)))
		pag = pag + 1
		saveFile('resultadosIndiv.csv', listaProductos)

#	print('Escaneado ... ' +url)

	cant = 0;
	payload = ""

	headers = {	'content-type': "application/x-www-form-urlencoded" }

	pagina = descargarResultadoData(url, 360, 10, payload, headers)
	pagina = pagina.find_all(class_='product-item');

	for link in pagina:
		linkPagina = link.find_all('a')[0].prettify().strip().split('href="')[1].split('"')[0];
		if (linkPagina not in listaProductos):	
			cant = cant + 1;
			listaProductos.append(linkPagina);

	print('Productos encontrados:' + str(len(listaProductos)))


	return;

def escanearProducto(url):

	print('Escaneando ' + url.split('****')[0]);

	cant = 1;
	payload = ""
	headers = {	'content-type': "application/x-www-form-urlencoded" }

	html = descargarResultadoDataSinBeautiful(url.split('****')[0], 360, 10, payload, headers)
	
	coloresReq = str(html).split('var colors = ')[1].split(';')[0].replace("'","").replace("\\","");
	tallesReq = str(html).split('var sizes = ')[2].split(';')[0].replace("'","").replace("\\","");

	pagina = BeautifulSoup(html, 'html.parser');






	
	codSKU = pagina.find_all(class_='product attribute sku')[0].text.replace('SKU','').strip()
	datosLocos = descargarResultadoDataSinBeautiful('http://www.priceshoes.com/services/search?action=getInventory&product='+codSKU.replace('ID-','')+'&sizes='+tallesReq+'=&colors='+coloresReq+'&prodId='+codSKU, 360, 10, payload, headers)
	datosLocos = BeautifulSoup(datosLocos, 'html.parser');

	ingg = datosLocos.find_all('tr');

	vallus = {};
	nombreses = ingg[0].find_all('td')
	pos = 0;
	for aaa in nombreses:
		vallus[pos]=aaa.text;
		pos = pos + 1;

	fasfasf = [];

	for aaa in ingg:
		bbb = aaa.find_all('td');
		lala = [];
		inde = 0;
		for mm in bbb:
			if (inde>0):
				lala.append('[' + vallus[inde] + ']' + mm.text)
			else:
				lala.append(mm.text)
			inde = inde + 1;
		fasfasf.append(' '.join(lala).replace('Fecha de Llegada:No Disponible',''))

	stockLococco = '';
	for ii in fasfasf:
		if not 'TIENDAS' in ii:
			stockLococco = stockLococco + ii + '\n';


	try:
		departamento = url.split('****')[1].split('___')[0];
	except:
		departamento = ''

	try:
		genero = url.split('****')[1].split('___')[1];
	except:
		genero = ''

	try:
		categoria = url.split('****')[1].split('___')[2];
	except:
		categoria = ''

	try:
		nombre = pagina.find_all(class_='base')[0].text.strip()
	except:
		nombre = ''

	try:
		foto = pagina.find_all(class_='fotorama__img')[0][src].text.strip()
	except:
		foto = ''


	try:
		descripcion = pagina.find_all(class_='descripcionCorta')[0].text.strip().replace('\n',' ').replace('\r',' ').rstrip().replace('g/m  2', 'g/m2 ').split('Tallas:')[0].replace('  ',' ').replace('  ',' ').replace('  ',' ').strip();
	except:
		descripcion = ''
	try:
		talla = str(html).split('"Talla Ropa","options":[')[1].split('"template":')[0];
		talla = talla.split('id');

		listaTall = [];
		for tt in talla:
			if (not ('products":[]' in tt) and ('"label":"' in tt) ):
				listaTall.append(tt.split('"label":"')[1].split('"')[0])
	except:
		listaTall = ['AGOTADO'];


	try:
		imagenes = pagina.find_all('embed')[0]['src']
	except:
		imagenes = ''

	listaResultados.append( '"' + codSKU + '";"' + departamento + '";"' + genero + '";"' + categoria + '";"' + url.split('****')[0]  + '";"' + nombre + '";"' + ','.join(listaTall) + '";"' + stockLococco + '";"' + descripcion + '";"' + foto + '";"' +'Link Ficha Tecnica' +  '";');

	return;


# Escanear las paginas para obtener los links a los productos
listaLinksIniciales = [
'http://www.priceshoes.com/productos/ropa/dama/pantalones?p='];

listaProductos = []
for pagina in listaLinksIniciales:
	escanearPagina(pagina);

listaResultados = []
listaResultados.append( 'Sku;Departamento;Género;Categoria;Url;Nombre;Fotos;Tallas;Notas;Colores;Ficha Tecnica');

for prod in listaProductos:
	escanearProducto(prod);
	saveFile('pino.csv',listaResultados);
