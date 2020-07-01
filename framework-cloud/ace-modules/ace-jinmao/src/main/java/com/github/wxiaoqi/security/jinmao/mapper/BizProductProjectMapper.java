package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizProductProject;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;

import java.util.List;

/**
 * 商品项目表
 * 
 * @author zxl
 * @Date 2018-12-05 09:58:44
 */
public interface BizProductProjectMapper extends CommonMapper<BizProductProject> {

    /**
     * 查询商品下的服务范围
     * @param id
     * @return
     */
    List<String> selectProjectIdById(String id);

    /**
     * 查询积分商品下的服务范围
     * @param id
     * @return
     */
    List<String> selectIntegralProjectIdById(String id);

    /**
     * 查询商品下的服务范围
     * @param id
     * @return
     */
    List<ResultProjectVo> selectProjectList(String id);

    /**
     * 查询积分商品下的服务范围
     * @param id
     * @return
     */
    List<ResultProjectVo> selectIntegralProjectList(String id);


    int delProjectInfo(String id);
}
