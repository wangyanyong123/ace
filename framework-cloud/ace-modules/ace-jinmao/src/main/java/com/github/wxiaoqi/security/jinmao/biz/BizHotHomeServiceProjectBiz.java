package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizHotHomeServiceProject;
import com.github.wxiaoqi.security.jinmao.mapper.BizHotHomeServiceProjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huangxl
 * @Date 2020-04-14 19:34:50
 */
@Service
public class BizHotHomeServiceProjectBiz extends BusinessBiz<BizHotHomeServiceProjectMapper, BizHotHomeServiceProject> {

    public int edit(String hotHomeServiceId, List<String> projectIdList, String modifyBy) {
        List<String> oldProjectIdList = this.mapper.selectProjectIdListByHhsId(hotHomeServiceId);
        if (CollectionUtils.isEmpty(oldProjectIdList)) {
            return save(hotHomeServiceId, projectIdList, modifyBy);
        } else {
            update(hotHomeServiceId, projectIdList, oldProjectIdList, modifyBy);
        }
        return 1;
    }

    private int save(String hotHomeServiceId, List<String> projectIdList, String modifyBy) {
        int i = 0;
        for (String projectId : projectIdList) {
            BizHotHomeServiceProject bizHotHomeServiceProject = new BizHotHomeServiceProject();
            bizHotHomeServiceProject.setId(UUIDUtils.generateUuid());
            bizHotHomeServiceProject.setHotHomeServiceId(hotHomeServiceId);
            bizHotHomeServiceProject.setProjectId(projectId);
            bizHotHomeServiceProject.setCreateBy(modifyBy);
            bizHotHomeServiceProject.setCreateTime(new Date());
            bizHotHomeServiceProject.setStatus("1");
            bizHotHomeServiceProject.setModifyBy(modifyBy);
            i += this.mapper.insertSelective(bizHotHomeServiceProject);
        }
        return i;
    }

    private void update(String hotHomeServiceId, List<String> projectIdList, List<String> oldProjectIdList, String modifyBy) {

        List<String> deleteList = new ArrayList<>();
        for (String projectId : oldProjectIdList) {
            if (!projectIdList.contains(projectId)) {
                deleteList.add(projectId);
            } else {
                projectIdList.remove(projectId);
            }
        }
        if (!CollectionUtils.isEmpty(deleteList)) {
            this.mapper.deleteByHhsIdAndPids(hotHomeServiceId, deleteList, modifyBy);
        }
        if (!CollectionUtils.isEmpty(projectIdList)) {
            this.save(hotHomeServiceId, projectIdList, modifyBy);
        }
    }

    public int updateStatusInvalidByHhsId(String id) {
        return this.mapper.deleteByHhsIdAndPids(id, null, BaseContextHandler.getUserID());
    }
}