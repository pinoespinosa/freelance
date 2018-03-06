from URL_Lib import descargarResultado
from File_Lib import saveFile, saveFileExc, loadFile
from bs4 import BeautifulSoup 	# pip install beautifulsoup4

import threading
import _thread
import time

#
#
# ********************************** Programa principal **********************************
#
#


	
files = [];
loadFile('resultados1.csv', files);
loadFile('resultados2.csv', files);
loadFile('resultados3.csv', files);
loadFile('resultados4.csv', files);
loadFile('resultados5.csv', files);
loadFile('resultados6.csv', files);
loadFile('resultados7.csv', files);
loadFile('resultados8.csv', files);
loadFile('resultados9.csv', files);
loadFile('resultados10.csv', files);
loadFile('resultados11.csv', files);
loadFile('resultados12.csv', files);
loadFile('resultados13.csv', files);
loadFile('resultados14.csv', files);
loadFile('resultados15.csv', files);
loadFile('resultados16.csv', files);

saveFile('compiilado.csv', files)




