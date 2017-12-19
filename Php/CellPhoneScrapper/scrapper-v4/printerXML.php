

<?php

function printXML($myfile, $source, $link)
{
    fwrite($myfile, "            <source>"              . trim($source)         . "</source>\n");
    fwrite($myfile, "            <link>"                . trim($link)           ."</link>\n");
}

function printXML_BASICS($myfile, $image, $brand, $devicename, $deviceversion, $alias, $market)
{
    fwrite($myfile, "            <basic>\n");
    fwrite($myfile, "               <image>"            . trim($image)     . "</image>\n");
    fwrite($myfile, "               <brand>"            . trim($brand)     . "</brand>\n");
    fwrite($myfile, "               <devicename>"       . trim($devicename)     . "</devicename>\n");
    fwrite($myfile, "               <deviceversion>"    . trim($deviceversion)     . "</deviceversion>\n");
    fwrite($myfile, "               <alias>"            . trim($alias)     . "</alias>\n");
    fwrite($myfile, "               <market>"           . trim($market)     . "</market>\n");
    fwrite($myfile, "            </basic>\n");
}

function printXML_NETWORK($myfile, $technology, $bands2G, $bands3G, $bands4G, $speed, $GPRS, $EDGE)
{  
    fwrite($myfile, "            <network>\n");
    fwrite($myfile, "               <technology>"       . trim($technology)     . "</technology>\n");
    fwrite($myfile, "               <bands2G>"          . trim($bands2G)     . "</bands2G>\n");
    fwrite($myfile, "               <bands3G>"          . trim($bands3G)     . "</bands3G>\n");
    fwrite($myfile, "               <bands4G>"          . trim($bands4G)     . "</bands4G>\n");
    fwrite($myfile, "               <speed>"            . trim($speed)     . "</speed>\n");
    fwrite($myfile, "               <GPRS>"             . trim($GPRS)     . "</GPRS>\n");
    fwrite($myfile, "               <EDGE>"             . trim($EDGE)     . "</EDGE>\n");
    fwrite($myfile, "            </network>\n");
}

function printXML_LAUNCH($myfile, $announced, $market_status, $launch_date, $fcc_approval)
{
    fwrite($myfile, "            <launch>\n");
    fwrite($myfile, "               <announced>"        . trim($announced)     . "</announced>\n");
    fwrite($myfile, "               <market_status>"    . trim($market_status)     . "</market_status>\n");
    fwrite($myfile, "               <launch_date>"      . trim($launch_date)     . "</launch_date>\n");
    fwrite($myfile, "               <fcc_approval>"     . trim($fcc_approval)     . "</fcc_approval>\n");
    fwrite($myfile, "            </launch>\n");
}

function printXML_BODY($myfile, $dimensions, $weight, $volume, $sim, $rugged, $water_resistant, $dust_resistant, $materials)
{  
    fwrite($myfile, "            <body>\n");
    fwrite($myfile, "               <dimensions>"       . trim($dimensions)     . "</dimensions>\n");
    fwrite($myfile, "               <weight>"           . trim($weight)     . "</weight>\n");
    fwrite($myfile, "               <volume>"           . trim($volume)     . "</volume>\n");
    fwrite($myfile, "               <sim>"              . trim($sim)     . "</sim>\n");  
    fwrite($myfile, "               <rugged>"           . trim($rugged)     . "</rugged>\n");
    fwrite($myfile, "               <water_resistant>"  . trim($water_resistant)     . "</water_resistant>\n");
    fwrite($myfile, "               <dust_resistant>"   . trim($dust_resistant)     . "</dust_resistant>\n");
    fwrite($myfile, "               <materials>"        . trim($materials)     . "</materials>\n");
    fwrite($myfile, "            </body>\n");
}

function printXML_DISPLAY($myfile, $type, $size, $screen_to_body, $resolution, $multitouch, $ppi_pixel_density, $protection, $feature1, $feature2, $feature3)
{  
    fwrite($myfile, "            <display>\n");
    fwrite($myfile, "               <type>"             . trim($type)     . "</type>\n");
    fwrite($myfile, "               <size>"             . trim($size)     . "</size>\n");
    fwrite($myfile, "               <screen_to_body>"   . trim($screen_to_body)     . "</screen_to_body>\n");
    fwrite($myfile, "               <resolution>"       . trim($resolution)     . "</resolution>\n");  
    fwrite($myfile, "               <multitouch>"       . trim($multitouch)     . "</multitouch>\n");
    fwrite($myfile, "               <ppi_pixel_density>". trim($ppi_pixel_density)     . "</ppi_pixel_density>\n");
    fwrite($myfile, "               <protection>"       . trim($protection)     . "</protection>\n");
    fwrite($myfile, "               <feature1>"         . trim($feature1)     . "</feature1>\n");
    fwrite($myfile, "               <feature2>"         . trim($feature2)     . "</feature2>\n");
    fwrite($myfile, "               <feature3>"         . trim($feature3)     . "</feature3>\n");
    fwrite($myfile, "            </display>\n");
}

