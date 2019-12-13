package com.wangsw.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangsw.blog.dao.TUserMapper;
import com.wangsw.blog.po.TUser;
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
    public JSONObject getById(@RequestParam("id") Integer id){
        JSONObject result = new JSONObject();
        try{
            TUser tUser = tUserMapper.selectByPrimaryKey(id);
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(tUser);
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
    public JSONObject getAll(){
        JSONObject result = new JSONObject();
        try{
            JSONArray jsonArray = new JSONArray();
            List<TUser> tUserList = tUserMapper.selectAll();
            if(tUserList.size()>0){
                for(TUser tUser : tUserList){
                    jsonArray.add(JSONObject.toJSON(tUser));
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
    @RequestMapping(value="/insert",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject insert(@RequestBody TUser user){
        JSONObject result = new JSONObject();
        try{
            user.setStatus(STATUS_Y);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            tUserMapper.insertSelective(user);
            logger.debug("用户ID=>{}",user.getId());
            result.put("data",user.getId());
            result.put("status","true");
            result.put("message","请求成功");
        }catch (Exception e){
            logger.error(e.toString());
            result.put("status","false");
            result.put("message","请求失败");
        }
        return result;
    }

    @ApiOperation(value = "更新用户", notes = "更新用户")
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody TUser user){
        JSONObject result = new JSONObject();
        try{
            user.setUpdateTime(new Date());
            tUserMapper.updateByPrimaryKeySelective(user);
            logger.debug("用户ID=>{}",user.getId());
            result.put("data",user.getId());
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
