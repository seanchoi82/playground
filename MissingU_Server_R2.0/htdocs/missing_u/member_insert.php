<?php

include "dbconn.php";


$mail = $_REQUEST["mail"]; 
$pass = $_REQUEST["pass"]; 
$nick = $_REQUEST["nick"]; 
$gender = $_REQUEST["gender"]; 
$blood_type = $_REQUEST["blood_type"]; 
$birthday = $_REQUEST["birthday"]; 
$birthtime = $_REQUEST["birthtime"]; 
$appearance = $_REQUEST["appearance"]; 
$body = $_REQUEST["body"]; 
$area = $_REQUEST["area"]; 
$purpose = $_REQUEST["purpose"]; 
$hobby = $_REQUEST["hobby"]; 
$drinking = $_REQUEST["drinking"]; 
$smoking = $_REQUEST["smoking"]; 
$introduction = $_REQUEST["introduction"];
$eumyang = $_REQUEST["eumyang"]; 
$photo1 = $_REQUEST["photo1"]; 
$photo2 = $_REQUEST["photo2"]; 
$photo3 = $_REQUEST["photo3"]; 
$photo4 = $_REQUEST["photo4"]; 
$photo5 = $_REQUEST["photo5"]; 
$photo6 = $_REQUEST["photo6"]; 
$photo7 = $_REQUEST["photo7"]; 
$photo8 = $_REQUEST["photo8"]; 
$photo9 = $_REQUEST["photo9"]; 
//$orgphoto1 = $_REQUEST["orgphoto1"]; 
//$orgphoto2 = $_REQUEST["orgphoto2"]; 
//$orgphoto3 = $_REQUEST["orgphoto3"]; 
//$orgphoto4 = $_REQUEST["orgphoto4"]; 
//$orgphoto5 = $_REQUEST["orgphoto5"]; 
//$orgphoto6 = $_REQUEST["orgphoto6"]; 
//$orgphoto7 = $_REQUEST["orgphoto7"]; 
//$orgphoto8 = $_REQUEST["orgphoto8"]; 
//$orgphoto9 = $_REQUEST["orgphoto9"]; 
//$facematchphoto = $_REQUEST["facematchphoto"]; 
$thumbnailphoto = $_REQUEST["thumbnailphoto"]; 
//$tipphoto = $_REQUEST["tipphoto"]; 

$arrBirthday = explode("년",$birthday);
$tempStr = mb_substr($birthday, mb_strlen($arrBirthday[0]) + 2, mb_strlen($birthday), 'UTF-8');
$arrBirthday2 = explode("월",$tempStr);
$tempStr2 = mb_substr($tempStr, mb_strlen($arrBirthday2[0]) + 2, mb_strlen($tempStr), 'UTF-8');
$arrBirthday3 = explode("일",$tempStr2);
$birthdate = $arrBirthday[0] . "-" . $arrBirthday2[0] . "-" . $arrBirthday3[0];

//echo $birthdate;

$sql = "select * from member where mail='$mail' or nick='$nick'";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);


if ($total_records == 0)
{
	$sql = "insert into member (mail, pass, nick, gender, blood_type, birthday, eumyang, birthtime, appearance, body, area, purpose, hobby, drinking, smoking, regdate, moddate, status, introduction, ";
	$sql .= "photo1, photo2, photo3, photo4, photo5, photo6, photo7, photo8, photo9, ";
	//$sql .= "orgphoto1, orgphoto2, orgphoto3, orgphoto4, orgphoto5, orgphoto6, orgphoto7, orgphoto8, orgphoto9, ";
	$sql .= "thumbnailphoto ) ";
	$sql .= "values ('$mail', '$pass', '$nick', '$gender', '$blood_type', date(replace('$birthdate','선택안함--','99991231')), '$eumyang', '$birthtime', '$appearance', '$body', '$area', '$purpose', '$hobby', '$drinking', '$smoking', now(), now(), 0, '$introduction', ";
	$sql .= "'$photo1', '$photo2', '$photo3', '$photo4', '$photo5', '$photo6', '$photo7', '$photo8', '$photo9', ";
	//$sql .= "'$orgphoto1', '$orgphoto2', '$orgphoto3', '$orgphoto4', '$orgphoto5', '$orgphoto6', '$orgphoto7', '$orgphoto8', '$orgphoto9', ";
	$sql .= "'$thumbnailphoto' )";

	$result=mysql_query($sql);
		
	if ($result != FALSE)
	{
		// 성공
		echo("<result>1</result>");
	}
	else
	{
		// 추가 실패
		echo("<result>2</result>");
		echo $sql;
		
	}
}
else
{
	// 아이디 혹은 닉네임 중복
	echo("<result>3</result>");
}



mysql_close();


?>