function printXML_PLATFORM($myfile, $operativesystem, $osversion, $upgradeable_to, $chipset, $cpu, $gpu)
{  
    fwrite($myfile, "            <platform>\n");
    fwrite($myfile, "               <operativesystem>"  . trim($operativesystem)      . "</operativesystem>\n");
    fwrite($myfile, "               <osversion>"        . trim($osversion)      . "</osversion>\n");
    fwrite($myfile, "               <upgradeable_to>"   . trim($upgradeable_to)      . "</upgradeable_to>\n");
    fwrite($myfile, "               <chipset>"          . trim($chipset)      . "</chipset>\n");  
    fwrite($myfile, "               <cpu>"              . trim($cpu)      . "</cpu>\n");
    fwrite($myfile, "               <gpu>"              . trim($gpu)      . "</gpu>\n");
    fwrite($myfile, "            </platform>\n");
}

function printXML_MEMORY($myfile, $card_slot, $internal, $ram, $mhz, $maximum_user_storage)
{  
    fwrite($myfile, "            <memory>\n");
    fwrite($myfile, "               <card_slot>"            . trim($card_slot)  . "</card_slot>\n");
    fwrite($myfile, "               <internal>"             . trim($internal)  . "</internal>\n");
    fwrite($myfile, "               <ram>"                  . trim($ram)  . "</ram>\n");
    fwrite($myfile, "               <mhz>"                  . trim($mhz)  . "</mhz>\n");  
    fwrite($myfile, "               <maximum_user_storage>" . trim($maximum_user_storage)  . "</maximum_user_storage>\n");
    fwrite($myfile, "            </memory>\n");
}

function printXML_CAMERA($myfile, $primary, $aperture_size, $sensor_type, $features, $flash, $optical_zoom, $video_resolution, $fps, $secondary)
{  
    fwrite($myfile, "            <camera>\n");
    fwrite($myfile, "               <primary>"              . trim($primary)     . "</primary>\n");
    fwrite($myfile, "               <aperture_size>"        . trim($aperture_size)     . "</aperture_size>\n");
    fwrite($myfile, "               <sensor_type>"          . trim($sensor_type)     . "</sensor_type>\n");
    fwrite($myfile, "               <features>"             . trim($features)     . "</features>\n");  
    fwrite($myfile, "               <flash>"                . trim($flash)     . "</flash>\n");
    fwrite($myfile, "               <optical_zoom>"         . trim($optical_zoom)     . "</optical_zoom>\n");
    fwrite($myfile, "               <video_resolution>"     . trim($video_resolution)     . "</video_resolution>\n");  
    fwrite($myfile, "               <fps>"                  . trim($fps)     . "</fps>\n");
    fwrite($myfile, "               <secondary>"            . trim($secondary)     . "</secondary>\n");
    fwrite($myfile, "            </camera>\n");
}

function printXML_SOUND($myfile, $alert_types, $loudspeaker, $jack3_5mm_, $sound1, $sound2)
{  
     fwrite($myfile, "            <sound>\n");
    fwrite($myfile, "               <alert_types>"          . trim($alert_types)     . "</alert_types>\n");
    fwrite($myfile, "               <loudspeaker>"          . trim($loudspeaker)     . "</loudspeaker>\n");
    fwrite($myfile, "               <jack3_5mm_>"           . trim($jack3_5mm_)     . "</jack3_5mm_>\n");
    fwrite($myfile, "               <sound1>"               . trim($sound1)     . "</sound1>\n");  
    fwrite($myfile, "               <sound2>"               . trim($sound2)     . "</sound2>\n");
    fwrite($myfile, "            </sound>\n");
}

function printXML_COMMS($myfile, $wlan, $bluetooth, $GPS, $NFC, $radio, $USB)
{  
    fwrite($myfile, "            <comms>\n");
    fwrite($myfile, "               <wlan>"                 . trim($wlan)     . "</wlan>\n");
    fwrite($myfile, "               <bluetooth>"            . trim($bluetooth)     . "</bluetooth>\n");
    fwrite($myfile, "               <GPS>"                  . trim($GPS)     . "</GPS>\n");
    fwrite($myfile, "               <NFC>"                  . trim($NFC)     . "</NFC>\n");  
    fwrite($myfile, "               <radio>"                . trim($radio)     . "</radio>\n");
    fwrite($myfile, "               <USB>"                  . trim($USB)     . "</USB>\n");
    fwrite($myfile, "            </comms>\n");
}

