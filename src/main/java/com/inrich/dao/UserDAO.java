package com.inrich.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.inrich.model.User;


@Mapper
public interface UserDAO {
	
	String TABLE_NAME = "user";
    String INSET_FIELDS = " name, password, salt, head_url ";
    String SELECT_FIELDS = " id, name, password, salt, head_url";
    
    @Insert({"insert into ",TABLE_NAME, " (",INSET_FIELDS,") values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);
    
    
    @Select({"select ",SELECT_FIELDS," from ", TABLE_NAME, "where id=#{id}"})
    User getUserById(int id);
    
    @Select({"select ",SELECT_FIELDS," from ", TABLE_NAME, "where name=#{username}"})
    User getUserByName(String username);

}
