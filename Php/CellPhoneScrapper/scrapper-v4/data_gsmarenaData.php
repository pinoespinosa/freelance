<?php


function invokeGsmarenaData($url)
{
    $info = file_get_contents( $url );
    $info =  splitInList($info,'data-spec="modelname">','>')[1];

    print $url . '<br>';
    print $info . '<br>' . '<br>';

    flush();
    ob_flush();
}

function invokeGsmarena($myfile)
{

	echo date(DATE_RFC2822);
    
    $categories = file_get_contents( SERVER_gsmarena . '/makers.php3');
    $categories =  splitInList($categories,'<tr><td><a href=','>');

    for($i=1; $i<count($categories); $i++){

        $nextPage = $categories[$i];

        while ($nextPage){

            $listCellsPage = file_get_contents( SERVER_gsmarena . '/' . $nextPage );
            $listCells =  splitInList($listCellsPage,'<div class="makers">','</ul>')[1];
            $listCells =  splitInList($listCells,'<li><a href="','">');

            for($j=1; $j<count($listCells); $j++){
                invokeGsmarenaData( SERVER_gsmarena . '/' . $listCells[$j] );
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

	echo date(DATE_RFC2822);

}

?>