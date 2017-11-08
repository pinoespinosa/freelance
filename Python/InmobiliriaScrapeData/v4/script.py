from URL_Lib import descargarResultadoData, descargarResultadoData2, descargarResultadoData3
from File_Lib import saveFile, loadFile
import time
import json
from datetime import datetime
from bs4 import BeautifulSoup 	# pip install beautifulsoup4

import tkinter
import tkinter.messagebox
from tkinter import *
import tkinter.ttk
from tkinter.ttk import *

import collections
import re

#
# ********************************** Programa principal **********************************
#
#



def descargarTodo(valor_web, valor_inmueble, valor_tipo, valor_ciudad, labbel, app, d, ubicacion2):

	web=[];
	web.append('www.metrocuadrado.com');
	web.append('www.fincaraiz.com.co');

	inmueble=[];
	inmueble.append('arriendo');
	inmueble.append('venta');
	inmueble2=[];
	inmueble2.append('2');
	inmueble2.append('1');

	tipo=[];
	tipo.append('apartamentos');
	tipo.append('casas');
	tipo.append('lotes');
	tipo.append('fincas');
	tipo.append('locales');
	tipo.append('oficinas');
	tipo.append('casas-campestres');
	tipo.append('bodegas');
	tipo.append('consultorios');


	tipo_metro=[];
	tipo_metro.append('apartamento');
	tipo_metro.append('casa');
	tipo_metro.append('lote');
	tipo_metro.append('finca');
	tipo_metro.append('local');
	tipo_metro.append('oficina');
	tipo_metro.append('casas-campestres');
	tipo_metro.append('bodega');
	tipo_metro.append('consultorio');

	tipo2=[];
	tipo2.append('8');
	tipo2.append('9');
	tipo2.append('2');
	tipo2.append('7');
	tipo2.append('3');
	tipo2.append('4');
	tipo2.append('21');
	tipo2.append('5');
	tipo2.append('18');

	ciudad={};
	ciudad['San Andres y Providencia']='79|';
	ciudad['bogota']='67|3630001';
	ciudad['Cundinamarca']='67|';
	ciudad['Cesar']='64|';
	ciudad['Norte de Santander']='85|';
	ciudad['Santander']='75|';
	ciudad['Boyacá']='59|';
	ciudad['Arauca']='56|';
	ciudad['Casanare']='62|';
	ciudad['Vichada']='84|';
	ciudad['Guanía']='68|';
	ciudad['Meta']='73|';
	ciudad['Guaviare']='69|';
	ciudad['Vaupés']='83|';
	ciudad['Amazonas']='54|';
	ciudad['Huila']='70|';
	ciudad['Caquetá']='61|';
	ciudad['Putumayo']='76|';
	ciudad['Nariño']='74|';
	ciudad['Cauca']='63|';
	ciudad['Tolima']='81|';
	ciudad['Valle del Cauca y Pacífico']='82|';
	ciudad['Quindío']='77|';
	ciudad['Caldas']='60|';
	ciudad['Risaralda y Eje Cafetero']='78|';
	ciudad['Chocó']='65|';
	ciudad['Antioquia']='55|';
	ciudad['Córdoba']='66|';
	ciudad['Sucre']='80|';
	ciudad['Bolívar']='58|';
	ciudad['Atlántico']='57|';
	ciudad['Magdalena']='72|';
	ciudad['Guajira']='71|';

	acumulaodo = []
	
	if (valor_web>0):
		acumulaodo.append('Nombre,Link,Ubicacion,Precio,Cant Habitaciones,Cant Baños,Antigüedad,Parqueadero,Area Privada, Area Construida, Estrato, Estado, Piso, Descripcion, Balcon, Calentador, Cocina Integral, Instalacion de gas, Actualizado, Visitas, Codigo Web');
	else:
 		acumulaodo.append('Nombre,Link,Ubicacion,Precio,Cant Habitaciones,Cant Baños,Antigüedad,Parqueadero,Codigo Web,Direccion,Descripcion,Nombre Barrio,Nombre Barrio Cat, Valor Venta, Valor Arriendo, Area construida, Tiempo de Construida, Estudio o Biblioteca, Terraza/Balcon, Instalacio de Gas, Tpo de calentador, Vista, Cuarto de Servicio, Tipo de Cortinas, Tipo de Acabado de Piso, Tipo de Piso en Alcobas, Numero de Piso');

	cant = 1;
	pos = 1;
	while (cant>0):

		labbel.set("Escaneando pagina " +str(pos)+ " ..." + str(valor_web));
		app.update_idletasks()

		if (valor_web>0):
			valor = descargarFincaRaizPage(web[valor_web],'/'+tipo[valor_tipo]+'/'+inmueble[valor_inmueble]+'/?ad=30|'+str(pos)+'||||'+inmueble2[valor_inmueble]+'||'+tipo2[valor_tipo]+'|||'+str(ciudad[valor_ciudad])+'||||||||||||||||1|||1||||||-1', acumulaodo, labbel, d, pos, 'fincaraiz_'+tipo[valor_tipo]+'_'+inmueble[valor_inmueble]+'_'+valor_ciudad.replace(' ','_')+'.csv');
		else:
			valor = descargarMetroCuadradoPage(web[valor_web],'/' + tipo_metro[valor_tipo] +'/'  +inmueble[valor_inmueble]+'/' + ubicacion2, acumulaodo, labbel, d, pos, 'metrocuadrado_'+tipo[valor_tipo]+'_'+inmueble[valor_inmueble]+'_'+ubicacion2+'.csv',   tipo_metro[valor_tipo], inmueble[valor_inmueble], ubicacion2);

		cant = len(valor)
		pos = pos + 1;

	labbel.set("Escanea completo");




