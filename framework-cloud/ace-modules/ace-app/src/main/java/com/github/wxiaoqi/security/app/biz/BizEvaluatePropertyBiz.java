package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BizEvaluateProperty;
import com.github.wxiaoqi.security.app.entity.BizEvaluatePropertyScale;
import com.github.wxiaoqi.security.app.mapper.BaseAppClientUserMapper;
import com.github.wxiaoqi.security.app.mapper.BizEvaluatePropertyMapper;
import com.github.wxiaoqi.security.app.mapper.BizEvaluatePropertyScaleMapper;
import com.github.wxiaoqi.security.app.mapper.BizUserProjectMapper;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.clientuser.out.UserVo;
import com.github.wxiaoqi.security.app.vo.property.in.SavePropertyEvaluate;
import com.github.wxiaoqi.security.app.vo.property.out.PropertyEvaluateInfo;
import com.github.wxiaoqi.security.app.vo.property.out.PropertyInfo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 物业评价表
 *
 * @author zxl
 * @Date 2019-01-07 15:20:17
 */
@Service
public class BizEvaluatePropertyBiz extends BusinessBiz<BizEvaluatePropertyMapper,BizEvaluateProperty> {

    @Autowired
    private BizEvaluatePropertyMapper bizEvaluatePropertyMapper;
    @Autowired
    private BizEvaluatePropertyScaleMapper bizEvaluatePropertyScaleMapper;
    @Autowired
    private BizUserProjectMapper bizUserProjectMapper;
    @Autowired
    private BaseAppClientUserMapper baseAppClientUserMapper;


