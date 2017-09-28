package kr.ko.nexmain.server.MissingU.gcm.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.ko.nexmain.server.MissingU.common.Constants;
import kr.ko.nexmain.server.MissingU.common.utils.MsgUtil;
import kr.ko.nexmain.server.MissingU.common.utils.UTL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

@Controller
@RequestMapping("/missingu/gcm")
public class GCMController {
	
	protected static Logger log = LoggerFactory.getLogger(GCMController.class);

	@Autowired
	private MsgUtil msgUtil;
	
	/**********************************************
	(GCM) GCM 테스트
	***********************************************/
	@RequestMapping(value="/gcmTest.html")
	public ModelAndView gcmTest(@RequestParam String msg, @RequestParam String gcmRegId, HttpServletRequest request) {
		log.info("{}","==============================================");
		log.info("[{} START]", "gcmTest()");
		log.info("Request URI : {}", request.getRequestURI());
		log.info("Input Msg : {}", msg);
		log.info("{}","----------------------------------------------");
		
		//String testRegId = "APA91bGh1Irr5sMs3HkzLmz6jRyYFhtQfvKZWDmBgHV55To1VIQS8OviDU-0kyUHL0x7NVeOWqAw1poE18_kEmMQ_jg9xvbJYhHWSVn2g9Pj18DgyOBYKjXrLJq6BD2PuO4sEm-DDOeGCbCUTumssCf4zViIQlOwpQ";
		Sender sender = new Sender(Constants.GCM.API_SERVER_KEY);
        Message message = new Message.Builder()
		        .collapseKey("collapseKey"+System.currentTimeMillis())
		        .timeToLive(3)
		        .delayWhileIdle(true)
		        .addData("message", "이곳에 전달할 메시지를 작성하면 됨.")
		        .build();
        
		com.google.android.gcm.server.Result result;
		
		try {
			result = sender.send(message, gcmRegId, 5);

			System.out.println("======= Send ======");

			if (result.getMessageId() != null) {
				String canonicalRegId = result.getCanonicalRegistrationId();
				System.out.println("canonicalRegId : " + canonicalRegId);
				
				if (canonicalRegId != null) {
					// same device has more than on registration ID: update database
					System.out.println("same device has more than on registration ID: update database");
				}
			} else {
				String error = result.getErrorCodeName();
				System.out.println("[ERROR]"+error);
				if (error.equals(com.google.android.gcm.server.Constants.ERROR_NOT_REGISTERED)) {
					// application has been removed from device - unregister
					// database
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.info("[ERROR : {}]", e.getMessage());
		}


		Map<String, Object> model = new HashMap<String, Object>();
		model.put("jsonString", msg);

		// 반환값이 되는 ModelAndView 인스턴스를 생성
		ModelAndView modelAndView = new ModelAndView("common/CommonJsonReturnPage");
		modelAndView.addAllObjects(model);

		
		log.info("{}","----------------------------------------------");
		log.info("[{} END]", "gcmTest()");
		log.info("{}","==============================================");
		return modelAndView;
	}
	
	
}