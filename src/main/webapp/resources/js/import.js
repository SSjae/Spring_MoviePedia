// contextpath 꺼내오기
function getContextPath() {
	return sessionStorage.getItem("contextpath");
}
let ctx = getContextPath();

// 자동 완성
$("#search").autocomplete({
	source: (request, response) => {
		$.ajax({
			url: ctx+"/movie/autocomplete",
			type : "POST",
			dataType: "json",
			data: {
	            //request.term >> 이거 자체가 text박스내에 입력된 값이다.
				searchValue: request.term
			},
			success: (data) => {
            	//return 된놈을 response() 함수내에 다음과 같이 정의해서 뽑아온다.
				response(
					$.map(data.resultList, (item) => {
						return {
                			//label : 화면에 보여지는 텍스트
                			//value : 실제 text태그에 들어갈 값
							label: item.movietitle,
							value: item.movietitle
						}
					})
				);
			}
		});
	},
    focus: (event, ui) => {
      return false;
    },
    select: (event, ui) => {},
    minLength: 1,
    delay: 100,
    autoFocus: false,
});