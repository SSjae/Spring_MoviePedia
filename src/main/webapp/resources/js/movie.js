// contextpath 꺼내오기
function getContextPath() {
	return sessionStorage.getItem("contextpath");
}
let ctx = getContextPath();

// 출연자 slick
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
				content += "<span class='title'>"+result[i].movieKtitle+"</span>";
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
			console.log(result)
			if(result) {
				// ok 클래스 추가(리뷰가 있다는 뜻)
				$(".review-comment").addClass("ok");
				// 수정을 위해 모달에다가 작성한 리뷰 내용 추가
				$(".star span").css("width",result.reviewstar * 2 * 10 + "%");
				$("#star").val(result.reviewstar * 2);
				$("#comment").val(result.reviewcontent);
				$(".comment_len").text(result.reviewcontent.length);
			} else {
				console.log(result);
				console.log("없다");
			}
		},
		error : function() {
		}
	})
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

// 코멘트 모달 띄우고 닫기
const reviewModal = () => {
	if($(".review-comment").hasClass("ok")) {
		
	} else {
		$(".modal").fadeIn();
		$("#comment").focus();				
	}
}

$(".x").click(() => {
	$(".modal").fadeOut();
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

// 코멘트 작성
$(".comment_btn").click(() => {
	let useremail = $("#useremail").val();
	let moviecode = $("#moviecode").val();
	let reviewcontent = $("#comment").val();
	let reviewstar = $("#star").val() / 2.0;
	
	$.ajax({
		url : ctx+"/review/addReview",
		type : "POST",
		data : JSON.stringify({"useremail":useremail,
				"moviecode":moviecode,
				"reviewcontent":reviewcontent,
				"reviewstar":reviewstar
			}),
		contentType:"application/json; charset=utf-8",
		dataType:"json",
		success : function(result) {
			if(result.check === "true") {
				$("#starAvg").text(result.moviestar);
				$("#rMemberCnt").text(result.rMemberCnt);
				$(".modal").fadeOut();
				$(".review-comment").addClass("ok");
			} else {
				alert("코멘트 작성이 실패하였습니다. 다시 시도해주세요");
			}
		},
		error : function() {
			console.log("서버 ");
		}
	})
})