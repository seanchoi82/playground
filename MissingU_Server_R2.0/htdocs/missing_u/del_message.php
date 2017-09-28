<?php

include "dbconn.php";
include "xml_writer.php";

$member_id = $_REQUEST["member_id"];
$box_user_id = $_REQUEST["box_user_id"];

$sql = "update message set receiver_read = 'D' where sender_id = $box_user_id and receiver_id = $member_id ";

$result = mysql_query($sql);


if ($result != FALSE){
	// 성공
	
	echo("<result>1</result>");	// 개설된 방이 없습니다

} else {
	echo("<result>2</result>"); // 데이터베이스 에러입니다
	echo("<query>" . $sql . "</query>");
}

mysql_close();
?>