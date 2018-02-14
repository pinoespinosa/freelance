#!/bin/bash

cd Front
ng build --prod --aot=false --output-hashing=all
cd dist




cd ..
cd 
cd Downloads

#scp -i "Efevisium.pem" ../freelance/Angular/software_metodologia/Front/dist/*  ubuntu@ec2-52-67-241-219.sa-east-1.compute.amazonaws.com:/home/ubuntu/apache-tomcat-8.5.14/webapps/metodologia-front

