<?php

include "dbconn.php";
include "xml_writer.php";

$id = $_REQUEST["id"]; 
$nick = $_REQUEST["nick"]; 
$room_name = $_REQUEST["room_name"]; 
$memo = $_REQUEST["memo"]; 
if (isset($_REQUEST["room_pass"])){
	$room_pass = $_REQUEST["room_pass"];
} else {
	$room_pass = null;
}
 
$max_user = $_REQUEST["max_user"]; 

$sql = "select * from room where member_id = $id and cur_user > 0";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);

header("Content-type: text/xml;charset=utf-8");
header("Cache-Control: no-cache, must-revalidate");
header("Pragma: no-cache");
echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
echo("<values>");

echo("<total_records>" . $total_records . "</total_records>");

if ($total_records == 0)
{
	$sql = "insert into room (room_pass, room_name, member_id, cur_user, max_user, memo, reg_date) ";
	$sql .= "values ('$room_pass', '$room_name', $id, 1, $max_user, '$memo', now() )";
	$result=mysql_query($sql);
	$RoomId = mysql_insert_id();
	if ($result == TRUE)
	{
		echo("<result>1</result>");


		$xml = new SimpleXmlWriter(); 

		$xml->Add('RoomId', $RoomId);
		$xml->Add('MaxUser', $max_user);

		echo $xml->xml;

	}
	else
	{
		echo("<result>2</result>"); //DB 에러
		//echo("<query>" . $sql . "</query>");
	}
}
else
{
	echo("<result>3</result>"); //이미 개설된 방이 있음
}

echo("</values>");

mysql_close();


?>
