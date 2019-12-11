package com.wangsw.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.wangsw.blog.dao.UserInfoMapper;
import com.wangsw.blog.po.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wangsw on 2019/12/10.
 */

@RestController
@RequestMapping("/blog")
@Api(description = "用户接口")
public class UserController {

    @Autowired
    UserInfoMapper userInfoMapper;

    @ApiOperation(value = "查询用户", notes = "查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户标识", required = true, paramType = "query", dataType = "Integer")
    })
    @RequestMapping(value="/get",method= RequestMethod.GET)
    @ResponseBody
    public JSONObject getProductInfo(@RequestParam("id") Integer id){
        JSONObject jsonObject = new JSONObject();
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);
        jsonObject = (JSONObject) JSONObject.toJSON(userInfo);
        System.out.println(jsonObject.toJSONString());
        return jsonObject;
    }

}
