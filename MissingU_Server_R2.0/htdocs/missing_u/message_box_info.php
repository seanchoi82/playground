<?php

include "dbconn.php";
include "xml_writer.php";

$member_id = $_REQUEST["member_id"]; 

$sql = "select distinct sender_id from message where receiver_id = $member_id and receiver_read != 'D' ";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);

if ($result != FALSE){

	

	while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
		$id = $row['sender_id'];
		$sql2  = "select member_id, gender, nick, (select DATE_FORMAT(max(send_date), '%y.%m.%d') from message where sender_id = $id or receiver_id = $id) as send_date, ";
		$sql2 .= "(select message from message where sender_id = $id or receiver_id = $id order by send_date DESC limit 1) as message, ";
		$sql2 .= "(select count(*) from message where sender_id = $id and receiver_id = $member_id and receiver_read = 'N') as count, ";
		$sql2 .= "thumbnailphoto as photo from member where member_id = $id ";
	
		$result2 = mysql_query($sql2);
		$total_records2 = mysql_num_rows($result2);
	
		if ($result2 != FALSE){
		// 성공
	
			if ($total_records2 > 0) {
		
				while ($row2 = mysql_fetch_array($result2, MYSQL_ASSOC)) 

				$output[]=$row2;
				//print(json_encode($output));
				//print(json_encode($row2));
		
			} 

		} else {
			echo("<result>3</result>"); // 데이터베이스 에러입니다
			//echo("<query>" . $sql . "</query>");
		}	
	}

	if ($total_records > 0) {
		print(json_encode($output));
	} else {
		echo("<result>2</result>");	// 개설된 방이 없습니다
	}

	

} else {
	echo("<result>4</result>"); // 데이터베이스 에러입니다
	//echo("<query>" . $sql . "</query>");
}

mysql_close();
?>