    /**
     * 保存物业评价
     * @param param
     * @return
     */
    public ObjectRestResponse savePropertyEvaluate(SavePropertyEvaluate param){
        ObjectRestResponse msg = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        if(StringUtils.isEmpty(param.getEvaluateType())){
            msg.setStatus(201);
            msg.setMessage("评价不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(param.getProjectId())){
            msg.setStatus(202);
            msg.setMessage("项目id不能为空");
            return msg;
        }
        if("0".equals(param.getEvaluateType())){
            msg.setStatus(201);
            msg.setMessage("评价至少一颗星");
            return msg;
        }
        if("1".equals(param.getEvaluateType()) || "2".equals(param.getEvaluateType()) || "5".equals(param.getEvaluateType())){
            if(StringUtils.isEmpty(param.getContent())){
                msg.setStatus(201);
                msg.setMessage("评价内容不能为空");
                return msg;
            }
        }
        int count = bizEvaluatePropertyMapper.selectIsEvaluatetByUser(param.getProjectId(),BaseContextHandler.getUserID());
        if(count > 0){
            msg.setStatus(201);
            msg.setMessage("您本月已评价过了!");
            return msg;
        }

        //判断用户当前角色
        CurrentUserInfosVo userInfo =  bizUserProjectMapper.getCurrentUserInfos(BaseContextHandler.getUserID());
        if(userInfo != null){
            if("4".equals(userInfo.getIdentityType())){
                msg.setStatus(201);
                msg.setMessage("请前去选择身份验证!");
                return msg;
            }
        }
        UserVo userVo = baseAppClientUserMapper.getUserNameById(BaseContextHandler.getUserID());
        BizEvaluateProperty property = new BizEvaluateProperty();
        try {
            property.setId(UUIDUtils.generateUuid());
            property.setUserId(BaseContextHandler.getUserID());
            if(userVo != null){
                property.setUserName(userVo.getName());
            }
            property.setProjectId(param.getProjectId());
            property.setContent(param.getContent());
            property.setEvaluateDate(sdf.format(new Date()));
            property.setEvaluateType(param.getEvaluateType());
            property.setTimeStamp(new Date());
            property.setCreateBy(BaseContextHandler.getUserID());
            property.setCreateTime(new Date());
            if(bizEvaluatePropertyMapper.insertSelective(property) < 0){
                msg.setStatus(501);
                msg.setMessage("保存物业评价失败!");
                return msg;
            }else{
                //维护数据
                List<PropertyInfo> propertyList =  bizEvaluatePropertyScaleMapper.selectPropertyInfo(param.getProjectId());
                PropertyInfo propertyInfo =  bizEvaluatePropertyScaleMapper.selectPropertyInfoByType(param.getProjectId(),param.getEvaluateType());
                //查询该项目的物业评价总数
                int total = bizEvaluatePropertyScaleMapper.selectEvaluateTotal(param.getProjectId());
                if(propertyInfo == null){
                    BizEvaluatePropertyScale scale = new BizEvaluatePropertyScale();
                    scale.setId(UUIDUtils.generateUuid());
                    scale.setProjectId(param.getProjectId());
                    scale.setEvaluateType(Integer.parseInt(param.getEvaluateType()));
                    scale.setEvaluateSum(1);
                    if(total == 0){
                        scale.setEvaluateScale("100.0%");
                    }else{
                        scale.setEvaluateScale(myPercent(1,(total+1)));
                    }
                    scale.setTimeStamp(new Date());
                    scale.setCreateBy(BaseContextHandler.getUserID());
                    scale.setCreateTime(new Date());
                    if(bizEvaluatePropertyScaleMapper.insertSelective(scale) < 0){
                        msg.setStatus(201);
                        msg.setMessage("维护物业评价数据失败!");
                        return msg;
                    }
                    if(total >= 1){
                        for(PropertyInfo info: propertyList){
                            if(!info.getEvaluateType().equals(param.getEvaluateType())){
                                BizEvaluatePropertyScale scale1 = new BizEvaluatePropertyScale();
                                scale1.setId(info.getId());
                                scale1.setEvaluateType(Integer.parseInt(info.getEvaluateType()));
                                scale1.setEvaluateSum(info.getEvaluateSum());
                                scale1.setEvaluateScale(myPercent(info.getEvaluateSum(),(total+1)));
                                scale1.setTimeStamp(new Date());
                                scale1.setModifyBy(BaseContextHandler.getUserID());
                                scale1.setModifyTime(new Date());
                                if(bizEvaluatePropertyScaleMapper.updateByPrimaryKeySelective(scale1) < 0) {
                                    msg.setStatus(201);
                                    msg.setMessage("维护物业评价数据失败!");
                                    return msg;
                                }
                            }
                        }
                    }
                }else{
                    BizEvaluatePropertyScale scale = new BizEvaluatePropertyScale();
                    scale.setId(propertyInfo.getId());
                    scale.setEvaluateType(Integer.parseInt(propertyInfo.getEvaluateType()));
                    scale.setEvaluateSum(propertyInfo.getEvaluateSum()+1);
                    scale.setEvaluateScale(myPercent((propertyInfo.getEvaluateSum()+1),(total+1)));
                    scale.setTimeStamp(new Date());
                    scale.setModifyBy(BaseContextHandler.getUserID());
                    scale.setModifyTime(new Date());
                    if(bizEvaluatePropertyScaleMapper.updateByPrimaryKeySelective(scale) < 0){
                        msg.setStatus(201);
                        msg.setMessage("维护物业评价数据失败!");
                        return msg;
                    }
                    for(PropertyInfo info: propertyList){
                        if(!info.getEvaluateType().equals(param.getEvaluateType())){
                            BizEvaluatePropertyScale scale1 = new BizEvaluatePropertyScale();
                            scale1.setId(info.getId());
                            scale1.setEvaluateType(Integer.parseInt(info.getEvaluateType()));
                            scale1.setEvaluateSum(info.getEvaluateSum());
                            scale1.setEvaluateScale(myPercent(info.getEvaluateSum(),(total+1)));
                            scale1.setTimeStamp(new Date());
                            scale1.setModifyBy(BaseContextHandler.getUserID());
                            scale1.setModifyTime(new Date());
                            if(bizEvaluatePropertyScaleMapper.updateByPrimaryKeySelective(scale1) < 0) {
                                msg.setStatus(201);
                                msg.setMessage("维护物业评价数据失败!");
                                return msg;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    public static String myPercent(int y, int z) {
        String baifenbi = "";// 接受百分比的值
        double baiy = y * 1.0;
        double baiz = z * 1.0;
        double fen = baiy / baiz;
// NumberFormat nf = NumberFormat.getPercentInstance();注释掉的也是一种方法
// nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位
        DecimalFormat df1 = new DecimalFormat("##.0%");
// ##.00%
// 百分比格式，后面不足2位的用0补齐
// baifenbi=nf.format(fen);
        baifenbi = df1.format(fen);
        return baifenbi;
    }


    /**
     * 查询物业评价详情
     * @param projectId
     * @return
     */
    public ObjectRestResponse<PropertyEvaluateInfo> getPropertyInfo(String projectId){
        PropertyEvaluateInfo propertyEvaluateInfo = new PropertyEvaluateInfo();
        ObjectRestResponse msg = new ObjectRestResponse();
        //判断用户当前角色
        CurrentUserInfosVo userInfo =  bizUserProjectMapper.getCurrentUserInfos(BaseContextHandler.getUserID());
        if("4".equals(userInfo.getIdentityType())){
            msg.setStatus(201);
            msg.setMessage("请前去选择身份验证!");
            return msg;
        }
        //查询当前项目是否存在客服,物业人员
       int cusTotal = bizEvaluatePropertyMapper.selectIsCusByProjectId(projectId);
        if(cusTotal < 1){
            msg.setStatus(201);
            msg.setMessage("当前项目暂无物业服务!");
            return msg;
        }
        //判断用户是否评价
        int count = bizEvaluatePropertyMapper.selectIsEvaluatetByUser(projectId,BaseContextHandler.getUserID());
        String evaluateType = bizEvaluatePropertyMapper.selectEvaluateTypeByUser(projectId,BaseContextHandler.getUserID());
        if(count == 0){
            propertyEvaluateInfo.setIsEvaluate("0");
            propertyEvaluateInfo.setEvaluateType("");
        }else{
            propertyEvaluateInfo.setIsEvaluate("1");
            propertyEvaluateInfo.setEvaluateType(evaluateType);
        }
        //评价等级详情
        List<PropertyInfo> propertyList =  bizEvaluatePropertyScaleMapper.selectPropertyInfo(projectId);
        if(propertyList == null || propertyList.size() == 0){
            propertyList = new ArrayList<>();
        }
        propertyEvaluateInfo.setPropertyInfoList(propertyList);
        //平均分
        int totalScale = bizEvaluatePropertyMapper.selectEvaluateScale(projectId);
        //查询该项目的物业评价总数
        int total = bizEvaluatePropertyScaleMapper.selectEvaluateTotal(projectId);
        if(totalScale == 0){
            propertyEvaluateInfo.setAverage("0");
        }else{
            propertyEvaluateInfo.setAverage(division(totalScale,total));
        }
        if(total == 0){
            propertyEvaluateInfo.setEvaluate("");
        }else{
            propertyEvaluateInfo.setEvaluate(getEvaluate(Double.parseDouble(division(totalScale,total))));
        }
        //获取当前月份
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH )+1;
        propertyEvaluateInfo.setCurrentDate(month);
        //当前月评价人数
        int currentUserNum = bizEvaluatePropertyMapper.selectEvaluatetCount(projectId);
        propertyEvaluateInfo.setCurrentUserNum(currentUserNum);

        return ObjectRestResponse.ok(propertyEvaluateInfo);
    }

    public static String division(int a ,int b){
        String result = "";
        float num =(float)a/b;

        DecimalFormat df = new DecimalFormat("0.0");

        result = df.format(num);

        return result;

    }


    public String getEvaluate(Double average){
        if(average < 3){
            return "不满意";
        }else if(average >= 3 && average < 4){
            return "一般";
        }else{
            return "满意";
        }
    }







}