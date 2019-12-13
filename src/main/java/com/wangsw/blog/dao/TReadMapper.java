package com.wangsw.blog.dao;

import com.wangsw.blog.po.TRead;

public interface TReadMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TRead record);

    int insertSelective(TRead record);

    TRead selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TRead record);

    int updateByPrimaryKey(TRead record);
}