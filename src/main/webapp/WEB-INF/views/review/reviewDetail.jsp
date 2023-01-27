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
<body class=".reviewDetail">
	<c:if test="${loginUser == null}">
		<script>
			alert("로그인 후 접속 가능합니다.");
			location.href = "${cp}/user/login";
		</script>
	</c:if>
	<c:import url="../import/header.jsp"></c:import>
	<section class="detail-layer">
		<div class="detail-head">
			<div class="head-content">
				<div class="content-1">
					<img src="${cp }/resources/images/profile.png" alt="프로필">
					<span class="name">${user.username }</span>
					<span class="date">${reviewDateTime }</span>				
				</div>
				<a class="content-2" href="${cp}/movie/movieInfo?moviecode=${movie.moviecode}">
					<div class="title">${movie.movieKtitle }</div>
					<div class="release">영화  ・ ${release }</div>
				</a>
				<div class="content-star">★ ${review.reviewstar }</div>				
			</div>
			<a class="head-img" href="${cp}/movie/movieInfo?moviecode=${movie.moviecode}">
				<img src="${movie.movieimg}" alt="포스터">
			</a>
		</div>
		<div class="detail-content">
			<div class="content">
				${review.reviewcontent }		
			</div>
		</div>
		<c:if test="${loginUser.useremail == user.useremail }">
			<div class="detail-button">
				<input id="useremail" type="hidden" value="${review.useremail }"/>
				<input id="moviecode" type="hidden" value="${review.moviecode }"/>
				<button onclick='reviewUpModal()'><img alt='trash' src='${cp }/resources/images/pencil.svg'>수정</button>
				<button onclick='reviewDelete()'><img alt='pencil' src='${cp }/resources/images/trash.svg'>삭제</button>
			</div>		
		</c:if>
		<hr>
	</section> 
	<c:import url="../import/footer.jsp">
		<c:param name="mtotal" value="${mtotal}"/>
		<c:param name="rtotal" value="${rtotal}"/>
	</c:import>
	<div class="modal">
		<input id="reviewstar" type="hidden" value="${review.reviewstar }"/>
		<input id="reviewcontent" type="hidden" value="${review.reviewcontent }"/>
		<input id="reviewcontentlength" type="hidden" value="${review.reviewcontent.length() }"/>
		<div class="modal_content">
			<div class="modal_head">
				<span>${movie.movieKtitle}</span>
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
</body>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="${cp}/resources/js/review.js"></script>
<script type="text/javascript" src="${cp}/resources/js/import.js"></script>
</html>