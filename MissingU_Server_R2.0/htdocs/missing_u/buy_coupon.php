<?php

include "dbconn.php";

$member_id = $_REQUEST["member_id"]; 
$buyPoint = $_REQUEST["buyPoint"];
$remarks = $_REQUEST["remarks"]; 

$sql = "insert into point (member_id, remarks, gubun, amount, mod_date ) ";
$sql .= "values ($member_id, '$remarks', '-', $buyPoint, now() )";

$result=mysql_query($sql);
		
if ($result != FALSE)
{
	// 성공
	$sql2 = "update member set point = point-$buyPoint, moddate = now() where member_id=$member_id ";
	$result2=mysql_query($sql2);
	echo("<result>1</result>");
}
else
{
	// 추가 실패 시 결제 취소 페이지로 이동
	echo("<result>2</result>");
	//echo $sql;
		
}

mysql_close();
?>

