package com.inrich.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.inrich.model.HostHolder;
import com.inrich.util.OutPrintUtil;


@Component
public class LoginInterceptor implements HandlerInterceptor{
	@Autowired
	HostHolder hostHolder;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse response, Object arg2) throws Exception {
		if(hostHolder.getUser() == null ) {
			System.out.println("-------进入登录验证");
			PrintWriter out=response.getWriter();
			out.print(OutPrintUtil.getJSONString("unlogin", "请先登录"));
			//response.sendRedirect("/SpringISMP/login.html");
			return false;
		}
		return true;
	}

}
