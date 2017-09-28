<?php

include "dbconn.php";


$mail = $_REQUEST["mail"]; 



$sql = "select * from member where mail='$mail'";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);


if ($total_records == 0)
{
	// 성공
	echo("<result>1</result>");
}
else
{
	// 실패
	echo("<result>2</result>");
}


mysql_close();


?>