def descargarMetroCuadradoPage(url1, url2, acumulaodo, labbel, d, pos, nombrefile, tip_imn, tip_op, ciudad):
	
	querystring = {"mtiponegocio":tip_op,"mtipoinmueble":tip_imn,"mciudad":ciudad,"selectedLocationCategory":"1","selectedLocationFilter":"mciudad","currentPage":pos,"totalPropertiesCount":"1184","totalUsedPropertiesCount":"1184","totalNewPropertiesCount":"0","sfh":"1"}
	pageResponse = descargarResultadoData2(url1 + url2,querystring, 360, 10, '', '')
	pubList = pageResponse.prettify().split('<div class="m_rs_list_item ">');

	elem = [];
	cant=0;

#	if 'No tenemos inmuebles que coincidan con su' in pageResponse.prettify():
#		return elem;

	for pub in pubList:
		if (cant==0):
			cant=1
		else:
			elem.append(pub.split('href="')[1].split('"')[0]);

	for aa in elem:
		orig2 = descargarResultadoData3(aa,'', 360, 10, '', '')

		try:
			dato_nombre = orig2.find_all(class_="m_property_info_title")[0].find_all("h1")[0].text.replace(',',';').strip()
		except:
			dato_nombre = ''

		try:
			dato_link = aa
		except:
			dato_link = ''

		try:
			dato_area = ''
		except:
			dato_area = ''

		try:
			dato_precio = orig2.find_all(class_="m_property_info_table")[0].find_all("dd")[0].text.replace(',',';').strip()
		except:
			dato_precio = ''

		try:
			dato_habit = orig2.find_all(class_="m_property_info_table")[0].prettify().split("Habitaciones")[1].split("<dd>")[1].split("</dd>")[0].strip()
		except:
			dato_habit = ''

		try:
			dato_banio = orig2.find_all(class_="m_property_info_table")[0].prettify().split("Ba")[1].split("<dd>")[1].split("</dd>")[0].strip()
		except:
			dato_banio = ''

		try:
			dato_edad = ''
		except:
			dato_edad = ''

		try:
			dato_parq = orig2.find_all(class_="m_property_info_table")[0].prettify().split("Parqueadero")[1].split("<dd>")[1].split("</dd>")[0].strip()
		except:
			dato_parq = ''

		try:
			dato_codweb = orig2.find_all(class_="ref")[0].text.strip()
		except:
			dato_codweb = ''

		try:
			dato_inmobiliaria = orig2.find_all(class_="datos_inmobiliaria")[0].text.strip()
		except:
			dato_inmobiliaria = ''

		try:
			dato_inmobiliaria = orig2.find_all(class_="datos_inmobiliaria")[0].text.strip()
		except:
			dato_inmobiliaria = ''

		try:
			dato_descrip = '"' + orig2.find_all(id="pDescription")[0].text.replace('"','').strip() + '"';
		except:
			dato_descrip = ''


		try:
			dato_nombreBarrio = orig2.prettify().split("Nombre común del barrio")[1].split("<h4>")[1].split("</h4>")[0].strip()
		except:
			dato_nombreBarrio = ''

		try:
			dato_nombreBarrioCat = orig2.prettify().split("Nombre del barrio catastral")[1].split("<h4>")[1].split("</h4>")[0].strip()
		except:
			dato_nombreBarrioCat = ''

		try:
			dato_valorVenta = orig2.prettify().split("Valor de venta")[1].split("<h4>")[1].split("<")[0].strip()
		except:
			dato_valorVenta = ''
		
		try:
			dato_valorArriendo = orig2.prettify().split("Valor de arriendo")[1].split("<h4>")[1].split("<")[0].strip()
		except:
			dato_valorArriendo = ''

		try:
			dato_areaConst = orig2.find_all(class_="m_property_info_details")[0].prettify().split("Área construida")[1].split("<h4>")[1].split("<")[0].strip() + '2'
		except:
			dato_areaConst = ''

		try:
			dato_tiempoCost = orig2.prettify().split("Tiempo de construido")[1].split("<h4>")[1].split("<")[0].strip()
		except:
			dato_tiempoCost = ''
		try:
			dato_biblio = orig2.prettify().split("Estudio o biblioteca")[1].split("<h4>")[1].split("<")[0].strip()
		except:
			dato_biblio = ''
		try:
			dato_terraza = orig2.prettify().split("Terraza/Balcón")[1].split("<h4>")[1].split("<")[0].strip()
		except:
			dato_terraza = ''

		try:
			dato_instalacioGas = orig2.prettify().split("Tipo instalación de gas")[1].split("<h4>")[1].split("<")[0].strip()
		except:
			dato_instalacioGas = ''
		try:
			dato_instalacioCalef = orig2.prettify().split("Tipo de calentador")[1].split("<h4>")[1].split("<")[0].strip()
		except:
			dato_instalacioCalef = ''
		try:
			dato_vista = orig2.prettify().split("Vista")[1].split("<h4>")[1].split("<")[0].strip()
		except:
			dato_vista = ''
		try:
			dato_habServ = orig2.prettify().split("Cuarto de servicio")[1].split("<h4>")[1].split("<")[0].strip()
		except:
			dato_habServ = ''
		try:
			dato_tipCortina = orig2.prettify().split("Tipo de Cortinas")[1].split("<h4>")[1].split("<")[0].strip()
		except:
			dato_tipCortina = ''
		try:
			dato_tipPiso = orig2.prettify().split("Tipo de acabado piso")[1].split("<h4>")[1].split("<")[0].strip()
		except:
			dato_tipPiso = ''
		try:
			dato_tipPisoAlcoba = orig2.prettify().split("Tipo de piso en alcobas")[1].split("<h4>")[1].split("<")[0].strip()
		except:
			dato_tipPisoAlcoba = ''
		try:
			dato_numPiso = orig2.prettify().split("Número de piso")[1].split("<h4>")[1].split("<")[0].strip()
		except:
			dato_numPiso = ''
		

		d.append(aa);
		
		labbel.set("Escaneando pagina " +str(pos)+ "...\n" + "\n".join(d));
		
		app.update_idletasks()
		app.update()

		acumulaodo.append(dato_nombre + ',' + dato_link + ',' + dato_area + ',' + dato_precio + ',' + dato_habit + ',' + dato_banio + ',' + dato_edad + ',' + dato_parq + ',' + dato_codweb + ',' + dato_inmobiliaria + ',' + dato_descrip + ',' + dato_nombreBarrio + ',' + dato_nombreBarrioCat + ',' + dato_valorVenta + ',' + dato_valorArriendo + ',' + dato_areaConst  + ',' + dato_tiempoCost+ ',' + dato_biblio+ ',' + dato_terraza+ ',' + dato_instalacioGas + ',' + dato_instalacioCalef + ',' + dato_vista + ',' + dato_habServ + ',' + dato_tipCortina + ',' + dato_tipPiso + ',' + dato_tipPisoAlcoba + ',' + dato_numPiso);
		saveFile(nombrefile.replace("/","_"), acumulaodo)

	return elem;



