<?php


function invokeDevicespecificationsData($url, $myfile, $brand)
{

    $url = str_replace("images/model/", "en/model/", $url);

    $info = file_get_contents( $url );
    $title =  splitInList($info,'<p>Model name of the device.</p></td><td>','</td>')[1];

    $description =  splitInValue($info,'<meta name="description" content="','" />');
    $camera =  splitInValue($info,'<b>Camera</b>:','<');
    $size =  splitInValue($info,'<b>Dimensions</b>:','<');
    $battery =  splitInValue($info,'<b>Battery</b>:','<');
    $speed =  splitInValue($info,'<b>CPU</b>:','<');


    printXML($myfile, $url, $title, $description, $brand, '', '', $size, $camera, $battery, $speed, '', '', '', '', '', '', '', '', '', '', '', '');

    flush();
    ob_flush();
}

function invokeDevicespecifications($myfile)
{
    $categories = file_get_contents( SERVER_devicespecifications);
    $categories =  splitInList($categories,'class="brand-listing-container-frontpage"','class="section-header"')[1];
    $categories =  splitInList($categories,'<a href="','</a>');

    for($i=1; $i<count($categories); $i++){

        $nextPage =  explode("\">", $categories[$i])[0];  
        print $nextPage;
        $brand = explode(">", $categories[$i])[1];
        flush();
        ob_flush();
        while ($nextPage){

            $listCellsPage = file_get_contents( $nextPage );
            $listCells =  splitInList($listCellsPage,'<div id="main">','model-listing-container-160')[1];
            $listCells =  splitInList($listCells,'data-src="','/80/main');

            for($j=1; $j<count($listCells); $j++){
                
                if (strpos($listCells[$j], 'model/') !== false) {          
                    print $listCells[$j] . '<br>';
                    flush();
                    ob_flush();
                    invokeDevicespecificationsData( $listCells[$j], $myfile, $brand );
                }
            }
        
            $nextPage = '';

        }
    }

    print '<br><br>';
}

?>