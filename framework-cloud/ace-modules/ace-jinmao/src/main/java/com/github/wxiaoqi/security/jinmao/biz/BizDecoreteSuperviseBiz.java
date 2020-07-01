package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.constant.DocPathConstant;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizDecoreteSupervise;
import com.github.wxiaoqi.security.jinmao.feign.ToolFegin;
import com.github.wxiaoqi.security.jinmao.mapper.BizDecoreteSuperviseMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizProductMapper;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.decoretesupervise.in.DecoreteParams;
import com.github.wxiaoqi.security.jinmao.vo.decoretesupervise.in.DecoreteStatus;
import com.github.wxiaoqi.security.jinmao.vo.decoretesupervise.out.DecoreteDetailVo;
import com.github.wxiaoqi.security.jinmao.vo.decoretesupervise.out.DecoreteListVo;
import com.github.wxiaoqi.security.jinmao.vo.group.out.ResultProject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 装修监理表
 *
 * @Date 2019-04-01 15:47:05
 */
@Service
public class BizDecoreteSuperviseBiz extends BusinessBiz<BizDecoreteSuperviseMapper, BizDecoreteSupervise> {

    private Logger logger = LoggerFactory.getLogger(BizDecoreteSuperviseBiz.class);
    @Autowired
    private BizDecoreteSuperviseMapper bizDecoreteSuperviseMapper;
    @Autowired
    private BizProductMapper bizProductMapper;
    @Autowired
    private ToolFegin toolFeign;

    public List<DecoreteListVo> getDecoreteSupervise(String projectId, String status, Integer page, Integer limit) {
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
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        List<DecoreteListVo> decoreteSuperviseList = bizDecoreteSuperviseMapper.getDecoreteSuperviseList(type,BaseContextHandler.getTenantID(),projectId, status, startIndex, limit);

        if (decoreteSuperviseList == null || decoreteSuperviseList.size() == 0) {
            decoreteSuperviseList = new ArrayList<>();
        }
        return decoreteSuperviseList;
    }

    public int getDecoreteSuperviseCount(String projectId,String status) {
        String type = bizProductMapper.selectSystemByTenantId(BaseContextHandler.getTenantID());
        return bizDecoreteSuperviseMapper.getDecoreteSuperviseCount(type,BaseContextHandler.getTenantID(),projectId,status);
    }

    public List<DecoreteDetailVo> getDecoreteDetail(String id) {
        List<DecoreteDetailVo> detailVos = new ArrayList<>();
        DecoreteDetailVo decoreteDetail = bizDecoreteSuperviseMapper.getDecoreteDetail(id);
        if (decoreteDetail != null) {
            if (decoreteDetail.getPromoImgeStr()!=null) {
                List<ImgInfo> image = new ArrayList<>();
                ImgInfo logoImage = new ImgInfo();
                logoImage.setUrl(decoreteDetail.getPromoImgeStr());
                image.add(logoImage);
                decoreteDetail.setPromoImge(image);
            }
            List<ResultProject> projectInfo = new ArrayList<>();
            ResultProject resultProject = new ResultProject();
            resultProject.setProjectId(decoreteDetail.getProjectId());
            resultProject.setProjectName(decoreteDetail.getProjectName());
            projectInfo.add(resultProject);
            decoreteDetail.setProjectInfo(projectInfo);
        }
        detailVos.add(decoreteDetail);
        return detailVos;
    }

