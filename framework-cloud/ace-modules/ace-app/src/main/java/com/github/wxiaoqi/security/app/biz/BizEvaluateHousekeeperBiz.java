package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BizEvaluateHousekeeper;
import com.github.wxiaoqi.security.app.entity.BizEvaluateHousekeeperDetail;
import com.github.wxiaoqi.security.app.mapper.*;
import com.github.wxiaoqi.security.app.vo.clientuser.out.CurrentUserInfosVo;
import com.github.wxiaoqi.security.app.vo.clientuser.out.UserVo;
import com.github.wxiaoqi.security.app.vo.evaluate.in.SaveHousekeeperEvaluate;
import com.github.wxiaoqi.security.app.vo.evaluate.out.DictValueVo;
import com.github.wxiaoqi.security.app.vo.evaluate.out.HousekeeperInfo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 管家评价表
 *
 * @author zxl
 * @Date 2019-01-07 15:20:17
 */
@Service
public class BizEvaluateHousekeeperBiz extends BusinessBiz<BizEvaluateHousekeeperMapper,BizEvaluateHousekeeper> {

    @Autowired
    private BizEvaluateHousekeeperMapper bizEvaluateHousekeeperMapper;
    @Autowired
    private BizEvaluateHousekeeperDetailMapper bizEvaluateHousekeeperDetailMapper;
    @Autowired
    private BizDictMapper bizDictMapper;
    @Autowired
    private BizUserProjectMapper bizUserProjectMapper;
    @Autowired
    private BaseAppClientUserMapper baseAppClientUserMapper;

