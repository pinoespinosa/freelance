from File_Lib import saveFile, loadFile
import time
import json
from datetime import datetime



#
# ********************************** Programa principal **********************************
#
#


notas = {};
lista5 = [];
loadFile("notas.csv",lista5);
for aa in lista5:
	if (not 'SKU' in aa):
		notas[aa.split(',')[0].strip()] = aa.split(',')[1].strip(); 


cat = {};
lista6 = [];
loadFile("cat.csv",lista6);
for aa in lista6:
		cat[aa.split(',')[0].strip()] = aa.split(',')[1].strip(); 



tab2 = {};
lista2 = [];
loadFile("tab2.csv",lista2);
for aa in lista2:
	if (not '"Id Producto"' in aa):
		tab2[aa.split(',')[2].strip()] = aa; 


tab1 = {};
lista1 = [];
loadFile("tab1.csv",lista1);
for aa in lista1:
	if (not '"Id Producto"' in aa):

		if (not aa.split(',')[2].strip() in tab2.keys()):
			tab1[aa.split(',')[2]] = aa;

	else:
		print(aa);


print('Cant Lista 1: ' + str(len(lista1)));
print('Cant Lista 2: ' + str(len(tab2)));
print('Cant Lista 1 - Lista 2: ' + str(len(tab1)));


lista82 = [];

for aa in tab1:
	valor = tab1[aa];

	codigo= valor.split(',')[1]; 
	sku= valor.split(',')[2];
	nombre= valor.split(',')[3]; 
	marca= valor.split(',')[6];
	precio= valor.split(',')[9];
	try:
		valor_catego = cat[valor.split(',')[14].strip()]
	except:
		valor_catego = 'vacio'
	
	valor_nota = notas[sku]

	lista82.append(codigo + ',' + sku + ',' + nombre + ',' + marca + ',' + valor_catego + ',,' + precio +  ',,,,,,' + valor_nota )

saveFile("resultado.csv",lista82);