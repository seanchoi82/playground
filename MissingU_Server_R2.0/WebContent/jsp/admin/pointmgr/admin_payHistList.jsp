<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ko">
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/admin_header.jsp"%>
<title>MissingU Administrator</title>
<link rel="stylesheet" type="text/css" href="/css/jqpagination/jqpagination.css">
<script src="/js/jquery.jqpagination.js"></script>
<script type="text/javascript">
/* <![CDATA[ */

$(document).ready(function() {
	//blockUI 셋팅(모든 ajax 호출 시)
	$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
	
	$('.pagination').jqPagination({
		//link_string	: '/?page={page_number}',
		//max_page	: 40,
		paged		: function(page) {
			if($("#page").val() != page) {
				$("#page").val(page);
				$("#startRow").val($("#pageSize").val() * (page-1));
				search();
			}
		}
	});
});

/* ]]> */
</script>
</head>
<body>
<div id="container">
	<header>
		<div class="tmenu">
			<span>관리자</span>님 안녕하세요! | <a href="/logout.html">로그아웃</a>
		</div>
		<div class="logo">MissingU 관리자모드</div>		
		<nav>
			<jsp:include page="/jsp/common/admin_top_nav.jsp" flush="false">
			        <jsp:param name="selected" value="payHistList"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<jsp:include page="/jsp/common/admin_lmenu_cash.jsp" flush="false">
			        <jsp:param name="selected" value="1"/>
				</jsp:include>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">결제내역 조회</h2>
				
				<form id="searchForm">
				<div class="pt10">
					<table class="tbl_nor" cellspacing="0" summary="검색조건">
						<caption>검색조건</caption>
						<colgroup>
							<col width="140">
							<col width="300">
							<col width="140">
							<col width="300">
						</colgroup>
						<tr>
							<th>닉네임</th>
							<td><input type="text" id="cond_nickName" name="cond_nickName"></td>
							<th>로그인아이디</th>
							<td><input type="text" id="cond_loginId" name="cond_loginId"></td>
						</tr>
					</table>
					<input type="hidden" id="pageSize" name="pageSize" value="10"/>
					<input type="hidden" id="page" name="page" value="1"/>
					<input type="hidden" id="startRow" name="startRow" value="0"/> 
				</div>
				</form>
				
				<div class="ui-btns alignLeft">
					<button id="btn_search" data-click-callback="searchInit()">조회</button>
				</div>
				
				<div class="pt30">
				</div>
				<div class="pt30">
					<table class="tbl_nor" cellspacing="0" summary="결제내역">
						<caption>결제내역</caption>
						<colgroup>
							<col width="100">
							<col width="100">
							<col width="100">
							<col width="100">
							<col width="100">
							<col width="100">
						</colgroup>
						<tr id="tb_header">
							<th hidden="true">ID</th>
							<th>닉네임</th>
							<th>결제일자</th>
							<th>주문번호</th>
							<th>상태</th>
							<th>상품코드</th>
							<th>결제금액</th>
						</tr>
					</table>
				</div>
				
				<!-- Content 끝 -->
			</div>
			<div class="gigantic pagination">
			    <a href="#" class="first" data-action="first">&laquo;</a>
			    <a href="#" class="previous" data-action="previous">&lsaquo;</a>
			    <input type="text" readonly="readonly" />
			    <a href="#" class="next" data-action="next">&rsaquo;</a>
			    <a href="#" class="last" data-action="last">&raquo;</a>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	function goPayHistList() {
		location.href="/missingu/admin/payHistList.html";
	}
	
	function goPointUseHistList() {
		location.href="/missingu/admin/pointUseHistList.html";
	}
	
	function searchInit() {
		//if(validateRequestParams()) {
			searchCnt();
			search();
		//}
	}
	
	function searchCnt() {
		$.ajax({
			url : "/missingu/admin/getPayHistListCnt.html",
			type : "POST",
			cache : false,
			data : $("#searchForm").serialize(),
			dataType : "json",
			success : function(data) {
				//$("#memberPoint").html(data.response.memberPoint).append(" P");
				var rsltCd = data.result.rsltCd;
				
				if(rsltCd == 0) { //조회성공
					var totalCnt = data.response.totalCnt;
					$('.pagination').jqPagination('option', 'max_page' , Math.ceil(totalCnt / $("#pageSize").val()));
				} else {
					alert(data.result.rsltMsg);
				}
			},
			error : function(data) {
				$(".desc").remove();
				alert('<spring:message code="comm.systemError.msgText" />');
			}
		});
	}
	
	function search() {
		$.ajax({
			url : "/missingu/admin/getPayHistList.html",
			type : "POST",
			cache : false,
			data : $("#searchForm").serialize(),
			dataType : "json",
			success : function(data) {
				//$("#memberPoint").html(data.response.memberPoint).append(" P");
				var rsltCd = data.result.rsltCd;
				
				if(rsltCd == 0) { //조회성공
					var payHistList = data.response.payHistList;
					var htmls = "";
					$.each(payHistList, function(i){
						
						var prodNm = "";
						if(!this.prodCd) {
							prodNm = "주문접수상태";
						}else if(this.prodCd == "p01001") {
							prodNm = "1만원권";
						}else if(this.prodCd == "p01002") {
							prodNm = "2만원권";
						}else if(this.prodCd == "p01003") {
							prodNm = "3만원권";
						}else{
							prodNm = "기타상품";
						}
						
						htmls +="<tr class=\"desc\">";
						htmls +="	<td hidden=\"true\">"+this.memberId+"</td>";
						htmls +="	<td><img src='"+this.mainPhotoUrl+"' class='profile-img' />"+this.nickName+"</td>";
						htmls +="	<td>"+(this.payDate||"주문접수상태")+"</td>";
						htmls +="	<td>"+this.orderNum+"</td>";
						htmls +="	<td>"+(this.status == "S" ? "결제완료" : "주문접수") +"</td>";
						htmls +="	<td>"+prodNm+"</td>";
						htmls +="	<td>"+this.amount+"</td>";
						htmls +="</tr>";
						htmls +="<tr class=\"desc\">";
						htmls +="	<td colspan='6' style='padding:5px; background:#f2f2f2;'>"+(this.prodName||"주문접수상태")+"</td>";
						htmls +="</tr>";
					});
					$(".desc").remove();
					$("#tb_header").after(htmls);
				} else {
					$(".desc").remove();
					alert(data.result.rsltMsg);
				}
			},
			error : function(data) {
				$(".desc").remove();
				alert(data.result.rsltMsg);
			}
		});
	}
	
	//Request 필수항목 체크
	function validateRequestParams() {
		var inputs = [
			{id: 'cond_loginId', label: '로그인아이디'},
			{id: 'cond_nickName', label: '닉네임'}
		];
		
		//적어도 한 개는 입력해야 함
		var emptyFieldCnt = 0;
		for (var i=0; i < inputs.length; i++)
		{
			var input = inputs[i];
			var $element = $('#' + input.id);
			 
    		if($.trim($element.val()) == '') {
    			emptyFieldCnt = emptyFieldCnt + 1;
    		}
		}
		
		if(emptyFieldCnt == inputs.length) {
			alert("적어도 하나의 조건을 입력해 주세요.");
			return false;
		}

		return true;
	}
	
	$(document).ready(function() {
		searchInit();
	});
	
</script>
</body>
</html>