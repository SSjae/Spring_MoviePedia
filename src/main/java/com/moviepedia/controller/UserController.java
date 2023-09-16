package com.moviepedia.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moviepedia.domain.LikeMovieDTO;
import com.moviepedia.domain.MovieDTO;
import com.moviepedia.domain.ReviewDTO;
import com.moviepedia.domain.UserDTO;
import com.moviepedia.service.AdminService;
import com.moviepedia.service.LikeMovieService;
import com.moviepedia.service.MovieService;
import com.moviepedia.service.ReviewService;
import com.moviepedia.service.UserService;

import lombok.Setter;

@Controller
@RequestMapping("/user/*")
public class UserController {
	@Setter(onMethod_ = @Autowired)
	private UserService uservice;
	
	@Setter(onMethod_ = @Autowired)
	private MovieService mservice;
	
	@Setter(onMethod_ = @Autowired)
	private ReviewService rservice;
	
	@Setter(onMethod_ = @Autowired)
	private LikeMovieService lservice;
	
	@Setter(onMethod_ = @Autowired)
	private AdminService aservice;
	
	@Setter(onMethod_ = @Autowired)
	private JavaMailSender mailSender;
	
	@GetMapping({"/login","/findPw"})
	public void replace() {
	}
	
	// 회원가입 페이지로 이동(모든 장르 데이터 넘겨야됨)
	@GetMapping("/join")
	public String replaceJoin(Model model) {
		if(aservice.mtotal() == 0) {
			model.addAttribute("msg", "영화 최신화가 필요합니다.");
			model.addAttribute("url", "/");
			
			return "alert";
		}
		
		// DB에서 모든 영화 장르만 가져옴(한 영화에 장르가 여러개)
		ArrayList<String> list = mservice.allGenre();
		
		List<String> newList = new ArrayList<String>();
		for(int i = 0; i < list.size(); i++) {
			String[] result = list.get(i).split("/");
			for(int j = 0; j < result.length; j++){
				// 장르가 여러개 있기 때문에 ", " 기준으로 짤라서 다시 리스트에 넣음
				newList.add(result[j]);
			}
		}
		
		// 중복 제거를 통해 모든 장르 뽑아냄
		Set<String> set = new HashSet<String>(newList);
		
		// 모든 장르 중에서 10개 이상인 것만 넘김
		List<String> genres = new ArrayList<String>();
        for (String str : set) {
        	// 갯수
        	if(Collections.frequency(newList, str) >= 10) {
        		genres.add(str);
        	}
        }
		
		model.addAttribute("genres", genres);
		return "user/join";
	}
	
	// join-아이디 중복 체크
	@GetMapping("/check")
	@ResponseBody
	public String check(String useremail) {
		int num = uservice.checkEmail(useremail);
		// 중복 안됨
		String result = "OK";
		
		if(num == 1) {
			// 중복
			result = "NO";
		}
		return result;
	}
	
	// 회원가입
	@PostMapping("/join")
	public String join(UserDTO user, Model model) {
		if(uservice.join(user)) {
			model.addAttribute("msg", "회원가입이 완료되었습니다.");
			model.addAttribute("url", "/");			
		} else {
			model.addAttribute("msg", "회원가입에 실패하였습니다. 다시 시도해주세요.");
			model.addAttribute("url", "user/join");		
		}
		
		return "alert";
	}
	
	// findPw-가입된 아이디인지 아닌지
	@GetMapping("/check_findPw")
	@ResponseBody
	public String check_findPw(String useremail) {
		int num = uservice.checkEmail(useremail);
		// 아이디가 있음
		String result = "OK";
		
		if(num == 0) {
			// 아이디 없음
			result = "NO";
		}
		return result;
	}
	
