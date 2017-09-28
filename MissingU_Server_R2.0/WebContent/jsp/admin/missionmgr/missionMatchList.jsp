<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>MissingU Administrator</title>
<%@ include file="/jsp/common/admin_header.jsp"%>
<script src="/js/jquery.jqpagination.js"></script>
<link rel="stylesheet" type="text/css" href="/css/jqpagination/jqpagination.css">
<style type="text/css">
	.tbl_nor input[type='text'].datepicker { width:100px; }
</style>
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
			        <jsp:param name="selected" value="missionMatch"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li <c:if test="${ reqParams.is_facematch eq '1'  }">class="select"</c:if>><a href="missionMatchList.html?is_facematch=1">미션매치 목록</a></li>
				<li <c:if test="${ reqParams.is_facematch eq '0'  }">class="select"</c:if>><a href="missionMatchList.html?is_facematch=0">페이스매치 목록</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->	
				<h2 class="icon-arrow-green">미션매치 목록</h2>
				<form id="searchForm" action="missionMatchList.html">
				<div class="pt30">
					<table class="tbl_nor" cellspacing="0" summary="미션매치 검색조건">
						<caption>미션매치 검색조건</caption>
						<colgroup>
							<col width="140">
							<col width="300">
							<col width="140">
							<col width="300">
						</colgroup>
						<tr>
							<th>기간</th>
							<td>
								<input type="text" name="period_date_start" class="datepicker" value="${ reqParams.period_date_start }">
								~ 
								<input type="text" name="period_date_end" class="datepicker" value="${ reqParams.period_date_end }">
							</td>
							<th>기능</th>
							<td>
								<input type="checkbox" id="status" name="status" value="1"  <c:if test='${ reqParams.status eq "1" }'>checked</c:if> /><label for="status">사용여부</label>
							</td>
						</tr>
						<tr>
							<th>제목</th>
							<td>
								<input type="text" name="cond_title" value="${reqParams.cond_title}"/>
							</td>
							<th>내용</th>
							<td>
								<input type="text" name="cond_content" value="${reqParams.cond_content}"/>
							</td>
						</tr>
						<tr>
							<th>언어</th>
							<td>
								<select name="lang">
									<option value="">전체</option>
									<option value="ko" <c:if test='${ reqParams.lang eq "ko" }'>selected</c:if>>한국어</option>
									<option value="ja" <c:if test='${ reqParams.lang eq "ja" }'>selected</c:if>>일본어</option>
									<option value="zh" <c:if test='${ reqParams.lang eq "zh" }'>selected</c:if>>중국어</option>
								</select>
							</td>
						</tr>
					</table> 
				</div>
				</form>
				
				<div class="ui-btns alignLeft">
					<button id="btn_search" data-click-callback="searchInit()">조회</button>
				</div>
				<br />
				<div class="pt30">
					<table class="tbl_nor" cellspacing="0" summary="미션매치 목록">
						<caption>미션매치 목록</caption>
						<colgroup>
							<col width="80">
							<col width="0" hidden="true">
							<col width="100">
							<col width="*">
							<col width="200">
							<col width="100">
							<col width="130">
						</colgroup>
						<tr id="tb_header">
							<th><input type="checkbox" id="replyAll" data-name="mIds" class="checkInverse"/></th>
							<th hidden="true">번호</th>
							<th>번호</th>
							<th>제목</th>
							<th>기간</th>
							<th>상태</th>
							<th>배틀수</th>
						</tr>
						<c:if test="${fn:length(response.dataList) == 0}">
							<tr>
								<td align="center" colspan="6">데이터가 없습니다.</td>
							</tr>
						</c:if>
						
						<c:forEach items="${response.dataList}" var="item">
							<tr class="record">
								<td align="center"><input type="checkbox" name="mIds" value="${ item.mId }" /></td>
								<td align="center">${item.mId}</td>
								<td>
									[<c:choose>
										<c:when test="${ item.lang eq 'ko' }">한국어</c:when>
										<c:when test="${ item.lang eq 'ja' }">일본어</c:when>
										<c:when test="${ item.lang eq 'zh' }">중국어</c:when>
									</c:choose>]
									<a href="<c:url value="/missingu/admin/missionMatchEdit.html">
										<c:param name="mId" value="${item.mId}"></c:param>
									</c:url>">
									${item.title}(${ item.maxCount })
									</a>
									<a href="<c:url value="/missingu/admin/missionMatchJoinList.html">
										<c:param name="mId" value="${item.mId}"></c:param>
									</c:url>">[참여보기]
									</a>
									<a href="<c:url value="/missingu/admin/missionMatchSates.html">
										<c:param name="mId" value="${item.mId}"></c:param>
									</c:url>">[통계보기]
									</a>
									</td>
								<td align="center">
								${item.startDate}
								~
								${item.endDate}
								</td>
								<td align="center">
									<c:if test="${item.status eq '1'}">사용</c:if>
									<c:if test="${item.status eq '0'}">사용안함</c:if>
								</td>
								<td align="center">${item.battleCnt}</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				
				<div class="ui-btns alignLeft">
					<button type="button" onclick="deleteMissionMatch()">삭제</button>
					<button id="btn_new" data-click-callback="addMissionMatch()">등록</button>
				</div>
				
				<br />
				<br />
				<br />
				
				<!-- Content 끝 -->
			</div>
			
			<jsp:include page="/jsp/common/paging.jsp">
			     <jsp:param name="actionPath" value="messageBoxList.html"/>
			     <jsp:param name="totalCount" value="${ response.totalCnt }"/>
			     <jsp:param name="countPerPage" value="${ reqParams.pageSize }"/>
			     <jsp:param name="blockCount" value="5"/>
			     <jsp:param name="nowPage" value="${ reqParams.nowPage }"/>
			     <jsp:param name="addQuerys" value="period_date_start=${ reqParams.period_date_start }&amp;period_date_end=${ reqParams.period_date_end }&amp;status=${ reqParams.status }&amp;cond_title=${ reqParams.cond_title }&amp;cond_content=${ reqParams.cond_content }&amp;is_facematch=${ reqParams.is_facematch }"/>
			     
			</jsp:include>
				
		</div>
	</div>
</div>	
<script type="text/javascript">
/* <![CDATA[ */
             
function deleteMissionMatch() {
	
	if(!confirm("정말 삭제 하시겠습니까?"))
		return;
	
	var arrs = [];
	$("input[name='mIds']").each(function() {
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
		url : "delMissionMatchArr.html",
		type : "POST",
		cache : false,
		data : { mIds:ids}, 
		dataType : "json",
		success : function(data) {
			
			//$("#memberPoint").html(data.response.memberPoint).append(" P");
			var rsltCd = data.result.rsltCd;
			var rsltMsg = data.result.rsltMsg;
			
			if(rsltCd == 0) { //저장성공
				alert("성공");
				location.href="missionMatchInfo.html";
			} else {
				alert(data.result.rsltMsg);
			}
		},
		error : function(data) {
			alert('<spring:message code="comm.systemError.msgText" />');
		}
	});
}

function addMissionMatch() {
	location.href="missionMatchEdit.html";
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
	
	
	$(".datepicker").datepicker();
	
});

/* ]]> */
</script>
</body>
</html>