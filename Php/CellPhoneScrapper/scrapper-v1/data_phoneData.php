<?php

function invokePhonearenaData($url, $myfile)
{

    $info = file_get_contents( $url );

    fwrite($myfile, "      <cellphone>\n");
    printXML($myfile, 'phonearena.com', $url);

    $image =  splitInList($info,'<div class="quicklook">','</a>')[1];
    $image =  splitInList($image,'<img src="','"')[1];
    $brand =  splitInValue($info,'"brand": {','},');
    $brand =  splitInValue($brand,'"name": "','"');
    $devicename =  splitInList($info,'<h1>','</h1>')[1];
    $devicename =  splitInList($devicename,'<span>','</span>')[1];
    $alias =  splitInList($info,'This device is also known as</em>','</p>')[1];
    $alias =  str_replace('<strong>','', $alias);
	$alias =  str_replace('</strong>','', $alias);
	$alias=preg_replace('/\s+/', ' ', $alias);
	printXML_BASICS($myfile, $image, $brand, $devicename, '', $alias, '');

    printXML_NETWORK($myfile, '', '', '', '', '', '', '');

   	$market_status =  splitInList($info,'Market status:','<br />')[1];
    $market_status =  splitInList($market_status,'>','<')[1];
   	$fcc_approval =  splitInList($info,'Date approved:','</ul>')[1];
    $fcc_approval =  splitInList($fcc_approval,'<li>','</li>')[1];
	printXML_LAUNCH($myfile, '', $market_status, '', $fcc_approval);

    $dimensions =  splitInValue($info,'Dimensions:','</ul>');
    $dimensions =  splitInValue($dimensions,'</span></span>','</li>');
    $weight =  splitInValue($info,'Weight:','</ul>');
    $weight =  splitInValue($weight,'</span></span>','<');
    $rugged =  splitInValue($info,'<b>IP certified</b>','</ul>');
    $rugged =  splitInValue($rugged,'<li>','</li>');
    $resistant =  splitInValue($info,'Rugged:','</ul>');
    $resistant =  splitInValue($resistant,'<li>','</li>');
    $materials =  splitInValue($info,'Materials:','</ul>');
    $materials =  splitInValue($materials,'<li>','</li>');
	printXML_BODY($myfile, $dimensions, $weight, '', '', $rugged, $resistant, $resistant, $materials);

    $type =  splitInValue($info,'Technology:','</ul>');
    $type =  splitInValue($type,'<li>','</li>');
    $size =  splitInValue($info,'Physical size:','</ul>');
    $size =  splitInValue($size,'</span></span>','</li>');
    $screen_to_body =  splitInValue($info,'Screen-to-body ratio:','</ul>');
    $screen_to_body =  splitInValue($screen_to_body,'<li>','</li>');
    $resolution =  splitInValue($info,'Resolution:','</ul>');
    $resolution =  splitInValue($resolution,'</span></span>','</li>');
	$ppi_pixel_density =  splitInValue($info,'Pixel density:','</ul>');
    $ppi_pixel_density =  splitInValue($ppi_pixel_density,'</span></span>','</li>');
    $feature1 =  splitInValue($info,'field-492','</ul>');
    $feature1 =  splitInValue($feature1,'<li>','</li>');
    $feature2 =  explode(',', $feature1)[1];
    $feature3 =  explode(',', $feature1)[2];
    $feature1 =  explode(',', $feature1)[0];
	printXML_DISPLAY($myfile, $type, $size, $screen_to_body, $resolution, '', $ppi_pixel_density, '', $feature1, $feature2, $feature3);
	
    $chipset =  splitInValue($info,'field-573','</ul>');
    $chipset =  splitInValue($chipset,'<li>','</li>');
	$cpu =  splitInValue($info,'field-351','</ul>');
    $cpu =  splitInValue($cpu,'</span></span>','</li>');
    $gpu =  splitInValue($info,'field-353','</ul>');
    $gpu =  splitInValue($gpu,'<li>','</li>');
	printXML_PLATFORM($myfile, '', '', '', $chipset, $cpu, $gpu);

	$ram =  splitInValue($info,'field-122','</ul>');
    $ram =  splitInValue($ram,'</span></span>','</li>');
    $maximum_user_storage =  splitInValue($info,'field-518','</ul>');
    $maximum_user_storage =  splitInValue($maximum_user_storage,'<li>','</li>');
	printXML_MEMORY($myfile, '', '', $ram, '', $maximum_user_storage);

    $aperture_size =  splitInValue($info,'Aperture size:','</ul>');
    $aperture_size =  splitInValue($aperture_size,'<li>','</li>');
    $flash =  splitInValue($info,'Flash:','</ul>');
    $flash =  splitInValue($flash,'<li>','</li>');
    $features =  splitInValue($info,'Hardware Features:','</ul>');
    $features =  splitInValue($features,'<li>','</li>');
    $primary =  splitInValue($info,'field-77','</ul>');
    $primary =  splitInValue($primary,'</span></span>','</li>');
    $video_resolution =  splitInValue($info,'field-376','</ul>');
    $video_resolution =  splitInValue($video_resolution,'<li>','</li>');
    $secondary =  splitInValue($info,'field-377','</ul>');
    $secondary =  splitInValue($secondary,'<li>','</li>');
    $optical_zoom =  splitInValue($info,'Optical zoom:','</ul>');
    $optical_zoom =  splitInValue($optical_zoom,'<li>','</li>');
	printXML_CAMERA($myfile, $primary, $aperture_size, '', $features, $flash, $optical_zoom, $video_resolution, $fps, $secondary);

    printXML_SOUND($myfile, '', '', '', '', '');

    $bluetooth =  splitInValue($info,'Bluetooth:','</ul>');
    $bluetooth =  splitInValue($bluetooth,'<li>','</li>');
    $GPS =  splitInValue($info,'Positioning:','</ul>');
    $GPS =  splitInValue($GPS,'<li>','</li>');
	printXML_COMMS($myfile, '', $bluetooth, $GPS, '', '', '');

	printXML_FEACTURES($myfile, '', '', '', '', '', '', '', '', '', '', '', '');

    $mAh =  splitInValue($info,'Capacity:','</ul>');
    $mAh =  splitInValue($mAh,'<li>','</li>');
    $fast_charge_technology =  splitInValue($info,'Fast Charge:','</ul>');
    $fast_charge_technology =  splitInValue($fast_charge_technology,'<li>','</li>');
    $batterytype =  splitInValue($info,'field-577','</ul>');
    $batterytype =  splitInValue($batterytype,'<li>','</li>');
	$talk_time_3G =  splitInValue($info,'field-291','</ul>');
    $talk_time_3G =  splitInValue($talk_time_3G,'</span></span>','<br />');
    $music_play =  splitInValue($info,'field-294','</ul>');
    $music_play =  splitInValue($music_play,'<li>','</li>');
    $video_playback =  splitInValue($info,'field-295','</ul>');
    $video_playback =  splitInValue($video_playback,'<li>','</li>');
    $internet_use_3g =  splitInValue($info,'3G:','</ul>');
    $internet_use_3g =  splitInValue($internet_use_3g,'<li>','</li>');
    $internet_use_LTE =  splitInValue($info,'>LTE:<','</ul>');
    $internet_use_LTE =  splitInValue($internet_use_LTE,'<li>','</li>');
    $internet_use_Wi_Fi =  splitInValue($info,'>Wi-Fi:<','</ul>');
    $internet_use_Wi_Fi =  splitInValue($internet_use_Wi_Fi,'<li>','</li>');
	printXML_BATTERY($myfile, $mAh, $fast_charge_technology, '', $batterytype, $talk_time_3G, $music_play, '', $internet_use_3g, $internet_use_LTE, $internet_use_Wi_Fi, $video_playback);

	printXML_MISC($myfile, '', '', '', '');
	printXML_POPULARITY($myfile, '', '');
	printXML_TEST($myfile, '', '', '', '', '', '', '');

    fwrite($myfile, "      </cellphone>\n");


    flush();
    ob_flush();
}


