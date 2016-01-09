package com.sam.yh.dao;

import com.sam.yh.model.UserAttribute;
import com.sam.yh.model.UserAttributeKey;

public interface UserAttributeMapper {
    int deleteByPrimaryKey(UserAttributeKey key);

    int insert(UserAttribute record);

    int insertSelective(UserAttribute record);

    UserAttribute selectByPrimaryKey(UserAttributeKey key);

    int updateByPrimaryKeySelective(UserAttribute record);

    int updateByPrimaryKey(UserAttribute record);
}