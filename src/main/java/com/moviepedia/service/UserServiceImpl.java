package com.moviepedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviepedia.domain.UserDTO;
import com.moviepedia.mapper.UserMapper;

import lombok.Setter;

@Service
public class UserServiceImpl implements UserService {
	@Setter(onMethod_ = @Autowired)
	private UserMapper mapper;
	
	@Override
	public int checkEmail(String useremail) {
		return mapper.checkEmail(useremail);
	}
	
	@Override
	public boolean join(UserDTO user) {
		return mapper.join(user);
	}
	
	@Override
	public UserDTO login(String useremail, String userpw) {
		return mapper.login(useremail, userpw);
	}
	
	@Override
	public boolean resetPw(String useremail, String userpw) {
		return mapper.resetPw(useremail, userpw);
	}
}
