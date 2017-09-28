<?php

include "dbconn.php";
include "xml_writer.php";

$mail = $_REQUEST["mail"]; 

//$page = $_REQUEST["page"]; 
$gender = $_REQUEST["gender"]; 
$age = $_REQUEST["age"]; 
$area = $_REQUEST["area"]; 
$appearance = $_REQUEST["appearance"]; 
$body = $_REQUEST["body"]; 
$purpose = $_REQUEST["purpose"]; 
$hobby = $_REQUEST["hobby"]; 
$drinking = $_REQUEST["drinking"]; 
$smoking = $_REQUEST["smoking"]; 
$posx = $_REQUEST["posx"]; 
$posy = $_REQUEST["posy"]; 
//$distance = $_REQUEST["distance"]; 


$sql = "select * from member where gender = '$gender'";
$sql.= "AND DATE_FORMAT(now(),'%Y')-substring(birthday,1,4) between substring('$age',1,2) and substring('$age',-2)";
$sql.= "AND area='$area'";
$sql.= "AND appearance='$appearance'";
$sql.= "AND body='$body'";
$sql.= "AND purpose='$purpose'";
$sql.= "AND hobby='$hobby'";
$sql.= "AND drinking='$drinking'";
$sql.= "AND smoking='$smoking'";


$result = mysql_query($sql);
$total_records = mysql_num_rows($result);


if ($total_records > 0)
{
	//echo("<result>1</result>");		// 성공

	$xml = new SimpleXmlWriter(); 
	
	header("Content-type: text/xml;charset=utf-8");
	header("Cache-Control: no-cache, must-revalidate");
	header("Pragma: no-cache");
	echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
	echo("<Values>");

	for ($i = 0; $i < $total_records; $i++)
	{
		$row = mysql_fetch_array($result);
		
		if (strcmp($row['mail'], $mail) == 0)
			continue;
		$xml->Add('FriendMail', $row['mail']);
		$xml->Add('FriendNick', $row['nick']);
		$xml->Add('FriendBirthDay', $row['birthday']);
		$xml->Add('FriendSex', $row['gender']);
		$xml->Add('FriendIntroduce', $row['introduction']);
		$xml->Add('FriendJoinReason', $row['purpose']);
		$xml->Add('FriendPosX', $row['posx']);
		$xml->Add('FriendPosY', $row['posy']);
		if(is_null($row['thumbnailphoto'])){
			$xml->Add('FriendPhoto', 'NULL');
		} else {
			$xml->Add('FriendPhoto', $row['thumbnailphoto']);
		}
		
		
		
	}

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

