package com.github.wxiaoqi.security.app.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.BaseAppClientUser;
import com.github.wxiaoqi.security.app.entity.BizGroup;
import com.github.wxiaoqi.security.app.entity.BizIntegralGroupDetail;
import com.github.wxiaoqi.security.app.entity.BizIntegralPersonalDetail;
import com.github.wxiaoqi.security.app.mapper.BaseAppClientUserMapper;
import com.github.wxiaoqi.security.app.mapper.BizGroupMapper;
import com.github.wxiaoqi.security.app.mapper.BizIntegralGroupDetailMapper;
import com.github.wxiaoqi.security.app.mapper.BizIntegralPersonalDetailMapper;
import com.github.wxiaoqi.security.app.vo.group.out.GroupIntegralVo;
import com.github.wxiaoqi.security.app.vo.integral.IntegralPersonalVo;
import com.github.wxiaoqi.security.app.vo.integral.PostsViewInfo;
import com.github.wxiaoqi.security.app.vo.integral.RuleInfo;
import com.github.wxiaoqi.security.app.vo.integral.RuleTheme;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 个人积分明细表
 *
 * @author zxl
 * @Date 2018-12-28 10:17:42
 */
@Service
public class BizIntegralPersonalDetailBiz extends BusinessBiz<BizIntegralPersonalDetailMapper,BizIntegralPersonalDetail> {

    @Autowired
    private BizIntegralPersonalDetailMapper bizIntegralPersonalDetailMapper;
    @Autowired
    private BaseAppClientUserMapper baseAppClientUserMapper;
    @Autowired
    private BizIntegralGroupDetailMapper bizIntegralGroupDetailMapper;
    @Autowired
    private BizGroupMapper bizGroupMapper;


    /**
     * 查询用户个人积分
     * @return
     */
     public ObjectRestResponse<String> getUserIntegralInfo(){
         return ObjectRestResponse.ok(bizIntegralPersonalDetailMapper.selectUserIntegralInfo(BaseContextHandler.getUserID()));
     }


    /**
     * 查询用户积分账单
     * @param createTime
     * @param page
     * @param limit
     * @return
     */
     public ObjectRestResponse<List<IntegralPersonalVo>> getIntegralPersonalDetail(String createTime, Integer page, Integer limit){
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
         List<IntegralPersonalVo> personalVoList = bizIntegralPersonalDetailMapper.selectIntegralPersonalDetail(BaseContextHandler.getUserID(),createTime,startIndex,limit);
         if(personalVoList == null || personalVoList.size() == 0){
             personalVoList = new ArrayList<>();
         }
         return ObjectRestResponse.ok(personalVoList);
     }


    /**
     * 公用加积分接口
     * @param ruleCode
     * @return
     */
     public ObjectRestResponse addPublicIntegarl(String ruleCode, String groupId, String objectId){
         ObjectRestResponse msg = new ObjectRestResponse();
         String userId = BaseContextHandler.getUserID();
         if(StringUtils.isEmpty(ruleCode)){
             msg.setStatus(501);
             msg.setMessage("规则编码不能为空");
             return msg;
         }
         if(StringUtils.isEmpty(userId)){
             msg.setStatus(501);
             msg.setMessage("用户id不能为空");
             return msg;
         }
         //根据规则编码查询规则信息
         RuleInfo ruleInfo = bizIntegralPersonalDetailMapper.selectRuleInfoByCode(ruleCode);
         if(ruleInfo != null){
             msg = getIntegralByCode(userId,groupId, ruleInfo, objectId);
         }
         return msg;
     }

     //添加个人明细
     public void addPersonIntegralDetail(String userId, String ruleId,String ruleName,int creditsValue){
         BizIntegralPersonalDetail person = new BizIntegralPersonalDetail();
         person.setId(UUIDUtils.generateUuid());
         person.setUserId(userId);
         person.setRuleId(ruleId);
         person.setRuleName(ruleName);
         person.setCreditsValue(creditsValue);
         person.setCreateBy(BaseContextHandler.getUserID());
         person.setCreateTime(new Date());
         person.setTimeStamp(new Date());
         int total = bizIntegralPersonalDetailMapper.insertSelective(person);
     }

     //添加小组明细
     public void addGroupIntegralDetail(String postsId, String groupId, String ruleId,String ruleName,int creditsValue){
         BizIntegralGroupDetail group = new BizIntegralGroupDetail();
         group.setId(UUIDUtils.generateUuid());
         group.setObjectId(postsId);
         group.setGroupId(groupId);
         group.setRuleId(ruleId);
         group.setRuleName(ruleName);
         group.setCreditsValue(creditsValue);
         group.setCreateBy(BaseContextHandler.getUserID());
         group.setCreateTime(new Date());
         group.setTimeStamp(new Date());
         int total = bizIntegralGroupDetailMapper.insertSelective(group);
     }

   //更新个人用户积分
    public void updateClientUserIntegral(String userId, int num){
        int creditsValue = baseAppClientUserMapper.selectUserIntegralById(userId);
        BaseAppClientUser user = new BaseAppClientUser();
        user.setId(userId);
        user.setCreditsValue(creditsValue+num);
        baseAppClientUserMapper.updateByPrimaryKeySelective(user);
    }

    //更新小组积分
    public void updateGroupIntegral(String groupId, int num){
        int creditsValue = bizIntegralGroupDetailMapper.selectGroupIntegralById(groupId);
        BizGroup group = new BizGroup();
        group.setId(groupId);
        group.setCreditsValue(creditsValue+num);
        bizGroupMapper.updateByPrimaryKeySelective(group);
    }

