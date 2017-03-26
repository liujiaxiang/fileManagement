package com.greennet.filemanagement.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.greennet.filemanagement.contants.AttributeConstant;
import com.greennet.filemanagement.model.Department;
import com.greennet.filemanagement.model.User;
import com.greennet.filemanagement.service.IDepartmentService;
import com.greennet.filemanagement.service.IUserService;

/**
 * 用户登录
* <p>Title: LoginController</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author liujiaxiang 
* @date 2016年11月18日 上午11:07:15
 */
@Controller
public class UserController
{
	
	@Resource
	private IUserService userService;
	
	@Resource
	private IDepartmentService departmentService;
	
	@RequestMapping("/login")
	public String login()
	{
		return "/admin/login";
	}
	
	@RequestMapping("/checkLogin")
	public String checkLogin(HttpServletRequest request, Model model)
	{
		try
		{
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		String username= request.getParameter("username");
		String password=request.getParameter("password");  
		User record = new User();
		record.setUsername(username);
		record.setPassword(password);
		User user = this.userService.checkUser(record);
		

		if (user == null)
		{
			model.addAttribute("user", "null");
			return "/admin/login";
		}
		request.getSession().setAttribute(AttributeConstant.SESSION_KEY_LOGINED_USER, user);
		request.getSession().setAttribute(AttributeConstant.SESSION_KEY_LOGINED_FLAG, AttributeConstant.SESSION_KEY_LOGIN_SUCESS);
		
		//获取目前的所有部门
		ArrayList<Department> departments = this.departmentService.selectAll();
		//给页面传递数据
		model.addAttribute("departments", departments);
		model.addAttribute("user", user);
		model.addAttribute("op", "show");
		model.addAttribute(AttributeConstant.MAIN_PAGE, "./admin/pages/adddepartment.vm");
		return "/admin/index";
	}
	
	/**
	 * 注册页面
	 */
	@RequestMapping("register")
	public String register(Model model)
	{
		//获取部门
		ArrayList<Department> departments = this.departmentService.selectAll();
		
		model.addAttribute("departments", departments);
		return "/admin/register";
	}
	
	/**
	 * 注册结果
	 */
	@RequestMapping("registerResult")
	public String registerResult(HttpServletRequest request, Model model)
	{
		User user = new User();
		user.setUsername(request.getParameter("username").trim());
		user.setPassword(request.getParameter("password").trim());
		user.setDepartment(request.getParameter("departname").trim());
		int result = this.userService.insert(user);
		
		if (1 != result)
		{
			return "/admin/pages/error";
		}
		
		model.addAttribute("result", "success");
		return "/admin/login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request)
	{
		request.getSession().removeAttribute(AttributeConstant.SESSION_KEY_LOGINED_USER);
		request.getSession().removeAttribute(AttributeConstant.SESSION_KEY_LOGINED_FLAG);
		
		return "/admin/login";
	}
	
}
