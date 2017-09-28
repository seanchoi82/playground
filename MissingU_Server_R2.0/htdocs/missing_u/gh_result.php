<?php

include "dbconn.php";
include "xml_writer.php";
include "./include/lunarTosolar.php";
include "./include/globalSaju.php";
include "./include/arrays.php";

$BirthDay = $_REQUEST["BirthDay"]; 
$EumYang = $_REQUEST["EumYang"];
$GHBirthDay = $_REQUEST["GHBirthDay"]; 
$GHEumYang = $_REQUEST["GHEumYang"];
$Blood = $_REQUEST["Blood"];
$GHBlood = $_REQUEST["GHBlood"];
$Gender = $_REQUEST["Gender"];

if ($EumYang == "양력") {
	$birthdate_i = substr($BirthDay, 0, 4).substr($BirthDay, 5, 2).substr($BirthDay, 8, 2);
} else if ($EumYang == "음력(윤달)"){
	$birthdate_i = ToSolar((int)substr($BirthDay, 0, 4),(int)substr($BirthDay, 5, 2),(int)substr($BirthDay, 8, 2),1);
} else {
	$birthdate_i = ToSolar((int)substr($BirthDay, 0, 4),(int)substr($BirthDay, 5, 2),(int)substr($BirthDay, 8, 2),0);
}

if ($GHEumYang == "양력") {
	$birthdate = substr($GHBirthDay, 0, 4).substr($GHBirthDay, 5, 2).substr($GHBirthDay, 8, 2);
} else if ($GHEumYang == "음력(윤달)"){
	$birthdate = ToSolar((int)substr($GHBirthDay, 0, 4),(int)substr($GHBirthDay, 5, 2),(int)substr($GHBirthDay, 8, 2),1);
} else {
	$birthdate = ToSolar((int)substr($GHBirthDay, 0, 4),(int)substr($GHBirthDay, 5, 2),(int)substr($GHBirthDay, 8, 2),0);
}

