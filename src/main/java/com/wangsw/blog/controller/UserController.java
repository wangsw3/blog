package com.wangsw.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangsw.blog.dao.UserInfoMapper;
import com.wangsw.blog.po.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by wangsw on 2019/12/10.
 */

@RestController
@RequestMapping("/user")
@Api(description = "用户接口")
public class UserController {

    @Autowired
    UserInfoMapper userInfoMapper;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "查询用户", notes = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户标识", required = true, paramType = "query", dataType = "Integer")
    })
    @RequestMapping(value="/get/id",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getUserInfo(@RequestParam("id") Integer id){
        JSONObject result = new JSONObject();
        try{
            UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(userInfo);
            logger.debug("用户id=>{}",id);
            logger.debug("用户信息=>{}",jsonObject.toJSONString());
            result.put("data",jsonObject.toJSONString());
            result.put("status","true");
            result.put("message","请求成功");
        }catch (Exception e){
            logger.error(e.toString());
            result.put("status","false");
            result.put("message","请求失败");
        }
        return result;
    }

    @ApiOperation(value = "查询用户列表", notes = "查询用户列表")
    @RequestMapping(value="/get/all",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getUserAll(){
        JSONObject result = new JSONObject();
        try{
            JSONArray jsonArray = new JSONArray();
            List<UserInfo> userInfoList = userInfoMapper.getUserAll();
            if(userInfoList.size()>0){
                for(UserInfo userInfo : userInfoList){
                    jsonArray.add(JSONObject.toJSON(userInfo));
                }
            }
            logger.debug("用户信息列表=>{}",jsonArray.toJSONString());
            result.put("data",jsonArray.toJSONString());
            result.put("status","true");
            result.put("message","请求成功");
        }catch (Exception e){
            logger.error(e.toString());
            result.put("status","false");
            result.put("message","请求失败");
        }
        return result;
    }

    @ApiOperation(value = "新增用户", notes = "新增用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userCode", value = "用户账号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userName", value = "用户名称", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "telephone", value = "手机号码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "avatar", value = "用户头像", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value="/insert",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject insertUserInfo(@RequestParam("userCode") String userCode,
                                  @RequestParam("password") String password,
                                  @RequestParam("userName") String userName,
                                  @RequestParam("telephone") String telephone,
                                  @RequestParam("avatar") String avatar){
        JSONObject result = new JSONObject();
        try{
            UserInfo userInfo = new UserInfo();
            userInfo.setUserCode(userCode);
            userInfo.setPassword(password);
            userInfo.setUserName(userName);
            userInfo.setTelephone(telephone);
            userInfo.setAvatar(avatar);
            int id = userInfoMapper.insert(userInfo);
            logger.debug("用户ID=>{}",id);
            result.put("data",id);
            result.put("status","true");
            result.put("message","请求成功");
        }catch (Exception e){
            logger.error(e.toString());
            result.put("status","false");
            result.put("message","请求失败");
        }
        return result;
    }

    @ApiOperation(value = "新增用户", notes = "新增用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户主键", required = true, paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "userCode", value = "用户账号", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userName", value = "用户名称", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "telephone", value = "手机号码", required = true, paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "avatar", value = "用户头像", required = true, paramType = "query", dataType = "String")
    })
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject updateUserInfo(@RequestParam("id") Integer id,
                                 @RequestParam("userCode") String userCode,
                                  @RequestParam("password") String password,
                                  @RequestParam("userName") String userName,
                                  @RequestParam("telephone") String telephone,
                                  @RequestParam("avatar") String avatar){
        JSONObject result = new JSONObject();
        try{
            UserInfo userInfo = new UserInfo();
            userInfo.setId(id);
            userInfo.setUserCode(userCode);
            userInfo.setPassword(password);
            userInfo.setUserName(userName);
            userInfo.setTelephone(telephone);
            userInfo.setAvatar(avatar);
            userInfoMapper.updateByPrimaryKey(userInfo);
            logger.debug("用户ID=>{}",id);
            result.put("data",id);
            result.put("status","true");
            result.put("message","请求成功");
        }catch (Exception e){
            logger.error(e.toString());
            result.put("status","false");
            result.put("message","请求失败");
        }
        return result;
    }

}
