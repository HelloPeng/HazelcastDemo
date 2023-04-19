package com.pansoft.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.MapListener;
import com.hazelcast.nacos.NacosDiscoveryProperties;
import com.hazelcast.nacos.NacosDiscoveryStrategyFactory;
import com.hazelcast.topic.ITopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * 类描述：
 * 创建人：LvZhenPeng
 * 创建时间：2023-04-17
 */
@Configuration
public class HazelcastConfiguration {

    /**
     * 配置初始化Config
     * @return
     */
    @Bean
    public Config hazelCastConfig() {
        Config config = new Config();
        config.setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig().setName("configuration").setEvictionConfig(new EvictionConfig().setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                                .setEvictionPolicy(EvictionPolicy.LFU))
                        .setTimeToLiveSeconds(-1))
                .setManagementCenterConfig(new ManagementCenterConfig().setConsoleEnabled(false))
                .setPartitionGroupConfig(new PartitionGroupConfig().setEnabled(false));
        return config;
    }

    /**
     * 初始化HazelcastInstance
     * @param config
     * @return
     */
    @Bean
    @ConditionalOnBean(Config.class)
    public HazelcastInstance hazelcastInstance(Config config) {
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.setProperty("hazelcast.discovery.enabled", "true");

        DiscoveryStrategyConfig discoveryStrategyConfig = new DiscoveryStrategyConfig(new NacosDiscoveryStrategyFactory())
                .addProperty(NacosDiscoveryProperties.SERVER_ADDR.key(), "192.168.249.5:10038")
                .addProperty(NacosDiscoveryProperties.APPLICATION_NAME.key(), "nacos")
                .addProperty(NacosDiscoveryProperties.NAMESPACE.key(), "nacos")
                .addProperty(NacosDiscoveryProperties.CLUSTER_NAME.key(), "nacos");
        config.getNetworkConfig().getJoin().getDiscoveryConfig().addDiscoveryStrategyConfig(discoveryStrategyConfig);

        return Hazelcast.newHazelcastInstance(config);
    }
}
