<%@ page language="java" contentType="text/html;charset=euc-kr" %>

<%
	NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

    String sEncodeData = request.getParameter("EncodeData");
    String sPlainData = "";
    String sSiteCode = "";						// �����������κ��� �ο����� ����Ʈ �ڵ�
    String sSitePassword = "";						// �����������κ��� �ο����� ����Ʈ �н�����
    String sCipherTime = "";						// ��ȣȭ�� �ð�
    String sRequestNumber = "";						// ��û ��ȣ
    String sResponseNumber = "";					// ���� ������ȣ
    String sAuthType = "";						// ���� ����
    String sMessage = "";
    
    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

    if( iReturn == 0 )
    {
        sPlainData = niceCheck.getPlainData();
        sCipherTime = niceCheck.getCipherDateTime();
        
        // ����Ÿ�� �����մϴ�.
        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
        
        sRequestNumber = (String)mapresult.get("REQ_SEQ");
        sResponseNumber = (String)mapresult.get("RES_SEQ");
        sAuthType = (String)mapresult.get("AUTH_TYPE");
        
        String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
        if(!sRequestNumber.equals(session_sRequestNumber))
        {
            sMessage = "���ǰ��� �ٸ��ϴ�. �ùٸ� ��η� �����Ͻñ� �ٶ��ϴ�.";
            sResponseNumber = "";
            sAuthType = "";
        }
    }
    else if( iReturn == -1)
    {
        sMessage = "��ȣȭ �ý��� �����Դϴ�.";
    }    
    else if( iReturn == -4)
    {
        sMessage = "��ȣȭ ó�������Դϴ�.";
    }    
    else if( iReturn == -5)
    {
        sMessage = "��ȣȭ �ؽ� �����Դϴ�.";
    }    
    else if( iReturn == -6)
    {
        sMessage = "��ȣȭ ������ �����Դϴ�.";
    }    
    else if( iReturn == -9)
    {
        sMessage = "�Է� ������ �����Դϴ�.";
    }    
    else if( iReturn == -12)
    {
        sMessage = "����Ʈ �н����� �����Դϴ�.";
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
<body>
    <center>
    <p><p><p><p>
    ���������� �Ϸ� �Ǿ����ϴ�.<br>
    <table width=500 border=1>
        <tr>
            <td>��ȣȭ�� �ð�</td>
            <td><%= sCipherTime %> (YYMMDDHHMMSS)</td>
        </tr>
        <tr>
            <td>��û ��ȣ</td>
            <td><%= sRequestNumber %></td>
        </tr>            
        <tr>
            <td>���������� ��ȣ</td>
            <td><%= sResponseNumber %></td>
        </tr>            
        <tr>
            <td>��������</td>
            <td><%= sAuthType %></td>
        </tr>
    </table><br><br>        
    <%= sMessage %><br>
    </center>
</body>
</html>