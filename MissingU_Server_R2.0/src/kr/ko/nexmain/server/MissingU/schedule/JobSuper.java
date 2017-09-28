package kr.ko.nexmain.server.MissingU.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public abstract class JobSuper extends QuartzJobBean {
    private ApplicationContext ctx;
 
    @Override
    protected void executeInternal(JobExecutionContext jobexecutioncontext) throws JobExecutionException {
        // TODO Auto-generated method stub
  
        ctx = (ApplicationContext)jobexecutioncontext.getJobDetail().getJobDataMap().get("applicationContext");
  
        executeJob(jobexecutioncontext);
    }
 
    protected Object getBean(String beanId) {
        return ctx.getBean(beanId);
    }
 
    protected abstract void executeJob(JobExecutionContext jobexecutioncontext);
}