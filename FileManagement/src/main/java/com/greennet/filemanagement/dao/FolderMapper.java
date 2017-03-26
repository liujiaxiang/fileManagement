package com.greennet.filemanagement.dao;

import java.util.ArrayList;

import com.greennet.filemanagement.model.Folder;

public interface FolderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Folder record);

    int insertSelective(Folder record);

    Folder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Folder record);

    int updateByPrimaryKey(Folder record);

	ArrayList<Folder> selectByDepartment(Integer d_id);
	
	ArrayList<Integer> selectFolderIdByDid(int id);
}