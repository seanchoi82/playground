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
			        <jsp:param name="selected" value="2"/>
				</jsp:include>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">포인트 이용내역 조회</h2>
				
				<form id="searchForm">
				<div class="pt10">
					<table class="tbl_nor" cellspacing="0" summary="검색조건">
						<caption>검색조건</caption>
						<colgroup>
							<col width="100">
							<col >
							<col width="100">
							<col >
							<col width="100">
							<col >
						</colgroup>
						<tr>
							<th>닉네임</th>
							<td><input type="text" id="cond_nickName" name="cond_nickName" value="${ param.cond_nickName }"></td>
							<th>로그인아이디</th>
							<td><input type="text" id="cond_loginId" name="cond_loginId" value="${ param.cond_loginId }"></td>
							<th>회원아이디</th>
							<td><input type="text" id="cond_memberId" name="cond_memberId" value="${ param.cond_memberId }"></td>
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
					<table class="tbl_nor" cellspacing="0" summary="조회결과">
						<caption>조회결과</caption>
						<colgroup>
							<col width="100">
							<col width="100">
							<col width="100">
							<col width="100">
							<col width="100">
						</colgroup>
						<tr id="tb_header">
							<th hidden="true">ID</th>
							<th>닉네임</th>
							<th>충전/사용</th>
							<th>사용포인트</th>
							<th>잔여포인트</th>
							<th>사용일시</th>
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
	//메뉴이동 => 결제내역 조회
	function goPayHistList() {
		location.href="/missingu/admin/payHistList.html";
	}
	//메뉴이동 => 포인트 이용내역 조회
	function goPointUseHistList() {
		location.href="/missingu/admin/pointUseHistList.html";
	}
	
	function searchInit() {
		//if(validateRequestParams()){
			searchCnt();
			search();
		//}
	}
	
	function searchCnt() {
		$.ajax({
			url : "/missingu/admin/getPointUseHistListCnt.html",
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
	
	//조회
	function search() {
		$.ajax({
			url : "/missingu/admin/getPointUseHistList.html",
			type : "POST",
			cache : false,
			data : $("#searchForm").serialize(),
			dataType : "json",
			success : function(data) {
				//$("#memberPoint").html(data.response.memberPoint).append(" P");
				var rsltCd = data.result.rsltCd;
				
				if(rsltCd == 0) { //조회성공
					var pointUseHistList = data.response.pointUseHistList;
					var htmls = "";
					$.each(pointUseHistList, function(i){
						htmls +="<tr class=\"desc\">";
						htmls +="	<td hidden=\"true\">"+this.id+"</td>";
						htmls +="	<td><img src='"+this.mainPhotoUrl+"' class='profile-img' />"+this.nickName+"</td>";
						htmls +="	<td>"+(this.eventTypeCd == "I" ? "충전" : "사용") +"</td>";
						htmls +="	<td>"+this.usePoint+"</td>";
						htmls +="	<td>"+this.remainPoint+"</td>";
						htmls +="	<td>"+this.createdDate+"</td>";
						htmls +="</tr>";
						htmls +="<tr class=\"desc\">";
						htmls +="	<td colspan='5' style='padding:5px; background:#f2f2f2;'>"+this.useDesc+"</td>";
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