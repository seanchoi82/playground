<?php

include "dbconn.php";
include "xml_writer.php";

$sql  = "select * , ";
$sql .= "(SELECT nick from member WHERE member_id = room.member_id) AS nick, ";
$sql .= "(SELECT gender from member WHERE member_id = room.member_id) AS gender, ";
$sql .= "(SELECT (YEAR(CURDATE()) - YEAR(birthday)) - (RIGHT(CURDATE(),5) < RIGHT(birthday,5)) from member WHERE member_id = room.member_id) AS age , ";
$sql .= "(SELECT area from member WHERE member_id = room.member_id) AS area ,";
$sql .= "(SELECT thumbnailphoto from member WHERE member_id = room.member_id) AS photo ";
$sql .= "from room where cur_user > 0";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);

if ($result != FALSE){
	// 성공
	
	if ($total_records > 0) {
		
		while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) 

		$output[]=$row;
		print(json_encode($output));
		
	} else {
		echo("<result>2</result>");	// 개설된 방이 없습니다
	}

} else {
	echo("<result>3</result>"); // 데이터베이스 에러입니다
	//echo("<query>" . $sql . "</query>");
}

mysql_close();
?>