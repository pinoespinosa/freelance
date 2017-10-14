import matplotlib.pyplot as plt; plt.rcdefaults()
import numpy as np


# Reading the 15th lines of CSV
LISTA = []
try:
	file = open("UnderlyingOptionsIntervalsQuotes_3600sec_2016-09-01.csv", "r")
	cant=0;
	for line in file:
		if cant<15 and cant>0:
			LISTA.append(line)
		cant=cant+1
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
plt.ylabel('Usage')
plt.title('Programming language usage')

# Defining the ranges
dif = (maxim - minim) / 4;
plt.ylim(ymin=minim - dif)
plt.ylim(ymax=maxim + dif)
plt.subplots_adjust(bottom=0.2)

plt.show()

