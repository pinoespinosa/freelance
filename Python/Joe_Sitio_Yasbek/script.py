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

		pagina = descargarResultadoData(url+str(pag), 360, 10, payload, headers)
		pagina = pagina.find_all(class_='col-c')[0].find_all('a');

		for link in pagina:
			linkPagina = link.prettify().strip().split('href="')[1].split('"')[0];

			if ( (linkPagina not in listaProductos) and ('www.yazbek.com.mx/item/' in linkPagina) ):	
				cant = cant + 1;
				listaProductos.append(linkPagina);
				print(linkPagina);

#		print('Productos encontrados:' + str(len(listaProductos)))
		pag = pag + 1
		saveFile('resultadosIndiv.csv', listaProductos)

#	print('Escaneado ... ' +url)

	cant = 0;
	payload = ""

	headers = {	'content-type': "application/x-www-form-urlencoded" }

	pagina = descargarResultadoData(url, 360, 10, payload, headers)
	pagina = pagina.find_all(class_='col-c')[0].find_all('a');

	for link in pagina:
		linkPagina = link.prettify().strip().split('href="')[1].split('"')[0];
		if (linkPagina not in listaProductos and 'www.yazbek.com.mx/item/' in linkPagina):	
			cant = cant + 1;
			listaProductos.append(linkPagina);

#	print('Productos encontrados:' + str(len(listaProductos)))


	return;

def escanearProducto(url):

	cant = 1;
	payload = ""
	headers = {	'content-type': "application/x-www-form-urlencoded" }

	html = descargarResultadoDataSinBeautiful(url, 360, 10, payload, headers)
	pagina = BeautifulSoup(html, 'html.parser');

	try:
		codSKU = pagina.find_all(class_='col-2')[0].find_all('h2')[0].text.strip()
	except:
		codSKU = ''


	try:
		categoria = pagina.find_all(class_='col-2')[0].find_all('h1')[0].text.strip()
	except:
		categoria = ''

	try:
		nombre = pagina.find_all(class_='descripcionLarga')[0].text.strip()
	except:
		nombre = ''

	try:
		descripcion = pagina.find_all(class_='descripcionCorta')[0].text.strip().replace('\n',' ').replace('\r',' ').rstrip().replace('g/m  2', 'g/m2 ').split('Tallas:')[0].replace('  ',' ').replace('  ',' ').replace('  ',' ').strip();
	except:
		descripcion = ''

	try:
		talla = pagina.find_all(class_='wrapptabmedida')[0].text.strip()
	except:
		talla = ''

	try:
		imagenes = pagina.find_all('embed')[0]['src']
	except:
		imagenes = ''

	tallaReal = []

	if 'CH' in talla:
		tallaReal.append('CH');

	if 'M' in talla:
		tallaReal.append('M');

	if 'G' in talla:
		tallaReal.append('G');

	if 'EG' in talla:
		tallaReal.append('EG');

	if 'EGG' in talla:
		tallaReal.append('EEG');

	listaCOLORRR = [];
	for uu in colores.keys():
		if (codSKU in colores[uu]):
			listaCOLORRR.append(uu);

	listaResultados.append( '"' + codSKU + '";"' + categoria + '";"' + url  + '";"' + nombre + '";"' + imagenes + '";"' + ', '.join(tallaReal) + '";"' + descripcion + '";"' + ', '.join(listaCOLORRR) + '";"' + 'Link Ficha Tecnica' +  '";');

	return;

