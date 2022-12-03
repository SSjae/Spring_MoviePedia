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
})
// 이메일 유효성 검사
$("#useremail").keyup(() => {
	let value = $("#useremail").val();
	let expect = /^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/;
	let add = (c) => {$("#useremail").addClass(c)};
	let remove = (c) => {$("#useremail").removeClass(c)};
	if(value !== "" && expect.test(value) == false) {
		$("#useremail").css("border","1px solid rgb(245, 0, 0)");
		$(".useremail-text").show();
		$(".check-text").hide();
		add("no"); remove("ok");
	} else if(expect.test(value) == true) {
		// 이메일 중복 검사
		var ctx = getContextPath();
		$.ajax({
			url : ctx+"/user/check",
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
					add("no"); remove("ok");
				}
			},
			error : function() {
				console.log("서버 ");
			}
		})
	} else {
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

// admin.jsp
// 로딩바 보여주기
$(document).ready(function() {
	$('#loading').hide();
	$('#recent').click(function(){
    	$('#loading').show();
    	return true;
    });
});