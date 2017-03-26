package com.greennet.filemanagement.service;

import com.greennet.filemanagement.model.User;


public interface IUserService {  
	int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    public User checkUser(User record);
}  