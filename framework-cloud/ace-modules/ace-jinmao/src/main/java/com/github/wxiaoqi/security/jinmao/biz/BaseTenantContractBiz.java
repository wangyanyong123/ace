package com.github.wxiaoqi.security.jinmao.biz;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.constant.BusinessConstant;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.BooleanUtil;
import com.github.wxiaoqi.security.common.util.Sha256PasswordEncoder;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.*;
import com.github.wxiaoqi.security.jinmao.feign.CodeFeign;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.*;
import com.github.wxiaoqi.security.jinmao.task.SyncUserInfoTask;
import com.github.wxiaoqi.security.jinmao.task.util.SyncBusinessUserParams;
import com.github.wxiaoqi.security.jinmao.vo.Business.OutParam.ResultBusinessVo;
import com.github.wxiaoqi.security.jinmao.vo.CompanyManagement.InputParam.UpdateEnableParam;
import com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.InputParam.CloseInvoice;
import com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.InputParam.ContractSaveParam;
import com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.InputParam.MerchantSaveParam;
import com.github.wxiaoqi.security.jinmao.vo.MerchantManagement.OutParam.*;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.postage.out.PostageInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商户协议表
 *
 * @author zxl
 * @Date 2018-12-05 10:04:44
 */
@Service
public class BaseTenantContractBiz extends BusinessBiz<BaseTenantContractMapper, BaseTenantContract> {

    private Logger logger = LoggerFactory.getLogger(BaseTenantContractBiz.class);
    private Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();
    @Resource
    private BaseTenantContractMapper baseTenantContractMapper;
    @Resource
    private BaseTenantProjectMapper baseTenantProjectMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private BaseTenantMapper baseTenantMapper;
    @Resource
    private BaseGroupMemberMapper baseGroupMemberMapper;
    @Resource
    private BaseTenantBusinessMapper baseTenantBusinessMapper;
    @Autowired
    private BaseTenantBiz baseTenantBiz;
    @Autowired
    private CodeFeign codeFeign;
    @Autowired
    private SyncUserInfoTask syncUserInfoTask;
    @Autowired
    private ToolFegin toolFeign;

    /**
     * 查询商户管理列表
     * @param enableStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ResultMerchantManageVo> getMerchantMangeList(String enableStatus, String searchVal, Integer page, Integer limit) {
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }

        if (page == 0) {
            page = 1;
        }
        Integer startIndex = (page - 1) * limit;
        //分页
        List<ResultMerchantManageVo> merchantManageList = baseTenantMapper.selectMerchantManageList(enableStatus, searchVal, startIndex, limit,BaseContextHandler.getTenantID());
        for(ResultMerchantManageVo manageVo: merchantManageList){
            //查询项目名称
            List<String> projectNames = baseTenantProjectMapper.selectProjectNamesById(manageVo.getId());
            if(projectNames != null && projectNames.size()>0){
                String projectName = "";
                StringBuilder sb = new StringBuilder();
                for (String project:projectNames){
                    sb.append(project + ",");
                }
                System.out.println(sb);
                projectName = sb.substring(0, sb.length()-1);
                manageVo.setProjectName(projectName);
            }else {
                manageVo.setProjectName("");
            }
            //查询业务名称
            List<String> businessNames = baseTenantBusinessMapper.selectBusinessNamesById(manageVo.getId());
            if(businessNames != null && businessNames.size()>0){
                String businessName = "";
                StringBuilder sb = new StringBuilder();
                for (String business:businessNames){
                    sb.append(business + ",");
                }
                businessName = sb.substring(0, sb.length()-1);
                manageVo.setBusName(businessName);
            }else {
                manageVo.setBusName("");
            }

        }
        return merchantManageList;
    }

    /**
     * 根据搜索条件查询数量
     * @param enableStatus
     * @param searchVal
     * @return
     */
    public int selectMerchantManageCount(String enableStatus, String searchVal) {
        return baseTenantMapper.selectMerchantManageCount(enableStatus, searchVal,BaseContextHandler.getTenantID());
    }

