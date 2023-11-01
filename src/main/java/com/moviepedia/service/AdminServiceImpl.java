package com.moviepedia.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.moviepedia.domain.ActorDTO;
import com.moviepedia.domain.MovieDTO;
import com.moviepedia.domain.PhotoDTO;
import com.moviepedia.domain.VideoDTO;
import com.moviepedia.mapper.AdminMapper;

import lombok.Setter;

@Service
public class AdminServiceImpl implements AdminService{
	@Setter(onMethod_ = @Autowired)
	private AdminMapper mapper;
	
	@Override
	public void recent() {
        List<MovieDTO> movie = new ArrayList<MovieDTO>();
        List<ActorDTO> actor = new ArrayList<ActorDTO>();
        List<PhotoDTO> photo = new ArrayList<PhotoDTO>();
        List<VideoDTO> video = new ArrayList<VideoDTO>();

        List<String> movieCodes = new ArrayList<String>();
        List<String> boxOffices = new ArrayList<String>();
        
		// 먼저 엑셀에서 영화 코드 읽어오기
        movieCodes = movieCodes();
        
        // 사이트에 있는 박스오피스 영화 코드 movieCodes에 중복 있는 거 제외하고 저장 및 따로도 저장
        boxOffices = boxOffices();
        
        movieCodes.addAll(boxOffices); // movieCodes 뒤에 boxOffices 붙이기
        
        // 중복을 방지하기 위해 List -> Set -> List로 변경
        Set<String> set = new HashSet<String>(movieCodes);
        movieCodes = new ArrayList<String>(set);

        // 이미 DB에 저장되어 있는 movieCodes 제외한 movieCodes
        movieCodes = codesDB(movieCodes);
        
        // insert 하기 전에 모든 boxoffice 0으로 변경 그래야 새로운 박스 오피스 가능
        mapper.boxUpdate_0();
		
        // 모든 것이 DB에 저장되어 있으면 할 필요 없으니 if문으로 조건 검사 후
        // 영화 관련 정보 긁어오기
        if(movieCodes.size() != 0) {
        	for(int i = 0; i < movieCodes.size(); i++) {
        		// 영화 크롤링
        		String movieURL = "https://pedia.watcha.com/ko-KR/contents/" + movieCodes.get(i);
        		String movieURL2 = "https://pedia.watcha.com/ko-KR/contents/" + movieCodes.get(i) + "/overview";
        		Document docMovie = null;
        		Document docMovie2 = null;
        		Document docActor = null;
        		Document docPhoto = null;
        		Document docVideo = null;
        		
        		try {
        			docMovie = Jsoup.connect(movieURL).userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
        			           							.header("scheme", "https")
        			           							.header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
        			           							.header("accept-encoding", "gzip, deflate, br")
        			           							.header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6")
        			           							.header("cache-control", "no-cache")
        			           							.header("pragma", "no-cache")
        			           							.header("upgrade-insecure-requests", "1")
        			           							.get();
        			docMovie2 = Jsoup.connect(movieURL2).userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
						        			           .header("scheme", "https")
						        			           .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
						        			           .header("accept-encoding", "gzip, deflate, br")
						        			           .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6")
						        			           .header("cache-control", "no-cache")
						        			           .header("pragma", "no-cache")
						        			           .header("upgrade-insecure-requests", "1")
						        			           .get();
        		
        			Elements movieSel = docMovie.select("#root");
        			Elements movieSel2 = docMovie2.select("#root .css-8ijtco-Main-setMainPaddingForXs-setMainPaddingForOverSm .css-1gkas1x-Grid ul dl");
       			
        			String movietitle = movieSel.select(".css-ysnb50-StyledWallpaperContainer .css-1tlhtfm-StyledTitle").text();
        			String movierelease = movieSel2.get(1).select("dd").text();
        			String movienation = movieSel2.get(2).select("dd").text();
        			movienation = movienation.replace(",", "/");
        			String moviegenre = movieSel2.get(3).select("dd").text();
        			String movietime = movieSel2.get(4).select("dd").text();
        			String moviegrade = movieSel2.get(5).select("dd").text();
        			String moviesummary = movieSel2.get(6).select("dd").text();
        			String movieimg = movieSel.select(".css-kk84tq-StyledContentInfoSection .css-1hh6j5c-StyledLazyLoadingImage-posterStyle-LazyLoadingImg img").attr("src");
        			
        			MovieDTO m = new MovieDTO(movieCodes.get(i), movietitle, movierelease, movienation, moviegenre, movietime, moviegrade, moviesummary, movieimg);
        			
        			movie.add(m);
        			
        			movietitle = movietitle.replace(" ", "");
        			System.out.println(movietitle + " - " + (i+1));
        			
        			// 액터 크롤링
        			String actorURL = "https://search.naver.com/search.naver?query=영화%20"+movietitle+"%20출연진";
        			docActor = Jsoup.connect(actorURL).userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
							     			           .header("scheme", "https")
							     			           .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
							     			           .header("accept-encoding", "gzip, deflate, br")
							     			           .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6")
							     			           .header("cache-control", "no-cache")
							     			           .header("pragma", "no-cache")
							     			           .header("upgrade-insecure-requests", "1")
							     			           .get();
        			Elements actorSel = docActor.select("#wrap #container #main_pack .cm_content_wrap .sec_scroll_cast_member .cast_box");
        			
        			// 감독 먼저 따로 액터에 넣는다.
        			String moviedirector = actorSel.get(0).select(".cast_list .item .title_box .name ._text").text();
        			String moviedirectorpart = actorSel.get(0).select(".cast_list .item .title_box .sub_text ._text").text();
        			String moviedirectorimg = actorSel.get(0).select(".cast_list .item .thumb img").attr("src");
        			
        			ActorDTO a = new ActorDTO(movieCodes.get(i), moviedirector, moviedirectorpart, moviedirectorimg);
        			
    			    actor.add(a);

        			for(int l = 1; l < actorSel.size(); l++) {
        				Elements s = actorSel.get(l).select(".cast_list li");
        				for(int n = 0; n < s.size(); n++) {
            			    String actorname = s.get(n).select(".item .title_box .name ._text").text();
            			    String actorpart = s.get(n).select(".item .title_box .sub_text ._text").text();
            			    String actorimg = s.get(n).select(".item .thumb img").attr("src");
            			    
            			    a = new ActorDTO(movieCodes.get(i), actorname, actorpart, actorimg);
            			    
            			    actor.add(a);
        				}
        			}
        			
        			System.out.println(actor.size());
        			
        			// 포토 크롤링
        			String photoURL = "https://search.naver.com/search.naver?query=영화%20"+movietitle+"%20포토";
        			docPhoto = Jsoup.connect(photoURL).userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
							     			           .header("scheme", "https")
							     			           .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
							     			           .header("accept-encoding", "gzip, deflate, br")
							     			           .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6")
							     			           .header("cache-control", "no-cache")
							     			           .header("pragma", "no-cache")
							     			           .header("upgrade-insecure-requests", "1")
							     			           .get();
        			Elements photoSel = docPhoto.select("#wrap #container #main_pack .cm_content_wrap .cm_pure_box .movie_photo_list ul li");
        			
        			for(int x = 0; x < photoSel.size(); x++) {
        				String photoimg = photoSel.get(x).select("a > img").attr("data-img-src");
        				
        				PhotoDTO p = new PhotoDTO(movieCodes.get(i), photoimg);
        				
        				photo.add(p);
        				
        			}
        			
        			System.out.println(photo.size());
        			
        			// 비디오 크롤링
        			String videoURL = "https://search.naver.com/search.naver?query=영화%20"+movietitle+"%20예고편";
        			docVideo = Jsoup.connect(videoURL).userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
							     			           .header("scheme", "https")
							     			           .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
							     			           .header("accept-encoding", "gzip, deflate, br")
							     			           .header("accept-language", "ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,es;q=0.6")
							     			           .header("cache-control", "no-cache")
							     			           .header("pragma", "no-cache")
							     			           .header("upgrade-insecure-requests", "1")
							     			           .get();
        			Elements videoSel = docVideo.select("#wrap #container #main_pack .cm_content_wrap ._sec_movie_clip_trailer .area_video_list_box ul li");
        			
        			for(int y = 0; y < videoSel.size(); y++) {
        				String videoimg = videoSel.get(y).select(".video_thmb a img").attr("src");
        				String videoaddr = videoSel.get(y).select(".video_thmb a").attr("href");
        				String videotitle = videoSel.get(y).select(".area_info a").text();
        				
        				VideoDTO v = new VideoDTO(movieCodes.get(i), videoimg, videoaddr, videotitle);
        				
        				video.add(v);
        			}
        			
        			System.out.println(video.size());
        			
        		} catch (Exception e) {
                    System.out.println("예외메시지 : " + e.getMessage());
        		}
        	}
        }
		
		mapper.minsert(movie);
		// movie insert 후  boxoffice인 애들 순서대로 숫자 업데이트
		for(int k = 0; k < boxOffices.size(); k++) {
			mapper.boxUpdate_1(boxOffices.get(k), k+1);
		}
		mapper.ainsert(actor);
		mapper.pinsert(photo);
		mapper.vinsert(video);
	}