colores = {}
colores['Arena'] = ' Playera cuello redondo B0300 Playera cuello redondo C0300 '
colores['Azul Claro'] = ' Playera cuello redondo B0300 Playera cuello redondo C0300 Top cuello redondo D0300 Playera tipo polo piqué J0500 '
colores['Beige'] = ' Playera cuello redondo C0300 Playera tipo polo piqué C0500 Camisa gabardina manga corta C0606 Camisa gabardina manga larga C0607 Playera tipo polo pique D0500 Camisa gabardina manga corta D0606 Camisa gabardina manga larga D0607 '
colores['Blanco'] = '  Playera cuello redondo B0300 Playera cuello redondo C0300 Playera manga larga C0304 Playera cuello V C0306 Playera sin mangas C0310 Playera tipo polo piqué C0500 Camisa oxford mil rayas manga corta C0602 Camisa oxford mil rayas manga larga C0603 Camisa oxford manga corta C0604 Camisa oxford manga larga C0605 Camisa gabardina manga corta C0606 Camisa gabardina manga larga C0607 Sudadera cuello redondo C0700 Sudadera con capucha y cangurera C0701 Sudadera con capucha y cierre C0702 Top cuello redondo D0300 Playera cuello V D0306 Playera tipo polo pique D0500 Camisa oxford mil rayas manga corta para dama D0602 Camisa oxford mil rayas manga larga para dama D0603 Camisa gabardina manga corta D0606 Camisa gabardina manga larga D0607 Playera cuello redondo J0300 Playera tipo polo piqué J0500 Sudadera juvenil cuello redondo J0700 Sudadera con capucha y cangurera unisex para jóvenes. Peso*: 285 g/m 2 *Peso en color oscuro Tejido: Felpa 50% algodón 50% poliéster natural. Tallas: XCH, CH, M, G, XG ¡Llama YA! 01.800.2.929235 ------------------------------------- Conoce nuestras sucursales: Tijuana, La Paz, Torreón, Acapulco, México D.F., Morelia, Cancún, Hermosillo, San Luis Potosí, Veracruz, Mérida J0701 Sudadera con capucha y cierre unisex para jóvenes. Peso*: 285 g/m 2 *Peso en color oscuro Tejido: Felpa 50% algodón 50% poliéster natural. Tallas: XCH, CH, M, G, XG ¡Llama YA! 01.800.2.929235 ------------------------------------- Conoce nuestras sucursales: Tijuana, La Paz, Torreón, Acapulco, México D.F., Morelia, Cancún, Hermosillo, San Luis Potosí, Veracruz, Mérida J0702 Playera cuello redondo N0300 '
colores['Blanco/ Cielo'] = ' Camisa oxford mil rayas manga corta C0602 Camisa oxford mil rayas manga larga C0603 Camisa oxford mil rayas manga corta para dama D0602 Camisa oxford mil rayas manga larga para dama D0603 '
colores['Blanco/ Gris'] = '  Camisa oxford mil rayas manga corta C0602 Camisa oxford mil rayas manga larga C0603 Camisa oxford mil rayas manga corta para dama D0602 Camisa oxford mil rayas manga larga para dama D0603  '
colores['Blanco/ Jade'] = '  '
colores['Blanco/ Marino'] = '  '
colores['Blanco/ Negro'] = '  '
colores['Blanco/ Rojo'] = ' Camisa oxford mil rayas manga corta C0602 Camisa oxford mil rayas manga larga C0603 Camisa oxford mil rayas manga corta para dama D0602 Camisa oxford mil rayas manga larga para dama D0603 '
colores['Canario'] = ' Playera cuello redondo B0300 Playera cuello redondo C0300 Top cuello redondo D0300 Playera cuello redondo J0300 Playera tipo polo piqué J0500 Playera cuello redondo N0300 '
colores['Carbon'] = ' Playera cuello redondo C0300 Playera manga larga con capucha y cangurera C0314 Playera manga larga con capucha cangurera y cierre C0324 Playera cuello redondo J0300 Playera cuello redondo N0300 '
colores['Celeste'] = '  Playera cuello redondo B0300 Playera cuello redondo C0300 Playera tipo polo piqué C0500 Playera tipo polo pique D0500 Playera cuello redondo J0300 Playera cuello redondo N0300 '

