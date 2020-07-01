package com.github.wxiaoqi.search.index;/**
 * @Auther: huangxl
 * @Date: 2019/4/19 12:45
 * @Description:
 */

import com.github.wxiaoqi.search.mapper.BrainpowerQuestionMapper;
import com.github.wxiaoqi.search.service.LuceneService;
import com.github.wxiaoqi.security.api.vo.brainpower.out.QuestionVo;
import com.github.wxiaoqi.security.api.vo.search.IndexObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Auther: huangxl
 * @Date: 2019/4/19 12:45
 */
@Slf4j
@Service
public class IndexService {

    @Autowired
    private LuceneService luceneService;
    @Autowired
    private BrainpowerQuestionMapper brainpowerQuestionMapper;

    public void refreshBrainpowerQuestionIndex() {
        String enableStatus = "2";
        int page = 0;
        int limit = 100000;
        int size = 0;
        log.info("开始重建知识库搜索索引"+ DateFormatUtils.format(new Date(),"yyyy-MM-dd'T'HH:mm:ss"));
        try {
            List<QuestionVo> questionList = brainpowerQuestionMapper.selectQuestionList("",enableStatus,"",page,limit);
            if(questionList!=null && questionList.size()>0){
                List<IndexObject> indexObjectList = new ArrayList<>();
                IndexObject indexObject = new IndexObject();
                for(QuestionVo questionVo : questionList){
                    indexObject = new IndexObject();
                    indexObject.setId(questionVo.getId());
                    indexObject.setTitle(questionVo.getQuestion());
                    indexObject.setDescripton(questionVo.getAnswer());
                    indexObject.setKeywords(questionVo.getFunctionPoint());
                    indexObject.setPostDate(DateFormatUtils.format(new Date(),"yyyy-MM-dd'T'HH:mm:ss"));
                    indexObject.setUrl("url");
                    indexObjectList.add(indexObject);
                }
                log.info("开始删除知识库搜索索引"+ DateFormatUtils.format(new Date(),"yyyy-MM-dd'T'HH:mm:ss"));
                luceneService.deleteAll();
                log.info("结束删除知识库搜索索引"+ DateFormatUtils.format(new Date(),"yyyy-MM-dd'T'HH:mm:ss"));
                log.info("开始创建知识库搜索索引"+ DateFormatUtils.format(new Date(),"yyyy-MM-dd'T'HH:mm:ss"));
                for (IndexObject object : indexObjectList) {
                    luceneService.save(object);
                }
                size = questionList.size();
                log.info("结束创建知识库搜索索引"+ DateFormatUtils.format(new Date(),"yyyy-MM-dd'T'HH:mm:ss")+",共创建"+size+"条索引");
            }
        }catch (Exception e){
            log.error("创建知识库搜索索引异常"+e.getMessage());
        } finally {

        }
        log.info("结束重建知识库搜索索引"+ DateFormatUtils.format(new Date(),"yyyy-MM-dd'T'HH:mm:ss"));
    }
}