    /**
     * 保存管家评价
     * @param param
     * @return
     */
    public ObjectRestResponse saveHousekeeperEvaluate(SaveHousekeeperEvaluate param){
        ObjectRestResponse msg = new ObjectRestResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        if(StringUtils.isEmpty(param.getEvaluateType())){
            msg.setStatus(201);
            msg.setMessage("评价不能为空");
            return msg;
        }
        HousekeeperInfo housekeeperInfo = bizEvaluateHousekeeperMapper.selectHousekeeperByUserId(BaseContextHandler.getUserID());
        //查询用户是否评价
        if(housekeeperInfo != null){
            int count = bizEvaluateHousekeeperMapper.selectIsEvaluatetByUser(housekeeperInfo.getHousekeeperId(), BaseContextHandler.getUserID());
            if(count > 0){
                msg.setStatus(201);
                msg.setMessage("您本月已评价过了!");
                return msg;
            }
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
        BizEvaluateHousekeeper housekeeper = new BizEvaluateHousekeeper();
        try {
            housekeeper.setId(UUIDUtils.generateUuid());
            housekeeper.setContent(param.getContent());
            housekeeper.setEvaluateDate(sdf.format(new Date()));
            housekeeper.setEvaluateReason(param.getEvaluateReason());
            housekeeper.setEvaluateType(param.getEvaluateType());
            if(housekeeperInfo != null){
                housekeeper.setHousekeeperId(housekeeperInfo.getHousekeeperId());
            }
            housekeeper.setProjectId(param.getProjectId());
            housekeeper.setUserId(BaseContextHandler.getUserID());
            if(userVo != null){
                housekeeper.setUserName(userVo.getName());
            }
            housekeeper.setTimeStamp(new Date());
            housekeeper.setCreateBy(BaseContextHandler.getUserID());
            housekeeper.setCreateTime(new Date());
            if(bizEvaluateHousekeeperMapper.insertSelective(housekeeper) < 0){
                msg.setStatus(501);
                msg.setMessage("保存管家评价失败!");
                return msg;
            }else{
                //维护数据
                BizEvaluateHousekeeperDetail detail = new BizEvaluateHousekeeperDetail();
                if(housekeeperInfo != null){
                    HousekeeperInfo info =  bizEvaluateHousekeeperDetailMapper.selectHousekeeperInfo(housekeeperInfo.getHousekeeperId());
                    //查询管家的满意数量
                    int count = bizEvaluateHousekeeperMapper.selectAtisfyCount(housekeeperInfo.getHousekeeperId());
                    int total = bizEvaluateHousekeeperMapper.selectEvaluateTotal(housekeeperInfo.getHousekeeperId());
                    if(info != null){
                        detail.setId(info.getDetailId());
                        detail.setEvaluateNum(info.getEvaluateNum()+1);
                        if(total == 0 || count == 0){
                            detail.setSatisfaction("0");
                        }else{
                            detail.setSatisfaction(myPercent(count,total));
                        }
                        detail.setTimeStamp(new Date());
                        detail.setModifyBy(BaseContextHandler.getUserID());
                        detail.setModifyTime(new Date());
                        if(bizEvaluateHousekeeperDetailMapper.updateByPrimaryKeySelective(detail) < 0){
                            msg.setStatus(502);
                            msg.setMessage("维护管家评价数据失败!");
                            return msg;
                        }
                    }else{
                        detail.setId(UUIDUtils.generateUuid());
                        detail.setHousekeeperId(housekeeperInfo.getHousekeeperId());
                        detail.setEvaluateNum(1);
                        if(total == 0 || count == 0){
                            detail.setSatisfaction("0%");
                        }else{
                            detail.setSatisfaction(myPercent(count,total));
                        }
                        detail.setTimeStamp(new Date());
                        detail.setCreateBy(BaseContextHandler.getUserID());
                        detail.setCreateTime(new Date());
                        if(bizEvaluateHousekeeperDetailMapper.insertSelective(detail) < 0){
                            msg.setStatus(502);
                            msg.setMessage("维护管家评价数据失败!");
                            return msg;
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
        DecimalFormat df1 = new DecimalFormat("##%");
// ##.00%
// 百分比格式，后面不足2位的用0补齐
// baifenbi=nf.format(fen);
        baifenbi = df1.format(fen);
        return baifenbi;
    }


    /**
     * 查询首页管家评价详情
     * @return
     */
    public ObjectRestResponse<HousekeeperInfo> getHousekeeperInfo(String projectId){
        HousekeeperInfo housekeeperInfo = bizEvaluateHousekeeperMapper.selectHousekeeperByUserId(BaseContextHandler.getUserID());
        if(housekeeperInfo != null){
            //判断用户是否评价
           int count = bizEvaluateHousekeeperMapper.selectIsEvaluatetByUser(housekeeperInfo.getHousekeeperId(),BaseContextHandler.getUserID());
           String evaluateType = bizEvaluateHousekeeperMapper.selectEvaluateTypeByUser(housekeeperInfo.getHousekeeperId(),BaseContextHandler.getUserID());
           if(count == 0){
               housekeeperInfo.setIsEvaluate("0");
               housekeeperInfo.setEvaluateType("");
           }else{
               housekeeperInfo.setIsEvaluate("1");
               housekeeperInfo.setEvaluateType(evaluateType);
           }
            HousekeeperInfo info =  bizEvaluateHousekeeperDetailMapper.selectHousekeeperInfo(housekeeperInfo.getHousekeeperId());
            if(info != null){
                housekeeperInfo.setEvaluateNum(info.getEvaluateNum());
                housekeeperInfo.setSatisfaction(info.getSatisfaction());
            }else{
                housekeeperInfo.setEvaluateNum(0);
                housekeeperInfo.setSatisfaction("0%");
            }
        }else{
            housekeeperInfo = new HousekeeperInfo();
            ObjectRestResponse msg = new ObjectRestResponse();
            msg.setStatus(201);
            msg.setMessage("当前项目未分配管家!");
            return msg;
        }
        return ObjectRestResponse.ok(housekeeperInfo);
    }


    /**
     * 根据编码查询字典值
     * @param code
     * @return
     */
     public ObjectRestResponse<List<DictValueVo>> getDictValue(String code){
         List<DictValueVo> dictValueVoList = bizDictMapper.selectDictValueList(code);
         if(dictValueVoList == null || dictValueVoList.size() == 0){
             dictValueVoList = new ArrayList<>();
         }
         return ObjectRestResponse.ok(dictValueVoList);
     }





}