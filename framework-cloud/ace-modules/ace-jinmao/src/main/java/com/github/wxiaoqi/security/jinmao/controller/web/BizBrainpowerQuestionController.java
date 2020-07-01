package com.github.wxiaoqi.security.jinmao.controller.web;

import com.github.wxiaoqi.security.api.vo.brainpower.out.QuestionVo;
import com.github.wxiaoqi.security.auth.client.annotation.CheckClientToken;
import com.github.wxiaoqi.security.auth.client.annotation.CheckUserToken;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.jinmao.biz.BizBrainpowerQuestionBiz;
import com.github.wxiaoqi.security.jinmao.feign.SearchFeign;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.in.SaveFunctionParam;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.in.SaveQuestionParam;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.in.UpdateIsShowParam;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.out.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 智能客服问答表
 *
 * @author huangxl
 * @Date 2019-04-10 18:24:34
 */
@RestController
@RequestMapping("web/bizBrainpowerQuestion")
@CheckClientToken
@CheckUserToken
@Api(tags = "智能客服管理")
public class BizBrainpowerQuestionController {

    @Autowired
    private BizBrainpowerQuestionBiz bizBrainpowerQuestionBiz;
    @Autowired
    private SearchFeign searchFeign;



    /**
     * 查询功能点列表
     * @return
     */
    @RequestMapping(value = "/getFunctionListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询功能点列表---PC端", notes = "查询功能点列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="enableStatus",value="状态查询(0:全部,1:待发布,2:已发布,3:已撤回)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="code",value="功能编码",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<FunctionVo> getFunctionListPc(String code, String enableStatus, Integer page, Integer limit){
        List<FunctionVo> functionList =  bizBrainpowerQuestionBiz.getFunctionList(code, enableStatus, page, limit);
        int total = bizBrainpowerQuestionBiz.selectFunctionCount(code, enableStatus);
        return new TableResultResponse<FunctionVo>(total, functionList);
    }



    /**
     * 查询功能点列表
     * @return
     */
    @RequestMapping(value = "/selectFunctionSearchVal",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "根据功能点搜索列表---PC端", notes = "根据功能点搜索列表---PC端",httpMethod = "GET")
    public TableResultResponse<FunctionVo> selectFunctionSearchVal(String code, String enableStatus, Integer page, Integer limit){
        List<FunctionVo> functionList =  bizBrainpowerQuestionBiz.selectFunctionSearchVal();
        return new TableResultResponse<FunctionVo>(functionList.size(), functionList);
    }


    /**
     * 查询置底功能点列表
     * @return
     */
    @RequestMapping(value = "/selectViewSortVo",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询置底功能点列表---PC端", notes = "查询置底功能点列表---PC端",httpMethod = "GET")
    public TableResultResponse<ViewSortVo> selectViewSortVo(String code, String enableStatus, Integer page, Integer limit){
        List<ViewSortVo> functionList =  bizBrainpowerQuestionBiz.selectViewSortVo();
        return new TableResultResponse<ViewSortVo>(functionList.size(), functionList);
    }


    /**
     * 修改排序
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateViewSortPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "修改排序---PC端", notes = "修改排序---PC端",httpMethod = "POST")
    public ObjectRestResponse updateViewSortPc(@RequestBody @ApiParam UpdateIsShowParam params){
        return bizBrainpowerQuestionBiz.updateViewSort(params);
    }



    /**
     * 保存功能点
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveFunctionPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存功能点---PC端", notes = "保存功能点---PC端",httpMethod = "POST")
    public ObjectRestResponse saveFunctionPc(@RequestBody @ApiParam SaveFunctionParam params){
        return bizBrainpowerQuestionBiz.saveFunction(params);
    }


    /**
     * 查询功能点详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getFunctionInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询功能点详情---PC端", notes = "查询功能点详情---PC端",httpMethod = "GET")
    public TableResultResponse<FunctionInfo> getFunctionInfoPc(@PathVariable String id){
        List<FunctionInfo> info = bizBrainpowerQuestionBiz.getFunctionInfo(id);
        return new TableResultResponse<FunctionInfo>(info.size(),info);
    }


    /**
     * 编辑功能点
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateFuctionPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑功能点---PC端", notes = "编辑功能点---PC端",httpMethod = "POST")
    public ObjectRestResponse updateFuctionPc(@RequestBody @ApiParam SaveFunctionParam params){
        return bizBrainpowerQuestionBiz.updateFuction(params);
    }


    /**
     * 置底，发布，撤回操作
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateIsShowOrFunctionStatusPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "置底，发布，撤回操作---PC端", notes = "置底，发布，撤回操作---PC端",httpMethod = "POST")
    public ObjectRestResponse updateIsShowOrFunctionStatusPc(@RequestBody @ApiParam UpdateIsShowParam params){
        return bizBrainpowerQuestionBiz.updateIsShowOrFunctionStatus(params);
    }

    /**
     * 置底，发布，撤回操作
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateQuestionStatusPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "发布，撤回操作---PC端", notes = "置底，发布，撤回操作---PC端",httpMethod = "POST")
    public ObjectRestResponse updateQuestionStatusPc(@RequestBody @ApiParam UpdateIsShowParam params){
        return bizBrainpowerQuestionBiz.updateQuestionStatus(params);
    }


    /**
     * 字典中功能点列表
     * @return
     */
    @RequestMapping(value = "/getDictValueList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "字典中功能点列表---PC端", notes = "字典中功能点列表---PC端",httpMethod = "GET")
    @ApiImplicitParam(name="code",value="编码id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    public TableResultResponse<ResultFunctionDictList> getDictValueList(String code){
        List<ResultFunctionDictList> functionDictList =  bizBrainpowerQuestionBiz.getDictValueList(code);
        return new TableResultResponse<ResultFunctionDictList>(functionDictList.size(), functionDictList);
    }

    /**
     * 字典中跳转链接列表
     * @return
     */
    @RequestMapping(value = "/getDictValueList2",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "字典中跳转链接列表---PC端", notes = "字典中跳转链接列表---PC端",httpMethod = "GET")
    @ApiImplicitParam(name="code",value="编码id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd")
    public TableResultResponse<ResultJumpLinkList> getDictValueList2(String code){
        List<ResultJumpLinkList> functionDictList =  bizBrainpowerQuestionBiz.getDictValueList2(code);
        return new TableResultResponse<ResultJumpLinkList>(functionDictList.size(), functionDictList);
    }





    /**
     * 查询问题库列表
     * @return
     */
    @RequestMapping(value = "/getQuestionListPc",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询问题库列表---PC端", notes = "查询问题库列表---PC端",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name="status",value="排序状态",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="enableStatus",value="状态查询(0:全部,1:待发布,2:已发布,3:已撤回)",dataType="String",paramType = "query",example="4"),
            @ApiImplicitParam(name="functionId",value="功能点id",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="searchVal",value="根据问题标题、所属分类模糊查询",dataType="String",paramType = "query",example="1sdsgsfdghsfdgsd"),
            @ApiImplicitParam(name="page",value="当前页码",dataType="Integer",paramType = "query",example="4"),
            @ApiImplicitParam(name="limit",value="每页条数",dataType="Integer",paramType = "query",example="1sdsgsfdghsfdgsd")
    })
    public TableResultResponse<QuestionVo> getQuestionListPc(String status, String functionId, String enableStatus, String searchVal, Integer page, Integer limit){
        List<QuestionVo> questionList =  bizBrainpowerQuestionBiz.getQuestionList(status,functionId, enableStatus, searchVal, page, limit);
        int total = bizBrainpowerQuestionBiz.selectQuestionCount(functionId, enableStatus, searchVal);
        return new TableResultResponse<QuestionVo>(total, questionList);
    }





    /**
     * 保存问题
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveQuestionPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "保存问题---PC端", notes = "保存问题---PC端",httpMethod = "POST")
    public ObjectRestResponse saveQuestionPc(@RequestBody @ApiParam SaveQuestionParam params){
        return bizBrainpowerQuestionBiz.saveQuestion(params);
    }


    /**
     * 查询问题详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/getQuestionInfoPc/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询问题详情---PC端", notes = "查询问题详情---PC端",httpMethod = "GET")
    public TableResultResponse<QuestionInfo> getQuestionInfoPc(@PathVariable String id){
        List<QuestionInfo> info = bizBrainpowerQuestionBiz.getQuestionInfo(id);
        return new TableResultResponse<QuestionInfo>(info.size(),info);
    }


    /**
     * 编辑问题
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateQuestionPc",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "编辑问题---PC端", notes = "编辑问题---PC端",httpMethod = "POST")
    public ObjectRestResponse updateQuestionPc(@RequestBody @ApiParam SaveQuestionParam params){
        return bizBrainpowerQuestionBiz.updateQuestion(params);
    }



    /**
     * 查询分类列表
     * @return
     */
    @RequestMapping(value = "/getClassifyList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询分类列表---PC端", notes = "查询分类列表---PC端",httpMethod = "GET")
    public TableResultResponse<ClassifyVo> getClassifyList(){
        List<ClassifyVo> functionList =  bizBrainpowerQuestionBiz.getClassifyList();
        return new TableResultResponse<ClassifyVo>(functionList.size(), functionList);
    }

    /**
     * 查询功能点列表
     * @return
     */
    @RequestMapping(value = "/getFunctionVo",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询发布功能点列表---PC端", notes = "查询发布功能点列表---PC端",httpMethod = "GET")
    public TableResultResponse<FunctionVo> getFunctionVo(){
        List<FunctionVo> functionList =  bizBrainpowerQuestionBiz.getFunctionVo();
        return new TableResultResponse<FunctionVo>(functionList.size(), functionList);
    }


    /**
     * 重建知识库搜索索引
     * @return
     */
    @RequestMapping(value = "/refreshBrainpowerQuestionIndex",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "重建知识库搜索索引---PC端", notes = "重建知识库搜索索引---PC端",httpMethod = "POST")
    public void refreshBrainpowerQuestionIndex(){
        searchFeign.refreshBrainpowerQuestionIndex();
    }



}