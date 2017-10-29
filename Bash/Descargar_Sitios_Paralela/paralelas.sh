

URL_LIST=$(cat "categoryLinks.txt")
ARCHIVO="$(echo categoryLinks.txt | cut -d'/' -f10)"


COUNTER=1
while [ -s $ARCHIVO ]; do
	
	echo $URL_LIST | xargs -n 1 -P 50 wget -q
    ls | grep "Page=$COUNTER" | sed 's/^/http:\/\/www.paginasamarillas.com.co\/servicios\//' > categoryLinks.txt

	COUNTERA=$(($COUNTER + 1))
	echo $COUNTERA

	sed -i "s/$COUNTER/$COUNTERA/g" categoryLinks.txt

    URL_LIST=$(cat "categoryLinks.txt")
	ARCHIVO="$(echo categoryLinks.txt | cut -d'/' -f10)"

	COUNTER=$COUNTERA

done

