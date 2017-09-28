package kr.ko.nexmain.server.MissingU.common.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import kr.ko.nexmain.server.MissingU.common.Constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.Sender;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UTL {
	protected static Logger log = LoggerFactory.getLogger(UTL.class);
	
	public static Integer[] explodeInteger(String data, String separator) {
		String[] splits = data.split(separator);
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for(String split : splits) {
			if(split != null && split.length() > 0)
				arr.add(Integer.parseInt(split));
		}
		
		return arr.toArray(new Integer[] { 0 });
	}
	
	public static ModelAndView getModelAndViewForJsonResponse(Map<String,Object> inputMap) {
		// JSON 스트링 생성
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		String jsonString = gson.toJson(inputMap);
		
		// 모델 생성
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("jsonString", jsonString);

		// 반환값이 되는 ModelAndView 인스턴스를 생성
		ModelAndView modelAndView = new ModelAndView("common/CommonJsonReturnPage");
		modelAndView.addAllObjects(model);
		if(inputMap != null && inputMap.containsKey("result")) {
			log.info("[RESULT : {}]", gson.toJson(inputMap.get("result")));
		}
		
		return modelAndView;
	}
	
    /***************************************************************************
     * 현재 날짜를 가져오는 함수
     *
     * @return               년월일 8자리 String ('20050601')
     ***************************************************************************/
    static public String getCurrentDate() {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        return dateFormat.format(date);
    }
    
    /***************************************************************************
     * 현재 날짜시간을 가져오는 함수
     *
     * @return               년월일시분초 14자리 String ('20050601093030')
     ***************************************************************************/
    static public String getCurrentDatetime() {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        return dateFormat.format(date);
    }
    
    /***************************************************************************
     * Gap 만큼 지난(이전) 날짜 구하기. (Gap 일단위)
     * 
     * @param   baseDay		8자리 년월일 String ('20050601')
     * @param   unitType	계산할 단위 타입(Calendar.DATE...)
     * @param   numToCal	(-) 이전, (+) 이후 떨어진 날짜 
     * @return               계산된 날자 
     **************************************************************************/
    public static String dateToGap(String baseDay, int fieldType, int numToCal){
        if (baseDay==null || baseDay.equals("")) return "";
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        
        String result = null;

        try {
            Calendar cal = Calendar.getInstance();
            Date dt = dateFormat.parse(baseDay);
            
            cal.setTime(dt);
            cal.add(fieldType, numToCal);

            dt = cal.getTime();
            result = dateFormat.format(cal.getTime());
        } catch (Exception e) {
            result = baseDay;
        }

        return result;
    }
    
    /***************************************************************************
     * 입력 Str 이 null인 경우 ""로 변환
     * @param str
     * @return
     ***************************************************************************/
    static public String nvl(String str) {
    	if(str == null) {
    		str = "";
    	}
    	return str;
    }
    
    /***************************************************************************
     * Null or Blank 여부 체크(String)
     * @param input
     * @return
     ***************************************************************************/
    static public boolean isNullOrBlank(String input) {
    	if(input != null && "".equals(input)) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    /***************************************************************************
     * Null or Blank 여부 체크(Integer)
     * @param input
     * @return
     ***************************************************************************/
    static public boolean isNullOrBlank(Integer input) {
    	if(input != null && input > 0) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    static public void sendGCM(String registrationId, Map<String,String> params) {
		log.info("{}","==============================================");
		log.info("[{} START]", "sendGCM()");
		log.info("registrationID : {}", registrationId);
		log.info("{}","----------------------------------------------");
		
    	Sender sender = new Sender(Constants.GCM.API_SERVER_KEY);
    	
    	Builder builder = new Builder();
    	builder.collapseKey("collapseKey"+System.currentTimeMillis());
    	builder.timeToLive(3);
    	builder.delayWhileIdle(true);
    	
    	for(Map.Entry<String, String> entry : params.entrySet()) {
    		builder.addData(entry.getKey(), entry.getValue());
    	}
        Message message = builder.build();
        
        com.google.android.gcm.server.Result result;
		
		try {
			result = sender.send(message, registrationId, 3);
			
			if (result.getMessageId() != null) {
				String canonicalRegId = result.getCanonicalRegistrationId();
				log.info("canonicalRegId : {}", canonicalRegId);
				
				if (canonicalRegId != null) {
					// same device has more than on registration ID: update database
					log.info("same device has more than on registration ID: update database");
				}
			} else {
				String error = result.getErrorCodeName();
				log.info("[ERROR] : {}", error);
				if (error.equals(com.google.android.gcm.server.Constants.ERROR_NOT_REGISTERED)) {
					// application has been removed from device - unregister
					// database
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "sendGCM()");
		log.info("{}","==============================================\n");
    }
    
	/**
	 * 숫자에 대해 LPAD 기능
	 * @param num
	 * @param size
	 * @return
	 */
	public static String lpad(int num, int size) {
		String f = "%0"+size+"d";
		return String.format(f, num);  
	}  


	public static int convertToInt(Object obj, int defaultValue) {
		try {
			return Integer.parseInt(obj + "");
		}catch(Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 인자로 받은 문자열을 Date 형식으로 변환
	 * @param date  형식의 날짜값
	 * @param formatString "yyyy-MM-dd hh:mm:ss" 형식의 문자열
	 * @return 변환된 Date 객체
	 */
	public static Date convertToDate(String date, String formatString) 
	{		
		Date cDate = null;
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatString);
			cDate = format.parse(date);
			return cDate;
		} catch (Exception e) {
			return new Date();
		}
	}
	
	/**
	 * 인자로 받은 문자열을 Date 형식으로 변환
	 * @param date "yyyy-MM-dd hh:mm:ss" 형식의 날짜값
	 * @return 변환된 Date 객체
	 */
	public static Date convertToDate(String date) {
		return convertToDate(date, "yyyy-MM-dd HH:mm");
	}
	
	/**
	 * 지난 시간 계산하기
	 * @param diff
	 * @return
	 */
	public static int leftHour(Calendar a, Calendar b) {
		return leftHour(a.getTimeInMillis() - b.getTimeInMillis());
	}
	
	/**
	 * 지난 시간 계산하기
	 * @param diff
	 * @return
	 */
	public static int leftHour(long diff) {
		
		if(diff == 0)
			return 0;
		
		int ONE_DAY = 1000 * 60 * 60 * 24;
		int ONE_HOUR = ONE_DAY / 24;
//		int ONE_MINUTE = ONE_HOUR / 60;
//		int ONE_SECOND = ONE_MINUTE / 60;
		
		diff %= ONE_DAY;

        return (int)(diff / ONE_HOUR);
	}

}
