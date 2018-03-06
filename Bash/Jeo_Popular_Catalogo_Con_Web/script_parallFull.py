from URL_Lib import descargarResultado
from File_Lib import saveFile, saveFileExc, loadFile
from bs4 import BeautifulSoup 	# pip install beautifulsoup4

import threading
import _thread
import time

#
#
# ********************************** Programa principal **********************************
#
#


def loadFiless(FILENOMBRE):

	LISTA = "";

	try:
		file = open(FILENOMBRE, "r")
		for line in file:
			LISTA = LISTA + line.strip() + '\n';
		file.close();
	except (FileNotFoundError):
		print('No se hallo el archivo ');
	
	return LISTA


def descargarCategoriaEspecifica22(URLLL, resultados):
	resultado = descargarResultado("/producto/" +  URLLL , 360, 10);


	try: 
		codigo = URLLL
	except:
		codigo = ''

	try: 
		nombre = resultado.split('<h2 class="with-tabs">')[1].split('</h2>')[0].replace("\\t",'').strip()
	except:
		nombre = ''
	
	try:	
		categoria = resultado.split('<b>Categor')[1].split('</div>')[0].split('</b>')[1].replace("\\t",'').replace("\\n",'').strip()
	except:
		categoria = ''

	try:	
		costo = resultado.split('class="uc-price">')[2].split('<')[0].replace("\\t",'').strip()
	except:
		costo = ''

	try:
		fotos = 'http://www.radec.com.mx/sites/all/files/productos/' + codigo + '.jpg';
	except:
		fotos = ''

	val = 0;

	nombre2 = nombre

	try:
		for car in resultado.split("/sites/all/themes/radec/images/car_icon.gif"):


			marca = ''
			marca_auto = ''
			modelo = ''
			anio = ''
			notas = ''

			if (val == 0):
				val = 1;
			else:
				try:
					marca_auto = car.split('<')[0].split('>')[1].replace("\\t",'').split('\\n')[2].strip()
				except:
					marca_auto = ''


				try:
					marca = ''

					if (' TYC ' in nombre):
						marca = 'TYC'
					
					if ( ' DEPO ' in nombre):
						marca = 'DEPO' 
				except:
					marca = ''

				try:				
					modelo = car.split('<')[0].split('>')[1].replace("\\t",'').split('\\n')[3].strip()
				except:
					modelo = ''



				anio = car.split('<')[0].split('>')[1].replace("\\t",'').split('\\n')[5].strip()


				if (anio != 'ALL YEARS'):
					anioOrigin2 = '#'+anio;
					anioOrigin = anioOrigin2.replace('#20','').replace('#19','').replace('-20','-').replace('-19','-')
					anioList = [];
	

					if ('-' in anio):
						
						anioInicio = int(anio.split('-')[0])
						anioFin = int(anio.split('-')[1] )

						while (anioInicio <= anioFin):
							anioList.append(str(anioInicio))
							anioInicio = anioInicio + 1;
	
						anio = ' '.join(anioList) + ' '
					
				
					if (len(anioList) < 5):
						nombre = nombre.replace(anioOrigin,anio);
					else:
						nombre = nombre.replace(anioOrigin,anioOrigin2.replace('#','').replace('-',' a '));

		

				try:	
					notas = resultado.split('<b>Aplicaciones:</b>')[1].split('</div>')[0].replace("\\t",'').replace("\\n",'').replace('<br/>',' - ')

					notas = BeautifulSoup(notas, 'html.parser').text;

					while ("  " in notas):
						notas = notas.replace('  ',' ');

					if (notas.startswith(' - ')):
						notas = notas.replace(" - ", "", 1)

					if (notas.endswith(' - ')):
						notas = rreplace(notas," - ", "", 1);

				except:
					notas = ''



				nombre= nombre.replace(' FD ', ' FORD ').replace(' CV ', ' CHEVROLET ').replace(' TY ', ' TOYOTA ').replace(' AD ', ' AUDI ').replace(' BK ', ' BUICK ').replace(' MC ', ' MERCEDES BENZ ').replace(' ST ', ' SEAT ').replace(' VW ', ' VOLKSWAGEN ').replace(' KI ', ' KIA ').replace(' NS ', ' NISSAN ').replace(' HD ', ' HONDA ').replace(' SN ',' SATURN ').replace(' JP ', ' JEEP ').replace(' AC ', ' ACURA ').replace(' DG ', ' DODGE ').replace(' PT ',' PONTIAC ').replace(' BW ', ' BMW ').replace(' CR ', ' CHRYSLER ').replace(' MT ', ' MITSUBISHI ').replace(' PG ',' PEUGEOT ').replace(' UNIV ', ' UNIVERSAL ').replace(' CR ', ' CHRYSLER ').replace(' MT ', ' MITSUBISHI ').replace(' PG ',' PEUGEOT ')
				nombre= nombre.replace(' JGO ', ' JUEGO ').replace(' CD ', ' CADILLAC ')

		resultados.append('"'+codigo+'","'+nombre +'","'+ marca +'","'+ marca_auto +'","'+ categoria +'","'+costo +'","' + modelo +'","'+ fotos+'","'+ anio +'","'+ notas +'"');
	except Exception as e:
		print('FALLO ---- > ' + URLLL)
		resultados.append('"'+URLLL+'"');
	
	return;


