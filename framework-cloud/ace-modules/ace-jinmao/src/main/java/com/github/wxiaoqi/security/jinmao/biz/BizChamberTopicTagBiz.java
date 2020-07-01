package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizChamberTopicTag;
import com.github.wxiaoqi.security.jinmao.entity.BizChamberTopicTagProject;
import com.github.wxiaoqi.security.jinmao.feign.CodeFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BizChamberTopicTagMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizChamberTopicTagProjectMapper;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ResultProjectVo;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.in.ChanberTopicTagParam;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.ChamberTopicTagInfo;
import com.github.wxiaoqi.security.jinmao.vo.chamberTopic.out.ChamberTopicTagVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 议事厅话题标签表
 *
 * @author huangxl
 * @Date 2019-08-05 11:36:13
 */
@Service
public class BizChamberTopicTagBiz extends BusinessBiz<BizChamberTopicTagMapper,BizChamberTopicTag> {

    private Logger logger = LoggerFactory.getLogger(BizChamberTopicTagBiz.class);
    @Autowired
    private BizChamberTopicTagMapper bizChamberTopicTagMapper;
    @Autowired
    private BizChamberTopicTagProjectMapper bizChamberTopicTagProjectMapper;
    @Autowired
    private CodeFeign codeFeign;

    /**
     * 查询议事厅话题标签列表
     * @param projectId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ChamberTopicTagVo> getTopicTagList(String projectId, String searchVal, Integer page, Integer limit){
        if (page == null || "".equals(page)) {
            page = 1;
        }
        if (limit == null || "".equals(limit)) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        //分页
        Integer startIndex = (page - 1) * limit;
        List<ChamberTopicTagVo> topicTagVoList = bizChamberTopicTagMapper.selectChamberTopicTagList(projectId,searchVal,startIndex,limit);
        if(topicTagVoList != null && topicTagVoList.size() > 0){
           for (ChamberTopicTagVo topicTagVo: topicTagVoList){
              List<String> projectNameList =  bizChamberTopicTagProjectMapper.selectProjectNameById(topicTagVo.getId());
              String projectNames = "";
              StringBuilder sb = new StringBuilder();
              if(projectNameList != null && projectNameList.size() >0){
                  for (String projectName : projectNameList){
                      sb.append(projectName+",");
                  }
              }
              if(sb.toString() != null && sb.length() > 0){
                  projectNames = sb.substring(0,sb.length()-1);
              }
               topicTagVo.setProjectNames(projectNames);
           }
        }
        return topicTagVoList;
    }

    /**
     * 查询议事厅话题标签列表数量
     * @param projectId
     * @param searchVal
     * @return
     */
    public int selectTopicTagCount(String projectId, String searchVal){
        return bizChamberTopicTagMapper.selectChamberTopicTagCount(projectId, searchVal);
    }


