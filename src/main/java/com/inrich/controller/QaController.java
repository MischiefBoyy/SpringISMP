package com.inrich.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.inrich.dao.LevelTwoDAO;
import com.inrich.model.LevelTwo;
import com.inrich.service.QaService;
import com.inrich.util.OutPrintUtil;

@RestController
public class QaController {
	@Autowired
	 QaService qaService;
	@Autowired
	LevelTwoDAO levelTwoDAO;
	
	
	@RequestMapping("/sy")
	public String index() {
		return qaService.getIndex();
		
	}
	
	
	@RequestMapping("/click")
	public String click(@RequestParam("id") int id,@RequestParam(value="isBase",defaultValue="999") int isBase ) {
		if(isBase == 999) {
			return OutPrintUtil.getJSONString("success", levelTwoDAO.selectLevelTwoById(id)); 
		}
		return qaService.getByClick(id, isBase);
	}
	
	@RequestMapping("/text")
	public String doText(@RequestParam("text") String text) {
		return qaService.doText(text);
	}
	
	@RequestMapping(path= {"/addIsmp"},method= {RequestMethod.POST})
	public String addIsmp(HttpServletRequest request,@RequestParam(value="id",defaultValue="-1")  int id ) {
		System.out.println("-----id:"+id);
		String result=null;
		List<MultipartFile> files=null;
		String []keyWords=null;
		files = ((MultipartHttpServletRequest) request).getFiles("file");
		System.out.println("---------"+files.size());
		LevelTwo parentModel=levelTwoDAO.selectLevelTwoById(id);
		String []questions=request.getParameterValues("question");
		if(!files.isEmpty()) {
			System.out.println("------进入添加图片");
			if(parentModel == null || parentModel.getIsQa()!=1) {
				return OutPrintUtil.getJSONString("error", "父类型不是问答！"); 
			}
			result=qaService.addTypeImage(files,questions,parentModel);
			return result;
		}
		
		keyWords=request.getParameterValues("keyWord");
		//System.out.println("keyWords:"+Arrays.toString(keyWords));
		if(request.getParameterValues("answer") != null) {
			System.out.println("------进入添加回答");
			String []answers=request.getParameterValues("answer");
			if(parentModel == null) {
				return OutPrintUtil.getJSONString("error", "基类问答，父类不能为空。");
			}
			result=qaService.addTypeAnswer(questions, answers,keyWords,parentModel);
			return result;
		}
		
		System.out.println("------进入添加问题");
		String []isQas=request.getParameterValues("isQa");
		result=qaService.addTypeQuestion(questions, isQas, keyWords,parentModel);
		
		return result;
	}
	
	@RequestMapping("/getParent")
	public String getParent() {
		List<Map<String,Object>> list=levelTwoDAO.selectNotBase();
		return OutPrintUtil.getJSONString("success", list); 
	}
	
	@RequestMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		int result=qaService.delete(id);
		return result>0?OutPrintUtil.getJSONString("success", "删除成功！"):OutPrintUtil.getJSONString("error", "删除失败！"); 
	}
	
	
	@RequestMapping("/getTableTree")
	public String getTableTree() {
		return qaService.getTable();
	}
	
	
	@RequestMapping(path= {"/editIsmp"},method= {RequestMethod.POST})
	public String editIsmp(HttpServletRequest request,MultipartFile file ) {
		String data=request.getParameter("data");
		return qaService.editISMP(JSON.parseObject(data,Map.class), file);
	}
	
}
