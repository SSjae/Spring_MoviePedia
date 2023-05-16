package com.moviepedia.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
        List<String> movieCodes = new ArrayList<String>();
        List<String> boxOffices = new ArrayList<String>();
        
        List<MovieDTO> movie = new ArrayList<MovieDTO>();
        List<ActorDTO> actor = new ArrayList<ActorDTO>();
        List<PhotoDTO> photo = new ArrayList<PhotoDTO>();
        List<VideoDTO> video = new ArrayList<VideoDTO>();
        
		// 먼저 엑셀에서 영화 코드 읽어오기
        movieCodes = movieCodes();
        
        // 사이트에 있는 박스오피스 영화 코드 movieCodes에 중복 있는 거 제외하고 저장 및 따로도 저장
        boxOffices = boxOffices();
        movieCodes.addAll(boxOffices); // movieCodes 뒤에 boxOffices 붙이기

        // 다 모은 movieCodes와 DB에 저장되어 있는 movieCodes를 비교해서 중복하지 않은 거만 뽑아서 insert... 그럼 DB에서 중복 따로 안해도 됨
        
        
        // insert 하기 전에 모든 boxoffice 0으로 변경
        mapper.boxUpdate_0();
        
        // movie insert 후 boxoffice인 애들 순서대로 숫자 부여
        // 나중에 main 페이지에서 boxoffice인 애들을 메인에 출력
        
        
        // 그 코드에 맞는 사이트 가서 데이터 긁어오기
		
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
		
		return boxOffices;
	}
}
