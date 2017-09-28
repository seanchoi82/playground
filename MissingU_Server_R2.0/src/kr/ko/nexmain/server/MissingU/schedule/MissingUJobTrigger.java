package kr.ko.nexmain.server.MissingU.schedule;

import kr.ko.nexmain.server.MissingU.chat.service.ChatService;

import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MissingUJobTrigger extends JobSuper {
	
	protected static Logger log = LoggerFactory.getLogger(MissingUJobTrigger.class);


	@Override
	protected void executeJob(JobExecutionContext jobexecutioncontext) {
		
		ChatService chatService = (ChatService)super.getBean("chatServiceImpl"); //get service bean from spring applicationContext
		
		chatService.cleanOldChatroom();
	}
}
