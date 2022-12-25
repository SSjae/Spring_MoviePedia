// contextpath 꺼내오기
function getContextPath() {
	return sessionStorage.getItem("contextpath");
}

// login.jsp, join.jsp 유효성 검사
// 클래스 no, ok에 따라 svg 이미지
// keyup으로 한 글자씩 쓸때마다 유효성 검사
$("#form-bt").click(() => {
	if($("#useremail").val() === "") {
		alert("이메일을 입력해주세요")
		$("#useremail").focus();
		return false;
	}
	if($("#userpw").val() === "") {
		alert("비밀번호를 입력해주세요")
		$("#userpw").focus();
		return false;
	}
	if($("#username").val() === "") {
		alert("이름을 입력해주세요")
		$("#username").focus();
		return false;
	}
	if($("#useremail").hasClass("no")) {
		alert("이메일을 다시 확인해주세요.")
		$("#useremail").focus();
		return false;
	}
	if($("#userpw").hasClass("no")) {
		alert("비밀번호는 최소 8자리 이상이어야 합니다.")
		$("#userpw").focus();
		return false;
	}
	if($("#username").hasClass("no")) {
		alert("이름은 최소 2자리 이상이어야 합니다.")
		$("#username").focus();
		return false;
	}
	if($("#form-bt").hasClass("join-bt") && !$("#authnum").is("[readonly]")) {
		alert("인증을 먼저 완료해주세요.");
		$("#authnum").focus();
		return false;
	}
	if($("#form-bt").hasClass("join-bt") && $("input:checkbox[name='prefer']:checked").length < 2) {
		alert("선호하는 장르 2개 이상 체크해주세요.");
		return false;
	}
	
	// 선호 장르 한 문자열로 만들어 보내기
	let prefergenre = "";
	$("input:checkbox[name='prefer']:checked").each(function() {
		prefergenre += "," + this.value;
    })
    prefergenre = prefergenre.substr(1);
	$("#prefergenre").val(prefergenre);
	
	
})
// 이메일 유효성 검사
// 인증버튼 활성화/비활성화
const auth = (val) => {
	if(val === "auth") {
		$("#auth").css("background", "#FF2F62");
		$("#auth").css("cursor", "pointer");
		$("#auth").removeAttr("disabled");
	} else {
		$("#auth").css("background", "#F2F2F2");
		$("#auth").css("cursor", "default");
		$("#auth").attr("disabled","disabled");			
	}
}
$("#useremail").keyup(() => {
	let value = $("#useremail").val();
	let expect = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
	let add = (c) => {$("#useremail").addClass(c)};
	let remove = (c) => {$("#useremail").removeClass(c)};
	
	if(value !== "" && expect.test(value) == false) {
		auth("n");
		$("#useremail").css("border","1px solid rgb(245, 0, 0)");
		$(".useremail-text").show();
		$(".check-text").hide();
		add("no"); remove("ok");
	} else if(expect.test(value) == true) {
		if($("#useremail").hasClass("join")) {
			// join.jsp - email
			// 이메일 중복 검사
			var ctx = getContextPath();
			$.ajax({
				url : ctx+"/user/check",
				type : "get",
				data : {"useremail":value},
				success : function(result) {
					if(result === "OK") {
						auth("auth");
						$("#useremail").css("border","none");
						$(".useremail-text").hide();
						$(".check-text").hide();
						add("ok"); remove("no");
					} else {
						auth("n");
						$("#useremail").css("border","1px solid rgb(245, 0, 0)");
						$(".check-text").show();
						$(".useremail-text").hide();
						add("no"); remove("ok");
					}
				},
				error : function() {
					console.log("서버 ");
				}
			})
		} else if ($("#useremail").hasClass("findPw")) {
			// findPw.jsp - email
			// 이메일이 존재하는지 아닌지
			var ctx = getContextPath();
			$.ajax({
				url : ctx+"/user/check_findPw",
				type : "get",
				data : {"useremail":value},
				success : function(result) {
					if(result === "OK") {
						$("#useremail").css("border","none");
						$(".useremail-text").hide();
						$(".check-text").hide();
						add("ok"); remove("no");
					} else {
						$("#useremail").css("border","1px solid rgb(245, 0, 0)");
						$(".check-text").show();
						$(".useremail-text").hide();
						add("no"); remove("ok");
					}
				},
				error : function() {
					console.log("서버 ");
				}
			})
		} else {
			// login.jsp - email
			$("#useremail").css("border","none");
			$(".useremail-text").hide();
			$(".check-text").hide();
			add("ok"); remove("no");
		}
	} else {
		auth("n");
		$("#useremail").css("border","none");
		$(".useremail-text").hide();
		$(".check-text").hide();
		remove("no"); remove("ok");
	}
})
// 비밀번호 유효성 검사
$("#userpw").keyup(() => {
	let value = $("#userpw").val();
	let add = (c) => {$("#userpw").addClass(c)};
	let remove = (c) => {$("#userpw").removeClass(c)};
	if(value !== "" && value.length < 8) {
		$("#userpw").css("border","1px solid rgb(245, 0, 0)");
		$(".userpw-text").show();
		add("no"); remove("ok");
	} else if(value.length >= 8) {
		$("#userpw").css("border","none");
		$(".userpw-text").hide();
		add("ok"); remove("no");
	} else {
		$("#userpw").css("border","none");
		$(".userpw-text").hide();
		remove("no"); remove("ok");
	}
})
// 이름 유효성 검사
$("#username").keyup(() => {
	let value = $("#username").val();
	let add = (c) => {$("#username").addClass(c)};
	let remove = (c) => {$("#username").removeClass(c)};
	if(value !== "" && value.length < 2) {
		$("#username").css("border","1px solid rgb(245, 0, 0)");
		$(".username-text").show();
		add("no"); remove("ok");
	} else if(value.length >= 2) {
		$("#username").css("border","none");
		$(".username-text").hide();
		add("ok"); remove("no");
	} else {
		$("#username").css("border","none");
		$(".username-text").hide();
		remove("no"); remove("ok");
	}
})

