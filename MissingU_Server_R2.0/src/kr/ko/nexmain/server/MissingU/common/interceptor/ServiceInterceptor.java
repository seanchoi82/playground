package kr.ko.nexmain.server.MissingU.common.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.model.Result;
import kr.ko.nexmain.server.MissingU.common.service.CommonService;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;
import kr.ko.nexmain.server.MissingU.membership.dao.MembershipDao;
import kr.ko.nexmain.server.MissingU.membership.model.MemberAttr;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Interceptor : 서비스 이용 기록
 */
public class ServiceInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = LoggerFactory.getLogger(ServiceInterceptor.class);
	
	@Autowired
	private CommonService commonService;
	
    @Override
    public boolean preHandle(HttpServletRequest request,
	    HttpServletResponse response, Object handler) throws Exception {
		log.info("{}", "==============================================");
		log.info("{}", "[Interceptor : Service Request]preHandle() START");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("{}", "----------------------------------------------");
		
//		Integer	memberId = null;
//		try {
//			memberId = Integer.parseInt((String)request.getParameter("gMemberId"));
//		
//			StringBuilder sbRequestType = new StringBuilder();
//			sbRequestType.append(request.getProtocol()).append(" | ");
//			sbRequestType.append(request.getMethod()).append(" | ");
//			sbRequestType.append(request.getRemoteAddr());
//			
//			Map<String,Object> inputMap = new HashMap<String,Object>();
//			inputMap.put("gMemberId", memberId);
//			inputMap.put("requestUri", request.getRequestURI());
//			inputMap.put("requestType", sbRequestType.toString());
//			inputMap.put("serviceCd", "");
//			
//			commonService.saveServiceAccessLog(inputMap);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
		
		log.info("{}", "[Interceptor : Service Request]preHandle() END");
		log.info("{}", "==============================================\n");

		return true;
    }
}