	@Override
	public int mtotal() {
		return mapper.mtotal();
	}
	
	@Override
	public int atotal() {
		return mapper.atotal();
	}
	
	@Override
	public int ptotal() {
		return mapper.ptotal();
	}
	
	@Override
	public int vtotal() {
		return mapper.vtotal();
	}
	
	// 엑셀 파일에 있는 영화 코드 읽기
	public List<String> movieCodes() {
		List<String> movieCodes = new ArrayList<String>();
		
		ClassPathResource resource = new ClassPathResource("movieCodes.xlsx");
		
		// 엑셀 파일에 있는 영화 이름 읽기
		try {
	    	// 경로에 있는 파일을 읽기
	        FileInputStream file = new FileInputStream(resource.getFile());
	        XSSFWorkbook workbook = new XSSFWorkbook(file);
	
	        int rowNo = 0;
	        int cellIndex = 0;
	        
	        XSSFSheet sheet = workbook.getSheetAt(0); // 0 번째 시트를 가져온다 
	        										  // 만약 시트가 여러개 인 경우 for 문을 이용하여 각각의 시트를 가져온다
	        int rows = sheet.getPhysicalNumberOfRows(); // 사용자가 입력한 엑셀 Row수를 가져온다
	        for(rowNo = 0; rowNo < rows; rowNo++){
	            XSSFRow row = sheet.getRow(rowNo);
	            if(row != null){
	                int cells = row.getPhysicalNumberOfCells(); // 해당 Row에 사용자가 입력한 셀의 수를 가져온다
	                for(cellIndex = 0; cellIndex <= cells; cellIndex++){  
	                    XSSFCell cell = row.getCell(cellIndex); // 셀의 값을 가져온다	        
	                    String value = "";	                    
	                    if(cell == null){ // 빈 셀 체크 
	                        continue;
	                    }else{
	                        // 타입 별로 내용을 읽는다
	                        switch (cell.getCellType()){
	                        case Cell.CELL_TYPE_STRING:
	                            value = cell.getStringCellValue() + "";
	                            break;
	                        case Cell.CELL_TYPE_ERROR:
	                            value = cell.getErrorCellValue() + "";
	                            break;
	                        }
	                    }
	                    movieCodes.add(value);
	                }
	            }
	        }
	    }catch(Exception e) {
			e.printStackTrace();
	    }
		
		return movieCodes;
	}
	
