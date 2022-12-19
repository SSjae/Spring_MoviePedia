package com.moviepedia.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviepedia.domain.ActorDTO;
import com.moviepedia.domain.MovieDTO;
import com.moviepedia.domain.PhotoDTO;
import com.moviepedia.mapper.AdminMapper;

import lombok.Setter;

@Service
public class AdminServiceImpl implements AdminService{
	@Setter(onMethod_ = @Autowired)
	private AdminMapper mapper;
	
	@Override
	public void recent() {
		LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);
        
        List<MovieDTO> movie = new ArrayList<MovieDTO>();
        List<ActorDTO> actor = new ArrayList<ActorDTO>();
        List<PhotoDTO> photo = new ArrayList<PhotoDTO>();
        
		for(int i = 1; i < 11; i++) {
			String rankURL = "https://movie.naver.com/movie/sdb/rank/rmovie.naver?sel=pnt&date="+formatedNow+"&tg=0&page="+i;
			Document docRank = null;
			
			try {
				docRank = Jsoup.connect(rankURL).get();
				Elements elemRank = docRank.select(".type_1 #old_content .list_ranking tbody");
				
				Iterator<Element> codeSel = elemRank.select("tr > td.title > .tit5 > a").iterator();
				Iterator<Element> titleSel = elemRank.select("tr > td.title > .tit5").iterator();
				
				while(titleSel.hasNext()) {
					// 영화 크롤링
					String moviecode = codeSel.next().attr("href").replaceAll("[^0-9]", "");
					String movieKtitle = titleSel.next().text();
					
					String detailURL = "https://movie.naver.com/movie/bi/mi/basic.naver?code="+moviecode;
					Document docDetail = Jsoup.connect(detailURL).get();
					Elements elemDetail = docDetail.select("#content .article");
					
					String movieEtitle = elemDetail.select(".mv_info_area .mv_info .h_movie2").text();
					String moviegenre = elemDetail.select(".mv_info_area .mv_info .info_spec dd:nth-of-type(1) span:nth-of-type(1)").text();
					String movienation = elemDetail.select(".mv_info_area .mv_info .info_spec dd:nth-of-type(1) span:nth-of-type(2)").text();
					String movietime = elemDetail.select(".mv_info_area .mv_info .info_spec dd:nth-of-type(1) span:nth-of-type(3)").text();
					String[] release = elemDetail.select(".mv_info_area .mv_info .info_spec dd:nth-of-type(1) span:nth-of-type(4)").text().split(", ");
					String movierelease = "";
					if(release.length > 0) {
						movierelease = release[release.length-1];
					}
					String moviedirector = elemDetail.select(".mv_info_area .mv_info .info_spec dd:nth-of-type(2) p").text();
					String moviegrade = elemDetail.select(".mv_info_area .mv_info .info_spec dd:nth-of-type(4) p a:nth-of-type(1)").text();
					String movieimg = elemDetail.select(".mv_info_area .poster > a > img").attr("src");
					String movieHsummary = elemDetail.select(".video .story_area .h_tx_story").text();
					String movieCsummary = elemDetail.select(".video .story_area .con_tx").text();
					
					MovieDTO m = new MovieDTO(moviecode, movieKtitle, movieEtitle, moviegenre, movienation, movietime, movierelease, moviedirector, moviegrade, movieimg, movieHsummary, movieCsummary);
					
					movie.add(m);
					
					// 액터 크롤링
					String actorURL = "https://movie.naver.com/movie/bi/mi/detail.naver?code="+moviecode;
					Document docActor = null;
					
					try {
						docActor = Jsoup.connect(actorURL).get();
						Elements elemActor = docActor.select("#content .article .section_group.section_group_frst .obj_section.noline .made_people .lst_people > li");
						
						for(int j = 0; j < elemActor.size(); j++) {
							String actorcode = elemActor.get(j).select("p.p_thumb > a").attr("href").replaceAll("[^0-9]", "");
							String actorimg = elemActor.get(j).select("p.p_thumb > a > img").attr("src");
							String actorKname = elemActor.get(j).select("div.p_info > a.k_name").text();
							String actorEname = elemActor.get(j).select("div.p_info > em.e_name").text();
							String actorpart = elemActor.get(j).select("div.p_info > div.part p.in_prt em").text();
							String actorrole = elemActor.get(j).select("div.p_info > div.part p.pe_cmt span").text();
							String actormovie = elemActor.get(j).select("div.p_info > ul.mv_product").text();
							
							ActorDTO a = new ActorDTO(actorcode, moviecode, actorKname, actorEname, actorpart, actorrole, actormovie, actorimg);
							
							actor.add(a);
						}
					} catch (Exception e) {}
					
					// 포토 크롤링
					String photoURL = "https://movie.naver.com/movie/bi/mi/photo.naver?code=" + moviecode + "&page=1#movieEndTabMenu";
					Document docPhoto = null;
					
					try {
						docPhoto = Jsoup.connect(photoURL).get();
						Elements elemPhoto = docPhoto.select("#content .article .obj_section2.noline .photo .gallery_group > ul > li");
						
						for(int k = 0; k < elemPhoto.size(); k++) {
							String photoimg = elemPhoto.get(k).select("a > img").attr("src");
							PhotoDTO p = null;
							
							// 성인인증 포토 제외
							if(!photoimg.equals("https://ssl.pstatic.net/static/movie/2012/06/adult_img221x150.png")) {
								p = new PhotoDTO(photoimg, moviecode);								
							}
							
							photo.add(p);
						}
					} catch (Exception e) {}
				}
			} catch (Exception e) {}
		}
		mapper.pdelete();
		mapper.adelete();
		mapper.mdelete();
		
		mapper.minsert(movie);
		mapper.ainsert(actor);
		mapper.pinsert(photo);
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
}
