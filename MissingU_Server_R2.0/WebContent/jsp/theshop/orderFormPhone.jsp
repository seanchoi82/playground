<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ko">
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/header.jsp"%>
<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0">
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma" content="no-cache"/>
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<style type="text/css">
	body { background: url(/img/bg_gray_nopad.png); }
</style>
<title>결제</title>

<script type="text/javascript">
	$(document).ready(function(){
		$("#btn_confirm").click(function(){
			$("#ORDERNO").val(getTimeStamp());
			document.charset = 'euc-kr';
			//$("#payForm").action("https://ssltest.daoupay.com/m/mobile/DaouMobileMng.jsp");
			$("#payForm").submit();
		});
	});
	
</script>

</head>
<body>

<div class="page">
	<div class="ui-box ui-box-white ui-box-shadow">
		<h2 class="icon-arrow-green">휴대폰 결제</h2>
		
		<div class="ui-box ui-box-lightgray">
			<ul class="product-info">
				<li>
					<span class="blot">▶</span>
					<span class="name"> 상품명 </span>
					<span class="split">|</span>
					<span id="prodName">
					${paramMap.PRODUCTNAME}
					</span>
				</li>
				
				<li>
					<span class="blot">▶</span>
					<span class="name"> 이용금액 </span>
					<span class="split">|</span>
					<span id="amount">
					${paramMap.AMOUNT}
					</span>원
				</li>
			</ul>
		</div>
		
		<form id="payForm" method="post" action="https://ssltest.daoupay.com/m/mobile/DaouMobileMng.jsp" >
		<div class="pt30">
			<h2 class="icon-arrow-green">추가정보 입력</h2>
			
			<table class="tbl_nor" cellspacing="0" summary="결제정보 입력">
				<caption>결제정보입력</caption>
				<colgroup>
					<col width="140">
				</colgroup>
				<tr>
					<th>설명</th>
					<td>※ 확인버튼 클릭시 결제 페이지로 연결됩니다.(이메일은 선택)</td>
				</tr>
				<tr>
					<th>결제통보 e-mail</th>
					<td><input type="text" id="EMAIL" maxlength="50" style="width:80%;"/></td>
				</tr>
			</table>
		</div>
		<div>
			<!--------------------------------------------------------------------------------------------
			 * CPID : 상점아이디 [필수항목]
			 * 상점 아이디를 입력하시면 됩니다. 
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="CPID" name=CPID value="${paramMap.CPID}">
			
			<!--------------------------------------------------------------------------------------------
			 * ORDERNO : 주문번호[필수항목]
			 * 상점의 유일한 key값으로 주문번호를 생성해서 입력하시면 됩니다.
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="ORDERNO" name=ORDERNO value="">
			
			<!--------------------------------------------------------------------------------------------
			 * PRODUCTTYPE : 상품구분[필수항목]
			 * 1:디지털, 2:실물
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="PRODUCTTYPE" name=PRODUCTTYPE value="1">
			
			<!--------------------------------------------------------------------------------------------
			 * BILLTYPE : 과금유형[필수항목]
			 * 1:일반, 2:월자동
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="BILLTYPE" name=BILLTYPE value="1">
			
			<!--------------------------------------------------------------------------------------------
			 * USERID : 고객아이디[필수항목]
			 * 주문자 아이디를 입력하시면 됩니다.
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="USERID" name=USERID value="${paramMap.gMemberId}">
			
			<!--------------------------------------------------------------------------------------------
			 * USERNAME : 고객이름[선택항목]
			 * 주문자 이름을 입력하시면 됩니다.
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="USERNAME" name=USERNAME value="">
			
			<!--------------------------------------------------------------------------------------------
			 * PRODUCTCODE : 상품코드[선택항목]
			 * 다우와 협의 후 정의된 상품 코드를 입력하시면 됩니다.
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="PRODUCTCODE" name=PRODUCTCODE value="${paramMap.PRODUCTCODE}">
			<input type="hidden" id="PRODUCTNAME" name=PRODUCTNAME value="${paramMap.PRODUCTNAME}">
			<input type="hidden" id="AMOUNT" name=AMOUNT value="${paramMap.AMOUNT}">
			
			<!--------------------------------------------------------------------------------------------
			 * RESERVEDINDEX1 : 예약항목1[선택항목]
			 * 다우패이 어드민의 거래내역에서 보고싶은 항목이나 검색할 내용을 입력합니다
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="RESERVEDINDEX1" name=RESERVEDINDEX1 value="">
			
			<!--------------------------------------------------------------------------------------------
			 * RESERVEDINDEX1 : 예약항목2[선택항목]
			 * 다우패이 어드민의 거래내역에서 보고싶은 항목이나 검색할 내용을 입력합니다
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="RESERVEDINDEX2" name=RESERVEDINDEX2 value="">
			
			<!--------------------------------------------------------------------------------------------
			 * RESERVEDSTRING : 예약스트링[선택항목]
			 * 거래시 필요한 정보를 입력하여 상점에서 사용할때 유용합니다.
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="RESERVEDSTRING" name=RESERVEDSTRING value="">
			
			<!--------------------------------------------------------------------------------------------
			 * RETURNURL : 결제완료 url[선택항목]
			 * 결제 완료 후, 이동할 url(팝업)
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="RETURNURL" name=RETURNURL value="${paramMap.RETURNURL}">
			
			<!--------------------------------------------------------------------------------------------
			 * HOMEURL : 결제완료 url[선택항목]
			 * 결제 완료 후, 이동할 url(프레임)
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="HOMEURL" name=HOMEURL value="${paramMap.HOMEURL}">
			
			<!--------------------------------------------------------------------------------------------
			 * DIRECTRESULTFLAG : 결제완료 url[필수항목]
			 * 결제 완료 후, 이동할 url(팝업)
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="DIRECTRESULTFLAG" name=DIRECTRESULTFLAG value="">
			
			<!--------------------------------------------------------------------------------------------
			 * 취소 버튼 눌렸을 시 URL : 결제완료 url[필수항목]
			 * 결제 완료 후, 이동할 url(팝업)
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="CLOSEURL" name=CLOSEURL value="${paramMap.CLOSEURL}">
			<!--------------------------------------------------------------------------------------------
			 * File 되었을시 URL : 결제완료 url[필수항목]
			 * 결제 완료 후, 이동할 url(팝업)
			---------------------------------------------------------------------------------------------->
			<input type="hidden" id="FAILURL" name=FAILURL value="${paramMap.FAILURL}">
			
		</div>
		</form>
		
		<div class="ui-btns">
			<button id="btn_confirm">확인</button>
			<button id="btn_cancel">취소</button>
		</div>
		
	</div>
</div>


</body>
</html>