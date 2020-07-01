package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.app.entity.BizFlowProcessOperate;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 流程操作表
 * 
 * @author huangxl
 * @Date 2018-11-23 13:54:35
 */
public interface BizFlowProcessOperateMapper extends CommonMapper<BizFlowProcessOperate> {

    List<BizFlowProcessOperate> selectOperateList(Map<?, ?> map);

    /**
     * 根据工单ID和操作类型查询操作ID
     * @param id 订单工单ID
     * @param operateType 按钮操作类型
     * @return
     */
    Map<String, Object> getOperateIdByIdAndOperateType(@Param("id") String id, @Param("operateType") String operateType);

}
