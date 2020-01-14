package com.wangsw.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangsw.blog.common.Result;
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
    public Result getById(@RequestParam("id") Integer id){
        JSONObject result = new JSONObject();
        try{
            TArticle tArticle = tArticleMapper.selectByPrimaryKey(id);
            result = (JSONObject) JSONObject.toJSON(tArticle);
            logger.debug("文章id=>{}",id);
            logger.debug("文章信息=>{}",result.toJSONString());

        }catch (Exception e){
            logger.error(e.toString());
            return new Result("0","请求失败");
        }
        return new Result("1","请求成功",result.toJSONString());
    }

    @ApiOperation(value = "分页查询文章列表", notes = "分页查询文章列表")
    @RequestMapping(value="/get/all",method= RequestMethod.GET)
    @ResponseBody
    public Result getAll(@RequestParam(value="pageNo",defaultValue="1")int pageNo, @RequestParam(value="pageSize",defaultValue="10")int pageSize){
        PageInfo<TArticle> result = new  PageInfo<TArticle>();
        try{
            PageHelper.startPage(pageNo,pageSize);
            List<TArticle> tArticleList = tArticleMapper.selectAll();
            if(tArticleList.size()>0){
                result = new PageInfo<>(tArticleList);
            }
            logger.debug("分页查询文章列表=>{}",result);
        }catch (Exception e){
            logger.error(e.toString());
            return new Result("0","请求失败");
        }
        return new Result("1","请求成功",result);
    }

    @ApiOperation(value = "热门文章列表", notes = "热门文章列表")
    @RequestMapping(value="/get/like",method= RequestMethod.GET)
    @ResponseBody
    public Result getLike(@RequestParam(value="pageNo",defaultValue="1")int pageNo, @RequestParam(value="pageSize",defaultValue="10")int pageSize){
        PageInfo<TArticle> result = new  PageInfo<TArticle>();
        try{
            PageHelper.startPage(pageNo,pageSize);
            List<TArticle> tArticleList = tArticleMapper.getLike();
            if(tArticleList.size()>0){
                result = new PageInfo<>(tArticleList);
            }
            logger.debug("热门文章列表=>{}",result);
        }catch (Exception e){
            logger.error(e.toString());
            return new Result("0","请求失败");
        }
        return new Result("1","请求成功",result);
    }

    @ApiOperation(value = "新增文章", notes = "新增文章")
    @RequestMapping(value="/insert",method= RequestMethod.POST)
    @ResponseBody
    public Result insert(@RequestBody TArticle article){
        try{
            article.setStatus(STATUS_Y);
            article.setCreateTime(new Date());
            article.setUpdateTime(new Date());
            tArticleMapper.insertSelective(article);
            logger.debug("文章ID=>{}",article.getId());
        }catch (Exception e){
            logger.error(e.toString());
            return new Result("0","请求失败");
        }
        return new Result("1","请求成功",article.getId()+"");
    }

    @ApiOperation(value = "更新文章", notes = "更新文章")
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody TArticle article){
        try{
            article.setUpdateTime(new Date());
            tArticleMapper.updateByPrimaryKeySelective(article);
            logger.debug("文章ID=>{}",article.getId());
        }catch (Exception e){
            logger.error(e.toString());
            return new Result("0","请求失败");
        }
        return new Result("1","请求成功",article.getId()+"");
    }

}
