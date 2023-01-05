package com.moviepedia.mapper;

import org.apache.ibatis.annotations.Param;

import com.moviepedia.domain.UserDTO;

public interface UserMapper {

	int checkEmail(String useremail);

	boolean join(UserDTO user);

	UserDTO login(@Param("useremail")String useremail, @Param("userpw")String userpw);

	boolean resetPw(@Param("useremail")String useremail, @Param("userpw")String userpw);

	UserDTO getUser(String useremail);
}