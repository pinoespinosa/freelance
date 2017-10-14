
import urllib2
import json
import glob
import re
from BeautifulSoup import BeautifulSoup as bs



def process( f , folder ):

	for num in glob.glob("../PASO_1/" + folder+"/*.txt"):
		print num

		with open(num) as lect:
			req=lect.read()


			for text in req.split("class=\"catalog-list-product"):

				if 'font-weight:normal' not in text:

					sap = text.split("width=100>")[1].split("</td>")[0].strip();
					url = 'http://www.radec.com.mx/sites/all/files/productos/' +  sap + '.jpg';

					des = text.split("img title=\"")[1].split("\" alt")[0].strip();
					pri = text.split("-price\">")[1].split("</div>")[0].strip();

					aplic = text.split("<div class=\"brands-models\">")[1].split("<td width=20 align=center")[0].replace("<img src='/sites/all/themes/radec/images/car-icon.png' align='absmiddle'>",";").replace("<img src='/sites/all/themes/radec/images/car-icon-white.png' align='absmiddle'>",";").replace("<br/>\"><img align=\"absmiddle\" src=\"/sites/all/themes/radec/images/bullet_add.png\"/> Ver m&aacute;s aplicaciones</a></div></div></td>","").replace("<div class=\"view_more_apps\"><a href=\"#\" class=\"tooltip apps\"","").replace("<br/>","").replace("</div></td>","");

					aplic2 = aplic.split("id=\"")[0];

					if "title=\"" in aplic:
						aplic2 = aplic.split("id=\"")[0] +  aplic.split("title=\"")[1];
	
					aplic2 = aplic2.replace(" ;",";").replace(" ;",";").replace(" ;",";").replace(";","",1).strip();

					f.write('("' + sap + '","' + url + '","' + des + '","' + pri + '","' + aplic2 + '"),\n')
#					f.write(sap + ';' + url + ';' + des + ';' + pri + '\n')

   	return


f = open('categorias_item_index.csv', 'w')

process(f, 'acces');		f.write('-----\n');		# Amarillo	2544
process(f, 'agotados');		f.write('-----\n');		# Verde 	2682
process(f, 'colision'); 	f.write('-----\n');		#			24.062
process(f, 'nuevos');		f.write('-----\n');		# Celeste	2407
process(f, 'radia');		f.write('-----\n');		# Rojo		1590
process(f, 'susp');									# Violeta 	4209

f.close();


# 32993 Sin repetidos
# 37499 Con repetidos
