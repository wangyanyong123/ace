package com.github.wxiaoqi.security.jinmao.biz;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.BooleanUtil;
import com.github.wxiaoqi.security.common.util.Sha256PasswordEncoder;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.*;
import com.github.wxiaoqi.security.jinmao.feign.CodeFeign;
import com.github.wxiaoqi.security.jinmao.mapper.*;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.InputParam.AilSaveParam;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.InputParam.ManageSaveParam;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.InputParam.UpdateEnableParam;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.InputParam.WechatSaveParam;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.OutParam.*;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import com.github.wxiaoqi.security.jinmao.vo.postage.in.SaveParams;
import com.xxl.job.core.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 租户表
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-20 12:35:12
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class BaseTenantBiz extends BusinessBiz<BaseTenantMapper, BaseTenant> {
    private Logger logger = LoggerFactory.getLogger(BaseTenantBiz.class);
    private Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();
    @Autowired
    private BaseTenantMapper baseTenantMapper;
    @Autowired
    private BaseTenantProjectMapper baseTenantProjectMapper;
    @Autowired
    private BizSettlementAliMapper bizSettlementAliMapper;
    @Autowired
    private BizSettlementWechatMapper bizSettlementWechatMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BaseGroupMemberMapper baseGroupMemberMapper;
    @Autowired
    private CodeFeign codeFeign;
    @Autowired
    private BaseTenantPostageMapper baseTenantPostageMapper;
    @Autowired
    private BaseTenantProjectBiz baseTenantProjectBiz;
    @Autowired
    private BaseProjectBiz baseProjectBiz;

    /**
     * 查询公司管理列表
     * @param enableStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ResultManageVo> getManageList(String enableStatus,String searchVal,Integer page,Integer limit){
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
        List<ResultManageVo> manageList = baseTenantMapper.selectCompanyManageList(enableStatus,searchVal,startIndex,limit);
        return manageList;
    }

    /**
     * 根据搜索条件查询数量
     * @param enableStatus
     * @param searchVal
     * @return
     */
    public int selectCompanyManageCount(String enableStatus,String searchVal){
        int total = baseTenantMapper.selectCompanyManageCount(enableStatus,searchVal);
        return total;
    }


    /**
     * 保存公司管理
     * @param param
     */
    public ObjectRestResponse saveManageInfo(ManageSaveParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        //账号
        ObjectRestResponse objectRestResponse = codeFeign.getCode("tenantAccount","1","6","0");
        logger.info("生成账号处理结果："+objectRestResponse.getData());

        //效验关联的项目是否在其它数据中维护,提示该项目已在维护状态中
        String temp = baseTenantMapper .selectIsProjectByProjectId(param.getProjectId());
        String tenantId = null;
        if(temp != null){
            logger.error("该项目已在其他公司维护中！");
            msg.setStatus(101);
            msg.setMessage("该项目已在其他公司维护中！");
        return msg;
        }else{
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            BaseTenant tenant = new BaseTenant();
            tenantId = UUIDUtils.generateUuid();
            tenant.setId(tenantId);
            if(objectRestResponse.getStatus()==200){
                tenant.setAccount(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
            }
            tenant.setName(param.getName());
            tenant.setTenantType("1");
            tenant.setAddress(param.getAddress());
            tenant.setLicenceNo(param.getLicenceNo());
            tenant.setJuristicperson(param.getJuristicPerson());
            if(param.getSetupTime() != null){
                tenant.setSetupTime(sdf.parse(param.getSetupTime()));
            }
            tenant.setRegCapital(param.getRegCapital());
            tenant.setContactorName(param.getContactorName());
            tenant.setContactTel(param.getContactTel());
            tenant.setContactEmail(param.getContactEmail());
            tenant.setSummary(param.getSummary());
            tenant.setEnableStatus("1");
            tenant.setCrtUserId(BaseContextHandler.getUserID());
            tenant.setCrtTime(new Date());
            tenant.setTenantId(BaseContextHandler.getTenantID());
            tenant.setCenterCityName(param.getCityName());
            int total = baseTenantMapper.insertSelective(tenant);
            msg.setData(tenantId);
            if(total > 0){
                //插入关联项目
                BaseTenantProject paoject = new BaseTenantProject();
                paoject.setId(UUIDUtils.generateUuid());
                paoject.setTenantId(tenantId);
                paoject.setProjectId(param.getProjectId());
                paoject.setTimeStamp(new Date());
                paoject.setCreateBy(BaseContextHandler.getUserID());
                paoject.setCreateTime(new Date());
                if (baseTenantProjectMapper.insertSelective(paoject) < 0){
                    logger.error("保存关联数据失败,projectId为{}",param.getProjectId());
                    msg.setStatus(102);
                    msg.setMessage("保存关联数据失败");
                    return msg;
                }
            }
        } catch (ParseException e) {
            logger.error("处理时间异常",e);
        }
    }
       //通过apollo设置默认密码
        Config config = ConfigService.getConfig("ace-jinmao");
        String password = config.getProperty("company-account-default-pwd",null);
       //新建用户
        BaseUser user = new BaseUser();
        String userId = UUIDUtils.generateUuid();
        user.setId(userId);
        user.setUsername(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
        user.setPassword(encoder.encode(password));
        user.setName(param.getName());
        //user.setDepartId("d583e7de6d2d48b78fb3c7dcb180cb1f");
        user.setSex("未知");
        user.setType("1");
        user.setStatus("1");
        user.setCrtTime(new Date());
        user.setCrtUserId(BaseContextHandler.getUserID());
        user.setCrtUserName(BaseContextHandler.getUsername());
        user.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        user.setIsDisabled(BooleanUtil.BOOLEAN_FALSE);
        user.setTenantId(BaseContextHandler.getTenantID());
        user.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(userMapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            user.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        }
        userMapper.insertSelective(user);

       //租户授予用户
        BaseTenant tenant = baseTenantMapper.selectByPrimaryKey(tenantId);
        tenant.setOwner(userId);
        updateSelectiveById(tenant);
        user = userMapper.selectByPrimaryKey(userId);
        user.setTenantId(tenantId);
        userMapper.updateByPrimaryKeySelective(user);

       //关联用户
        BaseGroupMember member = new BaseGroupMember();
        member.setId(UUIDUtils.generateUuid());
        member.setGroupId(BusinessConstant.getProjectmanager());
        member.setUserId(userId);
        member.setTenantId(BaseContextHandler.getTenantID());
        member.setCrtTime(new Date());
        member.setCrtUser(BaseContextHandler.getUserID());
        member.setCrtName(BaseContextHandler.getUsername());
        baseGroupMemberMapper.insertSelective(member);


        msg.setMessage("Operation succeed!");
        return msg;
    }

    /**
     *
     * 保存中心城市账号
     */
    public ObjectRestResponse saveCenterManageInfo(ManageSaveParam param){
        ObjectRestResponse msg = new ObjectRestResponse();

        Assert.hasLength(param.getProjectId(),"请选择项目");
        Assert.hasLength(param.getCityName(),"请选择中心城市");
        //效验关联的项目是否在其它数据中维护,提示该项目已在维护状态中
        String[] projectIds = param.getProjectId().split(",");
        List<String> projectIdList = baseTenantProjectBiz.existedProjectIdList(AceDictionary.TENANT_TYPE_CENTER, projectIds);
        if(CollectionUtils.isNotEmpty(projectIdList)){
            msg.setStatus(101);
            msg.setMessage("存在项目已在其他中心城市账号中！");
            return msg;
        }
            String tenantId = UUIDUtils.generateUuid();
            BaseTenant tenant = new BaseTenant();
            tenant.setId(tenantId);
            //账号
            ObjectRestResponse objectRestResponse = codeFeign.getCode("tenantAccount","1","6","0");
            logger.info("生成账号处理结果："+objectRestResponse.getData());
            if(objectRestResponse.getStatus()==200){
                tenant.setAccount(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
            }
            tenant.setName(param.getCityName()+"中心城市账号");
            tenant.setTenantType(AceDictionary.TENANT_TYPE_CENTER);
            tenant.setAddress(param.getAddress());
            tenant.setLicenceNo(param.getLicenceNo());
            tenant.setJuristicperson(param.getJuristicPerson());
            if(StringUtils.isNotEmpty(param.getSetupTime())){
                tenant.setSetupTime(DateUtil.parseDate(param.getSetupTime()));
            }
            tenant.setRegCapital(StringUtils.isEmpty(param.getRegCapital())?"0":param.getRegCapital());
            tenant.setContactorName(param.getContactorName());
            tenant.setContactTel(param.getContactTel());
            tenant.setContactEmail(param.getContactEmail());
            tenant.setSummary(param.getSummary());
            tenant.setEnableStatus("1");
            tenant.setCrtUserId(BaseContextHandler.getUserID());
            tenant.setCrtTime(new Date());
            tenant.setTenantId(BaseContextHandler.getTenantID());
            tenant.setCenterCityName(param.getCityName());
            int total = baseTenantMapper.insertSelective(tenant);
            msg.setData(tenantId);
            if(total > 0){
                //插入关联项目
                for (String projectId : projectIds) {
                    BaseTenantProject paoject = new BaseTenantProject();
                    paoject.setId(UUIDUtils.generateUuid());
                    paoject.setTenantId(tenantId);
                    paoject.setProjectId(projectId);
                    paoject.setTimeStamp(new Date());
                    paoject.setCreateBy(BaseContextHandler.getUserID());
                    paoject.setCreateTime(new Date());
                    baseTenantProjectMapper.insertSelective(paoject);
                }

            }

       //新建用户
        BaseUser user = new BaseUser();
        String userId = UUIDUtils.generateUuid();
        user.setId(userId);
        user.setUsername(tenant.getAccount());
        Config config = ConfigService.getConfig("ace-jinmao");
        String password = config.getProperty("central-city-account-default-pwd",null);
        user.setPassword(encoder.encode(password));
        user.setName(param.getCityName()+"中心城市");
        //user.setDepartId("d583e7de6d2d48b78fb3c7dcb180cb1f");
        user.setSex("未知");
        user.setType("1");
        user.setStatus("1");
        user.setCrtTime(new Date());
        user.setCrtUserId(BaseContextHandler.getUserID());
        user.setCrtUserName(BaseContextHandler.getUsername());
        user.setIsDeleted(BooleanUtil.BOOLEAN_FALSE);
        user.setIsDisabled(BooleanUtil.BOOLEAN_FALSE);
        user.setTenantId(BaseContextHandler.getTenantID());
        user.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        // 如果非超级管理员,无法修改用户的租户信息
        if (BooleanUtil.BOOLEAN_FALSE.equals(userMapper.selectByPrimaryKey(BaseContextHandler.getUserID()).getIsSuperAdmin())) {
            user.setIsSuperAdmin(BooleanUtil.BOOLEAN_FALSE);
        }
        userMapper.insertSelective(user);

       //租户授予用户
        tenant.setOwner(userId);
        updateSelectiveById(tenant);
        user = userMapper.selectByPrimaryKey(userId);
        user.setTenantId(tenantId);
        userMapper.updateByPrimaryKeySelective(user);

       //关联用户
        BaseGroupMember member = new BaseGroupMember();
        member.setId(UUIDUtils.generateUuid());
        member.setGroupId(BusinessConstant.getProjectmanager());
        member.setUserId(userId);
        member.setTenantId(BaseContextHandler.getTenantID());
        member.setCrtTime(new Date());
        member.setCrtUser(BaseContextHandler.getUserID());
        member.setCrtName(BaseContextHandler.getUsername());
        baseGroupMemberMapper.insertSelective(member);

        msg.setMessage("Operation succeed!");
        return msg;
    }


    /**
     * 保存支付宝设置
     * @param param
     */
    public ObjectRestResponse saveAilInfo(AilSaveParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        BizSettlementAli ail = new BizSettlementAli();
        String isTenantId = null;
        isTenantId = BaseContextHandler.getTenantID();
        String cid = BusinessConstant.getTenantId();
        if( cid.equals(isTenantId) && param.getIsPayInMall() != null){
            ail.setIsPayInMall("1");
            ail.setId(UUIDUtils.generateUuid());
            ail.setTenantId(isTenantId);
            ail.setAlipayAccountNo(param.getAlipayAccountNo());
            ail.setAlipayAccountName(param.getAlipayAccountName());
            ail.setAlipayPartner(param.getAlipayPartner());
            ail.setAlipayKey(param.getAlipayKey());
            ail.setRsa(param.getRsa());
            ail.setAliPublicKey(param.getAliPublicKey());
            ail.setTimeStamp(new Date());
            ail.setCreateBy(BaseContextHandler.getUserID());
            ail.setCreateTime(new Date());
        }else {
            ail.setId(UUIDUtils.generateUuid());
            ail.setTenantId(param.getTenantId());
            ail.setAlipayAccountNo(param.getAlipayAccountNo());
            ail.setAlipayAccountName(param.getAlipayAccountName());
            ail.setAlipayPartner(param.getAlipayPartner());
            ail.setAlipayKey(param.getAlipayKey());
            ail.setRsa(param.getRsa());
            ail.setAliPublicKey(param.getAliPublicKey());
            ail.setTimeStamp(new Date());
            ail.setCreateBy(BaseContextHandler.getUserID());
            ail.setCreateTime(new Date());
        }
        ail.setAppAliPublicKey(param.getAppAliPublicKey());
        ail.setAppId(param.getAppId());
        ail.setAppRsa2(param.getAppRsa());
        if (bizSettlementAliMapper.insertSelective(ail) < 0){
            logger.error("保存支付宝设置失败,tenantId为{}",param.getTenantId());
            msg.setStatus(101);
            return msg;
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }

    /**
     * 保存微信设置
     * @param param
     */
    public ObjectRestResponse saveWechatInfo(WechatSaveParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        BizSettlementWechat wechat = new BizSettlementWechat();
        String isTenantId = null;
        isTenantId = BaseContextHandler.getTenantID();
        String cid = BusinessConstant.getTenantId();
        if(cid.equals(isTenantId) && param.getIsPayInMall() != null){
            wechat.setIsPayInMall("1");
            wechat.setTenantId(isTenantId);
            wechat.setId(UUIDUtils.generateUuid());
            wechat.setWechatAppid(param.getWechatAppid());
            wechat.setWechatCode(param.getWechatCode());
            wechat.setWechatCertificate(param.getWechatCertificate());
            wechat.setWechatFee(Double.parseDouble(param.getWechatFee()));
            wechat.setWechatAccount(param.getWechatAccount());
            wechat.setWechatKey(param.getWechatKey());
            wechat.setTimeStamp(new Date());
            wechat.setCreateBy(BaseContextHandler.getUserID());
            wechat.setCreateTime(new Date());
        }else {
            wechat.setId(UUIDUtils.generateUuid());
            wechat.setTenantId(param.getTenantId());
            wechat.setWechatAppid(param.getWechatAppid());
            wechat.setWechatCode(param.getWechatCode());
            wechat.setWechatCertificate(param.getWechatCertificate());
            wechat.setWechatFee(Double.parseDouble(param.getWechatFee()));
            wechat.setWechatAccount(param.getWechatAccount());
            wechat.setWechatKey(param.getWechatKey());
            wechat.setTimeStamp(new Date());
            wechat.setCreateBy(BaseContextHandler.getUserID());
            wechat.setCreateTime(new Date());
        }
        if (bizSettlementWechatMapper.insertSelective(wechat) < 0){
            logger.error("保存微信设置失败,tenantId为{}",param.getTenantId());
            msg.setStatus(101);
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     *
     * @param param
     */
    public ObjectRestResponse updateEnableStatus(UpdateEnableParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        //启用时,判断该项目是否在其它数据中维护,如果维护过则启用失败
       if("1".equals(param.getEnableStatus())){
           BaseTenant tenant = baseTenantMapper.selectIsProjectInfo(param.getId());
           if(tenant != null){
               msg.setStatus(101);
               msg.setMessage("启用失败,该项目已在其它租户维护中！");
               return msg;
           }else{
               param.setUpdUserId(BaseContextHandler.getUserID());
               if (baseTenantMapper.updateTenantEnableStatus(param) < 0){
                   logger.error("修改启用失败,id为{}",param.getId());
                   msg.setStatus(102);
                   msg.setMessage("启用失败！");
                   return msg;
               }else{
                  String tenantId =  baseTenantMapper.selectBaseUserId(param.getId());
                  baseTenantMapper.updateBaseUserStatus("0",BaseContextHandler.getUserID(),tenantId);
               }
           }
       }else{
           param.setUpdUserId(BaseContextHandler.getUserID());
           if (baseTenantMapper.updateTenantEnableStatus(param) < 0){
               logger.error("修改禁用失败,id为{}",param.getId());
               msg.setStatus(103);
               msg.setMessage("禁用失败！");
               return msg;
           }else{
               String tenantId =  baseTenantMapper.selectBaseUserId(param.getId());
               baseTenantMapper.updateBaseUserStatus("1",BaseContextHandler.getUserID(),tenantId);
           }
       }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 查询商城支付管理详情
     * @param id
     * @return
     */
    public List<ResultTenantManageInfo> getPayMallInfo(String id){
        List<ResultTenantManageInfo> list  = new ArrayList<>();
        ResultTenantManageInfo info = new ResultTenantManageInfo();
        ResultAilVo ali = bizSettlementAliMapper.selectAilInfo(id);
        ResultWechatVo wechatVo = bizSettlementWechatMapper.selectWechatInfo(id);
        if(ali != null){
            info.setAli(ali);
        }else{
            ali = new ResultAilVo();
            info.setAli(ali);
        }
        if(wechatVo != null){
            info.setWechat(wechatVo);
        }else{
            wechatVo = new ResultWechatVo();
            info.setWechat(wechatVo);
        }
        list.add(info);
        return list;
    }
    /**
     * 查询公司管理详情
     * @param id
     * @return
     */
    public List<ResultTenantManageInfo> getTenantManageInfo(String id){
        List<ResultTenantManageInfo> list  = new ArrayList<>();
        ResultTenantManageInfo info = new ResultTenantManageInfo();
        ResultTenantVo tenant = baseTenantMapper.selectTenantInfo(id);
        ResultAilVo ali = bizSettlementAliMapper.selectAilInfo(id);
        ResultWechatVo wechatVo = bizSettlementWechatMapper.selectWechatInfo(id);
        info.setTenant(tenant);
        if(ali != null){
            info.setAli(ali);
        }
        if(wechatVo != null){
            info.setWechat(wechatVo);
        }
        list.add(info);
        return list;
    }

    /**
     * 更新支付宝设置
     * @param param
     */
    public ObjectRestResponse updateAilInfo(AilSaveParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        ResultAilVo returnAil = bizSettlementAliMapper.selectAilInfo(param.getTenantId());
        BizSettlementAli ail = new BizSettlementAli();
        if(returnAil != null){
            try {
                BeanUtils.copyProperties(returnAil,ail);
                ail.setTenantId(param.getTenantId());
                ail.setAlipayAccountNo(param.getAlipayAccountNo());
                ail.setAlipayAccountName(param.getAlipayAccountName());
                //ail.setAlipayPartner(param.getAlipayPartner());
                //ail.setAlipayKey(param.getAlipayKey());
                //ail.setRsa(param.getRsa());
                //ail.setAliPublicKey(param.getAliPublicKey());
                ail.setAppAliPublicKey(param.getAppAliPublicKey());
                ail.setAppId(param.getAppId());
                ail.setAppRsa2(param.getAppRsa());
                ail.setTimeStamp(new Date());
                ail.setModifyBy(BaseContextHandler.getUserID());
                ail.setModifyTime(new Date());
                if(bizSettlementAliMapper.updateByPrimaryKeySelective(ail) < 0){
                    logger.error("更新支付宝设置失败,id为{}",returnAil.getId());
                    msg.setStatus(101);
                    msg.setMessage("更新支付宝设置失败!");
                    return msg;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 更新微信设置
     * @param param
     */
    public ObjectRestResponse updateWechatInfo(WechatSaveParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        ResultWechatVo resultWechat = bizSettlementWechatMapper.selectWechatInfo(param.getTenantId());
        BizSettlementWechat wechat = new BizSettlementWechat();
        if(resultWechat != null) {
            try {
                BeanUtils.copyProperties(resultWechat,wechat);
                wechat.setTenantId(param.getTenantId());
                wechat.setWechatAppid(param.getWechatAppid());
                wechat.setWechatCode(param.getWechatCode());
                wechat.setWechatCertificate(param.getWechatCertificate());
                wechat.setWechatFee(Double.parseDouble(param.getWechatFee()));
                wechat.setWechatAccount(param.getWechatAccount());
                wechat.setWechatKey(param.getWechatKey());
                wechat.setTimeStamp(new Date());
                wechat.setModifyTime(new Date());
                wechat.setModifyBy(BaseContextHandler.getUserID());
                if (bizSettlementWechatMapper.updateByPrimarySelective(wechat) < 0) {
                    logger.error("更新微信设置失败,id为{}", resultWechat.getId());
                    msg.setStatus(101);
                    msg.setMessage("更新微信设置失败!");
                    return msg;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     *更新租户信息
     * @param param
     */
    public ObjectRestResponse updateTenamtInfo(ManageSaveParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ResultTenantVo resultTenant = baseTenantMapper.selectTenantInfo(param.getId());
        BaseTenant tenant = new BaseTenant();
        if(resultTenant != null) {
            try {
                BeanUtils.copyProperties(resultTenant,tenant);
                tenant.setName(param.getName());
                tenant.setAddress(param.getAddress());
                tenant.setLicenceNo(param.getLicenceNo());
                tenant.setJuristicperson(param.getJuristicPerson());
                if(param.getSetupTime() != null){
                    tenant.setSetupTime(sdf.parse(param.getSetupTime()));
                }
                tenant.setRegCapital(param.getRegCapital());
                tenant.setContactorName(param.getContactorName());
                tenant.setContactTel(param.getContactTel());
                tenant.setContactEmail(param.getContactEmail());
                tenant.setSummary(param.getSummary());
                tenant.setCenterCityName(param.getCityName());
                if (baseTenantMapper.updateByPrimaryKeySelective(tenant) < 0) {
                    logger.error("更新租户信息失败,id为{}", resultTenant.getId());
                    msg.setStatus(101);
                    msg.setMessage("更新租户信息失败!");
                    return msg;
                }else{
                    BaseUser baseUser = baseTenantMapper.selectWebUserInfo(param.getId());
                    if(baseUser != null){
                        BaseUser user = new BaseUser();
                        user.setId(baseUser.getId());
                        user.setName(param.getName());
                        user.setUpdTime(new Date());
                        user.setUpdUserId(BaseContextHandler.getUserID());
                        userMapper.updateByPrimaryKeySelective(user);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 更新中心城市管理账号信息
     */
    public ObjectRestResponse updateCenterTenantInfo(ManageSaveParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        ResultTenantVo resultTenant = baseTenantMapper.selectTenantInfo(param.getId());
        BaseTenant tenant = new BaseTenant();
        if(resultTenant != null) {
            BeanUtils.copyProperties(resultTenant,tenant);
            tenant.setName(param.getName());
            tenant.setAddress(param.getAddress());
            tenant.setLicenceNo(param.getLicenceNo());
            tenant.setJuristicperson(param.getJuristicPerson());
            if(StringUtils.isNotEmpty(param.getSetupTime())){
                tenant.setSetupTime(DateUtil.parseDate(param.getSetupTime()));
            }
            tenant.setRegCapital(param.getRegCapital());
            tenant.setContactorName(param.getContactorName());
            tenant.setContactTel(param.getContactTel());
            tenant.setContactEmail(param.getContactEmail());
            tenant.setSummary(param.getSummary());
            tenant.setCenterCityName(param.getCityName());
             baseTenantMapper.updateByPrimaryKeySelective(tenant);
            BaseUser baseUser = baseTenantMapper.selectWebUserInfo(param.getId());
            if(baseUser != null){
                BaseUser user = new BaseUser();
                user.setId(baseUser.getId());
                user.setName(param.getName());
                user.setUpdTime(new Date());
                user.setUpdUserId(BaseContextHandler.getUserID());
                userMapper.updateByPrimaryKeySelective(user);
            }
            Assert.hasLength(param.getProjectId(),"请设置项目");
            String[] projectIds = param.getProjectId().split(",");
            List<String> projectIdList = baseTenantProjectBiz.findProjectIdList(param.getId());
            for (String projectId : projectIds) {
                if(projectIdList.contains(projectId)){
                    projectIdList.remove(projectId);
                }else{
                    BaseTenantProject paoject = new BaseTenantProject();
                    paoject.setId(UUIDUtils.generateUuid());
                    paoject.setTenantId(param.getId());
                    paoject.setProjectId(projectId);
                    paoject.setTimeStamp(new Date());
                    paoject.setCreateBy(BaseContextHandler.getUserID());
                    paoject.setCreateTime(new Date());
                    baseTenantProjectMapper.insertSelective(paoject);
                }
            }
            if(CollectionUtils.isNotEmpty(projectIdList)){
                baseTenantProjectMapper.deleteByTenantId(param.getId(),projectIdList,BaseContextHandler.getUserID(),new Date());
            }



        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 查询当前用户所属角色
     * @return
     */
    public ObjectRestResponse<UserInfo> getUserInfo(){
        UserInfo userInfo = baseTenantMapper.selectRoleTypeByUser(BaseContextHandler.getUserID());
        if(userInfo == null){
            userInfo = new UserInfo();
        }
        return ObjectRestResponse.ok(userInfo);
    }


    public ObjectRestResponse savePostageInfo(SaveParams params) {
        ObjectRestResponse response = new ObjectRestResponse();
        baseTenantPostageMapper.deletePostage(BaseContextHandler.getTenantID());
        if (new BigDecimal(params.getPostage()).compareTo(BigDecimal.ZERO) == -1) {
            response.setStatus(101);
            response.setMessage("请输入正确的区间金额");
            return response;
        }
        if (params.getContent().size() >= 1){
            List<Map<String, String>> content = params.getContent();
            for (int i = 0; i <= content.size() - 1 ; i++) {
                String endAmount = content.get(i).get("endAmount");
                String startAmount = content.get(i).get("startAmount");
//                String startAmountNext = content.get(i+1).get("startAmount");
                String postage = content.get(i).get("postage");
                if (new BigDecimal(postage).compareTo(BigDecimal.ZERO) == -1) {
                    response.setStatus(101);
                    response.setMessage("请输入正确的区间金额");
                    return response;
                }
                if ( startAmount.equals("") || endAmount.equals("") || new BigDecimal(endAmount).compareTo(new BigDecimal(startAmount))== 0
                        ||  new BigDecimal(endAmount).compareTo(new BigDecimal(startAmount))== -1
                        ) {
                    response.setStatus(101);
                    response.setMessage("请输入正确的区间金额");
                    return response;
                }
            }
            for (Map<String, String> map : params.getContent()) {
                BaseTenantPostage baseTenantPostage = new BaseTenantPostage();
                baseTenantPostage.setTenantId(BaseContextHandler.getTenantID());
                baseTenantPostage.setStartAmount(new BigDecimal(map.get("startAmount")));
                baseTenantPostage.setEndAmount(new BigDecimal(map.get("endAmount")));
                baseTenantPostage.setPostage(new BigDecimal(map.get("postage")));
                baseTenantPostage.setId(UUIDUtils.generateUuid());
                baseTenantPostage.setTimeStamp(new Date());
                baseTenantPostage.setCreateBy(BaseContextHandler.getUserID());
                baseTenantPostage.setCreateTime(new Date());
                baseTenantPostageMapper.insertSelective(baseTenantPostage);
            }

        }
        BaseTenantPostage baseTenantPostage = new BaseTenantPostage();
        baseTenantPostage.setTenantId(BaseContextHandler.getTenantID());
        baseTenantPostage.setStartAmount(new BigDecimal(params.getStartAmount()));
        baseTenantPostage.setEndAmount(new BigDecimal(params.getEndAmount()));
        baseTenantPostage.setPostage(new BigDecimal(params.getPostage()));
        baseTenantPostage.setId(UUIDUtils.generateUuid());
        baseTenantPostage.setTimeStamp(new Date());
        baseTenantPostage.setCreateBy(BaseContextHandler.getUserID());
        baseTenantPostage.setCreateTime(new Date());
        baseTenantPostageMapper.insertSelective(baseTenantPostage);

        return response;
    }

    public ObjectRestResponse delPostageInfo(String id) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (baseTenantMapper.delPostageInfo(id) < 0) {
            response.setStatus(101);
            response.setMessage("删除失败");
        }
        return response;
    }

    public ObjectRestResponse getCenterCity() {
        ObjectRestResponse response = new ObjectRestResponse();
        List<CenterCityVo> centerCity = baseTenantMapper.getCenterCity();
        if (centerCity == null || centerCity.size() == 0) {
            centerCity = new ArrayList<>();
        }
        response.setData(centerCity);
        return response;
    }

    public List<ProjectListVo> getUnSelectedProjectList(String tenantType) {
        List<ProjectListVo> list = baseProjectBiz.getProjectListForPC();
        if(StringUtils.isEmpty(tenantType)){
            return list;
        }
        List<String> projectIdList = baseTenantProjectBiz.existedProjectIdList(tenantType, null);
        return list.stream().filter(item-> !projectIdList.contains(item.getId())).collect(Collectors.toList());
    }
}
