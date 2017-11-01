from URL_Lib import descargarResultado
from File_Lib import saveFile, saveFileExc, loadFile
from bs4 import BeautifulSoup 	# pip install beautifulsoup4


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

def descargarCategoriaEspecifica(FILENOMBRE):
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
resultados = [];


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



files = [];
loadFile('files.txt', files);
cant=1;
cant2=1;
for cat in files:
	descargarCategoriaEspecifica('../../Bash/Descargar_Sitios_Paralela/' + cat.strip());
	print(cat + ' ' + str(cant) + '..82730');
	cant = cant + 1;
	cant2= cant2 + 1;

	if (cant2==50):
		saveFile('resultados.csv', resultados)
		cant2=0;