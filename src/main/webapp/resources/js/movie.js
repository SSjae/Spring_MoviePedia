//출연자 slick
$('.actors-slick').slick({
  infinite: false,
  arrows : true,
  draggable : false,
  speed: 300,
  slidesToShow: 3,
  slidesToScroll: 3
});

// 갤러리 slick
$('.photos-slick').slick({
  infinite: false,
  arrows : true,
  draggable : false,
  variableWidth: true,
  speed: 300,
  slidesToShow: 4,
  slidesToScroll: 4
 });

//동영상 slick
$('.videos-slick').slick({
  infinite: false,
  arrows : true,
  draggable : false,
  variableWidth: true,
  speed: 300,
  slidesToShow: 4,
  slidesToScroll: 4
 });

// 코멘트 slick
$('.reviews-slick').slick({
  infinite: false,
  arrows : true,
  draggable : false,
  speed: 300,
  slidesToShow: 3,
  slidesToScroll: 3
 });

// 더보기를 눌렀을 때 나머지 출력
$(".add").click((e) => {
	e.preventDefault();
	$(".add").hide();
	let content = "";
	$.ajax({
		url : ctx+"/movie/similars",
		type : "get",
        dataType:"json",
		data : {"moviecode":$("#moviecode").val()},
		success : function(result) {
			for(let i = 0; i < result.length; i++) {
				content += "<div class='similars-main'>";
				content += "<a href="+ctx+"/movie/movieInfo?moviecode="+result[i].moviecode+">";
				content += "<img src='"+result[i].movieimg+"'alt='img'>";
				content += "<span class='title'>"+result[i].movietitle+"</span>";
				content += "<span class='rate'>평균 ★"+result[i].moviestar+"</span>";
				content += "</a>"
				content += "</div>"
			}
			$(".similars").append(content);
		},
		error : function() {
			console.log("서버 ");
		}
	})
})

// 코멘트가 있는지 없는지 확인 후 있으면 다시 그리고 없으면 안 그리기
const allComment = () => {
	let content = "";
	let moviecode = $("#moviecode").val();
	$.ajax({
		url : ctx+"/review/allReview",
		type : "get",
		dataType:"json",
		data : {"moviecode":$("#moviecode").val()},
		success : function(result) {
			if(result.length != 0) {
				content += '<hr>';
				content += '<div class="main-text">코멘트 <span>'+result.length+'</span><a href="'+ctx+'/review/reviewAll/'+moviecode+'">더보기</a></div>';
				content += '<div class="reviews reviews-slick">';
				for(let i = 0; i < result.length; i++) {
					content += '<div class="review">';
					content += 	'<div class="review-head">';
					content += 		'<div class="review-name">';
					content += 			'<img src="'+ctx+'/resources/images/profile.png" alt="프로필">';
					content += 			result[i].username;
					content += 		'</div>';
					content += 		'<div class="review-star">★ '+result[i].reviewstar+'</div>';
					content += 	'</div>';
					content += 	'<a href="'+ctx+'/review/reviewDetail/'+result[i].reviewnum+'/'+result[i].moviecode+'">';
					content += 		'<div class="review-content">';
					content += 			result[i].reviewcontent;
					content += 		'</div>';
					content += 	'</a>';
					content +=	'<div class="footer">';
					content +=		'<div class="review-footer">';
					content +=			'<img class="good" src="'+ctx+'/resources/images/good.svg" alt="good">'+result[i].reviewlikecnt;
					content +=			'<img src="'+ctx+'/resources/images/comment.svg" alt="comment">'+result[i].commentcnt;
					content +=		'</div>';
					content +=	'</div>';
					content += '</div>';
				}
				content += '</div>';
				$(".info-allComment").empty();
				$(".info-allComment").append(content);
			} else {
				$(".info-allComment").empty();
			}
			
			// 코멘트 slick
			$('.reviews-slick').slick({
			  infinite: false,
			  arrows : true,
			  draggable : false,
			  speed: 300,
			  slidesToShow: 3,
			  slidesToScroll: 3
			});
		},
		error : function() {
			console.log("좋아요 로딩 ");
		}
	})
}

