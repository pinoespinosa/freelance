#!/bin/sh


for cat in 11
do

	LISTO=true;
	num=0

	while $LISTO ;
	do

		curl "http://www.radec.com.mx/catalogo?type=$cat&brand=&model=&year=&search=&new=&exist=&all=&acces=&sector=3073&enfri=&btn_search=Buscar&page=$num" -H 'Host: www.radec.com.mx' -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:54.0) Gecko/20100101 Firefox/54.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8' -H 'Accept-Language: en-US,en;q=0.5' --compressed -H 'Cookie: __utma=72389776.1460591499.1500578541.1500578541.1500584443.2; __utmz=72389776.1500578541.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); boost-gzip=true; SESS721296bc8a9b4f397fb8b90d35e8c32d=bkmsbtf39h62nto7n37hgi02h6; DRUPAL_UID=1970; has_js=1; __utmb=72389776.21.9.1500586663668; __utmc=72389776; __utmt=1' -H 'Connection: keep-alive' -H 'Upgrade-Insecure-Requests: 1' -H 'If-Modified-Since: Thu, 20 Jul 2017 22:30:40 GMT' > susp/"archi$cat-$num".txt
		
		STRING="Lo sentimos, pero";
		FILE="susp/archi$cat-$num.txt";

		if grep -q "$STRING" $FILE; then
 			LISTO=false;
 			echo 'es false';
 			rm $FILE;
 		else
			LISTO=true;
			echo 'es true';
 		fi
		num=$(( num+1 ))
		echo $num
	done

done

