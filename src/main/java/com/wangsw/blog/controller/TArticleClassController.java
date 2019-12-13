package com.wangsw.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangsw.blog.dao.TArticleClassMapper;
import com.wangsw.blog.po.TArticleClass;
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
@RequestMapping("/articleClass")
@Api(description = "文章分类接口")
public class TArticleClassController {

    @Autowired
    TArticleClassMapper tArticleClassMapper;

    Logger logger = LoggerFactory.getLogger(TArticleClassController.class);

    @ApiOperation(value = "查询文章分类", notes = "查询文章分类")
    @RequestMapping(value="/get/id",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getById(@RequestParam("id") Integer id){
        JSONObject result = new JSONObject();
        try{
            TArticleClass tArticleClass = tArticleClassMapper.selectByPrimaryKey(id);
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(tArticleClass);
            logger.debug("文章分类id=>{}",id);
            logger.debug("文章分类信息=>{}",jsonObject.toJSONString());
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

    @ApiOperation(value = "查询文章分类列表", notes = "查询文章分类列表")
    @RequestMapping(value="/get/all",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getAll(){
        JSONObject result = new JSONObject();
        try{
            JSONArray jsonArray = new JSONArray();
            List<TArticleClass> tArticleClassList = tArticleClassMapper.selectAll();
            if(tArticleClassList.size()>0){
                for(TArticleClass tArticleClass : tArticleClassList){
                    jsonArray.add(JSONObject.toJSON(tArticleClass));
                }
            }
            logger.debug("文章分类信息列表=>{}",jsonArray.toJSONString());
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

    @ApiOperation(value = "新增文章分类", notes = "新增文章分类")
    @RequestMapping(value="/insert",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject insert(@RequestBody TArticleClass articleClass){
        JSONObject result = new JSONObject();
        try{
            articleClass.setStatus(STATUS_Y);
            articleClass.setCreateTime(new Date());
            articleClass.setUpdateTime(new Date());
            tArticleClassMapper.insertSelective(articleClass);
            logger.debug("文章分类ID=>{}",articleClass.getId());
            result.put("data",articleClass.getId());
            result.put("status","true");
            result.put("message","请求成功");
        }catch (Exception e){
            logger.error(e.toString());
            result.put("status","false");
            result.put("message","请求失败");
        }
        return result;
    }

    @ApiOperation(value = "更新文章分类", notes = "更新文章分类")
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody TArticleClass articleClass){
        JSONObject result = new JSONObject();
        try{
            articleClass.setUpdateTime(new Date());
            tArticleClassMapper.updateByPrimaryKeySelective(articleClass);
            logger.debug("文章分类ID=>{}",articleClass.getId());
            result.put("data",articleClass.getId());
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
