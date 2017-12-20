<?php


function invokeGsmarenaData($url, $myfile, $marca)
{
    $info = file_get_contents( $url );

    fwrite($myfile, "      <cellphone>\n");
    printXML($myfile, 'phonearena.com', $url);
 
    $image =  splitInValue($info,'specs-photo-main','</a>');
    $image =  splitInValue($image,'src=','>');
    $brand =  explode('-', $marca)[0];
    $devicename =  splitInList($info,'data-spec="modelname">','<')[1];
    $deviceversion =  splitInList($info,'Also known as','<')[1];
    printXML_BASICS($myfile, $image, $brand, $devicename, $deviceversion, '', '');

    $technology =  splitInValue($info,'"nettech">','<');
    $bands2G =  splitInValue($info,'net2g">','<');
    $bands3G =  splitInValue($info,'net3g">','<');
    $bands4G =  splitInValue($info,'net4g">','<');
    $speed =  splitInValue($info,'data-spec="speed">','<');
    $GPRS =  splitInValue($info,'data-spec="gprstext">','<');
    $EDGE =  splitInValue($info,'data-spec="edge">','<');
    printXML_NETWORK($myfile, $technology, $bands2G, $bands3G, $bands4G, $speed, $GPRS, $EDGE);

    $announced =  splitInValue($info,'"nfo" data-spec="year">','<');
    $valuee =  splitInValue($info,'data-spec="status"','td>');
    $market_status =  splitInValue($valuee,'>','.');
    $launch_date =  splitInValue($valuee,'.','<');
    printXML_LAUNCH($myfile, $announced, $market_status, $launch_date, '');
    
    $dimensions = splitInValue($info,'data-spec="dimensions">','<');
    $weight =     splitInValue($info,'data-spec="weight">','<');
    $sim =  splitInValue($info,'data-spec="sim">','<');
    $rugged =  splitInValue($info,'data-spec="bodyother">','<');
    printXML_BODY($myfile, $dimensions, $weight, '', $sim, $rugged, '', '', '');

    $type = splitInValue($info,'data-spec="displaytype">','<');
    $size = splitInValue($info,'data-spec="displaysize">','<');
    $screen_to_body = splitInValue($info,'data-spec="dimensions">','<');
    $resolution = splitInValue($info,'data-spec="displayresolution">','<');
    $multitouch = strip_tags(splitInValue($info,'Multitouch</a></td>','</tr>'));
    $feature1 = splitInValue($info,' data-spec="displayother">','</td>');
    printXML_DISPLAY($myfile, $type, $size, $screen_to_body, $resolution, $multitouch, '', '', $feature1, '', '');

    $operativesystem = splitInValue($info,'data-spec="os">','<');
    $osversion = splitInValue($info,'data-spec="os">','<');
    $upgradeable_to = '';
    $chipset = splitInValue($info,'data-spec="chipset">','<');
    $cpu = splitInValue($info,'data-spec="cpu">','<');
    $gpu = splitInValue($info,'data-spec="gpu">>','<');
    printXML_PLATFORM($myfile, $operativesystem, $osversion, $upgradeable_to, $chipset, $cpu, $gpu);

    $card_slot = splitInValue($info,'data-spec="memoryslot">','<');
    $internal = splitInValue($info,'data-spec="internalmemory">','<');
    $ram = splitInValue($info,'data-spec="internalmemory">','<');
    printXML_MEMORY($myfile, $card_slot, $internal, $ram, '', '');

    $primary = splitInValue($info,'data-spec="cameraprimary">','<');
    $features = splitInValue($info,'data-spec="camerafeatures">','<');
    $video_resolution = splitInValue($info,'data-spec="cameravideo">','<');
    $secondary = splitInValue($info,'data-spec="camerasecondary">','<');
    printXML_CAMERA($myfile, $primary, '', '', $features, '', '', $video_resolution, '', $secondary);

    $alert_types = splitInValue(splitInValue($info,'Alert types','tr>'),'nfo">','<');
    $loudspeaker = splitInValue(splitInValue($info,'Loudspeaker','tr>'),'nfo">','<');
    $jack3_5mm_ = splitInValue(splitInValue($info,'3.5mm jack','tr>'),'nfo">','<');
    printXML_SOUND($myfile, $alert_types, $loudspeaker, $jack3_5mm_, '', '');

    $wlan = splitInValue($info,'data-spec="wlan">','<');
    $bluetooth = splitInValue($info,'data-spec="bluetooth">','<');
    $GPS = splitInValue($info,'data-spec="gps">','<');
    $NFC = splitInValue($info,'data-spec="nfc">','<');
    $radio = splitInValue($info,'data-spec="radio">','<');
    $USB = splitInValue($info,'data-spec="usb">','<');
    printXML_COMMS($myfile, $wlan, $bluetooth, $GPS, $NFC, $radio, $USB);

    $sensors = splitInValue($info,'data-spec="sensors">','<');
    $messaging = splitInValue(splitInValue($info,'Messaging','tr>'),'nfo">','<');
    $browser = splitInValue(splitInValue($info,'Browser','tr>'),'nfo">','<');
    $JAVA = splitInValue(splitInValue($info,'Java','tr>'),'nfo">','<');
    $feature1 = splitInValue($info,'data-spec="featuresother">','</td>');
    printXML_FEACTURES($myfile, $sensors, $messaging, $browser, $JAVA, '', '', '', '', '', $feature1, '', '');

    $mAh = splitInValue($info,'data-spec="batdescription1">','<');
    $talk_time_3G = splitInValue($info,'data-spec="battalktime1">','<');
    $music_play = splitInValue($info,'data-spec="batmusicplayback1">','<');
    printXML_BATTERY($myfile, $mAh, '', '', '', $talk_time_3G, $music_play, '', '', '', '', '');

    $colors = splitInValue($info,'data-spec="colors">','<');
    $price = splitInValue($info,'data-spec="price">','<');
    $SAR = splitInValue($info,'data-spec="sar-us">','<');
    $SAR_EU = splitInValue($info,'data-spec="sar-eu">','<');
    printXML_MISC($myfile, $colors, $SAR, $SAR_EU, $price);
    
    $performance = strip_tags(splitInValue($info,'Performance</a></td>','</tr>'));
    $display = strip_tags(splitInValue($info,'Display</a></td>','</tr>'));
    $camera = strip_tags(splitInValue($info,'Camera</a></td>','</tr>'));
    $loudspeaker = strip_tags(splitInValue($info,'Loudspeaker</a></td>','</tr>'));
    $audio = strip_tags(splitInValue($info,'Audio quality</a></td>','</tr>'));
    $battery_life = strip_tags(splitInValue($info,'Battery life</a></td>','</tr>'));
    printXML_TEST($myfile, $performance, $display, $camera, $loudspeaker, $audio, '', $battery_life);
    fwrite($myfile, "      </cellphone>\n");

    flush();
    ob_flush();
}

