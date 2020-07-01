package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizCrmUnit;
import com.github.wxiaoqi.security.jinmao.entity.BizEnclosed;
import com.github.wxiaoqi.security.jinmao.entity.BizFacilities;
import com.github.wxiaoqi.security.jinmao.feign.CodeFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BizCrmProjectMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizCrmUnitMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizEnclosedMapper;
import com.github.wxiaoqi.security.jinmao.mapper.BizFacilitiesMapper;
import com.github.wxiaoqi.security.jinmao.vo.enclosed.in.SaveEnclosedParam;
import com.github.wxiaoqi.security.jinmao.vo.enclosed.in.SaveFacilitiesParam;
import com.github.wxiaoqi.security.jinmao.vo.enclosed.in.SetEnclosedParam;
import com.github.wxiaoqi.security.jinmao.vo.enclosed.out.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 围合表
 *
 * @author zxl
 * @Date 2018-12-24 11:45:40
 */
@Service
public class BizEnclosedBiz extends BusinessBiz<BizEnclosedMapper,BizEnclosed> {

    private Logger logger = LoggerFactory.getLogger(BizEnclosedBiz.class);
    @Autowired
    private BizEnclosedMapper bizEnclosedMapper;
    @Autowired
    private BizCrmProjectMapper bizCrmProjectMapper;
    @Autowired
    private BizFacilitiesMapper bizFacilitiesMapper;
    @Autowired
    private BizCrmUnitMapper bizCrmUnitMapper;
    @Autowired
    private CodeFeign codeFeign;
    /**
     * 查询围合树
     * @return
     */
    public List<EnclosedVo> selectEnclosedTreeList(String projectId) {
        List<EnclosedVo> enclosedVo = new ArrayList<>();
        EnclosedVo enclosed = new EnclosedVo();
        List<EnclosedTreeVo> enclosedTreeList = bizEnclosedMapper.selectEnclosedTreeList(projectId);
        if (enclosedTreeList!=null) {
            List<EnclosedTree> trees = new ArrayList<>();
            enclosedTreeList.forEach(enclosedInfo ->{
                trees.add(new EnclosedTree(enclosedInfo.getId(), enclosedInfo.getPid(), enclosedInfo.getName()));
            });
            List<EnclosedTree> treesTemp = TreeUtil.bulid(trees, "-1", null);
            enclosed.setChildren(treesTemp);
        }
        enclosedVo.add(enclosed);
        return enclosedVo;
    }

    /**
     * 查找项目树
     * @return
     */
    public List<ProjectVo> selectProjectTreeList(String projectId) {
        List<ProjectVo> projectVo = new ArrayList<>();
        ProjectVo project = new ProjectVo();
        List<ProjectTreeVo> projectTreeList = bizCrmProjectMapper.selectProjectTreeList(projectId);
        if (projectTreeList != null) {
            List<ProjectTree> tress = new ArrayList<>();
            projectTreeList.forEach(projectTreeVo -> {
                tress.add(new ProjectTree(projectTreeVo.getId(), projectTreeVo.getParentId(), projectTreeVo.getName(),projectTreeVo.getFlag()));
            });
            List<ProjectTree> treeTemp = TreeUtil.bulid(tress, "-1", null);
            project.setChildren(treeTemp);
        }
        projectVo.add(project);
        return projectVo;
    }

