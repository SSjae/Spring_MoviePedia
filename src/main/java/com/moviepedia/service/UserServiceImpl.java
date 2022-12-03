package com.moviepedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
