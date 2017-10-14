#!/bin/sh

for cat in 11 13 1360 14 15 16 17 19 20 21 22 23 24 25 26 2626 2627 2628 2629 2630 2631 2632 2633 2634 2635 2636 2637 2638 2639 2640 2641 2642 2643 2644 2645 2646 2647 2648 2651 2652 2683 2684 2685 2686 2687 27 2704 2705 2710 2712 2713 28 29 2999 30 3000 3001 3002 3003 3004 3005 3006 3007 3008 3009 3010 3011 3012 3013 3014 3015 3016 3017 3018 3019 3020 3021 3022 3023 3024 3025 3026 3027 3029 3030 3031 3032 3033 3034 3035 3036 3037 3038 3039 3040 3041 3042 3043 3044 3045 3046 3047 3048 3049 3050 3051 3052 3053 3054 3055 3057 3059 3060 3061 3062 3063 3064 3065 3069 3070 3071 3092 31 32 33 34 35 36 37 38 39 40 42 43 44 45 46 47 78 79
do

	LISTO=true;
	num=0

	while $LISTO ;
	do

		curl "http://www.radec.com.mx/catalogo?type=$cat&brand=&model=&year=&search=&new=&exist=&all=1&acces=&sector=&enfri=&btn_search=Buscar&page=$num" -H 'Host: www.radec.com.mx' -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:54.0) Gecko/20100101 Firefox/54.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8' -H 'Accept-Language: en-US,en;q=0.5' --compressed -H 'Cookie: __utma=72389776.1460591499.1500578541.1500578541.1500584443.2; __utmz=72389776.1500578541.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); boost-gzip=true; SESS721296bc8a9b4f397fb8b90d35e8c32d=bkmsbtf39h62nto7n37hgi02h6; DRUPAL_UID=1970; has_js=1; __utmb=72389776.21.9.1500586663668; __utmc=72389776; __utmt=1' -H 'Connection: keep-alive' -H 'Upgrade-Insecure-Requests: 1' -H 'If-Modified-Since: Thu, 20 Jul 2017 22:30:40 GMT' > colision/"archi$cat-$num".txt
		
		STRING="Lo sentimos, pero";
		FILE="colision/archi$cat-$num.txt";

		if grep -q "$STRING" $FILE; then
 			LISTO=false;
 			echo 'es false';
 			rm $FILE;
 		else
			LISTO=true;
			echo 'es true';
 		fi
		num=$(( num+1 ))
		echo $num
	done

done





for cat in 11 13 1360 14 15 16 17 19 20 21 22 23 24 25 26 2626 2627 2628 2629 2630 2631 2632 2633 2634 2635 2636 2637 2638 2639 2640 2641 2642 2643 2644 2645 2646 2647 2648 2651 2652 2683 2684 2685 2686 2687 27 2704 2705 2710 2712 2713 28 29 2999 30 3000 3001 3002 3003 3004 3005 3006 3007 3008 3009 3010 3011 3012 3013 3014 3015 3016 3017 3018 3019 3020 3021 3022 3023 3024 3025 3026 3027 3029 3030 3031 3032 3033 3034 3035 3036 3037 3038 3039 3040 3041 3042 3043 3044 3045 3046 3047 3048 3049 3050 3051 3052 3053 3054 3055 3057 3059 3060 3061 3062 3063 3064 3065 3069 3070 3071 3092 31 32 33 34 35 36 37 38 39 40 42 43 44 45 46 47 78 79
do

	LISTO=true;
	num=0

	while $LISTO ;
	do

		curl "http://www.radec.com.mx/catalogo?type=$cat&brand=&model=&year=&search=&new=1&exist=&all=&acces=&sector=&enfri=&btn_search=Buscar&page=$num" -H 'Host: www.radec.com.mx' -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:54.0) Gecko/20100101 Firefox/54.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8' -H 'Accept-Language: en-US,en;q=0.5' --compressed -H 'Cookie: __utma=72389776.1460591499.1500578541.1500578541.1500584443.2; __utmz=72389776.1500578541.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); boost-gzip=true; SESS721296bc8a9b4f397fb8b90d35e8c32d=bkmsbtf39h62nto7n37hgi02h6; DRUPAL_UID=1970; has_js=1; __utmb=72389776.21.9.1500586663668; __utmc=72389776; __utmt=1' -H 'Connection: keep-alive' -H 'Upgrade-Insecure-Requests: 1' -H 'If-Modified-Since: Thu, 20 Jul 2017 22:30:40 GMT' > nuevos/"archi$cat-$num".txt
		
		STRING="Lo sentimos, pero";
		FILE="nuevos/archi$cat-$num.txt";

		if grep -q "$STRING" $FILE; then
 			LISTO=false;
 			echo 'es false';
 			rm $FILE;
 		else
			LISTO=true;
			echo 'es true';
 		fi
		num=$(( num+1 ))
		echo $num
	done

