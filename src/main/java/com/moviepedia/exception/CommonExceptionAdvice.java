package com.moviepedia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;

@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {
	@ExceptionHandler(Exception.class)
	public String except(Exception e, Model model) {
		log.error("=====예외 처리=====");
		log.error(e.getStackTrace());
		log.error(e.getMessage());
		model.addAttribute("exception", e);
		return "exception/error_page";
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String except404(Exception e, Model model) {
		log.error("=====예외 처리404=====");
		log.error(e.getStackTrace());
		log.error(e.getMessage());
		model.addAttribute("exception", e);
		return "exception/error_404";
	}
}