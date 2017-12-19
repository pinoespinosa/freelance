<!DOCTYPE html>
<html>
<body>
<form action="webscrapper.php" method="post" enctype="multipart/form-data"  >   
    <input type="submit" value="Start scan gsmarena" name="submit1" style="width: 300px;" ><br>
    <input type="submit" value="Start scan phonearena --> Done" name="submit2" style="width: 300px;" ><br>
    <input type="submit" value="Start scan devicespecifications --> Done" name="submit3" style="width: 300px;" ><br>
    <input type="submit" value="Start scan whistleout --> Done" name="submit4" style="width: 300px;">

</form>
<br><br>

</body>
</html>



<?php

include 'configFile.php';

include 'printerXML.php';
include 'data_phoneData.php';
include 'data_whistleoutData.php';
include 'data_devicespecifications.php';

set_time_limit(0);
ini_set('max_execution_time', 30000);
date_default_timezone_set('America/Buenos_Aires');

define("SERVER_gsmarena", "http://www.gsmarena.com");
define("SERVER_phonearena", "https://www.phonearena.com");
define("SERVER_devicespecifications", "https://www.devicespecifications.com");
define("SERVER_whistleout", "https://www.whistleout.com");


# ------------- Error management -------------
function exception_error_handler($errno, $errstr, $errfile, $errline ){}
function catchException($e){}
set_exception_handler('catchException');
set_error_handler("exception_error_handler");


if(isset($_POST["submit1"])) {
    print 'Under construction ...';
}

if(isset($_POST["submit2"])) {
    $file2 = fopen( PHONERARENA_FILE_PATH , "w") or die("Unable to open file!");
	invokePhonearena('', $file2);
    fclose($file2);
}

if(isset($_POST["submit3"])) {
    $file3 = fopen( DEVICESPECIFICATIONS_FILE_PATH , "w") or die("Unable to open file!");
    invokeDevicespecifications($file3);
    fclose($file3);
}

if(isset($_POST["submit4"])) {
    $file4 = fopen( WHISLEOUT_FILE_PATH , "w") or die("Unable to open file!");
    invokeWhistleout($file4);
    fclose($file4);
}



function splitInValue($contenido, $inicio, $fin)
{
    $lista =  explode($inicio, $contenido);

    for($i=1; $i<count($lista); $i++){
        $lista[$i] =  explode($fin, $lista[$i])[0];
    }

    if(isset($lista[1])){
        $valor = $lista[1];
    }
    else {
        $valor = '';
    }


    return $valor;

}

function splitInList($contenido, $inicio, $fin)
{
    $lista =  explode($inicio, $contenido);

    for($i=1; $i<count($lista); $i++){
        $lista[$i] =  explode($fin, $lista[$i])[0];
    }

    return $lista;

}
?>

