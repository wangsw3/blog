package com.wangsw.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangsw.blog.common.Result;
import com.wangsw.blog.dao.TUserMapper;
import com.wangsw.blog.po.TUser;
import com.wangsw.blog.utils.AES128;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static com.wangsw.blog.common.Constants.STATUS_Y;

/**
 * Created by wangsw on 2019/12/10.
 */

@RestController
@RequestMapping("/user")
@Api(description = "用户接口")
public class TUserController {

    @Autowired
    TUserMapper tUserMapper;

    Logger logger = LoggerFactory.getLogger(TUserController.class);

    @ApiOperation(value = "查询用户", notes = "查询用户")
    @RequestMapping(value="/get/id",method= RequestMethod.GET)
    @ResponseBody
    public Result getId(@RequestParam("id") Integer id){
        JSONObject result = new JSONObject();
        try{
            TUser tUser = tUserMapper.selectByPrimaryKey(id);
            AES128.Decrypt(tUser.getPassword());



            result = (JSONObject) JSONObject.toJSON(tUser);
            logger.debug("用户id=>{}",id);
            logger.debug("用户信息=>{}",result.toJSONString());
        }catch (Exception e){
            logger.error(e.toString());
            return new Result("0","请求失败");
        }
        return new Result("1","请求成功",result.toJSONString());
    }

    @ApiOperation(value = "查询用户", notes = "查询用户")
    @RequestMapping(value="/get/name",method= RequestMethod.GET)
    @ResponseBody
    public Result getName(@RequestParam("userName") String userName){
        JSONObject result = new JSONObject();
        try{
            TUser tUser = tUserMapper.selectByUserName(userName);
            result = (JSONObject) JSONObject.toJSON(tUser);
            logger.debug("userName=>{}",userName);
            logger.debug("用户信息=>{}",result.toJSONString());
        }catch (Exception e){
            logger.error(e.toString());
            return new Result("0","请求失败");
        }
        return new Result("1","请求成功",result.toJSONString());
    }

    @ApiOperation(value = "分页查询所有用户列表", notes = "分页查询所有用户列表")
    @RequestMapping(value="/get/all",method= RequestMethod.GET)
    @ResponseBody
    public Result getAll(@RequestParam(value="pageNo",defaultValue="1")int pageNo, @RequestParam(value="pageSize",defaultValue="10")int pageSize){
        PageInfo<TUser> result = new  PageInfo<TUser>();
        try{
            PageHelper.startPage(pageNo,pageSize);
            List<TUser> tUserList = tUserMapper.selectAll();
            if(tUserList.size()>0){
                result = new PageInfo<>(tUserList);
            }
            logger.debug("分页用户信息列表=>{}",result);
        }catch (Exception e){
            logger.error(e.toString());
            return new Result("0","请求失败");
        }
        return new Result("1","请求成功",result);
    }

    @ApiOperation(value = "条件分页查询所有用户列表", notes = "条件分页查询所有用户列表")
    @RequestMapping(value="/get/para",method= RequestMethod.GET)
    @ResponseBody
    public Result getPara(@RequestBody TUser user, @RequestParam(value="pageNo",defaultValue="1")int pageNo, @RequestParam(value="pageSize",defaultValue="10")int pageSize){
        PageInfo<TUser> result = new  PageInfo<TUser>();
        try{
            PageHelper.startPage(pageNo,pageSize);
            List<TUser> tUserList = tUserMapper.getPara(user);
            if(tUserList.size()>0){
                result = new PageInfo<>(tUserList);
            }
            logger.debug("分页用户信息列表=>{}",result);
        }catch (Exception e){
            logger.error(e.toString());
            return new Result("0","请求失败");
        }
        return new Result("1","请求成功",result);
    }

    @ApiOperation(value = "最近登录用户列表", notes = "最近登录用户列表")
    @RequestMapping(value="/get/recent",method= RequestMethod.GET)
    @ResponseBody
    public Result getRecent(@RequestParam(value="pageNo",defaultValue="1")int pageNo, @RequestParam(value="pageSize",defaultValue="10")int pageSize){
        PageInfo<TUser> result = new  PageInfo<TUser>();
        try{
            PageHelper.startPage(pageNo,pageSize);
            List<TUser> tUserList = tUserMapper.getRecent();
            if(tUserList.size()>0){
                result = new PageInfo<>(tUserList);
            }
            logger.debug("最近登录用户信息列表=>{}",result);
        }catch (Exception e){
            logger.error(e.toString());
            return new Result("0","请求失败");
        }
        return new Result("1","请求成功",result);
    }

    @ApiOperation(value = "热评用户列表", notes = "热评用户列表")
    @RequestMapping(value="/get/hot",method= RequestMethod.GET)
    @ResponseBody
    public Result getHot(){
        JSONArray result = new JSONArray();
        try{
            List<TUser> tUserList = tUserMapper.getHot();
            if(tUserList.size()>0){
                for(TUser tUser : tUserList){
                    result.add(JSONObject.toJSON(tUser));
                }
            }
            logger.debug("热评用户列表=>{}",result.toJSONString());
        }catch (Exception e){
            logger.error(e.toString());
            return new Result("0","请求失败");
        }
        return new Result("1","请求成功",result.toJSONString());
    }

    @ApiOperation(value = "新增用户", notes = "新增用户")
    @RequestMapping(value="/insert",method= RequestMethod.POST)
    @ResponseBody
    public Result insert(@RequestBody TUser user){
        try{
            String password = AES128.Encrypt(user.getPassword());
            user.setStatus(STATUS_Y);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            tUserMapper.insertSelective(user);
            logger.debug("用户ID=>{}",user.getId());
        }catch (Exception e){
            logger.error(e.toString());
            return new Result("0","请求失败");
        }
        return new Result("1","请求成功",user.getId()+"");
    }

    @ApiOperation(value = "更新用户", notes = "更新用户")
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody TUser user){
        try{
            user.setUpdateTime(new Date());
            tUserMapper.updateByPrimaryKeySelective(user);
            logger.debug("用户ID=>{}",user.getId());
        }catch (Exception e){
            logger.error(e.toString());
            return new Result("0","请求失败");
        }
        return new Result("1","请求成功",user.getId()+"");
    }

}
