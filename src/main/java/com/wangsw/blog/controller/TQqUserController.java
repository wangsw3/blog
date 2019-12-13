package com.wangsw.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangsw.blog.dao.TQqUserMapper;
import com.wangsw.blog.po.TQqUser;
import io.swagger.annotations.Api;
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
@RequestMapping("/qqUser")
@Api(description = "QQ用户接口")
public class TQqUserController {

    @Autowired
    TQqUserMapper tQqUserMapper;

    Logger logger = LoggerFactory.getLogger(TQqUserController.class);

    @ApiOperation(value = "查询QQ用户", notes = "查询QQ用户")
    @RequestMapping(value="/get/id",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getById(@RequestParam("id") Integer id){
        JSONObject result = new JSONObject();
        try{
            TQqUser tQqUser = tQqUserMapper.selectByPrimaryKey(id);
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(tQqUser);
            logger.debug("QQ用户id=>{}",id);
            logger.debug("QQ用户信息=>{}",jsonObject.toJSONString());
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

    @ApiOperation(value = "查询QQ用户列表", notes = "查询QQ用户列表")
    @RequestMapping(value="/get/all",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getAll(){
        JSONObject result = new JSONObject();
        try{
            JSONArray jsonArray = new JSONArray();
            List<TQqUser> tQqUserList = tQqUserMapper.selectAll();
            if(tQqUserList.size()>0){
                for(TQqUser tQqUser : tQqUserList){
                    jsonArray.add(JSONObject.toJSON(tQqUser));
                }
            }
            logger.debug("QQ用户信息列表=>{}",jsonArray.toJSONString());
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

    @ApiOperation(value = "新增QQ用户", notes = "新增QQ用户")
    @RequestMapping(value="/insert",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject insert(@RequestBody TQqUser qqUser){
        JSONObject result = new JSONObject();
        try{
            qqUser.setStatus(STATUS_Y);
            qqUser.setCreateTime(new Date());
            qqUser.setLastloginTime(new Date());
            tQqUserMapper.insertSelective(qqUser);
            logger.debug("QQ用户ID=>{}",qqUser.getId());
            result.put("data",qqUser.getId());
            result.put("status","true");
            result.put("message","请求成功");
        }catch (Exception e){
            logger.error(e.toString());
            result.put("status","false");
            result.put("message","请求失败");
        }
        return result;
    }

    @ApiOperation(value = "更新QQ用户", notes = "更新QQ用户")
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody TQqUser qqUser){
        JSONObject result = new JSONObject();
        try{
            qqUser.setLastloginTime(new Date());
            tQqUserMapper.updateByPrimaryKeySelective(qqUser);
            logger.debug("QQ用户ID=>{}",qqUser.getId());
            result.put("data",qqUser.getId());
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
