package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizVisitorSignlogs;
import com.github.wxiaoqi.security.app.vo.visitor.out.VisitInfoVo;
import com.github.wxiaoqi.security.app.vo.visitor.out.VisitListVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 访客登记表
 * 
 * @author zxl
 * @Date 2019-01-08 17:51:58
 */
public interface BizVisitorSignlogsMapper extends CommonMapper<BizVisitorSignlogs> {

    String getVisitAddress(@Param("projectName") String projectName);

    VisitInfoVo getVisitLogInfo(@Param("id") String id);

    List<VisitListVo> getVisitLogList(@Param("projectId") String projectId,@Param("userId")String userId,@Param("page")Integer page,@Param("limit")Integer limit);

    String getEncloseId(@Param("unitId")String unitId);

    String getUnitId(@Param("houseId") String houseId);
}
