<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>무비피디아 - ${loginUser.username }</title>
<link rel="stylesheet" href="${cp}/resources/css/font.css">
<link rel="stylesheet" href="${cp}/resources/css/import.css">
<link rel="stylesheet" href="${cp}/resources/css/user.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css" />
</head>
<body class="myPage">
	<c:if test="${loginUser == null}">
		<script>
			alert("로그인 후 접속 가능합니다.");
			location.href = "${cp}/user/login";
		</script>
	</c:if>
	<c:import url="../import/header.jsp"></c:import>
	<section class="myPage-layer">
		<div class="backProfile" style="background: url(${cp}/resources/images/backProfile/backProfile_${ran}.jpg) no-repeat center; background-size: cover;">
		
		</div>
		<div class="myPage-main">
			<div class="profile">
				<img src="${cp}/resources/images/profile.png" alt="프로필" />
			</div>
			<div class="myPage-head">
				<div class="myPage-name">
					${loginUser.username}
				</div>
				<div class="myPage-button">
					<a href="${cp}/user/logout">로그아웃</a>
					<a class="deleteUser">회원탈퇴</a>
				</div>			
			</div>
			<div class="modal">
				<div class="modal_content">
					<input type="hidden" id="useremail" value="${loginUser.useremail}">
					<div class="modal_head">
						<span>탈퇴 요청 확인</span>
						<img class="x" src="${cp}/resources/images/x.svg" alt="x">				
					</div>
					<div class="delete-content">
						<p>"다시 한번 생각해보세요!"</p>
						<p>${loginUser.username}님이 평가하신 영화, 드라마…. 이런 추억을 모두 버리실 건가요? ㅠ.ㅠ 영화를 보며 감동받았던 순간, 펑펑 울었던 순간…이런 순간들을 버리지 말아주세요.</p>
						<p>${loginUser.username}님, 무비피디아는 아주 빠른 속도로 업데이트되고 있어요. 영화 뿐만 아니라 이승재님을 위한 다양한 문화생활 도우미가 되기 위해 밤낮없이 고민하며 발전시키고 있답니다.</p>
						<p>용서하는 마음으로 이 메일을 살포시 무시해 주시고, 차분하게 무비피디아에서 추억을 기록해보세요. 나중에 살려두길 잘했다고 생각하실 거예요!</p>
						<p>정말로 탈퇴하실 거라면, 이메일 인증 후 아래 버튼을 눌러주세요.</p>
					</div>
					<button id="delete-auth">이메일 인증</button>
					<div class="authdiv">
						<input id="authnum" type = "text" name="authnum" placeholder="인증번호 확인"/>
						<button id="delete-authbt">인증</button>
					</div>
					<button id="delete">회원 탈퇴 하기</button>
					<form method="post" action="${cp}/user/delete" id="deleteForm">
						<input type="hidden" value="${loginUser.useremail}" name="useremail">
					</form>
				</div>
			</div>
			<hr>
			<div class="myPage-review">
				<div class="text">평가<span>${myReview.size() }</span></div>
				<div class="review-main">
					<c:forEach items="${myReview}" var="review" varStatus="status">
						<div class="main">
							<a href="${cp}/movie/movieInfo?moviecode=${review.moviecode}">
								<img src="${myReviewMovie.get(status.index).movieimg }" alt="img">
								<div class="title">${myReviewMovie.get(status.index).movieKtitle }</div>
								<div class="rate">평가함 ★${review.reviewstar }</div>
							</a>
						</div>
					</c:forEach>				
				</div>
			</div>
			<hr>
			<div class="myPage-like">
				<div class="text">보고싶어요<span>${myLike.size() }</span></div>
				<div class="like-main">
					<c:forEach items="${myLike}" var="like" varStatus="status">
						<div class="main">
							<a href="${cp}/movie/movieInfo?moviecode=${like.moviecode}">
								<img src="${myLikeMovie.get(status.index).movieimg }" alt="img">
								<div class="title">${myLikeMovie.get(status.index).movieKtitle }</div>
								<div class="rate">평균 ★${myLikeMovie.get(status.index).moviestar }</div>
							</a>
						</div>
					</c:forEach>				
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
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script> 
<script type="text/javascript" src="${cp}/resources/js/user.js"></script>
<script type="text/javascript" src="${cp}/resources/js/import.js"></script>
</html>