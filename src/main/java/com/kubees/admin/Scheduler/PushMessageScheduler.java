package com.kubees.admin.Scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Slf4j
@Lazy(value = false)
@Component
@EnableScheduling
public class PushMessageScheduler implements SchedulingConfigurer {
    private static final String DEFAULT_CRON = "* * * 01 * ?";
    private String cron = DEFAULT_CRON;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> {
            log.info("         。。。");
        }, triggerContext -> {
            CronTrigger trigger = new CronTrigger(cron);
            return trigger.nextExecutionTime(triggerContext);
        });
    }
    public void setCron(String cron) {
        System.out.println("   cron："+this.cron+"    cron："+cron);
        this.cron = cron;
    }
    public String getCron() {
        return this.cron;
    }
}
