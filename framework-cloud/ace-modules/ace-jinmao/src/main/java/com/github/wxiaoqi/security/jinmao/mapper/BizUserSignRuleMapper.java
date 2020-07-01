package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.jinmao.entity.BizUserSignRule;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.vo.sign.out.SignList;
import com.github.wxiaoqi.security.jinmao.vo.sign.out.StatDataVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 运营服务-签到规则表
 * 
 * @author huangxl
 * @Date 2019-08-05 16:26:47
 */
public interface BizUserSignRuleMapper extends CommonMapper<BizUserSignRule> {

    List<SignList> getSignList(@Param("searchVal") String searchVal,@Param("signType") String signType,
                               @Param("page") Integer page,@Param("limit") Integer limit);

    int getSignListTotal(@Param("searchVal") String searchVal,@Param("signType") String signType);

    int getSignType();

    int deleteById(String id);

    int getSignDay(Integer signDay);

    /**
     * 按日统计签到人数
     * @param projectId
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<StatDataVo> selectStatSignByDay(@Param("projectId") String projectId, @Param("startTime") String startTime, @Param("endTime") String endTime,
                                         @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 按周统计签到数
     * @param projectId
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<StatDataVo> selectStatSignByWeek(@Param("projectId") String projectId, @Param("startTime") String startTime, @Param("endTime") String endTime,
                                          @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 按月统计签到数
     * @param projectId
     * @param startTime
     * @param endTime
     * @param page
     * @param limit
     * @return
     */
    List<StatDataVo> selectStatSignByMouth(@Param("projectId") String projectId, @Param("startTime") String startTime, @Param("endTime") String endTime,
                                           @Param("page") Integer page, @Param("limit") Integer limit);

}
