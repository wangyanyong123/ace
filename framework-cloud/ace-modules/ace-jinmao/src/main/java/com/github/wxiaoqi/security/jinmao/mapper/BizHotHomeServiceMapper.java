package com.github.wxiaoqi.security.jinmao.mapper;

import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import com.github.wxiaoqi.security.jinmao.entity.BizHotHomeService;
import com.github.wxiaoqi.security.jinmao.vo.common.ObjectIdAndName;
import com.github.wxiaoqi.security.jinmao.vo.hhser.in.SearchHotHomeServiceIn;
import com.github.wxiaoqi.security.jinmao.vo.hhser.out.HotHomeServiceInfoResult;
import com.github.wxiaoqi.security.jinmao.vo.hhser.out.SearchHotHomeServiceResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author huangxl
 * @Date 2020-04-14 19:34:50
 */
public interface BizHotHomeServiceMapper extends CommonMapper<BizHotHomeService> {

    int searchCount(SearchHotHomeServiceIn searchHotHomeServiceIn);

    List<SearchHotHomeServiceResult> searchResult(SearchHotHomeServiceIn searchHotHomeServiceIn);

    boolean existByBusId(@Param("busId") String busId);

    HotHomeServiceInfoResult selectInfoById(@Param("id") String id);

    List<ObjectIdAndName> selectHhsProjectList(@Param("busId") String budId);

    List<ObjectIdAndName> selectProjectList(@Param("busId") String busId);
}
