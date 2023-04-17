package com.pansoft.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述：
 * 创建人：LvZhenPeng
 * 创建时间：2023-04-17
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public String test(){
        return "success";
    }

}
