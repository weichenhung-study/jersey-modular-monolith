package com.ntou;

import com.ntou.creditcard.billing.generatebill.GenerateBillTask;
import com.ntou.tool.Common;
import lombok.extern.log4j.Log4j2;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Log4j2
@ApplicationPath("/res")
public class APIApplication extends ResourceConfig {
    final String MONTHLY_JOB = "monthlyJob";
    final String MONTHLY_TRIGGER = "monthlyTrigger";

    private Scheduler scheduler;

    public APIApplication() {
        packages("com.ntou");
    }

    @PostConstruct
    public void init() {
        try {
            SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            scheduler = schedulerFactory.getScheduler();
            scheduler.start();

            doMonthlyTask();

        } catch (SchedulerException e) {
            log.error(Common.EXCEPTION, e);
        }
    }
    @PreDestroy
    public void cleanup() {
        try {
            if (scheduler != null) {
                scheduler.shutdown();
            }
        } catch (SchedulerException e) {
            log.error(Common.EXCEPTION, e);
        }
    }
    private void doMonthlyTask() throws SchedulerException {
        log.info(Common.START_B + "doMonthlyTask");
        JobDetail job = JobBuilder.newJob(GenerateBillTask.class)
                .withIdentity(MONTHLY_JOB + GenerateBillTask.class.getName(), GenerateBillTask.class.toString())
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(MONTHLY_TRIGGER + GenerateBillTask.class.getName(), GenerateBillTask.class.toString())
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 6 30 * ?"))//("0 0 6 30 * ?"))// 每月30號早上六點觸發(秒、分、小時、日期、月份、星期)
                .build();
        scheduler.scheduleJob(job, trigger);
        log.info(Common.END_B + "doMonthlyTask");
    }
}
