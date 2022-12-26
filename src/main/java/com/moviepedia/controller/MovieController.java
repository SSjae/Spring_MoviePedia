package com.moviepedia.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moviepedia.domain.ActorDTO;
import com.moviepedia.domain.LikeMovieDTO;
import com.moviepedia.domain.MainMovieListDTO;
import com.moviepedia.domain.MovieDTO;
import com.moviepedia.domain.PhotoDTO;
import com.moviepedia.domain.UserDTO;
import com.moviepedia.service.MovieService;
import com.moviepedia.service.ReviewService;

import lombok.Setter;

@Controller
@RequestMapping("/movie/*")
public class MovieController {
	@Setter(onMethod_ = @Autowired)
	private MovieService mservice;

	@Setter(onMethod_ = @Autowired)
	private ReviewService rservice;
	
	// 메인 페이지 넘어가기
	// 영화 갯수, 리뷰 갯수, 개봉날짜(연도만)ㄴ
	// top10(평점), top20(보고싶다), 선호 장르, 재개봉 영화, 한국 영화, 해외 영화
	@GetMapping("/main")
	public String main(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		String[] genres = loginUser.getPrefergenre().split(",");
		
		int mtotal = mservice.mtotal();
		int rtotal = rservice.rtotal();
		
		ArrayList<MainMovieListDTO> movieLists = new ArrayList<MainMovieListDTO>();
		
		ArrayList<MovieDTO> top10 = mservice.top10();
		ArrayList<String> top10Release = relese(top10);
		movieLists.add(new MainMovieListDTO("무비피디아 TOP 10 영화", top10, top10Release, "", false));
		ArrayList<MovieDTO> likeTop20 = mservice.likeTop20();
		ArrayList<String> likeTop20Release = relese(likeTop20);
		movieLists.add(new MainMovieListDTO("제일 보고싶어하는 TOP 20 영화", likeTop20, likeTop20Release, "", false));
		for (String genre : genres) {
			ArrayList<MovieDTO> genreMovie = mservice.genreMovie(genre);
			ArrayList<String> genreMovieRelease = relese(genreMovie);
			movieLists.add(new MainMovieListDTO(genre+" 영화", genreMovie, genreMovieRelease, genre, true));
		}
		ArrayList<MovieDTO> kMovie = mservice.kMovie();
		ArrayList<String> kMovieRelease = relese(kMovie);
		movieLists.add(new MainMovieListDTO("한국 영화", kMovie, kMovieRelease, "대한민국",true));
		ArrayList<MovieDTO> fMovie = mservice.fMovie();
		ArrayList<String> fMovieRelease = relese(fMovie);
		movieLists.add(new MainMovieListDTO("해외 영화", fMovie, fMovieRelease, "해외",true));
		
		model.addAttribute("mtotal", mtotal);
		model.addAttribute("rtotal", rtotal);
		model.addAttribute("movieLists", movieLists);
		
		return "main";
	}
	
	// 영화 상세 정보
	@GetMapping("/movieInfo")
	public String movieInfo(String moviecode, Model model) {
		int mtotal = mservice.mtotal();
		int rtotal = rservice.rtotal();
		
		MovieDTO movie = mservice.movie(moviecode);
		movie.setMoviegenre(movie.getMoviegenre().replace(", ", "/"));
		movie.setMovienation(movie.getMovienation().replace(" , ", "/"));
		ArrayList<ActorDTO> actors = mservice.actors(moviecode);
		ArrayList<PhotoDTO> photos = mservice.photos(moviecode);
		int rMemberCnt = rservice.rMemberCnt(moviecode);

		String genre = movie.getMoviegenre().split("/")[0];
		List<MovieDTO> similar = mservice.similarMovie(moviecode, genre);

		model.addAttribute("mtotal", mtotal);
		model.addAttribute("rtotal", rtotal);
		model.addAttribute("movie", movie);
		model.addAttribute("rMemberCnt", rMemberCnt);
		model.addAttribute("actors", actors);
		model.addAttribute("photos", photos);
		model.addAttribute("similar", similar);
		return "movie/movieInfo";
	}
	
	// 보고싶어요를 했는지 안했는지 확인
	@GetMapping("/likeOk")
	@ResponseBody
	public String likeOk(String moviecode, String useremail) {
		LikeMovieDTO like = mservice.getLikeMovie(moviecode, useremail);
		
		String result = "no";
		if(like != null) {
			result = "ok";
		}
		
		return result;
	}
	
	// 보고싶어요 추가 혹은 취소
	@GetMapping("/like")
	@ResponseBody
	public String like(String status, String moviecode, String useremail) {
		String result = "";
		
		// status가 hate로 오면 보고싶어요 추가
		if(status.equals("hate")) {
			mservice.addLike(moviecode, useremail);
			result = "ok";
		} else {
			mservice.removeLike(moviecode, useremail);
			result = "no";
		}
		
		return result;
	}
	
	// 영화 상세 기본 정보
	@GetMapping("/movieDetail")
	public String movieDetail(String moviecode, Model model) {
		
		MovieDTO movie = mservice.movie(moviecode);

		model.addAttribute("movie", movie);
		movie.setMoviegenre(movie.getMoviegenre().replace(", ", "/"));
		movie.setMovienation(movie.getMovienation().replace(" , ", "/"));
		
		return "movie/movieDetail";
	}
	
	// 비슷한 장르 더보기
	@GetMapping("/similars")
	@ResponseBody
	public List<MovieDTO> similars(String moviecode) {
		MovieDTO movie = mservice.movie(moviecode);
		movie.setMoviegenre(movie.getMoviegenre().replace(", ", "/"));
		String genre = movie.getMoviegenre().split("/")[0];
		List<MovieDTO> similar = mservice.similarMovie(moviecode, genre);
		
		similar = similar.subList(15, similar.size());
		
		return similar;
	}
	
	// 영화 모두보기
	@GetMapping("/movieAll")
	public String movieAll(String title, Model model) {
		int mtotal = mservice.mtotal();
		int rtotal = rservice.rtotal();
		
		ArrayList<MovieDTO> movies = new ArrayList<MovieDTO>();
		
		if(title.equals("한국 영화")) {
			movies = mservice.kMovie();
		} else if (title.equals("해외 영화")) {
			movies = mservice.fMovie();
		} else {
			movies = mservice.genreMovie(title.substring(0, title.length()-3));
		}
		
		model.addAttribute("mtotal", mtotal);
		model.addAttribute("rtotal", rtotal);
		model.addAttribute("title", title);
		model.addAttribute("movies", movies);
		return "movie/movieAll";
	}
	
	// 재개봉으로 인한 개봉날짜가 여러 개인 것중 맨 처음에 개봉한 연도만 뽑아내기 위한 메소드
	public ArrayList<String> relese(ArrayList<MovieDTO> movies) {
		ArrayList<String> releaseArr = new ArrayList<String>();
		
		for (MovieDTO movieDTO : movies) {
			String release = movieDTO.getMovierelease();
			if(release.equals("")) {
				releaseArr.add("");
			}
			else if(release.length() == 7) {
				// 1999 개봉
				releaseArr.add(release.substring(release.length()-7, release.length()-3));
			} else if (release.length() == 11) {
				// 1999 .12 개봉
				releaseArr.add(release.substring(release.length()-11, release.length()-7));
			} else {
				// 1999 .12.12 개봉
				releaseArr.add(release.substring(release.length()-14, release.length()-10));				
			}
		}
		
		return releaseArr;
	}
}