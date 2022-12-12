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
<link rel="stylesheet" href="${cp}/resources/css/main.css">
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css" />
</head>
<body>
	<c:import url="./import/header.jsp"></c:import>
	<section>
		<div class="card" style="width: 100%; height: 1500px;">
			<div class="card-text">
				무비피디아 TOP 10 영화
			</div>
			<div class="card-main responsive">
				<c:forEach items="${top10}" var="top10">
					<div class="main">
						<img src="${top10.movieimg }" alt="img">
						<div class="text">
							${top10.movieKtitle }
						</div>
					</div>			
				</c:forEach>
			</div>
		</div>
	</section>
	<c:import url="./import/footer.jsp">
		<c:param name="mtotal" value="${mtotal}"/>
		<c:param name="rtotal" value="${rtotal}"/>
	</c:import>
</body>
<script src="http://code.jquery.com/jquery-3.6.1.js"></script>
<script src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script> 
<script type="text/javascript" src="${cp}/resources/js/main.js"></script>
</html>