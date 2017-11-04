#  Tutorial		https://www.linuxtotal.com.mx/?cont=info_admon_006

HORA_EJECUCION="20"
HORA_ACTUAL="$(date +"%H")"

DIRECTORIO="/home/pino/freelance/Python/Jesus_Horas/Parte1"



if [ "$HORA_ACTUAL" == "$HORA_EJECUCION" ] ; then
	cd $DIRECTORIO
	python3 getDataAcciones.py
fi
