package com.wangsw.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangsw.blog.dao.TLikeMapper;
import com.wangsw.blog.po.TLike;
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
@RequestMapping("/like")
@Api(description = "点赞接口")
public class TLikeController {

    @Autowired
    TLikeMapper tLikeMapper;

    Logger logger = LoggerFactory.getLogger(TLikeController.class);

    @ApiOperation(value = "查询点赞", notes = "查询点赞")
    @RequestMapping(value="/get/id",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getById(@RequestParam("id") Integer id){
        JSONObject result = new JSONObject();
        try{
            TLike tLike = tLikeMapper.selectByPrimaryKey(id);
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(tLike);
            logger.debug("点赞id=>{}",id);
            logger.debug("点赞信息=>{}",jsonObject.toJSONString());
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

    @ApiOperation(value = "查询点赞列表", notes = "查询点赞列表")
    @RequestMapping(value="/get/all",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getAll(){
        JSONObject result = new JSONObject();
        try{
            JSONArray jsonArray = new JSONArray();
            List<TLike> tLikeList = tLikeMapper.selectAll();
            if(tLikeList.size()>0){
                for(TLike tLike : tLikeList){
                    jsonArray.add(JSONObject.toJSON(tLike));
                }
            }
            logger.debug("点赞信息列表=>{}",jsonArray.toJSONString());
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

    @ApiOperation(value = "新增点赞", notes = "新增点赞")
    @RequestMapping(value="/insert",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject insert(@RequestBody TLike like){
        JSONObject result = new JSONObject();
        try{
            like.setStatus(STATUS_Y);
            like.setCreateTime(new Date());
            like.setUpdateTime(new Date());
            tLikeMapper.insertSelective(like);
            logger.debug("点赞ID=>{}",like.getId());
            result.put("data",like.getId());
            result.put("status","true");
            result.put("message","请求成功");
        }catch (Exception e){
            logger.error(e.toString());
            result.put("status","false");
            result.put("message","请求失败");
        }
        return result;
    }

    @ApiOperation(value = "更新点赞", notes = "更新点赞")
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody TLike like){
        JSONObject result = new JSONObject();
        try{
            like.setUpdateTime(new Date());
            tLikeMapper.updateByPrimaryKeySelective(like);
            logger.debug("点赞ID=>{}",like.getId());
            result.put("data",like.getId());
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
