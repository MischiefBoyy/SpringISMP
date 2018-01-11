package com.inrich.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inrich.util.OutPrintUtil;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(value=Exception.class)
	@ResponseBody
	public String exception(Exception e) {
		return OutPrintUtil.getJSONString("error", e.getMessage());
	}
}
