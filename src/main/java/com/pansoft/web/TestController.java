package com.pansoft.web;

import com.alibaba.fastjson2.JSON;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 类描述：
 * 创建人：LvZhenPeng
 * 创建时间：2023-04-17
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private HazelcastInstance hazelcastInstance;
    private boolean isCanLoop;

    @GetMapping("/get")
    public String test() {
        IMap<Object, Object> map = hazelcastInstance.getMap("lvzp-testMap");
        String resultData = JSON.toJSONString(map);
        log.info("接口获取到的数据 =========== >>>>" + resultData);
        return resultData;
    }

    @PostMapping("/put")
    public String put(@RequestBody Map<String, Object> params) {
        log.info("要添加的key =========== >>>> {}", JSON.toJSONString(params));
        IMap<Object, Object> map = hazelcastInstance.getMap("lvzp-testMap");
        map.putAll(params);
        return "success";
    }

    @GetMapping("/loop")
    public String startLoop() {
        isCanLoop = true;
        while (isCanLoop) {
            try {
                IMap<Object, Object> map = hazelcastInstance.getMap("lvzp-testMap");
                String data = JSON.toJSONString(map);
                log.info("当前获取到的数据 ====== >>>> " + data);
                Thread.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "endLoop";
    }

    @GetMapping("/stop/loop")
    public String stopLoop() {
        isCanLoop = false;
        return "stopSuccess";
    }
}
