<?php

include "dbconn.php";
include "xml_writer.php";

$FMNick = $_REQUEST["FMNick"]; 
$FMIsWin = $_REQUEST["FMIsWin"]; 
$FMIsLose = $_REQUEST["FMIsLose"]; 

echo("<result>1</result>");


$sql = "update member set fmwin=fmwin+$FMIsWin, fmwin2=fmwin2+$FMIsWin, fmwin3=fmwin3+$FMIsWin, fmlose=fmlose+$FMIsLose, fmlose2=fmlose2+$FMIsLose, fmlose3=fmlose3+$FMIsLose, matchdate = now() where nick='$FMNick'";

$result=mysql_query($sql);

mysql_close();


?>
