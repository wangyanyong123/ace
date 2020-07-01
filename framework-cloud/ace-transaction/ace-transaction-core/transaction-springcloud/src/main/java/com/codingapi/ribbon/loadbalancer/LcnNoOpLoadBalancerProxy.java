package com.codingapi.ribbon.loadbalancer;

import com.netflix.loadbalancer.NoOpLoadBalancer;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * created by foxdd 2017-12-05
 */
public class LcnNoOpLoadBalancerProxy extends NoOpLoadBalancer {

    LcnLoadBalancerRule lcnLoadBalancerRule = new LcnLoadBalancerRule();
    private Logger logger = LoggerFactory.getLogger(LcnNoOpLoadBalancerProxy.class);

    public LcnNoOpLoadBalancerProxy() {
        super();
    }

    @Override
    public Server chooseServer(Object key) {
        logger.info("enter chooseServer method, key:" + key);

        List<Server> serverList = new ArrayList<Server>();
        return lcnLoadBalancerRule.proxy(serverList, super.chooseServer(key));

    }

}
