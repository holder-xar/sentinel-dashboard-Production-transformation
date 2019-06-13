package com.alibaba.csp.sentinel.dashboard.rule.zookeeper;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author holder
 * @date 2019/6/12
 * @Description:
 */
@Configuration
public class ZookeeperConfig {

    private final Logger logger = LoggerFactory.getLogger(ZookeeperConfig.class);

    private static final int DEFAULT_ZK_SESSION_TIMEOUT = 30000;
    private static final int DEFAULT_ZK_CONNECTION_TIMEOUT = 10000;
    private static final int RETRY_TIMES = 3;
    private static final int SLEEP_TIME = 3000;

    @Value("${zookeeper.remoteAddress}")
    public String remoteAddress;



    @Bean
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, FlowRuleEntity.class);
    }

    @Bean(destroyMethod = "close")
    public CuratorFramework zkClient() {
        int sessionTimeout = DEFAULT_ZK_SESSION_TIMEOUT;
        int connectionTimeout = DEFAULT_ZK_CONNECTION_TIMEOUT;
        /*if (properties.getSessionTimeout() > 0) {
            sessionTimeout = properties.getSessionTimeout();
        }
        if (properties.getConnectionTimeout() > 0) {
            connectionTimeout = properties.getConnectionTimeout();
        }*/

        CuratorFramework zkClient = CuratorFrameworkFactory.newClient(remoteAddress,
                sessionTimeout, connectionTimeout,
                new ExponentialBackoffRetry(SLEEP_TIME, RETRY_TIMES));
        zkClient.start();

        logger.info("Initialize zk client CuratorFramework, connectString={}, sessionTimeout={}, connectionTimeout={}, retry=[sleepTime={}, retryTime={}]",
                remoteAddress, sessionTimeout, connectionTimeout, SLEEP_TIME, RETRY_TIMES);
        return zkClient;
    }






}
