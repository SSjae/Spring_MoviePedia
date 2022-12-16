package com.moviepedia.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moviepedia.domain.ActorDTO;
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
		ArrayList<MovieDTO> reMovie = mservice.reMovie();
		ArrayList<String> reMovieRelease = relese(reMovie);
		movieLists.add(new MainMovieListDTO("다시 개봉한 영화", reMovie, reMovieRelease, "재개봉",true));
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
		String[] movieRelease = movie.getMovierelease().split(", ");
		movie.setMoviegenre(movie.getMoviegenre().replace(", ", "/"));
		movie.setMovienation(movie.getMovienation().replace(" , ", "/"));
		ArrayList<ActorDTO> actors = mservice.actors(moviecode);
		ArrayList<PhotoDTO> photos = mservice.photos(moviecode);

		model.addAttribute("mtotal", mtotal);
		model.addAttribute("rtotal", rtotal);
		model.addAttribute("movie", movie);
		model.addAttribute("movieRelease", movieRelease[movieRelease.length-1]);
		model.addAttribute("actors", actors);
		model.addAttribute("photos", photos);
		return "movie/movieInfo";
	}
	
	// 재개봉으로 인한 개봉날짜가 여러 개인 것중 맨 처음에 개봉한 연도만 뽑아내기 위한 메소드
	public ArrayList<String> relese(ArrayList<MovieDTO> movies) {
		ArrayList<String> releaseArr = new ArrayList<String>();
		
		for (MovieDTO movieDTO : movies) {
			String release = movieDTO.getMovierelease();
			if(release.equals("")) {
				releaseArr.add("");
			}
			else if(release.length() == 7 || release.substring(release.length()-9, release.length()-8).equals(",")) {
				// 1999 개봉
				releaseArr.add(release.substring(release.length()-7, release.length()-3));
			} else if (release.length() == 11 || release.substring(release.length()-13, release.length()-12).equals(",")) {
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