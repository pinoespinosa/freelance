from URL_Lib import descargarResultadoData
from File_Lib import saveFile, loadFile
#
#
# ********************************** Programa principal **********************************
#
#



def rreplace(s, old, new, occurrence):
	li = s.rsplit(old, occurrence)
	return new.join(li)

file = [];
fileBase = [];

hashtable = {};

loadFile('cvsBase.csv',fileBase)

hashtable_by_nombre = {};
hashtable_by_sku = {};


cant = 0;

for aa in fileBase:

	nombre = aa.split(",")[1].replace("\"","");
	sku = aa.split(",")[0].replace("\"","");

	if nombre.endswith(' DER'):
		nombre1 = rreplace(nombre, ' DER', '', 1)
		
		if (nombre1 in hashtable_by_nombre):
			hashtable_by_sku[sku]= hashtable_by_nombre.get(nombre1);
		else:
			hashtable_by_nombre[nombre1] = cant;
			hashtable_by_sku[sku]= cant;
			cant = cant + 1;

	if nombre.endswith(' IZQ'):
		nombre2 = rreplace(nombre, ' IZQ', '', 1)
		
		if (nombre2 in hashtable_by_nombre):
			hashtable_by_sku[sku]= hashtable_by_nombre.get(nombre2);
		else:
			hashtable_by_sku[sku]= cant;
			hashtable_by_nombre[nombre2] = cant;
			cant = cant + 1;

	if (not nombre.endswith(' IZQ') and not nombre.endswith(' DER')):

		if (nombre in hashtable_by_nombre):
			hashtable_by_sku[sku]= hashtable_by_nombre.get(nombre);
		else:
			hashtable_by_sku[sku]= cant;
			hashtable_by_nombre[nombre] = cant;
			cant = cant + 1;


tofile = [];




for uu in fileBase:

	nombre = uu.split(",")[1].replace("\"","");
	sku = uu.split(",")[0].replace("\"","");

	tofile.append( str(hashtable_by_sku.get(sku)) + ',' +  uu)

saveFile('pino.txt',tofile)