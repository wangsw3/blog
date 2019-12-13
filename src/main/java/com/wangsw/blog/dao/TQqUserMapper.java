package com.wangsw.blog.dao;

import com.wangsw.blog.po.TQqUser;

import java.util.List;

public interface TQqUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TQqUser record);

    int insertSelective(TQqUser record);

    TQqUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TQqUser record);

    int updateByPrimaryKey(TQqUser record);

    List<TQqUser> selectAll();
}