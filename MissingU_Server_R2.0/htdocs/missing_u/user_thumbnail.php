<?php
include "dbconn.php";
include "xml_writer.php";

$memberId = $_REQUEST["id"];  


$sql = "select * from member where member_id=$memberId";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);


if ($total_records == 1)
{
	$row=mysql_fetch_array($result);

	echo($row['thumbnailphoto']);

} else {

	echo("0");

}


mysql_close();


?>