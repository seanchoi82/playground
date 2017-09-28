<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="ko">
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/admin_header.jsp"%>
<title>MissingU Administrator</title>
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
				<h2 class="icon-arrow-green">톡투미 글</h2>
				
				<div>
				<form id="memberForm" name="memberForm" method="post" enctype="multipart/form-data">
					<input type="hidden" name="talkId" id="talkId" value="${reqParams.talkId}" />
					<table class="tbl_nor" cellspacing="0" summary="톡투미">
						<caption>회원 기본정보</caption>
						<colgroup>
							<col width="140">
							<col width="300">
							<col width="140">
							<col width="300">
						</colgroup>
						<tr>
							<th>회원아이디</th>
							<td colspan="3">
								<table border='0' cellspacing="0" summary="톡투미">
								<tr>
									<td>
								<button type="button" onclick="choiceMember()">선택</button></td>
								<td>
								<input type="hidden" id="gCountry" name="gCountry" value="${ response.talk.country }"/>
								<input type="hidden" id="gLang" name="gLang" value="${ response.talk.lang }" />
								<input type="hidden" id="gMemberId" name="gMemberId"  value="${ response.talk.memberId }"/>
								<div id="memberDiv">
									<c:choose>
										<c:when test="${fn:length(response.memberId) > 0 }">
										회원정보 없음
										</c:when>
										<c:otherwise>
										 <img src="${ response.talk.mainPhotoUrl }" class="profile-img"/> ${response.talk.nickName }
										</c:otherwise>
									</c:choose>
								</div>
								</td>
								</tr>
								</table>
							</td>
						</tr>
						<tr>
							<th>제목</th>
							<td colspan="3"><input type="text" id="title" name="title" value="${ response.talk.title }" ></td>
						</tr>
						<tr>
							<th>제목</th>
							<td colspan="3"><textarea id="contents" name="contents" rows="10" style="width:90%">${ response.talk.contents }</textarea></td>
						</tr>
						<tr>
							<th>이미지</th>
							<td colspan="3">
								<input type="file" id="uploadFile" name="uploadFile"  accept="image/*" />
								<input type="hidden" id="talkToMePhotoUrl"><br />
								
								<a href="${ response.talk.talkPhotoOrgUrl }" class="nyroModal" title="톡투미 첨부파일"><img src="${response.talk.talkPhotoUrl }" class="profile-img"/></a>
								
							</td>
						</tr>
					</table>
				</form>
				</div>
				
				<div class="ui-btns alignLeft">
					<button id="btn_back" data-click-callback="back()">뒤로</button>
					<button id="btn_search" data-click-callback="save()">저장</button>
					<button id="btn_delete" data-click-callback="deleteTalkToMe()">삭제</button>
				</div>
				
				<div class="pt30">
				</div>
				<div class="pt30">
				</div>
				
				<h2 class="icon-arrow-green">댓글</h2>
				<form id="replyForm">
					<input type="hidden" name="talkId" value="${ reqParams.talkId }" />
				<table width="100%" cellspacing="0">
					<colgroup>
						<col width="150px" />
						<col />
						<col width="120px" />
					</colgroup>	
					<tr>
						<td>
							<div>
							<button type="button" onclick="choiceMemberByReply()">회원선택</button>
							</div>
							<div id="previewPic"></div>
							<input type="hidden" id="rCountry" name="gCountry" value="${ response.talk.country }"/>
							<input type="hidden" id="rLang" name="gLang" value="${ response.talk.lang }" />
							<input type="hidden" id="rMemberId" name="gMemberId"  value="${ response.talk.memberId }"/>
						</td>
						<td><textarea id="replyContent" name="replyContent" style="width:100%" rows="2" ></textarea></td>
						<td><button type="button" onclick="reply()" style="height:40px;">댓글달기</button>
					</tr>
				</table>
				</form>
				<table class="tbl_nor" cellspacing="0" summary="톡투미 댓글">
					<colgroup>
						<col width="20px" />
						<col width="100px" />
						<col width="80px" />
						<col />
						<col width="100px" />
					</colgroup>
					<tr>
						<th><input type="checkbox" id="replyAll" data-name="replyIds" class="checkInverse"/></th>
						<th>회원이미지 </th>
						<th>닉네임</th>
						<th>내용</th>
						<th>날짜</th>
					</tr>
					<c:if test="${fn:length(response.talk.reply) == 0}">
							<tr>
								<td align="center" colspan="5">댓글 데이터가 없습니다.</td>
							</tr>
						</c:if>
						<c:forEach items="${response.talk.reply}" var="replyItem">
							<tr>	
								<td><input type="checkbox" name="replyIds" value="${replyItem.replyId}"  /></td>
								<td><img src="${replyItem.mainPhotoUrl }" class="profile-img" /></td>
								<td>${replyItem.nickName }</td>
								<td>${replyItem.replyContent }</td>
								<td>${replyItem.createdDate }</td>
							</tr>
						</c:forEach>
				</table>
				<div class="pt30">
				<button type="button" onclick="deleteReply()">댓글 삭제</button>
				</div>
				<!-- Content 끝 -->
			</div>
			
			<ol class="info">
				<li>.</li>
			</ol>
		</div>
	</div>
</div>

