package kr.ko.nexmain.server.MissingU.c2dm;

import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

public class C2dmProvider extends Thread {
 
 private String deviceToken = null;
 private String alert = null;
 private int badge = 0;
 
 public C2dmProvider(String deviceToken, String alert) {
  this.deviceToken = deviceToken;
  this.alert = alert;
 }
 
 public void run() {
  try {
   
   sender(getAuthToken());
   
  }catch (Exception e) {
   e.printStackTrace();
  }
 }
 
 
 public void sender(String authToken) throws Exception{
  
  String rdata = URLEncoder.encode("registration_id", "UTF-8") + "=" + URLEncoder.encode(this.deviceToken, "UTF-8"); 
  rdata += "&" + URLEncoder.encode("collapse_key", "UTF-8") + "=" + URLEncoder.encode(this.alert, "UTF-8"); 
  rdata += "&" + URLEncoder.encode("delay_while_idle", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"); 
  rdata += "&" + URLEncoder.encode("data.message", "UTF-8") + "=" + URLEncoder.encode(this.alert, "UTF-8"); 
  rdata += "&" + URLEncoder.encode("data.badge", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(this.badge), "UTF-8"); 
  
  URL url = new URL("https://android.clients.google.com/c2dm/send");
  
  HttpURLConnection conn = (HttpURLConnection)url.openConnection();
  conn.setDoOutput(true);
  conn.setUseCaches(false);
  conn.setRequestMethod("POST");
  conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
  conn.setRequestProperty("Content-Length",Integer.toString(rdata.length() ));
  conn.setRequestProperty("Authorization", "GoogleLogin auth=" + authToken);
  OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
  
  wr.write(rdata);
  wr.flush();
  wr.close();
  
  int responseCode = conn.getResponseCode();
  
  if(responseCode == HttpServletResponse.SC_UNAUTHORIZED || responseCode == HttpServletResponse.SC_FORBIDDEN){
   System.out.println("AndroidApp : Unauthorized - need token");
  }
  
  String updatedAuthToken = conn.getHeaderField("Update-Client-Auth");
  if(updatedAuthToken != null && !authToken.equals(updatedAuthToken)){
   System.out.println("AndroidApp : Got updated auth token from datamessaging servers : " +  updatedAuthToken);
  }
  
  String reponseLine = new BufferedReader(new InputStreamReader(conn.getInputStream())).readLine();
  
  if(reponseLine == null || reponseLine.equals("")){
   System.out.println("AndroidApp : Got " +  responseCode + " response from Google AC2DM endpoint.");
   throw new IOException("Got empty response from Google AC2DM endpoint");
  }
  
  String[] responseParts = reponseLine.split("=",2);
  
  if(responseParts.length != 2){
   System.out.println("AndroidApp : Invalid message from google " +  responseCode + " " + reponseLine);
   throw new IOException("Invalid message from google " +  responseCode + " " + reponseLine);
  }
  if(responseParts[0].equals("id")){
   System.out.println("AndroidApp : Successfully sent data message to device : " + reponseLine);
  }
  
  if(responseParts[0].equals("Error")){
   String err = responseParts[1];
   System.out.println("AndroidApp : Got error response from Google datamessaging endpoint : " + err);
   throw new IOException("Server error : " + err);
  }else{
   System.out.println("AndroidApp : Invalid response from google : " + reponseLine + " " + responseCode);
  }
 }
 
 public String getAuthToken() throws Exception{
  
  // Prepare data for being posted
  String rdata = URLEncoder.encode("accountType", "UTF-8") + "=" + URLEncoder.encode("HOSTED_OR_GOOGLE", "UTF-8"); 
  rdata += "&" + URLEncoder.encode("Email", "UTF-8") + "=" + URLEncoder.encode("hyuni1982@gmail.com", "UTF-8"); 
  rdata += "&" + URLEncoder.encode("Passwd", "UTF-8") + "=" + URLEncoder.encode("cshlan14", "UTF-8"); 
  rdata += "&" + URLEncoder.encode("service", "UTF-8") + "=" + URLEncoder.encode("ac2dm", "UTF-8"); 
  
  // Send data
  URL url = new URL("https://www.google.com/accounts/ClientLogin"); 
  URLConnection conn = url.openConnection(); 
  conn.setDoOutput(true); 
  
  // Write post 
  OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
  wr.write(rdata); 
  wr.flush();
  
  // Get the response 
  BufferedReader rd; 
  String line; 
  StringBuffer resp = new StringBuffer(); 
  try { 
      rd = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
      while ((line = rd.readLine()) != null) { 
          resp.append(line); 
      } 
      wr.close(); 
      rd.close(); 
  } catch (FileNotFoundException e1) { 
      // Catch bad url 
      System.out.println("AndroidApp : Error: Bad url address!");
  } catch (IOException e1) { 
      // Catch 403 (usually bad username or password 
      if(e1.toString().contains("HTTP response code: 403")) 
       System.out.println("AndroidApp : Error: Forbidden response! Check username/password or service name.");
  } 
  
  String token = resp.toString().substring(resp.toString().indexOf("Auth=")+5); 
  return token;
 }
}

