package kr.ko.nexmain.server.MissingU.common.controller;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.web.servlet.ModelAndView;


public class BaseController {
	/**
	 * 페이징 변수 만들기
	 * @param params
	 */
	protected void makePaging(Map<String, Object> params) {
		
		if(!params.containsKey("nowPage"))
			params.put("nowPage", 1);
		if(!params.containsKey("pageSize"))
			params.put("pageSize", 20);
		
		int page = 1;
		int pageSize = 20;
		try {
			page = Integer.parseInt(params.get("nowPage") + "");
			pageSize = Integer.parseInt(params.get("pageSize") + "");
		}catch(Exception e) {
			page = 1;
		}
		params.remove("nowPage");
		params.remove("pageSize");
		
		params.put("startRow", (page-1)*pageSize);
		params.put("nowPage", page);
		params.put("pageSize", pageSize);
		
	}

	/** constants interface에 있는 코드를 모델에 추가해서 화면에서 사용하는 경우에만 등록 */
	protected void appendCommonCode(ModelAndView model, Class<?> clazz) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		for(Field field : clazz.getFields()) {
			field.setAccessible(true);
			try {
				map.put(field.getName(), field.get(clazz));
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
		}
		model.addObject(clazz.getSimpleName(), map);
	}
	
	protected void startLog(Logger log, Map<String,Object> params, HttpServletRequest request) {
		
		log.info("{}","==============================================");
		StackTraceElement[] ste = new Throwable().getStackTrace();
		if(ste != null && ste.length > 1) {
			log.info("[{} START]", ste[1].getClassName() + "["+ ste[1].getMethodName() +"]" + "("+ ste[1].getLineNumber()+ ")");
		}
		
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Param : {}", params.toString());
		log.info("{}","----------------------------------------------");		
	}
	
	protected void endLog(Logger log) {
		log.info("{}","----------------------------------------------");
		StackTraceElement[] ste = new Throwable().getStackTrace();
		if(ste != null && ste.length > 1) {
			log.info("[{} END]", ste[1].getClassName() + "["+ ste[1].getMethodName() +"]" + "("+ ste[1].getLineNumber()+ ")");
		}
		log.info("{}","==============================================\n");
	}
}
