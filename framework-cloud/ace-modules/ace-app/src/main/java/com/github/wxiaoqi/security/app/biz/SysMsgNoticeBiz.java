package com.github.wxiaoqi.security.app.biz;

import com.alibaba.fastjson.JSON;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.app.entity.SysMsgNotice;
import com.github.wxiaoqi.security.app.mapper.SysMsgNoticeMapper;
import com.github.wxiaoqi.security.app.vo.smsnotice.SmsNoticeList;
import com.github.wxiaoqi.security.app.vo.smsnotice.SmsNoticeVo;
import com.github.wxiaoqi.security.common.biz.BusinessBiz;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 消息通知
 *
 * @Date 2019-02-27 11:58:04
 */
@Service
@Slf4j
public class SysMsgNoticeBiz extends BusinessBiz<SysMsgNoticeMapper,SysMsgNotice> {

    @Autowired
    private SysMsgNoticeMapper sysMsgNoticeMapper;

    /**
     * 获取消息列表
     * @return
     */
    public ObjectRestResponse getSmsNoticeList(Integer page,Integer limit) {
        ObjectRestResponse response = new ObjectRestResponse();
        if (page<1) {
            page = 1;
        }
        if (limit<1) {
            limit = 10;
        }
        //分页
        int startIndex = (page - 1) * limit;
        List<SmsNoticeList> smsNoticeList = sysMsgNoticeMapper.getSmsNoticeList(BaseContextHandler.getUserID(), startIndex, limit);
        if (smsNoticeList == null || smsNoticeList.size() == 0) {
            smsNoticeList = new ArrayList<>();
        }

        response.setData(smsNoticeList);
        return response;
    }

    /**
     * 添加消息
     */
    public ObjectRestResponse saveSmsNotice(SmsNoticeVo smsNoticeVo) {
        log.info(">>>>>>>>>>>>>>>>>>>>接收手机消息通知参数:" + JSON.toJSONString(smsNoticeVo));
        ObjectRestResponse response = new ObjectRestResponse();
        if (StringUtils.isAnyoneEmpty( smsNoticeVo.getReceiverId())) {
            response.setStatus(101);
            response.setMessage("参数不能为空");
            return response;
        }

        SysMsgNotice sysMsgNotice = new SysMsgNotice();
        String smsId = UUIDUtils.generateUuid();
        sysMsgNotice.setId(smsId);
        if (smsNoticeVo.getReceiverId() != null ) {
            sysMsgNotice.setReceiverId(smsNoticeVo.getReceiverId());
        }else {
            sysMsgNotice.setReceiverId(BaseContextHandler.getUserID() == null ? "admin":BaseContextHandler.getUserID());
        }
        String[] msgNotice = smsNoticeVo.getSmsNotice();
        sysMsgNotice.setMsgType(msgNotice[0]);
        sysMsgNotice.setIsJump(msgNotice[1]);
        sysMsgNotice.setPage(msgNotice[2]);
        String title = "";
        String content = "";
        if (msgNotice[0].equals("1") || msgNotice[0].equals("4")){
            title = msgNotice[3].replace("#(status)",smsNoticeVo.getParamMap().get("status"));
            content = msgNotice[4];
        } else if (msgNotice[0].equals("2")) {
            title = msgNotice[3];
            String roomInfo =  msgNotice[4].replace("#(roominfo)",smsNoticeVo.getParamMap().get("roominfo"));
            content =  roomInfo.replace("#(identity)", smsNoticeVo.getParamMap().get("identity"));
        } else if (msgNotice[0].equals("5") || msgNotice[0].equals("8")) {
            title = msgNotice[3];
            content = msgNotice[4];
        } else if (msgNotice[0].equals("6") || msgNotice[0].equals("7")) {
            title = msgNotice[3];
            String titleInfo = msgNotice[4].replace("#(title)", smsNoticeVo.getParamMap().get("title"));
            content = titleInfo.replace("#(time)", smsNoticeVo.getParamMap().get("time"));
        } else if (msgNotice[0].equals("9")){
            title = msgNotice[3];
            String titleInfo = msgNotice[4].replace("#{friend}", smsNoticeVo.getParamMap().get("friend"));
            content = titleInfo;
        }else if (msgNotice[0].equals("10")){
            title = msgNotice[3];
            String titleInfo = msgNotice[4].replace("#{friend}", smsNoticeVo.getParamMap().get("friend"));
            if (smsNoticeVo.getParamMap().get("mine") != null) {
                 content = titleInfo.replace("#{mine}", smsNoticeVo.getParamMap().get("mine"));
            }else {
                content = titleInfo;
            }
        }
        sysMsgNotice.setObjectId(smsNoticeVo.getObjectId());
        sysMsgNotice.setMsgTitle(title);
        sysMsgNotice.setMsgContent(content);
        sysMsgNotice.setCreateBy(BaseContextHandler.getUserID() == null ? "admin":BaseContextHandler.getUserID());
        sysMsgNotice.setCreateTime(new Date());
        sysMsgNoticeMapper.insertSelective(sysMsgNotice);
        response.setData(smsId);
        return response;
    }

    /**
     * 查询用户下是否有未读消息
     * @return
     */
    public String getSmsNoticeSign() {
        ObjectRestResponse response = new ObjectRestResponse();
        List<String> smsNoticeSign = sysMsgNoticeMapper.getSmsNoticeSign(BaseContextHandler.getUserID());
        for (String sign : smsNoticeSign) {
            if (sign.equals("0")) {
                return "0";
            }
        }
        return "1";
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    public ObjectRestResponse getSmsNoticeDetail(String id) {
        ObjectRestResponse response = new ObjectRestResponse();
        sysMsgNoticeMapper.updateSmsNotice(id);
        return response;
    }

}