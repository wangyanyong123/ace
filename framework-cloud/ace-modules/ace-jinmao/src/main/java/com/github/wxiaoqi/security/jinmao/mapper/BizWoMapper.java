package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.api.vo.order.out.WoListForWebVo;
import com.github.wxiaoqi.security.api.vo.order.out.WoListVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizWo;
import com.github.wxiaoqi.security.jinmao.vo.wo.date.WoOnDateCount;
import com.github.wxiaoqi.security.jinmao.vo.wo.incidenttype.IncidentTypeCount;
import com.github.wxiaoqi.security.jinmao.vo.wo.typeclassify.ClassifyTypeCount;
import com.github.wxiaoqi.security.jinmao.vo.wo.woaging.WoAgingCount;
import com.github.wxiaoqi.security.jinmao.vo.wo.woaging.WoAgingList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 工单表
 *
 * @author huangxl
 * @Date 2018-12-03 15:00:10
 */
public interface BizWoMapper extends CommonMapper<BizWo> {

    List<WoListVo> selectWoListByUserId(Map<?, ?> map);

    List<WoListForWebVo> queryIncidentList(Map<?, ?> map);

    List<IncidentTypeCount> getIncidentTypeCount(@Param("projectId")String projectId,@Param("cityId")String cityId,
                                                 @Param("startTime")String startTime,@Param("endTime")String endTime,
                                                 @Param("page") Integer page,@Param("limit") Integer limit);

    int getIncidentTypeTotal(@Param("projectId")String projectId,@Param("cityId")String cityId,
                             @Param("startTime")String startTime,@Param("endTime")String endTime);

    List<ClassifyTypeCount> getClassifyTypeCount(Map<?,?> map);

    List<WoAgingCount> getWoAgingCount(@Param("projectId")String projectId, @Param("cityId")String cityId,
                                       @Param("startTime")String startTime, @Param("endTime")String endTime,@Param("incidentType") String incidentType);

    List<WoOnDateCount> getWoTypeCountByProperty(@Param("projectId")String projectId, @Param("startTime")String startTime, @Param("endTime")String endTime,
                                                @Param("page") Integer page,@Param("limit") Integer limit);

    int getWoTypeByPropertyTotal(@Param("projectId")String projectId, @Param("startTime")String startTime, @Param("endTime")String endTime);

    List<String> getClassifyName(@Param("type") String type);

    List<WoAgingList> getWoAgingListByMinute(Map<?,?> map);

    int getWoAgingListCount(Map<?,?> map);
}
