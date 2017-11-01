
import urllib2
import json
import glob
from BeautifulSoup import BeautifulSoup as bs



def process( f , folder ):

	for num in glob.glob('/home/pino/Freelance/Python/Joe_Cosmetica/*\\*'):

		with open(num) as lect:
		
			req=lect.read()

			for text in req.split("class=\"product item"):

				print(num)

				if 'item-image' in text:

						code = text.split("class=\"accent key\">")[1].split("</p>")[0].strip();
						url = text.split("background: url('")[1].split("')")[0].strip();
						nombre = text.split("<h1")[1].split(">")[1].split("</h1")[0].replace("&#39;","'").strip();
						try:
							precio = text.split("class=\"price btn\">")[1].split("<")[0].strip();
						except:
							precio ="";

						try:
							link = text.split("<a class=\"link\" href=\"")[1].split("\"")[0].strip();
						except:
							link ="";

						if (not precio):
							try:
								precio = text.split("btn\">")[1].split("<")[0].strip();
							except:
								precio ="";

						print(code)
						print(nombre)
						print(precio)
						print(url)

						print('')

#						f.write('"' + code + '","'+ nombre + '","'+ precio + '","'+ url + '","'+ num.split("Joe_Cosmetica/")[1]  + '"\n')
						if (link):
							f.write('http://chemisette.com.mx' + link  + '\n')
#					f.write(sap + ';' + url + ';' + des + ';' + pri + '\n')

	return


f = open('marca_item_index.csv', 'w')

process(f, '');		
f.write('-----\n');		# Amarillo	2544

f.close();


# 32993 Sin repetidos
# 37499 Con repetidos
