<?php

include "dbconn.php";
include "xml_writer.php";

$member_id = $_REQUEST["member_id"]; 

$sql = "select * from daoupay where member_id=$member_id order by pay_date DESC limit 0, 30 ";
$result = mysql_query($sql);
$total_records = mysql_num_rows($result);

if ($result != FALSE){

	if ($total_records > 0) {
	
		$xml = new SimpleXmlWriter(); 
	
		header("Content-type: text/xml;charset=utf-8");
		header("Cache-Control: no-cache, must-revalidate");
		header("Pragma: no-cache");
		echo "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
		echo("<Values>");	
	
		while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) {

			$xml->Add('payid', $row['daoupay_id']);
			$xml->Add('remarks', $row['remarks']);
			$xml->Add('gubun', $row['gubun']);
			$xml->Add('amount', $row['amount']);
			if($row['status'] == null) {
				$xml->Add('status', "정상");
			} else {
				$xml->Add('status', $row['status']);
			}
			$xml->Add('mod_date', $row['pay_date']);
		}
		echo $xml->xml;
		echo("<result>1</result>");		// 성공
		echo("</Values>");		
	} else {
		echo("<result>2</result>");	// 등록된 포인트 내역이 없습니다
	}

	

} else {
	echo("<result>3</result>"); // 데이터베이스 에러입니다
	//echo("<query>" . $sql . "</query>");
}

mysql_close();
?>