	@PostMapping("/findPw")
	@ResponseBody
	public String findPw(String useremail, String userpw, Model model) {
		// 설정 성공
		String result = "success";
		
		if(!uservice.resetPw(useremail, userpw)) {
			// 실패
			result="fail";
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
        
		return Integer.toString(checkNum);
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
				if(cookie.getName().equals("recentDate") && mservice.mtotal() != 0) {
					recentDate = cookie.getValue();
				}
			}
	        
	        model.addAttribute("today", formatedNow);
	        model.addAttribute("recentDate", recentDate);
			result = "user/admin";
		} else {
			UserDTO loginUser = uservice.login(useremail, userpw);
			if(loginUser != null) {
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", loginUser);
				result = "redirect:/movie/main";
			} else {
				model.addAttribute("msg", "아이디 또는 비밀번호가 틀렸습니다. 다시 시도해주세요.");
				model.addAttribute("url", "user/login");	
				result = "alert";
			}
		}
		return result;
	}
	
	// myPage로 이동
	@GetMapping("/myPage")
	public String myPage(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("loginUser");
		
		int mtotal = mservice.mtotal();
		int rtotal = rservice.rtotal();
		
		// backProfile 랜덤을 위한 숫자 보냄
		Random random = new Random();
		int ran = random.nextInt(6)+1;
		
		// 내가 평가한 reviewDTO, movieDTO
		ArrayList<ReviewDTO> myReview = rservice.myReview(user.getUseremail());
		ArrayList<MovieDTO> myReviewMovie = new ArrayList<MovieDTO>();
		for(ReviewDTO review : myReview) {
			myReviewMovie.add(mservice.movie(review.getMoviecode()));
		}
		
		ArrayList<LikeMovieDTO> myLike = lservice.myLike(user.getUseremail());
		ArrayList<MovieDTO> myLikeMovie = new ArrayList<MovieDTO>();
		for(LikeMovieDTO like : myLike) {
			myLikeMovie.add(mservice.movie(like.getMoviecode()));
		}

		model.addAttribute("mtotal", mtotal);
		model.addAttribute("rtotal", rtotal);
		model.addAttribute("ran", ran);
		model.addAttribute("myReview", myReview);
		model.addAttribute("myReviewMovie", myReviewMovie);
		model.addAttribute("myLike", myLike);
		model.addAttribute("myLikeMovie", myLikeMovie);
		
		return "user/myPage";
	}
	
	// 평가 및 보고싶어요 이동
	@GetMapping("/reviewLike")
	public String ratingLike(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("loginUser");
		
		int mtotal = mservice.mtotal();
		int rtotal = rservice.rtotal();
		
		// 내가 평가한 reviewDTO, movieDTO
		ArrayList<ReviewDTO> myReview = rservice.myReview(user.getUseremail());
		ArrayList<MovieDTO> myReviewMovie = new ArrayList<MovieDTO>();
		for(ReviewDTO review : myReview) {
			myReviewMovie.add(mservice.movie(review.getMoviecode()));
		}
		
		ArrayList<LikeMovieDTO> myLike = lservice.myLike(user.getUseremail());
		ArrayList<MovieDTO> myLikeMovie = new ArrayList<MovieDTO>();
		for(LikeMovieDTO like : myLike) {
			myLikeMovie.add(mservice.movie(like.getMoviecode()));
		}

		model.addAttribute("mtotal", mtotal);
		model.addAttribute("rtotal", rtotal);
		model.addAttribute("myReview", myReview);
		model.addAttribute("myReviewMovie", myReviewMovie);
		model.addAttribute("myLike", myLike);
		model.addAttribute("myLikeMovie", myLikeMovie);
		
		return "user/reviewLike";
	}
	
	// logout
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("loginUser");
		
		return "user/login";
	}
	
	// 회원탈퇴
	@PostMapping("/delete")
	public String delete(HttpServletRequest request, String useremail) {
		HttpSession session = request.getSession();
		session.removeAttribute("loginUser");
		
		// 이 유저의 모든 리뷰 DTO 받아오기
		ArrayList<ReviewDTO> myReview = rservice.myReview(useremail);
		// 이 유저의 모든 보고싶어요 DTO 받아오기
		ArrayList<LikeMovieDTO> myLike = lservice.myLike(useremail);
		
		// 유저 삭제(제약조건으로 인해 그 유저의 리뷰, 보고싶어요도 같이 삭제)
		uservice.delete(useremail);
		
		// 삭제 후 위에서 받은 리뷰 DTO의 각 영화의 전체 평점 평균을 구해서 그 영화 DTO에 적용
		for(ReviewDTO review : myReview) {
			double avg = rservice.reviewAvg(review.getMoviecode()) == null ? 0 : Double.parseDouble(rservice.reviewAvg(review.getMoviecode()));
			mservice.updateStar(review.getMoviecode(), avg);
		}
		// 삭제 후 위에서 받은 보고싶어요 DTO의 각 영화의 갯수를 구해서 그 영화 DTO에 적용
		for(LikeMovieDTO like : myLike) {
			mservice.likedown(like.getMoviecode());
		}
		
		return "redirect:/user/login";
	}
}