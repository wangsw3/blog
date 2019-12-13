package com.wangsw.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangsw.blog.dao.TArticleMapper;
import com.wangsw.blog.po.TArticle;
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
@RequestMapping("/article")
@Api(description = "文章接口")
public class TArticleController {

    @Autowired
    TArticleMapper tArticleMapper;

    Logger logger = LoggerFactory.getLogger(TArticleController.class);

    @ApiOperation(value = "查询文章", notes = "查询文章")
    @RequestMapping(value="/get/id",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getById(@RequestParam("id") Integer id){
        JSONObject result = new JSONObject();
        try{
            TArticle tArticle = tArticleMapper.selectByPrimaryKey(id);
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(tArticle);
            logger.debug("文章id=>{}",id);
            logger.debug("文章信息=>{}",jsonObject.toJSONString());
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

    @ApiOperation(value = "查询文章列表", notes = "查询文章列表")
    @RequestMapping(value="/get/all",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getAll(){
        JSONObject result = new JSONObject();
        try{
            JSONArray jsonArray = new JSONArray();
            List<TArticle> tArticleList = tArticleMapper.selectAll();
            if(tArticleList.size()>0){
                for(TArticle tArticle : tArticleList){
                    jsonArray.add(JSONObject.toJSON(tArticle));
                }
            }
            logger.debug("文章信息列表=>{}",jsonArray.toJSONString());
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

    @ApiOperation(value = "新增文章", notes = "新增文章")
    @RequestMapping(value="/insert",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject insert(@RequestBody TArticle article){
        JSONObject result = new JSONObject();
        try{
            article.setStatus(STATUS_Y);
            article.setCreateTime(new Date());
            article.setUpdateTime(new Date());
            tArticleMapper.insertSelective(article);
            logger.debug("文章ID=>{}",article.getId());
            result.put("data",article.getId());
            result.put("status","true");
            result.put("message","请求成功");
        }catch (Exception e){
            logger.error(e.toString());
            result.put("status","false");
            result.put("message","请求失败");
        }
        return result;
    }

    @ApiOperation(value = "更新文章", notes = "更新文章")
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody TArticle article){
        JSONObject result = new JSONObject();
        try{
            article.setUpdateTime(new Date());
            tArticleMapper.updateByPrimaryKeySelective(article);
            logger.debug("文章ID=>{}",article.getId());
            result.put("data",article.getId());
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
