/**
 * 
 */
$(document).ready(function() {
	$('#checkDupIdBtn').on('click',function() {
		var userId = $('#userId').val();
		console.log('call');
		
		if(userId === '') {
			alert('ID를 입력하세요.');
			$('#userId').focus();
			return;
		}
		
		$.ajax({
			url:"http://localhost:8001/checkdupid?userId=" + userId,
			type:"GET",
			dataType:"text",
			processData:false,
			contentType:true,
			success: function(data) {
				if(data === "true") {
					console.log(data);
					alert('이미 사용중인 아이디입니다.');
					$("#isCheckId").val("unChecked");
					$('#userId').val("");
				}else if(data === "false") {
					console.log(data);
					$("#isCheckId").val("checked");
					alert('사용가능한 아이디 입니다.');
				}
			},
			error: function() {
				console.log(userId);
				alert("error.");
			}
		});
	});
	
	$("#registerForm").on("submit",function() {
		if($("#isCheckId").val() === "unChecked") {
			alert("중복확인 해주세요.");
			event.preventDefault();
			$("isCheckId").focus();
		}
	});
});