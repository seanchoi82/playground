<?php

include "dbconn.php";
include "xml_writer.php";

$room_id = $_REQUEST["room_id"]; 
$guest_id = $_REQUEST["guest_id"]; 

$total_records = 0;

$sql = "select count(*) as count, cur_user from room where room_id = $room_id and cur_user > 0 and guest1_id = $guest_id ";
$result = mysql_query($sql);
$row=mysql_fetch_array($result);
$total_records1 = $row['count'];

//echo("<sql>" . $sql . "</sql>");
header("Content-type: text/xml;charset=utf-8");
header("Cache-Control: no-cache, must-revalidate");
header("Pragma: no-cache");
echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
echo("<values>");

if ($total_records1 != 0) {

	$total_records = $total_records + $total_records1;

	$cur_user = $row['cur_user'];
	
	switch($cur_user){
	case 4:
		$sql = "UPDATE room SET  cur_user = cur_user - 1, guest1_id = guest3_id WHERE room_id = $room_id ";
		break;
	case 3:
		$sql = "UPDATE room SET  cur_user = cur_user - 1, guest1_id = guest2_id WHERE room_id = $room_id ";
		break;
	case 2:
		$sql = "UPDATE room SET  cur_user = cur_user - 1 WHERE room_id = $room_id ";
		break;		
	}
	
	$result=mysql_query($sql);
	
	if ($result != TRUE)
	{
		echo("<result>2</result>"); //DB 에러
		echo("<query>" . $sql . "</query>");

	}

	switch($cur_user){
	case 4:
		$sql = "UPDATE room SET  guest3_id = NULL WHERE room_id = $room_id ";
		break;
	case 3:
		$sql = "UPDATE room SET  guest2_id = NULL WHERE room_id = $room_id ";
		break;
	case 2:
		$sql = "UPDATE room SET  guest1_id = NULL WHERE room_id = $room_id ";
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
	$sql = "select count(*) as count, cur_user from room where room_id = $room_id and cur_user > 0 and guest2_id = $guest_id ";
	$result = mysql_query($sql);
	$row=mysql_fetch_array($result);
	$total_records2 = $row['count'];
	
	if ($total_records2 != 0) {

		$total_records = $total_records + $total_records2;

		$cur_user = $row['cur_user'];
	
		switch($cur_user){
		case 4:
			$sql = "UPDATE room SET  cur_user = cur_user - 1, guest2_id = guest3_id WHERE room_id = $room_id ";
			break;
		case 3:
			$sql = "UPDATE room SET  cur_user = cur_user - 1 WHERE room_id = $room_id ";
			break;	
		}
	
		$result=mysql_query($sql);
	
		if ($result != TRUE)
		{
			echo("<result>2</result>"); //DB 에러
			echo("<query>" . $sql . "</query>");

		}

		switch($cur_user){
		case 4:
			$sql = "UPDATE room SET  guest3_id = NULL WHERE room_id = $room_id ";
			break;
		case 3:
			$sql = "UPDATE room SET  guest2_id = NULL WHERE room_id = $room_id ";
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
		$sql = "select count(*) as count, cur_user from room where room_id = $room_id and cur_user > 0 and guest3_id = $guest_id ";
		$result = mysql_query($sql);
		$row=mysql_fetch_array($result);
		$total_records3 = $row['count'];
		
		if ($total_records3 != 0) {

			$total_records = $total_records + $total_records3;

			$sql = "UPDATE room SET cur_user = cur_user - 1, guest3_id = NULL WHERE room_id = $room_id ";

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
	}
}

//echo("<total_records>" . $total_records . "</total_records>");

if ($total_records == 0)
{
	echo("<result>3</result>"); //개설된 방이 없음
}

echo("</values>");
mysql_close();


?>
