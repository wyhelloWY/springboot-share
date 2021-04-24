$(function(){

	$(".first_page").click(function(){
		var url = "/share/index";
		window.location.href=url;
	})
	$(".login").click(function(){
		var url = "/share/user/toPersonInfo";
		window.location.href=url;
	})

	//点赞
	$(".fa-heart-o").click(function() {


		var content_id = $("#content_id").text();
		var content_like = $("#content_like").text();
		var announcer_id = $("#announcer_id").text();
		var p = $(".fav_id").text();
		$.ajax({
			//请求方式
			type : "post",
			//请求的媒体类型
			//contentType: "application/json;charset=UTF-8",
			//请求地址
			url : "/share/likeList/addLike",
			//数据，json字符串
			data : {content_id:content_id,
				content_like:content_like, liked_id:announcer_id},
			dataType:"json",
			//请求成功
			success : function(data) {
				if(data.result=="exist"){
					alert("您已经点过赞了");
					$(".fa-heart-o").hide();
					$(".fa-heart").show();
					//$(this).parent().children(".picture-love-num").text(parseInt($(this).parent().children(".picture-love-num").text())+1);
					$(".fav_id").text(1);
				}else if(data.result == "success"){
					content_like++;
					alert("点赞成功");
					$(".fa-heart-o").hide();
					$(".fa-heart").show();
					$("#content_like").text(content_like);
					$(".fav_id").text(1);
				}else{
					alert("出现问题");
				}
				// alert("成功");

			},
			//请求失败，包含具体的错误信息
			error : function(e){
				alert("请勿重复操作");
				console.log(e.status);
				console.log(e.responseText);
			}
		});
		
	})

	//取消
	$(".info-picture-love>.fa-heart").click(function() {
		var content_id = $("#content_id").text();
		var content_like = $("#content_like").text();
		var announcer_id = $("#announcer_id").text();
		var p = $(".fav_id").text();
		// alert(p);
		// $(".fa-heart").hide();
		// $(".fa-heart-o").show();
		// $(".picture-love-num").text(parseInt($(".picture-love-num").text())-1)
		// $(".fav_id").text(0);
		$.ajax({
			//请求方式
			type : "post",
			//请求的媒体类型
			//contentType: "application/json;charset=UTF-8",
			//请求地址
			url : "/share/likeList/reduceLike",
			//数据，json字符串
			data : {content_id:content_id,
				content_like:content_like, liked_id:announcer_id},
			dataType:"json",
			//请求成功
			success : function(data) {
				if(data.result=="error"){
					alert("您取消点赞出现问题");
					$(".fa-heart").hide();
					$(".fa-heart-o").show();
					$(".fav_id").text(0);
					//$(this).parent().children(".picture-love-num").text(parseInt($(this).parent().children(".picture-love-num").text())+1);
				}else if(data.result == "success"){
					content_like--;
					alert("取消点赞成功");
					$(".fa-heart").hide();
					$(".fa-heart-o").show();
					$("#content_like").text(content_like);
					$(".fav_id").text(0);
				}else{
					alert("出现问题");
				}
			},
			//请求失败，包含具体的错误信息
			error : function(e){
				alert("请勿重复操作");
				console.log(e.status);
				console.log(e.responseText);
			}
		});

	})
	//状态
	$(".info-picture-content").mouseenter(function() {

		var j = $(".fav_id").text();
		// alert(j);
		if (j>0){

			$(".fa-heart-o").hide();
			$(".fa-heart").show();

		}else{

			$(".fa-heart").hide();
			$(".fa-heart-o").show();

		}
	})

	// 关注点击
	$(".add_attention>.fa-plus-square-o").click(function() {
		// alert("关注");
		// alert("asfa");
		var followed_id= $("#announcer_id").text();
		var att_id=$(".att_id").text();
		$.ajax({
			//请求方式
			type : "post",
			//请求的媒体类型
			//contentType: "application/json;charset=UTF-8",
			//请求地址
			url : "/share/attention/addAttention",
			//数据，json字符串
			data : {followed_id:followed_id},
			dataType:"json",
			//请求成功
			success : function(data) {
				if(data.result=="success"){

					$(".fa-plus-square-o").hide();
					$(".fa-plus-square").show();
					$(".att_span").text("已关注");
					$(".att_id").text(1);
					alert("关注成功");
				}else{
					alert("关注出现问题");
				}
				// alert("成功");
			},
			//请求失败，包含具体的错误信息
			error : function(e){
				console.log(e.status);
				console.log(e.responseText);
			}
		});

	})
	//取消关注
	$(".add_attention>.fa-plus-square").click(function(){

		var followed_id= $("#announcer_id").text();
		$.ajax({
			//请求方式
			type : "post",
			//请求的媒体类型
			//contentType: "application/json;charset=UTF-8",
			//请求地址
			url : "/share/attention/deleteAttention",
			//数据，json字符串
			data : {followed_id:followed_id},
			dataType:"json",
			//请求成功
			success : function(data) {
				if(data.result=="success"){

					$(".fa-plus-square-o").show();
					$(".fa-plus-square").hide();
					$(".att_span").text("关注");
					$(".att_id").text(0);
					alert("取消关注成功");
				}else{
					alert("取消关注出现问题");
				}
				// alert("成功");
			},
			//请求失败，包含具体的错误信息
			error : function(e){
				console.log(e.status);
				console.log(e.responseText);
			}
		});
	})
	//关注
	$("body").mouseover(function(){

		var p=$(".att_id").text();
		if(p>0)
		{
			$(".fa-plus-square-o").hide();
			$(".fa-plus-square").show();
			$(".att_span").text("已关注");


		}

	})

})