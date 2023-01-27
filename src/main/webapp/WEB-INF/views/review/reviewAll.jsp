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
<link rel="stylesheet" href="${cp}/resources/css/review.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
</head>
<body class="reviewAll">
	<c:import url="../import/header.jsp"></c:import>
	<section class="all-layer">
		<div class="all-header">
			<img onclick="history.go(-1);" src="${cp}/resources/images/backarrow.svg" alt="뒤로가기">
			<div class="text">코멘트</div>
		</div>
		<div class="all-main">
			<c:forEach items="${allReviews}" var="review">
				<div class="all-review">
					<div class="review-head">
						<div class="review-name">
							<img src="${cp }/resources/images/profile.png" alt="프로필">
							${review.username }		
						</div>
						<div class="review-star">★  ${review.reviewstar }</div>
					</div>
					<a href="${cp}/review/reviewDetail/${review.reviewnum}/${review.moviecode}">
						<div class="review-content">
							${review.reviewcontent }
						</div>					
					</a>
				</div>
			</c:forEach>
		</div>
	</section>
</body>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="${cp}/resources/js/review.js"></script>
<script type="text/javascript" src="${cp}/resources/js/import.js"></script>
</html>