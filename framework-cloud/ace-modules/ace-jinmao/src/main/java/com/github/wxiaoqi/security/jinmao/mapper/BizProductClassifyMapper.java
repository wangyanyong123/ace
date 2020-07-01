package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.jinmao.entity.BizProductClassify;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultClassifyVo;

import java.util.List;

/**
 * 商品分类表
 * 
 * @author zxl
 * @Date 2018-12-05 09:58:44
 */
public interface BizProductClassifyMapper extends CommonMapper<BizProductClassify> {

    /**
     * 查询商户下业务的所属分类名称
     * @param id
     * @return
     */
    List<String> selectClassifyNameById(String id);

    /**
     * 根据业务id查询业务类型
     * @param busId
     * @return
     */
    String selectTypeByBusId(String busId);


    List<ResultClassifyVo> selectClassifyListById(String id);


    int delClassifyInfo(String id);

    /**
     * 查询商户列表下的商品分类名称列表
     * @return
     */
    List<ResultClassifyVo> selectClassifyNameList();
}
