package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizBusiness;
import com.github.wxiaoqi.security.jinmao.vo.Business.OutParam.ResultBusinessInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.Business.OutParam.ResultBusinessVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 业务表
 * 
 * @author zxl
 * @Date 2018-12-03 10:32:32
 */
public interface BizBusinessMapper extends CommonMapper<BizBusiness> {

    List<BizBusiness> selectAllBusinessList(Map<?, ?> map);

    /**
     * 查询业务列表
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ResultBusinessVo> selectBusinessList(@Param("searchVal") String searchVal,
                                              @Param("page") Integer page,@Param("limit") Integer limit);

    /**
     * 查询业务列表数量
     * @param searchVal
     * @return
     */
    int selectBusinessCount(@Param("searchVal") String searchVal);


    /**
     * 根据业务id删除业务
     * @param id
     * @param userId
     * @return
     */
    int delBusinessInfo(@Param("id") String id, @Param("userId") String userId);


    /**
     * 根据业务id查询业务详情
     * @param id
     * @return
     */
    ResultBusinessInfoVo selectBusinessInfo(String id);
}
