package com.wangsw.blog.dao;

import com.wangsw.blog.po.TComment;

public interface TCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TComment record);

    int insertSelective(TComment record);

    TComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TComment record);

    int updateByPrimaryKey(TComment record);
}