package com.github.wxiaoqi.search.controller;

import com.github.wxiaoqi.search.index.IndexService;
import com.github.wxiaoqi.search.service.LuceneService;
import com.github.wxiaoqi.security.api.vo.search.IndexObject;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author ace
 * @create 2018/3/5.
 */
@RestController
@RequestMapping("/search")
@ApiIgnore
public class SearchController {
    @Autowired
    private LuceneService luceneService;
    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "", method = {RequestMethod.POST, RequestMethod.GET})
    public TableResultResponse<IndexObject> search(@RequestParam String word, @RequestParam(defaultValue = "1") Integer pageNumber, @RequestParam(defaultValue = "15") Integer pageSize) {
        return luceneService.page(pageNumber, pageSize, word);
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public ObjectRestResponse createIndexObject(@RequestBody IndexObject indexObject) {
        luceneService.save(indexObject);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/index", method = RequestMethod.DELETE)
    public ObjectRestResponse removeIndexObject(@RequestBody IndexObject indexObject) {
        luceneService.delete(indexObject);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/index/removeAll", method = RequestMethod.DELETE)
    public ObjectRestResponse removeAllIndexObject() {
        luceneService.deleteAll();
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/index", method = RequestMethod.PATCH)
    public ObjectRestResponse batchCreateIndexObject(@RequestBody List<IndexObject> indexObjects) {
        for (IndexObject object : indexObjects) {
            luceneService.save(object);
        }
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/index", method = RequestMethod.PUT)
    public ObjectRestResponse updateIndexObject(@RequestBody IndexObject indexObject) {
        luceneService.update(indexObject);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/index/refreshBrainpowerQuestionIndex", method = RequestMethod.GET)
    public ObjectRestResponse refreshBrainpowerQuestionIndex() {
        indexService.refreshBrainpowerQuestionIndex();
        return new ObjectRestResponse();
    }

}
