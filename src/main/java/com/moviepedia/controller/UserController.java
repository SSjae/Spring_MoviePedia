package com.moviepedia.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moviepedia.service.UserService;

import lombok.Setter;

@Controller
@RequestMapping("/user/*")
public class UserController {
	@Setter(onMethod_ = @Autowired)
	private UserService service;
	
	@Setter(onMethod_ = @Autowired)
	private JavaMailSender mailSender;
	
	@GetMapping({"/login", "/join"})
	public void replace() {
	}
	
	// 아이디 중복 체크
	@GetMapping("/check")
	@ResponseBody
	public String check(String useremail) {
		int num = service.checkEmail(useremail);
		// 중복 안됨
		String result = "OK";
		
		if(num == 1) {
			// 중복
			result = "NO";
		}
		return result;
	}
	
	// 이메일 인증
	@GetMapping("/auth")
	@ResponseBody
	public String auth(String useremail) {
		// 인증번호 랜덤 생성(111111 ~ 999999)
        Random random = new Random();
        int checkNum = random.nextInt(888888) + 111111;
        
        // 이메일 보내기
        String setFrom = "ssjj04022@naver.com";
        String toMail = useremail;
        String title = "회원가입 인증 이메일 입니다.";
        String content = 
                "홈페이지를 방문해주셔서 감사합니다." +
                "<br><br>" + 
                "인증 번호는 " + checkNum + "입니다." + 
                "<br>" + 
                "해당 인증번호를 인증번호 확인란에 기입하여 주세요.";
        
        try {
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content,true);
            mailSender.send(message);
            
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        String num = Integer.toString(checkNum);
        
		return num;
	}
	
	// 로그인 시도
	// admin 시 admin.jsp로 현재 날짜와 Cookie에 저장된 최신화 날짜도 같이 전달
	// 일반회원시 main.jsp로 session에 로그인 정보 저장
	@PostMapping("/login")
	public String login(HttpServletRequest request, String useremail, String userpw, Model model) {
		String result = "";
		
		if(useremail.equals("admin@moviepedia.com") && userpw.equals("admin1234")) {
			LocalDate now = LocalDate.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        String formatedNow = now.format(formatter);
	        
	        String recentDate = "최신화 필요";
	        
	        Cookie[] list = request.getCookies();
			for(Cookie cookie:list) {
				if(cookie.getName().equals("recentDate")) {
					recentDate = cookie.getValue();
				}
			}
	        
	        model.addAttribute("today", formatedNow);
	        model.addAttribute("recentDate", recentDate);
			result = "user/admin";
		} else {
			result = "main";
		}
		
		return result;
	}
}
