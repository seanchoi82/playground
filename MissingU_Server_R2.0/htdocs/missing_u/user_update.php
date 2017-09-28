<?php

include "dbconn.php";
include "xml_writer.php";
include "./include/lunarTosolar.php";

$Mail = $_REQUEST["Mail"]; 
$Password = $_REQUEST["Password"]; 
$Nick = $_REQUEST["Nick"]; 
$Sex = $_REQUEST["Sex"]; 
$Blood = $_REQUEST["Blood"]; 
$birthday = $_REQUEST["BirthDay"]; 
$BirthTime = $_REQUEST["BirthTime"]; 
$Form = $_REQUEST["Form"]; 
$Body = $_REQUEST["Body"]; 
$JoinReason = $_REQUEST["JoinReason"]; 
$Hoby = $_REQUEST["Hoby"]; 
$Drunken = $_REQUEST["Drunken"]; 
$Smoking = $_REQUEST["Smoking"]; 
$Introduce = $_REQUEST["Introduce"]; 
$EumYang = $_REQUEST["EumYang"]; 

$arrBirthday = explode("년",$birthday);
$tempStr = mb_substr($birthday, mb_strlen($arrBirthday[0]) + 2, mb_strlen($birthday), 'UTF-8');
$arrBirthday2 = explode("월",$tempStr);
$tempStr2 = mb_substr($tempStr, mb_strlen($arrBirthday2[0]) + 2, mb_strlen($tempStr), 'UTF-8');
$arrBirthday3 = explode("일",$tempStr2);
$birthdate = $arrBirthday[0] . "-" . $arrBirthday2[0] . "-" . $arrBirthday3[0];


$sql = "update member set pass='$Password', nick='$Nick', gender='$Sex', blood_type='$Blood', birthday=date('$birthdate'), eumyang='$EumYang', birthtime='$BirthTime', appearance='$Form', body='$Body', purpose='$JoinReason', hobby='$Hoby', drinking='$Drunken', smoking='$Smoking', introduction='$Introduce', moddate = now() where mail='$Mail'";


$result=mysql_query($sql);
		
if ($result != FALSE)
{

	if ($EumYang == "양력") {
		$solarbirth = $birthdate;
	} else if ($EumYang == "음력(윤달)"){
		$birthdate_solar = ToSolar((int)substr($birthdate, 0, 4),(int)substr($birthdate, 5, 2),(int)substr($birthdate, 8, 2),1);
		$solarbirth = substr($birthdate_solar, 0, 4) . "-" . substr($birthdate_solar, 4, 2) . "-" . substr($birthdate_solar, 6, 2);
	} else {
		$birthdate_solar = ToSolar((int)substr($birthdate, 0, 4),(int)substr($birthdate, 5, 2),(int)substr($birthdate, 8, 2),0);
		$solarbirth = substr($birthdate_solar, 0, 4) . "-" . substr($birthdate_solar, 4, 2) . "-" . substr($birthdate_solar, 6, 2);
	}
	
	header("Content-type: text/xml;charset=utf-8");
	header("Cache-Control: no-cache, must-revalidate");
	header("Pragma: no-cache");
	echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
	echo("<Values>");
	
	$xml = new SimpleXmlWriter(); 
	$xml->Add('SolarBirth', $solarbirth);
	echo $xml->xml;
	echo("<result>1</result>");		// 성공
	echo("</Values>");
}
else
{
	// 실패
	echo("<result>2</result>");
}


mysql_close();


?>

