package com.greennet.filemanagement.common;

import javax.servlet.http.HttpSession;

import com.greennet.filemanagement.contants.AttributeConstant;
import com.greennet.filemanagement.model.User;



public class CheckUser
{
	/***
     * 判断是否已登录
     * @param user2
     * @return
     */
    public static boolean isLogined(User user2,String loginFlag){
        if((user2 == null) || (user2.getUsername().isEmpty()) 
        	|| (loginFlag == null) || (!loginFlag.equalsIgnoreCase(AttributeConstant.SESSION_KEY_LOGIN_SUCESS)))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    /***
     * 判断是否已登录
     * @param session
     * @return
     */
    public static boolean isLogined(HttpSession session){
        String loginFlag = (String) session.getAttribute(AttributeConstant.SESSION_KEY_LOGINED_FLAG);
        User user2 = (User) session.getAttribute(AttributeConstant.SESSION_KEY_LOGINED_USER);
        return isLogined(user2,loginFlag);
    }
}