	// 사이트에서 boxOffice movieCode 코드 긁어오기
	public List<String> boxOffices() {
		List<String> boxOffices = new ArrayList<String>();
	    
	    String rankURL = "https://pedia.watcha.com/ko-KR";
		Document docRank = null;
		
		try {
			docRank = Jsoup.connect(rankURL).get();
			Elements elemRank = docRank.select("#root .css-126e3ta-NavContainer .css-lifknt-Self .w_exposed_cell");
			Elements ele = elemRank.get(0).select(".css-7v9338-StyledHorizontalScrollOuterContainer-createMediaQuery-createMediaQuery-createMediaQuery-createMediaQuery-createMediaQuery-createMediaQuery ul li");

			for(int z = 0; z < ele.size(); z++) {
				String code = ele.get(z).select("a").attr("href").substring(16);
				
				boxOffices.add(code);
			}
			
		} catch (Exception e) {}
		
		return boxOffices;
	}
	
	// 다 모은 movieCodes와 DB에 저장되어 있는 movieCodes를 비교해서 중복하지 않은 거만 뽑아 반환
	public List<String> codesDB(List<String> movieCodes) {
        List<String> movieCodesDB = new ArrayList<String>();
        movieCodesDB = mapper.movieCodesDB();
        
        // codes와 codesDB 두개를 합쳐서 중복된 값을 list로 따로 뽑아옴
        List<String> list = new ArrayList<String>(movieCodes);
        list.addAll(movieCodesDB);
        
        // 중복값 제거
        List<String> distinctLi = list.stream().distinct().collect(Collectors.toList());

        // 중복값 제거 된 값을 list에서 지움 그러면 중복값만 남음
		for (String distinctElement : distinctLi) {
			list.remove(distinctElement);
		}
		
		// movieCodes에서 DB에 저장되어 있는 값 제거
		for (String listValue : list) {
			movieCodes.remove(listValue);
		}
		
		return movieCodes;
	}
}
