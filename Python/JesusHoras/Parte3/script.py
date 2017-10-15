import matplotlib.pyplot as plt; plt.rcdefaults()
import numpy as np
import tkinter as tk
from tkinter import filedialog


LISTA = []

# Read the file until the date starts to repeat
ListaFechasLeidas = []

try:
	# Open select dialog windows
	root = tk.Tk()
	root.withdraw()
	file_path = filedialog.askopenfilename()
	file = open(file_path, "r")

	cant =0;
	
	for line in file:
		if ( cant>0 and not line.split(",")[1] in ListaFechasLeidas):
			LISTA.append(line)
			ListaFechasLeidas.append(line.split(",")[1])
		cant=cant+1;
	file.close();
except (FileNotFoundError):
	print("File not found")

# Defining the tribial range to plot
minim = 99*99*99;
maxim = -1;

# Populating the graph with data and updating the min and max
performance = []
objects = []
for valor in LISTA:
	precio = float( (float(valor.split(",")[15]) + float(valor.split(",")[16]))/2 )
	if (precio<minim):
		minim=precio
	if (precio>maxim):
		maxim=precio
	performance.append(precio) 
	objects.append(valor.split(",")[1])
	
# Defining the graph
y_pos = np.arange(len(objects))
plt.bar(y_pos, performance, align='center', alpha=0.5)
plt.xticks(y_pos, objects, ha='right', rotation=45 )
#plt.ylabel(file_path)
#plt.title('Valores de las acciones')

# Defining the ranges
dif = (maxim - minim) / 4;
plt.ylim(ymin=minim - dif)
plt.ylim(ymax=maxim + dif)
plt.subplots_adjust(bottom=0.2)

plt.show(block=False)

input("Press ENTER to exist") 