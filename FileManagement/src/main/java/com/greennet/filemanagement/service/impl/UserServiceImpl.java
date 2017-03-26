package com.greennet.filemanagement.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.greennet.filemanagement.dao.UserMapper;
import com.greennet.filemanagement.model.User;
import com.greennet.filemanagement.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService
{

	@Resource
	private UserMapper userDao;
	
	public int deleteByPrimaryKey(Integer id)
	{
		return this.userDao.deleteByPrimaryKey(id);
	}

	public int insert(User record)
	{
		return this.userDao.insert(record);
	}

	public int insertSelective(User record)
	{
		return this.userDao.insertSelective(record);
	}

	public User selectByPrimaryKey(Integer id)
	{
		return this.userDao.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(User record)
	{
		return this.userDao.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(User record)
	{
		return this.updateByPrimaryKey(record);
	}

	public User checkUser(User record)
	{
		return this.userDao.selectByNameAndPassWord(record);
	}

}
