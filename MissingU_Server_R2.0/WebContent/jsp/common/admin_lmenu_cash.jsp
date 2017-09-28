<%@ page language="java" contentType="text/html; charSet=UTF-8" pageEncoding="UTF-8" %>
<%
	String selected = request.getParameter("selected");
	
	String menu1 = "";
	String menu2 = "";
	String menu3 = "";
	String menu4 = "";
	String menu5 = "";
	
	if("1".equals(selected)) {
		menu1 = " class='select'";
	}else if("2".equals(selected)) {
		menu2 = " class='select'";
	}else if("3".equals(selected)) {
		menu3 = " class='select'";
	}else if("4".equals(selected)) {
		menu4 = " class='select'";
	}else if("5".equals(selected)) {
		menu5 = " class='select'";
	}
%>
				<li<%=menu1 %>><a href="/missingu/admin/payHistList.html">결제내역 조회</a></li>
				<li<%=menu2 %>><a href="/missingu/admin/pointUseHistList.html">포인트 이용내역 조회</a></li>
				<li<%=menu3 %>><a href="/missingu/admin/cash/winkList.html">윙크 조회</a></li>
				<li<%=menu4 %>><a href="/missingu/admin/cash/giftList.html">선물 조회</a></li>
				<li<%=menu5 %>><a href="/missingu/admin/cash/pointList.html">포인트 조회</a></li>