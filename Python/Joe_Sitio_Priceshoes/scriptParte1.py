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
	cant = 30;
	while (cant>29):

		print('Escaneado ... ' +url+ str(pag))

		cant = 0;
		payload = ""

		headers = {	'content-type': "application/x-www-form-urlencoded" }

		daatoss = descargarResultadoData(url+str(pag), 360, 10, payload, headers)
		pagina = daatoss.find_all(class_='item product product-item');

		datosCategoria = daatoss.find_all('ul', class_='items')[0].find_all('li');


		try:
			parte1= datosCategoria[2].text.strip()
		except:
			parte1 = ''

		try:
			parte2= datosCategoria[3].text.strip()
		except:
			parte2 = ''

		try:
			parte3= datosCategoria[4].text.strip()
		except:
			parte3 =''


		datosCategoria = '****' + parte1 + '___' + parte2 + '___' + parte3

		for link in pagina:
			linkPagina = link.find_all('a')[0].prettify().strip().split('href="')[1].split('"')[0] + datosCategoria;

			if ( (linkPagina not in listaProductos) ):	
				cant = cant + 1;
				listaProductos.append(linkPagina);

		print('Productos encontrados:' + str(len(listaProductos)))
		pag = pag + 1

		escaneados.append(url)
		saveFile('resultadosIndiv.csv', listaProductos)
		saveFile('escaneados.txt', escaneados)

	return;

def escanearProducto(url):

	print(url.split('****')[1].replace('___','/').strip() + ' --- > ' + url.split('****')[0].strip());

	cant = 1;
	payload = ""
	headers = {	'content-type': "application/x-www-form-urlencoded" }

	html = descargarResultadoDataSinBeautiful(url.split('****')[0], 360, 10, payload, headers)
	
	pagina = BeautifulSoup(html, 'html.parser');

	codSKU = pagina.find_all(class_='product attribute sku')[0].text.replace('SKU','').strip()

	try:
		coloresReq = str(html).split('var colors = ')[1].split(';')[0].replace("'","").replace("\\","");
		tallesReq = str(html).split('var sizes = ')[2].split(';')[0].replace("'","").replace("\\","");

		datosLocos = descargarResultadoDataSinBeautiful('http://www.priceshoes.com/services/search?action=getInventory&product='+codSKU.replace('ID-','')+'&sizes='+tallesReq+'=&colors='+coloresReq+'&prodId='+codSKU+'&color=', 360, 10, payload, headers)

	#	saveFile(codSKU+'pino.tt',[str(html)]);
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
		#	print(mm)


				if (inde>0):
					try:
						lala.append('[' + vallus[inde] + ']' + mm.text)
					except:
						pipipi = '';
				else:
					lala.append(mm.text)
				inde = inde + 1;

			fasfasf.append(' '.join(lala).replace('Fecha de Llegada:No Disponible',''))

		stockLococco = '';
		for ii in fasfasf:
			if not 'TIENDAS' in ii:
				stockLococco = stockLococco + ii + '\n';

	except:
		print('Fallo Tiendas')
		stockLococco = '';

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
		foto = str(html).split('"img":"')[1].split('"')[0].replace('\\','');
	except:
		foto = ''


	try:
		descripcion = pagina.find_all(class_='product attribute description')[0].text.strip()
	except:
		descripcion = ''


	try:
		descripcion2 = pagina.find_all(class_='data table additional-attributes')[0].text.replace('DESCRIPCIÓN DE PRODUCTO','').strip().replace('\n\n', '*****').replace('\n', ': ').replace('*****','\n').replace('\n: ','\n').split('\n')
		descripcion2.pop(0);
		descripcion2 = '\n'.join(descripcion2);
	except:
		descripcion2 = ''

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

	valorString = '"' + codSKU + '";"' + departamento + '";"' + genero + '";"' + categoria + '";"' + url.split('****')[0]  + '";"' + nombre + '";"' + ','.join(listaTall) + '";"' + stockLococco + '";"' + descripcion + '";"' + descripcion2 + '";"' + foto + '";';

	if ( (valorString not in listaResultados) ) :
		listaResultados.append(valorString);
	else:
		print('Repetido ... ' + codSKU)
	return;1


