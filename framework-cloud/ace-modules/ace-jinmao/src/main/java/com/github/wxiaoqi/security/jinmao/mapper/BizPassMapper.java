package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.jinmao.entity.BizPass;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.vo.passlog.QrPassListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author huangxl
 * @Date 2019-04-02 15:27:58
 */
public interface BizPassMapper extends CommonMapper<BizPass> {

    List<QrPassListVo> getQrPassLogList(@Param("projectId")String projectId,@Param("startDate")String startDate,@Param("endDate")String endDate,
                                        @Param("searchVal")String searchVal,@Param("page")Integer page,@Param("limit")Integer limit);

    int getQrPassCount(@Param("projectId")String projectId,@Param("startDate")String startDate,@Param("endDate")String endDate,@Param("searchVal") String searchVal);
}
