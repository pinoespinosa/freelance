from URL_Lib import descargarResultadoData
from File_Lib import saveFile, loadFile

#
#
# ********************************** Programa principal **********************************
#
#


def descargarDatosYahoo(codigo):

	pagina = descargarResultadoData(codigo, 360, 10, '', '')
	pagina = pagina.find_all(class_='BdT Bdc($c-fuji-grey-c) Ta(end) Fz(s) Whs(nw)');

	fecha = 	pagina[0].prettify().split("<span data-reactid=\"51\">")[1].split("<")[0].strip()
	abrir = 	pagina[0].prettify().split("<span data-reactid=\"53\">")[1].split("<")[0].strip()	
	maxVal = 	pagina[0].prettify().split("<span data-reactid=\"55\">")[1].split("<")[0].strip()	
	minVal = 	pagina[0].prettify().split("<span data-reactid=\"57\">")[1].split("<")[0].strip()
	cierre = 	pagina[0].prettify().split("<span data-reactid=\"59\">")[1].split("<")[0].strip()
	cierreAj = 	pagina[0].prettify().split("<span data-reactid=\"61\">")[1].split("<")[0].strip()
	volumen = 	pagina[0].prettify().split("<span data-reactid=\"63\">")[1].split("<")[0].strip()

	file.append(fecha + ';' + abrir + ';' + maxVal + ';' + minVal + ';' + cierre + ';' + cierreAj + ';' + volumen)

	return;


file = [];

loadFile('GOOG.csv',file)
descargarDatosYahoo('https://es.finance.yahoo.com/quote/GOOG/history?p=GOOG')
saveFile('GOOG.csv',file)
