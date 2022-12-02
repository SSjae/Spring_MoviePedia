// login.jsp

// admin.jsp
// 로딩바 보여주기
$(document).ready(function() {
	$('#loading').hide();
	$('#recent').click(function(){
    	$('#loading').show();
    	return true;
    });
});