def rreplace(s, old, new, occurrence):
	li = s.rsplit(old, occurrence)
	return new.join(li)


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

def print_time( lista, filename):
	resultados = [];
	cant=1;
	cant2=1;
	for cat in lista:
		try:
			descargarCategoriaEspecifica22(cat.strip(), resultados);
		except:
			print(' Fallo ' + cat )
			resultados.append('"'+cat.strip()+'"');


		cant = cant + 1;
		cant2= cant2 + 1;
		
		if (cant2==5):
			saveFile(filename, resultados)
			print (  str(cant / len(lista) * 100)  + '% ' + filename) ;
			cant2=0;
	saveFile(filename, resultados)
	

files = [];
loadFile('file.txt', files);



files1 = [];
files2 = [];
files3 = [];
files4 = [];
files5 = [];
files6 = [];
files7 = [];
files8 = [];
files9 = [];
files10 = [];
files11 = [];
files12 = [];
files13 = [];
files14 = [];
files15 = [];
files16 = [];

longitud = len(files)
i=0;

while (i<longitud):

	if (i<longitud):
		files1.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files2.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files3.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files4.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files5.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files6.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files7.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files8.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files9.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files10.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files11.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files12.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files13.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files14.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files15.append(files[i]);	
		i=i+1;

	if (i<longitud):
		files16.append(files[i]);	
		i=i+1;



t1 = threading.Thread(target=print_time, args = (files1, 'resultados1.csv' ))
t1.start()

t2 = threading.Thread(target=print_time, args = (files2, 'resultados2.csv' ))
t2.start()

t3 = threading.Thread(target=print_time, args = (files3, 'resultados3.csv' ))
t3.start()

t4 = threading.Thread(target=print_time, args = (files4, 'resultados4.csv' ))
t4.start()

t5 = threading.Thread(target=print_time, args = (files5, 'resultados5.csv' ))
t5.start()

t6 = threading.Thread(target=print_time, args = (files6, 'resultados6.csv' ))
t6.start()

t7 = threading.Thread(target=print_time, args = (files7, 'resultados7.csv' ))
t7.start()

t8 = threading.Thread(target=print_time, args = (files8, 'resultados8.csv' ))
t8.start()

t9 = threading.Thread(target=print_time, args = (files9, 'resultados9.csv' ))
t9.start()

t10 = threading.Thread(target=print_time, args = (files10, 'resultados10.csv' ))
t10.start()

t11 = threading.Thread(target=print_time, args = (files11, 'resultados11.csv' ))
t11.start()

t12 = threading.Thread(target=print_time, args = (files12, 'resultados12.csv' ))
t12.start()

t13 = threading.Thread(target=print_time, args = (files13, 'resultados13.csv' ))
t13.start()

t14 = threading.Thread(target=print_time, args = (files14, 'resultados14.csv' ))
t14.start()

t15 = threading.Thread(target=print_time, args = (files15, 'resultados15.csv' ))
t15.start()

t16 = threading.Thread(target=print_time, args = (files16, 'resultados16.csv' ))
t16.start()

