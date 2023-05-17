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
        
        System.out.println(movieCodes.size());
        
        // 사이트에 있는 박스오피스 영화 코드 movieCodes에 중복 있는 거 제외하고 저장 및 따로도 저장
        boxOffices = boxOffices();
        movieCodes.addAll(boxOffices); // movieCodes 뒤에 boxOffices 붙이기
        
        // 중복을 방지하기 위해 List -> Set -> List로 변경
        Set<String> set = new HashSet<String>(movieCodes);
        movieCodes = new ArrayList<String>(set);
        
        // boxOffice 포함한 것들도 엑셀에 저장
        addExcel(movieCodes);
        
        // 이미 DB에 저장되어 있는 movieCodes 제외한 movieCodes
        movieCodes = codesDB(movieCodes);
        
        // insert 하기 전에 모든 boxoffice 0으로 변경 그래야 새로운 박스 오피스 가능
        mapper.boxUpdate_0();
        
        // 영화 코드 긁어오기
        
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
	
	// boxOffice 포함한 것들도 엑셀에 저장
	public void addExcel(List<String> movieCodes) {
		ClassPathResource resource = new ClassPathResource("movieCodes.xlsx");

		try {
			// FileInputStream 으로 파일 읽기
			FileInputStream inputStream = new FileInputStream(resource.getFile());
			
			// XSSFWorkbook 객체 생성하기
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			// XSSFSheet 객체 생성 - 첫번째 시트를 가져온다 
			XSSFSheet sheet = workbook.getSheetAt(0);

			// 1번째 Row 부터 데이터 삽입
			for(int i = 0; i < movieCodes.size(); i++) {
				XSSFRow row = sheet.createRow(i);		// row 생성
				row.createCell(0).setCellValue(movieCodes.get(i));   // 칼럼을 생성한다
			}

			// FileOutputStream 으로 파일 저장하기
			FileOutputStream out = new FileOutputStream(resource.getFile());
			workbook.write(out);
			out.close();
		}catch (Exception e) {}	
	}
}
