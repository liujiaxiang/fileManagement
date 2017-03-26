package com.greennet.filemanagement.service;

import java.util.ArrayList;

import com.greennet.filemanagement.model.Department;

public interface IDepartmentService
{
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);
    
    Department selectByName(String name);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
    
    ArrayList<Department> selectAll();

}