colores['Chocolate'] = ' Playera cuello redondo C0300 Playera manga larga C0304 Playera tipo polo piqué C0500 Top cuello redondo D0300 Playera tipo polo pique D0500 Playera cuello redondo J0300 Playera cuello redondo N0300 '
colores['Cielo'] = 'Camisa oxford mil rayas manga corta C0602 Camisa oxford mil rayas manga larga C0603 Camisa oxford manga corta C0604 Camisa oxford manga larga C0605 Camisa oxford mil rayas manga corta para dama D0602 Camisa oxford mil rayas manga larga para dama D0603 '
colores['Delfin'] = 'Playera cuello redondo C0300'
colores['Fiusha'] = ' Playera cuello redondo B0300 Playera cuello redondo C0300 Top cuello redondo D0300 '
colores['Indigo Claro'] = ' Camisa mezclilla manga corta C0600 Camisa mezclilla manga larga C0601 Pantalón mezclilla C0651 Camisa mezclilla manga corta dama D0600 Camisa mezclilla manga larga dama D0601 '
colores['Jade'] = ' Playera cuello redondo C0300 Playera manga larga C0304 Playera sin mangas C0310 Playera cuello redondo J0300 Playera tipo polo piqué J0500 Playera cuello redondo N0300 '
colores['Jade Osc/Marino'] = ''
colores['Jade Oscuro'] = '  Playera cuello redondo C0300 Playera manga larga C0304 Playera sin mangas C0310 Playera tipo polo piqué C0500 Sudadera cuello redondo C0700 Sudadera con capucha y cangurera C0701 Sudadera con capucha y cierre C0702 Playera tipo polo pique D0500 Playera cuello redondo J0300 Playera tipo polo piqué J0500 Sudadera juvenil cuello redondo J0700 Sudadera con capucha y cangurera unisex para jóvenes. Peso*: 285 g/m 2 *Peso en color oscuro Tejido: Felpa 50% algodón 50% poliéster natural. Tallas: XCH, CH, M, G, XG ¡Llama YA! 01.800.2.929235 ------------------------------------- Conoce nuestras sucursales: Tijuana, La Paz, Torreón, Acapulco, México D.F., Morelia, Cancún, Hermosillo, San Luis Potosí, Veracruz, Mérida J0701 Sudadera con capucha y cierre unisex para jóvenes. Peso*: 285 g/m 2 *Peso en color oscuro Tejido: Felpa 50% algodón 50% poliéster natural. Tallas: XCH, CH, M, G, XG ¡Llama YA! 01.800.2.929235 ------------------------------------- Conoce nuestras sucursales: Tijuana, La Paz, Torreón, Acapulco, México D.F., Morelia, Cancún, Hermosillo, San Luis Potosí, Veracruz, Mérida J0702 Playera cuello redondo N0300  '
colores['Khaki'] = '  Pantalón gabardina C0650 Pantalón de Gabardina para dama D0650  '
colores['Ladrillo'] = ' Playera cuello redondo C0300 Playera cuello redondo J0300 Playera cuello redondo N0300  '

colores['Lima'] = ' Playera cuello redondo B0300 Playera cuello redondo C0300 Playera cuello redondo J0300 Playera cuello redondo N0300 '
colores['Mango'] = '  Playera cuello redondo C0300 Playera manga larga C0304 Playera sin mangas C0310 Playera tipo polo piqué C0500 Playera tipo polo pique D0500 Playera cuello redondo J0300 Playera cuello redondo N0300  '
colores['Marino'] = '  Playera cuello redondo B0300 Playera cuello redondo C0300 Playera manga larga C0304 Playera cuello V C0306 Playera sin mangas C0310 Playera manga larga con capucha y cangurera C0314 Playera manga larga con capucha cangurera y cierre C0324 Playera tipo polo piqué C0500 Camisa gabardina manga corta C0606 Camisa gabardina manga larga C0607 Pantalón gabardina C0650 Sudadera cuello redondo C0700 Sudadera con capucha y cangurera C0701 Sudadera con capucha y cierre C0702 Top cuello redondo D0300 Playera cuello V D0306 Playera tipo polo pique D0500 Camisa gabardina manga corta D0606 Camisa gabardina manga larga D0607 Pantalón de Gabardina para dama D0650 Playera cuello redondo J0300 Playera tipo polo piqué J0500 Sudadera juvenil cuello redondo J0700 Sudadera con capucha y cangurera unisex para jóvenes. Peso*: 285 g/m 2 *Peso en color oscuro Tejido: Felpa 50% algodón 50% poliéster natural. Tallas: XCH, CH, M, G, XG ¡Llama YA! 01.800.2.929235 ------------------------------------- Conoce nuestras sucursales: Tijuana, La Paz, Torreón, Acapulco, México D.F., Morelia, Cancún, Hermosillo, San Luis Potosí, Veracruz, Mérida J0701 Sudadera con capucha y cierre unisex para jóvenes. Peso*: 285 g/m 2 *Peso en color oscuro Tejido: Felpa 50% algodón 50% poliéster natural. Tallas: XCH, CH, M, G, XG ¡Llama YA! 01.800.2.929235 ------------------------------------- Conoce nuestras sucursales: Tijuana, La Paz, Torreón, Acapulco, México D.F., Morelia, Cancún, Hermosillo, San Luis Potosí, Veracruz, Mérida J0702 Playera cuello redondo N0300 '
colores['Marrón'] = ' Playera cuello redondo jaspe C0250 Playera cuello redondo C0300 Playera tipo polo piqué C0500 Playera cuello redondo jaspe dama D0250 Playera tipo polo pique D0500 '
colores['Moka'] = 'Playera cuello redondo C0300'
colores['Morado'] = ' Playera cuello redondo jaspe C0250 Playera cuello redondo C0300 Playera cuello redondo jaspe dama D0250 Top cuello redondo D0300 Playera cuello redondo J0300 Playera tipo polo piqué J0500 Playera cuello redondo N0300 '
colores['Mostaza'] = 'Playera cuello redondo C0300'
colores['Naranja'] = ' Playera cuello redondo B0300 Playera cuello redondo jaspe C0250 Playera cuello redondo C0300 Playera sin mangas C0310 Playera tipo polo piqué C0500 Playera cuello redondo jaspe dama D0250 Playera tipo polo pique D0500 Playera cuello redondo J0300 Playera tipo polo piqué J0500 Playera cuello redondo N0300 '
colores['Negro'] = '  Playera cuello redondo jaspe C0250 Playera cuello redondo C0300 Playera manga larga C0304 Playera cuello V C0306 Playera sin mangas C0310 Playera manga larga con capucha y cangurera C0314 Playera manga larga con capucha cangurera y cierre C0324 Playera tipo polo piqué C0500 Camisa gabardina manga corta C0606 Camisa gabardina manga larga C0607 Pantalón gabardina C0650 Sudadera cuello redondo C0700 Sudadera con capucha y cangurera C0701 Sudadera con capucha y cierre C0702 Playera cuello redondo jaspe dama D0250 Top cuello redondo D0300 Playera cuello V D0306 Playera tipo polo pique D0500 Camisa gabardina manga corta D0606 Camisa gabardina manga larga D0607 Pantalón de Gabardina para dama D0650 Playera cuello redondo J0300 Playera tipo polo piqué J0500 Sudadera juvenil cuello redondo J0700 Sudadera con capucha y cangurera unisex para jóvenes. Peso*: 285 g/m 2 *Peso en color oscuro Tejido: Felpa 50% algodón 50% poliéster natural. Tallas: XCH, CH, M, G, XG ¡Llama YA! 01.800.2.929235 ------------------------------------- Conoce nuestras sucursales: Tijuana, La Paz, Torreón, Acapulco, México D.F., Morelia, Cancún, Hermosillo, San Luis Potosí, Veracruz, Mérida J0701 Sudadera con capucha y cierre unisex para jóvenes. Peso*: 285 g/m 2 *Peso en color oscuro Tejido: Felpa 50% algodón 50% poliéster natural. Tallas: XCH, CH, M, G, XG ¡Llama YA! 01.800.2.929235 ------------------------------------- Conoce nuestras sucursales: Tijuana, La Paz, Torreón, Acapulco, México D.F., Morelia, Cancún, Hermosillo, San Luis Potosí, Veracruz, Mérida J0702 Playera cuello redondo N0300 '

