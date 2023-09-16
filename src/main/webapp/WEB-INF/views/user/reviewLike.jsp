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
<link rel="stylesheet" href="${cp}/resources/css/user.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
</head>
<body class="reviewLike">
	<c:if test="${loginUser == null}">
		<script>
			alert("로그인 후 접속 가능합니다.");
			location.href = "${cp}/user/login";
		</script>
	</c:if>
	<c:import url="../import/header.jsp"></c:import>
	<section class="all-layer">
		<div class="review">
			<div class="text">
				평가<span>${myReview.size() }</span>
			</div>
			<div class="review-main">
				<c:forEach items="${myReview}" var="review" varStatus="status">
					<div class="main">
						<a href="${cp}/movie/movieInfo?moviecode=${review.moviecode}">
							<img src="${myReviewMovie.get(status.index).movieimg }" alt="img">
							<div class="title">${myReviewMovie.get(status.index).movietitle }</div>
							<div class="rate">평가함 ★${review.reviewstar }</div>
						</a>
					</div>
				</c:forEach>				
			</div>
		</div>
		<div class="like">
			<div class="text">
				보고싶어요<span>${myLike.size() }</span>
			</div>
			<div class="like-main">
				<c:forEach items="${myLike}" var="like" varStatus="status">
					<div class="main">
						<a href="${cp}/movie/movieInfo?moviecode=${like.moviecode}">
							<img src="${myLikeMovie.get(status.index).movieimg }" alt="img">
							<div class="title">${myLikeMovie.get(status.index).movietitle }</div>
							<div class="rate">평균 ★${myLikeMovie.get(status.index).moviestar }</div>
						</a>
					</div>
				</c:forEach>				
			</div>
		</div>
	</section> 
	<c:import url="../import/footer.jsp">
		<c:param name="mtotal" value="${mtotal}"/>
		<c:param name="rtotal" value="${rtotal}"/>
	</c:import>
</body>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script> 
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="${cp}/resources/js/movie.js"></script>
<script type="text/javascript" src="${cp}/resources/js/user.js"></script>
<script type="text/javascript" src="${cp}/resources/js/import.js"></script>
</html>