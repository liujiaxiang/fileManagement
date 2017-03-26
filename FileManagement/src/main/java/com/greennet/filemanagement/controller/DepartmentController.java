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
import com.greennet.filemanagement.model.User;
import com.greennet.filemanagement.service.IDepartmentService;
import com.greennet.filemanagement.service.IFileService;
import com.greennet.filemanagement.service.IFolderService;
import com.greennet.filemanagement.service.IUserService;

@Controller
public class DepartmentController
{
	@Resource
	private IDepartmentService departmentService;
	@Resource
	private IUserService userService;
	@Resource
	IFolderService folderService;
	@Resource
	IFileService fileService;
	
	@RequestMapping("showdepartment")
	public String showDepartMent(HttpServletRequest request, Model model)
	{
		if (!CheckUser.isLogined(request.getSession()))
		{
			model.addAttribute("loginFlag", "0");
			return "/admin/login";
		}
		//获取目前的所有部门
		ArrayList<Department> departments = this.departmentService.selectAll();
		//给页面传递数据
		model.addAttribute("departments", departments);
		model.addAttribute("op", "show");
		model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/adddepartment.vm");
		return "/admin/index";
	}
	/**
	 * 添加部门页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("adddepartment")
	public String addDepartMent(HttpServletRequest request, Model model)
	{
		if (!CheckUser.isLogined(request.getSession()))
		{
			model.addAttribute("loginFlag", "0");
			return "/admin/login";
		}
		System.out.println(request.getSession().getAttribute("username"));
		model.addAttribute("op", "add");
		model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/adddepartment.vm");
		return "/admin/index";
		
	}
	
	/**
	 * 添加部门处理，并跳转到相应页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("department_add")
	public String toIndex(HttpServletRequest request, Model model)
	{
		if (!CheckUser.isLogined(request.getSession()))
		{
			model.addAttribute("loginFlag", "0");
			return "/admin/login";
		}
		String departmentName = request.getParameter("department_name").trim();
		String departmentDes  = request.getParameter("department_des").trim();
		System.out.println(departmentName+"  "+departmentDes);
		Department record = new Department();
		record.setDepartmentName(departmentName);
		record.setDepartmentDes(departmentDes);
		
		if (this.departmentService.selectByName(departmentName) !=null)
		{
			model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/error.vm");
			return "/admin/index";
		}
		
		int result = this.departmentService.insert(record);

		if (result != 1)
		{
			model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/error.vm");
			return "/admin/index";
		}
		
		//获取目前的所有部门
		ArrayList<Department> departments = this.departmentService.selectAll();
		//给页面传递数据
		model.addAttribute("departments", departments);
		model.addAttribute("op", "show");
		model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/adddepartment.vm");
		return "/admin/index";
	}
	
	/**
	 * 管理部门
	 */
	@RequestMapping("mandepartment")
	public String mandepartment(HttpServletRequest request, Model model)
	{
		if (!CheckUser.isLogined(request.getSession()))
		{
			model.addAttribute("loginFlag", "0");
			return "/admin/login";
		}
		//获取目前的所有部门
		ArrayList<Department> departments = this.departmentService.selectAll();
		//给页面传递数据
		model.addAttribute("departments", departments);
		model.addAttribute("op", "man");
		model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/mandepartment.vm");
		return "/admin/index";
	}
	
	/**
	 * 删除部门
	 */
	@RequestMapping("deldepartment")
	public String deldepartment(HttpServletRequest request, Model model)
	{
		if (!CheckUser.isLogined(request.getSession()))
		{
			model.addAttribute("loginFlag", "0");
			return "/admin/login";
		}
		
		User curUser = (User) request.getSession().getAttribute("user");
		if (!"admin".equals(curUser.getUsername()))
		{
			model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/noauth.vm");
			return "/admin/index";
		}
		
		int id = Integer.parseInt(request.getParameter("id").trim());
		//获取文件夹id集合
		ArrayList<Integer> f_ids = this.folderService.selectFolderIdByDid(id);
		
		System.out.println(f_ids);
		//要先删除部门下面的文件夹下的文件，再删除部门下面的文件夹，最后再删除部门
		for (Integer f_id : f_ids)
		{
			this.fileService.deleteByFolderId(f_id);
			this.folderService.deleteByPrimaryKey(f_id);
		}

		int result = this.departmentService.deleteByPrimaryKey(id);
		
		if (1 != result)
		{
			model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/error.vm");
			return "/admin/index";
		}
		//获取目前的所有部门
		ArrayList<Department> departments = this.departmentService.selectAll();
		//给页面传递数据
		model.addAttribute("departments", departments);
		model.addAttribute("op", "man");
		model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/mandepartment.vm");
		return "/admin/index";
	}
	
	/**
	 * 修改部门
	 */
	@RequestMapping("moddepartment")
	public String moddepartment(HttpServletRequest request, Model model)
	{
		
		int id = Integer.parseInt(request.getParameter("id").trim());
		Department department = new Department();
		department.setId(id);
		department.setDepartmentName(request.getParameter("department_name").trim());
		department.setDepartmentDes(request.getParameter("department_des").trim());
		
		int result = this.departmentService.updateByPrimaryKey(department);
		
		if (1 != result)
		{
			model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/error.vm");
			return "/admin/index";
		}
		//获取目前的所有部门
		ArrayList<Department> departments = this.departmentService.selectAll();
		//给页面传递数据
		model.addAttribute("departments", departments);
		model.addAttribute("op", "man");
		model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/mandepartment.vm");
		return "/admin/index";
	}
	

	
}
