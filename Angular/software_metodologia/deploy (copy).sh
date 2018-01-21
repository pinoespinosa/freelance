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

scp -i "Efevisium.pem" ../freelance/Angular/software_metodologia/Front/dist/*  ubuntu@ec2-18-231-64-98.sa-east-1.compute.amazonaws.com:/home/ubuntu/apache-tomcat-8.5.14/webapps/metodologia-front
scp -i "Efevisium.pem" ../Desktop/metodologia-manager.war  ubuntu@ec2-18-231-64-98.sa-east-1.compute.amazonaws.com:/home/ubuntu/apache-tomcat-8.5.14/webapps/

