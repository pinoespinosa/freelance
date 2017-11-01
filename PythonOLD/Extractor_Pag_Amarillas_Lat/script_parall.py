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


def descargarCategorias():

	LETRAS = ['a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'];

	for letra in LETRAS:

		indice = 1;
		nuevos = 1;
		while (nuevos>0):
			nuevos = 0;

			pagina = descargarResultado('http://www.paginasamarillas.com.co/listado-de-secciones/' + letra + '/' + str(indice), 360, 10)
		
			if (pagina):
				pagina = pagina.find_all(class_='companyList')[0].find_all('a')
				indice = indice + 1;

				for aa in pagina:
					categoryLinks.append(aa['href'])
					nuevos = nuevos + 1; 

	saveFile('categoryLinks.txt', categoryLinks)
	return;

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

def descargarCategoriaEspecifica(FILENOMBRE, resultados):
	indice = 1;
	nuevos = 1;
	while (nuevos>0):
		nuevos = 0;

		resultado = loadFiless(FILENOMBRE);

		resultado = BeautifulSoup(resultado, 'html.parser');


		try:
			resultado = resultado.find_all(class_='figuration');
		except:
			resultado = [];
#			print(cat.strip() + ' ---> Vacia');

#		if (resultado):
#			print(FILENOMBRE);

		for nego in resultado:
			nuevos=nuevos+1;
			
			titulo = nego.find_all(class_='titleFig')[0].find_all('a')[0].text.replace(";","");
			
			try:
				email  = nego.prettify().split('data-email="')[1].split('" data-home')[0].replace(";","");
			except:
				email='';
			
			try:
				telefono = nego.find_all(class_='infoContact')[0].find_all('span')[0].text.replace(";","");
			except:
				telefono='';

			try:
				direccion = nego.find_all(class_='infoContact')[0].find_all('div')[0].text.replace(";","");
			except:
				direccion='';


			try:
				categoria = nego.find_all(class_='seoSearch')[0].text.replace(";","");
			except:
				categoria='';

			result = '"' + titulo + '";"' + email + '";"' + telefono + '";"' + direccion + '";"' + categoria +'"';
			#print(result );

			if (result.strip() not in resultados):
				resultados.append(result.strip());

		indice=indice+1;
		nuevos=-1;
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

def print_time( lista, filename):
	
	
		resultados = [];
		cant=1;
		cant2=1;
		for cat in lista:
			try:
				descargarCategoriaEspecifica('../../Bash/Descargar_Sitios_Paralela2/' + cat.strip(), resultados);
			except:
				print('Errorcirijillo');
#			print(cat + ' ' + filename);
			
			cant = cant + 1;
			cant2= cant2 + 1;
		
			if (cant2==50):
				print (str((cant/4600)*100) + '%');
				saveFile(filename, resultados)
				cant2=0;



files = [];
loadFile('files.txt', files);



files1 = [];
files2 = [];
files3 = [];
files4 = [];
files5 = [];
files6 = [];
files7 = [];
files8 = [];


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


