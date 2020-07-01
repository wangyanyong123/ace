package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.jinmao.entity.BizVisitorSignlogs;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.vo.visitsign.ResultVisitInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.visitsign.ResultVisitListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 访客登记表
 * 
 * @author zxl
 * @Date 2019-01-08 17:51:58
 */
public interface BizVisitorSignlogsMapper extends CommonMapper<BizVisitorSignlogs> {


    List<ResultVisitListVo> getVisitSignList(@Param("projectId")String projectId,@Param("searchVal")String searchVal,
                                            @Param("page")Integer page,@Param("limit")Integer limit);

    ResultVisitInfoVo getVisitSignInfo(@Param("id") String id);

    int getVisitSignCount(@Param("projectId")String projectId,@Param("searchVal")String searchVal);
}
