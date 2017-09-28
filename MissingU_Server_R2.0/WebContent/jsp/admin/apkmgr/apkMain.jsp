<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<title><spring:message code="admin.apkmgr.apkMain.title" /></title>
<%@ include file="../../common/admin_header.jsp"%>
</head>
<body>
	
	<div class="ui-box ui-box-white ui-box-shadow">
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
							</c:url>">${apkItem.apkFileName}</a></td>
						<td align="left">${apkItem.apkDesc}</td>
						<td align="center">${apkItem.apkVersion}</td>
						<td align="center">${apkItem.registerName}</td>
						<td align="center">${apkItem.regDate}</td>
						<td align="center"><a
							href="<c:url value="/missingu/admin/apkDelete.html">
						<c:param name="apkId" value="${apkItem.apkId}"></c:param>
						</c:url>">삭제</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<div class="ui-btns">
			<button id="btn_new" >신규 업로드</button>
		</div>
		
	</div>
<script type="text/javascript">
	$("#btn_new").click(function(){
		parent.apkAdd();
	});
</script>
</body>
</html>