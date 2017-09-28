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
	//화면 로딩시 checkbox 크기 조정
	$('input[type=checkbox]').css({'margin':'10px','border':'1px solid red'}); 
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
			//$('.log').prepend('<li>Requested page ' + page + '</li>');
		}
	});
	
	var tbl = $('#resultTable');
	// 테이블 헤더에 있는 checkbox 클릭시
	$(":checkbox:first", tbl).click(function(){
		// 클릭한 체크박스가 체크상태인지 체크해제상태인지 판단
		if( $(this).is(":checked") ){
			$(":checkbox", tbl).attr("checked", "checked");
		}
		else{
			$(":checkbox", tbl).removeAttr("checked");
		}
		
		// 모든 체크박스에 change 이벤트 발생시키기
		$(":checkbox", tbl).trigger("change");
	});
	
	// 헤더에 있는 체크박스외 다른 체크박스 클릭시
	$(":checkbox:not(:first)", tbl).click(function(){
		var allCnt = $(":checkbox:not(:first)", tbl).length;
		var checkedCnt = $(":checkbox:not(:first)", tbl).filter(":checked").length;
		
		// 전체 체크박스 갯수와 현재 체크된 체크박스 갯수를 비교해서 헤더에 있는 체크박스 체크할지 말지 판단
		if( allCnt==checkedCnt ){
			$(":checkbox:first", tbl).attr("checked", "checked");
		}
		else{
			$(":checkbox:first", tbl).removeAttr("checked");
		}
	}).change(function(){
		if( $(this).is(":checked") ){
			// 체크박스의 부모 > 부모 니까 tr 이 되고 tr 에 selected 라는 class 를 추가한다.
			$(this).parent().parent().addClass("selected");
		}
		else{
			$(this).parent().parent().removeClass("selected");
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
			        <jsp:param name="selected" value="memberSearch"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li><a href="memberList.html">회원조회</a></li>
				<!-- <li class="select"><a href="javascript:memberSearch()">(구)회원조회</a></li> -->
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">회원조회</h2>
				
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
							<th>회원아이디</th>
							<td><input type="text" id="cond_memberId" name="cond_memberId"></td>
							<th>로그인아이디</th>
							<td><input type="text" id="cond_loginId" name="cond_loginId"></td>
						</tr>
						<tr>
							<th>닉네임</th>
							<td><input type="text" id="cond_nickName" name="cond_nickName"></td>
							<th>성별</th>
							<td>
								<select id="cond_sex" name="cond_sex">
									<option value="ALL">---- 전체 ----</option>
									<option value="G01001">남자</option>
									<option value="G01002">여자</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>국내/외 구분</th>
							<td>
								<select id="cond_country" name="cond_country">
									<option value="ALL">---- 전체 ----</option>
									<option value="L">국내</option>
									<option value="F">국외</option>
								</select>
							</td>
							<th>본인인증 여부</th>
							<td>
								<select id="cond_oneselfCertYn" name="cond_oneselfCertYn">
									<option value="ALL">---- 전체 ----</option>
									<option value="Y">Y</option>
									<option value="N">N</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>정회원 여부</th>
							<td>
								<select id="cond_membershipYn" name="cond_membershipYn">
									<option value="ALL">---- 전체 ----</option>
									<option value="Y">Y</option>
									<option value="N">N</option>
								</select>
							</td>
							<td></td>
							<td></td>
						</tr>
					</table>
					<input type="hidden" id="pageSize" name="pageSize" value="10"/>
					<input type="hidden" id="page" name="page" value="<%=page %>"/>
					<input type="hidden" id="startRow" name="startRow" value="0"/> 
				</div>
				</form>
				
				<div class="ui-btns alignLeft">
					<button id="btn_search" data-click-callback="searchInit()">조회</button>
					<button id="btn_search" data-click-callback="add()">신규회원</button>
					<button id="btn_search" data-click-callback="sendMsg('sendMsgPop')">쪽지발송</button>
				</div>
				
				<div class="pt30">
				</div>
				<div class="pt30">
					<table class="tbl_nor" id="resultTable" cellspacing="0" summary="회원조회">
						<tbody>
							<caption>회원조회</caption>
							<colgroup>
								<col width="50">
								<col width="100">
								<col width="150">
								<col width="150">
								<col width="80">
								<col width="150">
							</colgroup>
							<tr id="tb_header">
								<th><input type="checkbox"></th> 
								<th>회원ID</th>
								<th>로그인ID</th>
								<th>닉네임</th>
								<th>성별</th>
								<th>가입일자</th>
							</tr>
						</tbody>
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
			<!-- 
			<ol class="info">
				<li>.</li>
			</ol>
			 -->
		</div>
	</div>
</div>

<!-- 쪽지발송 팝업 레이어 -->
<div id="sendMsgPop" style="display:none;">
	<div class="content">
		<div class="ui-box ui-box-white ui-box-shadow">
			<h2 class="icon-arrow-green">쪽지발송</h2>
			
			<form id="sendMsgForm">
			<table class="tbl_nor" cellspacing="0" summary="쪽지발송">
				<caption>쪽지발송</caption>
				<colgroup>
					<col width="140">
				</colgroup>
				<tr>
					<th>발신자선택</th>
					<td>
						<button type="button" onclick="choiceMember()">선택</button>
						<input type="hidden" id="gCountry" name="gCountry" value="${ response.talk.country }"/>
						<input type="hidden" id="gLang" name="gLang" value="${ response.talk.lang }" />
						<input type="hidden" id="gMemberId" name="gMemberId"  value="${ response.talk.memberId }"/>
						<div id="memberDiv">
						</div>
					</td>
				</tr>
				<tr>
					<th>수신자</th>
					<td>
						<textarea rows="6" id="receiverList" name="receiverList" readonly="readonly"></textarea>
					</td>
				</tr>
				<tr>
					<th>내용</th>
					<td>
						<textarea rows="6" id="msgText" name="msgText" ></textarea>
					</td>
				</tr>
			</table>
			</form>
			
			<div class="ui-btns">
				<button id="btn_msg_close" data-click-callback="javascript:$.unblockUI();">닫기</button>
				<button id="btn_msg_send" data-click-callback="callSendMsg()">전송</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">

    function rebind(token, memberId, nickName, country, lang, photoUrl) {
		if(token == "sendmsg") {
			$("#gMemberId").val(memberId);
			$("#gCountry").val(country);
			$("#gLang").val(lang);
			$("#memberDiv").html("<img src='" + photoUrl + "' class='profile-img'/> " + nickName);
		}
    }

	function choiceMember() {
		window.open("memberSearchPop.html?token=sendmsg", "", "width=600, height=600");
		return;
	}

	//신규등록
	function add() {
		location.href="/missingu/admin/memberEdit.html?actionType=add";
	}
	
	//조회
	function memberSearch() {
		location.href="/missingu/admin/memberSearch.html";
	}
	
	function searchInit() {
		searchCnt();
		
	}
	
	function searchCnt() {
		$.ajax({
			url : "/missingu/admin/getMemberListCnt.html",
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
					$(".desc").remove();
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
			url : "/missingu/admin/getMemberList.html",
			type : "POST",
			cache : false,
			data : $("#searchForm").serialize(),
			dataType : "json",
			success : function(data) {
				//$("#memberPoint").html(data.response.memberPoint).append(" P");
				var rsltCd = data.result.rsltCd;
				
				if(rsltCd == 0) { //조회성공
					var memberList = data.response.memberList;
					var htmls = "";
					$.each(memberList, function(i){
						htmls +="<tr class=\"desc\">";
						//htmls +="	<td>"+(i+1)+"</td>";
						htmls +="	<td><input type='checkbox'></td>";
						htmls +="	<td><a href='/missingu/admin/memberEdit.html?actionType=modify&memberId=" +this.memberId+"'>"+this.memberId+"</a></td>";
						htmls +="	<td>"+this.loginId+"</td>";
						htmls +="	<td>"+this.nickName+"</td>";
						htmls +="	<td>"+this.sex+"</td>";
						htmls +="	<td>"+this.createdDate+"</td>";
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
	
	//쪽지발송
	function sendMsg(popupId) {
		var strCheckedMember = getCheckedMemberString();
		
		if(strCheckedMember != '') {
			$('#receiverList').val(strCheckedMember);
			showPopup(popupId);
		} else {
			alert("대상 회원을 선택해 주세요.");
		}
	}
	
	//선택된 회원 memberId 문자열 리턴(구분자 : ,)
	function getCheckedMemberString() {
		var rtnStr = '';
		
		$(".desc").each(function(){
			if( $("input:checkbox", this).is(":checked") ) {
				if(rtnStr != '') {
					rtnStr += ',';
				}
				rtnStr += $(this).find("a").text();
				
			}
		});
		
		return rtnStr;
		
	}
	
	//GCM 메세지 발송 서비스 호출
	function callSendMsg() {
		$.ajax({
			url : "/missingu/admin/sendMsg.html",
			type : "POST",
			cache : false,
			data : $("#sendMsgForm").serialize(),
			dataType : "json",
			success : function(data) {
				var rsltCd = data.result.rsltCd;
				
				if(rsltCd == 0) { //조회성공
					alert("메세지를 전송하였습니다.");
				} else {
					alert(data.result.rsltMsg);
				}
			},
			error : function(data) {
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