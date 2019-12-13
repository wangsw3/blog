package com.wangsw.blog.dao;

import com.wangsw.blog.po.TArticleClass;

import java.util.List;

public interface TArticleClassMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TArticleClass record);

    int insertSelective(TArticleClass record);

    TArticleClass selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TArticleClass record);

    int updateByPrimaryKey(TArticleClass record);

    List<TArticleClass> selectAll();
}