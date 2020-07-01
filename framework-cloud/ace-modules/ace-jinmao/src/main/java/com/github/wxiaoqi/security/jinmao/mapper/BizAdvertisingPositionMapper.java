package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizAdvertisingPosition;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import com.github.wxiaoqi.security.jinmao.vo.advertising.out.AdvertProjectInfo;
import com.github.wxiaoqi.security.jinmao.vo.advertising.out.ResultAdvert;
import com.github.wxiaoqi.security.jinmao.vo.advertising.out.ResultAdvertList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优选商城广告位
 * 
 * @author zxl
 * @Date 2018-12-17 15:07:24
 */
public interface BizAdvertisingPositionMapper extends CommonMapper<BizAdvertisingPosition> {

    /**
     * 查询广告列表
     * @param projectId
     * @return
     */
    List<ResultAdvertList> selectAdvertList(@Param("projectId")String projectId,@Param("searchVal")String searchVal,
                                            @Param("page")Integer page,@Param("limit")Integer limit);

    /**
     * 查询广告详情
     * @param id
     * @return
     */
    ResultAdvert selectAdvertInfo(@Param("id") String id);

    int selectAdvertCount(@Param("projectId")String projectId,@Param("searchVal") String searchVal);

    /**
     * 查询项目
     * @return
     */
    List<AdvertProjectInfo>  selectProjectByAdvertId(@Param("id") String id);

    int deleteProjectByAdvertId(@Param("advertId") String advertId);

    /**
     * 删除广告
     * @param id
     * @return
     */
    int deleteAdvertById(@Param("id") String id);

    List<ProjectListVo> getProjectInfo(@Param("productId") String productId);
}
