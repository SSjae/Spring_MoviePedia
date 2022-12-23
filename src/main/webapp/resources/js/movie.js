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



// 비슷한 장르 영화 더보기
$(document).ready(() => {
	// 전체 영화 수
	
	// 조회 인덱스
	let startIndex = 1;
	let stepIndex = 15;
	
	
	const similars = (startIndex) => {
		let endIndex = startIndex + stepIndex - 1;
		$.ajax({
			url : ctx+"/movie/similars",
			type : "get",
			data : {"movieCode":$("#moviecode").val(), "startIndex":startIndex, "endIndex":endIndex},
			success : function(result) {
				console.log(result);
			},
			error : function() {
				console.log("서버 ");
			}
		})
	}
	similars(startIndex);
})