function invokePhonearena($subpath, $myfile)
{

#	print date(DATE_RFC2822) . '<br>';

    fwrite($myfile, '<?xml version="1.0"?>' . "\n");
    fwrite($myfile, "<cellphones>\n");


    $categories = file_get_contents( SERVER_phonearena . '/phones/manufacturers');
    $categories =  splitInList($categories,'<div class="s_hover">','" class="s_thumb"');

    for($i=1; $i<count($categories); $i++){

        $nextPage = explode('a href="', $categories[$i] . $subpath)[1];
        print $nextPage . '<br>';
        flush();
        ob_flush();
        while ($nextPage){

            $listCellsPage = file_get_contents( SERVER_phonearena  . $nextPage );
            $listCells =  splitInList($listCellsPage,'id="phones"','class="s_static"')[1];
            $listCells =  splitInList($listCells,'<a class="s_thumb" href="','"');

            for($j=1; $j<count($listCells); $j++){
                print $listCells[$j] . '<br>';
                flush();
                ob_flush();
                invokePhonearenaData( SERVER_phonearena . $listCells[$j], $myfile );
            }
        
            if(isset(splitInList($listCellsPage,'class="s_next"','</li>')[1])){
               $nextPage = splitInList($listCellsPage,'class="s_next"','</li>')[1];
               $nextPage = splitInList($nextPage,'href="','"')[1];

            }
            else {
                $nextPage = '';
            }
        }
    }

    print '<br><br>';
    fwrite($myfile, "</cellphones>\n");

#    print date(DATE_RFC2822) . '<br>';

}


?>