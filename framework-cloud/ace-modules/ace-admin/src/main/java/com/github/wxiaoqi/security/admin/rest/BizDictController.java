package com.github.wxiaoqi.security.admin.rest;

import com.github.wxiaoqi.security.admin.biz.BizDictBiz;
import com.github.wxiaoqi.security.admin.vo.dict.in.SaveDictParam;
import com.github.wxiaoqi.security.admin.vo.dict.in.UpdateDictParam;
import com.github.wxiaoqi.security.admin.vo.dict.out.DictTree;
import com.github.wxiaoqi.security.admin.vo.dict.out.DictValueVo;
import com.github.wxiaoqi.security.admin.vo.dict.out.DictVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.IgnoreUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 业务数据字典
 *
 * @author huangxl
 * @Date 2019-02-14 15:53:33
 */
@RestController
@RequestMapping("web/bizDict")
@CheckClientToken
@CheckUserToken
@Api(tags = "业务数据字典")
public class BizDictController {

    @Autowired
    private BizDictBiz bizDictBiz;

    /**
     * 创建数据字典
     * @return
     */
    @RequestMapping(value = "/saveDict", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "创建数据字典---PC端", notes = "创建数据字典---PC端",httpMethod = "POST")
    public ObjectRestResponse saveDict(@RequestBody @ApiParam SaveDictParam param){
        return bizDictBiz.saveDict(param);
    }

    /**
     * 删除字典
     * @param id
     * @return
     */
    @RequestMapping(value = "/delDict/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "删除字典(左边)---PC端", notes = "删除字典(左边)---PC端",httpMethod = "GET")
    public ObjectRestResponse delDict(@PathVariable String id){
        return bizDictBiz.delDict(id);
    }


    @RequestMapping(value = "/getDictTreeList" ,method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询字典树---PC端", notes = "查询字典树---PC端",httpMethod = "GET")
    @ApiImplicitParam(name="searchVal",value="根据编码,名称查询",dataType="String",paramType = "query",example="4")
    public ObjectRestResponse<List<DictTree>> getDictTreeList(String searchVal){
        return  bizDictBiz.getDictTree(searchVal);
    }

    /**
     * 查询字典值列表
     * @return
     */
    @RequestMapping(value = "/getDictValueList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询字典值列表---PC端", notes = "查询字典值列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value="编码id",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据规则编码,名称模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<DictVo> getDictValueList(String id, String searchVal,Integer page, Integer limit){
        List<DictVo> dictVoList = bizDictBiz.getDictValueList(id, searchVal,page,limit);
        int total = bizDictBiz.selectDictCount(id, searchVal);
        return new TableResultResponse<DictVo>(total, dictVoList);
    }


    /**
     * 根据id删除字典值
     * @param id
     * @return
     */
    @RequestMapping(value = "/delDictValueById/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据id删除字典值(右边)---PC端", notes = "根据id删除字典值(右边)---PC端",httpMethod = "GET")
    public ObjectRestResponse delDictValueById(@PathVariable String id){
        return bizDictBiz.delDictValueById(id);
    }

    /**
     * 根据id查询字典值信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/getDictValueInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据id查询字典值信息---PC端", notes = "根据id查询字典值信息---PC端",httpMethod = "GET")
    public TableResultResponse<DictVo> getDictValueInfo(@PathVariable String id){
        List<DictVo> vo =  bizDictBiz.getDictValueInfo(id);
        return new TableResultResponse<DictVo>(vo.size(),vo);
    }


    /**
     * 编辑字典值
     * @return
     */
    @RequestMapping(value = "/updateDictValue", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑字典值---PC端", notes = "编辑字典值---PC端",httpMethod = "POST")
    public ObjectRestResponse updateDictValue(@RequestBody @ApiParam UpdateDictParam param){
        return bizDictBiz.updateDictValue(param);
    }

    /**
     * 字典对外提供接口(根据编码id查询)
     * @param code
     * @return
     */
    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/feign/{code}", method = RequestMethod.GET)
    @ApiOperation(value = "字典对外提供接口(根据编码id查询)---PC端", notes = "字典对外提供接口(根据编码id查询)---PC端",httpMethod = "GET")
    public TableResultResponse<DictValueVo> getDictValueByCode(@PathVariable("code") String code) {
        List<DictValueVo> dictValues = bizDictBiz.getDictValue(code).stream().sorted(new Comparator<DictValueVo>() {
            @Override
            public int compare(DictValueVo o1, DictValueVo o2) {
                return o1.getViewSort() - o2.getViewSort();
            }
        }).collect(Collectors.toList());
        return new TableResultResponse<DictValueVo>(dictValues.size(), dictValues);
    }



    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/type/{code}", method = RequestMethod.GET)
    @ApiOperation(value = "字典对外提供接口(返回Map对象)---PC端", notes = "字典对外提供接口(返回Map对象)---PC端",httpMethod = "GET")
    public Map<String, String> getDictValue(@PathVariable("code") String code) {
        List<DictValueVo> dictValues = bizDictBiz.getDictValue(code);
        Map<String, String> result = dictValues.stream().collect(
                Collectors.toMap(DictValueVo::getVal, DictValueVo::getName));
        return result;
    }




    /**
     * 查询三级字典树
     * @param pid
     * @return
     */
    @IgnoreClientToken
    @IgnoreUserToken
    @RequestMapping(value = "/selectDictThreeList/{pid}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询三级字典树", notes = "字典对外提供接口(查询三级字典树)",httpMethod = "GET")
    public ObjectRestResponse<List<DictTree>> getDictValuess(@PathVariable String pid){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        List<DictTree> dictValueVoList = bizDictBiz.selectDictThreeList(pid);
        objectRestResponse.setData(dictValueVoList);
        return objectRestResponse;
    }


}