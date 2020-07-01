package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizSensitiveWordsBiz;
import com.github.wxiaoqi.security.jinmao.vo.sensitive.in.SaveSensitiveParam;
import com.github.wxiaoqi.security.jinmao.vo.sensitive.in.SensitiveStatusParam;
import com.github.wxiaoqi.security.jinmao.vo.sensitive.in.WordsExcel;
import com.github.wxiaoqi.security.jinmao.vo.sensitive.out.SensitiveVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 敏感词表
 *
 * @author huangxl
 * @Date 2019-01-25 14:01:58
 */
@RestController
@RequestMapping("web/bizSensitiveWords")
@CheckClientToken
@CheckUserToken
@Api(tags = "敏感词管理")
public class BizSensitiveWordsController {

    @Autowired
    private BizSensitiveWordsBiz bizSensitiveWordsBiz;


    /**
     * 查询敏感词列表
     * @return
     */
    @RequestMapping(value = "/getSensitiveListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询敏感词列表---PC端", notes = "查询敏感词列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="sensitiveStatus",value="状态查询(1-启用，2-禁用,0:全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据敏感词模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<SensitiveVo> getSensitiveListPc(String sensitiveStatus, String searchVal, Integer page, Integer limit){
        List<SensitiveVo> sensitiveList = bizSensitiveWordsBiz.getSensitiveList(sensitiveStatus, searchVal, page, limit);
        int total =bizSensitiveWordsBiz.selectSensitiveCount(sensitiveStatus, searchVal);
        return new TableResultResponse<SensitiveVo>(total, sensitiveList);
    }



    /**
     * 保存敏感词
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveSensitiveWordsPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存敏感词---PC端", notes = "保存敏感词---PC端",httpMethod = "POST")
    public ObjectRestResponse saveSensitiveWordsPc(@RequestBody @ApiParam SaveSensitiveParam params){
        return bizSensitiveWordsBiz.saveSensitiveWords(params);
    }


    /**
     * 编辑敏感词
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateSensitiveInfoPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑敏感词---PC端", notes = "编辑敏感词---PC端",httpMethod = "POST")
    public ObjectRestResponse updateSensitiveInfoPc(@RequestBody @ApiParam SaveSensitiveParam params){
        return bizSensitiveWordsBiz.updateSensitiveInfo(params);
    }


    /**
     * 查询敏感词详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getSensitiveInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询敏感词详情---PC端", notes = "查询敏感词详情---PC端",httpMethod = "GET")
    public TableResultResponse<SaveSensitiveParam> getSensitiveInfoPc(@PathVariable String id){
        List<SaveSensitiveParam> sensitiveInfo = bizSensitiveWordsBiz.getSensitiveInfo(id);
        return new TableResultResponse<SaveSensitiveParam>(sensitiveInfo.size(),sensitiveInfo);
    }


    /**
     * 敏感词操作
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateSensitiveStatusPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "敏感词操作---PC端", notes = "敏感词操作---PC端",httpMethod = "POST")
    public ObjectRestResponse updateSensitiveStatusPc(@RequestBody @ApiParam SensitiveStatusParam params){
        return bizSensitiveWordsBiz.updateSensitiveStatus(params);
    }


    /**
     * 导入excel模板
     * @param file
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导入excel模板---PC端", notes = "导入excel模板---PC端",httpMethod = "POST")
    public ObjectRestResponse importExcel(@ApiParam(value="上传的文件",required=true)@RequestParam("file") MultipartFile file){
        return bizSensitiveWordsBiz.importExcel(file);
    }


    /**
     * 导出excel
     * @return
     */
    @RequestMapping(value = "/exportExcel",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "导出excel---PC端", notes = "导出excel---PC端",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="sensitiveStatus",value="状态查询(1-启用，2-禁用,0:全部)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="searchVal",value="根据敏感词模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public ObjectRestResponse exportExcel(@RequestBody @ApiParam WordsExcel excel){
        return bizSensitiveWordsBiz.exportExcel(excel.getSensitiveStatus(), excel.getSearchVal(), null, null);
    }




}