package com.github.wxiaoqi.security.admin.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.admin.entity.BizDict;
import com.github.wxiaoqi.security.admin.mapper.BizDictMapper;
import com.github.wxiaoqi.security.admin.vo.dict.in.SaveDictParam;
import com.github.wxiaoqi.security.admin.vo.dict.in.UpdateDictParam;
import com.github.wxiaoqi.security.admin.vo.dict.out.DictTree;
import com.github.wxiaoqi.security.admin.vo.dict.out.DictValueVo;
import com.github.wxiaoqi.security.admin.vo.dict.out.DictVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 业务数据字典
 *
 * @author huangxl
 * @Date 2019-02-14 15:53:33
 */
@Service
public class BizDictBiz extends BusinessBiz<BizDictMapper,BizDict> {

    @Autowired
    private BizDictMapper bizDictMapper;


    //创建字典
    public ObjectRestResponse saveDict(SaveDictParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(param.getName())){
            msg.setStatus(202);
            msg.setMessage("名称不能为空");
            return msg;
        }
        BizDict dict = new BizDict();
        if("-1".equals(param.getPid())){//添加父节点
            dict.setId(param.getId());
            dict.setPid("-1");
            dict.setVal(param.getVal());
            dict.setName(param.getName());
            dict.setEnName(param.getEnName());
            dict.setViewSort(param.getViewSort());
            dict.setCreateBy(BaseContextHandler.getUserID());
            dict.setCreateTime(new Date());
            dict.setTimeStamp(new Date());
            if(bizDictMapper.insertSelective(dict) < 0){
                msg.setStatus(201);
                msg.setMessage("保存字典失败!");
                return msg;
            }
        }else{
            //添加子节点
            if(StringUtils.isEmpty(param.getPid())){
                msg.setStatus(202);
                msg.setMessage("添加子节点,父id不能为空!");
                return msg;
            }else{
                List<DictValueVo> dictValueVos =  bizDictMapper.selectDictValueList(param.getPid());
                if(dictValueVos != null && dictValueVos.size() > 0){
                    for (DictValueVo vo : dictValueVos){
                        if(param.getVal().equals(vo.getVal())){
                            msg.setStatus(205);
                            msg.setMessage("同级中的编码值不能重复!");
                            return msg;
                        }
                    }
                }
                String temp = bizDictMapper.selectIsCodeById(param.getId());
                if(!StringUtils.isEmpty(temp) && temp != null){
                    msg.setStatus(205);
                    msg.setMessage("该编码已存在,请重新输入!");
                    return msg;
                }else{
                    if(!StringUtils.isEmpty(param.getId())){
                        dict.setId(param.getId());
                    }else{
                        String code = bizDictMapper.selectCodeByPid(param.getPid());
                        String id = null;
                        if(code != null && !"".equals(code)){
                            id = code;
                        }else{
                            if(param.getPid().indexOf("_")!=-1){
                                id = param.getPid()+"100";
                            }else{
                                id = param.getPid()+"_"+"100";
                            }
                        }
                        dict.setId(id);
                    }
                    dict.setPid(param.getPid());
                    dict.setVal(param.getVal());
                    dict.setName(param.getName());
                    dict.setEnName(param.getEnName());
                    dict.setViewSort(param.getViewSort());
                    dict.setCreateBy(BaseContextHandler.getUserID());
                    dict.setCreateTime(new Date());
                    dict.setTimeStamp(new Date());
                    if(bizDictMapper.insertSelective(dict) < 0){
                        msg.setStatus(201);
                        msg.setMessage("保存字典失败!");
                        return msg;
                    }
                }
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    //查询字典树
    public ObjectRestResponse<List<DictTree>> getDictTree(String searchVal) {
        List<DictVo> dictList =  bizDictMapper.selectDictTreeList(searchVal);
        List<DictTree> trees = new ArrayList<>();
        dictList.forEach(dict -> {
            trees.add(new DictTree(dict.getId(), dict.getPid(),dict.getVal(), dict.getName()));
        });
        List<DictTree> treesTemp = TreeUtil.bulid(trees, "-1", null);
        if(treesTemp == null || treesTemp.size() == 0){
            treesTemp = new ArrayList<>();
        }
        return ObjectRestResponse.ok(treesTemp);
    }


    /**
     * 删除字典
     * @param id
     * @return
     */
    public ObjectRestResponse delDict(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(201);
            msg.setMessage("id不能为空");
            return msg;
        }
        int isChildNode = bizDictMapper.selectIsChildNodeById(id);
        if(isChildNode > 1) {
            msg.setStatus(201);
            msg.setMessage("请先删除子节点!");
            return msg;
        }else{
            if(bizDictMapper.updateDictStatus(id, BaseContextHandler.getUserID()) < 0){
                msg.setStatus(201);
                msg.setMessage("删除节点失败!");
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 查询字典值列表
     * @param id
     * @param searchVal
     * @return
     */
    public List<DictVo> getDictValueList(String id, String searchVal, Integer page, Integer limit){
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
        List<DictVo> dictVoList = bizDictMapper.selectDictListById(id, searchVal,startIndex,limit);
        if(dictVoList == null || dictVoList.size() == 0){
            dictVoList = new ArrayList<>();
        }
        return dictVoList;
    }


    public int selectDictCount(String id, String searchVal){
          return bizDictMapper.selectDictListCount(id, searchVal);
    }


    /**
     * 根据id删除字典值
     * @param id
     * @return
     */
    public ObjectRestResponse delDictValueById(String id){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(id)){
            msg.setStatus(201);
            msg.setMessage("id不能为空");
            return msg;
        }
        if(bizDictMapper.updateDictStatus(id, BaseContextHandler.getUserID()) < 0) {
            msg.setStatus(201);
            msg.setMessage("删除字典值失败!");
            return msg;
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }


    /**
     * 根据id查询字典值信息
     * @param id
     * @return
     */
    public List<DictVo> getDictValueInfo(String id){
        List<DictVo> vo = new ArrayList<>();
        DictVo info = bizDictMapper.selectDictInfoById(id);
        if(info == null){
            info = new DictVo();
        }
        vo.add(info);
        return  vo;
    }


    /**
     * 编辑字典值
     * @param param
     * @return
     */
    public ObjectRestResponse updateDictValue(UpdateDictParam param){
        ObjectRestResponse msg = new ObjectRestResponse();
        if(StringUtils.isEmpty(param.getName())){
            msg.setStatus(202);
            msg.setMessage("名称不能为空");
            return msg;
        }
        if(StringUtils.isEmpty(param.getVal())){
            msg.setStatus(202);
            msg.setMessage("编码值不能为空");
            return msg;
        }
        DictVo info = bizDictMapper.selectDictInfoById(param.getId());
        if(info != null){
            List<DictValueVo> dictValueVos =  bizDictMapper.selectDictValueList(info.getPid());
            if(dictValueVos != null && dictValueVos.size() > 0){
                for (DictValueVo vo : dictValueVos){
                    if(!param.getId().equals(vo.getId())){
                        if(param.getVal().equals(vo.getVal())){
                            msg.setStatus(205);
                            msg.setMessage("同级中的编码值不能重复!");
                            return msg;
                        }
                    }
                }
            }
            BizDict dict = new BizDict();
            BeanUtils.copyProperties(info, dict);
            dict.setVal(param.getVal());
            dict.setName(param.getName());
            dict.setEnName(param.getEnName());
            dict.setViewSort(param.getViewSort());
            dict.setModifyBy(BaseContextHandler.getUserID());
            dict.setModifyTime(new Date());
            dict.setTimeStamp(new Date());
            if(bizDictMapper.updateByPrimaryKeySelective(dict) < 0){
                msg.setStatus(201);
                msg.setMessage("编辑字典值失败!");
                return msg;
            }
        }
        msg.setMessage("Operation succeed!");
        msg.setData("1");
        return msg;
    }

    /**
     * 字典对外提供接口
     * @param code
     * @return
     */
    public List<DictValueVo> getDictValue(String code){
        List<DictValueVo> dictValueVoList = bizDictMapper.selectDictValueList(code);
        if(dictValueVoList == null || dictValueVoList.size() == 0){
            dictValueVoList = new ArrayList<>();
        }
        return dictValueVoList;
    }

    /**
     * 查询三级字典树
     * @param pid
     * @return
     */
    public List<DictTree> selectDictThreeList(String pid){
        List<DictValueVo> dictList =  bizDictMapper.selectDictThreeList(pid);
        List<DictTree> trees = new ArrayList<>();
        dictList.forEach(dict -> {
            trees.add(new DictTree(dict.getId(), dict.getPid(),dict.getVal(), dict.getName()));
        });
        List<DictTree> treesTemp = TreeUtil.bulid(trees, pid, null);
        if(treesTemp == null || treesTemp.size() == 0){
            treesTemp = new ArrayList<>();
        }
        return treesTemp;
    }





}