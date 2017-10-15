import matplotlib.pyplot as plt; plt.rcdefaults()
import numpy as np
import tkinter as tk
from tkinter import filedialog



#
# Busca el strike menor y el mayor mas cercanos a un valor.
#
def getMinMaxStrike(typeShare, promedio, LISTA):
	done = False;
	pos=1
	fechaPrevi = ''
	filaMin = ''
	filaMax = ''
	while (pos < len(LISTA) and not done ):
		fecha_aux = LISTA[pos].split(",")[5] + '_' + LISTA[pos].split(",")[4]		
		if (float(LISTA[pos].split(",")[4]) < promedio):
			if (fechaPrevi != fecha_aux and LISTA[pos].split(",")[5] == typeShare) :
				fechaPrevi = fecha_aux
				filaMin = LISTA[pos];
		else:
			if (LISTA[pos].split(",")[5] == typeShare):
				done=True
				filaMax = LISTA[pos];
		pos = pos + 1

	return filaMin + '_' + filaMax



#
# Abro el cuadro de seleccion de archivo y almaceno todos los vencimientos
#
LISTA = []
Vencimientos = []
try:
	root = tk.Tk()
	root.withdraw()
	file_path = filedialog.askopenfilename()
	file = open(file_path, "r")

	cant =0;
	
	for line in file:
		if ( cant>0 and not line.split(",")[3] in Vencimientos):
			Vencimientos.append(line.split(",")[3])
		cant=cant+1;
		LISTA.append(line.strip());
	
except (FileNotFoundError):
	print("File not found")

#
# Muestro por pantalla los vencimientos y le doy la posibilidad de elegir 1 al usuario
#
pos = 0;
for aa in Vencimientos:
	print(str(pos) + '- ' + aa)
	pos = pos + 1;

valor = int(input("Seleccione el vencimiento: "));
valor = Vencimientos[valor];
print(valor)
file.close();


#
# Tomo el primer valor para la fecha seleccionada y genero el valor promedio para las call y puts
#
done = False;
valorCall = ''
valorPut = ''
pos = 0;
while (pos < len(LISTA) and  (not valorPut or not valorCall) ):
	
	if (LISTA[pos].split(",")[3] ==  valor):
	
		if (LISTA[pos].split(",")[5] == 'C'):
			done = True
			valorCall = LISTA[pos];
	
		if (LISTA[pos].split(",")[5] == 'P'):
			done = True
			valorPut = LISTA[pos];
	pos = pos + 1

promedioCall = float(valorCall.split(",")[15])/2 + float(valorCall.split(",")[16])/2
promedioPut = float(valorPut.split(",")[15])/2 + float(valorPut.split(",")[16])/2
print()
print('Promedio Call: ' + str(promedioCall))
print('Promedio Put: ' + str(promedioPut))


#
# Busco el mayor y menor strike mas cercanos
#
valor = getMinMaxStrike('C', promedioCall, LISTA);
filaCallMin = valor.split("_")[0];
filaCallMax = valor.split("_")[1];

valor = getMinMaxStrike('C', promedioPut, LISTA);
filaPutMin = valor.split("_")[0];
filaPutMax = valor.split("_")[1];



print('---------------------- CALL ------------------------\n')
print ('Inferior' + '\n' + filaCallMin + '\n')
print ('Superior' + '\n' + filaCallMax + '\n')
print('---------------------- PUTS ------------------------\n')
print ('Inferior' + '\n' + filaPutMin + '\n')
print ('Superior' + '\n' + filaPutMax + '\n')