colores['Ocre'] = 'Playera cuello redondo C0300'
colores['Olivo'] = ' Playera cuello redondo C0300 Playera manga larga C0304 Top cuello redondo D0300 '
colores['Paja'] = ' Camisa oxford manga corta C0604 Camisa oxford manga larga C0605 '
colores['Pistache'] = ' Playera cuello redondo C0300 '

colores['Plata'] = ' Playera cuello redondo C0300 '
colores['Rojo'] = ' Playera cuello redondo B0300 Playera cuello redondo C0300 Playera manga larga C0304 Playera sin mangas C0310 Playera tipo polo piqué C0500 Camisa oxford mil rayas manga corta C0602 Camisa oxford mil rayas manga larga C0603 Camisa gabardina manga corta C0606 Camisa gabardina manga larga C0607 Sudadera cuello redondo C0700 Sudadera con capucha y cangurera C0701 Sudadera con capucha y cierre C0702 Top cuello redondo D0300 Playera tipo polo pique D0500 Camisa oxford mil rayas manga corta para dama D0602 Camisa oxford mil rayas manga larga para dama D0603 Camisa gabardina manga corta D0606 Camisa gabardina manga larga D0607 Playera cuello redondo J0300 Playera tipo polo piqué J0500 Sudadera juvenil cuello redondo J0700 Sudadera con capucha y cangurera unisex para jóvenes. Peso*: 285 g/m 2 *Peso en color oscuro Tejido: Felpa 50% algodón 50% poliéster natural. Tallas: XCH, CH, M, G, XG ¡Llama YA! 01.800.2.929235 ------------------------------------- Conoce nuestras sucursales: Tijuana, La Paz, Torreón, Acapulco, México D.F., Morelia, Cancún, Hermosillo, San Luis Potosí, Veracruz, Mérida J0701 Sudadera con capucha y cierre unisex para jóvenes. Peso*: 285 g/m 2 *Peso en color oscuro Tejido: Felpa 50% algodón 50% poliéster natural. Tallas: XCH, CH, M, G, XG ¡Llama YA! 01.800.2.929235 ------------------------------------- Conoce nuestras sucursales: Tijuana, La Paz, Torreón, Acapulco, México D.F., Morelia, Cancún, Hermosillo, San Luis Potosí, Veracruz, Mérida J0702 Playera cuello redondo N0300 '
colores['Rosa Pastel'] = ' Playera cuello redondo B0300 Playera cuello redondo C0300 Top cuello redondo D0300 Playera cuello redondo J0300 Playera cuello redondo N0300 '
colores['Royal'] = ' Playera cuello redondo jaspe C0250 Playera cuello redondo C0300 Playera manga larga C0304 Playera sin mangas C0310 Playera tipo polo piqué C0500 Sudadera cuello redondo C0700 Sudadera con capucha y cangurera C0701 Sudadera con capucha y cierre C0702 Playera cuello redondo jaspe dama D0250 Playera tipo polo pique D0500 Playera cuello redondo J0300 Playera tipo polo piqué J0500 Sudadera juvenil cuello redondo J0700 Sudadera con capucha y cangurera unisex para jóvenes. Peso*: 285 g/m 2 *Peso en color oscuro Tejido: Felpa 50% algodón 50% poliéster natural. Tallas: XCH, CH, M, G, XG ¡Llama YA! 01.800.2.929235 ------------------------------------- Conoce nuestras sucursales: Tijuana, La Paz, Torreón, Acapulco, México D.F., Morelia, Cancún, Hermosillo, San Luis Potosí, Veracruz, Mérida J0701 Sudadera con capucha y cierre unisex para jóvenes. Peso*: 285 g/m 2 *Peso en color oscuro Tejido: Felpa 50% algodón 50% poliéster natural. Tallas: XCH, CH, M, G, XG ¡Llama YA! 01.800.2.929235 ------------------------------------- Conoce nuestras sucursales: Tijuana, La Paz, Torreón, Acapulco, México D.F., Morelia, Cancún, Hermosillo, San Luis Potosí, Veracruz, Mérida J0702 Playera cuello redondo N0300 '
colores['Turquesa'] = '  Playera cuello redondo B0300 Playera cuello redondo C0300 Playera cuello redondo J0300 Playera tipo polo piqué J0500 Playera cuello redondo N0300  '
colores['Verde Bosque'] = ' Playera cuello redondo C0300 Playera tipo polo piqué C0500 Playera tipo polo pique D0500 '