// 이메일 인증 버튼
let authnum = 0;
$("#auth").click(() => {
	let useremail = $("#useremail").val();
	
	var ctx = getContextPath();
	$.ajax({
		url : ctx+"/user/auth",
		type : "get",
		data : {"useremail":useremail},
		success : function(result) {
			console.log(result);
			authnum = result;
			alert("인증번호가 발송되었습니다.");
			
			// 이메일 인증 버튼 없애고 인증번호 창 보이기
			$("#auth").hide();
			$(".authdiv").show();
			// email-input 비활성
			$("#useremail").attr("readonly","readonly");
			$("#useremail").css("color", "gray");
			$("#authnum").focus();
		},
		error : function() {
			console.log("서버 ");
		}
	})
})

// 인증 버튼
$("#authbt").click((e) => {
	e.preventDefault();
	let value = $("#authnum").val();
	
	if(value === "") {
		alert("인증 번호를 입력해주세요.")
		$("#authnum").focus();
	} else if (value === authnum) {
		alert("이메일 인증이 완료되었습니다.");
		// authnum-input 비활성
		$("#authnum").attr("readonly","readonly");
		$("#authnum").css("color", "gray");
		// auth-button 비활성
		$("#authbt").css("background", "#F2F2F2");
		$("#authbt").css("cursor", "default");
		$("#authbt").attr("disabled","disabled");
		$("#userpw").focus();
	} else {
		alert("이메일 인증에 실패하였습니다.");
		$("#authnum").focus();
	}
	console.log(authnum)
})

// admin.jsp
// 로딩바 보여주기
$(document).ready(function() {
	$('#loading').hide();
	$('#recent').click(function(){
    	$('#loading').show();
    	return true;
    });
});

// 로그인 때 비밀번호 찾기 모달 띄우고 닫기
$("#findPw").click(function(){
	$(".modal").fadeIn();
});
  
$(".x").click(() => {
	$(".modal").fadeOut();
})

