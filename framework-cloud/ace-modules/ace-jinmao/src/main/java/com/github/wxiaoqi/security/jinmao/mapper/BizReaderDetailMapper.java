package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizReaderDetail;
import com.github.wxiaoqi.security.jinmao.vo.stat.out.StatDataVo;
import com.github.wxiaoqi.security.jinmao.vo.stat.out.StatTopicVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 阅读详情统计表
 * 
 * @author huangxl
 * @Date 2019-08-14 11:25:32
 */
public interface BizReaderDetailMapper extends CommonMapper<BizReaderDetail> {

    /**
     * 按分钟统计数据
     * @param projectId
     * @param type
     * @param dataType
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<StatDataVo> selectStatDataByMinute(@Param("projectId") String projectId, @Param("type") String type,@Param("dataType") String dataType,
                                            @Param("startTime") String startTime, @Param("endTime") String endTime,
                                            @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 按小时统计数据
     * @param projectId
     * @param type
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<StatDataVo> selectStatDataByHours(@Param("projectId") String projectId, @Param("type") String type,@Param("dataType") String dataType,
                                           @Param("startTime") String startTime, @Param("endTime") String endTime,
                                           @Param("page") Integer page, @Param("limit") Integer limit);

   //按天统计数据
    List<StatDataVo> selectStatDataByDay(@Param("projectId") String projectId, @Param("type") String type,@Param("dataType") String dataType,
                                           @Param("startTime") String startTime, @Param("endTime") String endTime,
                                           @Param("page") Integer page, @Param("limit") Integer limit);

    //按周统计数据
    List<StatDataVo> selectStatDataByWeek(@Param("projectId") String projectId, @Param("type") String type,@Param("dataType") String dataType,
                                           @Param("startTime") String startTime, @Param("endTime") String endTime,
                                           @Param("page") Integer page, @Param("limit") Integer limit);
    //按月统计数据
    List<StatDataVo> selectStatDataByMouth(@Param("projectId") String projectId, @Param("type") String type,@Param("dataType") String dataType,
                                           @Param("startTime") String startTime, @Param("endTime") String endTime,
                                           @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询统计话题信息
     * @param type
     * @param projectId
     * @param dataType
     * @param startTime
     * @param endTime
     * @param pSort
     * @param uSort
     * @param page
     * @param limit
     * @return
     */
    List<StatTopicVo> selectTopicListByType( @Param("type") String type,@Param("projectId") String projectId,@Param("dataType") String dataType,
                                                     @Param("startTime") String startTime, @Param("endTime") String endTime,
                                                     @Param("pSort") String pSort, @Param("uSort") String uSort,
                                                     @Param("page") Integer page, @Param("limit") Integer limit
                                                     );











}
