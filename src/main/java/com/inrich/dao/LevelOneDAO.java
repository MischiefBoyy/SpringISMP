package com.inrich.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.inrich.model.LevelOne;

@Mapper
public interface LevelOneDAO {
	 String TABLE_NAME = "level_one";
	 String INSET_FIELDS = " name, password, salt, head_url ";
	 String SELECT_FIELDS = " id, level, question, key_word, click_num,is_base";
	 
	@Select({"select ", SELECT_FIELDS, " from ",TABLE_NAME })
	List<LevelOne> selectAllLevelOne();
	
	@Select({"select ", SELECT_FIELDS, " from ",TABLE_NAME, "where id=#{id}"})
	LevelOne selectLevelOneById(int id);
}
