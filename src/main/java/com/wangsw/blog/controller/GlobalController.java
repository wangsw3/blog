package com.wangsw.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangsw on 2019/12/16.
 */

@Controller
public class GlobalController {
    /*
    * 默认加载页
    * */
    @RequestMapping({"/", "/Index"})
    public String toIndex(){
        return "page/front/Index";
    }

    /*
   * 地址转发
   * */
    @RequestMapping("{module}")
    public String toModulePage(@PathVariable String module){
        return "page/front/"+module;
    }
}