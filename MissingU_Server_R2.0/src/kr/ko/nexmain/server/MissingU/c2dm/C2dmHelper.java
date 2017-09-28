package kr.ko.nexmain.server.MissingU.c2dm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.ko.nexmain.server.MissingU.chat.service.ChatMainServiceImpl;
import kr.ko.nexmain.server.MissingU.common.Constants;

public class C2dmHelper {
	
	private Logger log = LoggerFactory.getLogger(C2dmHelper.class);

    public C2dmHelper() {
    }
    
    public boolean sendMsg(String regId,String authToken,String msg, List<List<String>> c2dmParamList) throws Exception{
    		//authToken = getAuthToken();
            StringBuffer postDataBuilder = new StringBuffer();
            String collapseKey = String.valueOf(Math.random() % 100 + 1);
            
            postDataBuilder.append("registration_id="+regId); //등록ID
            postDataBuilder.append("&collapse_key="+collapseKey);
            postDataBuilder.append("&delay_while_idle=1");
            postDataBuilder.append("&data.no=1");
            postDataBuilder.append("&data.answerer=MissingU");
            postDataBuilder.append("&data.msg="+URLEncoder.encode(msg, "UTF-8")); //태울 메시지
            
            for(List<String> c2dmParamItem : c2dmParamList) {
            	postDataBuilder.append("&data.");
            	postDataBuilder.append(c2dmParamItem.get(0)).append("=");	//key
            	postDataBuilder.append(c2dmParamItem.get(1));				//value
            }
            System.out.println(postDataBuilder.toString());
    
            byte[] postData = postDataBuilder.toString().getBytes("UTF8");
    
            URL url = new URL("https://android.apis.google.com/c2dm/send"); //Link 둘다 됨
            //URL url = new URL("https://android.clients.google.com/c2dm/send");
            
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setHostnameVerifier(new CustomizedHostnameVerifier());
            //HttpsURLConnection.setDefaultHostnameVerifier(new CustomizedHostnameVerifier());
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
            conn.setRequestProperty("Authorization", "GoogleLogin auth=" + authToken);
    
            OutputStream out = conn.getOutputStream();
            out.write(postData);
            out.close();
            
            int responseCode = conn.getResponseCode();
            
            if (responseCode == HttpServletResponse.SC_UNAUTHORIZED ||
                    responseCode == HttpServletResponse.SC_FORBIDDEN) {
                // The token is too old - return false to retry later, will fetch the token
                // from DB. This happens if the password is changed or token expires. Either admin
                // is updating the token, or Update-Client-Auth was received by another server,
                // and next retry will get the good one from database.
                System.out.println("Unauthorized - need token");
                //serverConfig.invalidateCachedToken();
                return false;
            }
    
            // Check for updated token header
            /*
            String updatedAuthToken = conn.getHeaderField(UPDATE_CLIENT_AUTH);
            if (updatedAuthToken != null && !authToken.equals(updatedAuthToken)) {
                System.out.println("Got updated auth token from datamessaging servers: " +
                        updatedAuthToken);
                //serverConfig.updateToken(updatedAuthToken);
            }
            */
    
            String responseLine = new BufferedReader(new InputStreamReader(conn.getInputStream())).readLine();
    
            // NOTE: You *MUST* use exponential backoff if you receive a 503 response code.
            // Since App Engine's task queue mechanism automatically does this for tasks that
            // return non-success error codes, this is not explicitly implemented here.
            // If we weren't using App Engine, we'd need to manually implement this.
            if (responseLine == null || responseLine.equals("")) {
                System.out.println("Got " + responseCode + " response from Google AC2DM endpoint.");
                //throw new IOException("Got empty response from Google AC2DM endpoint.");
            }
    
            String[] responseParts = responseLine.split("=", 2);
            if (responseParts.length != 2) {
                System.out.println("Invalid message from google: " + responseCode + " " + responseLine);
                //throw new IOException("Invalid response from Google " + responseCode + " " + responseLine);
            }
    
            if (responseParts[0].equals("id")) {
                System.out.println("Successfully sent data message to device: " + responseLine);
                return true;
            }
    
            if (responseParts[0].equals("Error")) {
                String err = responseParts[1];
                System.out.println("Got error response from Google datamessaging endpoint: " + err);
                // No retry.
                // TODO(costin): show a nicer error to the user.
                //throw new IOException(err);
            } else {
                // 500 or unparseable response - server error, needs to retry
                System.out.println("Invalid response from google " + responseLine + " " +  responseCode);
                return false;
            }
            
            return true;
    }
    
    
    /**
     * C2DM 메세지 발송을 위한 AuthToken 받아오는 메소드
     * @return
     * @throws Exception
     */
    public String getAuthToken() throws Exception{
        String authtoken = "";
        
        StringBuffer postDataBuilder = new StringBuffer();
        postDataBuilder.append("accountType=HOSTED_OR_GOOGLE"); //똑같이 써주셔야 합니다.
        postDataBuilder.append("&Email=");  					
        postDataBuilder.append(Constants.C2DM.C2DM_SENDER_ID);	//개발자 구글 id
        postDataBuilder.append("&Passwd=");           			
        postDataBuilder.append(Constants.C2DM.C2DM_SENDER_PW);	//개발자 구글 비빌번호
        postDataBuilder.append("&service=ac2dm"); 
        postDataBuilder.append("&source=test-1.0");


        byte[] postData = postDataBuilder.toString().getBytes("UTF8");


        URL url = new URL("https://www.google.com/accounts/ClientLogin");
        
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", Integer.toString(postData.length));


        
        OutputStream out = conn.getOutputStream();
        out.write(postData);
        out.close();


        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        
        String sidLine = br.readLine();
        String lsidLine = br.readLine();
        String authLine = br.readLine();
        
        log.debug("sidLine----------->>>"+sidLine);
        log.debug("lsidLine----------->>>"+lsidLine);
        log.debug("authLine----------->>>"+authLine);
        log.debug("AuthKey----------->>>"+authLine.substring(5, authLine.length()));
        
        authtoken = authLine.substring(5, authLine.length());
        
     return authtoken;
    }
    
}

