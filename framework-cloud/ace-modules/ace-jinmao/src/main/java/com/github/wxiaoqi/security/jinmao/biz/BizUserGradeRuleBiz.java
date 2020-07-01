package com.github.wxiaoqi.security.jinmao.biz;

import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.api.vo.img.ImgInfo;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizUserGradeRule;
import com.github.wxiaoqi.security.jinmao.feign.CodeFeign;
import com.github.wxiaoqi.security.jinmao.mapper.BizUserGradeRuleMapper;
import com.github.wxiaoqi.security.jinmao.vo.grade.in.GradeParams;
import com.github.wxiaoqi.security.jinmao.vo.grade.out.GradeList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxiaoqi.security.jinmao.entity.BizUserGradeRule;
import com.github.wxiaoqi.security.jinmao.mapper.BizUserGradeRuleMapper;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 运营服务-用户等级规则表
 *
 * @author huangxl
 * @Date 2019-08-05 16:26:47
 */
@Service
public class BizUserGradeRuleBiz extends BusinessBiz<BizUserGradeRuleMapper,BizUserGradeRule> {

    @Autowired
    private BizUserGradeRuleMapper gradeRuleMapper;
    @Autowired
    private CodeFeign codeFeign;

    public List<GradeList> getGradeList(String searchVal, Integer page, Integer limit) {

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
        int startIndex = (page - 1) * limit;
        List<GradeList> signLists =  gradeRuleMapper.getGradeList(searchVal, startIndex, limit);
        if (signLists.size()==0) {
            signLists = new ArrayList<>();
        }
        return signLists;
    }


    public int getGradeListTotal(String searchVal) {
        return gradeRuleMapper.getGradeListTotal(searchVal);
    }

    public ObjectRestResponse createGrade(GradeParams params) {
        ObjectRestResponse response = new ObjectRestResponse();

        int result =  gradeRuleMapper.getGradeIsExist(params.getIntegral());
        if ( result > 0) {
            response.setStatus(101);
            response.setMessage("该等级积分已经存在");
            return response;
        }
        BizUserGradeRule gradeRule = new BizUserGradeRule();
        ObjectRestResponse<String> code = codeFeign.getCode("grade", "GR", "6", "0");
        if (code.getStatus() == 200) {
            gradeRule.setCode(code.getData() == null ? "" : code.getData());
        }
        gradeRule.setId(UUIDUtils.generateUuid());
        gradeRule.setGradeTitle(params.getGradeTitle());
        gradeRule.setGradeImg(params.getImgId());
        gradeRule.setIntegral(params.getIntegral());
        gradeRule.setCreateBy(BaseContextHandler.getUserID());
        gradeRule.setCreateTime(new Date());
        gradeRule.setTimeStamp(new Date());
        gradeRuleMapper.insertSelective(gradeRule);
        return response;
    }

    public ObjectRestResponse updateGrade(GradeParams params) {
        ObjectRestResponse response = new ObjectRestResponse();
        BizUserGradeRule gradeRule = gradeRuleMapper.selectByPrimaryKey(params.getId());
        if (!params.getIntegral().equals(gradeRule.getIntegral())) {
            int result =  gradeRuleMapper.getGradeIsExist(params.getIntegral());
            if (result > 0) {
                response.setStatus(101);
                response.setMessage("该等级积分已经存在");
                return response;
            }
        }

        BizUserGradeRule userGradeRule = new BizUserGradeRule();
        BeanUtils.copyProperties(gradeRule,userGradeRule);
        userGradeRule.setGradeTitle(params.getGradeTitle());
        userGradeRule.setGradeImg(params.getImgId());
        userGradeRule.setIntegral(params.getIntegral());
        userGradeRule.setModifyBy(BaseContextHandler.getUserID());
        userGradeRule.setModifyTime(new Date());
        gradeRuleMapper.updateByPrimaryKey(userGradeRule);
        return response;
    }

    public ObjectRestResponse getGradeDetail(String id) {
        ObjectRestResponse response = new ObjectRestResponse();
        BizUserGradeRule gradeRule = gradeRuleMapper.selectByPrimaryKey(id);
        GradeParams grade = new GradeParams();
        BeanUtils.copyProperties(gradeRule,grade);
        if (gradeRule == null) {
            response.setStatus(101);
            response.setMessage("ID出现错误");
            return response;
        }
        List<ImgInfo> img = new ArrayList<>();
        ImgInfo imgInfo = new ImgInfo();
        if (gradeRule.getGradeImg() != null) {
            imgInfo.setUrl(gradeRule.getGradeImg());
            img.add(imgInfo);
        }
        grade.setGradeImg(img);
        response.setData(grade);
        return response;
    }

    public ObjectRestResponse deleteGrade(String id) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (gradeRuleMapper.deleteById(id) == 0) {
            response.setStatus(201);
            response.setMessage("删除失败");
            return response;
        }
        return response;
    }
}