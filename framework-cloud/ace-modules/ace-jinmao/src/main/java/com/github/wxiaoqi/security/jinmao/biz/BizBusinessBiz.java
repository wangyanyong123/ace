package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizBusiness;
import com.github.wxiaoqi.security.jinmao.feign.CodeFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BizBusinessMapper;
import com.github.wxiaoqi.security.jinmao.vo.Business.InputParam.SaveBusinessParam;
import com.github.wxiaoqi.security.jinmao.vo.Business.OutParam.ResultBusinessInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.Business.OutParam.ResultBusinessVo;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 业务表
 *
 * @author zxl
 * @Date 2018-12-03 10:32:32
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class BizBusinessBiz extends BusinessBiz<BizBusinessMapper,BizBusiness> {
    private Logger logger = LoggerFactory.getLogger(BizBusinessBiz.class);
    @Autowired
    private BizBusinessMapper bizBusinessMapper;
    @Autowired
    private CodeFeign codeFeign;

    /**
     * 查询业务列表
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ResultBusinessVo> getBusinessList(String searchVal,Integer page,Integer limit){
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
        List<ResultBusinessVo> businessVoList = bizBusinessMapper.selectBusinessList(searchVal, startIndex, limit);
        return businessVoList;
    }

    /**
     * 查询业务列表数量
     * @param searchVal
     * @return
     */
    public int selectBusinessCount(String searchVal){
        int total = bizBusinessMapper.selectBusinessCount(searchVal);
        return total;
    }


    /**
     * 保存业务
     * @param param
     * @return
     */
    public ObjectRestResponse saveBusiness(SaveBusinessParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        //String code = "BSC"+StringUtils.generateRandomNumber(4);
        ObjectRestResponse objectRestResponse = codeFeign.getCode("Business","BS","6","0");
        logger.info("生成业务编码处理结果："+objectRestResponse.getData());

        BizBusiness business = new BizBusiness();
        business.setId(UUIDUtils.generateUuid());
        if(objectRestResponse.getStatus()==200){
            business.setBusCode(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
        }
        business.setImgUrl(param.getImgUrl());
        business.setBusName(param.getBusName());
        business.setDescription(param.getDescription());
        business.setViewSort(Integer.parseInt(param.getViewSort()));
        business.setType(param.getType());
        business.setCreateType(param.getCreateType());
        business.setCreateBy(BaseContextHandler.getUserID());
        business.setTimeStamp(new Date());
        business.setCreateTime(new Date());
        if(bizBusinessMapper.insertSelective(business) < 0){
            msg.setStatus(101);
            msg.setMessage("保存业务失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 删除业务
     * @param id
     * @return
     */
    public ObjectRestResponse delBusinessInfo(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(101);
            msg.setMessage("id不能为空!");
            return msg;
        }
        if(bizBusinessMapper.delBusinessInfo(id, BaseContextHandler.getUserID()) < 0){
            msg.setStatus(102);
            msg.setMessage("删除业务失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询业务详情
     * @param id
     * @return
     */
    public List<ResultBusinessInfoVo> getBusinessInfo(String id){
        List<ResultBusinessInfoVo> resultVo = new ArrayList<>();
        ResultBusinessInfoVo businessInfoVo = bizBusinessMapper.selectBusinessInfo(id);
        if(businessInfoVo != null){
            resultVo.add(businessInfoVo);
        }else{
            ResultBusinessInfoVo info = new ResultBusinessInfoVo();
            resultVo.add(info);
        }
        return resultVo;
    }


    /**
     * 编辑业务
     * @param param
     * @return
     */
    public ObjectRestResponse updateBusiness(SaveBusinessParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        ResultBusinessInfoVo businessInfoVo = bizBusinessMapper.selectBusinessInfo(param.getId());
        if(businessInfoVo != null){
            try {
                BizBusiness business = new BizBusiness();
                BeanUtils.copyProperties(business , businessInfoVo);
                business.setBusName(param.getBusName());
                business.setDescription(param.getDescription());
                business.setViewSort(Integer.parseInt(param.getViewSort()));
                business.setType(param.getType());
                business.setCreateType(param.getCreateType());
                business.setModifyBy(BaseContextHandler.getUserID());
                business.setModifyTime(new Date());
                business.setTimeStamp(new Date());
                business.setImgUrl(param.getImgUrl());
                if(bizBusinessMapper.updateByPrimaryKeySelective(business) < 0){
                    msg.setStatus(101);
                    msg.setMessage("编辑业务失败!");
                    return msg;
                }
            } catch (Exception e) {
                logger.error("编辑业务失败!",e);
            }
        }else{
            msg.setStatus(102);
            msg.setMessage("编辑业务失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }
}