from URL_Lib import descargarResultadoData
from File_Lib import saveFile, loadFile
import time
import json
from datetime import datetime
#
# ********************************** Programa principal **********************************
#
#

def parse(aa):
	fecha = aa["date"];
	abrir = aa["open"];
	maxVal = aa["high"];
	minVal = aa["low"];
	cierre = aa["close"];
	cierreAj = aa["adjclose"];
	volumen = aa["volume"];
	return str(datetime.utcfromtimestamp(fecha)) + ',' + str(abrir) + ',' + str(maxVal) + ',' + str(minVal) + ',' + str(cierre) + ',' + str(cierreAj) + ',' + str(volumen)

#
# Download share data from url, parse the json and save in csv file. 
#	
def descargarInfoAccion(url,accion):
	file = [];
	loadFile(accion + '.csv',file)
	data = descargarResultadoData(url, 360, 10, '', '')
	data = data.split("\"HistoricalPriceStore\":")[1].split("]")[0] + ']}'
	data = json.loads(data)  

	if (file):
		file.append(parse(data["prices"][0]))
	else:
		file.append("Date,Open,High,Low,Close,Adj Close,Volume")
		for precio in data["prices"]:
			try:
				file.append(parse(precio))
			except:
				print('')

	saveFile(accion + '.csv',file)
	return;


acciones = [];
loadFile('acciones.csv',acciones)

# Interates over the shares and update the file
for accion in acciones:
	tiempo = int(time.time())
	tiempo3Anio = int(time.time()) - 94694400 ;
	descargarInfoAccion('https://es.finance.yahoo.com/quote/'+accion+'/history?period1='+str(tiempo3Anio)+'&period2='+str(tiempo)+'&interval=1d&filter=history&frequency=1d',accion)



