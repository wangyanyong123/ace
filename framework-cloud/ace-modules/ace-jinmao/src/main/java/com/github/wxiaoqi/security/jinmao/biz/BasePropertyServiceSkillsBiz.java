package com.github.wxiaoqi.security.jinmao.biz;

import com.alibaba.fastjson.JSON;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.MsgThemeConstants;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.Sha256PasswordEncoder;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.*;
import com.github.wxiaoqi.security.jinmao.feign.SmsFeign;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.*;
import com.github.wxiaoqi.security.jinmao.task.SyncUserInfoTask;
import com.github.wxiaoqi.security.jinmao.task.util.SyncHouseKeeperParams;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.Service.InputParam.SaveServiceParam;
import com.github.wxiaoqi.security.jinmao.vo.Service.InputParam.SaveSrviceGroupParam;
import com.github.wxiaoqi.security.jinmao.vo.Service.OutParam.*;
import com.github.wxiaoqi.security.jinmao.vo.houseKeeper.BuildInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 物业人员服务技能表
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-21 13:55:50
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class BasePropertyServiceSkillsBiz extends BusinessBiz<BasePropertyServiceSkillsMapper, BasePropertyServiceSkills> {

    private Logger logger = LoggerFactory.getLogger(BasePropertyServiceSkillsBiz.class);
    private Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();
    @Autowired
    private BasePropertyServiceSkillsMapper basePropertyServiceSkillsMapper;
    @Autowired
    private BasePropertyServiceGroupMapper basePropertyServiceGroupMapper;
    @Autowired
    private BaseAppServerUserMapper baseAppServerUserMapper;
    @Autowired
    private BasePropertyServiceAreaMapper basePropertyServiceAreaMapper;
    @Autowired
    private BaseTenantProjectMapper baseTenantProjectMapper;
    @Autowired
    private SmsFeign smsFeign;
    @Autowired
    private BizProductMapper bizProductMapper;
    @Autowired
    private BaseTenantProjectMapper tenantProjectMapper;
    @Autowired
    private BaseAppHousekeeperAreaBiz baseAppHousekeeperAreaBiz;

    @Autowired
    private BaseGroupMemberMapper baseGroupMemberMapper;
    @Autowired
    private BaseAppServerUserTenantIdMapper baseAppServerUserTenantMapper;

    @Autowired
    private SyncUserInfoTask syncUserInfoTask;

    @Autowired
    private BaseAppHousekeeperAreaMapper baseAppHousekeeperAreaMapper;
    @Autowired
    private ToolFegin toolFeign;



    /**
     * 查询技能列表
     * @param searchVal
     * @param ids
     * @return
     */
    public List<ResultSkillVo> getSkillList(String searchVal,String ids){
        List<String> idList = null;
        if(!StringUtils.isEmpty(ids)){
            idList = Arrays.asList(ids.split(","));
        }
        List<ResultSkillVo> skillList = basePropertyServiceSkillsMapper.selectSkillList(searchVal,idList);
        return skillList;
    }


    /**
     * 查询物业分类树
     * @return
     */
    public List<ResultServiceVo> getServiceTreeList(){
        List<ResultServiceVo> resultVo = new ArrayList<>();
        ResultServiceVo servicevo = new ResultServiceVo();
        List<ResultServiceTreeVo> serviceTreeList = basePropertyServiceGroupMapper.selectServiceTreeList(BaseContextHandler.getTenantID());
        if (serviceTreeList != null) {
            List<ServiceTree> trees = new ArrayList<>();
            serviceTreeList.forEach(service -> {
                trees.add(new ServiceTree(service.getId(),service.getPid(),service.getName()));
            });
            List<ServiceTree> treesTemp = TreeUtil.bulid(trees, "-1", null);
            servicevo.setChildren(treesTemp);
        }
        resultVo.add(servicevo);
        return resultVo;
    }

    /**
     *添加物业人员分类
     * @param param
     * @return
     */
    public ObjectRestResponse saveSrviceGroup(SaveSrviceGroupParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        BasePropertyServiceGroup service = new BasePropertyServiceGroup();
        service.setId(UUIDUtils.generateUuid());
        if(StringUtils.isEmpty(param.getId())){
            service.setPid("-1");
        }else{
            service.setPid(param.getId());
        }
        service.setName(param.getName());
        service.setTenantId(BaseContextHandler.getTenantID());
        service.setTimeStamp(new Date());
        service.setCreateBy(BaseContextHandler.getUserID());
        service.setCreateTime(new Date());
        if(basePropertyServiceGroupMapper.insertSelective(service) < 0){
            msg.setStatus(101);
            msg.setMessage("添加物业人员分类失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 查询物业分类详情
     * @param id
     * @return
     */
    public List<ResultServiceInfoVo> getServiceGroupInfo(String id){
        List<ResultServiceInfoVo> resultvo = new ArrayList<>();
        ResultServiceInfoVo info = new ResultServiceInfoVo();
        ResultServiceTreeVo serviceInfo =  basePropertyServiceGroupMapper.selectPidById(id);
        if(serviceInfo != null){
            ResultServiceTreeVo pname = basePropertyServiceGroupMapper.selectNameByPid(serviceInfo.getPid());
            info.setId(serviceInfo.getId());
            info.setName(serviceInfo.getName());
            if(pname != null){
                info.setpName(pname.getName());
                info.setPid(pname.getId());
            }
        }
        resultvo.add(info);
        return resultvo;
    }


    /**
     * 编辑物业人员分类
     * @param param
     * @return
     */
    public ObjectRestResponse updateServiceGroup(SaveSrviceGroupParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        BasePropertyServiceGroup service = new BasePropertyServiceGroup();
        ResultServiceTreeVo serviceInfo =  basePropertyServiceGroupMapper.selectPidById(param.getId());
        if(serviceInfo != null){
            try {
                BeanUtils.copyProperties(serviceInfo, service);
                service.setPid(param.getPid());
                service.setName(param.getName());
                service.setTimeStamp(new Date());
                service.setModifyBy(BaseContextHandler.getUserID());
                service.setModifyTime(new Date());
                if(basePropertyServiceGroupMapper.updateByPrimaryKeySelective(service) < 0){
                    msg.setStatus(101);
                    msg.setMessage("编辑物业人员分类失败!");
                    return msg;
                }
            }  catch (Exception e) {
                logger.error("编辑物业人员分类失败!",e);
            }
        }else{
            msg.setStatus(102);
            msg.setMessage("编辑物业人员分类失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }



    /**
     * 删除物业分类
     * @param id
     * @return
     */
    public ObjectRestResponse delServiceGroupInfo(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(basePropertyServiceGroupMapper.delServiceGroupInfo(id,BaseContextHandler.getUserID()) < 0){
            msg.setStatus(101);
            msg.setMessage("删除物业分类失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询物业管理人员列表
     * @param enableStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ResultServiceListVo>  getServiceList(String enableStatus, String searchVal,Integer page,Integer limit){
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
        //查询当前物业分类id是否是顶级
//        String pid = basePropertyServiceGroupMapper.selectIsAllServiceBySid(sid);
//        if("-1".equals(pid)){
//            sid = null;
//        }
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<ResultServiceListVo> serviceList = baseAppServerUserMapper.selectServiceList(type,BaseContextHandler.getTenantID(),
                enableStatus, searchVal, startIndex, limit);
        for (ResultServiceListVo service : serviceList){
            StringBuilder str = new StringBuilder();
            if("1".equals(service.getCustomer())){
                str.append("客服"+",");
            }
            if("1".equals(service.getHouseKeeper())){
                str.append("管家"+",");
            }
            if("1".equals(service.getService())){
                str.append("工程人员"+",");
            }
            if(str.length() > 0){
                String temp = str.substring(0, str.length() - 1);
                service.setType(temp);
                temp = "";
            }
            List<ResultSkillVo> skillList = basePropertyServiceSkillsMapper.selectSkillNameById(service.getId());
            if(skillList != null && skillList.size()> 0){
                String skillName = "";
                StringBuilder resultEva = new StringBuilder();
                for (ResultSkillVo name : skillList){
                    resultEva.append(name.getSkilName() + ",");
                }
                skillName = resultEva.substring(0, resultEva.length() - 1);
                service.setSkills(skillName);
            }
        }
        return serviceList;
    }

    /**
     * 根据条件查询物业人员数量
     * @param enableStatus
     * @param searchVal
     * @return
     */
    public int selectServiceCount(String enableStatus, String searchVal){
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        int total = baseAppServerUserMapper.selectServiceCount(type,BaseContextHandler.getTenantID(),enableStatus,searchVal);
        return total;
    }


    /**
     * 保存物业人员
     * @param param
     * @return
     */
    public ObjectRestResponse saveService(SaveServiceParam param){
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
        }else if(!com.github.wxiaoqi.security.common.util.StringUtils.isMobile(param.getMobilePhone())){
            msg.setStatus(508);
            msg.setMessage("请输入正确的手机号!");
            return msg;
        }
        if(!StringUtils.isEmpty(param.getEmail())
                && !com.github.wxiaoqi.security.common.util.StringUtils.checkEmail(param.getEmail())){
            msg.setStatus(509);
            msg.setMessage("请输入正确的Email!");
            return msg;
        }
        for (String personType : param.getPersonMold()){
            if("3".equals(personType)){
                if(param.getSkillId() ==null || param.getSkillId().size()==0){
                    msg.setStatus(511);
                    msg.setMessage("技能不能为空!");
                    return msg;
                }
                if(param.getBuildId() == null || param.getBuildId().size() == 0){
                    msg.setStatus(512);
                    msg.setMessage("服务范围不能为空!");
                    return msg;
                }
            }
        }
        //是否是管家
        String isHousekeeper = "0";
        BaseAppServerUser user = new BaseAppServerUser();
        String id = null;
        String projectId = baseTenantProjectMapper.selectProjectByTenantId(BaseContextHandler.getTenantID());
        String msgTheme = "";
        if(StringUtils.isEmpty(BaseContextHandler.getTenantID())){
            msg.setStatus(501);
            msg.setMessage("未获取到当前用户的租户id!");
            return msg;
        }
        //判断用户是否已存在
        BaseAppServerUser serverUserTenant = baseAppServerUserMapper.getUserByPhone(param.getMobilePhone(),BaseContextHandler.getTenantID());
        BaseAppServerUser appServerUser = baseAppServerUserMapper.selectIsUserByPhone(param.getMobilePhone());
        if(serverUserTenant != null && serverUserTenant.getIsBusiness().equals("1"))  {
            msg.setStatus(503);
            msg.setMessage("已经添加过该用户");
            return msg;
        } else if(appServerUser != null && appServerUser.getIsBusiness().equals("1")){
            msg.setStatus(503);
            msg.setMessage("该用户已是商业人员");
            return msg;
        } else {
            user = new BaseAppServerUser();
            if (param.getProfilePhoto() != null && param.getProfilePhoto().size() > 0) {
                for (ImgInfo imgInfo : param.getProfilePhoto()) {
                    if(StringUtils.isNotEmpty(imgInfo.getUrl())){
                        ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(imgInfo.getUrl(), DocPathConstant.PROPERTY);
                        if(objectRestResponse.getStatus()==200){
                            user.setProfilePhoto(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                        }
                    }
                    //user.setProfilePhoto(imgInfo.getUrl());
                }
            } else {
                user.setProfilePhoto("");
            }
            String seniorityPhoto = "";
            StringBuilder resultEva = new StringBuilder();
            if (param.getSeniorityPhoto() != null && param.getSeniorityPhoto().size() > 0) {
                for (ImgInfo imgInfo : param.getSeniorityPhoto()) {
                    resultEva.append(imgInfo.getUrl() + ",");
                }
                seniorityPhoto = resultEva.substring(0, resultEva.length() - 1);
                if(StringUtils.isNotEmpty(seniorityPhoto)){
                    ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(seniorityPhoto, DocPathConstant.PROPERTY);
                    if(objectRestResponse.getStatus()==200){
                        user.setSeniorityPhoto(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                    }
                }
                //user.setSeniorityPhoto(seniorityPhoto);

            } else {
                user.setSeniorityPhoto("");
            }
            Config config = ConfigService.getConfig("ace-jinmao");
            String password = config.getProperty("property-staff-default-pwd",null);
            user.setName(param.getName());
            user.setPassword(encoder.encode(password));
            user.setSex(param.getSex());
            user.setMobilePhone(param.getMobilePhone());
            user.setEmail(param.getEmail());
            user.setEnableStatus(param.getEnableStatus());
            for (String personType : param.getPersonMold()) {
                if ("1".equals(personType)) {
                    user.setIsCustomer("1");
                } else if ("2".equals(personType)) {
                    user.setIsHousekeeper("1");
                    isHousekeeper = "1";
                } else if ("3".equals(personType)) {
                    user.setIsService("1");
                }
            }
            id = UUIDUtils.generateUuid();
            if (appServerUser == null){
                user.setTenantId(BaseContextHandler.getTenantID());
                user.setId(id);
                user.setCreateBy(BaseContextHandler.getUserID());
                user.setCreateTime(new Date());
                baseAppServerUserMapper.insertSelective(user);
                //维护人员多个公司数据
                BaseAppServerUserTenantId baseAppServerUserTenant = new BaseAppServerUserTenantId();
                baseAppServerUserTenant.setId(id);
                baseAppServerUserTenant.setTenantId(BaseContextHandler.getTenantID());
                baseAppServerUserTenant.setCreateBy(BaseContextHandler.getUserID());
                baseAppServerUserTenant.setCreateTime(new Date());
                baseAppServerUserTenantMapper.insertSelective(baseAppServerUserTenant);
            }else {
                id = appServerUser.getId();
                user.setModifyBy(BaseContextHandler.getUserID());
                user.setModifyTime(new Date());
                baseAppServerUserMapper.updateByPrimaryKeySelective(user);
            }
           //关联管理数据
                String houseIds = "";
                for (String personType : param.getPersonMold()) {
                    if ("2".equals(personType)) {
                        //保存管家管理楼栋
                        StringBuilder houseString = new StringBuilder();
                        for (Map<String, String> house : param.getHouse()) {
                            houseString.append(house.get("value") + ",");
                        }
                        if (houseString.length() > 0) {
                            houseIds = houseString.substring(0, houseString.length() - 1);
                            baseAppHousekeeperAreaBiz.addBuild(id, houseIds);
                        }
                    }
                }
                //保存技能
                for (Map<String, String> skillId : param.getSkillId()) {
                    BasePropertyServiceSkills skill = new BasePropertyServiceSkills();
                    skill.setId(UUIDUtils.generateUuid());
                    skill.setAppServerId(id);
                    skill.setSkillId(skillId.get("id"));
                    skill.setSkillCode(skillId.get("skilCode"));
                    skill.setCreateBy(BaseContextHandler.getUserID());
                    skill.setCreateTime(new Date());
                    if (basePropertyServiceSkillsMapper.insertSelective(skill) < 0) {
                        msg.setStatus(103);
                        msg.setMessage("保存物业人员技能失败!");
                        return msg;
                    }
                }
                BasePropertyServiceArea area = new BasePropertyServiceArea();
                for (Map<String, String> buildId : param.getBuildId()) {
                    area.setId(UUIDUtils.generateUuid());
                    area.setBuildId(buildId.get("buildId"));
                    area.setAppServerId(id);
                    area.setProjectId(projectId);
                    area.setCreateBy(BaseContextHandler.getUserID());
                    area.setCreateTime(new Date());
                    if (basePropertyServiceAreaMapper.insertSelective(area) < 0) {
                        msg.setStatus(104);
                        msg.setMessage("保存物业人员服务范围失败!");
                        return msg;
                    }
                }
            //保存成功，添加权限
            BaseGroupMember auth = new BaseGroupMember();
            String authid = UUIDUtils.generateUuid();
            String groupId = "ef6d0a446e8c45029c7359d9b951a8db";
            auth.setId(authid);
            auth.setUserId(id);
            auth.setCrtTime(new Date());
            auth.setGroupId(groupId);
            auth.setCrtUser(BaseContextHandler.getUserID());
            auth.setTenantId(BaseContextHandler.getTenantID());
            auth.setCrtName(BaseContextHandler.getUsername());
            baseGroupMemberMapper.insertSelective(auth);
            msgTheme = MsgThemeConstants.ADD_SERVICE;
        }
        //发送短信
        if(!com.github.wxiaoqi.security.common.util.StringUtils.isEmpty(msgTheme)){
            Map<String,String> paramMap = new HashMap<>();
            paramMap.put("projectName",tenantProjectMapper.selectProjectNameByTenantId(BaseContextHandler.getTenantID()));
            paramMap.put("userName", param.getName());
            ObjectRestResponse result = smsFeign.sendMsg(param.getMobilePhone(),0,0,"2",msgTheme, JSON.toJSONString(paramMap));
            if(result.getStatus() != 200){
                logger.error("新建物业服务发送短信通知失败！");
            }
        }
        //管家
        if("1".equals(isHousekeeper)){
            SyncHouseKeeperParams temp = new SyncHouseKeeperParams();
            BaseAppServerUser baseAppServerUser = baseAppServerUserMapper.selectByPrimaryKey(user.getId());
            if (baseAppServerUser != null) {
                temp.setBaseAppServerUser(baseAppServerUser);
                List<BaseAppHousekeeperArea> areas = baseAppHousekeeperAreaMapper.getAreasByUserId(id);

                temp.setBaseAppHousekeeperAreas(areas);
                syncUserInfoTask.saveSyncUserInfo(temp, temp.getBaseAppServerUser().getMobilePhone(),"1","1");
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }



    /**
     * 删除物业人员
     * @param id
     * @return
     */
    public ObjectRestResponse deleteServiceInfo(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        ResultServiceInfo resultServiceInfo = baseAppServerUserMapper.selectServiceInfoById(id);
        BaseAppServerUser appServerUser = null;
        if(resultServiceInfo != null){
            appServerUser = baseAppServerUserMapper.getUserByPhone(resultServiceInfo.getMobilePhone(),BaseContextHandler.getTenantID());
            if(appServerUser != null){
               int userCount= baseAppServerUserTenantMapper.getServerTenant(id);
               if (userCount == 1){
                   if(baseAppServerUserMapper.deleteServiceInfo(id,BaseContextHandler.getUserID()) < 0){
                       msg.setStatus(101);
                       msg.setMessage("删除物业人员失败!");
                       return msg;
                   }else{
                       baseAppServerUserMapper.delAppUser(id,BaseContextHandler.getUserID());
                       baseAppServerUserMapper.delMemberUser(id);
                       baseAppServerUserTenantMapper.delServerTenant(BaseContextHandler.getTenantID(),id);
                   }
               }else {
                   baseAppServerUserTenantMapper.delServerTenant(BaseContextHandler.getTenantID(),id);
               }
                if(basePropertyServiceSkillsMapper.selectHouseBulids(id, BaseContextHandler.getTenantID()) >0 ){
                    basePropertyServiceSkillsMapper.delHouseBulids(id,BaseContextHandler.getUserID(),BaseContextHandler.getTenantID());
                }
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        //管家
        if (appServerUser != null) {
            syncUserInfoTask.saveSyncUserInfo(new SyncHouseKeeperParams().setBaseAppServerUser(new BaseAppServerUser()).setBaseAppHousekeeperAreas(new ArrayList<>()), appServerUser.getMobilePhone(),"3","1");
        }
        return msg;
    }

    /**
     * 查询物业人员详情
     * @param id
     * @return
     */
    public List<ResultServiceInfo> getServiceInfo(String id){
        List<ResultServiceInfo> resultvo = new ArrayList<>();
        ResultServiceInfo resultServiceInfo = baseAppServerUserMapper.selectServiceInfoById(id);
        List<ImgInfo> logoImages = new ArrayList<>();
        ImgInfo logoImage = new ImgInfo();
        logoImage.setUrl(resultServiceInfo.getProfilePhoto());
        logoImages.add(logoImage);
        resultServiceInfo.setLogo(logoImages);

        List<ImgInfo> senImages = new ArrayList<>();
        ImgInfo senImage = new ImgInfo();
        if(com.github.wxiaoqi.security.common.util.StringUtils.isNotEmpty(resultServiceInfo.getSeniorityPhoto())){
            String[] list = resultServiceInfo.getSeniorityPhoto().split(",");
            for(String stmp: list){
                senImage.setUrl(stmp);
                senImages.add(senImage);
            }
            resultServiceInfo.setSenlogo(senImages);
        }
        if(resultServiceInfo != null){
            if("1".equals(resultServiceInfo.getIsService())){
                //查询物业人员技能
                List<ResultSkillVo> skillNameList = basePropertyServiceSkillsMapper.selectSkillNameById(id);
                resultServiceInfo.setSkillName(skillNameList);
                //查询物业服务范围
                List<ResultServiceAreaInfoVo> serviceAreaInfo = basePropertyServiceAreaMapper.selectServiceAreaInfo(id,BaseContextHandler.getTenantID());
                resultServiceInfo.setAreaName(serviceAreaInfo);
            }if("1".equals(resultServiceInfo.getIsHouseKeeper())){
                List<BuildInfoVo> buildInfoVos =  baseAppHousekeeperAreaBiz.getGroupBuildsByUserId(resultServiceInfo.getId());
                resultServiceInfo.setBuildInfoVos(buildInfoVos);
            }
            //查询物业人员分类
//            ResultServiceTreeVo serviceGroupInfo = basePropertyServiceGroupMapper.selectPidById(resultServiceInfo.getServiceGroupId());
//            if(serviceGroupInfo != null){
//                resultServiceInfo.setGroupName(serviceGroupInfo.getName());
//            }

        }
        resultvo.add(resultServiceInfo);
        return resultvo;
    }


    /**
     * 查询物业人员技能服务范围
     * @return
     */
    public List<ResultServiceAreaInfoVo> getServiceAreaList(){
        String projectId = baseTenantProjectMapper.selectProjectByTenantId(BaseContextHandler.getTenantID());
        List<ResultServiceAreaInfoVo> areaInfoVoList = basePropertyServiceAreaMapper.selectServiceArea(projectId);
        /*List<ResultServiceVo> resultVo = new ArrayList<>();
        ResultServiceVo servicevo = new ResultServiceVo();
        List<ResultServiceAreaTreeVo> areaInfoVoList = basePropertyServiceAreaMapper.selectServiceArea();
        if (areaInfoVoList != null) {
            List<ServiceTree> trees = new ArrayList<>();
            areaInfoVoList.forEach(service -> {
                trees.add(new ServiceTree(service.getId(),service.getPid(),service.getName()));
            });
            List<ServiceTree> treesTemp = TreeUtil.bulid(trees, "-1", null);
            servicevo.setChildren(treesTemp);
        }
        resultVo.add(servicevo);*/
        return areaInfoVoList;
    }


    /**
     * 编辑物业人员
     * @param param
     * @return
     */
    public ObjectRestResponse updateService(SaveServiceParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(null == param){
            msg.setStatus(505);
            msg.setMessage("参数不能为空!");
            return msg;
        }
        if(com.github.wxiaoqi.security.common.util.StringUtils.isEmpty(param.getName())){
            msg.setStatus(506);
            msg.setMessage("姓名不能为空!");
            return msg;
        }
        if(com.github.wxiaoqi.security.common.util.StringUtils.isEmpty(param.getMobilePhone())){
            msg.setStatus(507);
            msg.setMessage("手机号不能为空!");
            return msg;
        }else if(!com.github.wxiaoqi.security.common.util.StringUtils.isMobile(param.getMobilePhone())){
            msg.setStatus(508);
            msg.setMessage("请输入正确的手机号!");
            return msg;
        }
        if(!com.github.wxiaoqi.security.common.util.StringUtils.isEmpty(param.getEmail())
                && !com.github.wxiaoqi.security.common.util.StringUtils.checkEmail(param.getEmail())){
            msg.setStatus(509);
            msg.setMessage("请输入正确的Email!");
            return msg;
        }
        String  projectId = baseTenantProjectMapper.selectProjectByTenantId(BaseContextHandler.getTenantID());
        ResultServiceInfo resultServiceInfo = baseAppServerUserMapper.selectServiceInfoById(param.getId());
        BaseAppServerUser user = new BaseAppServerUser();
        //是否是管家
        String isHousekeeper = "0";
        if(resultServiceInfo != null){
            try {
                BeanUtils.copyProperties(resultServiceInfo, user);
                user.setName(param.getName());
                user.setSex(param.getSex());
                user.setIsCustomer("0");
                user.setIsHousekeeper("0");
                user.setIsService("0");
                boolean falg = true;
                boolean falgHouseKeeper=true;
                for (String personType : param.getPersonMold()){
                    if("1".equals(personType)){
                        user.setIsCustomer("1");
                    }
                    if("2".equals(personType)){
                        falgHouseKeeper=false;
                        user.setIsHousekeeper("1");
                        isHousekeeper = "1";
                    }
                    if("3".equals(personType)){
                       falg =false;
                        if(param.getSkillId() ==null || param.getSkillId().size()==0){
                            msg.setStatus(511);
                            msg.setMessage("技能不能为空!");
                            return msg;
                        }
                        if(param.getBuildId() == null || param.getBuildId().size() == 0){
                            msg.setStatus(512);
                            msg.setMessage("服务范围不能为空!");
                            return msg;
                        }
                        user.setIsService("1");
                    }
                }
                if(falg){ //如果再次取消了物业人员，需要删除物业权限和删除技能和范围
                    basePropertyServiceSkillsMapper.deleteServiceSkillBySId(param.getId());
                    basePropertyServiceAreaMapper.deleteServiceAreaBySId(param.getId(),projectId);
                }
                if(falgHouseKeeper){
                    if(basePropertyServiceSkillsMapper.selectHouseBulids(param.getId(), BaseContextHandler.getTenantID()) >0 ){
                        basePropertyServiceSkillsMapper.delHouseBulids(param.getId(),BaseContextHandler.getUserID(),BaseContextHandler.getTenantID());
                    }
                }
                user.setEmail(param.getEmail());
                user.setEnableStatus(param.getEnableStatus());
                //user.setProfilePhoto(param.getProfilePhoto());
                if (param.getProfilePhoto() != null && param.getProfilePhoto().size() > 0) {
                    for (ImgInfo imgInfo : param.getProfilePhoto()) {
                        if(StringUtils.isNotEmpty(imgInfo.getUrl())){
                            ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(imgInfo.getUrl(), DocPathConstant.PROPERTY);
                            if(objectRestResponse.getStatus()==200){
                                user.setProfilePhoto(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                            }
                        }
                        //user.setProfilePhoto(imgInfo.getUrl());
                    }
                }else {
                    user.setProfilePhoto("");
                }

                String seniorityPhoto = "";
                StringBuilder resultEva = new StringBuilder();
                if (param.getSeniorityPhoto() != null && param.getSeniorityPhoto().size() > 0) {
                    for (ImgInfo imgInfo : param.getSeniorityPhoto()) {
                        resultEva.append(imgInfo.getUrl() + ",");
                    }
                    seniorityPhoto = resultEva.substring(0, resultEva.length() - 1);
                    if(StringUtils.isNotEmpty(seniorityPhoto)){
                        ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(seniorityPhoto, DocPathConstant.PROPERTY);
                        if(objectRestResponse.getStatus()==200){
                            user.setSeniorityPhoto(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                        }
                    }
                    //user.setSeniorityPhoto(seniorityPhoto);

                }else {
                    user.setSeniorityPhoto("");
                }
                user.setModifyTime(new Date());
                user.setModifyBy(BaseContextHandler.getUserID());
                if(baseAppServerUserMapper.updateByPrimaryKeySelective(user) < 0){
                    msg.setStatus(101);
                    msg.setMessage("编辑失败!");
                    return msg;
                }
                for (String personType : param.getPersonMold()){
                   if("2".equals(personType)){
                       //如果是管家，删除再保存

                           if(basePropertyServiceSkillsMapper.selectHouseBulids(param.getId(), BaseContextHandler.getTenantID()) >0 ){
                               if(param.getHouse().size()>0){
                                   StringBuilder houseString=new StringBuilder();
                                   for (Map<String,String> house: param.getHouse()) {
                                       houseString.append(house.get("value") + ",");
                                   }
                                   String  houseIds = houseString.substring(0, houseString.length() - 1);
                                   List<String> builds = Arrays.asList(houseIds.split(","));
                                   basePropertyServiceSkillsMapper.delHouseBulids(param.getId(),BaseContextHandler.getUserID(),BaseContextHandler.getTenantID());
                                   baseAppHousekeeperAreaBiz.addBuild(param.getId(),houseIds);
                               }else{
                                   basePropertyServiceSkillsMapper.delHouseBulids(param.getId(),BaseContextHandler.getUserID(),BaseContextHandler.getTenantID());
                               }
                           }else{
                               if(param.getHouse().size()>0) {
                                   StringBuilder houseString = new StringBuilder();
                                   for (Map<String, String> house : param.getHouse()) {
                                       houseString.append(house.get("value") + ",");
                                   }
                                   String houseIds = houseString.substring(0, houseString.length() - 1);
                                   baseAppHousekeeperAreaBiz.addBuild(param.getId(), houseIds);
                               }
                           }

                    }else if("3".equals(personType)){
                       //删除技能和服务范围，再保存
                       if(basePropertyServiceSkillsMapper.deleteServiceSkillBySId(param.getId()) >= 0){
                           //保存技能
                           for (Map<String,String> skillId: param.getSkillId()) {
                               BasePropertyServiceSkills skill = new BasePropertyServiceSkills();
                               skill.setId(UUIDUtils.generateUuid());
                               skill.setAppServerId(param.getId());
                               skill.setSkillId(skillId.get("id"));
                               skill.setSkillCode(skillId.get("skilCode"));
                               skill.setCreateBy(BaseContextHandler.getUserID());
                               skill.setCreateTime(new Date());
                               if (basePropertyServiceSkillsMapper.insertSelective(skill) < 0) {
                                   msg.setStatus(103);
                                   msg.setMessage("保存物业人员技能失败!");
                                   return msg;
                               }
                           }
                       }
                       if(basePropertyServiceAreaMapper.deleteServiceAreaBySId(param.getId(),projectId) >= 0){
                           //保存服务范围
                           BasePropertyServiceArea area = new BasePropertyServiceArea();
                           for (Map<String,String> buildId: param.getBuildId()){
                               area.setBuildId(buildId.get("buildId"));
                               area.setId(UUIDUtils.generateUuid());
                               area.setAppServerId(param.getId());
                               area.setProjectId(projectId);
                               area.setCreateBy(BaseContextHandler.getUserID());
                               area.setCreateTime(new Date());
                               if(basePropertyServiceAreaMapper.insertSelective(area) < 0){
                                   msg.setStatus(104);
                                   msg.setMessage("保存物业人员服务范围失败!");
                                   return msg;
                               }
                           }
                       }
                    }
                }
                BaseAppServerUser userByPhone = baseAppServerUserMapper.getUserByPhone(user.getMobilePhone(), BaseContextHandler.getTenantID());
                if (userByPhone == null) {
                    BaseAppServerUserTenantId baseAppServerUserTenant = new BaseAppServerUserTenantId();
                    baseAppServerUserTenant.setId(user.getId());
                    baseAppServerUserTenant.setTenantId(BaseContextHandler.getTenantID());
                    baseAppServerUserTenant.setCreateBy(BaseContextHandler.getUserID());
                    baseAppServerUserTenant.setCreateTime(new Date());
                    baseAppServerUserTenantMapper.insertSelective(baseAppServerUserTenant);
                }
            }  catch (Exception e) {
                logger.error("编辑失败!",e);
            }
        }else {
            msg.setStatus(105);
            msg.setMessage("编辑失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        //管家
        SyncHouseKeeperParams temp = new SyncHouseKeeperParams();
        BaseAppServerUser baseAppServerUser = baseAppServerUserMapper.selectByPrimaryKey(user.getId());
        if (baseAppServerUser != null) {
            temp.setBaseAppServerUser(baseAppServerUser);
            List<BaseAppHousekeeperArea> areas = baseAppHousekeeperAreaMapper.getAreasByUserId(param.getId());
            temp.setBaseAppHousekeeperAreas(areas);
            syncUserInfoTask.saveSyncUserInfo(temp, baseAppServerUser.getMobilePhone(),"2","1");
        }
        return msg;
    }

    /**
     * 查询是否已添加过该手机号
     * @return
     */
    public ObjectRestResponse selectUserByPhone(String phone){
        ObjectRestResponse msg = new ObjectRestResponse();
        BaseAppServerUser appServerUser = baseAppServerUserMapper.selectIsUserByPhone(phone);
        if(appServerUser != null){
             if (appServerUser.getIsBusiness().equals("1")) {
                msg.setMessage("该用户已是商业人员");
                msg.setStatus(101);
                return msg;
            } else if (appServerUser.getTenantId().equals(BaseContextHandler.getTenantID())) {
                 msg.setData(appServerUser);
                 msg.setStatus(503);
                 return msg;
             }else {
                msg.setStatus(503);
                msg.setMessage("已经添加过该用户");
                msg.setData(appServerUser);
                return msg;
            }
        }
        return msg;
    }

}
