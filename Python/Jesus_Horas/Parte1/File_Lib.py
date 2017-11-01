
def saveFile(FILENAME, LISTA):
	file = open(FILENAME,"w") 
	for valor in LISTA:
		file.write(valor.strip() + '\n');        
	file.close();
	return

def loadCreateFile(FILENAME, LISTA):
	try:
		file = open(FILENAME, "r")
		for line in file:
			LISTA.append(line)
		file.close();
	except (FileNotFoundError):
			file = open(FILENAME,"w") 
			for valor in LISTA:
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
		print('')
	return






























