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
							<th>닉네임</th>
							<td>
								<input type="text" id="nick_name" name="nick_name" value="${ req.nick_name }" />
							</td>
							<th>성별</th>
							<td>
								<input type="radio" id="sex_1" name="sex" value="${ SexCode.MALE }"  <c:if test='${ req.sex eq SexCode.MALE }'>selected</c:if> /><label for="sex_1">남자</label>
								<input type="radio" id="sex_2" name="sex" value="${ SexCode.FEMALE }" <c:if test='${ req.sex eq SexCode.MALE }'>selected</c:if> /><label for="sex_2">여자</label>
							</td>
						</tr>
						<tr>
							<th>코멘트</th>
							<td>
								<input type="text" id="comment" name="comment" value="${ req.comment }" />
							</td>
							<th>참여일</th>
							<td>
								<input type="text" name="created_date_start" class="datepicker" value="${ req.created_date_start }">
								~ 
								<input type="text" name="created_date_end" class="datepicker" value="${ req.created_date_end }">
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
										<c:when test="${ response.mission.lang eq 'ko' }">한국어</c:when>
										<c:when test="${ response.mission.lang eq 'ja' }">일본어</c:when>
										<c:when test="${ response.mission.lang eq 'zh' }">중국어</c:when>
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
					<table class="tbl_nor" cellspacing="0" summary="미션매치 참여목록">
						<caption>미션매치 참여목록</caption>
						<colgroup>
							<col width="80">
							<col width="150">
							<col width="*">
							<col width="200">
							<col width="100">
							<col width="130">
						</colgroup>
						<tr id="tb_header">
							<th><input type="checkbox" id="replyAll" data-name="mJIds" class="checkInverse"/></th>
							<th>프로필</th>
							<th>참여내용</th>
							<th>투표수/승수</th>
							<th>승률</th>
							<th>참여일</th>
						</tr>
						<c:if test="${fn:length(response.dataList) == 0}">
							<tr>
								<td align="center" colspan="6">데이터가 없습니다.</td>
							</tr>
						</c:if>
						
						<c:forEach items="${response.dataList}" var="item" varStatus="theCount">
							<tr class="record">
								<td align="center"><input type="checkbox" data-label="${ item.nickName }" name="mJIds" value="${ item.mJId }" /></td>
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
								<td>
									<a href="${ item.uploadfileorg }" class="nyroModal" title="미션이미지"><img src="${ item.uploadfile }" class="profile-img"/></a>
									<p>${ item.comment }<a href="missionMatchJoinEdit.html?mJId=${item.mJId }&mId=${item.mId}" target="_blank">[편집]</a></p>
								</td>
								<td align="center">
									${item.battleJoinCnt} / ${item.vote}
								</td>
								<td align="center">
									${ item.vote/item.battleJoinCnt * 100.0 }%									
								</td>
								<td align="center">
									${ item.createdDate }
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="ui-btns alignLeft">
					<button type="button" onclick="deleteJoinMissionMatch()">삭제</button>
					<button type="button" onclick="insertJoinMissionMatch()">등록</button>
				</div>
				<br />
				<br />
				<br />
				
				<jsp:include page="/jsp/common/paging.jsp">
				     <jsp:param name="actionPath" value="missionMatchJoinList.html"/>
				     <jsp:param name="totalCount" value="${ response.totalCnt }"/>
				     <jsp:param name="countPerPage" value="${ reqParams.pageSize }"/>
				     <jsp:param name="blockCount" value="5"/>
				     <jsp:param name="nowPage" value="${ reqParams.nowPage }"/>
				     <jsp:param name="addQuerys" value="nick_name=${ reqParams.nick_name }&amp;sex=${ reqParams.sex }&amp;mId=${ reqParams.mId }&amp;comment=${ reqParams.comment }&amp;created_date_start=${ reqParams.created_date_start }&amp;created_date_end=${ reqParams.created_date_end }"/>
				     
				</jsp:include>
				
				
				<!-- Content 끝 -->
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

	function insertJoinMissionMatch() {
		location.href="missionMatchJoinEdit.html?mId=${ reqParams.mId}";	
	}
	
	
	function searchInit() {
		$("#searchForm").submit();
	}
	
	function deleteJoinMissionMatch() {
		if(!confirm("정말 삭제 하시겠습니까?"))
			return;
		
		var arrs = [];
		$("input[name='mJIds']").each(function() {
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
			url : "missionMatchJoinDel.html",
			type : "POST",
			cache : false,
			data : { mJIds:ids}, 
			dataType : "json",
			success : function(data) {
				
				var rsltCd = data.result.rsltCd;
				var rsltMsg = data.result.rsltMsg;
				if(rsltCd == 0) { //저장성공
					alert("삭제 되었습니다.");
					location.href="missionMatchJoinList.html?mId=${ reqParams.mId }";
				} else {
					alert(rsltMsg);
				}
			},
			error : function(data) {
				alert('<spring:message code="comm.systemError.msgText" />');
			}
		});
	}
	
</script>
</body>
</html>