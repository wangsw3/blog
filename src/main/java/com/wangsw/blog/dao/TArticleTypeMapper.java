package com.wangsw.blog.dao;

import com.wangsw.blog.po.TArticleType;

public interface TArticleTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TArticleType record);

    int insertSelective(TArticleType record);

    TArticleType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TArticleType record);

    int updateByPrimaryKey(TArticleType record);
}