<script type="text/javascript">

	function deleteReply() {
		
		if(!confirm("정말 삭제 하시겠습니까?"))
			return;
		
		var arrs = [];
		$("input[name='replyIds']").each(function() {
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
			url : "delTalkReply.html",
			type : "POST",
			cache : false,
			data : { replyIds:ids, talkId : '${ reqParams.talkId }' }, 
			dataType : "json",
			success : function(data) {
				
				//$("#memberPoint").html(data.response.memberPoint).append(" P");
				var rsltCd = data.result.rsltCd;
				var rsltMsg = data.result.rsltMsg;
				
				if(rsltCd == 0) { //저장성공
					alert("성공");
					location.reload();
				} else {
					alert(data.result.rsltMsg);
				}
			},
			error : function(data) {
				alert('<spring:message code="comm.systemError.msgText" />');
			}
		});
	}

	function reply() {
		
		if(!$("#rMemberId").val()) {
			alert("등록할 회원을 선택 하세요.");
			return;
		}
		
		if(!$("#replyContent").val()) {
			alert("등록할 댓글을 입력 하세요.");
			return;
		}
		
		$.ajax({
			url : "saveTalkReply.html",
			type : "POST",
			cache : false,
			data : $("#replyForm").serialize(),
			dataType : "json",
			success : function(data) {
				
				//$("#memberPoint").html(data.response.memberPoint).append(" P");
				var rsltCd = data.result.rsltCd;
				var rsltMsg = data.result.rsltMsg;
				
				if(rsltCd == 0) { //저장성공
					alert("성공");
					location.reload();
				} else {
					alert(data.result.rsltMsg);
				}
			},
			error : function(data) {
				alert('<spring:message code="comm.systemError.msgText" />');
			}
		});
	}

	function rebind(token, memberId, nickName, country, lang, photoUrl) {
		
		if(token == "talktome") {
			$("#gMemberId").val(memberId);
			$("#gCountry").val(country);
			$("#gLang").val(lang);
			$("#memberDiv").html("<img src='" + photoUrl + "' class='profile-img'/> " + nickName);
		}else{
			$("#rMemberId").val(memberId);
			$("#rCountry").val(country);
			$("#rLang").val(lang);
			$("#previewPic").html("<img src='" + photoUrl + "'  class='profile-img'/><p>" + nickName + "</p>");
		}
	}

	function choiceMember() {
		window.open("memberSearchPop.html?token=talktome", "", "width=600, height=600");
		return;
	}
	
	function choiceMemberByReply() {
		window.open("memberSearchPop.html?token=talktomereply", "", "width=600, height=600");
		return;
	}

	function back() {
		// location.href="talkToMeList.html?startRow=" + $("#startRow").val();
		history.back();
	}
		
	function deleteTalkToMe() {
		if(confirm("정말 삭제 하시겠습니까? 회원 상태 변경이 아닌 실제 계정이 삭제 됩니다.")) {
			$.ajax({
				url : "talkToMeDelete.html",
				type : "POST",
				cache : false,
				data : $("#memberForm").serialize(),
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
	}
	
	//회원저장
	function save() {
		if(!validateSaveParams()) {
			return false;
		}
		
		saveWithFile();
	}
	
	//회원저장 요청에 File 있음
	function saveWithFile() {
	    var options = {
			//target:        '#output1',   // target element(s) to be updated with server response
			//beforeSubmit:  showRequest,  // pre-submit callback
			//success:       showResponse  // post-submit callback
			url : $("#talkId").val() == "" ? "/missingu/admin/saveTalk.html" : "/missingu/admin/editTalk.html",
			type : "POST",
			dataType : "json",
			success : function(data) {
				$.unblockUI();
				//$("#memberPoint").html(data.response.memberPoint).append(" P");
				var rsltCd = data.result.rsltCd;
				
				if(rsltCd == 0) { //저장성공
					alert("성공");
					if(data.response != undefined) {
						$("#talkId").val(data.response.talkId);
					}
					location.href="/missingu/admin/talkToMeEdit.html?talkId="+$("#talkId").val();
				} else {
					alert(data.result.rsltMsg);
				}
			},
			error : function(data) {
				$.unblockUI();
				alert('<spring:message code="comm.systemError.msgText" />');
			}
			// other available options:
			//url:       url         // override for form's 'action' attribute
			//type:      type        // 'get' or 'post', override for form's 'method' attribute
			//dataType:  null        // 'xml', 'script', or 'json' (expected server response type)
			//clearForm: true        // clear all form fields after successful submit
			//resetForm: true        // reset the form after successful submit
			
			// $.ajax options can be used here too, for example:
			//timeout:   3000
		};
	    
	    if(!$("#uploadFile").val()) {
	    	options.cache = false;
	    	options.data = $("#memberForm").serialize();
	    	$.ajax(options);
	    }else{
			// bind form using 'ajaxForm'
			$('#memberForm').ajaxSubmit(options);
			$.blockUI();
	    }
		
	}

	function validateSaveParams() {
		if($("#gMemberId").val() == '') {
			alert("회원을 선택해 주세요.");
			$("#loginId").focus();
			return false;
		}
		if($("#nickName").val() == '') {
			alert("닉네임을 입력해 주세요.");
			$("#nickName").focus();
			return false;
		}
		
		return true;
	}
	
	//포인트 부여 서비스 호출
	function callGivePoint() {
		if(confirm($('#giftPoint').val() + " 포인트를 부여 하시겠습니까?")) {
			$.ajax({
				url : "/missingu/admin/givePoint.html",
				type : "POST",
				cache : false,
				data : $("#givePointForm").serialize(),
				dataType : "json",
				success : function(data) {
					$.unblockUI();
					var rsltCd = data.result.rsltCd;
					
					if(rsltCd == 0) { //조회성공
						alert("성공");
						location.href="/missingu/admin/memberEdit.html?actionType=modify&memberId="+$("#memberId").val();
					} else {
						alert(data.result.rsltMsg);
					}
				},
				error : function(data) {
					$.unblockUI();
					alert('<spring:message code="comm.systemError.msgText" />');
				}
			});
			$.blockUI();
		}
	}
	
</script>
</body>
</html>