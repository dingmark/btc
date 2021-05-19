package com.example.btc.services.ThreadingSocketService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @Description: 配置类实现AsyncConfigurer接口，并重写getAsyncExecutor方法，并返回一个ThreadPoolTaskExecutor，
 * 这样我们就获得一个基于线程池TaskExecutor
 * @ClassName: CustomMultiThreadingConfig
 * @Author: OnlyMate
 * @Date: 2018年9月21日 下午2:50:14
 */
@Configuration
@ComponentScan("com.example.btc.services.ThreadingSocketService")
@EnableAsync//利用@EnableAsync注解开启异步任务支持
public class CustomMultiConfigSocket implements SchedulingConfigurer { //AsyncConfigurer

  /*  @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(20);
        taskExecutor.setMaxPoolSize(80);
        taskExecutor.setQueueCapacity(25);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return AsyncConfigurer.super.getAsyncUncaughtExceptionHandler();
    }*/
  @Override
  public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
      scheduledTaskRegistrar.setScheduler(setTaskExecutorsSocket());
  }
    //19 +8
    @Bean(destroyMethod="shutdown")
    public Executor setTaskExecutorsSocket(){
        return Executors.newScheduledThreadPool(20); // 3个线程来处理。
    }

}
