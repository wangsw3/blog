package com.wangsw.blog.dao;

import com.wangsw.blog.po.TLike;

public interface TLikeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TLike record);

    int insertSelective(TLike record);

    TLike selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TLike record);

    int updateByPrimaryKey(TLike record);
}