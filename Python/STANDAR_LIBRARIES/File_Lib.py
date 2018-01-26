
def saveFile(FILENAME, LISTA):
	file = open(FILENAME,"w") 
	for valor in LISTA:
		file.write(valor.strip() + '\n');        
	file.close();
	return

def saveFileExc(FILENAME, LISTA, LISTA_EXCLUIDA):
	file = open(FILENAME,"w") 
	for valor in LISTA:
		if (valor.strip() not in LISTA_EXCLUIDA):
			file.write(valor.strip() + '\n');        
	file.close();
	return

def loadFile(FILENAME, LISTA):
	try:
		file = open(FILENAME, "r")
		for line in file:
			LISTA.append(line.strip())
		file.close();
	except (FileNotFoundError):
		print('No se hallo el archivo ' + FILENAME);
	return

































