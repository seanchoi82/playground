<%@ page language="java" contentType="text/html;charset=euc-kr" %>

<%
NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();
    
    String sSiteCode = "";				// �����������κ��� �ο����� ����Ʈ �ڵ�
    String sSitePassword = "";				// �����������κ��� �ο����� ����Ʈ �н�����
    
    String sRequestNumber = "REQ0000000001";        	// ��û ��ȣ, �̴� ����/�����Ŀ� ���� ������ �ǵ����ְ� �ǹǷ� 
                                                    	// ��ü���� �����ϰ� �����Ͽ� ���ų�, �Ʒ��� ���� �����Ѵ�.
    sRequestNumber = niceCheck.getRequestNO(sSiteCode);
  	session.setAttribute("REQ_SEQ" , sRequestNumber);	// ��ŷ���� ������ ���Ͽ� ������ ���ٸ�, ���ǿ� ��û��ȣ�� �ִ´�.
  	
  	String sAuthType = "";      	// ������ �⺻ ����ȭ��, X: ����������, M: �ڵ���, C: �ſ�ī��
  	String sJuminid = "";		// ����� �ֹε�Ϲ�ȣ
   
    // CheckPlus(��������) ó�� ��, ��� ����Ÿ�� ���� �ޱ����� ���������� ���� http���� �Է��մϴ�.
    String sReturnUrl = "http://www.test.co.kr/checkplus_success.jsp";      // ������ �̵��� URL
    String sErrorUrl = "http://www.test.co.kr/checkplus_fail.jsp";          // ���н� �̵��� URL

    // �Էµ� plain ����Ÿ�� �����.
    String sPlainData = "7:JUMINID" + sJuminid.getBytes().length + ":" + sJuminid +
                        "7:REQ_SEQ" + sRequestNumber.getBytes().length + ":" + sRequestNumber +
                        "8:SITECODE" + sSiteCode.getBytes().length + ":" + sSiteCode +
                        "9:AUTH_TYPE" + sAuthType.getBytes().length + ":" + sAuthType +
                        "7:RTN_URL" + sReturnUrl.getBytes().length + ":" + sReturnUrl +
                        "7:ERR_URL" + sErrorUrl.getBytes().length + ":" + sErrorUrl;
    
    String sMessage = "";
    String sEncData = "";
    
    int iReturn = niceCheck.fnEncode(sSiteCode, sSitePassword, sPlainData);
    if( iReturn == 0 )
    {
        sEncData = niceCheck.getCipherData();
    }
    else if( iReturn == -1)
    {
        sMessage = "��ȣȭ �ý��� �����Դϴ�.";
    }    
    else if( iReturn == -2)
    {
        sMessage = "��ȣȭ ó�������Դϴ�.";
    }    
    else if( iReturn == -3)
    {
        sMessage = "��ȣȭ ������ �����Դϴ�.";
    }    
    else if( iReturn == -9)
    {
        sMessage = "�Է� ������ �����Դϴ�.";
    }    
    else
    {
        sMessage = "�˼� ���� ���� �Դϴ�. iReturn : " + iReturn;
    }
%>

<html>
<head>
    <title>NICE�ſ������� - CheckPlus �������� �׽�Ʈ</title>
</head>
    
<script language="javascript">
    window.open("https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb?m=checkplusSerivce&EncodeData=<%=sEncData%>");
</script>

<body>
    <center>
    <p><p><p><p>
    �������� �׽�Ʈ�� �����մϴ�.<br>
    <%= sMessage %><br>
    </center>
</body>
</html>