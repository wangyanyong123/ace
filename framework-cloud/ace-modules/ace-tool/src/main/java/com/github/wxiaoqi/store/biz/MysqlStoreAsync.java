package com.github.wxiaoqi.store.biz;

import com.github.wxiaoqi.constants.ResponseCodeEnum;
import com.github.wxiaoqi.security.api.vo.store.product.ProductStore;
import com.github.wxiaoqi.security.common.constant.AceDictionary;
import com.github.wxiaoqi.security.common.msg.ObjectRestResponse;
import com.github.wxiaoqi.store.entity.BizStore;
import com.github.wxiaoqi.store.mapper.BizStoreMapper;
import com.github.wxiaoqi.store.redis.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

@Component
public class MysqlStoreAsync {
    private static Logger logger = LoggerFactory.getLogger(MysqlStoreAsync.class);

    @Autowired
    private BizStoreLogBiz bizStoreLogBiz;

    @Resource
    private BizStoreMapper bizStoreMapper;

    @Autowired
    private RedisUtil redisUtil;


    @Async
    public ObjectRestResponse mysqlStore(ProductStore productStore,Integer operationType){
        ObjectRestResponse objectRestResponse = new ObjectRestResponse();
        BizStore bizStore = bizStoreMapper.queryStoreNum(productStore.getSpecId(),0);
        if(ObjectUtils.isEmpty(bizStore)){
            return redisUtil.errorRestResponse(ResponseCodeEnum.MYSQL_STORE_UPDATE_FAIL);
        }
        int currentNum = productStore.getAccessNum() + bizStore.getStoreNum();
        if(bizStore.getIsLimit()){
            int num = bizStoreMapper.updateStore(productStore.getSpecId(),currentNum,0);
            if(num < 0){
                objectRestResponse = redisUtil.errorRestResponse(ResponseCodeEnum.MYSQL_STORE_UPDATE_FAIL);
                logger.error("支付成功，操作结果：{}-{}，参数信息：{}",objectRestResponse.success(),objectRestResponse.getMessage(),productStore);
            }
        }
        bizStoreLogBiz.addStoreLogUpdate(productStore,bizStore.getStoreNum(),currentNum,bizStore.getIsLimit(),operationType,objectRestResponse);
        return objectRestResponse;
    }
}
