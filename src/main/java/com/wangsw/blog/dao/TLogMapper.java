package com.wangsw.blog.dao;

import com.wangsw.blog.po.TLog;

import java.util.List;

public interface TLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TLog record);

    int insertSelective(TLog record);

    TLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TLog record);

    int updateByPrimaryKey(TLog record);

    List<TLog> selectAll();
}