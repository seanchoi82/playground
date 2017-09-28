<?php

include "dbconn.php";
include "xml_writer.php";

$member_id = $_REQUEST["member_id"]; 

$sql = "select * from member where member_id=$member_id limit 1 ";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);

if ($result != FALSE){

	if ($total_records > 0) {
	
		$row=mysql_fetch_array($result);
	
		$xml = new SimpleXmlWriter(); 
	
		header("Content-type: text/xml;charset=utf-8");
		header("Cache-Control: no-cache, must-revalidate");
		header("Pragma: no-cache");
		echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		echo("<Values>");	

		$xml->Add('my_point', $row['point']);
		
		echo $xml->xml;
		echo("<result>1</result>");		// 성공
		echo("</Values>");		
	} else {
		echo("<result>2</result>");	// 유저정보가 없습니다
	}

	

} else {
	echo("<result>3</result>"); // 데이터베이스 에러입니다
	//echo("<query>" . $sql . "</query>");
}

mysql_close();
?>





