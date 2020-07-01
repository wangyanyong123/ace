package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizSensitiveWords;
import com.github.wxiaoqi.security.jinmao.vo.sensitive.in.SaveSensitiveParam;
import com.github.wxiaoqi.security.jinmao.vo.sensitive.out.SensitiveVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 敏感词表
 * 
 * @author huangxl
 * @Date 2019-01-25 14:01:58
 */
public interface BizSensitiveWordsMapper extends CommonMapper<BizSensitiveWords> {

    /**
     * 查询敏感词列表
     * @param sensitiveStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<SensitiveVo> selectSensitiveList(@Param("sensitiveStatus") String sensitiveStatus, @Param("searchVal") String searchVal,
                         @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 查询敏感词数量
     * @param sensitiveStatus
     * @param searchVal
     * @return
     */
    int selectSensitiveCount(@Param("sensitiveStatus") String sensitiveStatus, @Param("searchVal") String searchVal);


    /**
     * 查询敏感词详情
     * @param id
     * @return
     */
    SaveSensitiveParam selectSensitiveInfo(String id);

    /**
     * 敏感词操作(0-删除,1-已启用,2-禁用)
     * @param userId
     * @param status
     * @param id
     * @return
     */
    int updateSensitiveStatus(@Param("userId") String userId,@Param("status") String status,@Param("id") String id);


    List<String> selectAllSensitive();

    List<SensitiveVo> selectExportSensitiveList(@Param("sensitiveStatus") String sensitiveStatus, @Param("searchVal") String searchVal);

}
