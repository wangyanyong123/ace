package com.github.wxiaoqi.security.jinmao.biz;

import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.jinmao.entity.BizVisitorSignlogs;
import com.github.wxiaoqi.security.jinmao.mapper.BizVisitorSignlogsMapper;
import com.github.wxiaoqi.security.jinmao.vo.Product.OutParam.ImgInfo;
import com.github.wxiaoqi.security.jinmao.vo.visitsign.ResultVisitInfoVo;
import com.github.wxiaoqi.security.jinmao.vo.visitsign.ResultVisitListVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 访客登记表
 *
 * @author zxl
 * @Date 2019-01-08 17:51:58
 */
@Service
public class BizVisitorSignlogsBiz extends BusinessBiz<BizVisitorSignlogsMapper,BizVisitorSignlogs> {

    private Logger logger = LoggerFactory.getLogger(BizVisitorSignlogsBiz.class);
    @Autowired
    private BizVisitorSignlogsMapper bizVisitorSignlogsMapper;


    public List<ResultVisitListVo> getVisitSignList(String projectId, String searchVal,Integer page,Integer limit) {
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
        List<ResultVisitListVo> visitSignList = bizVisitorSignlogsMapper.getVisitSignList(projectId,searchVal,startIndex,limit);

        if (visitSignList == null && visitSignList.size() == 0) {
            visitSignList = new ArrayList<>();
        }
        return visitSignList;
    }

    public int getVisitSignCount(String projectId, String searchVal) {
        int total = bizVisitorSignlogsMapper.getVisitSignCount(projectId, searchVal);
        return total;
    }



    public ObjectRestResponse getVisitSignInfo(String id) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isEmpty(id)) {
            response.setMessage("id不能为空！");
            return response;
        }
        ResultVisitInfoVo visitSignInfo = bizVisitorSignlogsMapper.getVisitSignInfo(id);
        if (visitSignInfo == null ) {
            visitSignInfo = new ResultVisitInfoVo();
        }else {
            List<ImgInfo> visitPhoto = new ArrayList<>();
            ImgInfo imgInfo = new ImgInfo();
            if (visitSignInfo.getVisitPhotoStr() !=null) {
                imgInfo.setUrl(visitSignInfo.getVisitPhotoStr());
                visitPhoto.add(imgInfo);
            }
            visitSignInfo.setVisitPhoto(visitPhoto);
        }
        response.setData(visitSignInfo);
        return response;
    }
}