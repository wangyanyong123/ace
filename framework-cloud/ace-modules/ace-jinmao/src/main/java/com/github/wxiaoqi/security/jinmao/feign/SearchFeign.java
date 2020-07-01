package com.github.wxiaoqi.security.jinmao.feign;

import com.github.wxiaoqi.security.api.vo.order.in.ExcelInfoVo;
import com.github.wxiaoqi.security.api.vo.search.IndexObject;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ace
 * @create 2018/2/1.
 */
@FeignClient(value = "ace-tool", configuration = FeignApplyConfiguration.class)
public interface SearchFeign {

    @RequestMapping(value = "/search/index", method = RequestMethod.POST)
    public ObjectRestResponse createIndexObject(@RequestBody IndexObject indexObject);

    @RequestMapping(value = "/search/index", method = RequestMethod.PATCH)
    public ObjectRestResponse batchCreateIndexObject(@RequestBody List<IndexObject> indexObject);

    @RequestMapping(value = "/search/index", method = RequestMethod.DELETE)
    public ObjectRestResponse removeIndexObject(@RequestBody IndexObject indexObject);

    @RequestMapping(value = "/search/index/removeAll", method = RequestMethod.DELETE)
    public ObjectRestResponse removeAllIndexObject() ;

    @RequestMapping(value = "/search/index/refreshBrainpowerQuestionIndex", method = RequestMethod.GET)
    public ObjectRestResponse refreshBrainpowerQuestionIndex() ;
}
