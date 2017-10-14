<?php

function invokehistleoutData($url, $myfile)
{

    fwrite($myfile, "      <cellphone>\n");


    $url =  splitInList($url,'<a href="','"')[1];
    $contenido = file_get_contents(SERVER_whistleout . $url );
    printXML($myfile, 'whistleoutData.com', SERVER_whistleout . $url);

    $image =  splitInList($contenido,'class="[ col-xs-8 ] [ col-sm-7 ] [ col-md-8 ]"','</a>')[1];
    $image =  splitInList($image,'src="','"')[1];
    $brand =  splitInValue($contenido,'<li><a href="/CellPhones/Phones/','">');
    $devicename =  splitInValue(splitInValue($contenido,'<h1 class="font-800 font-8 font-7-xs font-9-lg mar-0">','</h1>'),'<span>','</span>');
 	printXML_BASICS($myfile, $image, $brand, $devicename, '', '', '');

    $technology =  splitInValue(splitInValue($contenido,'<th class="col-xs-7  col-sm-8">Main Connectivity</th>','</tr>'),'<td>','</td>');
    printXML_NETWORK($myfile, $technology, '', '', '', '', '', '');

    $launch_date =  splitInValue(splitInValue($contenido,'<th>Release Date</th>','</tr>'),'<td>','</td>');
	printXML_LAUNCH($myfile, '', '', $launch_date, '');

    $width =  splitInValue(splitInValue($contenido,'<th>Width</th>','</tr>'),'<td>','</td>');
    $heigth =  splitInValue(splitInValue($contenido,'<th>Height</th>','</tr>'),'<td>','</td>');
    $dimensions =  $width . ' x ' . $heigth;
    $weight =  splitInValue(splitInValue($contenido,'<th>Weight</th>','</tr>'),'<td>','</td>');
	printXML_BODY($myfile, $dimensions, $weight, '', '', '', '', '', '');

    $type =  splitInValue(splitInValue($contenido,'<th class="col-xs-7 col-sm-8">Type</th>','</tr>'),'<td>','</td>');
    $size =  splitInValue(splitInValue($contenido,'<th>Screen Size</th>','</tr>'),'<td>','</td>');
    $resolution =  splitInValue(splitInValue($contenido,'<th>Screen Resolution</th>','</tr>'),'<td>','</td>');
	printXML_DISPLAY($myfile, $type, $size, '', $resolution, '', '', '', '', '', '');

    $cpu =  splitInValue(splitInValue($contenido,'<th>Processor Type</th>','</tr>'),'<td>','</td>');
    $operativeSystem =  splitInValue(splitInValue($contenido,'<th>Operating System</th>','</tr>'),'<td>','</td>');
	printXML_PLATFORM($myfile, $operativeSystem, '', '', '', $cpu, '');

    $ram =  splitInValue(splitInValue($contenido,'<th class="col-xs-7  col-sm-8">RAM</th>','</tr>'),'<td>','</td>');
    $maximum_user_storage =  splitInValue(splitInValue($contenido,'<th>Internal</th>','</tr>'),'<td>','</td>');
	printXML_MEMORY($myfile, '', '', $ram, '', $maximum_user_storage);

    $primary =  splitInValue(splitInValue($contenido,'<th class="col-xs-7  col-sm-8">Resolution</th>','</tr>'),'<td>','</td>');
    $secondary =  splitInValue(splitInValue($contenido,'<th>Front Facing</th>','</tr>'),'<td>','</td>');
    $flash =  splitInValue(splitInValue($contenido,'<th>Flash Type</th>','</tr>'),'<td>','</td>');
    $video_resolution =  splitInValue(splitInValue($contenido,'<th>Video Camera</th>','</tr>'),'<td>','</td>');
	printXML_CAMERA($myfile, $primary, '', '', '', $flash, '', $video_resolution, '', $secondary);

    printXML_SOUND($myfile, '', '', '', '', '');
    
    $wlan =  splitInValue(splitInValue($contenido,'<th>WiFi</th>','</tr>'),'<td>','</td>');
    $bluetooth =  splitInValue(splitInValue($contenido,'<th>Bluetooth</th>','</tr>'),'<td>','</td>');
    $GPS =  splitInValue(splitInValue($contenido,'<th class="col-xs-7  col-sm-8">GPS</th>','</tr>'),'<td>','</td>');
    $radio =  splitInValue(splitInValue($contenido,'<th>FM Radio</th>','</tr>'),'<td>','</td>');
    $USB =  splitInValue(splitInValue($contenido,'<th>USB</th>','</tr>'),'<td>','</td>');
    printXML_COMMS($myfile, $wlan, $bluetooth, $GPS, '', $radio, $USB);

	printXML_FEACTURES($myfile, '', '', '', '', '', '', '', '', '', '', '', '');



    $talk_time_3G =  splitInValue(splitInValue($contenido,'<th>Battery (3G Talk)</th>','</tr>'),'<td>','</td>');
    $stand_by_time_2g =  splitInValue(splitInValue($contenido,'<th>Battery (Standby)</th>','</tr>'),'<td>','</td>');

    printXML_BATTERY($myfile, $mAh, $fast_charge_technology, $removable, $batterytype, $talk_time_3G, $music_play, $stand_by_time_2g, $internet_use_3g, $internet_use_LTE, $internet_use_Wi_Fi, $video_playback);
	printXML_MISC($myfile, '', '', '', '');
	printXML_POPULARITY($myfile, '', '');
	printXML_TEST($myfile, '', '', '', '', '', '', '');



    fwrite($myfile, "      </cellphone>\n");


    flush();
    ob_flush();
}


function invokeWhistleout($myfile)
{

    fwrite($myfile, "<?xml version=\"1.0\"?>\n");
    fwrite($myfile, "    <catalog>\n");

        $totalPagina = file_get_contents( 'https://www.whistleout.com/CellPhones/Phones/Finder');
        $totalPagina = intval(splitInList($totalPagina,'data-results-count="','"')[1]);

        $cantidad = 0;
        $nextPage = SERVER_whistleout . '/Ajax/MobilePhones/PhoneSearchResults/PagedResults?&current=0';
        flush();
        ob_flush();
        while ($nextPage){

            print '<br>' . ($cantidad / $totalPagina *100 ) . '%<br><br>';

            $listCellsPage = file_get_contents( $nextPage );

            if($listCellsPage){
                $listCells =  splitInList($listCellsPage,'<h2 class="mar-0 mar-b-3">','</h2>');

                for($j=1; $j<count($listCells); $j++){
                
                    print $listCells[$j] . '<br>';
                    flush();
                    ob_flush();
                    invokehistleoutData( $listCells[$j] , $myfile);
                }
            }
            $cantidad = $cantidad + 20;

            if ($totalPagina > $cantidad){
                $nextPage = SERVER_whistleout . '/Ajax/MobilePhones/PhoneSearchResults/PagedResults?&current=' . $cantidad ;
            }
            else
            {
                $nextPage = '';
            }
        }
    
    fwrite($myfile, "    </catalog>\n");

}



?>