function invokeGsmarena($myfile)
{

	echo date(DATE_RFC2822);
    
    fwrite($myfile, "<?xml version=\"1.0\"?>\n");
    fwrite($myfile, "    <catalog>\n");


    $categories = file_get_contents( SERVER_gsmarena . '/makers.php3');
    $categories =  splitInList($categories,'<tr><td><a href=','>');

    for($i=1; $i<count($categories); $i++){

        $nextPage = $categories[$i];

        

        while ($nextPage){

            $listCellsPage = file_get_contents( SERVER_gsmarena . '/' . $nextPage );
            $listCells =  splitInList($listCellsPage,'<div class="makers">','</ul>')[1];
            $listCells =  splitInList($listCells,'<li><a href="','">');

            for($j=1; $j<count($listCells); $j++){
                invokeGsmarenaData( SERVER_gsmarena . '/' . $listCells[$j] , $myfile, $categories[$i]);
            }
        
            if(isset(splitInList($listCellsPage,'<a class="pages-nextPage" href="','"')[1])){
                $nextPage = splitInList($listCellsPage,'<a class="pages-nextPage" href="','"')[1];
            }
            else {
                $nextPage = '';
            }
        }
    }

    print '<br><br>';

    fwrite($myfile, "    </catalog>\n");

	echo date(DATE_RFC2822);

}

?>