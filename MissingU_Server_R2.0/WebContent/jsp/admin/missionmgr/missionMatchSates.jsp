<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ko">
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/admin_header.jsp"%>
<title>MissingU Administrator</title>
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
				<li><a href="missionMatchList.html">미션매치 목록</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">미션매치 통계</h2>
				<form id="searchForm" action="missionMatchSates.html">
					<input type="hidden" name="mId" value="${ reqParams.mId }" />
					<table class="tbl_nor" cellspacing="0" summary="미션매치 검색조건">
						<caption>미션매치 검색조건</caption>
						<colgroup>
							<col width="140">
							<col width="300">
							<col width="140">
							<col width="300">
						</colgroup>
						<tr>
							<th>전체</th>
							<td>
								<input type="radio" id="is_all_1" name="is_all" value="1"  <c:if test='${ reqParams.is_all eq "1" }'>checked</c:if> /><label for="is_all_1">전체</label>
								<input type="radio" id="is_all_0" name="is_all" value="0"  <c:if test='${ reqParams.is_all eq "0" }'>checked</c:if> /><label for="is_all_0">기간설정</label>
							</td>
							<th>기간</th>
							<td>
								<select name="year">
									<c:forEach items="${ years }" var="item">
										<option value="${ item }" <c:if test="${ item eq reqParams.year }">selected</c:if>>${ item }년</option>
									</c:forEach>
								</select>
								
								<select name="month">
									<c:forEach items="${ months }" var="item">
										<option value="${ item }" <c:if test="${ item eq reqParams.month }">selected</c:if>>${item }월</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</table>
				</form>
				<div class="ui-btns alignLeft">
					<button id="btn_search" data-click-callback="searchInit()">조회</button>
				</div>
				<br />
				
				
				<div class="pt30">
				<table class="tbl_nor" cellspacing="0" summary="미션매치 목록">
						<caption>미션매치 목록</caption>
						<colgroup>
							<col width="*">
							<col width="200">
							<col width="100">
							<col width="130">
						</colgroup>
						<tr id="tb_header">
							<th>제목</th>
							<th>기간</th>
							<th>상태</th>
							<th>배틀수</th>
						</tr>
						<tr class="record">
							<td>
								[<c:choose>
										<c:when test="${ item.lang eq 'ko' }">한국어</c:when>
										<c:when test="${ item.lang eq 'ja' }">일본어</c:when>
										<c:when test="${ item.lang eq 'zh' }">중국어</c:when>
									</c:choose>]
								<a href="<c:url value="/missingu/admin/missionMatchEdit.html">
									<c:param name="mId" value="${response.mission.mId}"></c:param>
								</c:url>">
								${response.mission.title}(${ response.mission.maxCount })
								</a>
							</td>
							<td align="center">
							${response.mission.startDate}
							~
							${response.mission.endDate}
							</td>
							<td align="center">
								<c:if test="${response.mission.status eq '1'}">사용</c:if>
								<c:if test="${response.mission.status eq '0'}">사용안함</c:if>
							</td>
							<td align="center">${response.mission.battleCnt}</td>
						</tr>
					</table>
					<table class="tbl_nor" cellspacing="0" summary="미션매치 순위">
						<caption>미션매치 순위</caption>
						<colgroup>
							<col width="80">
							<col width="150">
							<col width="*">
							<col width="200">
							<col width="100">
							<col width="130">
						</colgroup>
						<tr id="tb_header">
							<th>순위</th>
							<th>프로필</th>
							<th>참여수</th>
							<th>승수</th>
							<th>승률</th>
						</tr>
						<c:if test="${fn:length(response.dataList) == 0}">
							<tr>
								<td align="center" colspan="6">데이터가 없습니다.</td>
							</tr>
						</c:if>
						
						<c:forEach items="${response.dataList}" var="item" varStatus="theCount">
							<tr class="record">
								<td align="center">${
									reqParams.startRow + theCount.count
								}</td>
								<td>
									<div class="profile">
										<img src="${ item.mainPhotoUrl }" class="profile-img" />
										<ul>
											<li><a href="memberEdit.html?actionType=modify&memberId=${ item.memberId}" target="_blank"><strong>${ item.nickName }</strong>(${ item.name })</a></li>
											<li><c:if test="${ item.sex eq 'G01001' }">남자</c:if>
												<c:if test="${ item.sex eq 'G01002' }">여자</c:if>
											</li>
										</ul>
									</div>
								</td>
								<td align="center">
									${item.battleJoinCnt}
								</td>
								<td align="center">
									${item.vote}
								</td>
								<td align="center">
									${ item.vote/item.battleJoinCnt * 100.0 }%
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<br />
				<br />
				<br />
				
				<jsp:include page="/jsp/common/paging.jsp">
				     <jsp:param name="actionPath" value="missionMatchSates.html"/>
				     <jsp:param name="totalCount" value="${ response.totalCnt }"/>
				     <jsp:param name="countPerPage" value="${ reqParams.pageSize }"/>
				     <jsp:param name="blockCount" value="5"/>
				     <jsp:param name="nowPage" value="${ reqParams.nowPage }"/>
				     <jsp:param name="addQuerys" value="is_all=${ reqParams.is_all }&amp;year=${ reqParams.year }&amp;month=${ reqParams.month }&amp;mId=${ reqParams.mId }"/>
				     
				</jsp:include>
				
				
				<!-- Content 끝 -->
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">	
	function searchInit() {
		$("#searchForm").submit();
	}
</script>
</body>
</html>