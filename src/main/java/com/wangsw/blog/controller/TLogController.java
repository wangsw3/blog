package com.wangsw.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangsw.blog.dao.TLogMapper;
import com.wangsw.blog.po.TLog;
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
@RequestMapping("/log")
@Api(description = "日志接口")
public class TLogController {

    @Autowired
    TLogMapper tLogMapper;

    Logger logger = LoggerFactory.getLogger(TLogController.class);

    @ApiOperation(value = "查询日志", notes = "查询日志")
    @RequestMapping(value="/get/id",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getById(@RequestParam("id") Integer id){
        JSONObject result = new JSONObject();
        try{
            TLog tLog = tLogMapper.selectByPrimaryKey(id);
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(tLog);
            logger.debug("日志id=>{}",id);
            logger.debug("日志信息=>{}",jsonObject.toJSONString());
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

    @ApiOperation(value = "查询日志列表", notes = "查询日志列表")
    @RequestMapping(value="/get/all",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getAll(){
        JSONObject result = new JSONObject();
        try{
            JSONArray jsonArray = new JSONArray();
            List<TLog> tLogList = tLogMapper.selectAll();
            if(tLogList.size()>0){
                for(TLog tLog : tLogList){
                    jsonArray.add(JSONObject.toJSON(tLog));
                }
            }
            logger.debug("日志信息列表=>{}",jsonArray.toJSONString());
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

    @ApiOperation(value = "新增日志", notes = "新增日志")
    @RequestMapping(value="/insert",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject insert(@RequestBody TLog log){
        JSONObject result = new JSONObject();
        try{
            log.setStatus(STATUS_Y);
            log.setCreateTime(new Date());
            tLogMapper.insertSelective(log);
            logger.debug("日志ID=>{}",log.getId());
            result.put("data",log.getId());
            result.put("status","true");
            result.put("message","请求成功");
        }catch (Exception e){
            logger.error(e.toString());
            result.put("status","false");
            result.put("message","请求失败");
        }
        return result;
    }

    @ApiOperation(value = "更新日志", notes = "更新日志")
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody TLog log){
        JSONObject result = new JSONObject();
        try{
            tLogMapper.updateByPrimaryKeySelective(log);
            logger.debug("日志ID=>{}",log.getId());
            result.put("data",log.getId());
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
