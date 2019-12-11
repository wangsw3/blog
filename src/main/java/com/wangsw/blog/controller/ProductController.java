package com.wangsw.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by wangsw on 2019/12/10.
 */
@Controller
@RequestMapping("/PRODUCT")
public class ProductController {

   /* @Autowired
    ProductInfoMapper productInfoMapper;

    @ResponseBody
    @RequestMapping(value="/GET1",method= RequestMethod.GET)
    public JSONArray putProductInfo(){

        ProductInfo productInfo = new ProductInfo();
        String productCode = "2134123412";
        productInfo.setProductCode(productCode);
        productInfo.setProductName(productCode);
        productInfo.setId(123);
        productInfoMapper.insert(productInfo);
        System.out.println(productInfo.getId());
        return new JSONArray();
    }


    @RequestMapping(value="/GET",method= RequestMethod.GET)
    @ResponseBody
    public JSONArray getProductInfo(){
        JSONArray jsonArray = new JSONArray();
        ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(1);
        JSONObject jd = (JSONObject)JSONObject.toJSON(productInfo);
        System.out.println(jd.toJSONString());
        return new JSONArray();
        *//*productInfoMapper.selectByPrimaryKey(1);
        return new JSONArray();*//*
    }

    public JSONObject productToJSONObject(ProductInfo productInfo){

        return new JSONObject();
    }*/
}
