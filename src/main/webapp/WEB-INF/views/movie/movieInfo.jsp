<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MoviePedia</title>
<link rel="stylesheet" href="${cp}/resources/css/font.css">
<link rel="stylesheet" href="${cp}/resources/css/import.css">
<link rel="stylesheet" href="${cp}/resources/css/movie.css">
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css" />
</head>
<body class="movieInfo">
	<c:import url="../import/header.jsp"></c:import>
	<section class="info-layer">
		<div class="info-head">
			<div class="head">
				<div class="poster">
					<img src="${movie.movieimg}" alt="포스터">
				</div>
				<div class="text">
					<div class="text-title">${movie.movieKtitle }</div>
					<div class="text-subTitle">${movie.movieEtitle == "" ? "" : movie.movieEtitle}</div>
					<div class="text-etc">${movie.movierelease == "" ? "" : movie.movierelease += " ・ "}${movie.moviegenre } ・ ${movie.movienation }</div>
					<div class="text-star">평균 ★${movie.moviestar} (${rMemberCnt}명)</div>
				</div>
			</div>
		</div>
		<div class="info-main">
			<div>
				<div class="main-text">기본 정보<a href="${cp}/movie/movieDetail?moviecode=${movie.moviecode}">더보기</a></div>
				<div class="moviedetail">
					${movie.movieKtitle }<br>
					${movie.movierelease == "" ? "" : movie.movierelease += " ・ "}${movie.moviegenre } ・ ${movie.movienation }<br>
					${movie.movietime } ・ ${movie.moviegrade }<br>
					<div class="content">
						<div>${movie.movieHsummary == "" ? "" : movie.movieHsummary}</div>
						<div>${movie.movieCsummary }</div>
					</div>
				</div>
			</div>
			<c:if test="${actors.size() != 0}">
				<hr>
				<div>
					<div class="main-text">출연자</div>
					<ul class="actors-slick">
						<c:forEach items="${actors}" var="actor" varStatus="status">
							<c:if test="${status.index % 3 eq 0 }">
								<li class="actors">
									<c:forEach var="j" begin="${status.index}" end="${actors.size()-status.index > 3 ? status.index + (3 - 1) : actors.size()-1}" step="1">
										<div class="actors-main">
											<img src="${actors.get(j).actorimg }" alt="actorImg"/>
											<div class="text">
												<div class="kname">${actors.get(j).actorKname }</div>
												<div class="part">${actors.get(j).actorpart } | ${actors.get(j).actorrole }</div>							
											</div>
										</div>
									</c:forEach>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</c:if>
			<hr>
			<div>
				<div class="main-text">갤러리</div>
				<div class="photos photos-slick">
					<c:forEach items="${photos}" var="photo" varStatus="status">
						<div class="photos-main">
							<img src="${photo.photoimg }" alt="photoImg"/>						
						</div>
					</c:forEach>
				</div>
			</div>
			<hr>
			<div>
				<div class="main-text">리뷰</div>
				<div class="reviews">
				
				</div>
			</div>
			<hr>
			<div>
				<div class="main-text">비슷한 장르 영화</div>
				<input type="hidden" value="${movie.moviecode }" id="moviecode"/>
				<div class="similars">
					<c:forEach var="i" begin="0" end="${similar.size() > 15 ? 14 : similar.size() - 1}" step="1">
						<div class="similars-main">
							<a href="${cp}/movie/movieInfo?moviecode=${similar.get(i).moviecode}">
								<img src="${similar.get(i).movieimg }" alt="img">
								<span class="title">${similar.get(i).movieKtitle }</span>
								<span class="rate">평균 ★${similar.get(i).moviestar }</span>
							</a>
						</div>			
					</c:forEach>
					<c:if test="${similar.size() > 15}">
						<button class='add'>더보기 <img src="${cp}/resources/images/bottomarrow.svg" alt="아래화살표"></button>					
					</c:if>
				</div>
			</div>
		</div>
	</section> 
	<c:import url="../import/footer.jsp">
		<c:param name="mtotal" value="${mtotal}"/>
		<c:param name="rtotal" value="${rtotal}"/>
	</c:import>
</body>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script> 
<script type="text/javascript" src="${cp}/resources/js/movie.js"></script>
</html>