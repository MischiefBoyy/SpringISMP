package com.inrich.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.inrich.model.User;


@Mapper
public interface UserDAO {
	
	String TABLE_NAME = "user";
    String INSET_FIELDS = " name, password ";
    String SELECT_FIELDS = " id, name, password ";
    
    @Insert({"insert into ",TABLE_NAME, " (",INSET_FIELDS,") values (#{name},#{password})"})
    int addUser(User user);
    
    
    @Select({"select ",SELECT_FIELDS," from ", TABLE_NAME, "where id=#{id}"})
    User getUserById(int id);
    
    @Select({"select ",SELECT_FIELDS," from ", TABLE_NAME, "where name=#{username}"})
    User getUserByName(String username);

}