done


for cat in 11 13 1360 14 15 16 17 19 20 21 22 23 24 25 26 2626 2627 2628 2629 2630 2631 2632 2633 2634 2635 2636 2637 2638 2639 2640 2641 2642 2643 2644 2645 2646 2647 2648 2651 2652 2683 2684 2685 2686 2687 27 2704 2705 2710 2712 2713 28 29 2999 30 3000 3001 3002 3003 3004 3005 3006 3007 3008 3009 3010 3011 3012 3013 3014 3015 3016 3017 3018 3019 3020 3021 3022 3023 3024 3025 3026 3027 3029 3030 3031 3032 3033 3034 3035 3036 3037 3038 3039 3040 3041 3042 3043 3044 3045 3046 3047 3048 3049 3050 3051 3052 3053 3054 3055 3057 3059 3060 3061 3062 3063 3064 3065 3069 3070 3071 3092 31 32 33 34 35 36 37 38 39 40 42 43 44 45 46 47 78 79
do

	LISTO=true;
	num=0

	while $LISTO ;
	do

		curl "http://www.radec.com.mx/catalogo?type=$cat&brand=&model=&year=&search=&new=&exist=1&all=&acces=&sector=&enfri=&btn_search=Buscar&page=$num" -H 'Host: www.radec.com.mx' -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:54.0) Gecko/20100101 Firefox/54.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8' -H 'Accept-Language: en-US,en;q=0.5' --compressed -H 'Cookie: __utma=72389776.1460591499.1500578541.1500578541.1500584443.2; __utmz=72389776.1500578541.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); boost-gzip=true; SESS721296bc8a9b4f397fb8b90d35e8c32d=bkmsbtf39h62nto7n37hgi02h6; DRUPAL_UID=1970; has_js=1; __utmb=72389776.21.9.1500586663668; __utmc=72389776; __utmt=1' -H 'Connection: keep-alive' -H 'Upgrade-Insecure-Requests: 1' -H 'If-Modified-Since: Thu, 20 Jul 2017 22:30:40 GMT' > agotados/"archi$cat-$num".txt
		
		STRING="Lo sentimos, pero";
		FILE="agotados/archi$cat-$num.txt";

		if grep -q "$STRING" $FILE; then
 			LISTO=false;
 			echo 'es false';
 			rm $FILE;
 		else
			LISTO=true;
			echo 'es true';
 		fi
		num=$(( num+1 ))
		echo $num
	done

done


for cat in 11 13 1360 14 15 16 17 19 20 21 22 23 24 25 26 2626 2627 2628 2629 2630 2631 2632 2633 2634 2635 2636 2637 2638 2639 2640 2641 2642 2643 2644 2645 2646 2647 2648 2651 2652 2683 2684 2685 2686 2687 27 2704 2705 2710 2712 2713 28 29 2999 30 3000 3001 3002 3003 3004 3005 3006 3007 3008 3009 3010 3011 3012 3013 3014 3015 3016 3017 3018 3019 3020 3021 3022 3023 3024 3025 3026 3027 3029 3030 3031 3032 3033 3034 3035 3036 3037 3038 3039 3040 3041 3042 3043 3044 3045 3046 3047 3048 3049 3050 3051 3052 3053 3054 3055 3057 3059 3060 3061 3062 3063 3064 3065 3069 3070 3071 3092 31 32 33 34 35 36 37 38 39 40 42 43 44 45 46 47 78 79
do

	LISTO=true;
	num=0

	while $LISTO ;
	do

		curl "http://www.radec.com.mx/catalogo?type=$cat&brand=&model=&year=&search=&new=&exist=&all=&acces=1&sector=&enfri=&btn_search=Buscar&page=$num" -H 'Host: www.radec.com.mx' -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:54.0) Gecko/20100101 Firefox/54.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8' -H 'Accept-Language: en-US,en;q=0.5' --compressed -H 'Cookie: __utma=72389776.1460591499.1500578541.1500578541.1500584443.2; __utmz=72389776.1500578541.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); boost-gzip=true; SESS721296bc8a9b4f397fb8b90d35e8c32d=bkmsbtf39h62nto7n37hgi02h6; DRUPAL_UID=1970; has_js=1; __utmb=72389776.21.9.1500586663668; __utmc=72389776; __utmt=1' -H 'Connection: keep-alive' -H 'Upgrade-Insecure-Requests: 1' -H 'If-Modified-Since: Thu, 20 Jul 2017 22:30:40 GMT' > acces/"archi$cat-$num".txt
		
		STRING="Lo sentimos, pero";
		FILE="acces/archi$cat-$num.txt";

		if grep -q "$STRING" $FILE; then
 			LISTO=false;
 			echo 'es false';
 			rm $FILE;
 		else
			LISTO=true;
			echo 'es true';
 		fi
		num=$(( num+1 ))
		echo $num
	done

