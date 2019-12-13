package com.wangsw.blog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangsw.blog.dao.TArticleTypeMapper;
import com.wangsw.blog.po.TArticleType;
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
@RequestMapping("/articleType")
@Api(description = "文章类型接口")
public class TArticleTypeController {

    @Autowired
    TArticleTypeMapper tArticleTypeMapper;

    Logger logger = LoggerFactory.getLogger(TArticleTypeController.class);

    @ApiOperation(value = "查询文章类型", notes = "查询文章类型")
    @RequestMapping(value="/get/id",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getById(@RequestParam("id") Integer id){
        JSONObject result = new JSONObject();
        try{
            TArticleType tArticleType = tArticleTypeMapper.selectByPrimaryKey(id);
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(tArticleType);
            logger.debug("文章类型id=>{}",id);
            logger.debug("文章类型信息=>{}",jsonObject.toJSONString());
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

    @ApiOperation(value = "查询文章类型列表", notes = "查询文章类型列表")
    @RequestMapping(value="/get/all",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getAll(){
        JSONObject result = new JSONObject();
        try{
            JSONArray jsonArray = new JSONArray();
            List<TArticleType> tArticleTypeList = tArticleTypeMapper.selectAll();
            if(tArticleTypeList.size()>0){
                for(TArticleType tArticleType : tArticleTypeList){
                    jsonArray.add(JSONObject.toJSON(tArticleType));
                }
            }
            logger.debug("文章类型信息列表=>{}",jsonArray.toJSONString());
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

    @ApiOperation(value = "新增文章类型", notes = "新增文章类型")
    @RequestMapping(value="/insert",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject insert(@RequestBody TArticleType articleType){
        JSONObject result = new JSONObject();
        try{
            articleType.setStatus(STATUS_Y);
            articleType.setCreateTime(new Date());
            articleType.setUpdateTime(new Date());
            tArticleTypeMapper.insertSelective(articleType);
            logger.debug("文章类型ID=>{}",articleType.getId());
            result.put("data",articleType.getId());
            result.put("status","true");
            result.put("message","请求成功");
        }catch (Exception e){
            logger.error(e.toString());
            result.put("status","false");
            result.put("message","请求失败");
        }
        return result;
    }

    @ApiOperation(value = "更新文章类型", notes = "更新文章类型")
    @RequestMapping(value="/update",method= RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody TArticleType articleType){
        JSONObject result = new JSONObject();
        try{
            articleType.setUpdateTime(new Date());
            tArticleTypeMapper.updateByPrimaryKeySelective(articleType);
            logger.debug("文章类型ID=>{}",articleType.getId());
            result.put("data",articleType.getId());
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
