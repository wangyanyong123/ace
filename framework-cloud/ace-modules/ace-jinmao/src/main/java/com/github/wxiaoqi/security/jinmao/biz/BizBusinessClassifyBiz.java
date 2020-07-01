package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.DateTimeUtil;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizBusiness;
import com.github.wxiaoqi.security.jinmao.entity.BizBusinessClassify;
import com.github.wxiaoqi.security.jinmao.feign.CodeFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BizBusinessClassifyMapper;
import com.github.wxiaoqi.security.jinmao.vo.Business.OutParam.ResultBusinessVo;
import com.github.wxiaoqi.security.jinmao.vo.Classify.InputParam.EditFirstClassifyParam;
import com.github.wxiaoqi.security.jinmao.vo.Classify.InputParam.SaveClassifyParam;
import com.github.wxiaoqi.security.jinmao.vo.Classify.OutParam.ResultClassifyVo;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 业务商品分类表
 *
 * @author zxl
 * @Date 2018-12-03 10:32:32
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class BizBusinessClassifyBiz extends BusinessBiz<BizBusinessClassifyMapper,BizBusinessClassify> {
    private Logger logger = LoggerFactory.getLogger(BizBusinessClassifyBiz.class);
    @Autowired
    private BizBusinessClassifyMapper bizBusinessClassifyMapper;
    @Autowired
    private CodeFeign codeFeign;

    /**
     * 查询商品分类列表
     * @param busId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ResultClassifyVo> getClassifyList(String busId,String searchVal,Integer page,Integer limit){
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
        List<ResultClassifyVo> classifyVoList = bizBusinessClassifyMapper.selectClassifyList(busId, searchVal, startIndex, limit);
        return classifyVoList;
    }

    /**
     * 查询商品分类数量
     * @param busId
     * @param searchVal
     * @return
     */
    public int selectClassifyCount(String busId,String searchVal){
       int total =  bizBusinessClassifyMapper.selectClassifyCount(busId, searchVal);
       return total;
    }


    /**
     * 删除商品分类
     * @param id
     * @return
     */
    public ObjectRestResponse delClassifyInfo(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(101);
            msg.setMessage("id不能为空!");
            return msg;
        }
        if(bizBusinessClassifyMapper.delClassifyInfo(id, BaseContextHandler.getUserID()) < 0){
            msg.setStatus(102);
            msg.setMessage("删除商品分类失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 保存商品分类
     * @param param
     * @return
     */
    public ObjectRestResponse saveClassify(SaveClassifyParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        ObjectRestResponse objectRestResponse = codeFeign.getCode("Classify","FC","6","0");
        logger.info("生成分类编码处理结果："+objectRestResponse.getData());

        BizBusinessClassify classify = new BizBusinessClassify();
        //String code = "FC" + StringUtils.generateRandomNumber(2);
        classify.setId(UUIDUtils.generateUuid());
        classify.setBusId(param.getBusId());
        if(objectRestResponse.getStatus()==200){
            classify.setClassifyCode(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
        }
        classify.setClassifyName(param.getClassifyName());
        classify.setViewSort(Integer.parseInt(param.getViewSort()));
        classify.setBusStatus(param.getBusStatus());
        classify.setCreateBy(BaseContextHandler.getUserID());
        classify.setTimeStamp(new Date());
        classify.setCreateTime(new Date());
        classify.setImgUrl(param.getImgUrl());
        if(bizBusinessClassifyMapper.insertSelective(classify) < 0){
            msg.setStatus(102);
            msg.setMessage("保存商品分类失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 查询商品分类详情
     * @param id
     * @return
     */
    public List<ResultClassifyVo> getClassifyInfo(String id){
        List<ResultClassifyVo> resultVo = new ArrayList<>();
        ResultClassifyVo classifyInfo =  bizBusinessClassifyMapper.selctClassifyInfo(id);
        if(classifyInfo != null){
            resultVo.add(classifyInfo);
        }else{
            ResultClassifyVo vo = new ResultClassifyVo();
            vo.setId("");
            vo.setBusName("");
            vo.setBusStatus("");
            vo.setClassifyName("");
            vo.setClassifyCode("");
            vo.setViewSort("");
            resultVo.add(vo);
        }
        return resultVo;
    }


    /**
     * 编辑商品分类
     * @param param
     * @return
     */
    public ObjectRestResponse updateClassify(SaveClassifyParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        ResultClassifyVo classifyInfo = bizBusinessClassifyMapper.selctClassifyInfo(param.getId());
        if(classifyInfo != null){
            try {
                BizBusinessClassify classify = new BizBusinessClassify();
                BeanUtils.copyProperties(classify , classifyInfo);
                classify.setClassifyName(param.getClassifyName());
                classify.setViewSort(Integer.parseInt(param.getViewSort()));
                classify.setBusStatus(param.getBusStatus());
                classify.setModifyBy(BaseContextHandler.getUserID());
                classify.setModifyTime(new Date());
                classify.setTimeStamp(new Date());
                classify.setImgUrl(param.getImgUrl());
                if(bizBusinessClassifyMapper.updateByPrimaryKeySelective(classify) < 0){
                    msg.setStatus(101);
                    msg.setMessage("编辑商品分类失败!");
                    return msg;
                }
            } catch (Exception e) {
                logger.error("编辑商品分类失败!",e);
            }
        }else{
            msg.setStatus(102);
            msg.setMessage("编辑商品分类失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    public ObjectRestResponse updateBusStatus(String id,String busStatus){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(101);
            msg.setMessage("id不能为空!");
            return msg;
        }
        if(StringUtils.isEmpty(busStatus)){
            msg.setStatus(102);
            msg.setMessage("分类状态不能为空!");
            return msg;
        }
        if("1".equals(busStatus)){
            if (bizBusinessClassifyMapper.updateClassifyEnableStatus(id,busStatus,BaseContextHandler.getUserID()) < 0){
                logger.error("修改启用失败,id为{}",id);
                msg.setStatus(103);
                msg.setMessage("启用失败！");
                return msg;
            }
        }else{
            if (bizBusinessClassifyMapper.updateClassifyEnableStatus(id,busStatus,BaseContextHandler.getUserID()) < 0){
                logger.error("修改禁用失败,id为{}",id);
                msg.setStatus(103);
                msg.setMessage("禁用失败！");
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 查询所有的订单类型的业务列表
     * @return
     */
    public List<ResultBusinessVo> getBusinessListByType(){
        List<ResultBusinessVo> businessVoList = bizBusinessClassifyMapper.selectBusinessListByType();
        return businessVoList;
    }
}