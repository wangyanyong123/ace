package com.github.wxiaoqi.security.jinmao.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import com.github.wxiaoqi.security.jinmao.entity.SysModuleProject;
import com.github.wxiaoqi.security.jinmao.mapper.SysModuleProjectMapper;
import com.github.wxiaoqi.security.jinmao.vo.AppResultModuleVo.AppModuleTree;
import com.github.wxiaoqi.security.jinmao.vo.AppResultModuleVo.ResultAppModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:13 2018/11/14
 * @Modified By:
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Service
public class SysModuleProjectBiz extends BusinessBiz<SysModuleProjectMapper,SysModuleProject> {

    @Autowired
    private SysModuleProjectMapper sysModuleProjectMapper;

    public ObjectRestResponse<List<AppModuleTree>> getModules(String projectId, String system) {
        String pid = sysModuleProjectMapper.selectMoudlePid();
        List<ResultAppModule> moduleList =  sysModuleProjectMapper.getModules(pid,projectId,system);
        List<AppModuleTree> trees = new ArrayList<>();
        moduleList.forEach(modules -> {
            trees.add(new AppModuleTree(modules.getId(), modules.getPid(), modules.getName(), modules.getCode(),modules.getLogo(),
                    modules.getSystem(),modules.getShowType(),modules.getIosVersion(),modules.getAndroidVersion(),modules.getBusId(),modules.getPageShowType()));
        });
        List<AppModuleTree> treesTemp = TreeUtil.bulid(trees, "-1", null);
        if(treesTemp == null || treesTemp.size() == 0){
            treesTemp = new ArrayList<>();
        }
        return ObjectRestResponse.ok(treesTemp);
    }


}