    //添加明细
    public void commentIntegral(String userId,String groupId, RuleInfo ruleInfo,String objectId){
         if("1".equals(ruleInfo.getType())){
             //添加个人明细
             addPersonIntegralDetail(userId,ruleInfo.getId(),ruleInfo.getRuleName(),ruleInfo.getCreditsValue());
             //更新个人用户积分
             updateClientUserIntegral(userId,ruleInfo.getCreditsValue());
         }else{
             //添加小组明细
             addGroupIntegralDetail(objectId,groupId,ruleInfo.getId(),ruleInfo.getRuleName(),ruleInfo.getCreditsValue());
             updateGroupIntegral(groupId,ruleInfo.getCreditsValue());
         }
    }


    //根据规则编码进行加积分操作
    public ObjectRestResponse getIntegralByCode(String userId,String groupId, RuleInfo ruleInfo, String objectId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
        ObjectRestResponse msg = new ObjectRestResponse();
        //查询小组的帖子是否领取
        int groupSacle = bizIntegralPersonalDetailMapper.selectGroupRuleInfoByUserId(groupId,ruleInfo.getId(),objectId);
        //总无上限
        if(-1 == ruleInfo.getCreditsUpperTotal()){
            //月无上限
            if(-1==ruleInfo.getCreditsUpperMonth()){
                //日无上限
                if(-1==ruleInfo.getCreditsUpperDay()){
                    //添加明细
                    commentIntegral(userId,groupId,ruleInfo,objectId);
                }else{
                    //日有上限
                    int user = bizIntegralPersonalDetailMapper.selectRuleInfoByUserId(userId,ruleInfo.getId(),sdf.format(new Date()));
                    if(user >= ruleInfo.getCreditsUpperDay() || groupSacle >=ruleInfo.getCreditsUpperDay() ){
                        //日积分超过上限
                        msg.setStatus(501);
                        msg.setMessage("日积分领取已上限!");
                        return msg;
                    }else{
                        //添加明细
                        commentIntegral(userId,groupId,ruleInfo,objectId);
                    }
                }
            }else{
                //月有上限
                int muser = bizIntegralPersonalDetailMapper.selectRuleInfoByUserId(userId,ruleInfo.getId(),sdf1.format(new Date()));
                if(muser >= ruleInfo.getCreditsUpperMonth() || groupSacle >= ruleInfo.getCreditsUpperMonth()){
                    //月积分超过上限
                    msg.setStatus(501);
                    msg.setMessage("月积分领取已上限!");
                    return msg;
                }else{
                    //日无上限
                    if(-1==ruleInfo.getCreditsUpperDay()){
                        //添加明细
                        commentIntegral(userId,groupId,ruleInfo,objectId);
                    }else{
                        //日有上限
                        int duser = bizIntegralPersonalDetailMapper.selectRuleInfoByUserId(userId,ruleInfo.getId(),sdf.format(new Date()));
                        if(duser >= ruleInfo.getCreditsUpperDay() || groupSacle >= ruleInfo.getCreditsUpperDay()){
                            //日积分超过上限
                            msg.setStatus(501);
                            msg.setMessage("日积分领取已上限!");
                            return msg;
                        }else{
                            //添加明细
                            commentIntegral(userId,groupId,ruleInfo,objectId);
                        }
                    }
                }
            }
        }else{
            //总积分上限
            int tuser = bizIntegralPersonalDetailMapper.selectRuleInfoByUserId(userId,ruleInfo.getId(),"");
            if(tuser >= ruleInfo.getCreditsUpperTotal() || groupSacle >= ruleInfo.getCreditsUpperTotal()){
                //月积分超过上限
                msg.setStatus(501);
                msg.setMessage("总积分领取已上限!");
                return msg;
            }else{
                //月无上限
                if(-1==ruleInfo.getCreditsUpperMonth()){
                    //日无上限
                    if(-1==ruleInfo.getCreditsUpperDay()){
                        //添加明细
                        commentIntegral(userId,groupId,ruleInfo,objectId);
                    }else{
                        //日有上限
                        int user = bizIntegralPersonalDetailMapper.selectRuleInfoByUserId(userId,ruleInfo.getId(),sdf.format(new Date()));
                        if(user >= ruleInfo.getCreditsUpperDay() || groupSacle >= ruleInfo.getCreditsUpperDay()){
                            //日积分超过上限
                            msg.setStatus(501);
                            msg.setMessage("日积分领取已上限!");
                            return msg;
                        }else{
                            //添加明细
                            commentIntegral(userId,groupId,ruleInfo,objectId);
                        }
                    }
                }else{
                    //月有上限
                    int muser = bizIntegralPersonalDetailMapper.selectRuleInfoByUserId(userId,ruleInfo.getId(),sdf1.format(new Date()));
                    if(muser >= ruleInfo.getCreditsUpperMonth() || groupSacle >= ruleInfo.getCreditsUpperMonth()){
                        //月积分超过上限
                        msg.setStatus(501);
                        msg.setMessage("月积分领取已上限!");
                        return msg;
                    }else{
                        //日无上限
                        if(-1==ruleInfo.getCreditsUpperDay()){
                            //添加明细
                            commentIntegral(userId,groupId,ruleInfo,objectId);
                        }else{
                            //日有上限
                            int duser = bizIntegralPersonalDetailMapper.selectRuleInfoByUserId(userId,ruleInfo.getId(),sdf.format(new Date()));
                            if(duser >= ruleInfo.getCreditsUpperDay() || groupSacle >= ruleInfo.getCreditsUpperDay()){
                                //日积分超过上限
                                msg.setStatus(501);
                                msg.setMessage("日积分领取已上限!");
                                return msg;
                            }else{
                                //添加明细
                                commentIntegral(userId,groupId,ruleInfo,objectId);
                            }
                        }
                    }
                }
            }
        }
        return msg;
    }





}