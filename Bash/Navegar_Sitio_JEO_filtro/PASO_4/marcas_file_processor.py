
import urllib2
import json
import glob
from BeautifulSoup import BeautifulSoup as bs



def process( f , folder ):

	for num in glob.glob(folder+"/*.txt"):

		with open(num) as lect:
		
			req=lect.read()

			print num

			for text in req.split("class=\"catalog-list-product"):

				if 'font-weight:normal' not in text:

					sap = text.split("width=80>")[1].split("</td>")[0].strip();
					url = 'http://www.radec.com.mx/sites/all/files/productos/' +  sap + '.jpg';

					des = text.split("img title=\"")[1].split("\" alt")[0].strip();
					pri = text.split("-price\">")[1].split("</div>")[0].strip();

					f.write('("' + sap + '","' + url + '","' + des + '","' + pri + '"),\n')
#					f.write(sap + ';' + url + ';' + des + ';' + pri + '\n')

   	return


f = open('marca_item_index.csv', 'w')

process(f, '../PASO_3/files');		f.write('-----\n');		# Amarillo	2544

f.close();


# 32993 Sin repetidos
# 37499 Con repetidos
