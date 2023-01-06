// contextpath 꺼내오기
function getContextPath() {
	return sessionStorage.getItem("contextpath");
}
let ctx = getContextPath();

//수정 버튼
const reviewUpModal = () => {
	$(".modal").fadeIn();
	$("#comment").focus();	
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

//로드 될 때 리뷰 수정을 위해 모달에 미리 써놓기
$(document).ready(() => {
	let reviewstar = $("#reviewstar").val();
	let reviewcontent = $("#reviewcontent").val();
	let reviewcontentlength = $("#reviewcontentlength").val();
	
	$(".star span").css("width",reviewstar * 2 * 10 + "%");
	$("#star").val(reviewstar * 2);
	$("#comment").val(reviewcontent);
	$(".comment_len").text(reviewcontentlength);
})

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

// 자기 자신 리뷰 수정
$(".comment_btn").click(() => {
	let useremail = $("#useremail").val();
	let moviecode = $("#moviecode").val();
	let reviewcontent = $("#comment").val();
	let reviewstar = $("#star").val() / 2.0;
	
	$.ajax({
		url : ctx+"/review/auReview",
		type : "POST",
		data : JSON.stringify({"useremail":useremail,
				"moviecode":moviecode,
				"reviewcontent":reviewcontent,
				"reviewstar":reviewstar,
				"update":true
			}),
		contentType:"application/json; charset=utf-8",
		dataType:"json",
		success : function(result) {
			if(result.check === "true") {
				$(".content-star").text("★ "+reviewstar);
				$(".content").text(reviewcontent);
				$(".modal").fadeOut();
			} else {
				alert("코멘트 작성이 실패하였습니다. 다시 시도해주세요");
			}
		},
		error : function() {
			console.log("서버 ");
		}
	})
})

// 자기 자신 리뷰 삭제
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
					location.href = ctx+"/movie/movieInfo?moviecode="+moviecode;
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