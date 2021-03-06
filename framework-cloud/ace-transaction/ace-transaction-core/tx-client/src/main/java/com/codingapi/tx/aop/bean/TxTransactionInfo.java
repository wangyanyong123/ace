package com.codingapi.tx.aop.bean;

import com.codingapi.tx.annotation.TxTransaction;
import com.codingapi.tx.model.TransactionInvocation;


/**
 * 切面控制对象
 * Created by lorne on 2017/6/8.
 */
public class TxTransactionInfo {


    private TxTransaction transaction;


    private TxTransactionLocal txTransactionLocal;

    /**
     * 事务组Id
     */
    private String txGroupId;

    /**
     * 最大超时时间（发起方模块的）
     */
    private int maxTimeOut;


    private TransactionInvocation invocation;


    public TxTransactionInfo(TxTransaction transaction, TxTransactionLocal txTransactionLocal, TransactionInvocation invocation, String txGroupId, int maxTimeOut) {
        this.transaction = transaction;
        this.txTransactionLocal = txTransactionLocal;
        this.txGroupId = txGroupId;
        this.maxTimeOut = maxTimeOut;
        this.invocation = invocation;
    }

    public int getMaxTimeOut() {
        return maxTimeOut;
    }


    public TxTransaction getTransaction() {
        return transaction;
    }

    public TxTransactionLocal getTxTransactionLocal() {
        return txTransactionLocal;
    }

    public String getTxGroupId() {
        return txGroupId;
    }

    public TransactionInvocation getInvocation() {
        return invocation;
    }

}
