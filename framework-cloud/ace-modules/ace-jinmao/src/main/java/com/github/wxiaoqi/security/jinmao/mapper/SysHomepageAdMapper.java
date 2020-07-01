package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.SysHomepageAd;
import com.github.wxiaoqi.security.jinmao.vo.adHomePage.out.AdHomePageInfo;
import com.github.wxiaoqi.security.jinmao.vo.adHomePage.out.AdHomePageVo;
import com.github.wxiaoqi.security.jinmao.vo.adHomePage.out.AdProjectInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * app广告位表
 * 
 * @author huangxl
 * @Date 2019-05-27 09:55:49
 */
public interface SysHomepageAdMapper extends CommonMapper<SysHomepageAd> {


    /**
     * 查询app广告业列表
     * @param isPublish
     * @param searchVal
     * @param projectId
     * @param page
     * @param limit
     * @return
     */
    List<AdHomePageVo> selectAdHomePageList(@Param("isPublish") String isPublish, @Param("searchVal") String searchVal,
          @Param("projectId") String projectId, @Param("page") Integer page, @Param("limit") Integer limit);


    /**
     * 获取过期广告ID
     * @return
     */
    List<String> selectAdOverdue();

    /**
     * 更新过期广告
     * @param adList
     * @return
     */
    int updateAdHomeStatus(@Param("adList") List<String> adList);
    /**
     * 查询app广告业列表数量
     * @param isPublish
     * @param searchVal
     * @param projectId
     * @return
     */
    int selectAdHomePageCount(@Param("isPublish") String isPublish, @Param("searchVal") String searchVal,
                              @Param("projectId") String projectId);

    /**
     * 根据广告id查询所属项目
     * @param adId
     * @return
     */
    List<String> selectProjectNameByAdId(String adId);

    /**
     * 查询app广告业详情
     * @param id
     * @return
     */
    AdHomePageInfo selectAdHomePageInfo(String id);


    List<AdProjectInfo> selectAdProjectVo(String adId);


    int delAdProjectByAdId(String adId);


    int operatingAdById(@Param("pStatus") String pStatus,@Param("userId") String userId, @Param("id") String id);



    List<AdHomePageInfo> getAppHomepageAdList(@Param("num") Integer num);
	
}
