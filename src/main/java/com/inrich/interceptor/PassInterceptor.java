package com.inrich.interceptor;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.inrich.dao.TicketDAO;
import com.inrich.dao.UserDAO;
import com.inrich.model.HostHolder;
import com.inrich.model.LoginTicket;


@Component
public class PassInterceptor implements HandlerInterceptor {
	@Autowired
	private TicketDAO  ticketDao;
	@Autowired
	private HostHolder hostHolder;
	@Autowired
	private UserDAO userDAO;

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		hostHolder.clear();
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView modelAndView)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		//System.out.println("-------进入拦截器1");
		String ticket=null;
		Cookie[] cookies=request.getCookies();
		if(cookies != null) {
			for(Cookie cookie:cookies) {
				if("ticket".equals(cookie.getName())) {
					ticket=cookie.getValue();
					//System.out.println("----------------ticket:"+ticket);
					break;
				}
			}
		}
		if(ticket != null) {
			LoginTicket loginTicket=ticketDao.getByTicket(ticket);
			if(loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() !=0) {
				return true;
			}
			hostHolder.setUser(userDAO.getUserById(loginTicket.getUserId()));
		}
		return true;
	}

	

}
