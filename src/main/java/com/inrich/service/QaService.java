package com.inrich.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inrich.dao.LevelTwoDAO;
import com.inrich.model.LevelTwo;
import com.inrich.util.OutPrintUtil;

@Service
public class QaService {
	
	@Autowired
	private LevelTwoDAO levelTwoDAO;
	
	/**
	 * 查询一级所有列表
	 * @TODO TODO
	 * @Time 2017年12月22日 上午11:59:46
	 * @author WEQ
	 * @return String
	 */
	public String getIndex() {
		List<LevelTwo> list=levelTwoDAO.selectIndex();
		Map<String,Object> map=new HashMap<>(2);
		map.put("info", list);
		map.put("isBase", false);
		return OutPrintUtil.getJSONString("success", map);
		
	}
	
	
	
	
	
	/**
	 * 通过点击获得的信息 id
	 * @TODO TODO
	 * @Time 2017年12月22日 下午1:43:43
	 * @author WEQ
	 * @return String
	 */
	public String getByClick(int id,int isBase) {
		Map<String,Object> map=new HashMap<>(2);
		if(isBase == 0) {
			map.put("info", levelTwoDAO.selectLevelTwoByPid(id));
			map.put("isBase", false);//为了前台判断是否添加可点击样式，false为添加，true为不添加
		}else {
			map.put("info", levelTwoDAO.selectLevelTwoById(id));
			map.put("isBase", true);
		}
		return OutPrintUtil.getJSONString("success", map);
	}
	
	
	
}
