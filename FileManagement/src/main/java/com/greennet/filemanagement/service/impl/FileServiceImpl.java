package com.greennet.filemanagement.service.impl;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.greennet.filemanagement.dao.FileMapper;
import com.greennet.filemanagement.model.File;
import com.greennet.filemanagement.service.IFileService;

@Service
public class FileServiceImpl implements IFileService
{

	@Resource
	FileMapper fileDao;
	
	public int deleteByPrimaryKey(Integer id)
	{
		return this.fileDao.deleteByPrimaryKey(id);
	}

	public int insert(File record)
	{
		return this.fileDao.insert(record);
	}

	public int insertSelective(File record)
	{
		return this.fileDao.insertSelective(record);
	}

	public File selectByPrimaryKey(Integer id)
	{
		return this.fileDao.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(File record)
	{
		return this.fileDao.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(File record)
	{
		return this.fileDao.updateByPrimaryKey(record);
	}

	public ArrayList<File> selectAll()
	{
		return this.fileDao.selectAll();
	}

	public int getFileNum(int f_id)
	{
		return this.fileDao.getFileNum(f_id);
	}

	public ArrayList<File> getFilePage(int startRow, int pageSize,int f_id)
	{
		return this.fileDao.getFilePage(startRow, pageSize,f_id);
	}

	public int deleteByFolderId(int f_id)
	{

		return this.fileDao.deleteByFolderId(f_id);
	}

}
