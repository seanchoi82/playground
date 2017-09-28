<?php

include "dbconn.php";


$mail = $_REQUEST["mail"]; 
$posx = $_REQUEST["posx"]; 
$posy = $_REQUEST["posy"]; 

$sql = "update member set posx='$posx', posy='$posy', posdate=now() where mail='$mail'";	

$result=mysql_query($sql);
		
if ($result != FALSE)
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

