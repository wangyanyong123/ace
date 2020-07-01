package com.github.wxiaoqi.security.jinmao.task;

import com.alibaba.fastjson.JSON;
import com.github.ag.core.exception.BaseException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.wxiaoqi.security.common.util.StringUtils;
import com.github.wxiaoqi.security.common.util.UUIDUtils;
import com.github.wxiaoqi.security.jinmao.entity.BaseAppServerUser;
import com.github.wxiaoqi.security.jinmao.entity.BaseUser;
import com.github.wxiaoqi.security.jinmao.entity.SyncTableUserInfo;
import com.github.wxiaoqi.security.jinmao.mapper.SyncTableUserInfoMapper;
import com.github.wxiaoqi.security.jinmao.task.util.HttpReqUtil;
import com.github.wxiaoqi.security.jinmao.task.util.SyncBusinessUserParams;
import com.github.wxiaoqi.security.jinmao.task.util.SyncHouseKeeperParams;
import io.undertow.util.StatusCodes;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 定时任务 - 同步用户信息
 * @author liuam
 * @date 2019-08-14 10:32
 */
@Component
@Slf4j
public class SyncUserInfoTask {

    @Autowired
    private SyncTableUserInfoMapper syncTableUserInfoMapper;

    @Value("${vrobot.sync.userInfo.url}")
    private String vrobotUrl;

    private static final int PAGE_SIZE = 1000;

    /**
     * 定时任务
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    public void syncUserInfo() {
        log.info("[定时任务 - 同步用户信息] 执行");
        // 查找第一页
        PageHelper.startPage(1, PAGE_SIZE);
        List<SyncTableUserInfo> firstPage = syncTableUserInfoMapper.selectByStatus("1");
        if (firstPage == null || firstPage.isEmpty()) {
            return;
        }
        PageInfo<SyncTableUserInfo> pageInfo = new PageInfo<>(firstPage);
        doSyncUserInfo(vrobotUrl, JSON.toJSONString(firstPage));

        // 遍历查找后面几页
        for (int i = 2; i <= pageInfo.getPages(); i++) {
            PageHelper.startPage(i, PAGE_SIZE);
            List<SyncTableUserInfo> list = syncTableUserInfoMapper.selectByStatus("1");
            if (list == null || list.isEmpty()) {
                return;
            }
            doSyncUserInfo(vrobotUrl, JSON.toJSONString(list));
        }
    }

    /**
     * 调用同步用户信息接口
     * @param url
     * @param json
     */
    private void doSyncUserInfo(String url, String json) {
        try {
            log.info("调用远程同步接口，url：{}，json：{}", url, json);
            CloseableHttpResponse response = HttpReqUtil.doPost(url, json);
            if (response.getStatusLine().getStatusCode() != StatusCodes.OK) {
                throw new Exception("响应返回的状态码：" + response.getStatusLine().getStatusCode());
            }
            String fjson = EntityUtils.toString(response.getEntity(),"UTF-8");
            List<String> fids = JSON.parseArray(fjson, String.class);
            log.info("接受到的响应的id：{}", fids);
            // 更新为已同步
            syncTableUserInfoMapper.updateByIds(fids, "2");
        } catch (Exception e) {
            log.info("同步发生错误：{}", e.getMessage(), e);
        }
    }

    /**
     * 保存信息到同步消息表
     * 管家使用：{@link SyncHouseKeeperParams}
     * 商业人员使用：{@link BaseAppServerUser}
     * 商户使用：{@link SyncBusinessUserParams}
     * @param message 同步的实体
     * @param userId 保证同一批数据的 userId 相同
     * @param operateType 1:新增，2：修改，3：删除
     * @param messageType 1:管家，2：商业人员，3：商户
     */
    public void saveSyncUserInfo(Object message, String userId, String operateType, String messageType) {

        if (message == null) {
            log.error("同步到消息表中的数据 message 为空");
            throw new BaseException("message 为空", 500);
        }
        if (StringUtils.isAnyoneEmpty(userId, operateType, messageType)) {
            log.error("同步到消息表中的数据必填项为空：, {}, {}, {}", userId, operateType, messageType);
            throw new BaseException("必填项为空", 500);
        }
        if (!"3".equals(operateType)) {
            if (!(message instanceof SyncHouseKeeperParams) && !(message instanceof BaseAppServerUser) && !(message instanceof SyncBusinessUserParams)) {
                log.error("接收到的消息不符合格式");
                throw new BaseException("消息不符合格式", 500);
            }
            if (message instanceof SyncHouseKeeperParams) {
                if (((SyncHouseKeeperParams)message).getBaseAppServerUser() == null) {
                    throw new BaseException("消息不符合格式", 500);
                }
            }
            if (message instanceof SyncBusinessUserParams) {
                if (((SyncBusinessUserParams)message).getBaseUser() == null) {
                    throw new BaseException("消息不符合格式", 500);
                }
            }
        }
        String messageJson = JSON.toJSONString(message);
        SyncTableUserInfo userInfo = new SyncTableUserInfo();
        userInfo.setId(UUIDUtils.generateUuid());
        userInfo.setUserId(userId);
        userInfo.setMessage(messageJson);
        userInfo.setCreateTime(new Date());
        userInfo.setMessageType(messageType);
        userInfo.setOperateType(operateType);

        // todo 使用分布式锁
        Integer seq = syncTableUserInfoMapper.getMaxSeqByUserId(userId);
        if (seq == null || seq.compareTo(0) < 0) {
            userInfo.setOperateSeq(1);
        } else {
            userInfo.setOperateSeq(seq + 1);
        }
        syncTableUserInfoMapper.insertSelective(userInfo);
        log.info("插入数据到同步消息表中, message:{}, userId:{}, operateType:{}, messageType:{}", messageJson, userId, operateType, messageType);
        CompletableFuture.runAsync(this::syncUserInfo);
    }

}