def descargarFincaRaizPage(url1, url2, acumulaodo, labbel, d, pos, nombrefile):
	file = [];
	orig = descargarResultadoData(url1, url2, 360, 10, '', '')
	data = orig.find_all(id="divAdverts")[0]
	data = data.find_all('ul');

	elem = [];

	for aa in data:
		try:
			elem.append(aa.find_all('a')[0]['href'])
		except:
			a=''


	for aa in elem:

		ooo = descargarResultadoData(url1, aa, 360, 10, '', '')
		dato_nombre2 = ooo.find_all(class_="title")[0].text.replace(',',';').strip().rstrip('\r\n');
		dato_nombre = ' '.join(line.strip() for line in dato_nombre2.splitlines())

		dato_link='www.fincaraiz.com.co' + aa.replace(',',';');
		
		prov_area = '<div class="breadcrumb left">' + ooo.prettify().split('<div class="breadcrumb left">')[1].split('<div class="breadcrumb right">')[0];
		prov_area = BeautifulSoup(prov_area, 'html.parser');
		prov_area = prov_area.text;
		prov_area = prov_area.split('/');
		dato_area='';
		for aa in prov_area:
			if (not 'Inicio' in aa) and (not ' - ' in aa): 
				dato_area = dato_area + '/' + aa.strip()

		try:
			dato_precio = ooo.find_all(class_="price")[0].find_all("h2")[0].text.replace(',',';').strip()
		except:
			dato_precio = ''
		try:
			dato_habit = str(int(ooo.prettify().split('src="/App_Theme/css/img/ico_bed.png"')[1].split(':')[1].split('<')[0].strip()));
		except:
			dato_habit = ''

		try:
			dato_banio = str(int(ooo.prettify().split('src="/App_Theme/css/img/ico_bath.png"')[1].split(':')[1].split('<')[0].strip()));
		except:
			dato_banio = ''

		try:
			dato_parq = str(int(ooo.prettify().split('src="/App_Theme/css/img/ico_garaje.png"')[1].split(':')[1].split('<')[0].strip()));
		except:
			dato_parq = ''

		try:
			dato_edad = ooo.prettify().split('edad:')[1].split(',')[0].strip();
		except:
			dato_edad= ''


		try:
			dato_areaPriv = '"' + ooo.find('b', text=re.compile(r'Área privada')).parent.text.split(':')[1].strip() + '"';
		except:
			dato_areaPriv = ''

		try:
			dato_areaConst = '"' +  ooo.find('b', text=re.compile(r'Área Const')).parent.text.split(':')[1].strip() + '"';
		except:
			dato_areaConst = ''

		try:
			dato_estrato ='"' + ooo.find('b', text=re.compile(r'Estrato')).parent.text.split(':')[1].text.replace('"','').strip() + '"';
		except:
			dato_estrato = ''

		try:
			dato_estado = ooo.find('b', text=re.compile(r'Estado')).parent.text.split(':')[1].strip();
		except:
			dato_estado = ''

		try:
			dato_piso = ooo.find('b', text=re.compile(r'Piso No')).parent.text.split(':')[1].strip();
		except:
			dato_piso = ''
		try:
			dato_descrip = '"' + ooo.find_all('div', class_='description')[0].find('p').text.replace('"','').strip() + '"';
		except:
			dato_descrip = ''


		try:
			dato_balcon = str(ooo.find('li', text=re.compile(r'Balcón')).text == 'Balcón');
		except:
			dato_balcon = ''
		try:
			dato_calentador = str(ooo.find('li', text=re.compile(r'Calentador')).text.strip() == 'Calentador');
		except:
			dato_calentador = ''
		try:
			dato_cocinaInt = str(ooo.find('li', text=re.compile(r'Cocina Integral')).text == 'Cocina Integral');
		except:
			dato_cocinaInt = ''
		try:
			dato_instalacioGas = str(ooo.find('li', text=re.compile(r'Instalación de gas')).text.strip() == 'Instalación de gas');
		except:
			dato_instalacioGas = ''
		
		try:
			dato_actualizado = ooo.find('li', style='display:inline;margin-right:30px;').text.replace("Actualizado:","").strip();
		except:
			dato_actualizado = ''
		try:
			dato_visitas = '';
		except:
			dato_visitas = ''
		try:
			dato_codweb = ooo.find_all('li', style='display:inline; margin-right:30px')[1].text;
		except:
			dato_codweb = ''

		d.append('www.fincaraiz.com.co' + aa);

		labbel.set("Escaneando pagina " +str(pos)+ "...\n" + "\n".join(d));
		app.update_idletasks()
		app.update()

		acumulaodo.append(dato_nombre + ',' + dato_link + ',' + dato_area + ',' + dato_precio + ',' + dato_habit + ',' + dato_banio + ',' + dato_edad + ',' + dato_parq + ',' + dato_areaPriv + ',' + dato_areaConst + ',' + dato_estrato + ',' +dato_estado + ',' + dato_descrip + ',' + dato_piso + ',' + dato_balcon + ',' + dato_calentador + ',' + dato_cocinaInt + ',' + dato_instalacioGas + ',' +dato_actualizado + ',' + dato_visitas + ',' + dato_codweb	);

		saveFile(nombrefile, acumulaodo)

	try:
		orig.find_all(title="Ir a la pagina Siguiente")[0]
	except:
		elem = [];

	return elem;



