package com.wangsw.blog.controller;

import com.wangsw.blog.common.Constants;
import com.wangsw.blog.common.Result;
import com.wangsw.blog.dao.TLogMapper;
import com.wangsw.blog.dao.TUserMapper;
import com.wangsw.blog.po.TLog;
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

import java.util.Date;

import static com.wangsw.blog.common.Constants.STATUS_Y;

/**
 * Created by wangsw on 2019/12/16.
 */

@RestController
public class LoginController{

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private TUserMapper tUserMapper;

    @Autowired
    private TLogMapper tLogMapper;

    @ApiOperation(value = "是否登录", notes = "是否登录")
    @RequestMapping(value="/IsLogin",method= RequestMethod.POST)
    @ResponseBody
    public Result isLogin(@RequestBody TUser user){
        String data = "false";
        //上次登录时间+设置超时时间
        long sessionTimeOut = SecurityUtils.getSubject().getSession().getLastAccessTime().getTime()+SecurityUtils.getSubject().getSession().getTimeout();
        if(sessionTimeOut > new Date().getTime()){
            //用户已经登录
            Object curUser = SecurityUtils.getSubject().getSession().getAttribute(user.getUserName());
            if (null != curUser) {
                data = "true";
                logger.debug("用户:"+user.getUserName()+"已登录");
            }else {
                data = "false";
                logger.debug("用户:"+user.getUserName()+"未登录");
            }
        }else {
            logger.debug("用户:"+user.getUserName()+"登录超时超时");
        }

        return new Result("1","请求成功",data);
    }

    @ApiOperation(value = "登录", notes = "登录")
    @RequestMapping(value="/Login",method= RequestMethod.POST)
    @ResponseBody
    public Result Login(@RequestBody TUser user){
        try {
            //shiro认证
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), AES128.Encrypt(user.getPassword()));
            subject.login(token);

            //用户信息存入session
            subject.getSession().setAttribute(user.getUserName(), tUserMapper.selectByUserName(user.getUserName()));
            user = tUserMapper.selectByUserName(user.getUserName());
            SecurityUtils.getSubject().getSession().setAttribute(user.getUserName(), user);
            SecurityUtils.getSubject().getSession().setTimeout(Constants.TIME_OUT);

            //记录日志
            TLog tLog = new TLog();
            tLog.setLogType(Constants.LOGIN);
            tLog.setUserId(user.getId());
            tLog.setDescription("登录");
            tLog.setIpAddress(user.getIp());
            tLog.setAddressName(user.getAddress());
            tLog.setCreateTime(new Date());
            tLog.setStatus(STATUS_Y);
            tLogMapper.insertSelective(tLog);

        } catch (UnknownAccountException e) {
            logger.debug("用户:"+user.getUserName()+"账号或密码错误", e.toString());
            return new Result("0","账号或密码错误");
        } catch (Exception e) {
            logger.debug("用户:"+user.getUserName()+"登录失败", e.toString());
            return new Result("0","登录失败");
        }
        logger.debug("用户:"+user.getUserName()+"登录成功");
        return new Result("1","登录成功");
    }

    @ApiOperation(value = "登出", notes = "登出")
    @RequestMapping(value="/Logout",method= RequestMethod.POST)
    @ResponseBody
    public Result Logout(@RequestBody TUser user){
        SecurityUtils.getSubject().getSession().removeAttribute(user.getUserName());
        logger.debug("用户:"+user.getUserName()+"登出成功");

        //记录日志
        TLog tLog = new TLog();
        tLog.setLogType(Constants.LOGOUT);
        tLog.setUserId(user.getId());
        tLog.setDescription("登出");
        tLog.setIpAddress(user.getIp());
        tLog.setAddressName(user.getAddress());
        tLog.setCreateTime(new Date());
        tLog.setStatus(STATUS_Y);
        tLogMapper.insertSelective(tLog);

        return new Result("1","登出成功");
    }

}