// 비밀번호 재설정 이메일 인증 버튼
$(".findPw-auth").click((e) => {
	if($("#useremail").val() === "") {
		alert("이메일을 입력해주세요")
		$("#useremail").focus();
		return false;
	}
	if($("#useremail").hasClass("no")) {
		alert("이메일을 다시 확인해주세요.")
		$("#useremail").focus();
		return false;
	}
	
	let useremail = $("#useremail").val();
	var ctx = getContextPath();
	e.preventDefault();
	
	$.ajax({
		url : ctx+"/user/auth",
		type : "get",
		data : {"useremail":useremail},
		success : function(result) {
			console.log(result);
			authnum = result;
			alert("인증번호가 발송되었습니다.");
			
			// 이메일 인증 버튼 없애고 인증번호 창 보이기
			$(".findPw-auth").hide();
			$(".authdiv").show();
			// email-input 비활성
			$("#useremail").attr("readonly","readonly");
			$("#useremail").css("color", "gray");
			$("#authnum").focus();
		},
		error : function() {
			console.log("서버 ");
		}
	})
})

// 비밀번호 재설정 인증 버튼
$("#findPw-authbt").click((e) => {
	e.preventDefault();
	let value = $("#authnum").val();
	
	if(value === "") {
		alert("인증 번호를 입력해주세요.")
		$("#authnum").focus();
	} else if (value === authnum) {
		alert("이메일 인증이 완료되었습니다.");
		// authnum-input 비활성
		$("#authnum").attr("readonly","readonly");
		$("#authnum").css("color", "gray");
		// findPw-auth-button 비활성
		$("#findPw-authbt").css("background", "#F2F2F2");
		$("#findPw-authbt").css("cursor", "default");
		$("#findPw-authbt").attr("disabled","disabled");
		
		$(".findPw-pw").show();
		$(".findPw-bt").show();
		$("#userpw").focus();
	} else {
		alert("이메일 인증에 실패하였습니다.");
		$("#authnum").focus();
	}
	console.log(authnum)
})

// 비밀번호 재설정 유효성 검사
$("#userpw-re").keyup(() => {
	let value1 = $("#userpw").val();
	let value2 = $("#userpw-re").val();
	let add = (c) => {$("#userpw-re").addClass(c)};
	let remove = (c) => {$("#userpw-re").removeClass(c)};
	if(value2 !== "" && value1 !== value2) {
		$("#userpw-re").css("border","1px solid rgb(245, 0, 0)");
		$(".userpw-re-text").show();
		add("no"); remove("ok");
	} else if(value1 === value2) {
		$("#userpw-re").css("border","none");
		$(".userpw-re-text").hide();
		add("ok"); remove("no");
	} else {
		$("#userpw-re").css("border","none");
		$(".userpw-re-text").hide();
		remove("no"); remove("ok");
	}
})

// 비밀번호 재설정 버튼
$(".findPw-bt").click((e) => {
	if($("#userpw").val() === "") {
		alert("비밀번호를 입력해주세요")
		$("#userpw").focus();
		return false;
	}
	if($("#userpw-re").val() === "") {
		alert("비밀번호를 확인해주세요")
		$("#userpw-re").focus();
		return false;
	}
	if($("#userpw").hasClass("no")) {
		alert("비밀번호는 최소 8자리 이상이어야 합니다.")
		$("#userpw").focus();
		return false;
	}
	if($("#userpw-re").hasClass("no")) {
		alert("비밀번호가 동일하지 않습니다.")
		$("#userpw-re").focus();
		return false;
	}
	if($("#userpw").val() !== $("#userpw-re").val()) {
		alert("비밀번호가 동일하지 않습니다.")
		$("#userpw-re").focus();
		return false;
	}

	var cpx = getContextPath();
	e.preventDefault();
	// ajax로 비밀번호 재설정 후 창 닫기
	let useremail = $("#useremail").val();
	let userpw = $("#userpw").val();
	$.ajax({
		url: cpx+"/user/findPw",
		type: "post",
		data: {"useremail":useremail, "userpw":userpw},
		success : function(result) {
			if(result === "success") {
				alert("비밀번호가 재설정 되었습니다.");
				window.close();
			} else {
				alert("다시 시도해주세요.");
				history.go(0);
			}
		},
		error : function() {
			console.log("서버 ");
		}
	})
})
