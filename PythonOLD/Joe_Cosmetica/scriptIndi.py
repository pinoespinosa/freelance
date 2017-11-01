
import urllib2
import json
import glob
from BeautifulSoup import BeautifulSoup as bs


	

def rreplace(s, old, new, occurrence):
  li = s.rsplit(old, occurrence)
  return new.join(li)

def process( f , folder ):

	for num in glob.glob('/home/pino/Freelance/Python/Joe_Cosmetica/nuevo/*'):

		print(num);
		with open(num) as lect:
		
			req=lect.read()

			try:
				code = req.split("<h1>")[1].split("</h1>")[0].strip();
			except:
				code ="";
			if (not code):
				code = req.split("<div class=\"title\">")[1].split("<")[0].strip();
				
			web = req.split("<meta property=\"og:url\" content=\"")[1].split("\"")[0].strip();
				
			








			try:
				colores = req.split("<div class=\"colors\">")[1].split("<div class=\"sizes\">")[0].strip();

				coloreee='';
				for color in colores.split("<div class=\"color-box\" style=\"background: "):
					if ('#' in color):
						coloreee = coloreee + color.split("\"")[0] + '\n';

			except:
				coloreee ="";



			
			if coloreee.endswith('\n'):
				coloreee=rreplace(coloreee, '\n', '', 1)

			if (not coloreee):
				coloreee='-'


			try:
				talles = req.split("<div class=\"sizes\">")[1].split("</select>")[0].strip().split(">");

				tallessss='';
				for tal in talles:

					if (tal.split("<")[0].strip()):
						tallessss = tallessss + tal.split("<")[0].strip() + '\n';

			except:
				tallessss ="";



			
			if tallessss.endswith('\n'):
				tallessss=rreplace(tallessss, '\n', '', 1)

			if (not tallessss):
				tallessss='-'



			try:
				galeria = req.split("<div class=\"gallery-container\">")[1].split("<div class=\"product-image\"")[0].strip();
			except:
				galeria='';

			try:
				foto1 = galeria.split("style=\"background: url(")[1].split(")")[0].strip();
			except:
				foto1='';
			if (not foto1):
				try:
					galeria2 = req.split("class=\"package-item-container")[1].split("class=\"selected-detail-container")[0].strip();
					foto1 = galeria2.split("url(&quot;")[1].split("&quot;)")[0].strip();
				except:
					foto1='-';

			try:
				foto2 = galeria.split("style=\"background: url(")[2].split(")")[0].strip();
			except:
				foto2='';

			if (not foto2):
				try:
					galeria2 = req.split("class=\"package-item-container")[1].split("class=\"selected-detail-container")[0].strip();
					foto2 = galeria2.split("url(&quot;")[2].split("&quot;)")[0].strip();
				except:
					foto2='-';
				
			try:
				foto3 = galeria.split("style=\"background: url(")[3].split("'")[0].strip();
			except:
				foto3='';


			if (not foto3):
				try:
					galeria2 = req.split("class=\"package-item-container")[1].split("class=\"selected-detail-container")[0].strip();
					foto3 = galeria2.split("url(&quot;")[3].split("&quot;)")[0].strip();
				except:
					foto3='-';

			try:
				foto4 = galeria.split("style=\"background: url('")[4].split("'")[0].strip();
			except:
				foto4='-';

			try:
				foto5 = galeria.split("style=\"background: url('")[5].split("'")[0].strip();
			except:
				foto5='-';

			try:
				foto6 = galeria.split("style=\"background: url('")[6].split("'")[0].strip();
			except:
				foto6='-';

			try:
				foto7 = galeria.split("style=\"background: url('")[7].split("'")[0].strip();
			except:
				foto7='-';

			try:
				foto8 = galeria.split("style=\"background: url('")[8].split("'")[0].strip();
			except:
				foto8='-';

			try:
				foto9 = galeria.split("style=\"background: url('")[9].split("'")[0].strip();
			except:
				foto9='-';

			try:
				foto10 = galeria.split("style=\"background: url('")[10].split("'")[0].strip();
			except:
				foto10='-';

			try:
				foto11 = galeria.split("style=\"background: url('")[11].split("'")[0].strip();
			except:
				foto11='-';

			try:
				foto12 = galeria.split("style=\"background: url('")[12].split("'")[0].strip();
			except:
				foto12='-';

			try:
				foto13 = galeria.split("style=\"background: url('")[13].split("'")[0].strip();
			except:
				foto13='-';

			try:
				foto14 = galeria.split("style=\"background: url('")[14].split("'")[0].strip();
			except:
				foto14='-';

			try:
				foto15 = galeria.split("style=\"background: url('")[15].split("'")[0].strip();
			except:
				foto15='-';

			foto16='-';


			try:
				contenido = req.split("<div class=\"info\">")[2].split("<div class=\"recommended\">")[0].strip();
				
				desc = contenido.split("<p")[3].split("</p>")[0].strip();
				desc = desc.replace(">","");
				print(code);
				print(desc);
		
			except:
				desc = '-';






			try:
				contenido = req.split("<div class=\"info\">")[2].split("<div class=\"recommended\">")[0].strip();
				
				coodigo = contenido.split("<p")[1].split("</p>")[0].strip();
				coodigo = coodigo.replace(">","");
				coodigo = coodigo.replace("class=\"accent key\"","");

			except:
				coodigo = '-';


			try:
				contenido = req.split("<div class=\"info\">")[2].split("<div class=\"recommended\">")[0].strip();
				
				precio = contenido.split("<p")[4].split("</p>")[0].strip();
				precio = precio.replace(">","");
				precio = precio.replace("class=\"accent key\"","");
				precio = precio.replace("class=\"price\"","");

			except:
				precio = '-';







			filatabla =  '"' + num + '","' + coodigo + '","'  + precio + '","' + code + '","' + desc + '","' + tallessss + '","' + coloreee + '","'+ web + '","' + foto1 + '","' + foto2 + '","' + foto3 + '","' + foto4 + '","' + foto5 + '","' + foto6 + '","' + foto7 + '","' + foto8 + '","' + foto9 + '","' + foto10 + '","' + foto11 + '","' + foto12 + '","' + foto13 + '","' + foto14 + '","' + foto15 + '","' + foto16 + '"\n';

			f.write(filatabla.replace("'","")); 	 



	return


f = open('marca_item_index.csv', 'w')
f.write( '"FILE","CODIGO","PRECIO","TITULO","DESCRIPCION","TALLES","COLORES","URL","FOTO1","FOTO2","FOTO3","FOTO4","FOTO5","FOTO6","FOTO7","FOTO8","FOTO9","FOTO10","FOTO11","FOTO12","FOTO13","FOTO14","FOTO15","FOTO16"\n'); 	 

process(f, '');		

f.close();


# 32993 Sin repetidos
# 37499 Con repetidos
