package com.greennet.filemanagement.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.greennet.filemanagement.dao.FolderMapper;
import com.greennet.filemanagement.model.Folder;
import com.greennet.filemanagement.service.IFolderService;

@Service
public class FolderServiceImpl implements IFolderService
{
	@Resource
	FolderMapper folderDao;
	
	public int deleteByPrimaryKey(Integer id)
	{
		return this.folderDao.deleteByPrimaryKey(id);
	}

	public int insert(Folder record)
	{
		return this.folderDao.insert(record);
	}

	public int insertSelective(Folder record)
	{
		return this.folderDao.insertSelective(record);
	}

	public Folder selectByPrimaryKey(Integer id)
	{
		return this.folderDao.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(Folder record)
	{
		return this.folderDao.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(Folder record)
	{
		return this.folderDao.updateByPrimaryKey(record);
	}

	public ArrayList<Folder> selectByDepartment(Integer d_id)
	{
		return this.folderDao.selectByDepartment(d_id);
	}

	public ArrayList<Integer> selectFolderIdByDid(int id)
	{
		return this.folderDao.selectFolderIdByDid(id);
	}

}
