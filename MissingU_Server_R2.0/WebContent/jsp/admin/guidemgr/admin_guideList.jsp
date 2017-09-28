<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>MissingU Administrator</title>
<%@ include file="/jsp/common/admin_header.jsp"%>
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
			        <jsp:param name="selected" value="guideList"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li><a href="manToManList.html">1:1 문의사항</a></li>
				<li><a href="noticeList.html">공지사항 조회</a></li>
				<li class="select"><a href="guideList.html">사용자가이드 조회</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">사용자가이드 조회</h2>
				
				<form id="searchForm">
				<div class="pt30">
					<table class="tbl_nor" cellspacing="0" summary="회원 검색조건">
						<caption>회원 검색조건</caption>
						<colgroup>
							<col width="140">
							<col width="300">
							<col width="140">
							<col width="300">
						</colgroup>
						<tr>
							<th>메뉴명</th>
							<td><input type="text" id="cond_menuName" name="cond_menuName"></td>
							<th>내용</th>
							<td><input type="text" id="cond_contents" name="cond_contents"></td>
						</tr>
						<tr>
							<th>언어</th>
							<td>
								<select id="cond_lang" name="cond_lang">
									<option value="ko">한국어</option>
									<option value="ja">일본어</option>
									<option value="zh">중국어</option>
								</select>
							</td>
							<th>출력여부</th>
							<td>
								<select id="cond_showYn" name="cond_showYn">
									<option value="">---- 전체 ----</option>
									<option value="Y">Y</option>
									<option value="N">N</option>
								</select>
							</td>
						</tr>
					</table>
					<input type="hidden" id="pageSize" name="pageSize" value="10"/>
					<input type="hidden" id="page" name="page" value="1"/>
					<input type="hidden" id="startRow" name="startRow" value="0"/> 
				</div>
				</form>
				
				<div class="ui-btns alignLeft">
					<button id="btn_add" data-click-callback="add()">등록</button>
					<button id="btn_search" data-click-callback="searchInit()">조회</button>
				</div>
				
				<div class="pt30">
				</div>
				<div class="pt30">
					<table class="tbl_nor" cellspacing="0" summary="사용자가이드">
						<caption>사용자가이드</caption>
						<colgroup>
							<col width="100">
							<col width="100">
							<col width="80">
							<col width="150">
							<col width="150">
						</colgroup>
						<tr id="tb_header">
							<th>메뉴ID</th>
							<th>메뉴명</th>
							<th>출력여부</th>
							<th>작성일자</th>
							<th>수정일자</th>
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
	//신규등록
	function add() {
		location.href="/missingu/admin/guideEdit.html?actionType=add";
	}

	function guideList() {
		location.href="/missingu/admin/guideList.html";
	}
	
	function searchInit() {
		searchCnt();
	}
	
	function searchCnt() {
		$.ajax({
			url : "/missingu/admin/getGuideListCnt.html",
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
					
					//조회 Count가 0보다 큰 경우 리스트 조회
					if(totalCnt > 0) {
						search();
					}
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
			url : "/missingu/admin/getGuideList.html",
			type : "POST",
			cache : false,
			data : $("#searchForm").serialize(),
			dataType : "json",
			success : function(data) {
				//$("#memberPoint").html(data.response.memberPoint).append(" P");
				var rsltCd = data.result.rsltCd;
				
				if(rsltCd == 0) { //조회성공
					var noticeList = data.response.guideList;
					var htmls = "";
					$.each(noticeList, function(i){
						htmls +="<tr class=\"desc\">";
						htmls +="	<td>"+this.menuId+"</td>";
						htmls +="	<td><a href='/missingu/admin/guideEdit.html?actionType=modify&menuId=" +this.menuId+"'>"+this.menuName+"</a></td>";
						htmls +="	<td>"+this.showYn+"</td>";
						htmls +="	<td>"+this.createdDate+"</td>";
						htmls +="	<td>"+this.updatedDate+"</td>";
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
				alert('<spring:message code="comm.systemError.msgText" />');
			}
		});
	}
	

	$(document).ready(function() {
		searchInit();
	});
	
</script>
</body>
</html>