package com.wangsw.blog.dao;

import com.wangsw.blog.po.TUser;

import java.util.List;

public interface TUserMapper {

    TUser selectByPrimaryKey(Integer id);

    TUser selectByUserName(String userName);

    List<TUser> selectAll();

    List<TUser> getPara(TUser record);

    List<TUser> getRecent();

    int deleteByPrimaryKey(Integer id);

    int insert(TUser record);

    int insertSelective(TUser record);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);
}