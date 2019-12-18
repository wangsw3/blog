package com.wangsw.blog.controller;

import com.wangsw.blog.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangsw on 2019/12/16.
 */

@RestController
public class LoginController {
    /*
    * 是否登录
    * */
    @RequestMapping("/IsLogin")
    public Result isLogin(){
        return new Result("1","请求成功","false");
    }

    /*
  * 登录
  * */
    @RequestMapping("/Login")
    public Result Login(){
        //获取
        return new Result("1","请求成功","");
    }
}