# Escanear las paginas para obtener los links a los productos
listaLinksIniciales = [
'https://www.yazbek.com.mx/idem.php?module=Catalog&action=CategoryIndex&sort_field=catalog.item.name&sort_order=ASC&category=4577&CategoryPager_page=',
'https://www.yazbek.com.mx/idem.php?module=Catalog&action=CategoryIndex&sort_field=catalog.item.name&sort_order=ASC&category=4578&CategoryPager_page=',
'https://www.yazbek.com.mx/idem.php?module=Catalog&action=CategoryIndex&sort_field=catalog.item.name&sort_order=ASC&category=4579&CategoryPager_page=',
'https://www.yazbek.com.mx/idem.php?module=Catalog&action=CategoryIndex&sort_field=catalog.item.name&sort_order=ASC&category=4691&CategoryPager_page=',
'https://www.yazbek.com.mx/idem.php?module=Catalog&action=CategoryIndex&sort_field=catalog.item.name&sort_order=ASC&category=4693&CategoryPager_page=',
'https://www.yazbek.com.mx/idem.php?module=Catalog&action=CategoryIndex&sort_field=catalog.item.name&sort_order=ASC&category=4763&CategoryPager_page=',
'https://www.yazbek.com.mx/idem.php?module=Catalog&action=CategoryIndex&sort_field=catalog.item.name&sort_order=ASC&category=4709&CategoryPager_page=',
'https://www.yazbek.com.mx/idem.php?module=Catalog&action=CategoryIndex&sort_field=catalog.item.name&sort_order=ASC&category=4580&CategoryPager_page='];

listaProductos = []
for pagina in listaLinksIniciales:
	escanearPagina(pagina);

listaResultados = []
listaResultados.append( 'Sku-Estilo;Categoria;Url;Nombre;Fotos;Tallas;Notas;Colores;Ficha Tecnica');

for prod in listaProductos:
	escanearProducto(prod);
	saveFile('pino.csv',listaResultados);
