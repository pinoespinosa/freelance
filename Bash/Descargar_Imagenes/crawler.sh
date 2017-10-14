
cat lista.txt | while read LINE
do

B="$(echo $LINE | cut -d'/' -f8)"

curl $LINE > $B
echo $B

if ! [ -s $B ] ; then
        rm -f $B
        curl http://www.radec.com.mx/sites/all/files/imagecache/product_list/productos/no_foto.jpg > $B

fi


done