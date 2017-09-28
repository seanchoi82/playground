<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma" content="no-cache"/>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui.js"></script>
<script type="text/javascript" src="/js/jquery.ui.datepicker-ko.min.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<link rel="stylesheet" type="text/css" href="/css/missingu.css">
<style type="text/css">
	body { background: url(/img/bg_gray_nopad.png); }
</style>
<title>본인인증</title>

</head>
<body>

<form:form id="oneselfCert" modelAttribute="oneselfCertReqVO" action="/missingu/theshop/oneselfCertSubmit.html" method="POST">
	<div class="page">
		
		<div class="ui-box ui-box-white ui-box-shadow">
			
			<h2 class="icon-arrow-green">본인인증 정보 입력</h2>
			
			<table class="tbl_nor" cellspacing="0" summary="본인인증 정보 입력">
				<caption>본인인증 정보 입력</caption>
				<colgroup>
					<col width="140">
				</colgroup>
				<tr>
					<th>설명</th>
					<td>※ 본인인증이 필요한 서비스입니다.<br>
					※ 고객님께 쾌적한 서비스를 제공하기 위함이며, 최초 1회만 하시면 됩니다.<br>
				</tr>
				<tr>
					<th>인증방식</th>
					<td>
						<input type="radio" name="authType" value="M" checked="checked"/>핸드폰
						<input type="radio" name="authType" value="C" disabled="disabled"/>신용카드
						<input type="radio" name="authType" value="X" disabled="disabled"/>공인인증
					</td>
				</tr>
				<!-- 
				<tr>
					<th>주민번호</th>
					<td>
						<!-- <input type="text" id="juminIdPrefix" maxlength="6"> - <input type="text" id="juminIdSuffix" maxlength="7"> -- >
						<form:input path="juminIdPrefix" maxlength="6" size="10" /> - <form:input path="juminIdSuffix" maxlength="7" size="10"/> 
					</td>
				</tr>
				 -->
			</table>
			<input name="gMemberId" type="hidden" value="${paramMap.gMemberId}">
			<input name="gLang" type="hidden" value="${paramMap.gLang}">
			
			<div class="ui-btns">
				<button id="btn_confirm">확인</button>
				<button id="btn_cancel">취소</button>
			</div>
			
		</div>
		
	</div>
</form:form>

<script type="text/javascript">

	$("#btn_confirm").click(function(){
		/*
		if($("#juminIdPrefix").val() == null || $("#juminIdPrefix").val() == '') {
			alert("주민번호를 입력해 주세요.");
			return false;
		}
		if($("#juminIdSuffix").val() == null || $("#juminIdSuffix").val() == '') {
			alert("주민번호를 입력해 주세요.");
			return false;
		}
		*/
		$("#oneselfCert").submit();
	});
	
</script>
</body>
</html>