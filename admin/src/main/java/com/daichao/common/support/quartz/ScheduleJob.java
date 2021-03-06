package com.daichao.common.support.quartz;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.daichao.modules.sys.dao.QuartzJobLogMapper;
import com.daichao.modules.sys.entity.QuartzJobEntity;
import com.daichao.modules.sys.entity.QuartzJobLogEntity;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.daichao.common.utils.SpringContextUtils;


/**
 * 定时任务
 * @author zcl<yczclcn@163.com>
 */
public class ScheduleJob extends QuartzJobBean {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private ExecutorService service = Executors.newSingleThreadExecutor(); 
	
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        QuartzJobEntity scheduleJob = (QuartzJobEntity) context.getMergedJobDataMap()
        		.get(QuartzJobEntity.JOB_PARAM_KEY);

		//获取spring bean
		QuartzJobLogMapper quartzJobLogMapper = (QuartzJobLogMapper) SpringContextUtils.getBean("quartzJobLogMapper");
        
        //数据库保存执行记录
        QuartzJobLogEntity log = new QuartzJobLogEntity();
        log.setJobId(scheduleJob.getJobId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethodName(scheduleJob.getMethodName());
        log.setParams(scheduleJob.getParams());
        
        //任务开始时间
        long startTime = System.currentTimeMillis();
        
        try {
            //执行任务
        	logger.info("任务准备执行，任务ID：" + scheduleJob.getJobId());
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(), 
            		scheduleJob.getMethodName(), scheduleJob.getParams());
            Future<?> future = service.submit(task);
            
			future.get();
			
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int)times);
			//任务状态    0：失败    1：成功
			log.setStatus(1);
			
			logger.info("任务执行完毕，任务ID：" + scheduleJob.getJobId() + "  总共耗时：" + times + "毫秒");
			
		} catch (Exception e) {
			
			logger.error("任务执行失败，任务ID：" + scheduleJob.getJobId(), e);
			
			//任务执行总时长
			long times = System.currentTimeMillis() - startTime;
			log.setTimes((int)times);
			
			//任务状态    0：失败    1：成功
			log.setStatus(0);
			log.setError(StringUtils.substring(e.toString(), 0, 2000));
			
		}finally {
			quartzJobLogMapper.save(log);
		}
    }
}
