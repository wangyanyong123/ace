package com.github.wxiaoqi.security.app.mapper;

import com.github.wxiaoqi.security.app.entity.BizAppUserFace;
import com.github.wxiaoqi.security.app.vo.face.FaceInfoVo;
import com.github.wxiaoqi.security.app.vo.face.SyncCrmFaceVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.common.util.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户人脸头像表
 * 
 * @author zxl
 * @Date 2019-01-07 10:36:52
 */
public interface BizAppUserFaceMapper extends CommonMapper<BizAppUserFace> {

    FaceInfoVo getFaceInfoByUserId(@Param("userId") String userId);

    int deleteFaceInfo(@Param("userId") String userId);



    /**
     * 获取上传人脸用户信息
     * @param userId
     * @return
     */
    List<SyncCrmFaceVo> queryFaceUserInfo(@Param("userId") String userId,@Param("projectId") String projectId);

    /**
     * 人脸历史数据查询
     * @param startIndex
     * @param limit
     * @return
     */
    List<SyncCrmFaceVo> syncHistoryFaceToCrm(@Param("startIndex") Integer startIndex, @Param("limit") Integer limit);
}
