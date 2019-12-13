package com.wangsw.blog.dao;

import com.wangsw.blog.po.TArticle;

import java.util.List;

public interface TArticleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TArticle record);

    int insertSelective(TArticle record);

    TArticle selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TArticle record);

    int updateByPrimaryKeyWithBLOBs(TArticle record);

    int updateByPrimaryKey(TArticle record);

    List<TArticle> selectAll();
}