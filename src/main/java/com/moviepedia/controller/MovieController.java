package com.moviepedia.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moviepedia.domain.ActorDTO;
import com.moviepedia.domain.MainMovieListDTO;
import com.moviepedia.domain.MovieDTO;
import com.moviepedia.domain.PhotoDTO;
import com.moviepedia.domain.UserDTO;
import com.moviepedia.domain.VideoDTO;
import com.moviepedia.service.LikeMovieService;
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
	
	@Setter(onMethod_ = @Autowired)
	private LikeMovieService lservice;
	
	// 메인 페이지 넘어가기
	// 영화 갯수, 리뷰 갯수, 개봉날짜(연도만)
	// top10(평점), top20(보고싶다), 선호 장르, 재개봉 영화, 한국 영화, 해외 영화
	@GetMapping("/main")
	public String main(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		UserDTO loginUser = (UserDTO) session.getAttribute("loginUser");
		
		if(loginUser == null) {
			model.addAttribute("msg", "로그인 후 접속 가능합니다.");
			model.addAttribute("url", "user/login");
			
			return "alert";
		}
		
		String[] genres = loginUser.getPrefergenre().split(",");
		
		int mtotal = mservice.mtotal();
		int rtotal = rservice.rtotal();
		
		ArrayList<MainMovieListDTO> movieLists = new ArrayList<MainMovieListDTO>();
		
		
		ArrayList<MovieDTO> boxOffice = mservice.boxOffice();
		movieLists.add(new MainMovieListDTO("박스오피스 순위", boxOffice, "", false));
		ArrayList<MovieDTO> top10 = mservice.top10();
		movieLists.add(new MainMovieListDTO("무비피디아 TOP 10 영화", top10, "", false));
		ArrayList<MovieDTO> likeTop20 = mservice.likeTop20();
		movieLists.add(new MainMovieListDTO("제일 보고싶어하는 TOP 20 영화", likeTop20, "", false));
		for (String genre : genres) {
			ArrayList<MovieDTO> genreMovie = mservice.genreMovie(genre);
			movieLists.add(new MainMovieListDTO(genre+" 영화", genreMovie, genre, true));
		}
		ArrayList<MovieDTO> kMovie = mservice.kMovie();
		movieLists.add(new MainMovieListDTO("한국 영화", kMovie, "한국",true));
		ArrayList<MovieDTO> fMovie = mservice.fMovie();
		movieLists.add(new MainMovieListDTO("해외 영화", fMovie, "해외",true));
		
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
		ArrayList<ActorDTO> actors = mservice.actors(moviecode);
		ArrayList<PhotoDTO> photos = mservice.photos(moviecode);
		ArrayList<VideoDTO> videos = mservice.videos(moviecode);
		
		int rMemberCnt = rservice.rMemberCnt(moviecode);

		String genre = movie.getMoviegenre().split("/")[0];
		List<MovieDTO> similar = mservice.similarMovie(moviecode, genre);
		
		// graph
		ArrayList<String> graphYear = new ArrayList<String>();
		graphYear.addAll(rservice.reviewYear());
		graphYear.addAll(lservice.likeMovieYear());
		Set<String> al = new HashSet<String>(graphYear);
		ArrayList<String> gYear = new ArrayList<>(al);
		Collections.sort(gYear);

		model.addAttribute("mtotal", mtotal);
		model.addAttribute("rtotal", rtotal);
		model.addAttribute("movie", movie);
		model.addAttribute("rMemberCnt", rMemberCnt);
		model.addAttribute("actors", actors);
		model.addAttribute("photos", photos);
		model.addAttribute("videos", videos);
		model.addAttribute("similar", similar);
		model.addAttribute("gYear", gYear);
		return "movie/movieInfo";
	}
	
	// 영화 상세 기본 정보
	@GetMapping("/movieDetail")
	public String movieDetail(String moviecode, Model model) {
		
		MovieDTO movie = mservice.movie(moviecode);

		model.addAttribute("movie", movie);
		
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
	
	// 영화 검색 자동 완성
	@PostMapping("/autocomplete")
	@ResponseBody
	public Map<String, Object> autocomplete(@RequestParam Map<String, Object> paramMap) {
		
		List<Map<String, Object>> resultList = mservice.autocomplete(paramMap);
		paramMap.put("resultList", resultList);
		
		return paramMap;
	}
	
	// 영화 검색
	@GetMapping("/search")
	public String search(String keyword, Model model) {
		ArrayList<MovieDTO> movies = mservice.search(keyword);
		model.addAttribute("keyword", keyword);
		model.addAttribute("movies", movies);
		
		return "movie/searchResult";
	}
	
	// 장르별 영화
	@GetMapping("/genre")
	public String search(Model model) {
		int mtotal = mservice.mtotal();
		int rtotal = rservice.rtotal();
		
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
		
		// 모든 장르 중에서 6개 이상인 것만 넘김
		List<String> genres = new ArrayList<String>();
        for (String str : set) {
        	// 갯수
        	if(Collections.frequency(newList, str) >= 6) {
        		genres.add(str);
        	}
        }
		
		ArrayList<MainMovieListDTO> movieLists = new ArrayList<MainMovieListDTO>();
		
		for (String genre : genres) {
			ArrayList<MovieDTO> genreMovie = mservice.genreMovie(genre);
			movieLists.add(new MainMovieListDTO(genre, genreMovie, genre, true));
		}

		model.addAttribute("mtotal", mtotal);
		model.addAttribute("rtotal", rtotal);
		model.addAttribute("genres", genres);
		model.addAttribute("movieLists", movieLists);
		
		return "movie/genre";
	}
}