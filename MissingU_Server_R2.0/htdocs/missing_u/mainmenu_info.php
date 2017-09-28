<?php

include "dbconn.php";
include "xml_writer.php";

$member_id = $_REQUEST["member_id"]; 

$sql = "select count(*) as unreadmsgcount from message where receiver_id = $member_id and receiver_read = 'N' ";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);

if ($result != FALSE){
	$row = mysql_fetch_array($result);
	echo("<unreadmsgcount>". $row['unreadmsgcount'] . "</unreadmsgcount>");
	echo("<result>1</result>"); //성공

} else {
	echo("<result>2</result>"); // 데이터베이스 에러입니다
	//echo("<query>" . $sql . "</query>");
}

mysql_close();
?>