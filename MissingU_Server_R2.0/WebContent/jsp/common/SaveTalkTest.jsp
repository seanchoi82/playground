<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<%@ include file="/jsp/common/header.jsp"%>
<title>Multipart Request Test</title>
</head>
<body>
<form:form modelAttribute="saveTalkReqVO" action="/missingu/talktome/saveTalk.html"	enctype="multipart/form-data">
	<div align="center" class="body">
	<h2><font color="green">Multipart Request Test</font></h2>
	<table>
		<tr height="40px">
			<td>gMemberId</td>
			<td><form:input path="gMemberId" cssClass="apkDesc" maxlength="50" /></td>
			<td><font color="red"><form:errors path="gMemberId" /></font></td>
		</tr>
		<tr height="40px">
			<td>gLang</td>
			<td><form:input path="gLang" cssClass="apkVersion" maxlength="50" /></td>
			<td><font color="red"><form:errors path="gLang" /></font></td>
		</tr>
		<tr height="40px">
			<td>title</td>
			<td><form:input path="title" cssClass="registerName" maxlength="50" /></td>
			<td><font color="red"><form:errors path="title" /></font></td>
		</tr>
		<tr height="40px">
			<td>contents</td>
			<td><form:input path="contents" cssClass="registerName" maxlength="50" /></td>
			<td><font color="red"><form:errors path="contents" /></font></td>
		</tr>
		<tr height="40px">
			<td>파일</td>
			<td><input type="file" name="uploadFile" /></td>
			<td></td>
		</tr>
	</table>
	<br>
	<input type="submit" value="등록" /> <input type="reset" value="리셋" /><br>
</form:form>
</body>
</html>