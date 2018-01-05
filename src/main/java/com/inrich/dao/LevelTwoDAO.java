package com.inrich.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.inrich.model.LevelTwo;
@Mapper
public interface LevelTwoDAO {
	LevelTwo selectLevelTwoById(int id);

	List<Map<String, Object>> selectLevelTwoByPid(int parentId);
	
	List<LevelTwo> selectIndex();
	
	int addLevelTwo(LevelTwo LevelTwo);
	
	LevelTwo selectByKeyword(String word);
	
	List<Map<String,Object>> selectNotBase();
	
	
	
}
