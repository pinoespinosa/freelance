#!/bin/bash

cd Front
ng build --prod --aot=false
cd dist

REMP="metodologia-front\/"

REMP1="polyfills"
sed -i "s/$REMP1/$REMP$REMP1/g" index.html

REMP2="vendor"
sed -i "s/$REMP2/$REMP$REMP2/g" index.html

REMP3="main"
sed -i "s/$REMP3/$REMP$REMP3/g" index.html

REMP4="styles\."
sed -i "s/$REMP4/$REMP$REMP4/g" index.html

REMP5="assets"
sed -i "s/$REMP5/$REMP$REMP5/g" index.html

REMP6="inline"
sed -i "s/$REMP6/$REMP$REMP6/g" index.html


cd ..
cd 
cd Downloads