# Escanear las paginas para obtener los links a los productos
listaLinksIniciales = [
'http://www.priceshoes.com/productos/ropa/dama/abrigos?p=',
'http://www.priceshoes.com/productos/ropa/dama/chamarras?p=',
'http://www.priceshoes.com/productos/ropa/dama/sueteres?p=',
'http://www.priceshoes.com/productos/ropa/dama/ponchos-y-capas?p=',
'http://www.priceshoes.com/productos/ropa/dama/chalecos?p=',
'http://www.priceshoes.com/productos/ropa/dama/especiales/moda-curvy?p=',
'http://www.priceshoes.com/productos/ropa/dama/ejecutiva?p=',
'http://www.priceshoes.com/productos/ropa/dama/especiales/dama-contemporanea?p=',
'http://www.priceshoes.com/productos/ropa/dama/especiales/dama-clasica?p=',
'http://www.priceshoes.com/productos/ropa/dama/vestidos-y-faldas?p=',
'http://www.priceshoes.com/productos/ropa/dama/pantalones?p=',
'http://www.priceshoes.com/productos/ropa/dama/palazzos-y-jumpers?p=',
'http://www.priceshoes.com/productos/ropa/dama/blusas-playeras-y-tops?p=',
'http://www.priceshoes.com/productos/ropa/dama/playeras?p=',
'http://www.priceshoes.com/productos/ropa/dama/deportivo?p=',
'http://www.priceshoes.com/productos/ropa/dama/shorts-y-capris?p=',
'http://www.priceshoes.com/productos/ropa/dama/especiales/maternidad?p=',
'http://www.priceshoes.com/productos/ropa/dama/pijamas?p=',
'http://www.priceshoes.com/productos/ropa/dama/trajes-de-bano?p=',
'http://www.priceshoes.com/productos/ropa/dama/playa?p=',

'http://www.priceshoes.com/productos/ropa/caballero/abrigos?p=',
'http://www.priceshoes.com/productos/ropa/caballero/sacos-abrigos?p=',
'http://www.priceshoes.com/productos/ropa/caballero/playeras?p=',
'http://www.priceshoes.com/productos/ropa/caballero/sudaderas-pants?p=',
'http://www.priceshoes.com/productos/ropa/caballero/jeans-y-joggers?p=',
'http://www.priceshoes.com/productos/ropa/caballero/pantalones?p=',
'http://www.priceshoes.com/productos/ropa/caballero/pijamas?p=',
'http://www.priceshoes.com/productos/ropa/caballero/jerseys?p=',
'http://www.priceshoes.com/productos/ropa/caballero/bermudas-y-shorts?p=',
'http://www.priceshoes.com/productos/ropa/caballero/ropa-interior?p=',

'http://www.priceshoes.com/productos/ropa/nina/chamarras-y-abrigos?p=',
'http://www.priceshoes.com/productos/ropa/nina/sueteres-y-chalecos?p=',
'http://www.priceshoes.com/productos/ropa/nina/sacos?p=',
'http://www.priceshoes.com/productos/ropa/nina/ropa-deportiva?p=',
'http://www.priceshoes.com/productos/ropa/nina/playeras-y-blusas?p=',
'http://www.priceshoes.com/productos/ropa/nina/blusas-y-tops?p=',
'http://www.priceshoes.com/productos/ropa/nina/conjuntos?p=',
'http://www.priceshoes.com/productos/ropa/nina/vestidos-y-faldas?p=',
'http://www.priceshoes.com/productos/ropa/nina/pantalones-y-jeans?p=',
'http://www.priceshoes.com/productos/ropa/nina/palazzos-y-jumpers?p=',
'http://www.priceshoes.com/productos/ropa/nina/pescadores-capris?p=',
'http://www.priceshoes.com/productos/ropa/nina/trajes-de-bano?p=',
'http://www.priceshoes.com/productos/ropa/nina/playa?p=',
'http://www.priceshoes.com/productos/ropa/nina/ropa-interior?p=',
'http://www.priceshoes.com/productos/ropa/nina/pijamas-y-batas?p=',

'http://www.priceshoes.com/productos/ropa/nino/abrigos-y-chamarras?p=',
'http://www.priceshoes.com/productos/ropa/nino/sudaderas-y-pants?p=',
'http://www.priceshoes.com/productos/ropa/nino/sueteres-y-chalecos?p=',
'http://www.priceshoes.com/productos/ropa/nino/camisas-y-playeras?p=',
'http://www.priceshoes.com/productos/ropa/nino/playeras?p=',
'http://www.priceshoes.com/productos/ropa/nino/conjuntos?p=',
'http://www.priceshoes.com/productos/ropa/nino/jeans-y-pantalones?p=',
'http://www.priceshoes.com/productos/ropa/nino/shorts-bermudas?p=',
'http://www.priceshoes.com/productos/ropa/nino/playa?p=',
'http://www.priceshoes.com/productos/ropa/nino/ropa-interior?p=',
'http://www.priceshoes.com/productos/ropa/nino/pijamas-y-batas?p=',

'http://www.priceshoes.com/productos/ropa/bebes/chamarras-y-abrigos?p=',
'http://www.priceshoes.com/productos/ropa/bebes/sueteres-y-chalecos?p=',
'http://www.priceshoes.com/productos/ropa/bebes/sudaderas-y-pants?p=',
'http://www.priceshoes.com/productos/ropa/bebes/faldas-y-vestidos?p=',
'http://www.priceshoes.com/productos/ropa/bebes/pantalones?p=',
'http://www.priceshoes.com/productos/ropa/bebes/jumpers-y-palazzos?p=',
'http://www.priceshoes.com/productos/ropa/bebes/camisas-blusas-playeras?p=',
'http://www.priceshoes.com/productos/ropa/bebes/shorts-bermudas-y-pescadores?p=',
'http://www.priceshoes.com/productos/ropa/bebes/conjuntos-y-mamelucos?p=',

'http://www.priceshoes.com/catalogos/lenceria?p=',
'http://www.priceshoes.com/productos/ropa/lenceria/coordinados?p=',
'http://www.priceshoes.com/productos/ropa/lenceria/push-up?p=',
'http://www.priceshoes.com/productos/ropa/lenceria/fajas-y-modeladores?p=',
'http://www.priceshoes.com/productos/ropa/lenceria/basicos?p=',
'http://www.priceshoes.com/productos/ropa/lenceria/deportivos?p=',

'http://www.priceshoes.com/productos/marcas/paris-hilton/ropa/jeans-y-pantalones?product_list_order=newest&product_list_dir=desc&p=',
'http://www.priceshoes.com/productos/ropa/dama/jeans/leggings-y-jeggings?product_list_order=newest&product_list_dir=desc&p=',
'http://www.priceshoes.com/catalogos/coleccion-invierno-jeans?product_list_order=newest&product_list_dir=desc&p=',
'http://www.priceshoes.com/productos/ropa/dama/jeans/skinny?product_list_order=newest&product_list_dir=desc&p=',
'http://www.priceshoes.com/productos/ropa/dama/jeans/cintura-regular?product_list_order=newest&product_list_dir=desc&p=',
'http://www.priceshoes.com/productos/ropa/dama/jeans/seven-eleven?product_list_order=newest&product_list_dir=desc&p=',
'http://www.priceshoes.com/productos/ropa/dama/jeans/liza-y-atmosphere?product_list_order=newest&product_list_dir=desc&p=',
'http://www.priceshoes.com/productos/marcas/goodyear/ropa?product_list_order=newest&product_list_dir=desc&p=',

'http://www.priceshoes.com/productos/zapatos/dama/botas-y-botines?p=',
'http://www.priceshoes.com/productos/zapatos/dama/zapatillas?p=',
'http://www.priceshoes.com/productos/zapatos/dama/sandalias?p=',
'http://www.priceshoes.com/productos/zapatos/dama/tenis-casuales?p=',
'http://www.priceshoes.com/productos/zapatos/dama/tenis-deportivos?p=',
'http://www.priceshoes.com/productos/zapatos/dama/todo-terreno?p=',
'http://www.priceshoes.com/productos/zapatos/dama/pantuflas?p=',
'http://www.priceshoes.com/productos/zapatos/caballero/botas?p=',
'http://www.priceshoes.com/productos/zapatos/caballero/zapatos-casuales?p=',
'http://www.priceshoes.com/productos/zapatos/caballero/zapatos-de-vestir?p=',
'http://www.priceshoes.com/productos/zapatos/caballero/mocasines?p=',
'http://www.priceshoes.com/productos/zapatos/caballero/sandalias?p=',
'http://www.priceshoes.com/productos/zapatos/caballero/confort?p=',
'http://www.priceshoes.com/productos/zapatos/caballero/tenis-casuales?p=',
'http://www.priceshoes.com/productos/zapatos/caballero/tenis-deportivos?p=',
'http://www.priceshoes.com/productos/zapatos/caballero/pantuflas?p=',
'http://www.priceshoes.com/productos/zapatos/nina/botas-y-botines?p=',
'http://www.priceshoes.com/productos/zapatos/nina/sandalias?p=',
'http://www.priceshoes.com/productos/zapatos/nina/flats-y-balerinas?p=',
'http://www.priceshoes.com/productos/zapatos/nina/mocasines?p=',
'http://www.priceshoes.com/productos/zapatos/nina/escolares?p=',
'http://www.priceshoes.com/productos/zapatos/nina/tenis-casual?p=',
'http://www.priceshoes.com/productos/zapatos/nina/tenis-deportivos?p=',
'http://www.priceshoes.com/productos/zapatos/nina/todo-terreno?p=',
'http://www.priceshoes.com/productos/zapatos/nina/pantuflas?p=',
'http://www.priceshoes.com/productos/zapatos/nino/botas-y-botines?p=',
'http://www.priceshoes.com/productos/zapatos/nino/zapatos-casuales?p=',
'http://www.priceshoes.com/productos/zapatos/nino/choclos?p=',
'http://www.priceshoes.com/productos/zapatos/nino/escolares?p=',
'http://www.priceshoes.com/productos/zapatos/nino/mocasines?p=',
'http://www.priceshoes.com/productos/zapatos/nino/tenis-casuales?p=',
'http://www.priceshoes.com/productos/zapatos/nino/tenis-deportivos?p=',
'http://www.priceshoes.com/productos/zapatos/nino/todo-terreno?p=',
'http://www.priceshoes.com/productos/zapatos/nino/sandalias?p=',
'http://www.priceshoes.com/productos/zapatos/nino/pantuflas?p=',
'http://www.priceshoes.com/productos/zapatos/bebes?p=',

'http://www.priceshoes.com/productos/deportes/running/calzado?p=',
'http://www.priceshoes.com/productos/deportes/running/ropa?p=',
'http://www.priceshoes.com/productos/deportes/running/accesorios?p=',
'http://www.priceshoes.com/productos/deportes/training/calzado?p=',
'http://www.priceshoes.com/productos/deportes/training/ropa?p=',
'http://www.priceshoes.com/productos/deportes/training/mochilas-gorras-y-bolsas?p=',
'http://www.priceshoes.com/productos/deportes/motorsport/calzado?p=',
'http://www.priceshoes.com/productos/deportes/motorsport/ducati?p=',
'http://www.priceshoes.com/productos/deportes/street-style/pantunflas?p=',
'http://www.priceshoes.com/productos/deportes/street-style/sandalias?p=',
'http://www.priceshoes.com/productos/deportes/street-style/calzado?p=',
'http://www.priceshoes.com/productos/deportes/skate/calzado?p=',
'http://www.priceshoes.com/productos/deportes/skate/ropa?p=',
'http://www.priceshoes.com/productos/deportes/skate/bolsas-mochilas-y-gorras?p=',
'http://www.priceshoes.com/productos/deportes/futbol/guantes-espinilleras-y-balones?p=',
'http://www.priceshoes.com/productos/deportes/futbol/jersey-mexico?p=',
'http://www.priceshoes.com/productos/deportes/futbol/ropa?p=',
'http://www.priceshoes.com/productos/deportes/futbol/calzado?p=',
'http://www.priceshoes.com/productos/deportes/basquetbol/tenis?p=',
'http://www.priceshoes.com/productos/deportes/basquetbol/accesorios?p=',
'http://www.priceshoes.com/productos/deportes/otras-disciplinas/all-terrain?p=',
'http://www.priceshoes.com/productos/deportes/otras-disciplinas/swimming?p=',
'http://www.priceshoes.com/productos/deportes/otras-disciplinas/botas?p=',
'http://www.priceshoes.com/productos/deportes/gorras?p=',
'http://www.priceshoes.com/productos/deportes/kids?p='

];

listaLinksIniciales2 = [
'http://www.priceshoes.com/productos/ropa/dama/jeans/leggings-y-jeggings?product_list_order=newest&product_list_dir=desc&p=',
'http://www.priceshoes.com/productos/ropa/dama/abrigos?p=',
'http://www.priceshoes.com/productos/deportes/running/ropa?p=',
'http://www.priceshoes.com/productos/deportes/running/accesorios?p=',
'http://www.priceshoes.com/productos/ropa/lenceria/coordinados?p='
 ]



listaProductos = []
escaneados = []

loadFile("escaneados.txt", escaneados)
loadFile("resultadosIndiv.csv", listaProductos)


print(escaneados)

for pagina in listaLinksIniciales:
	print(pagina)
	if (not (pagina in escaneados)):
		escanearPagina(pagina);

listaResultados = []
loadFile("pino.csv", listaResultados)
listaResultados.append( 'Sku;Departamento;Género;Categoria;Url;Nombre;Tallas;Existencias;Detalle;Descripcion;Imagenes');

for prod in listaProductos:
	escanearProducto(prod);
	saveFile('pino.csv',listaResultados);