package com.inrich.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.inrich.model.LevelTwo;
@Mapper
public interface LevelTwoDAO {
	
	/**
	 * 根据id获得当前项的具体信息
	 * @TODO TODO
	 * @Time 2018年1月9日 上午9:46:14
	 * @author WEQ
	 * @return LevelTwo
	 */
	LevelTwo selectLevelTwoById(int id);
	
	/**
	 * 根据父类查询信息
	 * @TODO TODO
	 * @Time 2018年1月9日 上午9:46:03
	 * @author WEQ
	 * @return List<Map<String,Object>>
	 */
	List<Map<String, Object>> selectLevelTwoByPid(int parentId);
	
	/**
	 * 查询首页的信息
	 * @TODO TODO
	 * @Time 2018年1月9日 上午9:45:42
	 * @author WEQ
	 * @return List<LevelTwo>
	 */
	List<LevelTwo> selectIndex();
	
	int addLevelTwo(LevelTwo LevelTwo);
	
	/**
	 * 通过关键字获得该条信息
	 * @TODO TODO
	 * @Time 2018年1月9日 上午9:45:21
	 * @author WEQ
	 * @return LevelTwo
	 */
	LevelTwo selectByKeyword(String word);
	
	/**
	 * 查询所有的父类，除去基类
	 * @TODO TODO
	 * @Time 2018年1月9日 上午9:44:56
	 * @author WEQ
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> selectNotBase();
	
	/**
	 * 查询 属性菜单信息
	 * @TODO TODO
	 * @Time 2018年1月9日 上午9:44:25
	 * @author WEQ
	 * @return List<Map<String,Object>>
	 */
	List<Map<String,Object>> selectTableJson();
	
	/**
	 * 删除操作，状态为0正常显示，状态为1即为删除状态
	 * @TODO TODO
	 * @Time 2018年1月9日 上午9:40:43
	 * @author WEQ
	 * @return int
	 */
	int updateState(@Param("id") int id,@Param("state")int state);
	
	
	
}
