<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ko">
<head>
<meta charset="utf-8">
<title>MissingU Administrator</title>
<%@ include file="/jsp/common/admin_header.jsp"%>
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
			        <jsp:param name="selected" value="apkList"/>
			</jsp:include>
		</nav>
	</header>
	<div class="body">
		<aside>
			<ul class="ui-box ui-box-white ui-box-shadow">
				<li class="select"><a href="apkList.html">MissingU APK/공지</a></li>
				<li><a href="apkListRandomChat.html">채팅 APK/공지</a></li>
			</ul>
		</aside>
		<div class="content">
			<div class="ui-box ui-box-white ui-box-shadow">
				<!-- Content 시작 -->
				<h2 class="icon-arrow-green">APK 업그레이드 </h2>
				<form name="search" id="search" method="post" action="apkUpgradeConfirm.html" onsubmit="return checkApkUpgrade();">
				<div class="rbox">
					<ul class="search">
						<li><label for="lang">언어</label>
							<select name="lang" id="lang" class="reload">
								<option value='ko' <c:if test="${ response.upgradeInfo.lang eq 'ko' }">selected</c:if>>한국어</option>
								<option value='ja' <c:if test="${ response.upgradeInfo.lang eq 'ja' }">selected</c:if>>일본어</option>
								<option value='zh' <c:if test="${ response.upgradeInfo.lang eq 'zh' }">selected</c:if>>중국어</option>
							</select>
						</li>
						<li><label for="version_code">최신버전</label><input type="text" id="version_code" name="version_code" value="${ response.upgradeInfo.versionCode }" /></li>
						<li><label for="version_name">버전이름</label><input type="text" id="version_name" name="version_name" value="${ response.upgradeInfo.versionName }" /></li>
						<li><label for="required_upgrade">필수업그레이드여부</label><input type="checkbox" id="required_upgrade" name="required_upgrade" value="1"  <c:if test="${ response.upgradeInfo.requiredUpgrade eq '1' }">checked</c:if>/></li>
						<li><label for="required_upgrade_for_vc">필수 하위버전</label><input type="text" id="required_upgrade_for_vc" name="required_upgrade_for_vc" value="${ response.upgradeInfo.requiredUpgradeForVersionCode }" /></li>
					</ul>
				</div>
				<div class="pt10"><button type="submit">!!!!!!!!업그레이드 적용!!!!!!!!</button></div> 
				</form>
				<br /><br />
				
				<h2 class="icon-arrow-green">긴급공지 정보</h2>
				<form name="search2" id="search2" method="post" action="emrNoticeConfirm.html" onsubmit="return checkEmrNotice();">
				<div class="rbox">
					<ul class="search">
						<li class="h50"><label for="show_yn">사용여부</label>
							<select name="show_yn" id="show_yn">
								<option value='Y' <c:if test="${ response.emrNotice.showYn eq 'Y' }">selected</c:if>>사용</option>
								<option value='N' <c:if test="${ response.emrNotice.showYn eq 'N' }">selected</c:if>>사용안함</option>
							</select>
						</li>
						<li class="h50"><label for="lang">언어</label>
							<select name="lang" id="lang" class="reload">
								<option value='ko' <c:if test="${ response.emrNotice.lang eq 'ko' }">selected</c:if>>한국어</option>
								<option value='ja' <c:if test="${ response.emrNotice.lang eq 'ja' }">selected</c:if>>일본어</option>
								<option value='zh' <c:if test="${ response.upgradeInfo.lang eq 'zh' }">selected</c:if>>중국어</option>
							</select>
						</li>
						<li class="h50"><label for="onday">1일1회띄우기</label><input type="checkbox" id="onday" name="onday" value="1"  <c:if test="${ response.emrNotice.onday eq '1' }">checked</c:if>/></li>
						<li class='h50 two-line'><label for="msg">내용</label>
							<textarea id="msg" name="msg" style="width:90%; height:50px;">${ response.emrNotice.message }</textarea>
						</li>
					</ul>
					<ul class="search">
						<li><label for="positive_usable">확인버튼</label><input type="checkbox" id="positive_usable" name="positive_usable" value="1"  <c:if test="${ response.emrNotice.positiveUsable eq '1' }">checked</c:if>/></li>
						<li><label for="positive_label">확인버튼명</label>
							<input type="text" id="positive_label" name=positive_label value="${ response.emrNotice.positiveLabel }" />
						</li>
						<li><label for="positive_action">확인액션</label>
							<select name="positive_action" id="positive_action">
								<option value='0' <c:if test="${ response.emrNotice.positiveAction eq '0' }">selected</c:if>>창닫기</option>
								<option value='1' <c:if test="${ response.emrNotice.positiveAction eq '1' }">selected</c:if>>앱종료</option>
								<option value='2' <c:if test="${ response.emrNotice.positiveAction eq '2' }">selected</c:if>>창닫은후 링크</option>
								<option value='3' <c:if test="${ response.emrNotice.positiveAction eq '3' }">selected</c:if>>앱종료후 링크</option>
							</select>
						</li>
						<li><label for="positive_url">확인링크</label>
							<input type="text" id="positive_url" name=positive_url value="${ response.emrNotice.positiveUrl }" />
						</li>
					</ul>
					<ul class="search">
						<li><label for="negative_usable">취소버튼</label><input type="checkbox" id="negative_usable" name="negative_usable" value="1"  <c:if test="${ response.emrNotice.negativeUsable eq '1' }">checked</c:if>/></li>
						<li><label for="negative_label">취소버튼명</label>
							<input type="text" id="negative_label" name=negative_label value="${ response.emrNotice.negativeLabel }" />
						</li>
						<li><label for="negative_action">취소액션</label>
							<select name="negative_action" id="negative_action">
								<option value='0' <c:if test="${ response.emrNotice.negativeAction eq '0' }">selected</c:if>>창닫기</option>
								<option value='1' <c:if test="${ response.emrNotice.negativeAction eq '1' }">selected</c:if>>앱종료</option>
								<option value='2' <c:if test="${ response.emrNotice.negativeAction eq '2' }">selected</c:if>>창닫은후 링크</option>
								<option value='3' <c:if test="${ response.emrNotice.negativeAction eq '3' }">selected</c:if>>앱종료후 링크</option>
							</select>
						</li>
						<li><label for="ignore_url">취소링크</label>
							<input type="text" id="ignore_url" name=ignore_url value="${ response.emrNotice.ignoreUrl }" />
						</li>
						<li style="float:none;"></li>
						<li><label for="ignore_usable">무시버튼</label><input type="checkbox" id="ignore_usable" name="ignore_usable" value="1"  <c:if test="${ response.emrNotice.ignoreUsable eq '1' }">checked</c:if>/></li>
						<li><label for="ignore_label">무시버튼명</label>
							<input type="text" id="ignore_label" name=ignore_label value="${ response.emrNotice.ignoreLabel }" />
						</li>
						<li><label for="ignore_action">무시액션</label>
							<select name="ignore_action" id="ignore_action">
								<option value='0' <c:if test="${ response.emrNotice.ignoreAction eq '0' }">selected</c:if>>창닫기</option>
								<option value='1' <c:if test="${ response.emrNotice.ignoreAction eq '1' }">selected</c:if>>앱종료</option>
								<option value='2' <c:if test="${ response.emrNotice.ignoreAction eq '2' }">selected</c:if>>창닫은후 링크</option>
								<option value='3' <c:if test="${ response.emrNotice.ignoreAction eq '3' }">selected</c:if>>앱종료후 링크</option>
							</select>
						</li>
						<li><label for="ignore_url">무시링크</label>
							<input type="text" id="ignore_url" name=ignore_url value="${ response.emrNotice.ignoreUrl }" />
						</li>
					</ul>
				</div>
				<div class="pt10"><button type="submit">!!!!!!!!긴급공지 적용!!!!!!!!</button></div> 
				</form>
				<br /><br />
				
				<h2 class="icon-arrow-green">APK 목록</h2>
				<div class="pt30">
					<table class="tbl_nor" cellspacing="0" summary="결제정보 입력">
						<caption>결제정보입력</caption>
						<colgroup>
							<col width="0" hidden="true">
							<col width="100">
							<col width="150">
							<col width="*">
							<col width="100">
							<col width="80">
							<col width="150">
							<col width="80">
						</colgroup>
						<tr id="tb_header">
							<th hidden="true">번호</th>
							<th>APK ID</th>
							<th>파일명</th>
							<th>설명</th>
							<th>버전</th>
							<th>등록자</th>
							<th>등록일자</th>
							<th>삭제</th>
						</tr>
						<c:forEach items="${apkItemList}" var="apkItem">
							<tr class="record">
								<td align="center">${apkItem.apkId}</td>
								<td align="center">
									<a href="<c:url value="/missingu/admin/apkDownload.apk">
										<c:param name="apkFileName" value="${apkItem.apkFileName}"></c:param>
									</c:url>"><font color="blue" style="text-decoration:underline;">${apkItem.apkFileName}</font></a></td>
								<td align="left">${apkItem.apkDesc}</td>
								<td align="center">${apkItem.apkVersion}</td>
								<td align="center">${apkItem.registerName}</td>
								<td align="center">${apkItem.regDate}</td>
								<td align="center"><a href="javascript:deleteApk('${apkItem.apkId}');"><font color="blue" style="text-decoration:underline;">삭제</font></a></td>
							</tr>
						</c:forEach>
					</table>
				</div>
				
				<div class="ui-btns">
					<button id="btn_new" data-click-callback="apkAdd()">신규 업로드</button>
				</div>
				<!-- Content 끝 -->
			</div>
			<ol class="info">
				<li>파일명을 클릭하시면 APK를 다운 받으실 수 있습니다.</li>
			</ol>
		</div>
	</div>
</div>
<script type="text/javascript">
	function apkList() {
		location.href="/missingu/admin/apkList.html";
	}
	
	function apkAdd() {
		location.href="/missingu/admin/apkAdd.html";
	}
	
	function deleteApk(apkId) {
		if(confirm("삭제하시겠습니까?")) {
			location.href="/missingu/admin/apkDelete.html?apkId="+apkId;
		}
	}
	
	function checkApkUpgrade() {
		return confirm("업그레이드 적용 전 마켓배포 확인!!\n\n업그레이드를 적용하면 새로 로그인 하는 사용자부터 업그레이드 알림이 적용 됩니다.\n\n진행 하시겠습니까??");
	}
	
	function checkEmrNotice() {
		return confirm("긴급공지가 즉시 적용됩니다. \n\n진행 하시겠습니까?");
	}
	
	$(".reload").change(function() {
		location.href='apkList.html?lang=' + $(this).val();
	});
	
</script>
</body>
</html>