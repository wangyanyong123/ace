package com.github.wxiaoqi.security.schedulewo;

import com.github.wxiaoqi.security.auth.client.EnableAceAuthClient;
import com.github.wxiaoqi.security.schedulewo.job.MsgJob;
import com.github.wxiaoqi.security.schedulewo.job.ScanningWODBJob;
import com.github.wxiaoqi.security.schedulewo.job.WoDispatchJob;
import com.spring4all.swagger.EnableSwagger2Doc;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Date;

/**
 * @author ace
 * @version 2017/12/26.
 */
@EnableEurekaClient
@SpringBootApplication
// 开启事务
//@EnableTransactionManagement
// 开启熔断监控
@EnableCircuitBreaker
// 开启服务鉴权
@EnableFeignClients({"com.github.wxiaoqi.security.auth.client.feign","com.github.wxiaoqi.security.schedulewo.feign"})
@MapperScan("com.github.wxiaoqi.security.schedulewo.mapper")
@EnableAceAuthClient
@EnableSwagger2Doc
@Slf4j
public class ScheduleWoBootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ScheduleWoBootstrap.class).web(true).run(args);

        scheduler();
    }

    public static Scheduler sch = null;

    static {
        try {
            sch = StdSchedulerFactory.getDefaultScheduler();
        } catch (SchedulerException e) {
            log.error("调度启动异常!", e);
        }
    }

    private static void scheduler() {

        try {

            log.error("调度开启......");

            Date startTime = new Date(System.currentTimeMillis() + 1000 * 10);
            /*------------------------------扫描工单------------------------------*/
            JobDetail scanningWODBJob = JobBuilder.newJob(ScanningWODBJob.class)
                    .withIdentity("ScanningWODBJob", "WO_GROUP").build();
            Trigger scanningTrigger = TriggerBuilder.newTrigger()
                    .withSchedule(CronScheduleBuilder.cronSchedule("*/10 * * * * ?"))
                    .withIdentity("ScanningWODBTrigger", "WO_GROUP").startAt(startTime).build();
            sch.scheduleJob(scanningWODBJob, scanningTrigger);
            /*------------------------------扫描工单------------------------------*/

            /*------------------------------匹配工单------------------------------*/
            JobDetail woDispatchJob = JobBuilder.newJob(WoDispatchJob.class).withIdentity("WoDispatchJob", "WO_GROUP")
                    .build();
            Trigger dispatchTrigger = TriggerBuilder.newTrigger()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1))
                    .withIdentity("WoDispatchJobTrigger", "WO_GROUP").startAt(startTime).build();
            sch.scheduleJob(woDispatchJob, dispatchTrigger);
            /*------------------------------匹配工单------------------------------*/

            /*------------------------------发送消息------------------------------*/
            JobDetail msgJob = JobBuilder.newJob(MsgJob.class).withIdentity("MsgJob", "MSG_GROUP").build();
            Trigger msgTrigger = TriggerBuilder.newTrigger()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1))
                    .withIdentity("MsgJobTrigger", "MSG_GROUP").startAt(startTime).build();
            sch.scheduleJob(msgJob, msgTrigger);
            /*------------------------------发送消息------------------------------*/

            sch.start();

            log.error("调度已经开启!");
        } catch (Exception e) {
            log.error("调度启动失败!", e);
        }

    }


}
