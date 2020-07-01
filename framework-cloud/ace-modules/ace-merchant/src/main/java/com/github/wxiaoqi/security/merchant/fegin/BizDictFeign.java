package com.github.wxiaoqi.security.merchant.fegin;

import com.github.wxiaoqi.security.merchant.vo.evaluate.out.DictValueVo;
import com.github.wxiaoqi.security.auth.client.config.FeignApplyConfiguration;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ace
 * @create 2018/2/1.
 */
@FeignClient(value = "ace-admin", configuration = FeignApplyConfiguration.class)
public interface BizDictFeign {
    /**
     * 获取字典对对应值,查询三级字典树
     *
     * @param pid
     * @return
     */
    @RequestMapping(value = "/web/bizDict/selectDictThreeList/{pid}", method = RequestMethod.GET)
    public ObjectRestResponse selectDictThreeList(@PathVariable("pid") String pid);

    /**
     * 字典对外提供接口
     * @param code
     * @return
     */
    @RequestMapping(value = "/web/bizDict/feign/{code}", method = RequestMethod.GET)
    public TableResultResponse<DictValueVo> getDictValueByCode(@PathVariable("code") String code);

}
