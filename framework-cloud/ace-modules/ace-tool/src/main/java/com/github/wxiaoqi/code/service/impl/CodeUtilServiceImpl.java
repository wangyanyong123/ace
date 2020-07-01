package com.github.wxiaoqi.code.service.impl;

import com.github.wxiaoqi.code.lock.RedisLocker;
import com.github.wxiaoqi.code.mapper.CodeUtilMapper;
import com.github.wxiaoqi.code.service.CodeUtilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: Lzx
 * @Description:
 * @Date: Created in 11:44 2018/11/12
 * @Modified By:
 */
@Slf4j
@Service
public class CodeUtilServiceImpl implements CodeUtilService {

    @Autowired
    private CodeUtilMapper codeUtilMapper;

//    @Autowired
//    private RedisLocker1 distributedLocker;
    @Autowired
    private RedisLocker redisLocker;
    private static final int TIMEOUT = 10*1000;
    /**
     * 生成编码 【调用存储过程方式】
     *
     * @param searchKey    编码类型
     * @param prefixCode   前缀编码
     * @param codeSize     返回编码长度
     * @param sequenceType 自增序列类型
     * @return result        存储过程生成的编码
     */
    @Override
    public String getAutoCode(String searchKey, String prefixCode, String codeSize, String sequenceType) {
        return getAutoCode(searchKey,prefixCode,codeSize,sequenceType,0);
    }

    /**
     * 生成编码 【调用存储过程方式】
     *
     * @param searchKey    编码类型
     * @param prefixCode   前缀编码
     * @param codeSize     返回编码长度
     * @param sequenceType 自增序列类型
     * @param tryCount 重试次数
     *
     * @return result        存储过程生成的编码
     */
    public String getAutoCode(String searchKey, String prefixCode, String codeSize, String sequenceType,int tryCount) {
        Map<String, String> param = new HashMap<String, String>();
        param.put("result", "");
        param.put("searchKey", searchKey);
        param.put("prefixCode", prefixCode);
        param.put("codeSize", codeSize);
        param.put("sequenceType", sequenceType);
        String retCode = "";
        StringBuilder lockType = new StringBuilder();
        lockType.append(searchKey).append(".").append(prefixCode).
                append(".").append(codeSize).append(".").append(sequenceType);
        try {
            //加锁
            long time = System.currentTimeMillis() + TIMEOUT;
            if(!redisLocker.lock(lockType.toString(),String.valueOf(time))){
                log.error("生成唯一编码的人数太多，请稍后再试！！！");
                //重试5次
                if(tryCount<5){
                    Thread.sleep(500);
                    tryCount = tryCount+1;
                    return getAutoCode(searchKey,prefixCode,codeSize,sequenceType,tryCount);
                }
                return null;
            }
            codeUtilMapper.getCode(param);
            retCode = prefixCode + param.get("result");

            //解锁
            redisLocker.unlock(lockType.toString(),String.valueOf(time));

        }catch (Exception e){
            log.error("获取编码异常："+e);
            e.printStackTrace();
        }
        return retCode;
    }

//    private  void doTask(List<String> autoCodeList,Map<String, String> param,int count) {
//        String Orderno = null;
//        SimpleDateFormat format = new SimpleDateFormat("yyMMdd"); // 时间字符串产生方式
//        String uid_pfix = format.format(new Date()); // 组合流水号前一部分，时间字符串，如：20160126
//        if (maxOrderno != null && maxOrderno.contains(uid_pfix)) {
//            String uid_end = maxOrderno.substring(6, 10); // 截取字符串最后四位，结果:0001
//            System.out.println("uid_end=" + uid_end);
//            int endNum = Integer.parseInt(uid_end); // 把String类型的0001转化为int类型的1
//            System.out.println("endNum=" + endNum);
//            int tmpNum = 10000 + endNum + 1; // 结果10002
//            System.out.println("tmpNum=" + tmpNum);
//            Orderno = uid_pfix + subStr("" + tmpNum, 1);// 把10002首位的1去掉，再拼成201601260002字符串
//        } else {
//            Orderno = uid_pfix + "0001";
//        }
//        List arry = new ArrayList();
//        arry.add(Orderno);
//        //批量生成多个
//        while (count>1){
//            Orderno = String.valueOf(Integer.parseInt(Orderno.trim())+1);
//            arry.add(Orderno);
//            count--;
//        }
//
//    }

}
