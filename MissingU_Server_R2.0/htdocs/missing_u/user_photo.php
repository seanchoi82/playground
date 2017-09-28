<?php

// !!! 위아래에 공백이 있으면 안됨..그것까지 echo가 되버리기 때문에..xml로 쏴주면 좋은데..잘 안되어서 일단 이렇게 해 놓았음

include "dbconn.php";
include "xml_writer.php";

$Mail = $_REQUEST["Mail"]; 
$Num = $_REQUEST["Num"]; 


$sql = "select * from member where mail='$Mail'";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);


if ($total_records == 1)
{
	$row=mysql_fetch_array($result);

	if ($Num == 0)
		echo($row['facematchphoto']);
	if ($Num == 1)
		echo($row['photo1']);
	if ($Num == 2)
		echo($row['photo2']);
	if ($Num == 3)
		echo($row['photo3']);
	if ($Num == 4)
		echo($row['photo4']);
	if ($Num == 5)
		echo($row['photo5']);
	if ($Num == 6)
		echo($row['photo6']);
	if ($Num == 7)
		echo($row['photo7']);
	if ($Num == 8)
		echo($row['photo8']);
	if ($Num == 9)
		echo($row['photo9']);
}


mysql_close();


?>