package com.inrich.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.inrich.dao.TicketDAO;
import com.inrich.dao.UserDAO;
import com.inrich.model.LoginTicket;
import com.inrich.model.User;
import com.inrich.util.OutPrintUtil;


@Service
public class UserService {

	@Autowired
	private UserDAO userDao;
	@Autowired
	private TicketDAO ticketDao;

	public User getUserById(int id) {
		return userDao.getUserById(id);
	}

	public String regUser(String username, String password) {
		User user = userDao.getUserByName(username);
		if (user != null) {
			return OutPrintUtil.getJSONString("error", "账号已经存在");
		}
		user = new User();
		user.setName(username);
		user.setPassword(password);

		int result = userDao.addUser(user);

		if (result > 0) {
			return OutPrintUtil.getJSONString("success", "注册成功.");
		}

		return OutPrintUtil.getJSONString("error", "注册失败.");
	}

	public Map<String, Object> login(String username, String password, int remember) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUserByName(username);
		if (user == null) {
			map.put("msgname", "用户名不存在.");
			return map;
		}
		if (!password.equals(user.getPassword())) {
			map.put("msgpwd", "密码不正确.");
			return map;
		}

		String ticket = addTicket(user.getId(),remember);
		map.put("ticket", ticket);
		return map;
	}
	
	public void logout(String ticket) {
		ticketDao.updateStatus(ticket, 1);
		
	}

	/**
	 * 生成cook 的值
	 * 
	 * @param id
	 * @return
	 */
	private String addTicket(int userId,int remember) {
		LoginTicket ticket = new LoginTicket();
		Date date = new Date();
		if(remember>0) {// 记住密码的话 设置有效期为7天
			date.setTime(date.getTime() + 1000 * 3600 * 24 * 7);
		}else {
			date.setTime(date.getTime() + 1000 * 3600 * 24);
		}
		ticket.setExpired(date);
		ticket.setStatus(0);// 0代表可以登录 1代表不能 登录
		ticket.setUserId(userId);
		ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
		ticketDao.addTicket(ticket);
		return ticket.getTicket();
	}
}