    /**
     * 获取选中单元ID
     * @param enclosedId
     * @return
     */
    public List<ChosenUnitVo> getChosenUnitId(String enclosedId) {
        List<ChosenUnitVo> list = new ArrayList<>();
        ChosenUnitVo chosenUnitVo = new ChosenUnitVo();
        List<UnitInfoVo> unitInfo = bizCrmProjectMapper.selectChosenUnitByEnclosedId(enclosedId);
        if (unitInfo != null && unitInfo.size() > 0) {
            String unitId = "";
            StringBuilder sb = new StringBuilder();
            for (UnitInfoVo infoVo : unitInfo) {
                sb.append(infoVo.getUnitId() + ",");
            }
            unitId = sb.substring(0, sb.length() - 1);
            chosenUnitVo.setUnitId(unitId.split(","));
        }
        list.add(chosenUnitVo);
        return list;
    }
    /**
     * 保存围合
     * @param param
     * @return
     */
    public ObjectRestResponse saveEnclosedInfo(SaveEnclosedParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        BizEnclosed bizEnclosed = new BizEnclosed();
        String enclosedId = UUIDUtils.generateUuid();
        bizEnclosed.setId(enclosedId);
        bizEnclosed.setProjectId(param.getProjectId());
        if (param.getEnclosedPid() != null) {
            bizEnclosed.setEnclosedPid(param.getEnclosedPid());
        }else {
            bizEnclosed.setEnclosedPid("");
        }
        bizEnclosed.setEnclosedName(param.getEnclosedName());
        bizEnclosed.setTenantId(BaseContextHandler.getTenantID());
        bizEnclosed.setCreateBy(BaseContextHandler.getUserID());
        bizEnclosed.setCreateTime(new Date());
        bizEnclosed.setTimeStamp(new Date());
        msg.setData(enclosedId);
        if (bizEnclosedMapper.insertSelective(bizEnclosed) < 0) {
            logger.error("保存数据失败,id为{}",param.getId());
            msg.setStatus(102);
            msg.setMessage("保存数据失败");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }

    /**
     * 根据ID查询围合详情
     * @param id
     * @return
     */
    public List<EnclosedInfoVo> getEnclosedInfoById(String id) {
        List<EnclosedInfoVo> enclosedInfo = new ArrayList<>();
        EnclosedInfoVo enclosedInfoVo = bizEnclosedMapper.selectEnclosedById(id);
        if (enclosedInfoVo != null) {
            ProjectInfoVo projectInfoVo = bizCrmProjectMapper.selectProjectById(enclosedInfoVo.getProjectId());
            if (projectInfoVo != null) {
                List<ProjectInfoVo> list = new ArrayList<>();
                list.add(projectInfoVo);
                enclosedInfoVo.setProjectInfo(list);
            }else {
                enclosedInfoVo.setProjectInfo(new ArrayList<>());
            }
            enclosedInfo.add(enclosedInfoVo);
        }else {
            EnclosedInfoVo infoVo = new EnclosedInfoVo();
            infoVo.setId("");
            infoVo.setProjectInfo(new ArrayList<>());
            infoVo.setEnclosedName("");
            infoVo.setProjectId("");
            enclosedInfo.add(infoVo);
        }
        return enclosedInfo;
    }

    /**
     * 编辑围合信息
     * @param param
     * @return
     */
    public ObjectRestResponse updateEnclosedInfo(SaveEnclosedParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        EnclosedInfoVo enclosedInfoVo = bizEnclosedMapper.selectEnclosedById(param.getId());
        BizEnclosed bizEnclosed = new BizEnclosed();
        if (enclosedInfoVo != null) {
            BeanUtils.copyProperties(enclosedInfoVo,bizEnclosed);
        }
        bizEnclosed.setProjectId(param.getProjectId());
        if (param.getEnclosedPid() != null) {
            bizEnclosed.setEnclosedPid(param.getEnclosedPid());
        }else {
            bizEnclosed.setEnclosedPid("");
        }
        bizEnclosed.setEnclosedName(param.getEnclosedName());
        bizEnclosed.setTenantId(BaseContextHandler.getTenantID());
        bizEnclosed.setModifyBy(BaseContextHandler.getUserID());
        bizEnclosed.setModifyTime(new Date());
        if (bizEnclosedMapper.updateByPrimaryKeySelective(bizEnclosed) < 0) {
            logger.error("修改数据失败,id为{}",param.getId());
            msg.setStatus(102);
            msg.setMessage("修改数据失败");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 保存道闸信息
     * @param param
     * @return
     */
    public ObjectRestResponse saveFacilitiesInfo(SaveFacilitiesParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        //编码
        String projectCode = bizFacilitiesMapper.selectProjectCodeById(param.getEnclosedId());
        String temp ="";
        if (param.getType().equals("1")) {
            temp = "+" + projectCode + "=" + "01060405"+"-";
        }else {
            temp = "+" + projectCode + "=" + "01060406"+"-";
        }
        ObjectRestResponse serialNumber = codeFeign.getCode("ProFacilities", temp, "9", "1");
        logger.info("生成编码处理结果：" + serialNumber.getData());
        BizFacilities bizFacilities = new BizFacilities();
        String faciId = UUIDUtils.generateUuid();
        bizFacilities.setId(faciId);
        if (serialNumber.getStatus() == 200) {
            bizFacilities.setFacilitiesCode(serialNumber.getData()==null?"":(String)serialNumber.getData());
        }
        bizFacilities.setEnclosedId(param.getEnclosedId());
        bizFacilities.setFacilitiesName(param.getName());
        bizFacilities.setType(param.getType());
        bizFacilities.setCreateBy(BaseContextHandler.getUserID());
        bizFacilities.setCreateTime(new Date());
        bizFacilities.setTimeStamp(new Date());
        msg.setData(faciId);
        if (bizFacilitiesMapper.insertSelective(bizFacilities) < 0) {
            logger.error("保存数据失败,id为{}",param.getId());
            msg.setStatus(102);
            msg.setMessage("保存数据失败");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }

    /**
     * 根据ID查询道闸详情
     * @param id
     * @return
     */
    public List<FacilitiesInfoVo> getFacilitiesInfoById(String id) {
        List<FacilitiesInfoVo> facilitiesInfo = new ArrayList<>();
        FacilitiesInfoVo facilitiesInfoVos = bizFacilitiesMapper.selectFacilitiesById(id);
        if (facilitiesInfo != null) {
            if (facilitiesInfoVos.getEnclosedId() == null) {
                facilitiesInfoVos.setEnclosedId("");
            }
        }else {
            FacilitiesInfoVo facilitiesInfoVo = new FacilitiesInfoVo();
            facilitiesInfoVo.setId("");
            facilitiesInfoVo.setEnclosedId("");
            facilitiesInfoVo.setName("");
            facilitiesInfoVo.setName("");
        }
        facilitiesInfo.add(facilitiesInfoVos);
        return facilitiesInfo;
    }

    /**
     * 编辑道闸
     * @param param
     * @return
     */
    public ObjectRestResponse updateFacilitiesInfo(SaveFacilitiesParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        FacilitiesInfoVo facilitiesInfoVos = bizFacilitiesMapper.selectFacilitiesById(param.getId());
        BizFacilities bizFacilities = new BizFacilities();
        BeanUtils.copyProperties(facilitiesInfoVos,bizFacilities);
        bizFacilities.setEnclosedId(param.getEnclosedId());
        bizFacilities.setFacilitiesName(param.getName());
        bizFacilities.setType(param.getType());
        bizFacilities.setModifyBy(BaseContextHandler.getUserID());
        bizFacilities.setModifyTime(new Date());
        if (bizFacilitiesMapper.updateByPrimaryKeySelective(bizFacilities) < 0) {
            logger.error("修改数据失败,id为{}",param.getId());
            msg.setStatus(102);
            msg.setMessage("修改数据失败");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        return msg;
    }

    /**
     * 单元设置围合
     * @param param
     * @return
     */
    public ObjectRestResponse setEnclosedOnUnit(SetEnclosedParam param) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (bizEnclosedMapper.selectUnitEnclosedIsDelete(param.getEnclosedId()) > 0) {
            //删除围合关联
            if (bizEnclosedMapper.deleteEnclosedOnUnit(param.getEnclosedId()) < 0) {
                logger.error("关联围合失败,围合id为{}",param.getEnclosedId());
                msg.setStatus(102);
                msg.setMessage("关联围合失败");
                return msg;
            }
        }
        //重新关联
        for (String unitId : param.getUnitId()) {
            BizCrmUnit bizCrmUnit = new BizCrmUnit();
            BizCrmUnit CrmUnit = bizCrmUnitMapper.selectByPrimaryKey(unitId);
            if (CrmUnit != null) {
                 BeanUtils.copyProperties(CrmUnit,bizCrmUnit);
            }
            if (param.getEnclosedId() != null) {
                bizCrmUnit.setEnclosedId(param.getEnclosedId());
                bizCrmUnit.setModifyTime(new Date());
                if (bizCrmUnitMapper.updateByPrimaryKeySelective(bizCrmUnit) < 0) {
                    logger.error("关联围合失败,单元id为{}",param.getUnitId());
                    msg.setStatus(102);
                    msg.setMessage("关联围合失败");
                    return msg;
                }

            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 获取顶级围合
     * @return
     */
    public List<EnclosedInfoVo> getTopEnclosedInfo() {
        List<EnclosedInfoVo> list = new ArrayList<>();
        List<EnclosedInfoVo> topEnclosedInfo = bizEnclosedMapper.selectTopEnclosedInfo();
        if (topEnclosedInfo.size() == 0 || topEnclosedInfo == null) {
            topEnclosedInfo = new ArrayList<>();
        }else {
            for (EnclosedInfoVo infoVo : topEnclosedInfo) {
                list.add(infoVo);
            }
        }
        return list;
    }

    /**
     * 删除围合
     * @param id
     * @return
     */
    public ObjectRestResponse deleteEnclosedById(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (bizEnclosedMapper.deleteEnclosedById(id) < 0) {
            logger.error("删除失败,id为{}",id);
            msg.setStatus(102);
            msg.setMessage("删除围合失败");
            return msg;
        }
        msg.setMessage("删除围合成功");
        return msg;
    }

    /**
     * 删除道闸
     * @param id
     * @return
     */
    public ObjectRestResponse deleteFacilitiesById(String id) {
        ObjectRestResponse msg = new ObjectRestResponse();
        if (bizEnclosedMapper.deleteFacilitiesById(id) < 0) {
            logger.error("删除失败,id为{}",id);
            msg.setStatus(102);
            msg.setMessage("删除道闸失败");
            return msg;
        }
        msg.setMessage("删除道闸成功");
        return msg;
    }

    /**
     * 查询道闸列表
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
      public List<FacilitiesInfoVo> getFacilitiesList(String enclosedId, String searchVal,Integer page,Integer limit) {
        if (page == null || page.equals("")) {
            page = 1;
        }
        if (limit == null || limit.equals("")) {
            limit = 10;
        }
        if(page == 0) {
            page = 1;
        }
        Integer startIndex = (page - 1) * limit;
        List<FacilitiesInfoVo> facilitiesInfoVos = bizEnclosedMapper.selectFacilitiesList(enclosedId,searchVal,startIndex,limit);
        if (facilitiesInfoVos == null ||facilitiesInfoVos.size()==0) {
            facilitiesInfoVos = new ArrayList<>();
        }
        return facilitiesInfoVos;
    }
}