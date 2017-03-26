package com.greennet.filemanagement.util;

import com.greennet.filemanagement.contants.AttributeConstant;

public class PageUtil
{
	
	public static int getPageNum(int dbCount)
	{
		int pageNum = 1;
		int pageSize = AttributeConstant.PAGE_SIZE;
		//一页20条记录,计算页数
		if ((dbCount / pageSize == 0))
		{
			pageNum = 1;
		}
		else if ((dbCount % pageSize == 0))
		{
			pageNum = dbCount / pageSize;
		}
		else
		{
			pageNum = dbCount / pageSize +1;
		}
		
		return pageNum;
		
	}
	
}
