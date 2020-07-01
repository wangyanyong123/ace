package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizServiceHotline;
import com.github.wxiaoqi.security.jinmao.mapper.*;
import com.github.wxiaoqi.security.jinmao.vo.Hotline.InputParam.SaveHotlineParam;
import com.github.wxiaoqi.security.jinmao.vo.Hotline.OutParam.ResultAppHotlineVo;
import com.github.wxiaoqi.security.jinmao.vo.Hotline.OutParam.ResultHotlineInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.Hotline.OutParam.ResultHotlineVo;
import com.github.wxiaoqi.security.jinmao.vo.ResultProjectVo.ProjectListVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 服务热线
 *
 * @author Mr.AG
 * @email 463540703@qq.com
 * @version 2018-11-26 13:57:08
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class BizServiceHotlineBiz extends BusinessBiz<BizServiceHotlineMapper, BizServiceHotline> {

    private Logger logger = LoggerFactory.getLogger(BizServiceHotlineBiz.class);
    @Autowired
    private BizServiceHotlineMapper bizServiceHotlineMapper;
    @Autowired
    private BasePropertyServiceSkillsMapper basePropertyServiceSkillsMapper;
    @Autowired
    private BaseTenantProjectMapper baseTenantProjectMapper;
    @Autowired
    private BaseTenantMapper baseTenantMapper;
    @Autowired
    private BizCrmProjectMapper bizCrmProjectMapper;
    @Autowired
    private BizProductMapper bizProductMapper;

    /**
     * 查询服务热线列表
     * @param projectId
     * @param searchVal
     * @param page
     * @param limit
     * @return
     */
    public List<ResultHotlineVo> getHotlineList(String projectId, String searchVal, Integer page,Integer limit){
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
        List<ResultHotlineVo> hotlineVoList = bizServiceHotlineMapper.selectHotlineList(type,BaseContextHandler.getTenantID(),
                projectId, searchVal, startIndex, limit);
        for(ResultHotlineVo vo : hotlineVoList){
            String holineId = bizServiceHotlineMapper.selectIdByTitle(vo.getProjectId());
            vo.setId(holineId);
        }
        return hotlineVoList;
    }


    /**
     * 删除服务热线
     * @param id
     * @return
     */
    public ObjectRestResponse delHotlineInfo(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(101);
            msg.setMessage("id不能为空!");
            return msg;
        }
        if(bizServiceHotlineMapper.delHotlineInfo(id, BaseContextHandler.getUserID()) < 0){
            msg.setStatus(102);
            msg.setMessage("删除服务热线失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     *保存服务热线
     * @param param
     * @return
     */
    public ObjectRestResponse saveHotline(SaveHotlineParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        String data = bizServiceHotlineMapper.selectDataByProjectId(BaseContextHandler.getTenantID(),param.getProjectId());
        if(data != null){
            msg.setStatus(102);
            msg.setMessage("该项目已存在数据!");
            return msg;
        }
        //查询最后一条记录的排序值
        String sort = bizServiceHotlineMapper.selectLastSortByTime();
        //String projectId = baseTenantProjectMapper.selectProjectByTenantId(BaseContextHandler.getTenantID());
        BizServiceHotline line = new BizServiceHotline();
        for (Map<String,String> temp : param.getContent()){
            line.setId(UUIDUtils.generateUuid());
            line.setProjectId(param.getProjectId());
            line.setTitle(param.getTitle());
            line.setName(temp.get("name"));
            if(sort != null){
                line.setSort(Integer.parseInt(sort)+1);
            }else{
                line.setSort(1);
            }
            line.setHotline(temp.get("phone"));
            line.setTenantId(BaseContextHandler.getTenantID());
            line.setTimeStamp(new Date());
            line.setCreateBy(BaseContextHandler.getUserID());
            line.setCreateTime(new Date());
            if(bizServiceHotlineMapper.insertSelective(line) < 0){
                msg.setStatus(101);
                msg.setMessage("保存服务热线失败!");
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 根据热线id查询服务热线详情
     * @param id
     * @return
     */
    public List<ResultHotlineInfoVo> getHotlineInfo(String id){
        List<ResultHotlineInfoVo> resultVo = new ArrayList<>();
        ResultHotlineInfoVo hotlineInfo = bizServiceHotlineMapper.selectHotlineInfo(id);
       // String projectId = basePropertyServiceSkillsMapper.selectProjectIdByUserId(BaseContextHandler.getUserID());
        List<ResultHotlineInfoVo> list = bizServiceHotlineMapper.selectNameByProjectId(hotlineInfo.getProjectId());
        hotlineInfo.setContent(list);
        resultVo.add(hotlineInfo);
        return resultVo;
    }

    /**
     * 编辑服务热线
     * @param param
     * @return
     */
    public ObjectRestResponse updateHotline(SaveHotlineParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        String sort = bizServiceHotlineMapper.selectLastSortByTime();
        ResultHotlineInfoVo hotlineInfo = bizServiceHotlineMapper.selectHotlineInfo(param.getId());
        //String projectId = baseTenantProjectMapper.selectProjectByTenantId(BaseContextHandler.getTenantID());
        bizServiceHotlineMapper.delHotline(param.getProjectId());
        BizServiceHotline line = new BizServiceHotline();
        for (Map<String,String> temp : param.getContent()){
            //插入
            line.setId(UUIDUtils.generateUuid());
            line.setProjectId(param.getProjectId());
            line.setTitle(param.getTitle());
            line.setName(temp.get("name"));
            if(sort != null){
                line.setSort(Integer.parseInt(sort)+1);
            }else{
                line.setSort(1);
            }
            line.setHotline(temp.get("phone"));
            line.setTenantId(BaseContextHandler.getTenantID());
            line.setTimeStamp(new Date());
            line.setCreateBy(BaseContextHandler.getUserID());
            line.setCreateTime(new Date());
            if(bizServiceHotlineMapper.insertSelective(line) < 0){
                msg.setStatus(101);
                msg.setMessage("编辑服务热线 失败!");
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询App热线服务列表
     * @param projectId
     * @return
     */
    public ObjectRestResponse getAppHotlineList(String projectId){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(projectId)){
            msg.setStatus(101);
            msg.setMessage("项目id不能为空!");
            return msg;
        }
       List<ResultAppHotlineVo> hotlineAppVoList = bizServiceHotlineMapper.selectAppHotlineList(projectId);
        if(hotlineAppVoList == null && hotlineAppVoList.size()==0 ){
            hotlineAppVoList = new ArrayList<>();
        }
        msg.setData(hotlineAppVoList);
       return msg;
    }

    /**
     * 根据租户id查询项目
     * @return
     */
    public List<ProjectListVo> getProjectList(){
        List<ProjectListVo> projectListVoList = new ArrayList<>();
        String type = baseTenantMapper.selectTenantTypeById(BaseContextHandler.getTenantID());
        if("1".equals(type) || "2".equals(type) || "4".equals(type) ){
            projectListVoList = bizServiceHotlineMapper.selectProjectById(BaseContextHandler.getTenantID());
        }else if("3".equals(type)){
            projectListVoList =  bizCrmProjectMapper.selectProjectName();
        }
        return projectListVoList;
    }

}
