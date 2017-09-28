<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>MissingU Administrator</title>
<%@ include file="/jsp/common/admin_header.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/jqpagination/jqpagination.css">
<script src="/js/jquery.jqpagination.js"></script>
<style>
.friends .ubox li { cursor: pointer; }
.friends .ubox li.selected { color:red; font-weight: bold; }
.msgbox li span em { cursor: pointer;}
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
			        <jsp:param name="selected" value="manToManList"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li class="select"><a href="manToManList.html">1:1 문의사항</a></li>
				<li><a href="noticeList.html">공지사항 조회</a></li>
				<li><a href="guideList.html">사용자가이드 조회</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">1:1 문의사항</h2>
				<h4>고객님께서 요청하신 문의사항 입니다.</h4>
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
							<th>등록자</th>
							<td>
								<div id="cond_memberImg" class="profile_img">${ reqParams.memberId }</div>
								<input type="hidden" id="cond_memberId" name="memberId" />
								<button type="button" onclick="choiceMember()">선택</button>
							</td>
							<th>문의구분/상태</th>
							<td>
								<select id="cond_code" name="code">
									<option value="">전체</option>
									<c:forEach items="${ codes }" var="entry">
										<option value="${ entry.key }" <c:if test="${ entry.key eq reqParams.code }">selected</c:if>>${ entry.value }</option>
									</c:forEach>
								</select>
								<select id="cond_status" name="status">
									<option value="">전체</option>
									<c:forEach items="${ status }" var="entry">
										<option value="${ entry.key }" <c:if test="${ entry.key eq reqParams.status }">selected</c:if>>${ entry.value }</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</table> 
				</div>
				<div class="ui-btns alignLeft">
					<button id="btn_search" data-click-callback="search()">조회</button>
				</div>
				</form>
				
				
				<div class="pt30">
				</div>
				<div class="pt30">
					<table class="tbl_nor" cellspacing="0" summary="1:1 문의 목록">
						<caption>1:1 문의 목록</caption>
						<colgroup>
							<col width="80">
							<col width="150">
							<col width="100">
							<col width="150">
							<col>
							<col width="90">
							<col width="150">
						</colgroup>
						<tr id="tb_header">
							<th><input type="checkbox" id="replyAll" data-name="mIds" class="checkInverse"/></th>
							<th>등록자</th>
							<th>문의구분</th>
							<th>연락처</th>
							<th>내용/메모</th>
							<th>상태</th>
							<th>등록일/수정일</th>
						</tr>
						<c:if test="${fn:length(response.dataList) == 0}">
							<tr>
								<td align="center" colspan="7">데이터가 없습니다.</td>
							</tr>
						</c:if>
						<c:forEach items="${response.dataList}" var="item">
							<tr class="record" data-idx="${ item.mId }">
								<td align="center"><input type="checkbox" name="mIds" value="${ item.mId }" /></td>
								<td>
									<img src="${ item.mainPhotoUrl }" class="profile-img"/>
									${ item.nickName }
								</td>
								<td align="center">
									${ codes[item.code] }
								</td>
								<td>${item.contract}</td>
								<td>
									<div class="rbox">
										<p><c:if test="${ fn:length(item.file) > 0 }"><a href="${ item.file }" class="nyroModal" title="첨부파일"><img src="${item.file }" class="profile-img"/></a></c:if>${item.content}</p>
									</div>
									<textarea name="memo" style="width:100%;" placeholder="메모를 입력하세요.">${item.memo}</textarea>
								</td>
								<td align="center">
									<select name="status">
									<c:forEach items="${ status }" var="entry">
										<option value="${ entry.key }" <c:if test="${ entry.key eq item.status }">selected</c:if>>${ entry.value }</option>
									</c:forEach>
									</select>
									<button type="button" onclick="upateManToMan(${ item.mId })">변경</button>
								</td>
								<td align="center">
									<p>${item.createdDate}</p>
									/
									<p>${item.updatedDate}</p>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="ui-btns alignLeft">
					<button type="button" onclick="deleteManToMan()">1:1 삭제</button>
				</div>
				
				<jsp:include page="/jsp/common/paging.jsp">
				    <jsp:param name="actionPath" value="messageBoxList.html"/>
				    <jsp:param name="totalCount" value="${ response.totalCnt }"/>
				    <jsp:param name="countPerPage" value="${ reqParams.pageSize }"/>
				    <jsp:param name="blockCount" value="5"/>
				    <jsp:param name="nowPage" value="${ reqParams.nowPage }"/>
				    <jsp:param name="addQuerys" value="memberId=${ reqParams.memberId }&amp;status=${ reqParams.status }&amp;code=${ reqParams.code}"/>
				</jsp:include>
				<!--  -->
			</div> 
		</div>
	</div>
</div>
<script type="text/javascript">
function rebind(token, memberId, nickName, country, lang, photoUrl) {
	
	$("#cond_memberId").val(memberId);
	$("#cond_memberImg").html("<img src='" + photoUrl + "' class='profile-img'/> " + nickName);
}

function choiceMember() {
	window.open("memberSearchPop.html?token=", "", "width=600, height=600");
	return;
}

function search() {
	if(!$("#cond_memberId").val() && !$("#cond_code").val() && !$("#cond_status").val()) {
		alert("등록자, 문의구분 또는 상태를 선택 해주세요.");
		return;
	} 
	
	$("#searchForm").submit();
}

function deleteManToMan() {
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
		url : "delManToManArr.html",
		type : "POST",
		cache : false,
		data : { mIds:ids}, 
		dataType : "json",
		success : function(data) {
			
			var rsltCd = data.result.rsltCd;
			var rsltMsg = data.result.rsltMsg;
			if(rsltCd == 0) { //저장성공
				alert("삭제 되었습니다.");
				location.href="manToManList.html";
			} else {
				alert(rsltMsg);
			}
		},
		error : function(data) {
			alert('<spring:message code="comm.systemError.msgText" />');
		}
	});
}

function upateManToMan(mId) {
	
	var $parent = $("tr[data-idx='" + mId + "']");
	var memo = $parent.find("textarea").val();
	var status = $parent.find("select").val();
	
	
	$.ajax({
		url : "updateManToMan.html",
		type : "POST",
		cache : false,
		data : { 
				mId : mId, 
				memo : memo, 
				status : status
			}, 
		dataType : "json",
		success : function(data) {
			
			var rsltCd = data.result.rsltCd;
			var rsltMsg = data.result.rsltMsg;
			if(rsltCd == 0) { //저장성공
				alert("저장 되었습니다.");
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