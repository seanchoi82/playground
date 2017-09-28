<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ko">
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/header.jsp"%>
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<style type="text/css">
	body { background: url(/img/bg_gray_nopad.png); }
</style>
<title>Result Page</title>

</head>
<body>
	<div class="page">
		
		<div class="ui-box ui-box-white ui-box-shadow">
			
			<h2 class="icon-arrow-green">Result</h2>
			
			<table class="tbl_nor" cellspacing="0" summary="Result">
				<caption>Result</caption>
				<colgroup>
					<col width="140">
				</colgroup>
				<tr>
					<th>Result</th>
					<td><span id="resultMsg">${resultMsg}</span></td>
				</tr>
			</table>
			
		</div>
		
	</div>
</body>
</html>