package com.pansoft.web;

import com.alibaba.fastjson2.JSON;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
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
public class TestController {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    @GetMapping("/get")
    public String test() {
        IMap<Object, Object> map = hazelcastInstance.getMap("lvzp-testMap");
        return JSON.toJSONString(map);
    }

    @PostMapping("/put")
    public String put(@RequestBody Map<String, Object> params) {
        IMap<Object, Object> map = hazelcastInstance.getMap("lvzp-testMap");
        map.putAll(params);
        return "success";
    }
}
