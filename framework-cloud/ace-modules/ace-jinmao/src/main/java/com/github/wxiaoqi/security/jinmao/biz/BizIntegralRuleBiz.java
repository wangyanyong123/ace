package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizIntegralRule;
import com.github.wxiaoqi.security.jinmao.feign.CodeFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BizIntegralRuleMapper;
import com.github.wxiaoqi.security.jinmao.vo.rule.in.SaveRuleParam;
import com.github.wxiaoqi.security.jinmao.vo.rule.in.UpdateRuleStatus;
import com.github.wxiaoqi.security.jinmao.vo.rule.out.RuleVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 个人积分规则配置表
 *
 * @author zxl
 * @Date 2018-12-21 17:43:42
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class BizIntegralRuleBiz extends BusinessBiz<BizIntegralRuleMapper,BizIntegralRule> {

    private Logger logger = LoggerFactory.getLogger(BizIntegralRuleBiz.class);
    @Autowired
    private BizIntegralRuleMapper bizIntegralRuleMapper;
    @Autowired
    private CodeFeign codeFeign;

    /**
     * 查询积分规则列表
     * @param ruleStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<RuleVo> getRuleList(String type ,String ruleStatus, String searchVal, Integer page, Integer limit){
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
        List<RuleVo> ruleVoList = bizIntegralRuleMapper.selectRuleList(type,ruleStatus, searchVal, startIndex, limit);
        if(ruleVoList == null || ruleVoList.size() == 0){
            ruleVoList = new ArrayList<>();
        }
        return ruleVoList;
    }


    /**
     * 查询积分规则列表数量
     * @param ruleStatus
     * @param searchVal
     * @return
     */
    public int selectRuleCount(String type ,String ruleStatus, String searchVal){
        int total = bizIntegralRuleMapper.selectRuleCount(type,ruleStatus, searchVal);
        return total;
    }


    /**
     * 保存积分规则
     * @param param
     * @return
     */
    public ObjectRestResponse SaveIntegraRule(SaveRuleParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        ObjectRestResponse objectRestResponse = codeFeign.getCode("Rule","A","5","0");
        logger.info("生成商品编码处理结果："+objectRestResponse.getData());
        if(StringUtils.isEmpty(param.getRuleName())){
            msg.setStatus(201);
            msg.setMessage("规则名称不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getRuleDesc())){
            msg.setStatus(202);
            msg.setMessage("规则说明不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getCreditsValue())){
            msg.setStatus(203);
            msg.setMessage("积分值不能为空!");
            return msg;
        }
        BizIntegralRule rule = new BizIntegralRule();
        rule.setId(UUIDUtils.generateUuid());
        if(objectRestResponse.getStatus()==200){
            rule.setRuleCode(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
        }
        rule.setRuleName(param.getRuleName());
        rule.setRuleDesc(param.getRuleDesc());
        rule.setCreditsValue(Integer.parseInt(param.getCreditsValue()));
       if(StringUtils.isEmpty(param.getCreditsUpperDay())){
           rule.setCreditsUpperDay(-1);
       }else{
           rule.setCreditsUpperDay(Integer.parseInt(param.getCreditsUpperDay()));
       }
        if(StringUtils.isEmpty(param.getCreditsUpperMonth())){
            rule.setCreditsUpperMonth(-1);
        }else{
            rule.setCreditsUpperMonth(Integer.parseInt(param.getCreditsUpperMonth()));
        }
        if(StringUtils.isEmpty(param.getCreditsUpperTotal())){
            rule.setCreditsUpperTotal(-1);
        }else{
            rule.setCreditsUpperTotal(Integer.parseInt(param.getCreditsUpperTotal()));
        }
        rule.setType(param.getType());
        rule.setCreateBy(BaseContextHandler.getUserID());
        rule.setCreateTime(new Date());
        rule.setTimeStamp(new Date());
        if(bizIntegralRuleMapper.insertSelective(rule) < 0){
            msg.setStatus(501);
            msg.setMessage("保存积分规则失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }



    /**
     * 积分规则操作(0-删除,2-已启用,3-已停用)
     * @param param
     * @return
     */
    public ObjectRestResponse updateRuleStatus(UpdateRuleStatus param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(bizIntegralRuleMapper.updateRuleStatus(BaseContextHandler.getUserID(),param.getStatus(),param.getId()) < 0){
            msg.setStatus(201);
            msg.setMessage("积分规则操作失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询积分规则详情
     * @param id
     * @return
     */
    public List<RuleVo> getRuleInfo(String id){
        List<RuleVo> returnVo = new ArrayList<>();
        RuleVo ruleInfo =  bizIntegralRuleMapper.selectRuleInfo(id);
        if(ruleInfo == null){
            ruleInfo = new RuleVo();
        }else{
            if("-1".equals(ruleInfo.getCreditsUpperDay())){
                ruleInfo.setCreditsUpperDay("");
            }
            if("-1".equals(ruleInfo.getCreditsUpperMonth())){
                ruleInfo.setCreditsUpperMonth("");
            }
            if("-1".equals(ruleInfo.getCreditsUpperTotal())){
                ruleInfo.setCreditsUpperTotal("");
            }
        }
        returnVo.add(ruleInfo);
        return returnVo;
    }


    /**
     * 编辑积分规则
     * @param param
     * @return
     */
    public ObjectRestResponse updateIntegraRule(SaveRuleParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(param.getRuleName())){
            msg.setStatus(201);
            msg.setMessage("规则名称不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getRuleDesc())){
            msg.setStatus(202);
            msg.setMessage("规则说明不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(param.getCreditsValue())){
            msg.setStatus(203);
            msg.setMessage("积分值不能为空!");
            return msg;
        }
        RuleVo ruleInfo =  bizIntegralRuleMapper.selectRuleInfo(param.getId());
        if(ruleInfo != null){
            BizIntegralRule rule = new BizIntegralRule();
            try {
                BeanUtils.copyProperties(rule,ruleInfo);
                rule.setRuleName(param.getRuleName());
                rule.setRuleDesc(param.getRuleDesc());
                rule.setCreditsValue(Integer.parseInt(param.getCreditsValue()));
                if(StringUtils.isEmpty(param.getCreditsUpperDay())){
                    rule.setCreditsUpperDay(-1);
                }else{
                    rule.setCreditsUpperDay(Integer.parseInt(param.getCreditsUpperDay()));
                }
                if(StringUtils.isEmpty(param.getCreditsUpperMonth())){
                    rule.setCreditsUpperMonth(-1);
                }else{
                    rule.setCreditsUpperMonth(Integer.parseInt(param.getCreditsUpperMonth()));
                }
                if(StringUtils.isEmpty(param.getCreditsUpperTotal())){
                    rule.setCreditsUpperTotal(-1);
                }else{
                    rule.setCreditsUpperTotal(Integer.parseInt(param.getCreditsUpperTotal()));
                }
                rule.setType(param.getType());
                rule.setModifyBy(BaseContextHandler.getUserID());
                rule.setModifyTime(new Date());
                rule.setTimeStamp(new Date());
                if(bizIntegralRuleMapper.updateByPrimaryKeySelective(rule) < 0){
                    msg.setStatus(501);
                    msg.setMessage("编辑积分规则失败!");
                    return msg;
                }
            } catch (Exception e) {
                logger.error("编辑积分规则失败!",e);
            }
        }else{
            msg.setStatus(502);
            msg.setMessage("查询详情失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }




}