    /**
     * 保存话题标签
     * @param param
     * @return
     */
    public ObjectRestResponse saveTopicTag(ChanberTopicTagParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(param.getTopicTagName())){
            msg.setStatus(1002);
            msg.setMessage("话题标签不能为空");
            return msg;
        }
        if(param.getProjectVo() == null || param.getProjectVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("项目不能为空!");
            return msg;
        }
        ObjectRestResponse objectRestResponse = codeFeign.getCode("Tag","TP","6","0");
        logger.info("生成话题标签编码处理结果："+objectRestResponse.getData());
        BizChamberTopicTag topicTag = new BizChamberTopicTag();
        String id = UUIDUtils.generateUuid();
        topicTag.setId(id);
        if(objectRestResponse.getStatus() == 200){
            topicTag.setTopicTagCode(objectRestResponse.getData()==null?"":(String)objectRestResponse.getData());
        }
        topicTag.setTopicTagName(param.getTopicTagName());
        topicTag.setViewSort(Integer.parseInt(param.getViewSort()));
        topicTag.setCreateBy(BaseContextHandler.getUserID());
        topicTag.setCreateDate(new Date());
        if(bizChamberTopicTagMapper.insertSelective(topicTag) > 0){
            //保存项目
            for (ResultProjectVo temp : param.getProjectVo()){
                BizChamberTopicTagProject project = new BizChamberTopicTagProject();
                project.setId(UUIDUtils.generateUuid());
                project.setTopicTagId(id);
                project.setProjectId(temp.getId());
                project.setCreateBy(BaseContextHandler.getUserID());
                project.setCreateDate(new Date());
                bizChamberTopicTagProjectMapper.insertSelective(project);
            }
        }else{
            msg.setStatus(105);
            msg.setMessage("保存话题标签失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 查询话题标签详情
     * @param id
     * @return
     */
    public ObjectRestResponse<ChamberTopicTagInfo> getTopicTagInfo(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(102);
            msg.setMessage("id不能为空!");
            return msg;
        }
        ChamberTopicTagInfo topicTagInfo =  bizChamberTopicTagMapper.selectChamberTopicTagInfo(id);
        if(topicTagInfo != null){
            List<ResultProjectVo> projectVoList =  bizChamberTopicTagProjectMapper.selectProjectInfoById(id);
            topicTagInfo.setProjectVo(projectVoList);
        }
        return ObjectRestResponse.ok(topicTagInfo);
    }

    /**
     * 编辑话题标签
     * @param param
     * @return
     */
    public ObjectRestResponse updateTopicTag(ChanberTopicTagParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(param == null){
            msg.setStatus(1001);
            msg.setMessage("参数不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(param.getTopicTagName())){
            msg.setStatus(1002);
            msg.setMessage("话题标签不能为空");
            return msg;
        }
        if(param.getProjectVo() == null || param.getProjectVo().size() == 0){
            msg.setStatus(201);
            msg.setMessage("项目不能为空!");
            return msg;
        }
        ChamberTopicTagInfo topicTagInfo =  bizChamberTopicTagMapper.selectChamberTopicTagInfo(param.getId());
        if(topicTagInfo != null){
            BizChamberTopicTag topicTag = new BizChamberTopicTag();
            BeanUtils.copyProperties(topicTagInfo,topicTag);
            topicTag.setTopicTagName(param.getTopicTagName());
            topicTag.setViewSort(Integer.parseInt(param.getViewSort()));
            topicTag.setUpdateBy(BaseContextHandler.getUserID());
            topicTag.setUpdateDate(new Date());
            if(bizChamberTopicTagMapper.updateByPrimaryKeySelective(topicTag) > 0){
                  if(bizChamberTopicTagProjectMapper.deleteTopicTagById(param.getId()) > 0){
                      //保存项目
                      for (ResultProjectVo temp : param.getProjectVo()){
                          BizChamberTopicTagProject project = new BizChamberTopicTagProject();
                          project.setId(UUIDUtils.generateUuid());
                          project.setTopicTagId(param.getId());
                          project.setProjectId(temp.getId());
                          project.setCreateBy(BaseContextHandler.getUserID());
                          project.setCreateDate(new Date());
                          bizChamberTopicTagProjectMapper.insertSelective(project);
                      }
                  }
            }else{
                msg.setStatus(1101);
                msg.setMessage("编辑话题标签失败!");
                return msg;
            }
        }else{
            msg.setStatus(1101);
            msg.setMessage("查无此详情!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }

    /**
     * 删除话题标签
     * @param id
     * @return
     */
    public ObjectRestResponse delTopicTag(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            msg.setStatus(101);
            msg.setMessage("id不能为空!");
            return msg;
        }
        if(bizChamberTopicTagMapper.selectIsRelateByTagId(id) > 0){
            msg.setStatus(101);
            msg.setMessage("该标签已有话题数据，无法删除!");
            return msg;
        }
        if (bizChamberTopicTagMapper.delTopicTagById(id, BaseContextHandler.getUserID()) < 0) {
            msg.setStatus(102);
            msg.setMessage("删除话题标签失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;

    }





}