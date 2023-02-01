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

// 댓글 코멘트 부분 그리기
const comment = () => {
	$.ajax({
		url : ctx+"/review/commentOk",
		type : "get",
		data : {"reviewnum":$("#reviewnum").val()},
		dataType:"json",
		success : function(result) {
			let content = "";
			if(result.length != 0) {
				// 댓글이 있을 때
				for(let i = 0; i < result.length; i++) {
					content += '<div class="comment">';
					content += 	'<div class="comment-head">';
					content += 		'<div class="head-1">';
					content += 			'<img src="'+ctx+'/resources/images/profile.png" alt="프로필">';
					content += 			'<span class="name">'+result[i].username+'</span>';
					content += 		'</div>';
					content += 		'<div class="head-2">';
					content += 			'<span class="date">'+result[i].commentdate+'</span>';
					content += 		'</div>';
					content += 	'</div>';
					content += 	'<div class="comment-content">';
					content += 		'<span>'+result[i].commentcontent+'</span>';
					if($("#useremail").val() === result[i].useremail) {
						content += '<input type="hidden" id="commentnum" value="'+result[i].commentnum+'"/>'
						content += '<div class="button">';
						content += 	'<button onclick="commentUpdate()"><img alt="trash" src="'+ctx+'/resources/images/pencil.svg">수정</button>';
						content += 	'<button onclick="commentDelete()"><img alt="pencil" src="'+ctx+'/resources/images/trash.svg">삭제</button>';
						content += '</div>'
					}
					content += 	'</div>';
					content += 	'<hr>';
					content += '</div>';
				}
				$(".detail-comment").append(content);
			}
		},
		error : function() {
			console.log("좋아요 로딩 ");
		}
	})
}

// 로드 될 때 리뷰 수정을 위해 모달에 미리 써놓기
// 로드할 떄 리뷰 좋아요 있는지 없는지 확인
// 로드할 때 리뷰 댓글 있는지 없는지 확인 있으면 직접 넣어주기
$(document).ready(() => {
	let reviewstar = $("#reviewstar").val();
	let reviewcontent = $("#reviewcontent").val();
	let reviewcontentlength = $("#reviewcontentlength").val();
	
	$(".star span").css("width",reviewstar * 2 * 10 + "%");
	$("#star").val(reviewstar * 2);
	$("#comment").val(reviewcontent);
	$(".comment_len").text(reviewcontentlength);
	
	$.ajax({
		url : ctx+"/review/reviewLikeOk",
		type : "get",
		data : {"reviewnum":$("#reviewnum").val(), "useremail":$("#useremail").val()},
		success : function(result) {
			if(result === "ok") {				
				$(".likebtn").css("color","rgb(255, 47, 110)");
				$(".likebtn svg path").attr("fill","#FF2F6E");
				$(".likebtn").addClass("ok");
			} else {
				$(".likebtn").css("color","#87898B");
				$(".likebtn svg path").attr("fill","#67686A");
				$(".likebtn").addClass("no");
			}
		},
		error : function() {
			console.log("좋아요 로딩 ");
		}
	})
	
	comment();
})

// 리뷰 좋아요 클릭
$(".likebtn").click(() => {
	// ajax로 보낸 데이터인 status에 따라 보고싶어요 추가인지 취소인지 구별
	let status = "";
	if($(".likebtn").hasClass("ok")) {
		// 이미 댓글 좋아요를 한 상태이면
		status = "like";
	} else {
		// 댓글 좋아요를 한 상태가 아니면
		status = "hate";
	}
	
	$.ajax({
		url : ctx+"/review/reviewLike",
		type : "get",
		data : {"status":status, "reviewnum":$("#reviewnum").val(), "useremail":$("#useremail").val()},
		success : function(result) {
			if(result === "ok") {				
				// status가 hate로 가서 보고싶어요를 추가한 상태
				$(".likebtn").css("color","rgb(255, 47, 110)");
				$(".likebtn svg path").attr("fill","#FF2F6E");	
				$(".likebtn").addClass("ok");	
				$(".likebtn").removeClass("no");
				$(".lCnt").text(Number($(".lCnt").text()) + 1);
			} else {
				// status가 like로 가서 보고싶어요를 취소한 상태
				$(".likebtn").css("color","#67686A");
				$(".likebtn svg path").attr("fill","#67686A");
				$(".likebtn").addClass("no");
				$(".likebtn").removeClass("ok");
				$(".lCnt").text(Number($(".lCnt").text()) - 1);
			}
		},
		error : function() {
			console.log("서버 ");
		}
	})
})