    public ObjectRestResponse saveDecoreteSupervise(DecoreteParams params) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (bizDecoreteSuperviseMapper.getProjectByDecorete(params.getProjectId())!=null) {
            response.setStatus(101);
            response.setMessage("该项目已创建装修监理");
            return response;
        }
        BizDecoreteSupervise bizDecoreteSupervise = new BizDecoreteSupervise();
        String decoreteId = UUIDUtils.generateUuid();
        bizDecoreteSupervise.setId(decoreteId);
        bizDecoreteSupervise.setProjectId(params.getProjectId());
        bizDecoreteSupervise.setCostPrice(params.getCostPrice());
        bizDecoreteSupervise.setServicePrice(params.getServicePrice());
        if (params.getPromoImge() != null) {
            for (ImgInfo imgInfo : params.getPromoImge()) {
                if(StringUtils.isNotEmpty(imgInfo.getUrl())){
                    ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(imgInfo.getUrl(), DocPathConstant.PROPERTY);
                    if(objectRestResponse.getStatus()==200){
                        bizDecoreteSupervise.setPromoImge(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                    }
                }
                //bizDecoreteSupervise.setPromoImge(imgInfo.getUrl());
            }
        }else {
            bizDecoreteSupervise.setPromoImge("");
        }
        bizDecoreteSupervise.setServiceIntro(params.getServiceIntro());
        bizDecoreteSupervise.setCreateBy(BaseContextHandler.getUserID());
        bizDecoreteSupervise.setCreateTime(new Date());
        bizDecoreteSupervise.setTimeStamp(new Date());
        if (bizDecoreteSuperviseMapper.insertSelective(bizDecoreteSupervise) < 0) {
            response.setStatus(101);
            response.setMessage("保存失败!");
            return response;
        }
        response.setData(decoreteId);
        return response;
    }

    public ObjectRestResponse updateDecoreteSupervise(DecoreteParams params) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (!bizDecoreteSuperviseMapper.selectByPrimaryKey(params.getId()).getProjectId().equals(params.getProjectId())) {
            if (bizDecoreteSuperviseMapper.getProjectByDecorete(params.getProjectId())!=null) {
                response.setStatus(101);
                response.setMessage("该项目已创建装修监理");
                return response;
            }
        }
        BizDecoreteSupervise bizDecoreteSupervise = new BizDecoreteSupervise();
        bizDecoreteSupervise.setId(params.getId());
        bizDecoreteSupervise.setProjectId(params.getProjectId());
        bizDecoreteSupervise.setCostPrice(params.getCostPrice());
        bizDecoreteSupervise.setServicePrice(params.getServicePrice());
        if (params.getPromoImge() != null) {
            for (ImgInfo imgInfo : params.getPromoImge()) {
                if(StringUtils.isNotEmpty(imgInfo.getUrl())){
                    ObjectRestResponse objectRestResponse = toolFeign.moveAppUploadUrlPaths(imgInfo.getUrl(), DocPathConstant.PROPERTY);
                    if(objectRestResponse.getStatus()==200){
                        bizDecoreteSupervise.setPromoImge(objectRestResponse.getData()==null ? "" : (String)objectRestResponse.getData());
                    }
                }
                //bizDecoreteSupervise.setPromoImge(imgInfo.getUrl());
            }
        }else {
            bizDecoreteSupervise.setPromoImge("");
        }
        bizDecoreteSupervise.setServiceIntro(params.getServiceIntro());
        bizDecoreteSupervise.setModifyBy(BaseContextHandler.getUserID());
        bizDecoreteSupervise.setModifyTime(new Date());
        bizDecoreteSuperviseMapper.updateByPrimaryKeySelective(bizDecoreteSupervise);
        if (bizDecoreteSuperviseMapper.updateByPrimaryKeySelective(bizDecoreteSupervise) < 0) {
            response.setStatus(101);
            response.setMessage("编辑失败!");
            return response;
        }
        return response;
    }

    public ObjectRestResponse updateStatus(DecoreteStatus param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (bizDecoreteSuperviseMapper.updateStatus(param.getId(),param.getStatus()) < 0) {
            logger.error("修改状态失败，id为{}", param.getId());
            msg.setStatus(101);
            msg.setMessage("修改失败！");
            return msg;
        }
        msg.setData("1");
        return msg;
    }

    public ObjectRestResponse deleteDecoreteSupervise(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (bizDecoreteSuperviseMapper.deleteDecoreteSupervise(id)<0 || bizDecoreteSuperviseMapper.deleteDecoreteSupervise(id)==0) {
            logger.error("删除失败,id为{}",id);
            msg.setStatus(101);
            msg.setMessage("删除装修监理失败!");
            return msg;
        }
        return msg;
    }

}