// 리뷰가 등록 안되어있을 때
const noReview = (username) => {
	let content = "";
	content += "<div class='no-comment'>";
	content += "<span>이 작품에 대한 "+ username +" 님의 평가를 글로 남겨보세요.</span>";
	content += "<button onclick='reviewModal()'>코멘트 남기기</button>";
	content += "</div>"
	$(".info-comment").append(content);
}

// 리뷰가 등록 되어 있을 때
const okReview = (reviewnum, reviewstar, reviewcontent, moviecode) => {
	let content = "";
	content += "<div class='ok-comment'>";
	content += "<div class='comment-SC'>";
	content += "<div class='S-star'>내 평점<br>★"+reviewstar+"</div>";
	content += "<div class='C-content'>";
	content += "<div class='text'>";
	content += "<a href='"+ctx+"/review/reviewDetail/"+reviewnum+"/"+moviecode+"'>"+reviewcontent+"</a>";
	content += "</div></div></div>";
	content += "<div class='comment-UD'>";
	content += "<button onclick='reviewUpModal()'><img alt='trash' src='"+ctx+"/resources/images/pencil.svg'>수정</button>";
	content += "<button onclick='reviewDelete()'><img alt='pencil' src='"+ctx+"/resources/images/trash.svg'>삭제</button>";
	content += "</div></div>";
	$(".info-comment").append(content);
}

// 로드 될 때 좋아요 있는지 없는지 확인
// 로드 될 때 리뷰가 있는지 없는지 확인
$(document).ready(() => {
	$.ajax({
		url : ctx+"/likemovie/likeOk",
		type : "get",
		data : {"moviecode":$("#moviecode").val(), "useremail":$("#useremail").val()},
		success : function(result) {
			if(result === "ok") {				
				$(".plus").hide();
				$(".flag").show();
				$(".review-like").css("color", "#ff2f6e");
				$(".review-like").addClass("ok");
			} else {
				$(".plus").show();
				$(".flag").hide();
				$(".review-like").css("color", "rgb(41, 42, 50)");
				$(".review-like").addClass("no");
			}
		},
		error : function() {
			console.log("좋아요 로딩 ");
		}
	})
	
	$.ajax({
		url : ctx+"/review/reviewOk",
		type : "get",
		data : {"moviecode":$("#moviecode").val(), "useremail":$("#useremail").val()},
		dataType:"json",
		success : function(result) {
			if(result.useremail === null) {
				noReview($("#username").val());
			} else {
				// ok 클래스 추가(리뷰가 있다는 뜻)
				$(".review-comment").addClass("ok");
				// 수정을 위해 모달에다가 작성한 리뷰 내용 추가
				$(".star span").css("width",result.reviewstar * 2 * 10 + "%");
				$("#star").val(result.reviewstar * 2);
				$("#comment").val(result.reviewcontent);
				$(".comment_len").text(result.reviewcontent.length);
				okReview(result.reviewnum, result.reviewstar, result.reviewcontent, result.moviecode);
			}
		},
		error : function() {
			console.log("리뷰 로딩");
		}
	})
	
	// 이 영화에 코멘트가 있는 지 없는지에 따라 그려짐
	allComment();
})

// 보고싶어요 클릭
$(".review-like").click(() => {
	// ajax로 보낸 데이터인 status에 따라 보고싶어요 추가인지 취소인지 구별
	let status = "";
	if($(".review-like").hasClass("ok")) {
		// 이미 보고싶어요를 한 상태이면
		status = "like";
	} else {
		// 보고싶어요를 한 상태가 아니면
		status = "hate";
	}
	
	$.ajax({
		url : ctx+"/likemovie/like",
		type : "get",
		data : {"status":status, "moviecode":$("#moviecode").val(), "useremail":$("#useremail").val()},
		success : function(result) {
			if(result === "ok") {				
				// status가 hate로 가서 보고싶어요를 추가한 상태
				$(".plus").hide();
				$(".flag").show();
				$(".review-like").css("color", "#ff2f6e");
				$(".review-like").addClass("ok");
				$(".review-like").removeClass("no");
			} else {
				// status가 like로 가서 보고싶어요를 취소한 상태
				$(".plus").show();
				$(".flag").hide();
				$(".review-like").css("color", "rgb(41, 42, 50)");
				$(".review-like").addClass("no");
				$(".review-like").removeClass("ok");
			}
		},
		error : function() {
			console.log("서버 ");
		}
	})
})

