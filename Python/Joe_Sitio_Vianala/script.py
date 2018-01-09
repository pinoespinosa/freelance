import sys
sys.path.append('../STANDAR_LIBRARIES')

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


def escanearPagina(url):

	pag = 1;
	cant = 1;
	while (cant>0):

		print('Escaneado ... ' +url+ str(pag))

		cant = 0;
		payload = ""

		headers = {	'content-type': "application/x-www-form-urlencoded" }

		pagina = descargarResultadoData(url+str(pag), 360, 10, payload, headers)
		pagina = pagina.find_all(class_='type-product');

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
	pagina = pagina.find_all(class_='type-product');

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

	pagina = descargarResultadoData(url, 360, 10, payload, headers)
	


	codigoSKU = pagina.find_all(class_='sku')[0].text;
	titulo = pagina.find_all(class_='entry-title')[0].text;
	notas = pagina.find_all(class_='entry-summary')[0].text.strip();

	if ('cotizador' in notas):
		notas = notas.split("cotizador")[1].strip();

	if ('Agotado' in notas):
		notas = notas.split("Agotado")[1].strip();

	if ('SKU' in notas):
		notas = notas.split("SKU")[0].strip();

	if ('Contamos' in notas):
		notas = notas.split("Contamos")[0].strip();

	if ('En Vianala' in notas):
		notas = notas.split("En Vianala")[0].strip();
	
	if ('VIANALA ES UNA F' in notas):
		notas = notas.split("VIANALA ES UNA F")[0].strip();
	
	if ('Nuestra marca:' in notas):
		notas = notas.split("Nuestra marca:")[0].strip();
	
	if ('¿Quieres saber más sobre Vianala' in notas):
		notas = notas.split("¿Quieres saber más sobre Vianala")[0].strip();

	if ('Vianala es una empresa familiar' in notas):
		notas = notas.split("Vianala es una empresa familiar")[0].strip();

	if ('Síguenos en nuestras redes sociales' in notas):
		notas = notas.split("Síguenos en nuestras redes sociales")[0].strip();

	if ('Todas las bolsas y carteras Vianala' in notas):
		notas = notas.split("Todas las bolsas y carteras Vianala")[0].strip();

	if ('Los productos Vianala' in notas):
		notas = notas.split("Los productos Vianala")[0].strip();

	if ('Vianala es una fábrica en León' in notas):
		notas = notas.split("Vianala es una fábrica en León")[0].strip();

	if ('León, Gto.' in notas):
		notas = notas.split("León, Gto.")[0].strip();

	if ('En la fábrica' in notas):
		notas = notas.split("En la fábrica")[0].strip();

	if ('Convierte en distribuidor de artículos' in notas):
		notas = notas.split("Convierte en distribuidor de artículos")[0].strip();
		
	if ('Para surtir tus pedidos de mayoreo' in notas):
		notas = notas.split("Para surtir tus pedidos de mayoreo")[0].strip();


	imagenes = [];

	for aa in pagina.find_all(class_='attachment-shop_single'):
		if (aa['data-large_image'] not in imagenes):
			imagenes.append(aa['data-large_image'])



	try:
		imagen1 = imagenes[0];
	except:
		imagen1 = ''

	try:
		imagen2 = imagenes[1];
	except:
		imagen2 = ''

	try:
		imagen3 = imagenes[2];
	except:
		imagen3 = ''

	try:
		imagen4 = imagenes[3];
	except:
		imagen4 = ''

	try:
		imagen5 = imagenes[4];
	except:
		imagen5 = ''

	try:
		imagen6 = imagenes[5];
	except:
		imagen6 = ''

	try:
		imagen7 = imagenes[6];
	except:
		imagen7 = ''

	try:
		imagen8 = imagenes[7];
	except:
		imagen8 = ''

	try:
		imagen9 = imagenes[8];
	except:
		imagen9 = ''

	try:
		imagen10 = imagenes[9];
	except:
		imagen10 = ''

	print(titulo);


	listaResultados.append( codigoSKU + ";" + titulo + ';'+ url + ';"'+ notas + '";' + imagen1 + ";" + imagen2 + ";" + imagen3 + ";" + imagen4 + ";" + imagen5 + ";" + imagen6 + ";" + imagen7 + ";" + imagen8 + ";" + imagen9 + ";" + imagen10);

	return;




# Escanear las paginas para obtener los links a los productos
listaLinksIniciales = [
'http://www.vianala.com/page/', 
'http://www.vianala.com/catalogo/bolsas/tienda-online-bolsos-en-mexico/page/',
'http://www.vianala.com/catalogo/bolsas/comprar-bolsos-por-internet/',
'http://www.vianala.com/catalogo/bolsas/compra-de-bolsos-online/page/',
'http://www.vianala.com/catalogo/bolsas/venta-bolsos/page/',
'http://www.vianala.com/catalogo/bolsas/bolsos-tipo-mochila-piel/',
'http://www.vianala.com/catalogo/bolsas/tienda-online-de-bolsos/page/',
'http://www.vianala.com/catalogo/bolsas/bolsos-venta-online/',
'http://www.vianala.com/catalogo/bolsas/crossbody/',
'http://www.vianala.com/catalogo/bolsas/bolsas-con-bordados-chiapanecos/',
'http://www.vianala.com/catalogo/carteras/page/',
'http://www.vianala.com/catalogo/ofertas/',
'http://www.vianala.com/catalogo/regalos-corporativos-empresariales/page/',
'http://www.vianala.com/catalogo/portafolios-de-piel/',
'http://www.vianala.com/catalogo/carteras/cartera-para-hombre/',
'http://www.vianala.com/catalogo/mensajeras/bolsos-tipo-bandolera-para-caballero/',
'http://www.vianala.com/catalogo/regalos-corporativos-empresariales/tarjeteros-de-piel/',
'http://www.vianala.com/catalogo/regalos-corporativos-empresariales/articulos-piel-viaje/portacorbatas-de-piel/'];

listaLinksIniciales2 = [
'http://www.vianala.com/catalogo/bolsas/crossbody/'];

listaProductos = []
for pagina in listaLinksIniciales:
	escanearPagina(pagina);

listaResultados = []
for prod in listaProductos:
	escanearProducto(prod);
	saveFile('pino.csv',listaResultados);