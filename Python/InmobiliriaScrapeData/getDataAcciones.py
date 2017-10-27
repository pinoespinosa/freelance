from URL_Lib import descargarResultadoData
from File_Lib import saveFile, loadFile
import time
import json
from datetime import datetime



import tkinter
import tkinter.messagebox
from tkinter import *
import tkinter.ttk
from tkinter.ttk import *

import collections


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

	tipo2=[];
	tipo2.append('8');
	tipo2.append('9');
	tipo2.append('2');
	tipo2.append('7');
	tipo2.append('3');
	tipo2.append('4');
	tipo2.append('21');

	ciudad={};
	ciudad['San Andres y Providencia']='79';
	ciudad['Bogotá D.C.']='67';
	ciudad['Cundinamarca']='67';
	ciudad['Cesar']='64';
	ciudad['Norte de Santander']='85';
	ciudad['Santander']='75';
	ciudad['Boyacá']='59';
	ciudad['Arauca']='56';
	ciudad['Casanare']='62';
	ciudad['Vichada']='84';
	ciudad['Guanía']='68';
	ciudad['Meta']='73';
	ciudad['Guaviare']='69';
	ciudad['Vaupés']='83';
	ciudad['Amazonas']='54';
	ciudad['Huila']='70';
	ciudad['Caquetá']='61';
	ciudad['Putumayo']='76';
	ciudad['Nariño']='74';
	ciudad['Cauca']='63';
	ciudad['Tolima']='81';
	ciudad['Valle del Cauca y Pacífico']='82';
	ciudad['Quindío']='77';
	ciudad['Caldas']='60';
	ciudad['Risaralda y Eje Cafetero']='78';
	ciudad['Chocó']='65';
	ciudad['Antioquia']='55';
	ciudad['Córdoba']='66';
	ciudad['Sucre']='80';
	ciudad['Bolívar']='58';
	ciudad['Atlántico']='57';
	ciudad['Magdalena']='72';
	ciudad['Guajira']='71';


	acumulaodo = []
	cant = 1;
	pos = 1;
	while (cant>0):

		labbel.set("Escaneando pagina " +str(pos)+ " ...");
		app.update_idletasks()

		if (valor_web>0):
			valor = descargarInfoAccion(web[valor_web],'/'+tipo[valor_tipo]+'/'+inmueble[valor_inmueble]+'/?ad=30|'+str(pos)+'||||'+inmueble2[valor_inmueble]+'||'+tipo2[valor_tipo]+'|||'+str(ciudad[valor_ciudad])+'|||||||||||||||||1|||1||||||-1', acumulaodo, labbel, d, pos, tipo[valor_tipo]+'_'+inmueble[valor_inmueble]+'_'+valor_ciudad.replace(' ','_'));
		else:
			valor = descargarInfoAccion2(web[valor_web],'/'+tipo[valor_tipo]+'/'+inmueble[valor_inmueble]+'/?ad=30|'+str(pos)+'||||'+inmueble2[valor_inmueble]+'||'+tipo2[valor_tipo]+'|||'+str(ciudad[valor_ciudad])+'|||||||||||||||||1|||1||||||-1', acumulaodo, labbel, d, pos, tipo[valor_tipo]+'_'+inmueble[valor_inmueble]+'_'+valor_ciudad.replace(' ','_'));


		cant = len(valor)
		pos = pos + 1;

	labbel.set("Escanea completo");


def descargarInfoAccion(url1, url2, acumulaodo, labbel, d, pos, nombrefile):
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
		dato_titulo = ooo.find_all(class_="title")[0].text.replace(',',';').strip()
		dato_precio = ooo.find_all(class_="price")[0].find_all("h2")[0].text.replace(',',';').strip()
		dato_url='www.fincaraiz.com.co' + aa.replace(',',';');



		d.append('www.fincaraiz.com.co' + aa);

		labbel.set("Escaneando pagina " +str(pos)+ "...\n" + "\n".join(d));
		app.update_idletasks()
		app.update()

		print()
		acumulaodo.append(dato_titulo + ',' + dato_precio + ',' + dato_url);

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
	webb= combo1.current()
	tipoOper= combo1.current()
	tipoProp= combo2.current()
	ubicacion= combo3.get()
	ubicacion2= combo3.get()

	if (webb>-1 and tipoOper>-1 and tipoProp>-1 and ( ubicacion!='' or ubicacion2!='') ):
		descargarTodo(webb, tipoOper, tipoProp, ubicacion, v, app, d, ubicacion2);

app.geometry('640x480')


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
combo2.config(values =('Apartamento', 'Casa','Lote', 'Finca','Local', 'Oficina','Casa Campestre'))
combo2.grid(row=3, column=2, sticky='E', padx=10)

v4 = StringVar()
e4 = Label(app, textvariable=v4)
e4.grid(row=4, column=1, sticky='E', padx=10, pady=10)
v4.set('Ubicacion [Solo para el sitio fincaraiz.com.co]')
ciudad = StringVar(app)
combo3 = tkinter.ttk.Combobox(app, textvariable=ciudad)
combo3.config(values =('Amazonas','Antioquia','Arauca','Atlántico', 'Bogotá D.C.','Bolívar','Boyacá','Caldas','Caquetá','Casanare','Cauca', 'Cesar','Chocó','Cundinamarca','Córdoba','Guajira','Guanía','Guaviare','Huila','Magdalena','Meta','Nariño','Norte de Santander','Putumayo','Quindío','Risaralda y Eje Cafetero','San Andres y Providencia', 'Santander','Sucre','Tolima','Valle del Cauca y Pacífico','Vaupés','Vichada'))
combo3.grid(row=4, column=2, sticky='E', padx=10)

v5 = StringVar()
e5 = Label(app, textvariable=v5)
e5.grid(row=5, column=1, sticky='E', padx=10, pady=10)
v5.set('Ubicacion [Solo para el sitio fincaraiz.com.co]')
ciudad2 = StringVar(app)
combo4 = tkinter.ttk.Combobox(app, textvariable=ciudad)
combo4.config(values =('Amazonas','Antioquia','Arauca','Atlántico', 'Bogotá D.C.','Bolívar','Boyacá','Caldas','Caquetá','Casanare','Cauca', 'Cesar','Chocó','Cundinamarca','Córdoba','Guajira','Guanía','Guaviare','Huila','Magdalena','Meta','Nariño','Norte de Santander','Putumayo','Quindío','Risaralda y Eje Cafetero','San Andres y Providencia', 'Santander','Sucre','Tolima','Valle del Cauca y Pacífico','Vaupés','Vichada'))
combo4.grid(row=5, column=2, sticky='E', padx=10)

boton = Button(app, text="OK", command=callback)
boton.grid(row=6, column=1, sticky='E', padx=10, pady=10)

v = StringVar()
e = Label(app, textvariable=v)
e.grid(row=7, column=3, sticky='E')

app.update_idletasks()
app.mainloop();











