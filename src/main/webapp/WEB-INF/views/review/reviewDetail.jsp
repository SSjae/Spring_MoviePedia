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
		<div class="detail-footer">
			<input id="reviewnum" type="hidden" value="${review.reviewnum }"/>
			<div class="detail-cnt">
				좋아요 ${reviewlikecnt}
				댓글 ${commentcnt}		
			</div>
			<c:if test="${loginUser.useremail == user.useremail }">
				<div class="detail-button">
					<input id="useremail" type="hidden" value="${review.useremail }"/>
					<input id="moviecode" type="hidden" value="${review.moviecode }"/>
					<button onclick='reviewUpModal()'><img alt='trash' src='${cp }/resources/images/pencil.svg'>수정</button>
					<button onclick='reviewDelete()'><img alt='pencil' src='${cp }/resources/images/trash.svg'>삭제</button>
				</div>
			</c:if>
		</div>
		<hr>
		<div class="detail-button">
			<button>
				<svg width="20" height="20">
					<path d="M5.6252 7.9043H3.1252C2.6652 7.9043 2.29187 8.27763 2.29187 8.73763V17.071C2.29187 17.531 2.6652 17.9043 3.1252 17.9043H5.6252C6.08604 17.9043 6.45854 17.531 6.45854 17.071V8.73763C6.45854 8.27763 6.08604 7.9043 5.6252 7.9043Z" fill="#87898B"></path>
					<path d="M11.71 4.34525L11.7017 3.99359L11.6825 3.14525L11.6809 3.09692L11.6759 3.04942C11.6684 2.96942 11.6792 2.93442 11.6775 2.93275C11.6917 2.92442 11.7534 2.90442 11.8725 2.90442C12.115 2.90442 13.3225 2.97609 13.3225 4.38692C13.3225 4.93359 13.2775 5.35859 13.1809 5.72692L12.8375 7.03275C12.8034 7.16525 12.9025 7.29442 13.0392 7.29442H14.3892H15.7317C16.0575 7.29442 16.3684 7.43692 16.585 7.68442C16.7975 7.93025 16.9009 8.25609 16.87 8.58275L15.6717 14.7703L15.6634 14.8119L15.6584 14.8536C15.59 15.3961 15.0959 15.8211 14.5334 15.8211H8.54169V8.19942C8.54169 7.89109 8.71169 7.56275 9.04835 7.22359L11.3417 4.90025L11.5775 4.66109C11.71 4.52359 11.71 4.34525 11.71 4.34525ZM17.5275 6.86525C17.0734 6.34275 16.4184 6.04442 15.7317 6.04442H14.3892C14.5167 5.56025 14.5725 5.02942 14.5725 4.38692C14.5725 2.50942 13.1734 1.65442 11.8725 1.65442C11.3825 1.65442 11 1.80775 10.7367 2.11025C10.5667 2.30359 10.3792 2.64442 10.4325 3.17359L10.4517 4.02192L8.15835 6.34525C7.58335 6.92692 7.29169 7.55109 7.29169 8.19942V16.2378C7.29169 16.6978 7.66502 17.0711 8.12502 17.0711H14.5334C15.7342 17.0711 16.7559 16.1603 16.8992 15.0078L18.1067 8.77192C18.1925 8.08109 17.9809 7.38692 17.5275 6.86525Z" fill="#87898B"></path>
				</svg>
				좋아요
			</button>
			<button>
				<svg width="20" height="20">
					<path d="M9.99963 2.08325C5.65046 2.08325 2.12546 5.02159 2.12546 8.64575C2.12546 10.5891 3.13962 12.3358 4.74962 13.5374L4.61129 17.2416C4.61129 17.4899 4.81462 17.6591 5.03046 17.6591C5.12129 17.6591 5.21462 17.6291 5.29462 17.5616L8.12462 15.0208C8.72629 15.1433 9.35379 15.2083 9.99963 15.2083C14.3496 15.2083 17.8746 12.2699 17.8746 8.64575C17.8746 5.02159 14.3496 2.08325 9.99963 2.08325ZM9.99962 3.33325C13.653 3.33325 16.6246 5.71659 16.6246 8.64575C16.6246 11.5749 13.653 13.9583 9.99962 13.9583C9.44962 13.9583 8.90296 13.9041 8.37379 13.7966C8.29129 13.7791 8.20796 13.7708 8.12462 13.7708C7.81962 13.7708 7.52046 13.8833 7.28962 14.0908L5.93462 15.3074L5.99879 13.5841C6.01462 13.1733 5.82712 12.7808 5.49796 12.5349C4.14879 11.5291 3.37546 10.1116 3.37546 8.64575C3.37546 5.71659 6.34712 3.33325 9.99962 3.33325Z" fill="#87898B"></path>
				</svg>
				댓글
			</button>
		</div>
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