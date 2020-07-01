package com.github.wxiaoqi.security.jinmao.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.MsgThemeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Sha256PasswordEncoder;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BaseAppServerUser;
import com.github.wxiaoqi.security.jinmao.entity.BaseGroupMember;
import com.github.wxiaoqi.security.jinmao.feign.NoticeFeign;
import com.github.wxiaoqi.security.jinmao.feign.SmsFeign;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.BaseAppServerUserMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BaseGroupMemberMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BaseTenantProjectMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductMapper;
import com.github.wxiaoqi.security.jinmao.task.SyncUserInfoTask;
import com.github.wxiaoqi.security.jinmao.vo.Customer.InputParam.SaveCusParam;
import com.github.wxiaoqi.security.jinmao.vo.Customer.outParam.ResultCusInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.Customer.outParam.ResultCustomerVo;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.busService.in.SaveBusServiceParam;
import com.github.wxiaoqi.security.jinmao.vo.busService.out.BusServiceInfo;
import com.github.wxiaoqi.security.jinmao.vo.busService.out.BusServiceVo;
import com.github.wxiaoqi.security.jinmao.vo.face.UserFaceInfo;
import com.github.wxiaoqi.security.jinmao.vo.houseKeeper.HouseKeeperInVo;
import com.github.wxiaoqi.security.jinmao.vo.houseKeeper.HouseKeeperInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.houseKeeper.HouseKeeperVo;
import com.github.wxiaoqi.security.jinmao.vo.household.HouseholdVo;
import com.github.wxiaoqi.security.jinmao.vo.user.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * app服务端用户表
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-21 13:55:50
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
@Slf4j
public class BaseAppServerUserBiz extends BusinessBiz<BaseAppServerUserMapper, BaseAppServerUser> {

    private Logger logger = LoggerFactory.getLogger(BaseAppServerUserBiz.class);
    private Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();

    @Autowired
    private BaseAppServerUserMapper baseAppServerUserMapper;
    @Autowired
    private SmsFeign smsFeign;

    @Autowired
	private BaseTenantProjectMapper tenantProjectMapper;
	@Autowired
    private BizProductMapper bizProductMapper;

    @Autowired
    private BaseGroupMemberMapper baseGroupMemberMapper;

    @Autowired
    private SyncUserInfoTask syncUserInfoTask;
    @Autowired
    private ToolFegin toolFeign;

    @Autowired
	private NoticeFeign noticeFeign;

    /**
     * 查询客服人员列表
     * @param enableStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ResultCustomerVo> getCustomerList(String enableStatus, String searchVal,Integer page,Integer limit){
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<ResultCustomerVo> customerList =  baseAppServerUserMapper.selectCustomerList(type,BaseContextHandler.getTenantID(),
                enableStatus,searchVal, startIndex, limit);
        return customerList;
    }


    /**
     * 查询客服人员列表数量
     * @param enableStatus
     * @param searchVal
     * @return
     */
    public int  selectCustomerCount(String enableStatus, String searchVal){
		String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        int total = baseAppServerUserMapper.selectCustomerCount(type,BaseContextHandler.getTenantID(),
                enableStatus,searchVal);
        return total;
    }

