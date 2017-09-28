package kr.ko.nexmain.server.MissingU.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import kr.ko.nexmain.server.MissingU.common.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

public class NetworkUtil {
	
	protected static Logger log = LoggerFactory.getLogger(NetworkUtil.class);
	
	public static final int BUFFER_SIZE = 4096;
	
	public static class HttpResult {
		boolean result;
		String resultMsg;
		int httpStatusCode;
		String content;
		
		public boolean isResult() {
			return result;
		}
		public void setResult(boolean result) {
			this.result = result;
		}
		
		public String getResultMsg() {
			return resultMsg;
		}
		public void setResultMsg(String resultMsg) {
			this.resultMsg = resultMsg;
		}
		public int getHttpStatusCode() {
			return httpStatusCode;
		}
		public void setHttpStatusCode(int httpStatusCode) {
			this.httpStatusCode = httpStatusCode;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
	}

	public static HttpResult requestHttp(HttpMethod method, String url, String content) {
		
		HttpResult result = new HttpResult();
		DefaultHttpClient client = new DefaultHttpClient();
		String DEV_SERVER_URL = Constants.TSTORE.DEBUG_MODE ? Constants.TSTORE.RECEIPTS_VARIFICATION_URL_DEBUG  : Constants.TSTORE.RECEIPTS_VARIFICATION_URL;
		HttpPost httpPostRequest = new HttpPost(DEV_SERVER_URL);
		
		StringEntity se;
		try {
			se = new StringEntity(content, "UTF-8");
		    httpPostRequest.setEntity(se);
		    httpPostRequest.setHeader("Content-type", "application/json");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result.setResult(false);
			result.setHttpStatusCode(-1);
			result.setResultMsg("인코딩 오류 입니다.");
			return result;
		}
		
		try {
			HttpResponse resp = client.execute(httpPostRequest);
			result.setHttpStatusCode(resp.getStatusLine().getStatusCode());
			
			if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
			{
				HttpEntity respEntity = resp.getEntity();
				if (respEntity != null)
				{
					try
					{
						InputStream ins = respEntity.getContent();
						
						ByteArrayOutputStream bs = new ByteArrayOutputStream();
			            int i = ins.read();
			            while (i != -1) {
			                bs.write(i);
			                i = ins.read();
			            }
			            
			            
			            result.setResult(true);
						result.setResultMsg("");
						result.setContent(bs.toString());
						
					} catch (IllegalStateException e) {
						
						e.printStackTrace();
						result.setResult(false);
						result.setResultMsg("서버 오류 입니다. " + e.getMessage());
						
					} catch (IOException e) {
						e.printStackTrace();
						
						result.setResult(false);
						result.setResultMsg("서버 오류 입니다. " + e.getMessage());
					}
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			
			result.setResult(false);
			result.setResultMsg("서버 오류 입니다. " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			
			result.setResult(false);
			result.setResultMsg("서버 오류 입니다. " + e.getMessage());
		}
		
		return result;
	}
}
