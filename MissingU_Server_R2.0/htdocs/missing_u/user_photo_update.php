<?php

include "dbconn.php";


$Mail = $_REQUEST["Mail"]; 
$Photo = $_REQUEST["Photo"]; 
$Num = $_REQUEST["Num"]; 

if ($Num == 1)
	$sql = "update member set photo1='$Photo' where mail='$Mail'";
else if ($Num == 2)
	$sql = "update member set photo2='$Photo' where mail='$Mail'";
else if ($Num == 3)
	$sql = "update member set photo3='$Photo' where mail='$Mail'";
else if ($Num == 4)
	$sql = "update member set photo4='$Photo' where mail='$Mail'";
else if ($Num == 5)
	$sql = "update member set photo5='$Photo' where mail='$Mail'";
else if ($Num == 6)
	$sql = "update member set photo6='$Photo' where mail='$Mail'";
else if ($Num == 7)
	$sql = "update member set photo7='$Photo' where mail='$Mail'";
else if ($Num == 8)
	$sql = "update member set photo8='$Photo' where mail='$Mail'";
else if ($Num == 9)
	$sql = "update member set photo9='$Photo' where mail='$Mail'";
else if ($Num == 10)
	$sql = "update member set facematchphoto='$Photo' where mail='$Mail'";
else if ($Num == 11)
	$sql = "update member set thumbnailphoto='$Photo' where mail='$Mail'";
else if ($Num == 12)
	$sql = "update member set tipphoto='$Photo' where mail='$Mail'";	

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

