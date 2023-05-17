package com.moviepedia.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
import org.jsoup.nodes.Element;
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
        	for(int i = 0; i < 1; i++) {
        		String rankURL = "https://pedia.watcha.com/ko-KR/contents/" + movieCodes.get(i);
        		String rankURL2 = "https://pedia.watcha.com/ko-KR/contents/" + movieCodes.get(i) + "/overview";
        		Document docRank = null;
        		Document docRank2 = null;
        		
        		try {
        			docRank = Jsoup.connect(rankURL).get();
        			docRank2 = Jsoup.connect(rankURL2).get();
        			Elements elemRank = docRank.select("#root .css-99klbh");
        			Iterator<Element> elemRank2 = docRank2.select("#root .css-18gwkcr .css-1y901al-Row ul").iterator();
        			
        			String movietitle = elemRank.select(".css-1p7n6er-Pane .css-171k8ad-Title").text();
        			String movierelease = "";
        			String movienation = "";
        			String moviegenre = "";
        			String movietime = "";
        			String moviegrade = "";
        			String moviedirector = elemRank.select(".css-1s8bs5j .css-13avw3k-PeopleUlRow ul li").attr("title");
        			String moviesummary = "";
        			String movieimg = elemRank.select(".css-10ofaaw .css-569z5v img").attr("src");
        			
        			while(elemRank2.hasNext()) {
        				switch (elemRank2.next().select("dl dt").text()) {
						case "제작 연도":
							movierelease = elemRank2.next().select("dl dd").text();
							break;
						case "국가":
							movienation = elemRank2.next().select("dl dd").text();
							break;
						case "장르":
							moviegenre = elemRank2.next().select("dl dd").text();
							break;
						case "상영시간":
							movietime = elemRank2.next().select("dl dd").text();
							break;
						case "연령 등급":
							moviegrade = elemRank2.next().select("dl dd").text();
							break;
						case "내용":
							moviesummary = elemRank2.next().select("dl dd").text();
							break;
						default:
							break;
						}
        			}
        			
        			System.out.println(movietitle);
        			System.out.println(moviedirector);
        			System.out.println(movieimg);
        			System.out.println(movierelease);
        			System.out.println(movienation);
        			System.out.println(moviegenre);
        			System.out.println(movietime);
        			System.out.println(moviegrade);
        			System.out.println(moviesummary);
        		} catch (Exception e) {}
        	}
        }
        
        
        // movie insert 전 movie list에 있는 boxoffice인 애들 순서대로 숫자 부여
        // 숫자 부여 후 movie insert
        // 나중에 main 페이지에서 boxoffice인 애들을 메인에 출력
		
//		mapper.minsert(movie);
//		mapper.ainsert(actor);
//		mapper.pinsert(photo);
//		mapper.vinsert(video);
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
			Elements elemRank = docRank.select("#root .css-99klbh .css-119xxd7 ul");
			
			Iterator<Element> codeSel = elemRank.select("li a").iterator();
			
			while(codeSel.hasNext()) {
				String code = codeSel.next().attr("href").substring(16);
				
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
