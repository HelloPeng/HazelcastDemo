package com.pansoft.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.MapListener;
import com.hazelcast.topic.ITopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类描述：
 * 创建人：LvZhenPeng
 * 创建时间：2023-04-17
 */
@Configuration
public class HazelcastConfiguration {

    @Bean
    public Config hazelCastConfig() {
        ManagementCenterConfig managementCenterConfig = new ManagementCenterConfig();
        managementCenterConfig.setConsoleEnabled(true);
        managementCenterConfig.addTrustedInterface("192.168.212.127");
        Config config = new Config();
        config.setManagementCenterConfig(managementCenterConfig);
        //设置一个名为hazelcast-instance的实体对象
        config.setInstanceName("hazelcast-instance")
                .addMapConfig(new MapConfig()
                        .setName("configuration")
                        // Map中存储条目的最大值[0~Integer.MAX_VALUE]。默认值为10000。
                        .setEvictionConfig(new EvictionConfig().setSize(Integer.MAX_VALUE)
                                //数据释放策略[NONE|LRU|LFU]。这是Map作为缓存的一个参数，用于指定数据的回收算法。默认为NONE。LRU：“最近最少使用“策略。
                                .setEvictionPolicy(EvictionPolicy.LRU))
                        //数据留存时间[0~Integer.MAX_VALUE]。缓存相关参数，单位秒，默认为0。
                        .setTimeToLiveSeconds(-1));
        return config;
    }
   /* @Bean
    public HazelcastInstance hazelcastInstance(Config config) {
        HazelcastInstance hzInstance = Hazelcast.newHazelcastInstance(config);
        //分布式map监听
        IMap<Object, Object> imap = hzInstance.getMap("hazelcastMap");
        imap.addLocalEntryListener(new MapListener());
        //拦截器（没写内容）
        imap.addInterceptor(new IMapInterceptor());
        //发布/订阅模式
        ITopic<String> topic = hzInstance.getTopic("hazelcastTopic");
        topic.addMessageListener(new TopicListener());

        return hzInstance;
    }*/
}
