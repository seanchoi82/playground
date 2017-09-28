<?php

include "dbconn.php";
include "xml_writer.php";

$member_id = $_REQUEST["member_id"]; 

$sql = "select friend_id from friends where member_id = $member_id and status= 'F' ";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);

if ($result != FALSE){

	

	while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {
		$id = $row['friend_id'];
		$sql2  = "select member_id,mail,nick,birthday,gender,introduction,purpose,posx,posy,thumbnailphoto from member where member_id = $id";

	
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