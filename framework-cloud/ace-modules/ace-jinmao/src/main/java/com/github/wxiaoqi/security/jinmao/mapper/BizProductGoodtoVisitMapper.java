package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizProductGoodtoVisit;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.ProductListVo;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.ResultGoodVisitInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.ResultGoodVisitVo;
import com.github.wxiaoqi.security.jinmao.vo.productgoodvisit.outputparam.TenantVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 好物探访表
 * 
 * @author zxl
 * @Date 2018-12-10 10:10:53
 */
public interface BizProductGoodtoVisitMapper extends CommonMapper<BizProductGoodtoVisit> {

    /**
     * 查询好物探访管理列表
     * @param enableStatus
     * @param searchVal
     * @param page  当前页码
     * @param limit
     * @return 每页条数
     */
    List<ResultGoodVisitVo> selectGoodVisitList(@Param("enableStatus") String enableStatus, @Param("searchVal") String searchVal,
                                                @Param("projectId")String projectId, @Param("page") Integer page, @Param("limit") Integer limit);
    /**
     * 根据搜索条件查询数量
     * @param enableStatus
     * @param searchVal
     * @return
     */
    int selectGoodVisitCount(@Param("enableStatus") String enableStatus, @Param("searchVal") String searchVal,@Param("projectId")String projectId);
    /**
     * 修改禁用与启用状态
     *
     * @return
     */
    int updateGoodVisitStatus(@Param("id") String id, @Param("enableStatus") String enableStatus);

    /**
     * 根据商品id查询该管理详情
     * @param id
     * @return
     */
    ResultGoodVisitInfoVo getGoodVisitInfo(@Param("id") String id);

    /**
     * 查询项目ID关联商品
     * @return
     */
    List<ProductListVo> selectProductList(@Param("searchVal")String searchVal);

    /**
     * 商品关联项目
     * @param productId
     * @return
     */
    List<ProjectListVo> selectProjectNames(@Param("productId") String productId);
    /**
     * 查询商品下项目
     * @param id
     * @return
     */
    List<ProductListVo> selectGVProductList(@Param("id") String id);

    /**
     * 查询商品关联商户
     * @param productId
     * @return
     */
    TenantVo selectTenantByProductId(@Param("productId") String productId);

    /**
     * 查询商品是否已推荐
     * @param productId
     * @return
     */
    String selectGoodVisitExist(@Param("productId") String productId);

    /**
     * 删除商品
     * @param id
     * @return
     */
    int deleteGoodVisit(@Param("id") String id,@Param("status") String status);

}
