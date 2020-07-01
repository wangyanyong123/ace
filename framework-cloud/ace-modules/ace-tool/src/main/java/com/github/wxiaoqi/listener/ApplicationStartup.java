package com.github.wxiaoqi.listener;

import com.github.wxiaoqi.search.index.IndexService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        IndexService service = contextRefreshedEvent.getApplicationContext().getBean(IndexService.class);
        service.refreshBrainpowerQuestionIndex();
    }
}