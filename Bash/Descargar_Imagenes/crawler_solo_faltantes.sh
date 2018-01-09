
cat lista.txt | while read LINE
do

B="$(echo $LINE | cut -d'/' -f8)"


if ! [ -s $B ] ; then
	curl $LINE > $B

fi


done