// 별점 보이게 하기(메세지)
let starMemo = [
	"평가하기",
	"최악이에요",
	"싫어요",
	"재미없어요",
	"별로예요",
	"부족해요",
	"보통이에요",
	"볼만해요",
	"재미있어요",
	"훌륭해요!",
	"최고예요!"
]

const drawStar = (target) => {
	$(".star span").css("width",target.value*10 + "%");
	$(".starMemo").text(starMemo[target.value]);
}

// 코멘트 모달 떠서 글자 한글자씩 입력했을 때
$("#comment").keyup((e) => {
	let content = $("#comment").val();
	
	if(content.length === 0 || content === "") {
		$(".comment_len").text("0");
		$(".comment_btn").css("background", "#F2F2F2");
		$(".comment_btn").css("cursor", "default");
		$(".comment_btn").attr("disabled","disabled");
	} else {
		$(".comment_len").text(content.length);
		$(".comment_btn").css("background", "rgb(255, 47, 110)");
		$(".comment_btn").css("cursor", "pointer");
		$(".comment_btn").removeAttr("disabled");
	}
})

// 코멘트 모달 띄우고 닫기
const reviewModal = () => {
	if($(".review-comment").hasClass("ok")) {
		
	} else {
		$(".modal").fadeIn();
		$("#comment").focus();				
	}
}

// 수정 버튼
const reviewUpModal = () => {
	$(".modal").fadeIn();
	$("#comment").focus();	
}

$(".x").click(() => {
	$(".modal").fadeOut();
})

// 코멘트 작성 및 수정
$(".comment_btn").click(() => {
	let useremail = $("#useremail").val();
	let moviecode = $("#moviecode").val();
	let reviewcontent = $("#comment").val();
	let reviewstar = $("#star").val() / 2.0;
	let status = $(".review-comment").hasClass("ok") ? true : false;
	
	$.ajax({
		url : ctx+"/review/auReview",
		type : "POST",
		data : JSON.stringify({"useremail":useremail,
				"moviecode":moviecode,
				"reviewcontent":reviewcontent,
				"reviewstar":reviewstar,
				"update":status
			}),
		contentType:"application/json; charset=utf-8",
		dataType:"json",
		success : function(result) {
			if(result.check === "true") {
				// 평점, 리뷰 갯수, ok 클래스 삽입
				$("#starAvg").text(result.moviestar);
				$("#rMemberCnt").text(result.rMemberCnt);
				$(".modal").fadeOut();
				$(".review-comment").addClass("ok");
				// 작성 성공하면 원래 있던 코멘트 달기 지우고 자기 코멘트 띄우게 하기
				$(".info-comment").empty();
				okReview(result.reviewnum, reviewstar, reviewcontent, moviecode);
				// 모든 코멘트 부분 다시 그리기
				allComment();
			} else {
				alert("코멘트 작성이 실패하였습니다. 다시 시도해주세요");
			}
		},
		error : function() {
			console.log("서버 ");
		}
	})
})

// 리뷰 삭제 버튼
const reviewDelete = () => {
	let useremail = $("#useremail").val();
	let moviecode = $("#moviecode").val();
	
	if(confirm("코멘트를 삭제하시겠어요?")) {
		$.ajax({
			url : ctx+"/review/deleteReview",
			type : "POST",
			data : JSON.stringify({"useremail":useremail,
				"moviecode":moviecode }),
			contentType:"application/json; charset=utf-8",
			dataType:"json",
			success : function(result) {
				if(result.check === "true") {
					// 평점, 리뷰 갯수, ok 클래스 제거
					$("#starAvg").text(result.moviestar);
					$("#rMemberCnt").text(result.rMemberCnt);
					$(".review-comment").removeClass("ok");
					// 삭제 성공하면 자기 코멘트 없애고 원래 있던 코멘트 달기 띄우기
					$(".info-comment").empty();
					noReview($("#username").val());
					// 삭제가 됬으니 코멘트 모달에 있는 내용들 초기화
					$(".star span").css("width","0%");
					$("#star").val(0);
					$("#comment").val("");
					$(".comment_len").text("0");
					// 모든 코멘트 부분 다시 그리기
					allComment();
				} else {
					alert("코멘트 삭제 실패하였습니다. 다시 시도해주세요");
				}
			},
			error : function() {
				console.log("서버 ");
			}
		})		
	}
}