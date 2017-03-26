package com.greennet.filemanagement.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.greennet.filemanagement.dao.DepartmentMapper;
import com.greennet.filemanagement.model.Department;
import com.greennet.filemanagement.service.IDepartmentService;

@Service
public class DepartMentServiceImpl implements IDepartmentService
{

	@Resource
	DepartmentMapper departmentDao;
	
	public int deleteByPrimaryKey(Integer id)
	{
		return this.departmentDao.deleteByPrimaryKey(id);
	}

	public int insert(Department record)
	{
		return this.departmentDao.insert(record);
	}

	public int insertSelective(Department record)
	{
		return this.departmentDao.insertSelective(record);
	}

	public Department selectByPrimaryKey(Integer id)
	{
		return this.departmentDao.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(Department record)
	{
		return this.departmentDao.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(Department record)
	{
		return this.departmentDao.updateByPrimaryKey(record);
	}

	public ArrayList<Department> selectAll()
	{
		return this.departmentDao.selectAll();
	}

	public Department selectByName(String name)
	{
		return this.departmentDao.selectByName(name);
	}

}
