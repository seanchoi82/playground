<?php

include "dbconn.php";
include "xml_writer.php";
include "./include/lunarTosolar.php";
include "./include/globalSaju.php";

$mail = $_REQUEST["mail"]; 
$pass = $_REQUEST["pass"]; 
$regid = $_REQUEST["regid"];


$sql = "select * from member where mail='$mail'";
$result_mail = mysql_query($sql);
$total_records_mail = mysql_num_rows($result_mail);

$sql = "select * from member where mail='$mail' and pass='$pass'";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);



if ($total_records == 1)
{

	$xml = new SimpleXmlWriter(); 

	$row=mysql_fetch_array($result);
	
	if($row['reg_id'] != $regid) {
		$sql2 = "update member set reg_id='$regid' where mail='$mail'";

		$result2 = mysql_query($sql2);
	}
	
	if ($row['eumyang'] == "양력") {
		$birthdate = substr($row['birthday'], 0, 4).substr($row['birthday'], 5, 2).substr($row['birthday'], 8, 2);
	} else if ($row['eumyang'] == "음력(윤달)"){
		$birthdate = ToSolar((int)substr($row['birthday'], 0, 4),(int)substr($row['birthday'], 5, 2),(int)substr($row['birthday'], 8, 2),1);
	} else {
		$birthdate = ToSolar((int)substr($row['birthday'], 0, 4),(int)substr($row['birthday'], 5, 2),(int)substr($row['birthday'], 8, 2),0);
	}

	$chongi_date = WhatChongi(substr($birthdate, 0, 4),substr($birthdate, 4, 2),substr($birthdate, 6, 2));

	$ohang = What5hangC($chongi_date[0]);
	
	$samja_date = Tolunar(substr($birthdate, 0, 4),substr($birthdate, 4, 2),substr($birthdate, 6, 2));
	
	$solarbirth = substr($birthdate, 0, 4) . "-" . substr($birthdate, 4, 2) . "-" . substr($birthdate, 6, 2);
	
	header("Content-type: text/xml;charset=utf-8");
	header("Cache-Control: no-cache, must-revalidate");
	header("Pragma: no-cache");
	echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
	echo("<Values>");	
	
	$xml->Add('Id', $row['member_id']);
	$xml->Add('Mail', $row['mail']);
	$xml->Add('Password', $row['pass']);
	$xml->Add('Nick', $row['nick']);
	$xml->Add('Sex', $row['gender']);
	$xml->Add('Blood', $row['blood_type']);
	$xml->Add('BirthDay', $row['birthday']);
	$xml->Add('SolarBirth', $solarbirth);
	$xml->Add('EumYang', $row['eumyang']);
	$xml->Add('BirthTime', $row['birthtime']);
	$xml->Add('Form', $row['appearance']);
	$xml->Add('Body', $row['body']);
	$xml->Add('JoinReason', $row['purpose']);
	$xml->Add('Hoby', $row['hobby']);
	$xml->Add('Drunken', $row['drinking']);
	$xml->Add('Smoking', $row['smoking']);
	$xml->Add('Introduce', $row['introduction']);
	$xml->Add('Ohang', $ohang[0]);
	$xml->Add('Gg', $samja_date[2]);
	$xml->Add('Photo', $row['photo1']);
	$xml->Add('Photo2', $row['photo2']);
	$xml->Add('Photo3', $row['photo3']);
	$xml->Add('Photo4', $row['photo4']);
	$xml->Add('Photo5', $row['photo5']);
	$xml->Add('Photo6', $row['photo6']);
	$xml->Add('Photo7', $row['photo7']);
	$xml->Add('Photo8', $row['photo8']);
	$xml->Add('Photo9', $row['photo9']);
	$xml->Add('ThumbnailPhoto', $row['thumbnailphoto']);
	$xml->Add('PosX', $row['posx']);
	$xml->Add('PosY', $row['posy']);

	echo $xml->xml;
	echo("<result>1</result>");		// 성공
	echo("</Values>");
}
else
{
	if ($total_records_mail == 0)
	{
		echo("<result>2</result>");	// 계정 없음
	}
	else
	{
		echo("<result>3</result>");	// 비번 틀림
	}
}


mysql_close();


?>