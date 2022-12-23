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