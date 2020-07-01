package com.github.wxiaoqi.search.schedule;

import com.github.wxiaoqi.search.index.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时创建知识库搜索索引
 *
 * @author huangxl
 */
@Configuration
@Slf4j
@EnableScheduling
public class BrainpowerIndexSchedule {

    @Autowired
    private IndexService indexService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void refreshNotCompleteGroupBuyProduct() {
        indexService.refreshBrainpowerQuestionIndex();
    }
}
