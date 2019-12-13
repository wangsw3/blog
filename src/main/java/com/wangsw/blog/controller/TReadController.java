package com.wangsw.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangsw.blog.dao.TReadMapper;
import com.wangsw.blog.po.TRead;
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
@RequestMapping("/read")
@Api(description = "已阅接口")
public class TReadController {

    @Autowired
    TReadMapper tReadMapper;

    Logger logger = LoggerFactory.getLogger(TReadController.class);

    @ApiOperation(value = "查询已阅", notes = "查询已阅")
    @RequestMapping(value="/get/id",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getById(@RequestParam("id") Integer id){
        JSONObject result = new JSONObject();
        try{
            TRead tRead = tReadMapper.selectByPrimaryKey(id);
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(tRead);
            logger.debug("已阅id=>{}",id);
            logger.debug("已阅信息=>{}",jsonObject.toJSONString());
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

    @ApiOperation(value = "查询已阅列表", notes = "查询已阅列表")
    @RequestMapping(value="/get/all",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getAll(){
        JSONObject result = new JSONObject();
        try{
            JSONArray jsonArray = new JSONArray();
            List<TRead> tReadList = tReadMapper.selectAll();
            if(tReadList.size()>0){
                for(TRead tRead : tReadList){
                    jsonArray.add(JSONObject.toJSON(tRead));
                }
            }
            logger.debug("已阅信息列表=>{}",jsonArray.toJSONString());
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

    @ApiOperation(value = "新增已阅", notes = "新增已阅")
    @RequestMapping(value="/insert",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject insert(@RequestBody TRead read){
        JSONObject result = new JSONObject();
        try{
            read.setStatus(STATUS_Y);
            read.setCreateTime(new Date());
            tReadMapper.insertSelective(read);
            logger.debug("已阅ID=>{}",read.getId());
            result.put("data",read.getId());
            result.put("status","true");
            result.put("message","请求成功");
        }catch (Exception e){
            logger.error(e.toString());
            result.put("status","false");
            result.put("message","请求失败");
        }
        return result;
    }

    @ApiOperation(value = "更新已阅", notes = "更新已阅")
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody TRead read){
        JSONObject result = new JSONObject();
        try{
            tReadMapper.updateByPrimaryKeySelective(read);
            logger.debug("已阅ID=>{}",read.getId());
            result.put("data",read.getId());
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
