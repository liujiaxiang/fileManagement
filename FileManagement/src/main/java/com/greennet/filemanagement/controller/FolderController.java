package com.greennet.filemanagement.controller;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greennet.filemanagement.common.CheckUser;
import com.greennet.filemanagement.contants.AttributeConstant;
import com.greennet.filemanagement.model.Department;
import com.greennet.filemanagement.model.Folder;
import com.greennet.filemanagement.model.User;
import com.greennet.filemanagement.service.IDepartmentService;
import com.greennet.filemanagement.service.IFileService;
import com.greennet.filemanagement.service.IFolderService;
import com.greennet.filemanagement.service.IUserService;
import com.sun.mail.imap.DefaultFolder;
import com.sun.org.apache.regexp.internal.recompile;

@Controller
public class FolderController
{
	@Resource
	IFolderService folderService;
	@Resource
	IDepartmentService departmentService;
	@Resource
	IUserService userService;
	@Resource
	IFileService fileService;
	
	/**
	 * 管理部门文件夹
	 */
	@RequestMapping("manDepartmentFolder")
	public String manDepartmentFolder(HttpServletRequest request, Model model)
	{
		if (!CheckUser.isLogined(request.getSession()))
		{
			model.addAttribute("loginFlag", "0");
			return "/admin/login";
		}
		//获取当前的部门id
		Integer d_id = Integer.parseInt(request.getParameter("d_id"));
		//获取部门
		Department department = this.departmentService.selectByPrimaryKey(d_id);
		//根据部门id获取当前部门所有的文件夹
		ArrayList<Folder> folders = this.folderService.selectByDepartment(d_id);
		
		//列出该组的教师文件夹
		model.addAttribute("op", "manFolder");
		model.addAttribute("folders", folders);
		model.addAttribute("department", department);
		model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/manDepartmentFolder.vm");
		return "/admin/index";
	}
	
	@RequestMapping("addFolder")
	public String addFolder(HttpServletRequest request,Model model)
	{
		
		
		if (!CheckUser.isLogined(request.getSession()))
		{
			model.addAttribute("loginFlag", "0");
			return "/admin/login";
		}
		Department department = new Department();
		
		//通过当前用户获取部门
		User curUser = (User) request.getSession().getAttribute("user");
		
		//当前用户是管理员,则可以在任何部门下创建文件夹，否则只能在用户所属的组下创建
		if ("admin".equals(curUser.getUsername()))
		{
			department = this.departmentService
					    .selectByPrimaryKey(Integer.parseInt(request.getParameter("d_id")));
		}
		else 
		{
			department = this.departmentService.selectByName(curUser.getDepartment());
		}
		
		//获取文件夹信息
		Folder record = new Folder();
		record.setFolderName(request.getParameter("folder_name").trim());
		//record.setFolderDes(request.getParameter("folder_des").trim());
		record.setdId(department.getId());
		record.setuId(curUser.getId());
		
		System.out.println(record.toString());
		
		//添加文件夹
		int result = this.folderService.insert(record);
		
		if (1 != result)
		{
			model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/error.vm");
			return "/admin/index"; 
		}
		
		//获取该部门下的所有文件夹
		ArrayList<Folder> folders = this.folderService.selectByDepartment(department.getId());
		
		model.addAttribute("op", "manFolder");
		model.addAttribute("folders", folders);
		model.addAttribute("department", department);
		model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/manDepartmentFolder.vm");
		return "/admin/index";
	}
	
	@RequestMapping("delfolder")
	public String delfolder(HttpServletRequest request, Model model)
	{
		
		User curUser = (User) request.getSession().getAttribute("user");
		if (!"admin".equals(curUser.getUsername()))
		{
			model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/noauth.vm");
			return "/admin/index";
		}
		//先删除文件夹下的文件才能删除文件夹
		this.fileService.deleteByFolderId(Integer.parseInt(request.getParameter("f_id")));
		this.folderService.deleteByPrimaryKey(Integer.parseInt(request.getParameter("f_id")));
		
		//获取当前的部门id
		Integer d_id = Integer.parseInt(request.getParameter("d_id"));
		//获取部门
		Department department = this.departmentService.selectByPrimaryKey(d_id);
		//获取该部门下的所有文件夹
		ArrayList<Folder> folders = this.folderService.selectByDepartment(department.getId());
		
		model.addAttribute("op", "manFolder");
		model.addAttribute("folders", folders);
		model.addAttribute("department", department);
		model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/manDepartmentFolder.vm");
		return "/admin/index";
				
	}
	
}