done


for cat in 11
do

	LISTO=true;
	num=0

	while $LISTO ;
	do

		curl "http://www.radec.com.mx/catalogo?type=$cat&brand=&model=&year=&search=&new=&exist=&all=&acces=&sector=3073&enfri=&btn_search=Buscar&page=$num" -H 'Host: www.radec.com.mx' -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:54.0) Gecko/20100101 Firefox/54.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8' -H 'Accept-Language: en-US,en;q=0.5' --compressed -H 'Cookie: __utma=72389776.1460591499.1500578541.1500578541.1500584443.2; __utmz=72389776.1500578541.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); boost-gzip=true; SESS721296bc8a9b4f397fb8b90d35e8c32d=bkmsbtf39h62nto7n37hgi02h6; DRUPAL_UID=1970; has_js=1; __utmb=72389776.21.9.1500586663668; __utmc=72389776; __utmt=1' -H 'Connection: keep-alive' -H 'Upgrade-Insecure-Requests: 1' -H 'If-Modified-Since: Thu, 20 Jul 2017 22:30:40 GMT' > susp/"archi$cat-$num".txt
		
		STRING="Lo sentimos, pero";
		FILE="susp/archi$cat-$num.txt";

		if grep -q "$STRING" $FILE; then
 			LISTO=false;
 			echo 'es false';
 			rm $FILE;
 		else
			LISTO=true;
			echo 'es true';
 		fi
		num=$(( num+1 ))
		echo $num
	done

done

for cat in 11 13 1360 14 15 16 17 19 20 21 22 23 24 25 26 2626 2627 2628 2629 2630 2631 2632 2633 2634 2635 2636 2637 2638 2639 2640 2641 2642 2643 2644 2645 2646 2647 2648 2651 2652 2683 2684 2685 2686 2687 27 2704 2705 2710 2712 2713 28 29 2999 30 3000 3001 3002 3003 3004 3005 3006 3007 3008 3009 3010 3011 3012 3013 3014 3015 3016 3017 3018 3019 3020 3021 3022 3023 3024 3025 3026 3027 3029 3030 3031 3032 3033 3034 3035 3036 3037 3038 3039 3040 3041 3042 3043 3044 3045 3046 3047 3048 3049 3050 3051 3052 3053 3054 3055 3057 3059 3060 3061 3062 3063 3064 3065 3069 3070 3071 3092 31 32 33 34 35 36 37 38 39 40 42 43 44 45 46 47 78 79
do

	LISTO=true;
	num=0

	while $LISTO ;
	do

		curl "http://www.radec.com.mx/catalogo?type=$cat&brand=&model=&year=&search=&new=&exist=&all=&acces=&sector=&enfri=1&btn_search=Buscar&page=$num" -H 'Host: www.radec.com.mx' -H 'User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:54.0) Gecko/20100101 Firefox/54.0' -H 'Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8' -H 'Accept-Language: en-US,en;q=0.5' --compressed -H 'Cookie: __utma=72389776.1460591499.1500578541.1500578541.1500584443.2; __utmz=72389776.1500578541.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); boost-gzip=true; SESS721296bc8a9b4f397fb8b90d35e8c32d=bkmsbtf39h62nto7n37hgi02h6; DRUPAL_UID=1970; has_js=1; __utmb=72389776.21.9.1500586663668; __utmc=72389776; __utmt=1' -H 'Connection: keep-alive' -H 'Upgrade-Insecure-Requests: 1' -H 'If-Modified-Since: Thu, 20 Jul 2017 22:30:40 GMT' > radia/"archi$cat-$num".txt
		
		STRING="Lo sentimos, pero";
		FILE="radia/archi$cat-$num.txt";

		if grep -q "$STRING" $FILE; then
 			LISTO=false;
 			echo 'es false';
 			rm $FILE;
 		else
			LISTO=true;
			echo 'es true';
 		fi
		num=$(( num+1 ))
		echo $num
	done

done