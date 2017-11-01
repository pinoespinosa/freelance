import matplotlib.pyplot as plt; plt.rcdefaults()
import numpy as np
import tkinter as tk
from tkinter import filedialog
import datetime

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
# Muestro por pantalla los vencimientos y le doy la posibilidad de elegir 1 al usuario, y muestro los posteriores
#
for aa in Vencimientos:
	print(aa)

valor = input("Escriba una fecha (YYYY-MM-DD): ");
file.close();

fecha = datetime.date(int(valor.split("-")[0]),int(valor.split("-")[1]), int(valor.split("-")[2]))
for aa in Vencimientos:

	venci = datetime.date(int(aa.split("-")[0]),int(aa.split("-")[1]), int(aa.split("-")[2]))
	if (venci > fecha):
		print (venci)


