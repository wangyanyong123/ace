package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.brainpower.out.QuestionVo;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizBrainpowerFunction;
import com.github.wxiaoqi.security.jinmao.entity.BizBrainpowerQuestion;
import com.github.wxiaoqi.security.jinmao.entity.BizQuestionClassify;
import com.github.wxiaoqi.security.jinmao.feign.SearchFeign;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.BizBrainpowerFunctionMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizBrainpowerQuestionMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizQuestionClassifyMapper;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.in.SaveFunctionParam;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.in.SaveQuestionParam;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.in.UpdateIsShowParam;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.out.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 智能客服问答表
 *
 * @author huangxl
 * @Date 2019-04-10 18:24:34
 */
@Service
@Slf4j
public class BizBrainpowerQuestionBiz extends BusinessBiz<BizBrainpowerQuestionMapper,BizBrainpowerQuestion> {

    @Autowired
    private BizBrainpowerFunctionMapper bizBrainpowerFunctionMapper;
    @Autowired
    private BizBrainpowerQuestionMapper bizBrainpowerQuestionMapper;
    @Autowired
    private BizQuestionClassifyMapper bizQuestionClassifyMapper;
    @Autowired
    private SearchFeign searchFeign;
    @Autowired
    private ToolFegin toolFeign;


    /**
     * 保存功能点
     * @param param
     * @return
     */
    public ObjectRestResponse saveFunction (SaveFunctionParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(201);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(param.getFunctionDictList().size() == 0 || param.getFunctionDictList() == null){
            msg.setStatus(201);
            msg.setMessage("功能点不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(param.getDescription())){
            msg.setStatus(201);
            msg.setMessage("功能点内容不能为空");
            return msg;
        }
        for (ResultFunctionDictList vo : param.getFunctionDictList()){
            if(bizBrainpowerFunctionMapper.selectIsFunctionByCode(vo.getCode()) > 0){
                msg.setStatus(201);
                msg.setMessage("该功能点已存在");
                return msg;
            }
        }
        StringBuilder sb = new StringBuilder();
        BizBrainpowerFunction function = new BizBrainpowerFunction();
        function.setId(UUIDUtils.generateUuid());
        for (ResultFunctionDictList vo : param.getFunctionDictList()){
            function.setCode(vo.getCode());
            function.setFunctionPoint(vo.getFunctionPoint());
        }
        function.setDescription(param.getDescription());
        for (ResultJumpLinkList vo : param.getJumpLinkList()){
            function.setJumpCode(vo.getJumpCode());
            function.setJumpLink(vo.getJumpLink());
        }
        String pictures = "";
        if(param.getPictureList() != null && param.getPictureList().size() >0){
            for(ImgInfo temp: param.getPictureList()){
                sb.append(temp.getUrl()+",");
            }
            pictures = sb.substring(0,sb.length()-1);
        }
        if(StringUtils.isNotEmpty(pictures)){
            ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(pictures, DocPathConstant.PROPERTY);
            if(objectRestResponse.getStatus()==200){
                function.setPicture(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
            }
        }
        //function.setPicture(pictures);
        function.setViewSort(999);
        function.setCreateBy(BaseContextHandler.getUserID());
        function.setCreateTime(new Date());
        function.setTimeStamp(new Date());
        if(bizBrainpowerFunctionMapper.insertSelective(function) < 0){
            msg.setStatus(501);
            msg.setMessage("创建失败");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 查询功能点列表
     * @param code
     * @param enableStatus
     * @param page
     * @param limit
     * @return
     */
    public List<FunctionVo> getFunctionList(String code, String enableStatus, Integer page, Integer limit){
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
        List<FunctionVo> functionVoList = bizBrainpowerFunctionMapper.selectFunctionList(code, enableStatus, startIndex, limit);
        if(functionVoList == null || functionVoList.size() == 0){
            functionVoList = new ArrayList<>();
        }
        return functionVoList;
    }


    public int selectFunctionCount(String code, String enableStatus){
        return bizBrainpowerFunctionMapper.selectFunctionCount(code, enableStatus);
    }


    public List<FunctionVo> selectFunctionSearchVal(){
        List<FunctionVo> functionVoList = bizBrainpowerFunctionMapper.selectFunctionSearchVal();
        if(functionVoList == null || functionVoList.size() == 0){
            functionVoList = new ArrayList<>();
        }
        return functionVoList;
    }

    /**
     * 查询置底功能点列表
     * @return
     */
    public List<ViewSortVo> selectViewSortVo(){
        List<ViewSortVo> sortVoList = bizBrainpowerFunctionMapper.selectViewSortVo();
        if(sortVoList == null || sortVoList.size() == 0){
            sortVoList = new ArrayList<>();
        }
        return sortVoList;
    }


    /**
     * 查询功能点详情
     * @param id
     * @return
     */
    public List<FunctionInfo> getFunctionInfo(String id){
        List<FunctionInfo> functionInfos = new ArrayList<>();
        FunctionInfo functionInfo = bizBrainpowerFunctionMapper.selectFunctionInfo(id);
        if(functionInfo != null){
            List<ResultJumpLinkList> jumpLinkList = new ArrayList<>();
            ResultJumpLinkList info = new ResultJumpLinkList();
            info.setJumpCode(functionInfo.getJumpCode());
            info.setJumpLink(functionInfo.getJumpLink());
            jumpLinkList.add(info);
            functionInfo.setJumpLinkList(jumpLinkList);

            List<ResultFunctionDictList> functionDictList = new ArrayList<>();
            ResultFunctionDictList functioninfo = new ResultFunctionDictList();
            functioninfo.setCode(functionInfo.getCode());
            functioninfo.setFunctionPoint(functionInfo.getFunctionPoint());
            functionDictList.add(functioninfo);
            functionInfo.setFunctionDictList(functionDictList);

            //处理图片
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            if(functionInfo.getPictureList() != null){
                String[] pictures = functionInfo.getPicture().split(",");
                for(String url : pictures){
                    imgInfo.setUrl(url);
                    list.add(imgInfo);
                }
                functionInfo.setPictureList(list);
            }
        }else{
            functionInfo = new FunctionInfo();
        }
        functionInfos.add(functionInfo);
        return functionInfos;
    }


    /**
     * 编辑功能点
     * @param param
     * @return
     */
    public ObjectRestResponse updateFuction (SaveFunctionParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(201);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(param.getDescription())){
            msg.setStatus(201);
            msg.setMessage("功能点内容不能为空");
            return msg;
        }
        FunctionInfo functionInfo = bizBrainpowerFunctionMapper.selectFunctionInfo(param.getId());
        StringBuilder sb = new StringBuilder();
        BizBrainpowerFunction function = new BizBrainpowerFunction();
        if(functionInfo != null) {
            if("2".equals(functionInfo.getEnableStatus())){
                msg.setStatus(201);
                msg.setMessage("该功能点已发布,不可编辑");
                return msg;
            }
            BeanUtils.copyProperties(functionInfo, function);
            function.setDescription(param.getDescription());
            for (ResultJumpLinkList vo : param.getJumpLinkList()){
                function.setJumpCode(vo.getJumpCode());
                function.setJumpLink(vo.getJumpLink());
            }
            String pictures = "";
            if(param.getPictureList() != null && param.getPictureList().size() >0){
                for(ImgInfo temp: param.getPictureList()){
                    sb.append(temp.getUrl()+",");
                }
                pictures = sb.substring(0,sb.length()-1);
            }
            if(StringUtils.isNotEmpty(pictures)){
                ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(pictures, DocPathConstant.PROPERTY);
                if(objectRestResponse.getStatus()==200){
                    function.setPicture(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                }
            }
            //function.setPicture(pictures);
            function.setModifyBy(BaseContextHandler.getUserID());
            function.setModifyTime(new Date());
            function.setTimeStamp(new Date());
            if (bizBrainpowerFunctionMapper.updateByPrimaryKeySelective(function) < 0) {
                msg.setStatus(501);
                msg.setMessage("创建失败");
                return msg;
            }
        }else{
            msg.setStatus(501);
            msg.setMessage("查无此详情");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 置底，发布，撤回操作
     * @param param
     * @return
     */
    public ObjectRestResponse updateIsShowOrFunctionStatus(UpdateIsShowParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if("1".equals(param.getStatus()) || "0".equals(param.getStatus())){
            List<String> list = bizBrainpowerFunctionMapper.selectFunctionByCode(param.getId());
            for(String temp:list){
                if(bizBrainpowerFunctionMapper.updatefunctionIsShow(param.getStatus(),BaseContextHandler.getUserID(),temp) < 0){
                    msg.setStatus(501);
                    msg.setMessage("操作失败");
                    return msg;
                }
            }
        }
        if("2".equals(param.getStatus()) || "3".equals(param.getStatus())){
            if("2".equals(param.getStatus())){//撤回
                FunctionInfo functionInfo = bizBrainpowerFunctionMapper.selectFunctionInfo(param.getId());
                if(functionInfo != null){
                    if("3".equals(functionInfo.getEnableStatus())){
                        if(bizBrainpowerFunctionMapper.selectIsFunctionByCode(functionInfo.getCode()) > 0){
                            msg.setStatus(201);
                            msg.setMessage("该功能点已存在");
                            return msg;
                        }
                    }
                }
            }
            if(bizBrainpowerFunctionMapper.updatefunctionStatus(param.getStatus(),BaseContextHandler.getUserID(),param.getId()) < 0){
                msg.setStatus(501);
                msg.setMessage("操作失败");
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 置底，发布，撤回操作
     * @param param
     * @return
     */
    public ObjectRestResponse updateQuestionStatus(UpdateIsShowParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(bizBrainpowerQuestionMapper.updatequestionStatus(param.getStatus(),BaseContextHandler.getUserID(),param.getId()) < 0){
            msg.setStatus(501);
            msg.setMessage("操作失败");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 修改排序
     * @param param
     * @return
     */
    public ObjectRestResponse updateViewSort(UpdateIsShowParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        List<Map<String,String>> mapList =  param.getSortInfo();
        for (Map<String,String> module:mapList) {
            if(module.get("sort") != "" && module.get("sort") != null){
                if(bizBrainpowerFunctionMapper.updateViewSort(module.get("sort"),BaseContextHandler.getUserID(),module.get("id")) < 0){
                    msg.setStatus(501);
                    msg.setMessage("操作失败");
                    return msg;
                }
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 功能点列表
     * @param code
     * @return
     */
    public List<ResultFunctionDictList> getDictValueList(String code){
        List<ResultFunctionDictList> result = new ArrayList<>();
        //查询字典中配置的功能点
        List<ResultFunctionDictList> dictValueList = bizBrainpowerFunctionMapper.selectDictValueList(code);
        List<ResultFunctionDictList> functionDictList = bizBrainpowerFunctionMapper.seelctFunctionPointList();
        result.addAll(dictValueList);
        result.removeAll(functionDictList);
        return result;
    }

    public List<ResultJumpLinkList> getDictValueList2(String code){
        //查询字典中配置的功能点
        List<ResultJumpLinkList> dictValueList = bizBrainpowerFunctionMapper.selectDictValueList2(code);
        return dictValueList;
    }


    /**
     * 保存问题
     * @param param
     * @return
     */
    public ObjectRestResponse saveQuestion(SaveQuestionParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(201);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(param.getFunctionId())){
            msg.setStatus(201);
            msg.setMessage("功能点id不能为空");
            return msg;
        }
        if(StringUtils.isAnyoneEmpty(param.getQuestion(),param.getAnswer(),param.getClassify())){
            msg.setStatus(201);
            msg.setMessage("问题,答案，所属分类都不能为空");
            return msg;
        }
        StringBuilder sb = new StringBuilder();
        BizBrainpowerQuestion question = new BizBrainpowerQuestion();
        String id = UUIDUtils.generateUuid();
        question.setId(id);
        question.setFunctionId(param.getFunctionId());
        question.setQuestion(param.getQuestion());
        for (ResultJumpLinkList vo : param.getJumpLinkList()){
            question.setJumpCode(vo.getJumpCode());
            question.setJumpLink(vo.getJumpLink());
        }
        String pictures = "";
        if(param.getPictureList() != null && param.getPictureList().size() >0){
            for(ImgInfo temp: param.getPictureList()){
                sb.append(temp.getUrl()+",");
            }
            pictures = sb.substring(0,sb.length()-1);
        }
        if(StringUtils.isNotEmpty(pictures)){
            ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(pictures, DocPathConstant.PROPERTY);
            if(objectRestResponse.getStatus()==200){
                question.setPicture(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
            }
        }
        //question.setPicture(pictures);
        question.setAnswer(param.getAnswer());
        question.setSolveNumber("0");
        question.setUnsolveNumber("0");
        question.setCreateBy(BaseContextHandler.getUserID());
        question.setCreateTime(new Date());
        question.setTimeStamp(new Date());
        if(bizBrainpowerQuestionMapper.insertSelective(question) < 0){
            msg.setStatus(501);
            msg.setMessage("创建失败");
            return msg;
        }else{
            BizQuestionClassify classify = new BizQuestionClassify();
            classify.setId(UUIDUtils.generateUuid());
            classify.setQuestionId(id);
            classify.setClassify(param.getClassify());
            classify.setCreateBy(BaseContextHandler.getUserID());
            classify.setCreateTime(new Date());
            classify.setTimeStamp(new Date());
            bizQuestionClassifyMapper.insertSelective(classify);
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询问题库列表
     * @param functionId
     * @param enableStatus
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<QuestionVo> getQuestionList(String status, String functionId, String enableStatus,String searchVal,Integer page, Integer limit){
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
        List<QuestionVo> questionVoList = bizBrainpowerQuestionMapper.selectQuestionList(status,functionId, enableStatus, searchVal, startIndex, limit);
        if(questionVoList == null || questionVoList.size() == 0){
            questionVoList = new ArrayList<>();
        }
        return questionVoList;
    }



    public int selectQuestionCount(String functionId, String enableStatus,String searchVal){
        return bizBrainpowerQuestionMapper.selectQuestionCount(functionId, enableStatus, searchVal);
    }



    /**
     * 查询问题详情
     * @param id
     * @return
     */
    public List<QuestionInfo> getQuestionInfo(String id){
        List<QuestionInfo> questionInfos = new ArrayList<>();
        QuestionInfo questionInfo = bizBrainpowerQuestionMapper.selectQuestionInfo(id);
        if(questionInfo != null){
            List<ResultJumpLinkList> jumpLinkList = new ArrayList<>();
            ResultJumpLinkList info = new ResultJumpLinkList();
            info.setJumpCode(questionInfo.getJumpCode());
            info.setJumpLink(questionInfo.getJumpLink());
            jumpLinkList.add(info);
            questionInfo.setJumpLinkList(jumpLinkList);
            //处理图片
            List<ImgInfo> list = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            if(questionInfo.getPictureList() != null){
                String[] pictures = questionInfo.getPicture().split(",");
                for(String url : pictures){
                    imgInfo.setUrl(url);
                    list.add(imgInfo);
                }
                questionInfo.setPictureList(list);
            }
        }else{
            questionInfo = new QuestionInfo();
        }
        questionInfos.add(questionInfo);
        return questionInfos;
    }


    /**
     * 编辑问题
     * @param param
     * @return
     */
    public ObjectRestResponse updateQuestion(SaveQuestionParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(201);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(param.getFunctionId())){
            msg.setStatus(201);
            msg.setMessage("功能点id不能为空");
            return msg;
        }
        if(StringUtils.isAnyoneEmpty(param.getQuestion(),param.getAnswer(),param.getClassify())){
            msg.setStatus(201);
            msg.setMessage("问题,答案，所属分类都不能为空");
            return msg;
        }
        StringBuilder sb = new StringBuilder();
        BizBrainpowerQuestion question = new BizBrainpowerQuestion();
        QuestionInfo questionInfo = bizBrainpowerQuestionMapper.selectQuestionInfo(param.getId());
        if(questionInfo != null){
            if("2".equals(questionInfo.getEnableStatus())){
                msg.setStatus(201);
                msg.setMessage("该问题已发布,不可编辑");
                return msg;
            }
            BeanUtils.copyProperties(questionInfo,question);
            question.setFunctionId(param.getFunctionId());
            question.setQuestion(param.getQuestion());
            for (ResultJumpLinkList vo : param.getJumpLinkList()){
                question.setJumpCode(vo.getJumpCode());
                question.setJumpLink(vo.getJumpLink());
            }
            String pictures = "";
            if(param.getPictureList() != null && param.getPictureList().size() >0){
                for(ImgInfo temp: param.getPictureList()){
                    sb.append(temp.getUrl()+",");
                }
                pictures = sb.substring(0,sb.length()-1);
            }
            if(StringUtils.isNotEmpty(pictures)){
                ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(pictures, DocPathConstant.PROPERTY);
                if(objectRestResponse.getStatus()==200){
                    question.setPicture(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                }
            }
            //question.setPicture(pictures);
            question.setAnswer(param.getAnswer());
            question.setModifyBy(BaseContextHandler.getUserID());
            question.setModifyTime(new Date());
            question.setTimeStamp(new Date());
            if(bizBrainpowerQuestionMapper.updateByPrimaryKeySelective(question) < 0){
                msg.setStatus(501);
                msg.setMessage("编辑失败");
                return msg;
            }else{
               if(!param.getClassify().equals(questionInfo.getClassify())){
                   if(bizBrainpowerQuestionMapper.delClassifyStatus(BaseContextHandler.getUserID(),questionInfo.getId()) > 0){
                       BizQuestionClassify classify = new BizQuestionClassify();
                       classify.setId(UUIDUtils.generateUuid());
                       classify.setQuestionId(questionInfo.getId());
                       classify.setClassify(param.getClassify());
                       classify.setCreateBy(BaseContextHandler.getUserID());
                       classify.setCreateTime(new Date());
                       classify.setTimeStamp(new Date());
                       bizQuestionClassifyMapper.insertSelective(classify);
                   }
               }
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }



    /**
     * 查询功能点列表
     * @return
     */
    public List<FunctionVo> getFunctionVo(){
        List<FunctionVo> functionVoList = bizBrainpowerQuestionMapper.selectFunctionVo();
        if(functionVoList == null || functionVoList.size() == 0){
            functionVoList = new ArrayList<>();
        }
        return functionVoList;
    }


    /**
     * 查询分类列表
     * @return
     */
    public List<ClassifyVo> getClassifyList(){
        List<ClassifyVo> classifyList = bizBrainpowerQuestionMapper.selectClassifyList();
        if(classifyList == null || classifyList.size() == 0){
            classifyList = new ArrayList<>();
        }
        return classifyList;
    }

}