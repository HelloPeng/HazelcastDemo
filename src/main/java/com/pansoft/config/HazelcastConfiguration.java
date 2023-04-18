package com.pansoft.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.MapListener;
import com.hazelcast.topic.ITopic;
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

   /* @Bean
    public Config hazelCastConfig() {
        List<String> memberList = new ArrayList<>();
        memberList.add("172.17.60.22");
        Config config = new Config();
        NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setInterfaces(new InterfacesConfig().setInterfaces(memberList));
        networkConfig.setPort(20400);
        networkConfig.setPortAutoIncrement(true);
        networkConfig.setPortCount(100);
        networkConfig.setOutboundPorts(Collections.singletonList(0));
        networkConfig.setJoin(new JoinConfig()
                .setMulticastConfig(new MulticastConfig().setEnabled(false))
                .setTcpIpConfig(new TcpIpConfig().setEnabled(true).setMembers(memberList).setRequiredMember(null).setConnectionTimeoutSeconds(5)));
        Map<String, ExecutorConfig> map = new HashMap<>();
        map.put("default", new ExecutorConfig().setPoolSize(16).setQueueCapacity(0));
        List<EntryListenerConfig> entryListenerConfigList = new ArrayList<>();
        EntryListenerConfig entryListenerConfig = new EntryListenerConfig();
        entryListenerConfig.setClassName("com.palm.vert.cluster.listener.ClusterCacheListener");
        entryListenerConfigList.add(entryListenerConfig);
        config.setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig().setName("configuration").setEvictionConfig(new EvictionConfig().setSize(200).setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE)
                                .setEvictionPolicy(EvictionPolicy.LFU))
                        .setTimeToLiveSeconds(-1).setEntryListenerConfigs(entryListenerConfigList))
                .setManagementCenterConfig(new ManagementCenterConfig().setConsoleEnabled(false))
                .setNetworkConfig(networkConfig)
                .setPartitionGroupConfig(new PartitionGroupConfig().setEnabled(false))
                .setExecutorConfigs(map);
        return config;
    }*/

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getEurekaConfig()
                .setEnabled(true)
                .setProperty("self-registration", "true")
                .setProperty("namespace", "hazelcast");
        return config;
    }
}
