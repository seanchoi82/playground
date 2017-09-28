<?php

include "dbconn.php";
include "xml_writer.php";

$room_id = $_REQUEST["room_id"]; 

$sql = "select * from room where room_id = $room_id and cur_user > 0";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);

//echo("<total_records>" . $total_records . "</total_records>");
header("Content-type: text/xml;charset=utf-8");
header("Cache-Control: no-cache, must-revalidate");
header("Pragma: no-cache");
echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
echo("<values>");

if ($total_records != 0)
{
	$sql = "UPDATE room SET guest1_id = NULL, guest2_id = NULL, guest3_id = NULL, cur_user = 0 WHERE room_id = $room_id ";
	
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
}
else
{
	echo("<result>3</result>"); //개설된 방이 없음
}

echo("</values>");
mysql_close();


?>
