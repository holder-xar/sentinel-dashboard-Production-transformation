package com.alibaba.csp.sentinel.dashboard.rule.zookeeper;

import java.io.File;

/**
 * @author holder
 * @date 2019/6/12
 * @Description:
 */
public class ZookeeperConfigUtils {

    public static final String GROUP_ID = "SENTINEL_GROUP";

    private static final String FLOW_RULE_DATA_ID_POSTFIX = "-flow-rules";

    /**
     *
     * @param app name
     * @return zk path
     */
    public static String getFlowRuleZkPath(String app){
        return File.separator + GROUP_ID + File.separator + app + FLOW_RULE_DATA_ID_POSTFIX;
    }

}