    /**
     * 保存客服人员
     * @param param
     * @return
     */
    public ObjectRestResponse saveCustomer(SaveCusParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(null == param){
            msg.setStatus(505);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getName())){
            msg.setStatus(506);
            msg.setMessage("姓名不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getMobilePhone())){
            msg.setStatus(507);
            msg.setMessage("手机号不能为空!");
            return msg;
        }else if(!StringUtils.isMobile(param.getMobilePhone())){
            msg.setStatus(508);
            msg.setMessage("请输入正确的手机号!");
            return msg;
        }
        if(!StringUtils.isEmpty(param.getEmail())
                && !StringUtils.checkEmail(param.getEmail())){
            msg.setStatus(509);
            msg.setMessage("请输入正确的Email!");
            return msg;
        }
        String msgTheme = "";
        if(StringUtils.isEmpty(BaseContextHandler.getTenantID())){
            msg.setStatus(501);
            msg.setMessage("未获取到当前用户的租户id!");
            return msg;
        }
        //判断用户是否已存在
        BaseAppServerUser appServerUser = baseAppServerUserMapper.selectIsUserByPhone(param.getMobilePhone());
        if(appServerUser != null){
            if(!BaseContextHandler.getTenantID().equals(appServerUser.getTenantId())){
                String projrctName = tenantProjectMapper.selectProjectNameByTenantId(appServerUser.getTenantId());
                msg.setStatus(502);
                msg.setMessage("该用户已经是"+projrctName+"的服务人员了!");
            } else if ("1".equals(appServerUser.getIsCustomer())){
                msg.setStatus(503);
                msg.setMessage("已经添加过该用户了!");
                return msg;
            } else {
                BaseAppServerUser user = new BaseAppServerUser();
                user.setId(appServerUser.getId());
                user.setName(param.getName());
                user.setEmail(param.getEmail());
                user.setProfilePhoto(param.getProfilePhoto());
                user.setEnableStatus(param.getEnableStatus());
                user.setSex(param.getSex());
                user.setStatus("1");
                user.setIsCustomer("1");
                user.setModifyTime(new Date());
                user.setModifyBy(BaseContextHandler.getUserID());
                baseAppServerUserMapper.updateByPrimaryKeySelective(user);
                msgTheme = MsgThemeConstants.ADD_CUSTOMER;
            }
        }else{
            //添加客服人员
            BaseAppServerUser user = new BaseAppServerUser();
            user.setId(UUIDUtils.generateUuid());
            user.setPassword(encoder.encode("123456"));
            user.setName(param.getName());
            user.setSex(param.getSex());
            user.setMobilePhone(param.getMobilePhone());
            user.setEmail(param.getEmail());
            user.setEnableStatus(param.getEnableStatus());
            user.setProfilePhoto(param.getProfilePhoto());
            user.setServiceGroupId(UUIDUtils.generateUuid());
            user.setIsCustomer("1");
            user.setTenantId(BaseContextHandler.getTenantID());
            user.setCreateBy(BaseContextHandler.getUserID());
            user.setCreateTime(new Date());
            if(baseAppServerUserMapper.insertSelective(user) < 0){
                msg.setStatus(102);
                msg.setMessage("保存客服人员失败!");
                return msg;
            }
            msgTheme = MsgThemeConstants.ADD_CUSTOMER;
        }
        //发送短信
        if(!StringUtils.isEmpty(msgTheme)){
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("projectName",tenantProjectMapper.selectProjectNameByTenantId(BaseContextHandler.getTenantID()));
            paramMap.put("userName", param.getName());
            System.out.println(JSON.toJSONString(paramMap));
            ObjectRestResponse result = smsFeign.sendMsg(param.getMobilePhone(),0,0,"2",msgTheme,JSON.toJSONString(paramMap));
            if(result.getStatus() != 200){
                logger.error("新建客服发送短信通知失败！");
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 查询客服人员详情
     * @param id
     * @return
     */
    public List<ResultCusInfoVo> getCusInfo(String id){
        List<ResultCusInfoVo> list = new ArrayList<>();
        ResultCusInfoVo cusInfo = baseAppServerUserMapper.selectCustomerInfoById(id);
        if(cusInfo != null){
            list.add(cusInfo);
        }else{
            ResultCusInfoVo vo = new ResultCusInfoVo();
            list.add(vo);
        }
        return list;
    }

    /**
     * 删除客服人员
     * @param id
     * @return
     */
    public ObjectRestResponse delCusInfo(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        ResultCusInfoVo cusInfo = baseAppServerUserMapper.selectCustomerInfoById(id);
        if(cusInfo != null){
            BaseAppServerUser appServerUser = baseAppServerUserMapper.selectIsUserByPhone(cusInfo.getMobilePhone());
            if(appServerUser != null){
                if(baseAppServerUserMapper.delCusInfo(id,BaseContextHandler.getUserID()) < 0){
                    msg.setStatus(101);
                    msg.setMessage("删除客服人员失败!");
                    return msg;
                }else{
                    if("0".equals(appServerUser.getIsBusiness()) && "0".equals(appServerUser.getIsService()) && "0".equals(appServerUser.getIsHousekeeper())){
                        baseAppServerUserMapper.delAppUser(id,BaseContextHandler.getUserID());
                    }
                }
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 编辑客服人员
     * @param param
     * @return
     */
    public ObjectRestResponse updateCus(SaveCusParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(null == param){
            msg.setStatus(505);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getName())){
            msg.setStatus(506);
            msg.setMessage("姓名不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getMobilePhone())){
            msg.setStatus(507);
            msg.setMessage("手机号不能为空!");
            return msg;
        }else if(!StringUtils.isMobile(param.getMobilePhone())){
            msg.setStatus(508);
            msg.setMessage("请输入正确的手机号!");
            return msg;
        }
        if(!StringUtils.isEmpty(param.getEmail())
                && !StringUtils.checkEmail(param.getEmail())){
            msg.setStatus(509);
            msg.setMessage("请输入正确的Email!");
            return msg;
        }

        ResultCusInfoVo cusInfo = baseAppServerUserMapper.selectCustomerInfoById(param.getId());
        BaseAppServerUser user = new BaseAppServerUser();
        if(cusInfo != null){
            try {
                BeanUtils.copyProperties(cusInfo,user);
                user.setName(param.getName());
                user.setSex(param.getSex());
                /*if(!cusInfo.getMobilePhone().equals(param.getMobilePhone())){
                    //判断用户是否已存在
                    BaseAppServerUser appServerUser = baseAppServerUserMapper.selectIsUserByPhone(param.getMobilePhone());
                    if(appServerUser != null) {
                        if (!BaseContextHandler.getTenantID().equals(appServerUser.getTenantId())) {
                            String projrctName = tenantProjectMapper.selectProjectNameByTenantId(appServerUser.getTenantId());
                            msg.setStatus(502);
                            msg.setMessage("该用户已经是"+projrctName+"的服务人员了!");
                            return msg;
                        } else if ("1".equals(appServerUser.getIsCustomer())) {
                            msg.setStatus(503);
                            msg.setMessage("已经添加过该用户了!");
                            return msg;
                        }
                    }
                    user.setMobilePhone(param.getMobilePhone());
                    user.setIsActive("0");
                }*/
                user.setEmail(param.getEmail());
                user.setEnableStatus(param.getEnableStatus());
                user.setProfilePhoto(param.getProfilePhoto());
                user.setModifyBy(BaseContextHandler.getUserID());
                user.setModifyTime(new Date());
                if(baseAppServerUserMapper.updateByPrimaryKeySelective(user) < 0){
                    msg.setStatus(101);
                    msg.setMessage("编辑客服人员失败!");
                    return msg;
                }
            } catch (Exception e) {
                logger.error("编辑客服人员失败!,id为{}",e);
            }
        }else{
            msg.setStatus(102);
            msg.setMessage("编辑客服人员失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    public ObjectRestResponse addHouseKeeper(HouseKeeperInVo houseKeeperVo) {
        ObjectRestResponse msg = new ObjectRestResponse();
		if(null == houseKeeperVo){
			msg.setStatus(505);
			msg.setMessage("参数不能为空!");
			return msg;
		}
		if(StringUtils.isEmpty(houseKeeperVo.getName())){
			msg.setStatus(506);
			msg.setMessage("姓名不能为空!");
			return msg;
		}
		if(StringUtils.isEmpty(houseKeeperVo.getMobilePhone())){
			msg.setStatus(507);
			msg.setMessage("手机号不能为空!");
			return msg;
		}else if(!StringUtils.isMobile(houseKeeperVo.getMobilePhone())){
			msg.setStatus(508);
			msg.setMessage("请输入正确的手机号!");
			return msg;
		}
		if(!StringUtils.isEmpty(houseKeeperVo.getEmail())
			&& !StringUtils.checkEmail(houseKeeperVo.getEmail())){
			msg.setStatus(509);
			msg.setMessage("请输入正确的Email!");
			return msg;
		}
        BaseAppServerUser param = new BaseAppServerUser();
        param.setMobilePhone(houseKeeperVo.getMobilePhone());
        param.setStatus("1");
        param = this.selectOne(param);
		if(StringUtils.isEmpty(BaseContextHandler.getTenantID())){
			msg.setStatus(501);
			msg.setMessage("未获取到当前用户的租户id!");
			return msg;
		}
		String msgTheme = "";
        if(null != param && !StringUtils.isEmpty(param.getId())){
        	if(!BaseContextHandler.getTenantID().equals(param.getTenantId())){
        		String projrctName = tenantProjectMapper.selectProjectNameByTenantId(param.getTenantId());
				msg.setStatus(502);
				msg.setMessage("该用户已经是"+projrctName+"的服务人员了!");
				return msg;
			} else if ("1".equals(param.getIsHousekeeper())){
				msg.setStatus(503);
				msg.setMessage("已经添加过该用户了!");
				return msg;
			} else {
				param.setName(houseKeeperVo.getName());
				param.setEmail(houseKeeperVo.getEmail());
				if(houseKeeperVo.getProfilePhoto().size() >0 ){
					param.setProfilePhoto(houseKeeperVo.getProfilePhoto().get(0).getUrl());
				}
				param.setSex(houseKeeperVo.getSex());
				param.setIsHousekeeper("1");
				param.setModifyTime(new Date());
				param.setModifyBy(BaseContextHandler.getUserID());
				this.updateSelectiveById(param);
				msgTheme = MsgThemeConstants.ADD_HOUSEKEEPER;
			}
		} else {
			//添加客服人员
			BaseAppServerUser user = new BaseAppServerUser();
			org.springframework.beans.BeanUtils.copyProperties(houseKeeperVo,user);
			user.setId(UUIDUtils.generateUuid());
			user.setPassword(encoder.encode("123456"));
			user.setEnableStatus("1");
			if(houseKeeperVo.getProfilePhoto().size() >0 ){
				user.setProfilePhoto(houseKeeperVo.getProfilePhoto().get(0).getUrl());
			}
			user.setIsHousekeeper("1");
			user.setTenantId(BaseContextHandler.getTenantID());
			user.setCreateBy(BaseContextHandler.getUserID());
			user.setCreateTime(new Date());
			user.setStatus("1");
			if(baseAppServerUserMapper.insertSelective(user) < 1){
				msg.setStatus(504);
				msg.setMessage("添加管家失败!");
				return msg;
			}
			msgTheme = MsgThemeConstants.ADD_NEW_HOUSEKEEPER;
		}
		if(!StringUtils.isEmpty(msgTheme)){
			Map<String,String> paramMap = new HashMap<>();
			paramMap.put("projectName",tenantProjectMapper.selectProjectNameByTenantId(BaseContextHandler.getTenantID()));
			paramMap.put("userName", houseKeeperVo.getName());
			ObjectRestResponse result = smsFeign.sendMsg(houseKeeperVo.getMobilePhone(),0,0,"2",msgTheme,JSON.toJSONString(paramMap));
			if(result.getStatus() != 200){
				logger.error("新建管家发送短信通知失败！");
			}
		}
		msg.setMessage("添加成功");
		return msg;
    }

	public ObjectRestResponse<HouseKeeperVo> getHouseKeeperInfoByPhone(String mobilePhone) {
		ObjectRestResponse<HouseKeeperVo> restResponse = new ObjectRestResponse<>();
		BaseAppServerUser param = new BaseAppServerUser();
		param.setMobilePhone(mobilePhone);
		param.setStatus("1");
		param.setTenantId(BaseContextHandler.getTenantID());
		param = this.selectOne(param);
		HouseKeeperVo houseKeeperVo = new HouseKeeperVo();
		if(null != param){
			org.springframework.beans.BeanUtils.copyProperties(param, houseKeeperVo);
		}
		if(StringUtils.isEmpty(houseKeeperVo.getProfilePhoto())){
			houseKeeperVo.setProfilePhoto("");
		}
		restResponse.setData(houseKeeperVo);
		return restResponse;
	}

	public ObjectRestResponse<HouseKeeperInfoVo> getHouseKeeperInfo(String id) {
		ObjectRestResponse msg = new ObjectRestResponse();
		if(StringUtils.isEmpty(id)){
			msg.setStatus(501);
			msg.setMessage("id不能为空!");
			return msg;
		}
		BaseAppServerUser param = new BaseAppServerUser();
		param.setId(id);
		param.setTenantId(BaseContextHandler.getTenantID());
		param.setStatus("1");
		param.setIsHousekeeper("1");
		BaseAppServerUser appServerUser = this.selectOne(param);
		if(null != appServerUser && !StringUtils.isEmpty(appServerUser.getId())){
			HouseKeeperInfoVo houseKeeperInfoVo = new HouseKeeperInfoVo();
			org.springframework.beans.BeanUtils.copyProperties(appServerUser,houseKeeperInfoVo);
			msg.setData(houseKeeperInfoVo);
			return msg;
		}
		msg.setMessage("未查询到该用户！");
		return msg;
	}

	public ObjectRestResponse updateHouseKeeper(HouseKeeperInfoVo houseKeeperInfoVo) {
		ObjectRestResponse msg = new ObjectRestResponse();
		if(null == houseKeeperInfoVo){
			msg.setStatus(501);
			msg.setMessage("参数不能为空!");
			return msg;
		}
		if(StringUtils.isEmpty(houseKeeperInfoVo.getName())){
			msg.setStatus(502);
			msg.setMessage("姓名不能为空!");
			return msg;
		}
		if(StringUtils.isEmpty(houseKeeperInfoVo.getMobilePhone())){
			msg.setStatus(503);
			msg.setMessage("手机号不能为空!");
			return msg;
		}else if(!StringUtils.isMobile(houseKeeperInfoVo.getMobilePhone())){
			msg.setStatus(504);
			msg.setMessage("请输入正确的手机号!");
			return msg;
		}
		if(!StringUtils.isEmpty(houseKeeperInfoVo.getEmail())
				&& !StringUtils.checkEmail(houseKeeperInfoVo.getEmail())){
			msg.setStatus(505);
			msg.setMessage("请输入正确的Email!");
			return msg;
		}
		if(StringUtils.isEmpty(houseKeeperInfoVo.getId())){
			msg.setStatus(506);
			msg.setMessage("id不能为空!");
			return msg;
		}

		BaseAppServerUser p = new BaseAppServerUser();
		p.setId(houseKeeperInfoVo.getId());
		if(!houseKeeperInfoVo.getMobilePhone().equals(this.baseAppServerUserMapper.selectOne(p).getMobilePhone())){
			msg.setStatus(508);
			msg.setMessage("手机号不能修改!");
			return msg;
		}

		BaseAppServerUser appServerUser = new BaseAppServerUser();
		org.springframework.beans.BeanUtils.copyProperties(houseKeeperInfoVo,appServerUser);
		appServerUser.setModifyBy(BaseContextHandler.getUserID());
		appServerUser.setModifyTime(new Date());
		if(baseAppServerUserMapper.updateByPrimaryKeySelective(appServerUser) < 1){
			msg.setStatus(507);
			msg.setMessage("id错误!");
			return msg;
		}
		msg.setMessage("修改成功");
		return msg;
	}


    /**
     * 查询商业服务人员列表
     * @param enableStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
	public List<BusServiceVo> getBusServiceList(String enableStatus, String searchVal,Integer page,Integer limit){
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<BusServiceVo> busServiceVoList = baseAppServerUserMapper.selectBusServiceList(type,BaseContextHandler.getTenantID(),
                enableStatus,searchVal,startIndex,limit);
        return busServiceVoList;
    }

    /**
     * 查询商业服务人员数量
     * @param enableStatus
     * @param searchVal
     * @return
     */
    public int selectBusServiceCount(String enableStatus, String searchVal){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        int total = baseAppServerUserMapper.selectBusServiceCount(type, BaseContextHandler.getTenantID(),enableStatus, searchVal);
        return total;
    }


    /**
     * 保存商业服务人员
     * @param param
     * @return
     */
    public ObjectRestResponse saveBusService(SaveBusServiceParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(null == param){
            msg.setStatus(505);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getName())){
            msg.setStatus(506);
            msg.setMessage("姓名不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getMobilePhone())){
            msg.setStatus(507);
            msg.setMessage("手机号不能为空!");
            return msg;
        }else if(!StringUtils.isMobile(param.getMobilePhone())){
            msg.setStatus(508);
            msg.setMessage("请输入正确的手机号!");
            return msg;
        }
        if(!StringUtils.isEmpty(param.getEmail())
                && !StringUtils.checkEmail(param.getEmail())){
            msg.setStatus(509);
            msg.setMessage("请输入正确的Email!");
            return msg;
        }
        String msgTheme = "";
        if(StringUtils.isEmpty(BaseContextHandler.getTenantID())){
            msg.setStatus(501);
            msg.setMessage("未获取到当前用户的租户id!");
            return msg;
        }
        BaseAppServerUser user = new BaseAppServerUser();
        String id = UUIDUtils.generateUuid();
        //判断用户是否已存在
        BaseAppServerUser appServerUser = baseAppServerUserMapper.selectIsUserByPhone(param.getMobilePhone());
        if(appServerUser != null){
            if(!BaseContextHandler.getTenantID().equals(appServerUser.getTenantId())){
                String projrctName = tenantProjectMapper.selectProjectNameByTenantId(appServerUser.getTenantId());
                msg.setStatus(502);
                msg.setMessage("该用户已经是"+projrctName+"的服务人员了!");
                return msg;
            } else if ("1".equals(appServerUser.getIsBusiness())){
                msg.setStatus(503);
                msg.setMessage("已经添加过该用户了!");
                return msg;
            }
        }else{
            //保存商业服务人员
            Config config = ConfigService.getConfig("ace-jinmao");
            String password = config.getProperty("business-service-staff-default-pwd",null);
            user = new BaseAppServerUser();
            user.setId(id);
            user.setPassword(encoder.encode(password));
            user.setName(param.getName());
            user.setSex(param.getSex());
            user.setMobilePhone(param.getMobilePhone());
            user.setEmail(param.getEmail());
            user.setEnableStatus(param.getEnableStatus());
            if(param.getProfilePhotoList() != null & param.getProfilePhotoList().size() > 0){
                if(StringUtils.isNotEmpty(param.getProfilePhotoList().get(0).getUrl())){
                    ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(param.getProfilePhotoList().get(0).getUrl(), DocPathConstant.SHOP);
                    if(restResponse.getStatus()==200){
                        user.setProfilePhoto(restResponse.getData()==null ? "" : (String)restResponse.getData());
                    }
                }
                //user.setProfilePhoto(param.getProfilePhotoList().get(0).getUrl());
            }
            user.setServiceGroupId(UUIDUtils.generateUuid());
            user.setIsBusiness("1");
            user.setTenantId(BaseContextHandler.getTenantID());
            user.setCreateBy(BaseContextHandler.getUserID());
            user.setCreateTime(new Date());
            if(baseAppServerUserMapper.insertSelective(user) < 0){
                msg.setStatus(102);
                msg.setMessage("保存商业服务人员失败!");
                return msg;
            }
            //保存成功，添加权限
            BaseGroupMember auth=new BaseGroupMember();
            String authid = UUIDUtils.generateUuid();
            String groupId = "de6d0a446e8d25029c1122d9b951a8ve";
            auth.setId(authid);
            auth.setUserId(id);
            auth.setCrtTime(new Date());
            auth.setGroupId(groupId);
            auth.setCrtUser(BaseContextHandler.getUserID());
            auth.setTenantId(BaseContextHandler.getTenantID());
            auth.setCrtName(BaseContextHandler.getUsername());
            baseGroupMemberMapper.insertSelective(auth);
            msgTheme = MsgThemeConstants.ADD_BUSINESS;
        }
        //发送短信
        if(!StringUtils.isEmpty(msgTheme)){
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("projectName",tenantProjectMapper.selectProjectNameByTenantId(BaseContextHandler.getTenantID()));
            paramMap.put("userName", param.getName());
            ObjectRestResponse result = smsFeign.sendMsg(param.getMobilePhone(),0,0,"2",msgTheme,JSON.toJSONString(paramMap));
            if(result.getStatus() != 200){
                logger.error("新建商业服务发送短信通知失败！");
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        //商业人员
        BaseAppServerUser baseAppServerUser = baseAppServerUserMapper.selectByPrimaryKey(user.getId());
        if (baseAppServerUser != null) {
            syncUserInfoTask.saveSyncUserInfo(baseAppServerUser, baseAppServerUser.getMobilePhone(),"1","2");
        }
        return msg;
    }


    /**
     * 查询商业服务人员信息
     * @param id
     * @return
     */
    public List<BusServiceInfo> getBusServiceInfo(String id){
        List<BusServiceInfo> list = new ArrayList<>();
        BusServiceInfo busServiceInfo = baseAppServerUserMapper.selectBusServiceInfoById(id);
        if(busServiceInfo != null){
            List<ImgInfo> imgList = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            imgInfo.setUrl(busServiceInfo.getProfilePhoto());
            imgList.add(imgInfo);
            busServiceInfo.setProfilePhotoList(imgList);
            list.add(busServiceInfo);
        }else{
            BusServiceInfo vo = new BusServiceInfo();
            list.add(vo);
        }
        return list;
    }


    /**
     * 删除商业服务人员
     * @param id
     * @return
     */
    public ObjectRestResponse delBusServiceInfo(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        BusServiceInfo busServiceInfo = baseAppServerUserMapper.selectBusServiceInfoById(id);
        BaseAppServerUser appServerUser = null;
        if(busServiceInfo != null){
            appServerUser = baseAppServerUserMapper.selectIsUserByPhone(busServiceInfo.getMobilePhone());
            if(appServerUser != null){
                if(baseAppServerUserMapper.delBusServiceInfo(id,BaseContextHandler.getUserID()) < 0){
                    msg.setStatus(101);
                    msg.setMessage("删除商业服务人员失败!");
                    return msg;
                }else{
                    if("0".equals(appServerUser.getIsCustomer()) && "0".equals(appServerUser.getIsService()) && "0".equals(appServerUser.getIsHousekeeper())){
                        baseAppServerUserMapper.delAppUser(id,BaseContextHandler.getUserID());
                    }
                }
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        //商业人员
        if (appServerUser != null) {
            syncUserInfoTask.saveSyncUserInfo(appServerUser, appServerUser.getMobilePhone(),"3","2");
        }
        return msg;
    }



    /**
     * 编辑商业服务人员
     * @param param
     * @return
     */
    public ObjectRestResponse updateBusService(SaveBusServiceParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(null == param){
            msg.setStatus(505);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getName())){
            msg.setStatus(506);
            msg.setMessage("姓名不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getMobilePhone())){
            msg.setStatus(507);
            msg.setMessage("手机号不能为空!");
            return msg;
        }else if(!StringUtils.isMobile(param.getMobilePhone())){
            msg.setStatus(508);
            msg.setMessage("请输入正确的手机号!");
            return msg;
        }
        if(!StringUtils.isEmpty(param.getEmail())
                && !StringUtils.checkEmail(param.getEmail())){
            msg.setStatus(509);
            msg.setMessage("请输入正确的Email!");
            return msg;
        }

        BusServiceInfo busServiceInfo = baseAppServerUserMapper.selectBusServiceInfoById(param.getId());
        BaseAppServerUser user = new BaseAppServerUser();
        if(busServiceInfo != null){
            try {
                BeanUtils.copyProperties(busServiceInfo ,user);
                user.setName(param.getName());
                user.setSex(param.getSex());
                user.setEmail(param.getEmail());
                user.setEnableStatus(param.getEnableStatus());
                if(param.getProfilePhotoList() != null & param.getProfilePhotoList().size() > 0){
                    if(StringUtils.isNotEmpty(param.getProfilePhotoList().get(0).getUrl())){
                        ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(param.getProfilePhotoList().get(0).getUrl(), DocPathConstant.SHOP);
                        if(restResponse.getStatus()==200){
                            user.setProfilePhoto(restResponse.getData()==null ? "" : (String)restResponse.getData());
                        }
                    }
                    //user.setProfilePhoto(param.getProfilePhotoList().get(0).getUrl());
                }else{
                    user.setProfilePhoto("");
                }
                user.setModifyBy(BaseContextHandler.getUserID());
                user.setModifyTime(new Date());
                if(baseAppServerUserMapper.updateByPrimaryKeySelective(user) < 0){
                    msg.setStatus(101);
                    msg.setMessage("编辑商业服务人员失败!");
                    return msg;
                }
            } catch (Exception e) {
                logger.error("编辑商业服务人员失败!,id为{}",e);
            }
        }else{
            msg.setStatus(102);
            msg.setMessage("编辑商业服务人员失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        //商业人员
        BaseAppServerUser baseAppServerUser = baseAppServerUserMapper.selectByPrimaryKey(user.getId());
        if (baseAppServerUser != null) {
            syncUserInfoTask.saveSyncUserInfo(baseAppServerUser, baseAppServerUser.getMobilePhone(),"2","2");
        }
        return msg;
    }
    /**
     * 查询住户列表
     * @param floorId
     * @param searchVal
     * @param page
     * @param limit
     * @author zl
     * @Date 2019/3/5 下午3:17
     * @return
     */
    public List<HouseholdVo> getHouseholdList(String houseId,String floorId, String searchVal, Integer page, Integer limit){
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        //String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<HouseholdVo> houseServiceVoList = baseAppServerUserMapper.selectHouseholdList(houseId,floorId,searchVal,startIndex,limit);
        return houseServiceVoList;
    }
    /**
     * 查询住户总数
     * @param floorId
     * @param searchVal
     * @author zl
     * @Date 2019/3/5 下午3:17
     * @return
     */
    public int  selectHouseholdCount(String houseId ,String floorId, String searchVal){
        int total = baseAppServerUserMapper.getHouseholdCount(houseId,floorId,searchVal);
        return total;
    }


    public String getProjectId(String userID) {
        return baseAppServerUserMapper.getProjectId(userID);
    }


	public ObjectRestResponse relieve(String id) {
		ObjectRestResponse response = new ObjectRestResponse();
		try {
			baseAppServerUserMapper.relieve(id,BaseContextHandler.getUserID());
			UserFaceInfo userFaceInfo = baseAppServerUserMapper.getUserFaceInfoById(id);
			log.info("调用删除人脸权限接口开始");
			ObjectRestResponse rest = noticeFeign.deleteFaceByUser(userFaceInfo);
			log.info("调用删除人脸权限接口结束,调用结果：" + JSONObject.toJSONString(rest));
		}catch (Exception e){
			e.printStackTrace();
			response.setStatus(502);
			response.setMessage("解除失败！");
		}
		return response;
	}


	public ObjectRestResponse checkUser(String phone){
        ObjectRestResponse response = new ObjectRestResponse();
        UserInfoVo  user =   baseAppServerUserMapper.checkUser(phone);
        if (user == null) {
            response.setStatus(501);
            response.setMessage("该手机号尚未注册‘回家’APP，请前往注册");
            return response;
        }
        response.setData(user.getId());
        return response;
    }

    public ObjectRestResponse getUserDetail(String id){
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            response.setStatus(101);
            response.setMessage("ID不能为空");
            return response;
        }
        UserInfoVo userInfo =   baseAppServerUserMapper.getUserDetail(id);
        response.setData(userInfo);
        return response;
    }

    public List<UserInfoVo> getUserList(String searchVal,String isOperation,Integer page,Integer limit) {
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<UserInfoVo> userInfoVos =  baseAppServerUserMapper.getUserList(searchVal,isOperation,startIndex,limit);
        if (userInfoVos.size() == 0) {
            userInfoVos = new ArrayList<>();
        }
        return userInfoVos;
    }

    public int getUserListTotal(String searchVal,String isOperation) {
        return baseAppServerUserMapper.getUserListTotal(searchVal,isOperation);
    }

    public ObjectRestResponse deleteUser(String id) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            response.setStatus(101);
            response.setMessage("ID不能为空");
            return response;
        }
        int result =  baseAppServerUserMapper.deleteUserOperation(id);
        if (result == 0) {
            response.setStatus(103);
            response.setMessage("删除失败");
            return response;
        }
        return response;
    }

    public ObjectRestResponse updateStatus(String id, String isOperation) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            response.setStatus(101);
            response.setMessage("ID不能为空");
            return response;
        }
        int result =  baseAppServerUserMapper.updateStatus(id,isOperation);
        if (result == 0) {
            response.setStatus(103);
            response.setMessage("操作失败");
            return response;
        }
        return response;
    }

    public ObjectRestResponse createUser(String phone) {
        ObjectRestResponse response = new ObjectRestResponse();
        UserInfoVo user = baseAppServerUserMapper.checkUser(phone);
        if ("1".equals(user.getIsOperation())) {
            response.setStatus(101);
            response.setMessage("该用户已添加");
            return response;
        }
        if("2".equals(user.getIsOperation())){
            response.setStatus(101);
            response.setMessage("该用户已被禁用");
            return response;
        }
        int result = baseAppServerUserMapper.setUserOperation(phone);
        if (result == 0) {
            response.setStatus(101);
            response.setMessage("保存失败");
            return response;
        }
        return response;
    }
}
