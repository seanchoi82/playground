<%@ page language="java" contentType="text/html;charset=euc-kr" %>

<%
	NiceID.Check.CPClient niceCheck = new  NiceID.Check.CPClient();

    String sEncodeData = request.getParameter("EncodeData");
    String sPlainData = "";
    String sSiteCode = "";						// 나신평정보로부터 부여받은 사이트 코드
    String sSitePassword = "";						// 나신평정보로부터 부여받은 사이트 패스워드
    String sCipherTime = "";						// 복호화한 시간
    String sRequestNumber = "";						// 요청 번호
    String sResponseNumber = "";					// 인증 고유번호
    String sAuthType = "";						// 인증 수단
    String sMessage = "";
    
    int iReturn = niceCheck.fnDecode(sSiteCode, sSitePassword, sEncodeData);

    if( iReturn == 0 )
    {
        sPlainData = niceCheck.getPlainData();
        sCipherTime = niceCheck.getCipherDateTime();
        
        // 데이타를 추출합니다.
        java.util.HashMap mapresult = niceCheck.fnParse(sPlainData);
        
        sRequestNumber = (String)mapresult.get("REQ_SEQ");
        sResponseNumber = (String)mapresult.get("RES_SEQ");
        sAuthType = (String)mapresult.get("AUTH_TYPE");
        
        String session_sRequestNumber = (String)session.getAttribute("REQ_SEQ");
        if(!sRequestNumber.equals(session_sRequestNumber))
        {
            sMessage = "세션값이 다릅니다. 올바른 경로로 접근하시기 바랍니다.";
            sResponseNumber = "";
            sAuthType = "";
        }
    }
    else if( iReturn == -1)
    {
        sMessage = "복호화 시스템 에러입니다.";
    }    
    else if( iReturn == -4)
    {
        sMessage = "복호화 처리오류입니다.";
    }    
    else if( iReturn == -5)
    {
        sMessage = "복호화 해쉬 오류입니다.";
    }    
    else if( iReturn == -6)
    {
        sMessage = "복호화 데이터 오류입니다.";
    }    
    else if( iReturn == -9)
    {
        sMessage = "입력 데이터 오류입니다.";
    }    
    else if( iReturn == -12)
    {
        sMessage = "사이트 패스워드 오류입니다.";
    }    
    else
    {
        sMessage = "알수 없는 에러 입니다. iReturn : " + iReturn;
    }

%>

<html>
<head>
    <title>NICE신용평가정보 - CheckPlus 본인인증 테스트</title>
</head>
<body>
    <center>
    <p><p><p><p>
    본인인증이 완료 되었습니다.<br>
    <table width=500 border=1>
        <tr>
            <td>복호화한 시간</td>
            <td><%= sCipherTime %> (YYMMDDHHMMSS)</td>
        </tr>
        <tr>
            <td>요청 번호</td>
            <td><%= sRequestNumber %></td>
        </tr>            
        <tr>
            <td>나신평응답 번호</td>
            <td><%= sResponseNumber %></td>
        </tr>            
        <tr>
            <td>인증수단</td>
            <td><%= sAuthType %></td>
        </tr>
    </table><br><br>        
    <%= sMessage %><br>
    </center>
</body>
</html>