$solarbirth = substr($birthdate, 0, 4) . "-" . substr($birthdate, 4, 2) . "-" . substr($birthdate, 6, 2);

	$chongi_date = WhatChongi((int)substr($birthdate, 0, 4),(int)substr($birthdate, 4, 2),(int)substr($birthdate, 6, 2));
	
	$chongi_date_i = WhatChongi((int)substr($birthdate_i, 0, 4),(int)substr($birthdate_i, 4, 2),(int)substr($birthdate_i, 6, 2));

	$ohang = What5hangC((int)$chongi_date[0]);
	
	if ($Gender == "남자") {
		$GHInPoint = $arrGHIn[(int)$chongi_date_i[0]][(int)$chongi_date[0]];
		$GHInText = $arrGHInText[(int)$chongi_date_i[0]][(int)$chongi_date[0]];
		$GHJisu = $arrGHInJisu[(int)$chongi_date_i[0]][(int)$chongi_date[0]];
	} else {
		$GHInPoint = $arrGHIn[(int)$chongi_date[0]][(int)$chongi_date_i[0]];
		$GHInText = $arrGHInText[(int)$chongi_date[0]][(int)$chongi_date_i[0]];
		$GHJisu = $arrGHInJisu[(int)$chongi_date[0]][(int)$chongi_date_i[0]];
	}

	
	$samja_date = Tolunar((int)substr($birthdate, 0, 4),(int)substr($birthdate, 4, 2),(int)substr($birthdate, 6, 2));
	
	$samja_date_i = Tolunar((int)substr($birthdate_i, 0, 4),(int)substr($birthdate_i, 4, 2),(int)substr($birthdate_i, 6, 2));
	
	if ($Gender == "남자") {
		$GHGgPoint = $arrGHGg[(int)$samja_date_i[2]][(int)$samja_date[2]];
	} else {
		$GHGgPoint = $arrGHGg[(int)$samja_date[2]][(int)$samja_date_i[2]];
	}
	
	
	
	switch ($Blood) {
    case "A":
        $b_i = 0;
        break;
    case "B":
        $b_i = 1;
        break;
    case "O":
        $b_i = 2;
        break;
    case "AB":
        $b_i = 3;
        break;        
	}
	
	switch ($GHBlood) {
    case "A":
        $b = 0;
        break;
    case "B":
        $b = 1;
        break;
    case "O":
        $b = 2;
        break;
    case "AB":
        $b = 3;
        break;        
	}	
	
	$birthdayNumStr_i = substr($birthdate_i, 4, 2) . substr($birthdate_i, 6, 2);
	$birthdayNum_i = (int)$birthdayNumStr_i;
			
	if ($birthdayNum_i > 1222 || $birthdayNum_i < 121 ){ //염소자리 
		$starNum_i = 0;
	} else if ($birthdayNum_i > 120 && $birthdayNum_i < 220 ){ //물병자리 
		$starNum_i = 1;
	} else if ($birthdayNum_i > 219 && $birthdayNum_i < 321 ){ //물고기자리 
		$starNum_i = 2;
	} else if ($birthdayNum_i > 320 && $birthdayNum_i < 421 ){ //양자리 
		$starNum_i = 3;
	} else if ($birthdayNum_i > 420 && $birthdayNum_i < 522 ){ //황소자리 
		$starNum_i = 4;
	} else if ($birthdayNum_i > 521 && $birthdayNum_i < 622 ){ //쌍동이자리 
		$starNum_i = 5;
	} else if ($birthdayNum_i > 621 && $birthdayNum_i < 724 ){ //게자리 
		$starNum_i = 6;
	} else if ($birthdayNum_i > 723 && $birthdayNum_i < 824 ){ //사자자리 
		$starNum_i = 7;
	} else if ($birthdayNum_i > 823 && $birthdayNum_i < 924 ){ //처녀자리 
		$starNum_i = 8;
	} else if ($birthdayNum_i > 923 && $birthdayNum_i < 1024 ){ //천칭자리 
		$starNum_i = 9;
	} else if ($birthdayNum_i > 1023 && $birthdayNum_i < 1123 ){ //전갈자리 
		$starNum_i = 10;
	} else if ($birthdayNum_i > 1122 && $birthdayNum_i < 1223 ){ //궁수자리 
		$starNum_i = 11;
	}
	
	$birthdayNumStr = substr($birthdate, 4, 2) . substr($birthdate, 6, 2);
	$birthdayNum = (int)$birthdayNumStr;
			
	if ($birthdayNum > 1222 || $birthdayNum < 121 ){ //염소자리 
		$starNum = 0;
	} else if ($birthdayNum > 120 && $birthdayNum < 220 ){ //물병자리 
		$starNum = 1;
	} else if ($birthdayNum > 219 && $birthdayNum < 321 ){ //물고기자리 
		$starNum = 2;
	} else if ($birthdayNum > 320 && $birthdayNum < 421 ){ //양자리 
		$starNum = 3;
	} else if ($birthdayNum > 420 && $birthdayNum < 522 ){ //황소자리 
		$starNum = 4;
	} else if ($birthdayNum > 521 && $birthdayNum < 622 ){ //쌍동이자리 
		$starNum = 5;
	} else if ($birthdayNum > 621 && $birthdayNum < 724 ){ //게자리 
		$starNum = 6;
	} else if ($birthdayNum > 723 && $birthdayNum < 824 ){ //사자자리 
		$starNum = 7;
	} else if ($birthdayNum > 823 && $birthdayNum < 924 ){ //처녀자리 
		$starNum = 8;
	} else if ($birthdayNum > 923 && $birthdayNum < 1024 ){ //천칭자리 
		$starNum = 9;
	} else if ($birthdayNum > 1023 && $birthdayNum < 1123 ){ //전갈자리 
		$starNum = 10;
	} else if ($birthdayNum > 1122 && $birthdayNum < 1223 ){ //궁수자리 
		$starNum = 11;
	}	
	
	if ($Gender == "남자") {
		$GHTotalPoint = ceil(($GHInPoint + $GHGgPoint + $arrGHBlood[$b_i][$b] + $arrGHStarPoint[$arrGHStar[$starNum][$starNum_i]]) / 4);
	} else {
		$GHTotalPoint = ceil(($GHInPoint + $GHGgPoint + $arrGHBlood[$b][$b_i] + $arrGHStarPoint[$arrGHStar[$starNum_i][$starNum]]) / 4);
	}
	
	
	header("Content-type: text/xml;charset=utf-8");
	header("Cache-Control: no-cache, must-revalidate");
	header("Pragma: no-cache");
	echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
	echo("<Values>");

$xml = new SimpleXmlWriter(); 

$xml->Add('GHOhang', $ohang[0]);
$xml->Add('GHGg', $samja_date[2]);
$xml->Add('GHInPoint', $GHInPoint);
$xml->Add('GHGgPoint', $GHGgPoint);

if ($Gender == "남자") {
	$xml->Add('GHBloodPoint', $arrGHBlood[$b_i][$b]);
	$xml->Add('GHBloodPointText', $arrGHBloodText[$b_i][$b]);
	$xml->Add('GHStarPoint', $arrGHStarPoint[$arrGHStar[$starNum][$starNum_i]]);
	$xml->Add('GHStarPointText', $arrGHStarText[$arrGHStar[$starNum][$starNum_i]]);
} else {
	$xml->Add('GHBloodPoint', $arrGHBlood[$b][$b_i]);
	$xml->Add('GHBloodPointText', $arrGHBloodText[$b][$b_i]);
	$xml->Add('GHStarPoint', $arrGHStarPoint[$arrGHStar[$starNum_i][$starNum]]);
	$xml->Add('GHStarPointText', $arrGHStarText[$arrGHStar[$starNum_i][$starNum]]);
}

$xml->Add('GHSolarBirth', $solarbirth);
$xml->Add('GHTotalPoint', $GHTotalPoint);
$xml->Add('GHInText', $GHInText);
$xml->Add('GHJisu1', substr($GHJisu,0,1));
$xml->Add('GHJisu2', substr($GHJisu,1,1));
$xml->Add('GHJisu3', substr($GHJisu,2,1));
$xml->Add('GHJisu4', substr($GHJisu,3,1));
//GHInText
//GHGgText
echo $xml->xml;
	echo("<result>1</result>");		// 성공
	echo("</Values>");

mysql_close();


?>

