package com.greennet.filemanagement.service;

import java.util.ArrayList;

import com.greennet.filemanagement.model.File;

public interface IFileService
{
    int deleteByPrimaryKey(Integer id);

    int insert(File record);

    int insertSelective(File record);

    File selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(File record);

    int updateByPrimaryKey(File record);
    
    ArrayList<File> selectAll();
    
    int getFileNum(int f_id);
    
    public ArrayList<File> getFilePage(int startRow, int pageSize,int f_id);

    int deleteByFolderId(int f_id);
}
