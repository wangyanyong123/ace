package com.github.wxiaoqi.security.app.biz;

import com.github.wxiaoqi.security.app.mapper.BizBusinessMapper;
import com.github.wxiaoqi.security.app.mapper.BizProductClassifyMapper;
import com.github.wxiaoqi.security.app.vo.classify.out.ClassifyVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 分类相关业务
 *
 * @author: guohao
 * @create: 2020-04-16 19:03
 **/
@Service
public class BizClassifyBiz {

    @Resource
    private BizBusinessMapper bizBusinessMapper;
    @Resource
    private BizProductClassifyMapper bizProductClassifyMapper;

    public List<ClassifyVo> findBusinessClassifyList() {
        return bizBusinessMapper.selectBusinessClassifyList();
    }

    public List<ClassifyVo> findSecondClassifyList(String busId) {
        return bizProductClassifyMapper.selectSecondClassifyList(busId);
    }
}