function printXML_FEACTURES($myfile, $sensors, $messaging, $browser, $JAVA, $fast_charging, $audio_formats, $video_formats, $audiovideoeditor, $documents, $feature1, $feature2, $feature3)
{  
    fwrite($myfile, "            <feactures>\n");
    fwrite($myfile, "               <sensors>"              . trim($sensors)     . "</sensors>\n");
    fwrite($myfile, "               <messaging>"            . trim($messaging)     . "</messaging>\n");
    fwrite($myfile, "               <browser>"              . trim($browser)     . "</browser>\n");
    fwrite($myfile, "               <JAVA>"                 . trim($JAVA)     . "</JAVA>\n");  
    fwrite($myfile, "               <fast_charging>"        . trim($fast_charging)     . "</fast_charging>\n");
    fwrite($myfile, "               <audio_formats>"        . trim($audio_formats)     . "</audio_formats>\n");
    fwrite($myfile, "               <video_formats>"        . trim($video_formats)     . "</video_formats>\n");
    fwrite($myfile, "               <audiovideoeditor>"     . trim($audiovideoeditor)     . "</audiovideoeditor>\n");
    fwrite($myfile, "               <documents>"            . trim($documents)     . "</documents>\n");
    fwrite($myfile, "               <feature1>"             . trim($feature1)     . "</feature1>\n");
    fwrite($myfile, "               <feature2>"             . trim($feature2)     . "</feature2>\n");
    fwrite($myfile, "               <feature3>"             . trim($feature3)     . "</feature3>\n");
    fwrite($myfile, "            </feactures>\n");
}

function printXML_BATTERY($myfile, $mAh, $fast_charge_technology, $removable, $batterytype, $talk_time_3G, $music_play, $stand_by_time_2g, $internet_use_3g, $internet_use_LTE, $internet_use_Wi_Fi, $video_playback)
{  
    fwrite($myfile, "            <battery>\n");
    fwrite($myfile, "               <mAh>"                      . trim($mAh)     . "</mAh>\n");
    fwrite($myfile, "               <fast_charge_technology>"   . trim($fast_charge_technology)     . "</fast_charge_technology>\n");
    fwrite($myfile, "               <removable>"                . trim($removable)     . "</removable>\n");
    fwrite($myfile, "               <batterytype>"              . trim($batterytype)     . "</batterytype>\n");  
    fwrite($myfile, "               <talk_time_3G>"             . trim($talk_time_3G)     . "</talk_time_3G>\n");
    fwrite($myfile, "               <music_play>"               . trim($music_play)     . "</music_play>\n");
    fwrite($myfile, "               <stand_by_time_2g>"         . trim($stand_by_time_2g)     . "</stand_by_time_2g>\n");
    fwrite($myfile, "               <internet_use_3g>"          . trim($internet_use_3g)     . "</internet_use_3g>\n");
    fwrite($myfile, "               <internet_use_LTE>"         . trim($internet_use_LTE)     . "</internet_use_LTE>\n");
    fwrite($myfile, "               <internet_use_Wi_Fi>"       . trim($internet_use_Wi_Fi)     . "</internet_use_Wi_Fi>\n");
    fwrite($myfile, "               <video_playback>"           . trim($video_playback)     . "</video_playback>\n");
    fwrite($myfile, "            </battery>\n");
}

function printXML_MISC($myfile, $colors, $SAR, $SAR_EU, $price)
{  
    fwrite($myfile, "            <MISC>\n");
    fwrite($myfile, "               <colors>"                   . trim($colors)     . "</colors>\n");
    fwrite($myfile, "               <SAR>"                      . trim($SAR)     . "</SAR>\n");
    fwrite($myfile, "               <SAR_EU>"                   . trim($SAR_EU)     . "</SAR_EU>\n");
    fwrite($myfile, "               <price>"                    . trim($price)     . "</price>\n");  
    fwrite($myfile, "            </MISC>\n");
}

function printXML_POPULARITY($myfile, $hits, $fans)
{  
    fwrite($myfile, "            <POPULARITY>\n");
    fwrite($myfile, "               <hits>"                     . trim($hits)     . "</hits>\n");
    fwrite($myfile, "               <fans>"                     . trim($fans)     . "</fans>\n");
    fwrite($myfile, "            </POPULARITY>\n");
}

function printXML_TEST($myfile, $performance, $display, $camera, $loudspeaker, $audio, $quality, $battery_life)
{  
    fwrite($myfile, "            <TESTS>\n");
    fwrite($myfile, "               <performance>"              . trim($performance)     . "</performance>\n");
    fwrite($myfile, "               <display>"                  . trim($display)     . "</display>\n");
    fwrite($myfile, "               <camera>"                   . trim($camera)     . "</camera>\n");
    fwrite($myfile, "               <loudspeaker>"              . trim($loudspeaker)     . "</loudspeaker>\n");
    fwrite($myfile, "               <audio>"                    . trim($audio)     . "</audio>\n");
    fwrite($myfile, "               <quality>"                  . trim($quality)     . "</quality>\n");
    fwrite($myfile, "               <battery_life>"             . trim($battery_life)     . "</battery_life>\n");
    fwrite($myfile, "            </TESTS>\n");
}

?>