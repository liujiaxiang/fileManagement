package com.greennet.filemanagement.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.greennet.filemanagement.common.CheckUser;
import com.greennet.filemanagement.contants.AttributeConstant;
import com.greennet.filemanagement.model.Department;
import com.greennet.filemanagement.model.File;
import com.greennet.filemanagement.model.Folder;
import com.greennet.filemanagement.model.User;
import com.greennet.filemanagement.service.IDepartmentService;
import com.greennet.filemanagement.service.IFileService;
import com.greennet.filemanagement.service.IFolderService;
import com.greennet.filemanagement.service.IUserService;
import com.greennet.filemanagement.util.PageUtil;

/**
 * 文件管理
* <p>Title: FileController</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author liujiaxiang 
* @date 2016年11月19日 下午6:36:53
 */

@Controller
public class FileController
{
	@Resource
	IFileService fileService;
	@Resource
	IUserService userService;
	@Resource
	IFolderService folderService;
	@Resource
	IDepartmentService departmentService;
	
	@RequestMapping("manfile")
	public String manFile(HttpServletRequest request, Model model)
	{
		if (!CheckUser.isLogined(request.getSession()))
		{
			model.addAttribute("loginFlag", "0");
			return "/admin/login";
		}
		//获取当前用户
		User curUser = (User) request.getSession().getAttribute("user");
		
		//获取文件夹
		int fId = Integer.parseInt(request.getParameter("f_id"));
		Folder folder = this.folderService.selectByPrimaryKey(fId);
		//获取部门
		int dId = Integer.parseInt(request.getParameter("d_id"));
		Department department = this.departmentService.selectByPrimaryKey(dId);
		//获取点击的文件夹所属用户
		int uId = Integer.parseInt(request.getParameter("u_id"));
		
		User f_User = this.userService.selectByPrimaryKey(uId);
		//获取当前页
		int curPage = 1;
		if (request.getParameter("page") != null)
		{
			curPage  = Integer.parseInt(request.getParameter("page").trim());
		}
		//开始行
		int startRow = Integer.parseInt(request.getParameter("startRow").trim());
		//每一页记录数
		int pageSize = AttributeConstant.PAGE_SIZE;
		//数据库里的记录总页数
		int pageNum = PageUtil.getPageNum(this.fileService.getFileNum(fId));
		ArrayList<File> files = this.fileService.getFilePage(startRow, pageSize, fId);
		//ArrayList<File> files = this.fileService.selectAll();
		
		model.addAttribute("files", files);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("curPage", curPage);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("startRow", startRow);
		model.addAttribute("f_User", f_User);
		model.addAttribute("folder", folder);
		model.addAttribute("department", department);
		
		model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/manfile.vm");
		return "/admin/index";
	}
	
