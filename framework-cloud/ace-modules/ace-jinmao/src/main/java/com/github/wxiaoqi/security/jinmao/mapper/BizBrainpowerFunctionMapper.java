package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizBrainpowerFunction;
import com.github.wxiaoqi.security.jinmao.vo.brainpower.out.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 智能客服功能点表
 * 
 * @author huangxl
 * @Date 2019-04-10 18:24:34
 */
public interface BizBrainpowerFunctionMapper extends CommonMapper<BizBrainpowerFunction> {

    /**
     * 查询该功能点是否存在
     * @param code
     * @return
     */
    int selectIsFunctionByCode(String code);


    /**
     * 查询功能点列表
     * @param code
     * @param enableStatus
     * @param page
     * @param limit
     * @return
     */
    List<FunctionVo> selectFunctionList(@Param("code") String code,@Param("enableStatus") String enableStatus,
                                        @Param("page") Integer page, @Param("limit") Integer limit );


    int selectFunctionCount(@Param("code") String code,@Param("enableStatus") String enableStatus);

    List<FunctionVo> selectFunctionSearchVal();

    /**
     * 查询功能点详情
     * @param id
     * @return
     */
    FunctionInfo selectFunctionInfo(String id);

    List<String> selectFunctionByCode(String id);

    /**
     * 置底功能
     * @param status
     * @param userId
     * @param id
     * @return
     */
    int updatefunctionIsShow(@Param("status") String status, @Param("userId") String userId, @Param("id") String id);

    /**
     *
     * @param status
     * @param userId
     * @param id
     * @return
     */
    int updatefunctionStatus(@Param("status") String status, @Param("userId") String userId, @Param("id") String id);


    List<ResultFunctionDictList> seelctFunctionPointList();


    List<ResultFunctionDictList> selectDictValueList(String code);

    List<ResultJumpLinkList> selectDictValueList2(String code);

    /**
     * 查询置底功能点列表
     * @return
     */
    List<ViewSortVo> selectViewSortVo();

    /**
     * 修改排序
     * @param sort
     * @param userId
     * @param id
     * @return
     */
    int updateViewSort(@Param("sort") String sort, @Param("userId") String userId, @Param("id") String id);
}