    /**
     * 保存商户管理
     * @param param
     * @return
     */
    public ObjectRestResponse saveMerchantManageInfo(MerchantSaveParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        List<String> id = baseTenantMapper.getMerchantName(param.getName());
        if (id != null && id.size() > 0) {
            msg.setStatus(101);
            msg.setMessage("名字已经存在");
            return msg;
        }
        ObjectRestResponse objectRestResponse = codeFeign.getCode("Merchant","2","6","0");
        logger.info("生成账号处理结果："+objectRestResponse.getData());
        String merchantId = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            StringBuilder sb = new StringBuilder();
            BaseTenant merchant = new BaseTenant();
            merchantId = UUIDUtils.generateUuid();
            merchant.setId(merchantId);
            merchant.setTenantType("2");
            if(objectRestResponse.getStatus()==200){
                merchant.setAccount(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
            }
            merchant.setName(param.getName());
            merchant.setAddress(param.getAddress());
            merchant.setLicenceNo(param.getLicenceNo());
            merchant.setJuristicperson(param.getJuristicPerson());
            if (param.getSetupTime()!=null) {
                merchant.setSetupTime(sdf.parse(param.getSetupTime()));
            }
            merchant.setRegCapital(param.getRegCapital());
            merchant.setContactorName(param.getContactorName());
            merchant.setContactTel(param.getContactTel());
            merchant.setContactEmail(param.getContactEmail());
            if (param.getLogoImg().size()>0&&param.getLogoImg()!=null) {
                for (ImgInfo imgInfo : param.getLogoImg()) {
                    if(StringUtils.isNotEmpty(imgInfo.getUrl())){
                        ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(imgInfo.getUrl(), DocPathConstant.MERCHANT);
                        if(restResponse.getStatus()==200){
                            merchant.setLogoImg(restResponse.getData()==null ? "" : (String)restResponse.getData());
                        }
                    }
                    //merchant.setLogoImg(imgInfo.getUrl());
                }
            }else {
                merchant.setLogoImg("");
            }
            String qualificImgs = "";
            for(ImgInfo temp: param.getQualificImgList()){
                sb.append(temp.getUrl()+",");
            }
            qualificImgs = sb.substring(0,sb.length()-1);
            if(StringUtils.isNotEmpty(qualificImgs)){
                ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(qualificImgs, DocPathConstant.MERCHANT);
                if(restResponse.getStatus()==200){
                    merchant.setQualificImg(restResponse.getData()==null ? "" : (String)restResponse.getData());
                }
            }
            //merchant.setQualificImg(qualificImgs);
            merchant.setSummary(param.getSummary());
            merchant.setEnableStatus("1");
            merchant.setCrtUserId(BaseContextHandler.getUserID());
            merchant.setCrtTime(new Date());
            merchant.setTenantId(BaseContextHandler.getTenantID());
            int total = baseTenantMapper.insertSelective(merchant);
            msg.setData(merchantId);
        } catch (ParseException e) {
            logger.error("处理时间异常",e);
        }
        //新建用户
        //通过apollo设置默认密码
        Config config = ConfigService.getConfig("ace-jinmao");
        String password = config.getProperty("tenant-account-default-pwd", null);
        BaseUser user = new BaseUser();
        String userId = UUIDUtils.generateUuid();
        user.setId(userId);
        user.setUsername(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
        user.setPassword(encoder.encode(password));
        user.setName(param.getName());
        //user.setDepartId("d583e7de6d2d48b78fb3c7dcb180cb1f");
        user.setType("2");
        user.setSex("未知");
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

        //商户授予用户
        BaseTenant merchant = baseTenantMapper.selectByPrimaryKey(merchantId);
        merchant.setOwner(userId);
        baseTenantBiz.updateSelectiveById(merchant);
        user = userMapper.selectByPrimaryKey(userId);
        user.setTenantId(merchantId);
        userMapper.updateByPrimaryKeySelective(user);

        //关联用户
        BaseGroupMember member = new BaseGroupMember();
        member.setId(UUIDUtils.generateUuid());
        member.setGroupId(BusinessConstant.getMerchantmanager());
        member.setUserId(userId);
        member.setTenantId(BaseContextHandler.getTenantID());
        member.setCrtTime(new Date());
        member.setCrtUser(BaseContextHandler.getUserID());
        member.setCrtName(BaseContextHandler.getUsername());
        baseGroupMemberMapper.insertSelective(member);

        //同步至调度引擎
        SyncBusinessUserParams params = new SyncBusinessUserParams();
        BaseUser baseUser = userMapper.selectByPrimaryKey(user.getId());
        if (baseUser != null) {
            params.setBaseUser(baseUser);
            params.setBaseTenant(merchant);
            syncUserInfoTask.saveSyncUserInfo(params, baseUser.getUsername(),"1","3");
            logger.info("开始同步至调度引擎，id为{}", baseUser.getId());
        }

        msg.setMessage("Operation succeed!");
        return msg;
    }

    /**
     * 保存协议设置
     * @param param
     * @return
     */
    public ObjectRestResponse saveContractInfo(ContractSaveParam param) {
        ObjectRestResponse msg =  new ObjectRestResponse();
        BaseTenantContract contract = new BaseTenantContract();
        BaseTenantBusiness business = new BaseTenantBusiness();
        BaseTenantProject project = new BaseTenantProject();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            contract.setId(UUIDUtils.generateUuid());
            contract.setEnterpriseId(param.getEnterpriseId());
            contract.setProtocolPerson(param.getProtocolPerson());
            contract.setProtocolTel(param.getProtocolTel());
            contract.setSignDate(sdf.parse(param.getSignDate()));
            contract.setBond(param.getBond());
            contract.setAnnualFee(param.getAnnualFee());
            if (param.getImageId().size()>0&&param.getImageId()!=null) {
                List<ImgInfo> list = new ArrayList<>();
                for (ImgInfo imgInfo : param.getImageId()) {
                    ImgInfo image = new ImgInfo();
                    image.setUrl(imgInfo.getUrl());
                    list.add(image);
                }
                String imageUrl = "";
                StringBuilder sb = new StringBuilder();
                for (ImgInfo imgInfo : list) {
                    sb.append(imgInfo.getUrl() + ",");
                }
                imageUrl = sb.substring(0, sb.length() - 1);
                if(StringUtils.isNotEmpty(imageUrl)){
                    ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(imageUrl, DocPathConstant.MERCHANT);
                    if(restResponse.getStatus()==200){
                        contract.setImageId(restResponse.getData()==null ? "" : (String)restResponse.getData());
                    }
                }
                //contract.setImageId(imageUrl);
            }else {
                contract.setImageId("");
            }
            contract.setAccountType(param.getAccountType());
            contract.setAccountName(param.getAccountName());
            contract.setAccountNumber(param.getAccountNumber());
            contract.setAccountBookName(param.getAccountBookName());
            contract.setStatus("1");
            contract.setTimeStamp(new Date());
            contract.setCreateBy(BaseContextHandler.getUserID());
            contract.setCreateTime(new Date());
            //插入关联
            if (baseTenantContractMapper.insertSelective(contract) > 0) {
                for (Map<String, String> projectIds : param.getProjectId()) {
                    //项目范围
                    project.setId(UUIDUtils.generateUuid());
                    project.setTenantId(param.getEnterpriseId());
                    project.setProjectId(projectIds.get("id"));
                    project.setTimeStamp(new Date());
                    project.setCreateBy(BaseContextHandler.getUserID());
                    project.setCreateTime(new Date());
                    if (baseTenantProjectMapper.insertSelective(project) < 0) {
                        logger.error("保存关联数据失败,projectId为{}",projectIds.get("id"));
                        msg.setStatus(102);
                        msg.setMessage("保存关联数据失败");
                        return msg;
                    }
                }
                for (Map<String, String> busIds : param.getBusId()) {
                    //协议业务
                    business.setId(UUIDUtils.generateUuid());
                    business.setTenantId(param.getEnterpriseId());
                    business.setBusId(busIds.get("id"));
                    business.setTimeStamp(new Date());
                    business.setCreateBy(BaseContextHandler.getUserID());
                    business.setCreateTime(new Date());
                    if (baseTenantBusinessMapper.insertSelective(business) < 0) {
                        logger.error("保存关联数据失败,busId为{}",busIds.get("id"));
                        msg.setStatus(102);
                        msg.setMessage("保存关联数据失败");
                        return msg;
                    }
                }
            }else {
                baseTenantMapper.deleteMerchantById(param.getEnterpriseId());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 修改商户状态
     * @param param
     * @return
     */
    public ObjectRestResponse updateEnableStatus(UpdateEnableParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        param.setUpdUserId(BaseContextHandler.getUserID());
        if (baseTenantMapper.updateMerchantEnableStatus(param) < 0) {
            logger.error("修改启用失败，id为{}", param.getId());
            msg.setStatus(102);
            msg.setMessage("启用失败！");
            return msg;
        }else{
            String tenantId =  baseTenantMapper.selectBaseUserId(param.getId());
            if("1".equals(param.getEnableStatus())){
                baseTenantMapper.updateBaseUserStatus("0",BaseContextHandler.getUserID(),tenantId);
            }else{
                baseTenantMapper.updateBaseUserStatus("1",BaseContextHandler.getUserID(),tenantId);
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 查询商户管理详情
     * @param id
     * @return
     */
    public List<ResultMerchantManageInfo> getMerchantManageInfo(String id) {
        List<ResultMerchantManageInfo> list = new ArrayList<>();
        ResultMerchantManageInfo info = new ResultMerchantManageInfo();
        ResultMerchantVo merchant = baseTenantMapper.selectMerchantInfo(id);
        ResultContractVo contract = baseTenantContractMapper.selectContractInfo(id);
        //邮费规则
        List<PostageInfoVo> postageInfo = baseTenantMapper.getPostageInfo(BaseContextHandler.getTenantID());
        List<PostageInfoVo> postageList = new ArrayList<>();
        if (postageInfo != null && postageInfo.size() > 0) {
            for (PostageInfoVo postage : postageInfo) {
                if (! postage.getEndAmount().equals("-1")) {
                    postageList.add(postage);
                }else {
                    merchant.setPostage(postage.getPostage());
                    merchant.setStartAmount(postage.getStartAmount());
                    merchant.setEndAmount(postage.getEndAmount());
                    merchant.setPostId(postage.getId());
                }
            }
            merchant.setPostageList(postageList);

        }else {
            merchant.setPostageList(new ArrayList<>());
            merchant.setPostage("0");
            merchant.setStartAmount("0");
            merchant.setEndAmount("-1");
        }
        if (merchant.getLogo() != null) {
            List<ImgInfo> imgInfo = new ArrayList<>();
            ImgInfo imge = new ImgInfo();
            imge.setUrl(merchant.getLogo());
            imgInfo.add(imge);
            merchant.setLogoImg(imgInfo);
        }else {
            merchant.setLogoImg(new ArrayList<>());
        }
        //商户营业资历图片
         List<ImgInfo> selectList = new ArrayList<>();
            ImgInfo qualificImgInfo = new ImgInfo();
            if(merchant.getQualificImg() != null){
                String[] qualificImgs = merchant.getQualificImg().split(",");
                for(String url : qualificImgs){
                    qualificImgInfo.setUrl(url);
                    selectList.add(qualificImgInfo);
                }
                merchant.setQualificImgList(selectList);
            }
        info.setMerchant(merchant);
        if (contract != null) {
            if (contract.getImageUrl()!=null && !contract.getImageUrl().equals("")) {
                List<ImgInfo> imageList = new ArrayList<>();
                String[] urlList = contract.getImageUrl().split(",");
                for (String url : urlList) {
                    //返回图片名称
                    String fileName = baseTenantContractMapper.getPhotoInfo(url);
                    ImgInfo imgInfo = new ImgInfo();
                    if (!StringUtils.isEmpty(fileName)) {
                        imgInfo.setName(fileName);
                    }else {
                        imgInfo.setName("");
                    }
                    imgInfo.setUrl(url);
                    imageList.add(imgInfo);
                }
                contract.setImageId(imageList);
            }else {
                contract.setImageId(new ArrayList<>());
            }
            List<ResultProjectInfo> project = baseTenantContractMapper.selectContractProjectById(id);
            if (project != null) {
                contract.setProjectId(project);
            }else {
                contract.setProjectId(new ArrayList<>());
            }
            List<ResultBusinessInfo> business = baseTenantContractMapper.selectContractBusinessById(id);
            if (business != null) {
                contract.setBusId(business);
            }else {
                contract.setProjectId(new ArrayList<>());
            }
            info.setContract(contract);
        }else {
            ResultContractVo contractVo = new ResultContractVo();
            contractVo.setId("");
            contractVo.setEnterpriseId("");
            contractVo.setProtocolPerson("");
            contractVo.setProtocolTel("");
            contractVo.setSignDate(new Date());
            contractVo.setBond(new BigDecimal(0));
            contractVo.setAnnualFee(new BigDecimal(0));
            contractVo.setImageId(new ArrayList<>());
            contractVo.setAccountType("");
            contractVo.setAccountName("");
            contractVo.setAccountNumber("");
            contractVo.setAccountBookName("");
            contractVo.setProjectId(new ArrayList<>());
            contractVo.setBusId(new ArrayList<>());
            info.setContract(contractVo);
        }
        list.add(info);
        return list;
    }

    /**
     * 更新协议设置
     * @param param
     * @return
     */
    public ObjectRestResponse updateContractInfo(ContractSaveParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        ResultContractVo contractVo = baseTenantContractMapper.selectContractInfo(param.getEnterpriseId());
        BaseTenantContract contract = new BaseTenantContract();
        BaseTenantBusiness business = new BaseTenantBusiness();
        BaseTenantProject project = new BaseTenantProject();
       if (contractVo != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                BeanUtils.copyProperties(contractVo,contract);
                contract.setEnterpriseId(param.getEnterpriseId());
                contract.setProtocolPerson(param.getProtocolPerson());
                contract.setProtocolTel(param.getProtocolTel());
                contract.setSignDate(sdf.parse(param.getSignDate()));
                contract.setBond(param.getBond());
                contract.setAnnualFee(param.getAnnualFee());
                if (param.getImageId().size()>0&&param.getImageId()!=null) {
                    List<ImgInfo> list = new ArrayList<>();
                    for (ImgInfo imgInfo : param.getImageId()) {
                        ImgInfo image = new ImgInfo();
                        image.setUrl(imgInfo.getUrl());
                        list.add(image);
                    }
                    String imageUrl = "";
                    StringBuilder sb = new StringBuilder();
                    for (ImgInfo imgInfo : list) {
                        sb.append(imgInfo.getUrl() + ",");
                    }
                    imageUrl = sb.substring(0, sb.length() - 1);
                    if(StringUtils.isNotEmpty(imageUrl)){
                        ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(imageUrl, DocPathConstant.MERCHANT);
                        if(restResponse.getStatus()==200){
                            contract.setImageId(restResponse.getData()==null ? "" : (String)restResponse.getData());
                        }
                    }
                    //contract.setImageId(imageUrl);
                }else {
                    contract.setImageId("");
                }
                contract.setAccountType(param.getAccountType());
                contract.setAccountName(param.getAccountName());
                contract.setAccountNumber(param.getAccountNumber());
                contract.setAccountBookName(param.getAccountBookName());
                contract.setTimeStamp(new Date());
                contract.setModifyBy(BaseContextHandler.getUserID());
                contract.setModifyTime(new Date());
                //修改关联
                if (baseTenantContractMapper.updateByPrimaryKeySelective(contract) > 0) {
                    //删除原有项目关联
                    baseTenantProjectMapper.deleteProjectByMerchantId(param.getEnterpriseId());
                    for (Map<String, String> projectIds : param.getProjectId()) {
                        //项目范围
                        project.setId(UUIDUtils.generateUuid());
                        project.setTenantId(param.getEnterpriseId());
                        project.setProjectId(projectIds.get("id"));
                        project.setTimeStamp(new Date());
                        project.setCreateBy(BaseContextHandler.getUserID());
                        project.setCreateTime(new Date());
                        if (baseTenantProjectMapper.insertSelective(project) < 0) {
                            logger.error("保存关联数据失败,projectId为{}",projectIds.get("id"));
                            msg.setStatus(102);
                            msg.setMessage("保存关联数据失败");
                            return msg;
                        }
                    }
                    //删除原有业务关联
                    baseTenantBusinessMapper.deleteBusinessByMerchantId(param.getEnterpriseId());
                    for (Map<String, String> busIds : param.getBusId()) {
                        //协议业务
                        business.setId(UUIDUtils.generateUuid());
                        business.setTenantId(param.getEnterpriseId());
                        business.setBusId(busIds.get("id"));
                        business.setTimeStamp(new Date());
                        business.setCreateBy(BaseContextHandler.getUserID());
                        business.setCreateTime(new Date());
                        if (baseTenantBusinessMapper.insertSelective(business) < 0) {
                            logger.error("保存关联数据失败,busId为{}",busIds.get("id"));
                            msg.setStatus(102);
                            msg.setMessage("保存关联数据失败");
                            return msg;
                        }
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
     * 设置打烊，开票
     * @param params
     * @return
     */
    public  ObjectRestResponse updateIsInvoice(CloseInvoice params){
        ObjectRestResponse msg=new ObjectRestResponse();
        BaseTenant baseInfo = new BaseTenant();
        String tId = BaseContextHandler.getTenantID();
        baseInfo.setId(tId);
        if(params.getSetUpClosure().equals("isInvoice")){
           baseInfo.setIsInvoice(params.getId());
        }else if(params.getSetUpClosure().equals("isClose")){
            baseInfo.setIsClose(params.getId());
        }else if(params.getSetUpClosure().equals("isPrint")){
            baseInfo.setIsPrint(params.getId());
        }
        baseInfo.setUpdTime(new Date());
        baseInfo.setUpdUserId(BaseContextHandler.getUserID());
        if(baseTenantMapper.updateByPrimaryKeySelective(baseInfo)>0){
            msg.setMessage("Operation succeed!");
            msg.setData("1");
        }else {
            msg.setStatus(201);
            msg.setMessage("设置失败");
            return msg;
        }
        return msg;
    }

    /**
     * 更改商户基本信息
     * @param param
     * @return
     */
    public ObjectRestResponse updateMerchantInfo(MerchantSaveParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        List<String> id = baseTenantMapper.getMerchantName(param.getName());
        ResultMerchantVo resultMerchant = baseTenantMapper.selectMerchantInfo(param.getId());
        if (! resultMerchant.getName().equals(param.getName())) {
            if (id != null && id.size() > 0) {
                logger.info("名字已经存在，id为{}", param.getId());
                msg.setStatus(101);
                msg.setMessage("名字已经存在");
                return msg;
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        BaseTenant merchant = new BaseTenant();
        if (resultMerchant != null) {
            try {
                BeanUtils.copyProperties(resultMerchant,merchant);
                merchant.setName(param.getName());
                merchant.setAddress(param.getAddress());
                merchant.setLicenceNo(param.getLicenceNo());
                merchant.setJuristicperson(param.getJuristicPerson());
                if (param.getSetupTime() != null) {
                     merchant.setSetupTime(sdf.parse(param.getSetupTime()));
                }
                if (param.getRegCapital() != null) {
                    merchant.setRegCapital(param.getRegCapital());
                }
                merchant.setContactorName(param.getContactorName());
                merchant.setContactTel(param.getContactTel());
                merchant.setContactEmail(param.getContactEmail());
                if (param.getLogoImg().size()>0&&param.getLogoImg()!=null) {
                    for (ImgInfo imgInfo : param.getLogoImg()) {
                        if(StringUtils.isNotEmpty(imgInfo.getUrl())){
                            ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(imgInfo.getUrl(), DocPathConstant.MERCHANT);
                            if(restResponse.getStatus()==200){
                                merchant.setLogoImg(restResponse.getData()==null ? "" : (String)restResponse.getData());
                            }
                        }
                        //merchant.setLogoImg(imgInfo.getUrl());
                    }
                }else {
                    merchant.setLogoImg("");
                }
                String qualificImgs = "";
                if(param.getQualificImgList() != null & param.getQualificImgList().size()>0){

                    for(ImgInfo temp: param.getQualificImgList()){
                        sb.append(temp.getUrl()+",");
                    }
                    qualificImgs = sb.substring(0,sb.length()-1);
                }
                if(StringUtils.isNotEmpty(qualificImgs)){
                    ObjectRestResponse restResponse = toolFeign.moveAppUploadUrlPaths(qualificImgs, DocPathConstant.MERCHANT);
                    if(restResponse.getStatus()==200){
                        merchant.setQualificImg(restResponse.getData()==null ? "" : (String)restResponse.getData());
                    }
                }
                //merchant.setQualificImg(qualificImgs);
                merchant.setSummary(param.getSummary());
                if (baseTenantMapper.updateByPrimaryKeySelective(merchant) < 0) {
                    logger.error("更新商户信息失败,id为{}", resultMerchant.getId());
                    msg.setStatus(101);
                    msg.setMessage("更新商户信息失败!");
                    return msg;
                }else {
                    BaseUser baseUser = baseTenantMapper.selectWebUserInfo(param.getId());
                    if (baseUser != null) {
                        BaseUser user = new BaseUser();
                        user.setId(baseUser.getId());
                        user.setName(param.getName());
                        user.setUpdTime(new Date());
                        user.setUpdUserId(BaseContextHandler.getUserID());
                        userMapper.updateByPrimaryKeySelective(user);
                        //同步至调度引擎
                        BaseUser newUser = baseTenantMapper.selectWebUserInfo(param.getId());
                        if (newUser != null) {
                            SyncBusinessUserParams params = new SyncBusinessUserParams();
                            params.setBaseUser(newUser);
                            params.setBaseTenant(merchant);
                            syncUserInfoTask.saveSyncUserInfo(params, newUser.getUsername(),"2","3");
                            logger.info("开始同步至调度引擎，id为{}", newUser.getId());
                        }
                    }
                }
            } catch (Exception e) {
                msg.setMessage("编辑失败");
                msg.setStatus(101);
                return msg;
            }
        }



        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 查询商户所关联业务
     * @return
     */
    public List<ResultBusinessVo> getBusiness(){
        List<ResultBusinessVo> businessVoList = baseTenantContractMapper.selectContractBusinessInfo();
        return businessVoList;
    }


}