// 댓글 클릭
$(".commentbtn").click(() => {
	$(".comment_modal").fadeIn();
	$("#comment_comment").focus();
})

$(".x").click(() => {
	$(".comment_modal").fadeOut();
})

// 코멘트 모달 떠서 글자 한글자씩 입력했을 때
$("#comment_comment").keyup((e) => {
	let content = $("#comment_comment").val();
	
	if(content.length === 0 || content === "") {
		$(".comment_comment_len").text("0");
		$(".comment_comment_btn").css("background", "#F2F2F2");
		$(".comment_comment_btn").css("cursor", "default");
		$(".comment_comment_btn").attr("disabled","disabled");
	} else {
		$(".comment_comment_len").text(content.length);
		$(".comment_comment_btn").css("background", "rgb(255, 47, 110)");
		$(".comment_comment_btn").css("cursor", "pointer");
		$(".comment_comment_btn").removeAttr("disabled");
	}
})

// 리뷰 댓글 작성
$(".comment_comment_btn").click(() => {
	let reviewnum = $("#reviewnum").val();
	let useremail = $("#useremail").val();
	let commentcontent = $("#comment_comment").val();
	let content = "";
	
	$.ajax({
		url : ctx+"/review/comment",
		type : "POST",
		data : JSON.stringify({"reviewnum":reviewnum,
			"useremail":useremail,
			"commentcontent":commentcontent
		}),
		contentType:"application/json; charset=utf-8",
		dataType:"json",
		success : function(result) {
			$(".comment_modal").fadeOut();
			
			// 새로운 리뷰 댓글 그리기
			content += '<div class="comment">';
			content += 	'<div class="comment-head">';
			content += 		'<div class="head-1">';
			content += 			'<img src="'+ctx+'/resources/images/profile.png" alt="프로필">';
			content += 			'<span class="name">'+result.username+'</span>';
			content += 		'</div>';
			content += 		'<div class="head-2">';
			content += 			'<span class="date">'+result.commentdate+'</span>';
			content += 		'</div>';
			content += 	'</div>';
			content += 	'<div class="comment-content">';
			content += 		'<span>'+result.commentcontent+'</span>';
			if($("#useremail").val() === result.useremail) {
				content += '<input type="hidden" id="commentnum" value="'+result.commentnum+'"/>'
				content += '<div class="button">';
				content += 	'<button onclick="commentUpdate()"><img alt="trash" src="'+ctx+'/resources/images/pencil.svg">수정</button>';
				content += 	'<button onclick="commentDelete()"><img alt="pencil" src="'+ctx+'/resources/images/trash.svg">삭제</button>';
				content += '</div>'
			}
			content += 	'</div>';
			content += 	'<hr>';
			content += '</div>';

			$(".detail-comment").append(content);
			$(".cCnt").text(Number($(".cCnt").text()) + 1);
		},
		error : function() {
			console.log("서버 ");
		}
	})
})

// 리뷰 댓글 삭제
const commentDelete = () => {
	if(confirm("댓글을 삭제하시겠어요?")) {
		$.ajax({
			url : ctx+"/review/deleteComment",
			type : "get",
			data : {"commentnum":$("#commentnum").val()},
			success : function(result) {
				if(result) {
					$(".detail-comment").empty();
					// 모든 댓글 부분 다시 그리기
					comment();
					
					$(".cCnt").text(Number($(".cCnt").text()) - 1);
				} else {
					alert("댓글 삭제 실패하였습니다. 다시 시도해주세요");
				}
			},
			error : function() {
				console.log("서버 ");
			}
		})		
	}
}