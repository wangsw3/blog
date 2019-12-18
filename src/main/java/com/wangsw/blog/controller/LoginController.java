package com.wangsw.blog.controller;

import com.wangsw.blog.common.Constants;
import com.wangsw.blog.common.Result;
import com.wangsw.blog.dao.TUserMapper;
import com.wangsw.blog.po.TUser;
import com.wangsw.blog.utils.AES128;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangsw on 2019/12/16.
 */

@RestController
public class LoginController{

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private TUserMapper tUserMapper;

    @ApiOperation(value = "是否登录", notes = "是否登录")
    @RequestMapping(value="/IsLogin",method= RequestMethod.GET)
    @ResponseBody
    public Result isLogin(){
        String data = "false";
        if (SecurityUtils.getSubject().isAuthenticated()) {
            data = "true";
            logger.debug("账号已登录");
        }else {
            data = "false";
            logger.debug("账号未登录");
        }
        return new Result("1","请求成功",data);
    }

    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value="/Login",method= RequestMethod.POST)
    @ResponseBody
    public Result Login(@RequestBody TUser user){
        try {
            // shiro认证
            Subject subject = SecurityUtils.getSubject();
            //UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), AES128.Encrypt(user.getPassword()));
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
            subject.login(token);
            //subject.getSession().setAttribute(Constants.SESSION_USER, tUserMapper.selectByUserName(user.getUserName()));
            //session.setAttribute("currentUser", tUserMapper.selectByUserName(user.getUserName()));
        } catch (UnknownAccountException e) {
            logger.debug("登陆失败", e.toString());
            return new Result("0","账号或密码错误!");
        } catch (Exception e) {
            logger.debug("登陆失败", e.toString());
            return new Result("0","登陆失败!");
        }
        //获取
        return new Result("1","登录成功");
    }

}