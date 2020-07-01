package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizDecoreteApply;
import com.github.wxiaoqi.security.jinmao.vo.decoreteapply.DecApplyDetailVo;
import com.github.wxiaoqi.security.jinmao.vo.decoreteapply.DecoreteApplyListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 装修监理申请表
 * 
 * @author huangxl
 * @Date 2019-04-02 11:27:00
 */
public interface BizDecoreteApplyMapper extends CommonMapper<BizDecoreteApply> {


    List<DecoreteApplyListVo> getDecoreteApplyList(@Param("type")String type,@Param("tenantId")String tenantId,@Param("projectId") String projectId, @Param("status") String status,
                                                   @Param("searchVal") String searchVal,@Param("page") Integer page, @Param("limit") Integer limit);


    int getDecoreteApplyCount(@Param("type")String type,@Param("tenantId")String tenantId,@Param("projectId") String projectId,@Param("searchVal") String searchVal,@Param("status") String status);

    DecApplyDetailVo getDecoreteDetail(@Param("id") String id);

}
