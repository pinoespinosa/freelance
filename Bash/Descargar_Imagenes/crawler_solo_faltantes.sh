
cat lista.txt | while read LINE
do

B="$(echo $LINE | cut -d'/' -f16)"


if ! [ -s imagenes/$B ] ; then
	curl $LINE > imagenes/$B

fi


done