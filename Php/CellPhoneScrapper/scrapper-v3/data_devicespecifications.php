<?php


function invokeDevicespecificationsData($url, $myfile, $brand)
{

    $url = str_replace("images/model/", "en/model/", $url);
    print($url . '<br>');
    $info = file_get_contents( $url );

    fwrite($myfile, "      <cellphone>\n");
    printXML($myfile, 'devicespecifications.com', $url);
    
    $image =  splitInValue($info,'<div id="model-image" style="background-image: url(',')');
    $devicename =  splitInValue($info,'<p>Model name of the device.</p></td><td>','</td>');
    $brand =  splitInValue($info,'<td>Brand<p>Brand name of the company that manufactures the device.</p></td><td>','</td>');
    $alias = 'falta';
    printXML_BASICS($myfile, $image, $brand, $devicename, '', $alias, '');
   

    if (strpos('<td>GSM<p>', 'GSM') !== false) {    $bands2G = 'true';  }   else {  $bands2G = 'false'; }
    if (strpos('<td>CDMA<p>', 'CDMA') !== false) {  $bands3G = 'true';  }   else {  $bands3G = 'false'; }
    if (strpos('<td>LTE<p>', 'LTE') !== false) {    $bands4G = 'true';  }   else {  $bands4G = 'false'; }
    printXML_NETWORK($myfile, '', $bands2G, $bands3G, $bands4G, '', '', '');

    printXML_LAUNCH($myfile, '', '', '', '');


    $dimensions = splitInValue($info,'<b>Dimensions</b>:','<');
    $weight = splitInValue($info,'<b>Weight</b>:','<');
    $volume = splitInValue($info,'<td>Volume<p>','</tr>');
    $volume = splitInValue($volume,'</span>','<span>');
    $materials = splitInValue($info,'<td>Body materials<p>','</tr>');
    $materials = str_replace("<br />", " ", $materials);
    $materials = (strip_tags($materials));
    $materials = str_replace("Materials used in the fabrication of the device's body.", "", $materials);
    printXML_BODY($myfile, $dimensions, $weight, $volume, '', '', '', '', $materials);


    $type = (strip_tags(splitInValue($info,'<td>Type/technology<p>One of the main characteristics of the display is its type/technology, on which depends its performance.</p></td>','</tr>')));
    $size = (strip_tags(splitInValue(splitInValue($info,'<td>Diagonal size<p>','</tr>'),'<td>','<span>')));
    $resolution = (strip_tags(splitInValue($info,'<td>Resolution<p>The display resolution shows the number of pixels on the horizontal and vertical side of the screen. The higher the resolution is, the greater the detail of the displayed content.</p>','</tr>')));
    $ppi_pixel_density = (strip_tags(splitInValue(splitInValue($info,'<td>Pixel density<p>','</tr>'),'<td>','<span>')));
    printXML_DISPLAY($myfile, $type, $size, '', $resolution, '', $ppi_pixel_density, '', '', '', '');

    $operativesystem = (strip_tags(splitInValue(splitInValue($info,'<td>Operating system (OS)<p>','</tr>'),'<td>','</td>')));
    $chipset = splitInValue($info,'<b>SoC</b>:','<');   
    $cpu = splitInValue($info,'<b>CPU</b>:','<');
    $gpu = splitInValue($info,'<b>GPU</b>:','<');
    printXML_PLATFORM($myfile, $operativesystem, '', '', $chipset, $cpu, $gpu);

    $mhz = (strip_tags(splitInValue(splitInValue($info,'<td>RAM frequency<p>','</tr>'),'<td>','<span>')));
    $ram = splitInValue($info,'<b>RAM</b>:',',');
    $maximum_user_storage = splitInValue($info,'<b>Storage</b>:',',');
    printXML_MEMORY($myfile, '', '', $ram, $mhz, $maximum_user_storage);

    $primary = splitInValue($info,'<b>Camera</b>:','<');
    $aperture_size = (strip_tags(splitInValue(splitInValue($info,'<td>Aperture<p>','</tr>'),'<td>','</td>')));
    $sensor_type = (strip_tags(splitInValue(splitInValue($info,'<td>Sensor type<p>','</tr>'),'<td>','</td>')));
    $fps = (strip_tags(splitInValue(splitInValue($info,'<td>Video FPS<p>','</tr>'),'<td>','</td>')));
    $flash = (strip_tags(splitInValue(splitInValue($info,'<td>Flash type<p>','</tr>'),'<td>','</td>')));
    printXML_CAMERA($myfile, $primary, $aperture_size, $sensor_type, '', $flash, '', '', $fps, '');

    printXML_SOUND($myfile, '', '', '', '', '');

    if (strpos($info, '<h2 class="header">Bluetooth</h2>') !== false) {    $bluetooth = 'true';  }   else {  $bluetooth = 'false'; }
    if (strpos($info, '<h2 class="header">USB</h2>') !== false) {    $USB = 'true';  }   else {  $USB = 'false'; }
    $GPS = splitInValue($info,'<b>Positioning</b>:','<');
    printXML_COMMS($myfile, '', $bluetooth, $GPS, '', '', $USB);

    printXML_FEACTURES($myfile, '', '', '', '', '', '', '', '', '', '', '', '');



    $mAh = (strip_tags(splitInValue(splitInValue($info,'<td>Capacity<p>','</tr>'),'<td>','<span>')));
    $fast_charge_technology = (strip_tags(splitInValue(splitInValue($info,'<td>Quick charge technology<p>','</tr>'),'<td>','<span>')));
    printXML_BATTERY($myfile, $mAh, $fast_charge_technology, '', '', '', '', '', '', '', '', '');

    printXML_MISC($myfile, '', '', '', '');
    printXML_POPULARITY($myfile, '', '');
    printXML_TEST($myfile, '', '', '', '', '', '', '');

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
        $brand = explode(">", $categories[$i])[1];
        flush();
        ob_flush();
        while ($nextPage){

            $listCellsPage = file_get_contents( $nextPage );
            $listCells =  splitInList($listCellsPage,'<div id="main">','model-listing-container-160')[1];
            $listCells =  splitInList($listCells,'data-src="','/80/main');

            for($j=1; $j<count($listCells); $j++){
                
                if (strpos($listCells[$j], 'model/') !== false) {          
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