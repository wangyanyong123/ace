package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizBusinessClassify;
import com.github.wxiaoqi.security.jinmao.vo.Business.OutParam.ResultBusinessVo;
import com.github.wxiaoqi.security.jinmao.vo.Classify.OutParam.ResultClassifyVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 业务商品分类表
 * 
 * @author zxl
 * @Date 2018-12-03 10:32:32
 */
public interface BizBusinessClassifyMapper extends CommonMapper<BizBusinessClassify> {

    /**
     * 查询商品分类列表
     * @param busId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ResultClassifyVo> selectClassifyList(@Param("busId") String busId,@Param("searchVal") String searchVal,
                                              @Param("page") Integer page,@Param("limit") Integer limit);

    /**
     * 查询商品分类数量
     * @param busId
     * @param searchVal
     * @return
     */
    int selectClassifyCount(@Param("busId") String busId,@Param("searchVal") String searchVal);


    /**
     * 删除商品分类
     * @param id
     * @param userId
     * @return
     */
    int delClassifyInfo(@Param("id") String id, @Param("userId") String userId);

    /**
     * 查询商品分类详情
     * @param id
     * @return
     */
    ResultClassifyVo selctClassifyInfo(String id);

    /**
     * 启用与禁用
     * @param id
     * @param busStatus
     * @param userId
     * @return
     */
    int updateClassifyEnableStatus(@Param("id") String id,@Param("busStatus") String busStatus,@Param("userId") String userId);

    /**
     * 查询所有的订单类型的业务列表
     * @return
     */
    List<ResultBusinessVo> selectBusinessListByType();

}
