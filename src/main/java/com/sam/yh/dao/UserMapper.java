package com.sam.yh.dao;

import com.sam.yh.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);
    
    int updateByPrimaryKey(User record);
    
    User selectByUserName(String userName);  
        

    User selectByUserAccount(String userAccount);  
       
    User selectByUserDeviceInfo(String mobilePhone);//Joy modify

}