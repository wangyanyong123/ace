package com.codingapi.tx.springcloud.feign;

import com.codingapi.tx.aop.bean.TxTransactionLocal;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lorne on 2017/6/26.
 */
public class TransactionRestTemplateInterceptor implements RequestInterceptor {


    private Logger logger = LoggerFactory.getLogger(TransactionRestTemplateInterceptor.class);

    @Override
    public void apply(RequestTemplate requestTemplate) {

        TxTransactionLocal txTransactionLocal = TxTransactionLocal.current();
        String groupId = txTransactionLocal == null ? null : txTransactionLocal.getGroupId();
        int maxTimeOut = txTransactionLocal == null ? 0 : txTransactionLocal.getMaxTimeOut();

        logger.info("LCN-SpringCloud TxGroup info -> groupId:" + groupId + ",maxTimeOut:" + maxTimeOut);

        if (txTransactionLocal != null) {
            requestTemplate.header("tx-group", groupId);
            requestTemplate.header("tx-maxTimeOut", String.valueOf(maxTimeOut));
        }
    }

}
