package com.github.wxiaoqi.security.admin.mapper;

import com.github.wxiaoqi.security.admin.entity.BizDict;
import com.github.wxiaoqi.security.admin.vo.dict.out.DictValueVo;
import com.github.wxiaoqi.security.admin.vo.dict.out.DictVo;
import com.github.wxiaoqi.security.common.mapper.CommonMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 业务数据字典
 * 
 * @author huangxl
 * @Date 2019-02-14 15:53:33
 */
public interface BizDictMapper extends CommonMapper<BizDict> {

    /**
     * 判断编码是否已存在
     * @param id
     * @return
     */
    String selectIsCodeById(String id);

    /**
     * 判断该节点是否有叶子节点
     * @param id
     * @return
     */
    int selectIsChildNodeById(@Param("id") String id);

    String selectCodeByPid(String pid);


    /**
     * 删除节点
     * @param id
     * @param userId
     * @return
     */
    int updateDictStatus(@Param("id") String id ,@Param("userId") String userId);

    //查询字典树
    List<DictVo> selectDictTreeList(@Param("searchVal") String searchVal);

    /**
     * 根据编码查询字典值列表
     * @param id
     * @param searchVal
     * @return
     */
    List<DictVo> selectDictListById(@Param("id") String id,@Param("searchVal") String searchVal,
                                    @Param("page") Integer page, @Param("limit") Integer limit);


    int selectDictListCount(@Param("id") String id,@Param("searchVal") String searchVal);

    /**
     * 根据id查询字典值信息
     * @param id
     * @return
     */
    DictVo selectDictInfoById(String id);

    /**
     * 根据code查询字典值
     * @param code
     * @return
     */
    List<DictValueVo> selectDictValueList(String code);

    /**
     * 查询三级字典树
     * @param pid
     * @return
     */
    List<DictValueVo> selectDictThreeList(String pid);
}
