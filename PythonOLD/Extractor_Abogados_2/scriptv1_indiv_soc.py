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
	pagina = descargarResultadoData('http://www.coamalaga.es/colegio/quienessomos/colegiados/datoscolegiados.asp?idArqSoc='+codigo.split("\";\"")[0]+'&rnd=0.4214829040690903', 360, 10, payload, headers)
	
#	print (pagina);

	try:
		email = str(pagina).split("<![CDATA[")[1].split("]")[0].replace(";",",").replace("\"","").strip();
	except:
		email='';

	try:
		direccion = str(pagina).split("direccion")[1].split("<![CDATA[")[1].split("]")[0].replace(";",",").replace("\\xaa","ma ").replace("\"","").replace("<br>"," ").strip();
	except:
		direccion='';

	try:
		numerocolegiado = str(pagina).split("<numerocolegiado>")[1].split("</numerocolegiado>")[0].replace(";",",").replace("\"","").strip();
	except:
		numerocolegiado='';

	try:
		localidad = str(pagina).split("direccion")[1].split("<![CDATA[")[1].split("]")[0].split("-")[1].split("(")[0].replace(";",",").replace("\\xaa","ma ").replace("\"","").replace("<br>"," ").strip();
	except:
		localidad='';

	try:
		telefono = str(pagina).split("telefono")[1].split("<![CDATA[")[1].split("]")[0].replace(";",",").replace("\\xaa","ma ").replace("\"","").replace("<br>"," ").strip();
	except:
		telefono='';

	try:
		email2 = pagina.split("E-mail")[1].split("</span>")[1].split("<span>")[1].split(">")[1].split("<")[0].strip();
	except:
		email2='';


	print('"' + numerocolegiado + '";"' + codigo.split("\";\"")[1] + '";"' + direccion + '";"' + localidad + '";"' + telefono + '";"' + email + '";"' )
	finales.append('"' + numerocolegiado + '";"' + codigo.split("\";\"")[1] + '";"' + direccion + '";"' + localidad + '";"' + telefono + '";"' + email + '";')
	saveFile('resultadosFinal_SOC.csv', finales)

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
loadFile('resultadosFinal_Sociedades.csv', lista);

finales = [];
for link in lista:
	descargarCategorias(link.strip())
