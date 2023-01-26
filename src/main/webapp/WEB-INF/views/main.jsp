<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>무비피디야</title>
<link rel="stylesheet" href="${cp}/resources/css/font.css">
<link rel="stylesheet" href="${cp}/resources/css/import.css">
<link rel="stylesheet" href="${cp}/resources/css/main.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css" />
</head>
<body>
	<c:import url="./import/header.jsp"></c:import>
	<section class="main-layer">
		<c:forEach items="${movieLists}" var="movieLists">
			<div class="card">
				<div class="card-text">
					${movieLists.title }<a href="${cp}/movie/movieAll?title=${movieLists.title}">${movieLists.all == false ? "" : "모두 보기 > "}</a>
				</div>
				<div class="card-main main-movie">
					<c:forEach items="${movieLists.movies}" var="movie" varStatus="status">
						<div class="main">
							<a href="${cp}/movie/movieInfo?moviecode=${movie.moviecode}">
								<img src="${movie.movieimg }" alt="img">
								<span class="title">${movie.movieKtitle }</span>
								<span class="year-nation">
									${movieLists.releases.get(status.index) == "" ? "" : movieLists.releases.get(status.index) += " ・ "}${movie.movienation}
								</span>
								<span class="rate">평균 ★${movie.moviestar }</span>
							</a>
						</div>			
					</c:forEach>
				</div>
			</div>
		</c:forEach>
	</section>
	<c:import url="./import/footer.jsp">
		<c:param name="mtotal" value="${mtotal}"/>
		<c:param name="rtotal" value="${rtotal}"/>
	</c:import>
</body>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script> 
<script type="text/javascript" src="${cp}/resources/js/main.js"></script>
<script type="text/javascript" src="${cp}/resources/js/import.js"></script>
</html>