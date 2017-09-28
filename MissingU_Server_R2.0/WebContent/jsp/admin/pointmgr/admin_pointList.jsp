<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ko">
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/admin_header.jsp"%>
<title>MissingU Administrator</title>
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
			        <jsp:param name="selected" value="payHistList"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<jsp:include page="/jsp/common/admin_lmenu_cash.jsp" flush="false">
			        <jsp:param name="selected" value="5"/>
				</jsp:include>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">포인트 조회</h2>
				
				<form id="searchForm" action="pointList.html" method="get">
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
						<td><input type="text" name="nickName"  value="${ reqParams.nickName }"></td>
						<th>로그인아이디</th>
						<td><input type="text" name="loginId" value="${ reqParams.loginId }"></td>
					</tr>
					<tr>
						<th>포인트</th>
						<td colspan="3">
							<input type="number" name="startPointRange"  value="${ reqParams.startPointRange }">
							~
							<input type="number" name="endPointRange"  value="${ reqParams.endPointRange }">
						</td>
					</tr>
				</table>
				</form>
				<div class="ui-btns alignLeft">
					<button id="btn_search" data-click-callback="searchInit()">조회</button>
				</div>
				
				<div class="pt30">
					<table class="tbl_nor" cellspacing="0" summary="포인트 내역">
						<caption>포인트 내역</caption>
						<colgroup>
							<col>
							<col width="150">
							<col width="200">
							<col width="100">
						</colgroup>
						<tr id="tb_header">
							<th>회원</th>
							<th>가입일</th>
							<th>금액</th>
							<th>작업</th>
						</tr>
						<c:forEach items="${ response.dataList }" var="item">
							<tr>
								<td>
									<img src="${ item.mainPhotoUrl }" class="profile-img" data-member-id="${ item.memberId }" />
									<a href="/missingu/admin/memberEdit.html?actionType=modify&memberId=${ item.memberId }" target="_balnk">${ item.nickName }</a> 
									(<c:choose><c:when test='${ item.sex == "G01001" }'>남</c:when><c:otherwise>여</c:otherwise></c:choose>)
								</td>
								<td class="ct">${ item.createdDate }</td>
								<td class="ct">${ item.itemAmount}</td>
								<td class="ct">
									&nbsp;
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				
				<!-- Content 끝 -->
			</div>
			
			<jsp:include page="/jsp/common/paging.jsp">
			    <jsp:param name="actionPath" value="pointList.html"/>
			    <jsp:param name="totalCount" value="${ response.totalCnt }"/>
			    <jsp:param name="countPerPage" value="${ reqParams.pageSize }"/>
			    <jsp:param name="blockCount" value="5"/>
			    <jsp:param name="nowPage" value="${ reqParams.nowPage }"/>
			    <jsp:param name="addQuerys" value="loginId=${ reqParams.loginId }&amp;nickName=${ reqParams.nickName }&amp;startPointRange=${ reqParams.startPointRange }&amp;endPointRange=${ reqParams.endPointRange }"/>
			</jsp:include>
		</div>
	</div>
</div>

<script type="text/javascript">

	function rebind(token, memberId, nickName, country, lang, photoUrl) {
		
		if(token == "sender") {
			$("#cond_senderId").val(memberId);
			$("#cond_senderIdImg").html("<img src='" + photoUrl + "' class='profile-img'/> " + nickName);
			$("#cond_senderCountry").val(country);
			$("#cond_senderLang").val(lang);
		}else if(token == "receiver") { 
			$("#cond_receiverId").val(memberId);
			$("#cond_receiverIdImg").html("<img src='" + photoUrl + "' class='profile-img'/> " + nickName);
		}
	}
	
	function choiceMember(token) {
		window.open("/missingu/admin/memberSearchPop.html?token=" + token, "", "width=600, height=600");
		return;
	}

	function giftCancel(id) {
		if(confirm("이력삭제 후 발송건수가 복원됩니다. 단 포인트는 회수되지 않습니다. 그래도 진행하시겠습니까?\n\n※여성 및 관리자 무료발송이 있기 때문에 회수불가")) {
			$.ajax({
				url : "/missingu/admin/cash/giftDelete.html",
				type : "POST",
				cache : false,
				data : { id:id }, 
				dataType : "json",
				success : function(data) {
					
					var rsltCd = data.result.rsltCd;
					var rsltMsg = data.result.rsltMsg;
					if(rsltCd == 0) {
						location.reload();
					} else {
						alert(rsltMsg);
					}
				},
				error : function(data) {
					alert('<spring:message code="comm.systemError.msgText" />');
				}
			});
		}
	}
	
	function giftSend() {
		if(!$("#cond_senderId").val()) {
			alert("보내는 사람을 선택하세요.");
			return;
		}else if(!$("#cond_receiverId").val()) {
			alert("받는 사람을 선택하세요.");
			return;
		}
		
		$.ajax({
			url : "/missingu/friends/sendGift.html",
			type : "POST",
			cache : false,
			data : { 
					gMemberId: $("#cond_senderId").val(), 
					gLang : $("#cond_senderLang").val(), 
					gCountry : $("#cond_senderCountry").val(), 
					friendId : $("#cond_receiverId").val(), 
					gcmPass : $("#gcmPass").is(":checked"), 
					pointPass : $("#pointPass").is(":checked"), 
					itemCd : "T03001"
				}, 
			dataType : "json",
			success : function(data) {
				
				var rsltCd = data.result.rsltCd;
				var rsltMsg = data.result.rsltMsg;
				if(rsltCd == 0) {
					location.reload();
				} else {
					alert(rsltMsg);
				}
			},
			error : function(data) {
				alert('<spring:message code="comm.systemError.msgText" />');
			}
		});
		
		// 1. 보내는 사람 선택
		// 2. 받는 사람 선택
		// 3. 발송시 gcm 보내기 및 포인트 차감 사용 여부 확인
		// 4. 발송
	}
	
	function searchInit() {
		$("#searchForm").submit();
	}
	
	$(document).ready(function() {
		
	});
	
</script>
</body>
</html>