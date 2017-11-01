from URL_Lib import descargarResultadoData
from File_Lib import saveFile, saveFileExc, loadFile
import re

#
#
# ********************************** Programa principal **********************************
#
#


def descargarCategorias(codigo):

	payload = "lang=E&codi="+codigo
	headers = {	'content-type': "application/x-www-form-urlencoded" }

	pagina2 = descargarResultadoData('http://www.coamalaga.es/colegio/quienessomos/colegiados/colegiados.asp?Apellidos=&Localidad='+codigo+'&TipoBusqueda=2&rnd=0.5768493866276709', 360, 10, payload, headers)
	

	pagina2 = pagina2.find_all(class_='link');

	for pagina in pagina2:

		try:
			name = pagina.text;
		except:
			name='';

		try:
			matri = pagina['id'].replace("divColegiado_","");
		except:
			matri='';

		try:
			dire = pagina.split(">-->")[1].split("</span>")[0].replace(";",",").strip();
		except:
			dire='';

		try:
			tel = pagina.split("Tel")[1].split("</span>")[0].split("<span>")[1].strip();
		except:
			tel='';

		try:
			email1 = pagina.split("E-mail")[1].split("</span>")[0].split(">")[3].split("<")[0].strip();
		except:
			email1='';

		try:
			email2 = pagina.split("E-mail")[1].split("</span>")[1].split("<span>")[1].split(">")[1].split("<")[0].strip();
		except:
			email2='';


		if (matri):
			print('"' + name + '";"' + matri)
			finales.append(matri + '";"' + name)
			saveFile('resultadosFinal_Sociedades.csv', finales)

	return;


def descargarCategoriaEspecifica():

	pag = 1;
	cant = 1;
	
	while (cant>0):
		cant = 0;
		payload = "lang=E&pagina="+str(pag)+"&arqSOC=arq"

		headers = {	'content-type': "application/x-www-form-urlencoded" }

		pagina = descargarResultadoData('https://www.arquitectes.cat/iframes/arquitectes/arquitectes/llistat.php', 360, 10, payload, headers)
		pagina = pagina.find_all(class_='item_list');

		for link in pagina:
			cant = cant + 1
			lista.append(link.prettify().strip().split("codi=")[1].split("&amp")[0]);

		print(pag)
		pag = pag + 1
		saveFile('resultadosIndiv.csv', lista)
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




lista = [];
loadFile('resultadosIndiv.csv', lista);

finales = [];
for link in lista:
	descargarCategorias(link.strip())
