<?php

include "dbconn.php";
include "xml_writer.php";

$fmgender = $_REQUEST["fmgender"]; 

$sql = "select count(*) as rec_count from member where gender='$fmgender' and facematchphoto is not NULL order by matchdate asc limit 32";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);

$row = mysql_fetch_array($result);



if ($row['rec_count'] > 31)
{

	$sql = "select mail, nick from member where gender='$fmgender' and facematchphoto is not NULL order by matchdate asc limit 32";
	$result = mysql_query($sql);
	$total_records = mysql_num_rows($result);

	$xml = new SimpleXmlWriter(); 
	
	header("Content-type: text/xml;charset=utf-8");
	header("Cache-Control: no-cache, must-revalidate");
	header("Pragma: no-cache");
	echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
	echo("<Values>");

	for ($i = 0; $i < $total_records; $i++)
	{
		$row = mysql_fetch_array($result);

		$xml->Add('Mail', $row['mail']);
		$xml->Add('Nick', $row['nick']);
	}

	echo $xml->xml;
	
	echo("<result>1</result>");		// 성공
	echo("</Values>");
}
else
{
	echo("<result>2</result>");		// 실패

}


mysql_close();


?>