package com.github.wxiaoqi.security.jinmao.mapper;


import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BaseAppServerUser;
import com.github.wxiaoqi.security.jinmao.vo.Customer.outParam.ResultCusInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.Customer.outParam.ResultCustomerVo;
import com.github.wxiaoqi.security.jinmao.vo.Service.OutParam.ResultServiceInfo;
import com.github.wxiaoqi.security.jinmao.vo.Service.OutParam.ResultServiceListVo;
import com.github.wxiaoqi.security.jinmao.vo.busService.out.BusServiceInfo;
import com.github.wxiaoqi.security.jinmao.vo.busService.out.BusServiceVo;
import com.github.wxiaoqi.security.jinmao.vo.face.UserFaceInfo;
import com.github.wxiaoqi.security.jinmao.vo.household.HouseholdVo;
import com.github.wxiaoqi.security.jinmao.vo.user.UserInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * app服务端用户表
 * 
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-21 13:55:50
 */
public interface BaseAppServerUserMapper extends CommonMapper<BaseAppServerUser> {

    /**
     * 查询客服人员列表
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<ResultCustomerVo> selectCustomerList(@Param("type") String type, @Param("tenantId") String tenantId,
               @Param("enableStatus") String enableStatus,@Param("searchVal") String searchVal,
               @Param("page") Integer page,@Param("limit") Integer limit);

    /**
     * 查询客服人员列表数量
     * @param tenantId
     * @param enableStatus
     * @param searchVal
     * @return
     */
    int selectCustomerCount(@Param("type") String type, @Param("tenantId") String tenantId,
                            @Param("enableStatus") String enableStatus,@Param("searchVal") String searchVal);


    /**
     * 根据手机号判断客服用户是否已存在
     * @param phone
     * @return
     */
    BaseAppServerUser selectIsUserByPhone(@Param("phone") String phone);

    BaseAppServerUser getUserByPhone(@Param("phone") String phone,@Param("tenantId")String tenantId);

    /**
     * 根据用户id查询客服人员信息
     * @param id
     * @return
     */
    ResultCusInfoVo selectCustomerInfoById(String id);

    /**
     * 删除客服人员
     * @param id
     * @param userId
     * @return
     */
    int delCusInfo(@Param("id") String id, @Param("userId") String userId);


    /**
     * 查询物业人员列表
     * @return
     */
    List<ResultServiceListVo> selectServiceList(@Param("type") String type, @Param("tenantId") String tenantId,
            @Param("enableStatus") String enableStatus,@Param("searchVal") String searchVal,
                                                @Param("page") Integer page,@Param("limit") Integer limit);

    /**
     * 根据条件查询物业人员数量
     * @param tenantId
     * @param enableStatus
     * @param searchVal
     * @return
     */
    int selectServiceCount(@Param("type") String type, @Param("tenantId") String tenantId,
                           @Param("enableStatus") String enableStatus,@Param("searchVal") String searchVal);

    /**
     * 删除物业人员
     * @param id
     * @param userId
     * @return
     */
    int deleteServiceInfo(@Param("id") String id, @Param("userId") String userId);


    /**
     * 根据用户id查询物业人员信息
     * @param id
     * @return
     */
    ResultServiceInfo selectServiceInfoById(String id);


    /**
     * 查询商业服务人员列表
     * @param type
     * @param tenantId
     * @param enableStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    List<BusServiceVo> selectBusServiceList(@Param("type") String type, @Param("tenantId") String tenantId,
                                            @Param("enableStatus") String enableStatus,@Param("searchVal") String searchVal,
                                            @Param("page") Integer page,@Param("limit") Integer limit);

    /**
     * 查询商业服务人员数量
     * @param type
     * @param tenantId
     * @param enableStatus
     * @param searchVal
     * @return
     */
    int selectBusServiceCount(@Param("type") String type, @Param("tenantId") String tenantId,
                              @Param("enableStatus") String enableStatus,@Param("searchVal") String searchVal);

    /**
     * 根据用户id查询商业服务人员信息
     * @param id
     * @return
     */
    BusServiceInfo selectBusServiceInfoById(String id);

    /**
     * 删除商业服务人员
     * @param id
     * @param userId
     * @return
     */
    int delBusServiceInfo(@Param("id") String id, @Param("userId") String userId);

    /**
     * 删除人员
     * @param id
     * @param userId
     * @return
     */
    int delAppUser(@Param("id") String id, @Param("userId") String userId);
    /**
     * 同时删除鉴权
     * @param id
     * @return
     */
    int delMemberUser(@Param("id") String id);
    /**
     * 查询住户
     * @param floorId
     * @return
     */
    List<HouseholdVo> selectHouseholdList(@Param("houseId")String houseId, @Param("floorId")String floorId, @Param("searchVal") String searchVal,
                                              @Param("page") Integer page, @Param("limit") Integer limit);

    /**
     * 根据住户列表数量
     * @param floorId
     * @param searchVal
     * @return
     */
    int getHouseholdCount(@Param("houseId") String houseId,@Param("floorId") String floorId,@Param("searchVal") String searchVal);

    String getProjectId(@Param("userId")String userId);

	int relieve(@Param("id") String id, @Param("userId") String userID);

    List<UserInfoVo> getUserList(@Param("searchVal")String searchVal,@Param("isOperation")String isOperation,
                                 @Param("page") Integer page,@Param("limit") Integer limit);

    int getUserListTotal(@Param("searchVal")String searchVal,@Param("isOperation")String isOperation);

    UserInfoVo getUserDetail(String id);

    UserInfoVo  checkUser(String phone);

    int deleteUserOperation(String id);

    int updateStatus(@Param("id") String id,@Param("isOperation") String isOperation);

    int setUserOperation(String phone);

	UserFaceInfo getUserFaceInfoById(@Param("id")String id);
}