d = collections.deque(maxlen=10)


app = Tk()

def close_window():
	import sys
	print('pino')

def callback():
	webb= combo0.current()
	tipoOper= combo1.current()
	tipoProp= combo2.current()
	ubicacion= combo3.get()
	ubicacion2= combo4.get()

	if (webb>-1 and tipoOper>-1 and tipoProp>-1 ):
		if (( webb==1 and ubicacion!='') or ( webb==0 and ubicacion2!='')):
			descargarTodo(webb, tipoOper, tipoProp, ubicacion, v, app, d, ubicacion2);

app.geometry('640x300')


v1 = StringVar()
v1.set('Web')
e1 = Label(app, textvariable=v1)
e1.grid(row=1, column=1, sticky='E', padx=10, pady=10)
variable2 = StringVar(app)
combo0 = tkinter.ttk.Combobox(app, textvariable=variable2)
combo0.config(values =('metrocuadrado.com', 'fincaraiz.com.co'))
combo0.grid(row=1, column=2, sticky='E', padx=10)

v2 = StringVar()
v2.set('Tipo de operacion')
e2 = Label(app, textvariable=v2)
e2.grid(row=2, column=1, sticky='E', padx=10, pady=10)
variable = StringVar(app)
combo1 = tkinter.ttk.Combobox(app, textvariable=variable)
combo1.config(values =('Arriendo', 'Venta'))
combo1.grid(row=2, column=2, sticky='E', padx=10)

