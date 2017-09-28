<?php

include "dbconn.php";
include "xml_writer.php";

$room_id = $_REQUEST["room_id"]; 
$guest_id = $_REQUEST["guest_id"]; 

$sql = "select count(*) as count, cur_user from room where room_id = $room_id and cur_user > 0 ";
$result = mysql_query($sql);
$row=mysql_fetch_array($result);
$total_records = $row['count'];

//echo("<sql>" . $sql . "</sql>");

header("Content-type: text/xml;charset=utf-8");
header("Cache-Control: no-cache, must-revalidate");
header("Pragma: no-cache");
echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
echo("<values>");

if ($total_records != 0) {

	$cur_user = $row['cur_user'];

	switch($cur_user){
	case 3:
		$sql = "UPDATE room SET cur_user = cur_user + 1, guest3_id = $guest_id WHERE room_id = $room_id ";
		break;
	case 2:
		$sql = "UPDATE room SET cur_user = cur_user + 1, guest2_id = $guest_id WHERE room_id = $room_id ";
		break;
	case 1:
		$sql = "UPDATE room SET cur_user = cur_user + 1, guest1_id = $guest_id WHERE room_id = $room_id ";
		break;
	}

	$result=mysql_query($sql);
	
	if ($result == TRUE)
	{
		echo("<result>1</result>");

	}
	else
	{
		echo("<result>2</result>"); //DB 에러
		echo("<query>" . $sql . "</query>");
	}	

	
} else {
	echo("<result>3</result>"); //개설된 방이 없음
}

//echo("<total_records>" . $total_records . "</total_records>");

echo("</values>");
mysql_close();


?>
