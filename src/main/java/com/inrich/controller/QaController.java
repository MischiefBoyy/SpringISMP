package com.inrich.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inrich.service.QaService;

@RestController
public class QaController {
	@Autowired
	private QaService qaService;
	
	@RequestMapping("/sy")
	public String index() {
		return qaService.getIndex();
	}
	
	
	@RequestMapping("/click")
	public String click(@RequestParam("id") int id,@RequestParam("isBase") int isBase) {
		return qaService.getByClick(id, isBase);
	}
	
	@RequestMapping("/text")
	public String doText(@RequestParam("text") String text) {
		return qaService.doText(text);
	}
}
