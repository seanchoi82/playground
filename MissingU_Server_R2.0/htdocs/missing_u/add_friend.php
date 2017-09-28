<?php

include "dbconn.php";


$member_id = $_REQUEST["member_id"]; 
$friend_id = $_REQUEST["friend_id"]; 

$sql = "select count(*) as count from friends where member_id=$member_id and friend_id=$friend_id ";
$result=mysql_query($sql);

if ($result != FALSE)
{
	$row=mysql_fetch_array($result);
	$count = 0 + $row['count'];
	if ( $count == 0)
	{
		$sql = "insert into friends (member_id, friend_id, reg_date, status ) ";
		$sql .= "values ($member_id, $friend_id, now(), 'F' )";

		$result=mysql_query($sql);
		
		if ($result != FALSE)
		{
			// 성공
			echo("<result>1</result>");
		}
		else
		{
			// 추가 실패
			echo("<result>2</result>");
			echo $sql;
		
		}
		
	} else {
		//이미 등록된 친구
		echo("<result>3</result>");
	}

}
else
{
	// 추가 실패
	echo("<result>2</result>");

		
}

mysql_close();


?>