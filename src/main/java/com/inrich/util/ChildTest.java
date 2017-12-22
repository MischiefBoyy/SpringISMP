package com.inrich.util;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.inrich.service.QaService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ChildTest {
	@Autowired
	private QaService qaService;
	@Test
	public void testOutCallAction() {
		System.out.println(qaService.getIndex());
	}
	
	@Test
	public void getByClick() {
		System.out.println(qaService.getByClick(11, 1));
	}
	
	

}
