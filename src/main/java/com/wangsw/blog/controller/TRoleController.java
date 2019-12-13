package com.wangsw.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangsw.blog.dao.TRoleMapper;
import com.wangsw.blog.po.TRole;
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
@RequestMapping("/role")
@Api(description = "角色接口")
public class TRoleController {

    @Autowired
    TRoleMapper tRoleMapper;

    Logger logger = LoggerFactory.getLogger(TRoleController.class);

    @ApiOperation(value = "查询角色", notes = "查询角色")
    @RequestMapping(value="/get/id",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getById(@RequestParam("id") Integer id){
        JSONObject result = new JSONObject();
        try{
            TRole tRole = tRoleMapper.selectByPrimaryKey(id);
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(tRole);
            logger.debug("角色id=>{}",id);
            logger.debug("角色信息=>{}",jsonObject.toJSONString());
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

    @ApiOperation(value = "查询角色列表", notes = "查询角色列表")
    @RequestMapping(value="/get/all",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getAll(){
        JSONObject result = new JSONObject();
        try{
            JSONArray jsonArray = new JSONArray();
            List<TRole> tRoleList = tRoleMapper.selectAll();
            if(tRoleList.size()>0){
                for(TRole tRole : tRoleList){
                    jsonArray.add(JSONObject.toJSON(tRole));
                }
            }
            logger.debug("角色信息列表=>{}",jsonArray.toJSONString());
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

    @ApiOperation(value = "新增角色", notes = "新增角色")
    @RequestMapping(value="/insert",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject insert(@RequestBody TRole role){
        JSONObject result = new JSONObject();
        try{
            role.setStatus(STATUS_Y);
            role.setCreateTime(new Date());
            role.setUpdateTime(new Date());
            tRoleMapper.insertSelective(role);
            logger.debug("角色ID=>{}",role.getId());
            result.put("data",role.getId());
            result.put("status","true");
            result.put("message","请求成功");
        }catch (Exception e){
            logger.error(e.toString());
            result.put("status","false");
            result.put("message","请求失败");
        }
        return result;
    }

    @ApiOperation(value = "更新角色", notes = "更新角色")
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody TRole role){
        JSONObject result = new JSONObject();
        try{
            role.setUpdateTime(new Date());
            tRoleMapper.updateByPrimaryKeySelective(role);
            logger.debug("角色ID=>{}",role.getId());
            result.put("data",role.getId());
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
