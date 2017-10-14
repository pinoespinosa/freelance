
import urllib2
import json
import glob
from BeautifulSoup import BeautifulSoup as bs



def process( f , folder ):

	for num in glob.glob(folder+"/*.txt"):

		with open(num) as lect:
		
			req=lect.read()

			print num

			sap = req.split("digo SAP:</b>")[1].split("</div>")[0].strip();
			url = 'http://www.radec.com.mx/sites/all/files/productos/' +  sap + '.jpg';
			des = req.split("img title=\"")[1].split("\" alt")[0].strip();
			pri = req.split("uc-price\">$")[1].split("</span")[0].strip();


			aplic = req.split("<b>Aplicaciones:</b><br/>")[1].split("<div class=\"add-to-cart\">")[0].replace("<img src='/sites/all/themes/radec/images/car_icon.gif' align='absmiddle'>",";");
			aplic2 = aplic.split("id=\"")[0];

			if "title=\"" in aplic:
				aplic2 = aplic.split("id=\"")[0] +  aplic.split("title=\"")[1];
	

			aplic2 = aplic2.replace("\n"," ").replace(" ;",";").replace(" ;",";").replace(";","",1).strip();

			aplic2 = aplic2.replace("			     			                "," ");
			aplic2 = aplic2.replace("					 			    			     			    			    				        "," ");

			aplic2 = aplic2.replace("<br/>","");

			while '	' in aplic2:
 				aplic2 = aplic2.replace("	"," ");
			
			while '  ' in aplic2:
 				aplic2 = aplic2.replace("  "," ");

			aplic2 = aplic2.replace("</div> </div>","");
 			aplic2 = aplic2.replace(" ;",";");

			f.write('("' + sap + '","' + url + '","' + des + '","' + pri + '","' + aplic2 + '"),\n')
#			f.write(sap + ';' + url + ';' + des + ';' + pri + '\n')

   	return


f = open('indiv_files.sql', 'w')

process(f, 'indiv_files');		f.write('-----\n');		# Amarillo	2544

f.close();

