package com.moviepedia.service;

import com.moviepedia.domain.UserDTO;

public interface UserService {

	int checkEmail(String useremail);

	boolean join(UserDTO user);

	UserDTO login(String useremail, String userpw);

	boolean resetPw(String useremail, String userpw);

	UserDTO getUser(String useremail);
}
