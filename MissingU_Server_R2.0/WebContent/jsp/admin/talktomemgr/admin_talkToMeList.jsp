<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>MissingU Administrator</title>
<%@ include file="/jsp/common/admin_header.jsp"%>
<script src="/js/jquery.jqpagination.js"></script>
<script type="text/javascript">
             
function deleteTalk() {
	
	if(!confirm("정말 삭제 하시겠습니까?"))
		return;
	
	var arrs = [];
	$("input[name='talkIds']").each(function() {
		if($(this).is(":checked")) {
			arrs.push($(this).val());
		}
	});
	
	var ids = "";
	for(var i=0;i<arrs.length;i++) {
		if(i>0 )ids += ",";
		ids += arrs[i];
	}
	
	$.ajax({
		url : "delTalkArr.html",
		type : "POST",
		cache : false,
		data : { talkIds:ids}, 
		dataType : "json",
		success : function(data) {
			
			//$("#memberPoint").html(data.response.memberPoint).append(" P");
			var rsltCd = data.result.rsltCd;
			var rsltMsg = data.result.rsltMsg;
			
			if(rsltCd == 0) { //저장성공
				alert("성공");
				location.href="talkToMeList.html";
			} else {
				alert(data.result.rsltMsg);
			}
		},
		error : function(data) {
			alert('<spring:message code="comm.systemError.msgText" />');
		}
	});
}

function talkToMeAdd() {
	location.href="talkToMeEdit.html";
}

function searchInit() {
	$("#searchForm").submit();
}

$(document).ready(function() {
	
	var totalCnt = ${response.totalCnt };
	$('.pagination').jqPagination({
		//link_string	: '/?page={page_number}',
		current_page : "${ reqParams.page}", 
		max_page	: Math.ceil(totalCnt / $("#pageSize").val()),
		paged		: function(page) {
			
			 if($("#page").val() != page) {
				$("#page").val(page);
				$("#startRow").val($("#pageSize").val() * (page-1));
			
				$("#searchForm").submit();
			 }
		}
	});
	
	
	
	
});
</script>
<link rel="stylesheet" type="text/css" href="/css/jqpagination/jqpagination.css">
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
			        <jsp:param name="selected" value="talkToMe"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li class="select"><a href="/missingu/admin/talkToMeList.html">톡투미 목록</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->	
				<h2 class="icon-arrow-green">톡투미 목록</h2>
				<form id="searchForm" action="talkToMeList.html">
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
							<th>회원아이디</th>
							<td><input type="text" id="cond_memberId" name="cond_memberId" value="${reqParams.cond_memberId}"></td>
							<th>로그인아이디</th>
							<td><input type="text" id="cond_loginId" name="cond_loginId" value="${reqParams.cond_loginId}"></td>
						</tr>
						<tr>
							<th>닉네임</th>
							<td><input type="text" id="cond_nickName" name="cond_nickName" value="${reqParams.cond_nickName}"></td>
							<th>성별</th>
							<td>
								<select id="cond_sex" name="cond_sex">
									<option value="ALL">---- 전체 ----</option>
									<option value="G01001" <c:if test="${ reqParams.cond_sex eq 'G01001' }">selected</c:if>>남자</option>
									<option value="G01002" <c:if test="${ reqParams.cond_sex eq 'G01002' }">selected</c:if>>여자</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>제목</th>
							<td colspan="3">
								<input type="text" name="cond_title" value="${reqParams.cond_title}"/>
							</td>
						</tr>
					</table>
					<input type="hidden" id="pageSize" name="pageSize" value="10"/>
					<input type="hidden" id="page" name="page" value="${ reqParams.page}"/>
					<input type="hidden" id="startRow" name="startRow" value="0" /> 
				</div>
				</form>
				
				<div class="ui-btns alignLeft">
					<button id="btn_search">조회</button>
				</div>
				<br />
				<div class="pt30">
					<table class="tbl_nor" cellspacing="0" summary="톡투미 목록">
						<caption>톡투미 목록</caption>
						<colgroup>
							<col width="80">
							<col width="0" hidden="true">
							<col width="100">
							<col width="*">
							<col width="100">
							<col width="100">
							<col width="150">
						</colgroup>
						<tr id="tb_header">
							<th><input type="checkbox" id="replyAll" data-name="talkIds" class="checkInverse"/></th>
							<th hidden="true">번호</th>
							<th>번호</th>
							<th>제목</th>
							<th>조회수</th>
							<th>등록자</th>
							<th>등록일자</th>
						</tr>
						<c:if test="${fn:length(response.talkToMeList) == 0}">
							<tr>
								<td align="center" colspan="6">데이터가 없습니다.</td>
							</tr>
						</c:if>
						
						<c:forEach items="${response.talkToMeList}" var="talkToMeItem">
							<tr class="record">
								<td align="center"><input type="checkbox" name="talkIds" value="${ talkToMeItem.talkId }" /></td>
								<td align="center">${talkToMeItem.talkId}</td>
								<td>
									<a href="<c:url value="/missingu/admin/talkToMeEdit.html">
										<c:param name="talkId" value="${talkToMeItem.talkId}"></c:param>
										<c:param name="startRow" value="${reqParams.startRow}"></c:param>
									</c:url>"
									<c:if test='${ talkToMeItem.hideTag == "1" }'>style='text-decoration:line-through'</c:if>>
									<c:if test='${ talkToMeItem.hideTag == "1" }'>(사용자 삭제)</c:if>
									${talkToMeItem.title}(${ talkToMeItem.replyCnt })
									</a></td>
								<td align="center">${talkToMeItem.readCnt}</td>
								<td align="center">${talkToMeItem.nickName}</td>
								<td align="center">${talkToMeItem.createdDate}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				
				
				
				<div class="ui-btns alignLeft">
					<button type="button" onclick="deleteTalk()">댓글 삭제</button>
					<button id="btn_new" data-click-callback="talkToMeAdd()">톡투미 등록</button>
				</div>
				
				<br />
				<br />
				<br />
				
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
$("#btn_search").click(function() {
	$("#searchForm").submit();
});
</script>	
</body>
</html>