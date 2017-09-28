package kr.ko.nexmain.server.MissingU.membership.interceptor;

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
 * 포인트 체크
 * 포인트 부족시 포인트 부족 메세지 출력
 */
public class PointCheckInterceptor extends HandlerInterceptorAdapter {
	private static final Logger log = LoggerFactory.getLogger(PointCheckInterceptor.class);
	
	@Autowired
	private CommonService commonService;
	@Autowired
	private MembershipDao membershipDao;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("{}", "==============================================");
		log.info("{}", "[Interceptor]preHandle() START");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("{}", "----------------------------------------------");
		
		Map<String,Object> inputMap = new HashMap<String,Object>();
		
		boolean returnFlag = true;
		int		pointNeed = 0;
		Long	memberPoint = (long) 0;
		
		Integer	memberId = Integer.parseInt((String)request.getParameter("gMemberId"));
		String	gLang = (String)request.getParameter("gLang");
		
		Map<String,Object> member = membershipDao.selectMemberAndPointInfoByMemberId(memberId);
		
		if(member == null) {
			log.info("member is null");
		} else {
			inputMap.put("requestURI", request.getRequestURI());
			inputMap.put("memberId", memberId);
			inputMap.put("msgId", request.getParameter("msgId"));
			inputMap.put("roomId", request.getParameter("roomId"));
			
			String pointPass = request.getParameter("pointPass");
			// 관리자모드 기능일때 무시
			if("true".equals(pointPass)) {
				returnFlag = true;
			}else{
				Map<String,Object> returnMap = commonService.getMemberPointAndNeedPoint(inputMap, gLang);
				pointNeed	= (Integer)returnMap.get("pointNeed");
				memberPoint	= (Long)returnMap.get("memberPoint");
				log.info("pointNeed : {}", pointNeed);
				log.info("memberPoint : {}", memberPoint);
	
				//필요한 포인트가 부족한 경우 리턴
				if(pointNeed > 0) {
					if(memberPoint < pointNeed) {
						//포인트 부족
						returnFlag = false;
						response.sendRedirect(Constants.RequestURI.LACK_OF_POINT_ERROR);
					} 
					/*
					else {
						//포인트가 있는 경우, 고객에게 사용 컨펌을 한번 더 받는다.
						String gConfrim = request.getParameter("gConfirm");
						log.info("gConfrim : {}", gConfrim);
						if(StringUtils.isEmpty(gConfrim) || Constants.NO.equalsIgnoreCase(gConfrim)) {
							returnFlag = false;
							StringBuilder paramStr = new StringBuilder();
							paramStr.append("?gMemberId=");
							paramStr.append(memberId);
							paramStr.append("&requestURI=");
							paramStr.append(request.getRequestURI());
							response.sendRedirect(Constants.RequestURI.CONFIRM_TO_USE_POINT+paramStr.toString());
						}
					}
					*/
				}
			}
		}
		
		log.info("{}", "[Interceptor]preHandle() END");
		log.info("{}", "==============================================\n");

		return returnFlag;
    }
}