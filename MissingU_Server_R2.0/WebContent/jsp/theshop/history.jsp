<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/header.jsp"%>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma" content="no-cache"/>
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<style type="text/css">
	body { background: url(/img/bg_gray_nopad.png); }
</style>
<title><spring:message code="theshop.history.title.buyHistory" /></title>
</head>
<body>
<div class="page">

	<div class="ui-box ui-box-white ui-box-shadow">
		<h2 class="icon-arrow-green"><spring:message code="theshop.history.title.buyHistory" /></h2>
		
		<div class="ui-box ui-box-lightgray">
			<ul class="product-info">
				<li>
					<span class="blot">▶</span>
					<span class="name"> <spring:message code="theshop.itemSelection.ticket.point" /> </span>
					<span class="split">|</span>
					<span id="memberPoint">
					${ response.memberPoint }
					</span>
				</li>
			</ul>
		</div>
		<div class="pt30">
			<ul class="tabhost">
				<li class="select"><spring:message code="theshop.history.title.payHistory" /></li>
			</ul>
			<table class="tbl_nor" cellspacing="0" summary="결제정보">
				<caption><spring:message code="theshop.history.payInfo" /></caption>
				<colgroup>
					<col width="0" hidden="true">
					<col width="140">
					<col width="*">
					<col width="150">
				</colgroup>
				<tr id="tb_header">
					<th hidden="true"><spring:message code="theshop.history.table.header.no" /></th>
					<th><spring:message code="theshop.history.table.header.date" /></th>
					<th><spring:message code="theshop.history.table.header.desc" /></th>
					<th><spring:message code="theshop.history.table.header.amount" /></th>
				</tr>
				<c:forEach items="${ response.payHistList }" var="item">
					<tr class="desc">
						<td hidden=true>${ item.payId }</td>
						<td>${ item.updatedDate }</td>
						<td>${ item.prodName }</td>
						<td>${ item.amount }<spring:message code='theshop.comm.currency' /></td>
					</tr>
				</c:forEach>
				<c:if test='${ fn:length(response.payHistList) == 0 }'>
					<tr class="desc">
						<td colspan="4"><spring:message code="theshop.history.table.noResult" /></td>
					</tr>
				</c:if>
			</table>
		</div>
		
		<div class="ui-btns">
			<jsp:include page="/jsp/common/paging.jsp">
			    <jsp:param name="actionPath" value="history.html"/>
			    <jsp:param name="totalCount" value="${ response.totalCnt }"/>
			    <jsp:param name="countPerPage" value="${ reqParams.pageSize }"/>
			    <jsp:param name="blockCount" value="3"/>
			    <jsp:param name="nowPage" value="${ reqParams.nowPage }"/>
			    <jsp:param name="addQuerys" value="gMemberId=${ reqParams.gMemberId }&amp;gLang=${ reqParams.gLang }&amp;gCountry=${ reqParams.gCountry }"/>
			</jsp:include>
		</div>
	</div>
</div>
</body>
</html>