<?php

include "dbconn.php";
include "xml_writer.php";

$mail = $_REQUEST["mail"]; 


$sql = "select * from member where mail='$mail'";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);



if ($total_records > 0)
{
	//echo("<result>1</result>");		// 성공

	$xml = new SimpleXmlWriter(); 

	$row = mysql_fetch_array($result);
	
	header("Content-type: text/xml;charset=utf-8");
	header("Cache-Control: no-cache, must-revalidate");
	header("Pragma: no-cache");
	echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
	echo("<Values>");	
	
	$xml->Add('OrgPhoto1', $row['orgphoto1']);
	$xml->Add('OrgPhoto2', $row['orgphoto2']);
	$xml->Add('OrgPhoto3', $row['orgphoto3']);
	$xml->Add('OrgPhoto4', $row['orgphoto4']);
	$xml->Add('OrgPhoto5', $row['orgphoto5']);
	$xml->Add('OrgPhoto6', $row['orgphoto6']);
	$xml->Add('OrgPhoto7', $row['orgphoto7']);
	$xml->Add('OrgPhoto8', $row['orgphoto8']);
	$xml->Add('OrgPhoto9', $row['orgphoto9']);

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

