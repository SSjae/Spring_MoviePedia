<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>무비피디아</title>
<link rel="stylesheet" href="${cp}/resources/css/font.css">
<link rel="stylesheet" href="${cp}/resources/css/import.css">
<link rel="stylesheet" href="${cp}/resources/css/movie.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css" />
</head>
<body class="movieInfo">
	<c:if test="${loginUser == null}">
		<script>
			alert("로그인 후 접속 가능합니다.");
			location.href = "${cp}/user/login";
		</script>
	</c:if>
	<c:import url="../import/header.jsp"></c:import>
	<section class="info-layer">
		<div class="info-head">
			<div class="head">
				<div class="poster">
					<img src="${movie.movieimg}" alt="포스터">
				</div>
				<div class="text">
					<div class="text-title">${movie.movietitle }</div>
					<div class="text-etc">${movie.movierelease} ・ ${movie.moviegenre } ・ ${movie.movienation }</div>
					<div class="text-star">평균 ★<span id="starAvg">${movie.moviestar}</span> (<span id="rMemberCnt">${rMemberCnt}명</span>)</div>
					<div class="text-review">
						<div class="review-like">
							<input id="useremail" type="hidden" value="${loginUser.useremail}">
							<input id="username" type="hidden" value="${loginUser.username}">
							<svg class="plus" width="24" height="24">
								<path d="M20.5 13.0929H13.1428V20.5H10.8571V13.0929H3.5V10.8071H10.8571V3.5H13.1428V10.8071H20.5V13.0929Z" fill="currentColor"></path>
							</svg>
							<svg class="flag" width="24" height="24">
								<path d="M18.5969 7.14941H5.26761C5.0139 7.14941 4.80798 7.35533 4.80798 7.60904V20.0365C4.80798 20.4098 5.22901 20.6267 5.53328 20.4107L11.9323 15.8705L18.3312 20.4107C18.6355 20.6267 19.0565 20.4098 19.0565 20.0365V7.60904C19.0565 7.35533 18.8506 7.14941 18.5969 7.14941Z" fill="currentColor"></path>
								<path d="M18.1373 3H5.72725C5.21889 3 4.80798 3.41183 4.80798 3.91926V5.29815C4.80798 5.55187 5.0139 5.75779 5.26761 5.75779H18.5969C18.8506 5.75779 19.0565 5.55187 19.0565 5.29815V3.91926C19.0565 3.41183 18.6447 3 18.1373 3Z" fill="currentColor"></path>
							</svg>
							<span>보고싶어요</span>
						</div>
						<div class="review-comment" onclick="reviewModal()">
							<svg class="pencil" width="24" height="24">
								<path d="M3 17.2525V21.0025H6.75L17.81 9.9425L14.06 6.1925L3 17.2525ZM20.71 7.0425C21.1 6.6525 21.1 6.0225 20.71 5.6325L18.37 3.2925C17.98 2.9025 17.35 2.9025 16.96 3.2925L15.13 5.1225L18.88 8.8725L20.71 7.0425Z" fill="currentColor"></path>
							</svg>
							<span>코멘트</span>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="modal">
			<div class="modal_content">
				<div class="modal_head">
					<span>${movie.movietitle}</span>
					<img class="x" src="${cp}/resources/images/x.svg" alt="x">				
				</div>
				<textarea id="comment" maxlength="10000" placeholder="이 작품에 대한 생각을 자유롭게 표현해주세요."></textarea>
				<div class="modal_footer">
					<div class="review-star">
						<div class="starMemo">평가하기</div>
						<span class="star">
							★★★★★
							<span>★★★★★</span>
							<input id="star" type="range" oninput="drawStar(this)" value="0" step="1" min="0" max="10">
						</span>
					</div>
					<div class="modal_etc">
						<span class="comment_len">0</span><span>/10000</span>
						<input class="comment_btn" type='button' disabled="disabled" value='저장'/>						
					</div>
				</div>
			</div>
		</div>
		<div class="info-comment">
		
		</div>
		<div class="info-main">
			<div>
				<div class="main-text">기본 정보<a href="${cp}/movie/movieDetail?moviecode=${movie.moviecode}">더보기</a></div>
				<div class="moviedetail">
					${movie.movietitle }<br>
					${movie.movierelease} ・ ${movie.moviegenre } ・ ${movie.movienation }<br>
					${movie.movietime } ・ ${movie.moviegrade }<br>
					<div class="content">
						<div>${movie.moviesummary }</div>
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
											<img src="${actors.get(j).actorimg == '' ? 'https://avatars.githubusercontent.com/u/222212?v=4' : actors.get(j).actorimg}" alt="actorImg"/>
											<div class="text">
												<div class="kname">${actors.get(j).actorname }</div>
												<div class="part">${actors.get(j).actorpart }</div>							
											</div>
										</div>
									</c:forEach>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
			</c:if>
			<c:if test="${photos.size() != 0}">
				<hr>
				<div>
					<div class="main-text">갤러리</div>
					<div class="photos photos-slick">
						<c:forEach items="${photos}" var="photo" varStatus="status">
							<div class="photos-main">
								<a href="${photo.photoimg }" target="_blank"><img src="${photo.photoimg }" alt="photoImg"/></a>				
							</div>
						</c:forEach>
					</div>
				</div>
			</c:if>
			<c:if test="${videos.size() != 0}">
				<hr>
				<div>
					<div class="main-text">동영상</div>
					<div class="videos videos-slick">
						<c:forEach items="${videos}" var="video" varStatus="status">
							<div class="videos-main">
								<a href="${video.videoaddr }" target="_blank">
									<div class="play">
										<img src="data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHdpZHRoPSIzMiIgaGVpZ2h0PSIzMiIgdmlld0JveD0iMCAwIDMyIDMyIj4KICAgIDxnIGZpbGw9Im5vbmUiIGZpbGwtcnVsZT0iZXZlbm9kZCI+CiAgICAgICAgPGNpcmNsZSBjeD0iMTYiIGN5PSIxNiIgcj0iMTUiIGZpbGw9IiMwMDAiIGZpbGwtb3BhY2l0eT0iLjUxIi8+CiAgICAgICAgPHBhdGggZmlsbD0iI0ZGRiIgZD0iTTE2IDMwYzcuNzMyIDAgMTQtNi4yNjggMTQtMTQgMC03LjczMS02LjI2OC0xNC0xNC0xNFMyIDguMjY5IDIgMTZjMCA3LjczMiA2LjI2OCAxNCAxNCAxNG0wIDJDNy4xNjMgMzIgMCAyNC44MzcgMCAxNiAwIDcuMTY0IDcuMTYzIDAgMTYgMHMxNiA3LjE2NCAxNiAxNmMwIDguODM3LTcuMTYzIDE2LTE2IDE2Ii8+CiAgICAgICAgPHBhdGggZmlsbD0iI0ZGRiIgZD0iTTEyIDIyLjA5VjkuOTFhLjUuNSAwIDAgMSAuNzY5LS40MjFsOS41NjggNi4wODlhLjUuNSAwIDAgMSAwIC44NDRsLTkuNTY4IDYuMDlBLjUuNSAwIDAgMSAxMiAyMi4wOSIvPgogICAgPC9nPgo8L3N2Zz4K" alt="play"/>									
									</div>
									<img src="${video.videoimg }" alt="videoImg"/>
									<span class="videotitle">${video.videotitle }</span>
								</a>
							</div>
						</c:forEach>
					</div>
				</div>
			</c:if>
			<div class="info-allComment">
			
			</div>
			<input type="hidden" value="${movie.moviecode }" id="moviecode"/>
			<c:if test="${similar.size() != 0}">
				<hr>
				<div>
					<div class="main-text">비슷한 장르 영화</div>
					<div class="similars">
						<c:forEach var="i" begin="0" end="${similar.size() > 15 ? 14 : similar.size() - 1}" step="1">
							<div class="similars-main">
								<a href="${cp}/movie/movieInfo?moviecode=${similar.get(i).moviecode}">
									<img src="${similar.get(i).movieimg }" alt="img">
									<span class="title">${similar.get(i).movietitle }</span>
									<span class="rate">평균 ★${similar.get(i).moviestar }</span>
								</a>
							</div>			
						</c:forEach>
						<c:if test="${similar.size() > 15}">
							<button class='add'>더보기 <img src="${cp}/resources/images/bottomarrow.svg" alt="아래화살표"></button>					
						</c:if>
					</div>
				</div>
			</c:if>
		</div>
	</section> 
	<c:import url="../import/footer.jsp">
		<c:param name="mtotal" value="${mtotal}"/>
		<c:param name="rtotal" value="${rtotal}"/>
	</c:import>
</body>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script> 
<script type="text/javascript" src="${cp}/resources/js/movie.js"></script>
<script type="text/javascript" src="${cp}/resources/js/import.js"></script>
</html>