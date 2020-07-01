package com.github.wxiaoqi.security.jinmao.mapper;


import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BaseTenant;
import com.github.wxiaoqi.security.jinmao.entity.BaseUser;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.InputParam.UpdateEnableParam;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.CenterCityVo;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.ResultManageVo;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.ResultTenantVo;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.UserInfo;
import com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.OutParam.ResultMerchantManageVo;
import com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.OutParam.ResultMerchantVo;
import com.github.wxiaoqi.security.jinmao.vo.postage.out.PostageInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 租户表
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-20 12:35:12
 */
public interface BaseTenantMapper extends CommonMapper<BaseTenant> {

    /**
     * 查询公司管理列表
     * @param enableStatus
     * @param searchVal
     * @param page  当前页码
     * @param limit
     * @return 每页条数
     */
    List<ResultManageVo> selectCompanyManageList(@Param("enableStatus") String enableStatus, @Param("searchVal") String searchVal,
    @Param("page") Integer page,@Param("limit") Integer limit);

    /**
     * 根据搜索条件查询数量
     * @param enableStatus
     * @param searchVal
     * @return
     */
    int selectCompanyManageCount(@Param("enableStatus") String enableStatus, @Param("searchVal") String searchVal);

    /**
     * 修改禁用与启用状态
     * @param param
     * @return
     */
    int updateTenantEnableStatus(UpdateEnableParam param);

    /**
     * 效验关联的项目是否在其它数据中维护
     * @param projectId
     * @return
     */
    String selectIsProjectByProjectId(String projectId);

    /**
     * 启用时,判断该项目是否在其它数据中维护,如果维护过则启用失败
     * @param id
     * @return
     */
    BaseTenant selectIsProjectInfo(@Param("id") String id);

    /**
     * 根据租户id查询该管理详情
     * @param id
     * @return
     */
    ResultTenantVo selectTenantInfo(String id);

    /**
     * 查询商户管理列表
     * @param enableStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ResultMerchantManageVo> selectMerchantManageList(@Param("enableStatus") String enableStatus, @Param("searchVal")String searchVal,
                                                         @Param("page")Integer page, @Param("limit")Integer limit,@Param("tenantId")String tenantId);

    /**
     * 根据搜索条件查询数量
     * @param enableStatus
     * @param searchVal
     * @return
     */
    int selectMerchantManageCount(@Param("enableStatus") String enableStatus, @Param("searchVal") String searchVal,@Param("tenantId")String tenantId);


    /**
     * 修改禁用与启用状态
     * @param param
     * @return
     */
    int updateMerchantEnableStatus(UpdateEnableParam param);

    /**
     * 根据商户id查询管理详情
     * @param id
     * @return
     */
    ResultMerchantVo selectMerchantInfo(String id);

    List<PostageInfoVo> getPostageInfo(String tenantId);

    int deleteMerchantById(String enterpriseId);

    /**
     * 根据id查询租户类型
     * @param id
     * @return
     */
    String selectTenantTypeById(String id);

    /**
     * 查询当前用户所属角色
     * @param userId
     * @return
     */
    UserInfo selectRoleTypeByUser(String userId);

    /**
     * 获取商家名字
     * @return
     */
    List<String> getMerchantName(@Param("name") String name);

    UserInfo selectRoleTypeByUserId(String userId);


    String selectBaseUserId(String tenantId);

    int updateBaseUserStatus(@Param("status") String status,@Param("updUserId") String updUserId,@Param("id") String id);


    BaseUser selectWebUserInfo(String tenantId);

    int delPostageInfo(String id);

    List<CenterCityVo> getCenterCity();
}
