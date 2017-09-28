<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<%@ include file="/jsp/common/header.jsp"%>
<title><spring:message code="admin.apkmgr.apkAdd.title" /></title>
</head>
<body>
<form:form modelAttribute="apkItem" action="/missingu/admin/apkAddSubmit.html"	enctype="multipart/form-data">
	<div align="center" class="body">
	<h2><font color="green"><spring:message code="admin.apkmgr.apkAdd.title" /></font></h2>
	<table>
		<tr height="40px">
			<td>설명</td>
			<td><form:input path="apkDesc" cssClass="apkDesc" maxlength="50" /></td>
			<td><font color="red"><form:errors path="apkDesc" /></font></td>
		</tr>
		<tr height="40px">
			<td>버전</td>
			<td><form:input path="apkVersion" cssClass="apkVersion" maxlength="50" /></td>
			<td><font color="red"><form:errors path="apkVersion" /></font></td>
		</tr>
		<tr height="40px">
			<td>등록자</td>
			<td><form:input path="registerName" cssClass="registerName" maxlength="50" /></td>
			<td><font color="red"><form:errors path="registerName" /></font></td>
		</tr>
		<tr height="40px">
			<td>APK 파일</td>
			<td><input type="file" name="apkFile" /></td>
			<td></td>
		</tr>
	</table>
	<br>
	<input type="submit" value="등록" /> <input type="reset" value="리셋" /><br>
</form:form>
</body>
</html>