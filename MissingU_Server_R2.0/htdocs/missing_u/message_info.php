<?php

include "dbconn.php";
include "xml_writer.php";

$member_id = $_REQUEST["member_id"];
$box_user_id = $_REQUEST["box_user_id"];

$sql = "select sender_id, message, (select thumbnailphoto from member where member_id=sender_id) as photo , unix_timestamp(send_date) as send_date from message where (receiver_id = $member_id and receiver_read != 'D' and sender_id = $box_user_id) or ";
$sql .= " (receiver_id = $box_user_id and sender_id = $member_id) ";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);


if ($result != FALSE){
	// 성공
	
	if ($total_records > 0) {
		
		while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) 

		$output[]=$row;
		print(json_encode($output));
		
		$sql2 = "update message set receiver_read = 'Y' where sender_id = $box_user_id and receiver_id = $member_id ";
		$result2 = mysql_query($sql2);
		
	} else {
		echo("<result>2</result>");	// 개설된 방이 없습니다
	}

} else {
	echo("<result>3</result>"); // 데이터베이스 에러입니다
	//echo("<query>" . $sql . "</query>");
}

mysql_close();
?>