	@RequestMapping("addFile")
	public String addFile(@RequestParam MultipartFile mfile, HttpServletRequest request, Model model) throws IOException
	{
		if (!CheckUser.isLogined(request.getSession()))
		{
			model.addAttribute("loginFlag", "0");
			return "/admin/login";
		}
		
		User curUser = new User();
		curUser =  (User) request.getSession().getAttribute("user");
		
		//用户
		int uId = Integer.parseInt(request.getParameter("u_id"));
		User f_User = this.userService.selectByPrimaryKey(uId);
		
		//获取文件夹
		int fId = Integer.parseInt(request.getParameter("f_id"));
		Folder folder = this.folderService.selectByPrimaryKey(fId);
		
		//获取部门
		int dId = Integer.parseInt(request.getParameter("d_id"));
		Department department = this.departmentService.selectByPrimaryKey(dId);
//		
//		if (!f_User.getUsername().equals(curUser.getUsername()))
//		{
//			model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/noauth.vm");
//			return "/admin/index"; 
//		}
		
		
		ArrayList<String> fileName = new ArrayList<String>();
		File file = new File();
		
		//如果只是上传一个文件，则只需要MultipartFile类型接收文件即可，而且无需显式指定@RequestParam注解  
        //如果想上传多个文件，那么这里就要用MultipartFile[]类型来接收文件，并且还要指定@RequestParam注解  
        //并且上传多个文件时，前台表单中的所有<input type="file"/>的name都应该是myfiles，否则参数里的myfiles无法获取到所有上传的文件  
          
        if(mfile.isEmpty())
        { 
        	//文件未上传,提示用户上传学校图片
        	model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/error.vm");
			return "/admin/index"; 
        }
        else
        {  
            System.out.println("文件长度: " + mfile.getSize());  
            System.out.println("文件类型: " + mfile.getContentType());  
            System.out.println("文件名称: " + mfile.getName());  
            System.out.println("文件原名: " + mfile.getOriginalFilename());  
            System.out.println("========================================");  
            //如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
            String realPath = request.getSession().getServletContext().getRealPath("/views/asserts/upload");  
            System.out.println(realPath);
            
            //这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的  
            String saveFileName = mfile.getOriginalFilename();
            FileUtils.copyInputStreamToFile(mfile.getInputStream(), new java.io.File
            		(realPath, saveFileName));
            fileName.add(saveFileName);
            file.setfId(fId);
            file.setFileName(saveFileName);
        	file.setFilePath("./views/asserts/upload"+"/"+saveFileName);
        	file.setUploadUser(curUser.getUsername());
        }  
        
        
        int result = this.fileService.insert(file);
    	if (1 != result)
		{
			model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/error.vm");
			return "/admin/index"; 
		}
        
        //获取当前页
  		int curPage = 1;
  		if (request.getParameter("page") != null)
  		{
  			curPage  = Integer.parseInt(request.getParameter("page").trim());
  		}
  		//开始行
  		int startRow = Integer.parseInt(request.getParameter("startRow").trim());
  		//每一页记录数
  		int pageSize = AttributeConstant.PAGE_SIZE;
  		//数据库里的记录总页数
  		int pageNum = PageUtil.getPageNum(this.fileService.getFileNum(fId));

        ArrayList<File> files = this.fileService.getFilePage(startRow, pageSize, fId);
		
		//ArrayList<File> mfiles = this.fileService.selectAll();
		
        model.addAttribute("files", files);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("curPage", curPage);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("startRow", startRow);
		model.addAttribute("curUser", curUser);
		model.addAttribute("f_User", f_User);
		model.addAttribute("folder", folder);
		model.addAttribute("department", department);
		
		model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/manfile.vm");
		return "/admin/index";
	}
	
	@RequestMapping("delFile")
	public String delFile(HttpServletRequest request, Model model)
	{
		if (!CheckUser.isLogined(request.getSession()))
		{
			model.addAttribute("loginFlag", "0");
			return "/admin/login";
		}
		
		User curUser = new User();
		curUser =  (User) request.getSession().getAttribute("user");
		
		//文件
		int f_id = Integer.parseInt(request.getParameter("file_id"));
		File file = this.fileService.selectByPrimaryKey(f_id);
		
		//文件夹
		int fId = Integer.parseInt(request.getParameter("folder_id"));
		Folder folder = this.folderService.selectByPrimaryKey(fId);
		
		//获取部门
		Department department = this.departmentService.selectByPrimaryKey(folder.getdId());
		
		//当前登录用户与上传用户不相同，并且当前用户不是管理，那么不能删除文件，否则删除文件
		if (!file.getUploadUser().equals(curUser.getUsername()) 
				&& !("admin".equals(curUser.getUsername())))
		{
			model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/error.vm");
			return "/admin/index"; 
		}
		this.fileService.deleteByPrimaryKey(f_id);
		 //设置当前页为第一页
  		int curPage = 1;
  		//开始行
  		int startRow = 0;
  		//每一页记录数
  		int pageSize = AttributeConstant.PAGE_SIZE;
  		//数据库里的记录总页数
  		int pageNum = PageUtil.getPageNum(this.fileService.getFileNum(fId));
  	
		
        ArrayList<File> files = this.fileService.getFilePage(startRow, pageSize, fId);
        
        model.addAttribute("files", files);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("curPage", curPage);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("startRow", startRow);
		model.addAttribute("curUser", curUser);
		model.addAttribute("folder", folder);
		model.addAttribute("department", department);
		
		model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/manfile.vm");
		return "/admin/index";
	}
}
