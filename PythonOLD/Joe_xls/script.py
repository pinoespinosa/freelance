from __future__ import print_function
from os.path import join, dirname, abspath
from xlrd.sheet import ctype_text   
import xlrd
import xlwt
import sys


def readFile(xl_workbook, indice):
	xl_sheet = xl_workbook.sheet_by_index(indice)

	num_cols = xl_sheet.ncols   # Number of columns

	for row_idx in range(1, xl_sheet.nrows):    # Iterate through rows
		key = (xl_sheet.cell(row_idx, 0));
		key = str(key.value);
		descrip = str(xl_sheet.cell(row_idx, 1).value);
		descrip = unicode(descrip, errors='replace')

		cantidad = int(xl_sheet.cell(row_idx, 2).value);

		print(xl_sheet.cell(row_idx, 2));
			
		if (not config.get(key)):
			config[key] = str(cantidad) + '_' + descrip;
		else:
			valor = int(config.get(key).split("_")[0]);
			if (cantidad > valor):
				config[key] = str(cantidad) + '_' + descrip;


config = {}

reload(sys)
sys.setdefaultencoding('utf8')

# --------------------- Reading file ---------------------

fname =  'Existencia.xls'
xl_workbook = xlrd.open_workbook(fname)

print("Reading page 0")
readFile(xl_workbook,0)

print("Reading page 1")
readFile(xl_workbook,1)

# --------------------- Saving new file ---------------------

workbook = xlwt.Workbook()
sheet = workbook.add_sheet('Hoja1')




date_format = xlwt.XFStyle()
date_format.num_format_str =  '#,##0.00'

cant = 1;
for valor in config:
	sheet.write(cant, 0, valor)
	sheet.write(cant, 1, config.get(valor).split("_")[1])
	sheet.write(cant, 2, int(config.get(valor).split("_")[0]))
	cant = cant+1; 

sheet.write(0, 0, 'Material')
sheet.write(0, 1, 'Texto')
sheet.write(0, 2, 'Libre utiliz')

workbook.save('Existencia_Edited.xls')

