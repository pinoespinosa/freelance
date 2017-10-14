#!/bin/sh

cp ../PASO_2/categorias_item_index.csv cat_indice_sin_rep.csv 
cp ../PASO_4/marca_item_index.csv mar_indice_sin_rep.csv 

sort -u cat_indice_sin_rep.csv -o cat_indice_sin_rep.csv
sort -u mar_indice_sin_rep.csv -o mar_indice_sin_rep.csv