v3 = StringVar()
e3 = Label(app, textvariable=v3)
e3.grid(row=3, column=1, sticky='E', padx=10, pady=10)
v3.set('Tipo de inmueble')
inmueble = StringVar(app)
combo2 = tkinter.ttk.Combobox(app, textvariable=inmueble)
combo2.config(values =('Apartamento', 'Casa','Lote', 'Finca','Local', 'Oficina','Casa Campestre', 'Bodega', 'Consultorio'))
combo2.grid(row=3, column=2, sticky='E', padx=10)

v4 = StringVar()
e4 = Label(app, textvariable=v4)
e4.grid(row=4, column=1, sticky='E', padx=10, pady=10)
v4.set('Ubicacion [Solo para el sitio fincaraiz.com.co]')
ciudad = StringVar(app)
combo3 = tkinter.ttk.Combobox(app, textvariable=ciudad)
combo3.config(values =('Amazonas','Antioquia','Arauca','Atlántico', 'bogota','Bolívar','Boyacá','Caldas','Caquetá','Casanare','Cauca', 'Cesar','Chocó','Cundinamarca','Córdoba','Guajira','Guanía','Guaviare','Huila','Magdalena','Meta','Nariño','Norte de Santander','Putumayo','Quindío','Risaralda y Eje Cafetero','San Andres y Providencia', 'Santander','Sucre','Tolima','Valle del Cauca y Pacífico','Vaupés','Vichada'))
combo3.grid(row=4, column=2, sticky='E', padx=10)

v5 = StringVar()
e5 = Label(app, textvariable=v5)
e5.grid(row=5, column=1, sticky='E', padx=10, pady=10)
v5.set('Ubicacion [Solo para el sitio metrocuadrado.com]')
ciudad2 = StringVar(app)
combo4 = tkinter.ttk.Entry(app, textvariable=ciudad2)
combo4.grid(row=5, column=2, sticky='E', padx=10)

boton = Button(app, text="OK", command=callback)
boton.grid(row=6, column=1, sticky='E', padx=10, pady=10)

v = StringVar()
e = Label(app, textvariable=v)
e.grid(row=7, column=3, sticky='E')

app.update_idletasks()
app.mainloop();











