package com.moviepedia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviepedia.mapper.ReviewMapper;

import lombok.Setter;

@Service
public class ReviewServiceImpl implements ReviewService {
	@Setter(onMethod_ = @Autowired)
	private ReviewMapper mapper;

	@Override
	public int rtotal() {
		return mapper.rtotal();
	}
	
	@Override
	public int rMemberCnt(String moviecode) {
		return mapper.rMemberCnt(moviecode);
	}
}