package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizReport;
import com.github.wxiaoqi.security.jinmao.vo.report.out.FeedbackVo;
import com.github.wxiaoqi.security.jinmao.vo.report.out.ReportPersonVo;
import com.github.wxiaoqi.security.jinmao.vo.report.out.ReportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 举报管理表
 * 
 * @author huangxl
 * @Date 2019-03-04 17:13:39
 */
public interface BizReportMapper extends CommonMapper<BizReport> {


    /**
     * 查询举报列表
     * @param projectId
     * @param groupId
     * @param reportType
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ReportVo> selectReportList(@Param("projectId") String projectId, @Param("groupId") String groupId, @Param("reportType") String reportType,
                                    @Param("searchVal") String searchVal, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 查询举报列表数量
     * @param projectId
     * @param groupId
     * @param reportType
     * @param searchVal
     * @return
     */
    int selectReportCount(@Param("projectId") String projectId, @Param("groupId") String groupId, @Param("reportType") String reportType,
                          @Param("searchVal") String searchVal);

    /**
     * 允许/禁止评论,发帖
     * @param status
     * @param userId
     * @param beUserId
     * @return
     */
    int updateForbidStatus(@Param("status") String status, @Param("userId") String userId, @Param("beUserId") String beUserId);

    /**
     * 查看举报人信息
     * @param reportId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ReportPersonVo> seelctReportPersonList(@Param("reportId") String reportId, @Param("searchVal") String searchVal,
                                                @Param("page") Integer page, @Param("limit") Integer limit);

    int seelctReportPersonCount(@Param("reportId") String reportId, @Param("searchVal") String searchVal);

    /**
     * 根据用户id查询反馈信息
     * @param userId
     * @return
     */
    List<FeedbackVo> selectFeedbackByUserId(@Param("userId") String userId, @Param("searchVal") String searchVal,
                                            @Param("page") Integer page, @Param("limit") Integer limit);


    int selectFeedbackByUserIdCount(@Param("userId") String userId, @Param("searchVal") String searchVal);
	
}
