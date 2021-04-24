$(function() {

	// var user_id = $("#object_user_phone").text();
	// var user_id = "18870373508";
	LeaveMessage();
	pictureAjax();
	//图片ajax传输
	function pictureAjax(){
		$.ajax({
			//请求方式
			type : "GET",
			//请求的媒体类型
			//contentType: "application/json;charset=UTF-8",
			//请求地址
			url : "/share/content/getElseContent",
			//数据，json字符串
			 data : {id:"123"},
			dataType:"json",
			//请求成功
			success : function(data) {
				if(null != data && "" != data){
					var arr = [];
					for(var item in data){
						arr.push(data[item]);
					}
					//var inf = '';
					/*<![CDATA[*/
					for (var i = 0; i < arr.length; i++) {
						/*]]>*/
						console.log(arr[i]);
						var div = $('<div class="infoItem"><img src="/share/pictures/' +
							arr[i].image_file+'" class="info-picture-img" ><div class="info-picture-content"><div class="info-picture-love">' +
							'<i class="fa fa-heart fa-2x" aria-hidden="true"></i><i class="fa fa-heart-o fa-2x" aria-hidden="true"></i><span class="picture-love-num">'+
							arr[i].content_like +'</span><span class="like_status" style="display: none;">' +
							arr[i].like_status+'</span></div></div><span class="info_picture_id" style="display: none">' +
							arr[i].content_id+'</span></div>');
						$(".infoItems").append(div);

						waterFall();
					}
				}else{
					$(".infoItems").append('<div class="baocuo" style="width: 200px;margin-left: auto;margin-right: auto; background-color: #1ABC9C;font-size: 12px; <!-- display: none; -->"> <h1 style="font-size: 15px;color: white;line-height: 20px;">' +
						'此用户分享内容为空！</h1> </div>');
				}


				dianZan();
				quZan();
				$(".infoItem>.info-picture-img").click(function(){

					var id = $(this).parent().children(".info_picture_id").text();
					//详情ajax跳转
					var url = "/share/content/toImageDetail/" + id;
					window.location.href = url;

				})
				panDuan();
			},
			//请求失败，包含具体的错误信息
			error : function(e){
				console.log(e.status);
				console.log(e.responseText);
			}
		});
	}


	//状态
	function panDuan(){
		$(".infoItem>.info-picture-content").mouseenter(function() {

			var p = $(this).children(".info-picture-love").children(".like_status").text();
			var this_s=this;
			if (p >0) {
				$(this_s).children(".info-picture-love").children(".fa-heart-o").hide();
				$(this_s).children(".info-picture-love").children(".fa-heart").show();

			}else
			{
				$(this_s).children(".info-picture-love").children(".fa-heart-o").show();
				$(this_s).children(".info-picture-love").children(".fa-heart").hide();

			}
		})
	}

	function dianZan(){
		$(".info-picture-love>.fa-heart-o").click(function(){
			var info_man_id=$("#object_user_phone").text();

			var info_id=$(this).parent().parent().parent().children(".info_id").text();

			var content_i=$(this).parent().children(".picture-love-num").text();
			var this_s=this;
			var p = $(this).parent().children(".like_status").text();
			alert(p);
			if(p==0)
			{
				$.ajax({
					//请求方式
					type : "post",
					//请求的媒体类型
					//contentType: "application/json;charset=UTF-8",
					//请求地址
					url : "/share/likeList/addLike",
					//数据，json字符串
					data : {content_id:info_id,
						content_like:content_i, liked_id:info_man_id},
					dataType:"json",
					//请求成功
					success : function(data) {
						if(data.result=="exist"){
							alert("您已经点过赞了");
							$(this_s).hide();
							$(this_s).parent().children(".fa-heart").show();
							//$(this).parent().children(".picture-love-num").text(parseInt($(this).parent().children(".picture-love-num").text())+1);
							$(this_s).parent().children(".like_status").text(1);
						}else if(data.result == "success"){
							content_i++;
							alert("点赞成功");
							$(this_s).hide();
							$(this_s).parent().children(".fa-heart").show();
							$(this_s).parent().children(".picture-love-num").text(content_i);
							$(this_s).parent().children(".like_status").text(1);
						}else{
							alert("出现问题");
						}
						// alert("成功");

					},
					//请求失败，包含具体的错误信息
					error : function(e){
						console.log(e.status);
						console.log(e.responseText);
					}
				});
				// $(this).hide();
				//
				// $(this).parent().children(".fa-heart").show();
				//
				// $(this).parent().children(".picture-love-num").text(parseInt($(this).parent().children(".picture-love-num").text())+1);
				//
				// $(this).parent().children(".like_status").text(1);
			}

		})

	}
	function quZan(){
		$(".info-picture-love>.fa-heart").click(function(){
			var info_man_id=$("#object_user_phone").text();

			var info_id=$(this).parent().parent().parent().children(".info_id").text();

			var content_i=$(this).parent().children(".picture-love-num").text();
			var this_s=this;
			var p = $(this).parent().children(".like_status").text();
			alert(p);
			if(p>0)
			{
				$.ajax({
					//请求方式
					type : "post",
					//请求的媒体类型
					//contentType: "application/json;charset=UTF-8",
					//请求地址
					url : "/share/likeList/reduceLike",
					//数据，json字符串
					data : {content_id:info_id,
						content_like:content_i, liked_id:info_man_id},
					dataType:"json",
					//请求成功
					success : function(data) {
						if(data.result=="error"){
							alert("您取消点赞出现问题");
							$(this_s).hide();
							$(this_s).parent().children(".fa-heart-o").show();
							$(this_s).parent().children(".like_status").text(0);
							//$(this).parent().children(".picture-love-num").text(parseInt($(this).parent().children(".picture-love-num").text())+1);
						}else if(data.result == "success"){
							content_i--;
							alert("取消点赞成功");
							$(this_s).hide();
							$(this_s).parent().children(".fa-heart-o").show();
							$(this_s).parent().children(".picture-love-num").text(content_i);
							$(this_s).parent().children(".like_status").text(0);
						}else{
							alert("出现问题");
						}
					},
					//请求失败，包含具体的错误信息
					error : function(e){
						console.log(e.status);
						console.log(e.responseText);
					}
				});
				// $(this).hide();
				//
				// $(this).parent().children(".fa-heart-o").show();
				//
				// $(this).parent().children(".picture-love-num").text(parseInt($(this).parent().children(".picture-love-num").text())+1);
				//
				// $(this).parent().children(".like_status").text(0);
			}

		})
	}
//留言信息
	function LeaveMessage(){
		$.ajax({
			//请求方式
			type : "GET",
			//请求的媒体类型
			//contentType: "application/json;charset=UTF-8",
			//请求地址
			url : "/share/leaveMessage/getOtherMessage",
			//数据，json字符串
			data : {action:"get"},
			dataType:"json",
			//请求成功
			success : function(data) {
				if(null != data && "" != data){
					var arr = [];
					for(var item in data){
						arr.push(data[item]);
					}
					//var inf = '';
					/*<![CDATA[*/
					for (var i = 0; i < arr.length; i++) {
						/*]]>*/
						console.log(arr[i]);
						var leave = $('<div class="Leave_A_Message"><div class="Leave_man"><img src="/share/pictures/' +
							arr[i].image_file+'" ><span>' +
							arr[i].username+'</span><span class="Leave_A_Message_time">' +
							arr[i].message_date+'</span></div><div class="leave_content"><p class="messagecontent">' +
							arr[i].message_content+'</p></div></div>');
						$(".leavegroup").append(leave);
					}
				}else{
					$(".leavegroup").append('<div class="baocuo" style="width: 200px;margin-left: auto;margin-right: auto; background-color: #1ABC9C;font-size: 12px; <!-- display: none; -->"> <h1 style="font-size: 15px;color: white;line-height: 20px;">此用户留言为空！</h1> </div>');
				}


			},
			//请求失败，包含具体的错误信息
			error : function(e){
				console.log(e.status);
				console.log(e.responseText);
			}
		});
	}
	$(".UlInfomation>ul>li").click(function() {
		var i = $(this).index();
		$(".container>ul>li").eq(i).show();
		$(".container>ul>li").eq(i).siblings().hide();
		console.log(i);
		$(this).siblings().removeClass("one");
		$(this).addClass("one");

	});
	
	//已发布图片瀑布流
	// 瀑布函数
	function waterFall() {
	    // 1- 确定列数  = 页面的宽度 / 图片的宽度
		var box = $("#box2");
		var items =$(".infoItem");
		// 定义每一列之间的间隙 为10像素
		var gap = 10;
	    var pageWidth = $("#box2").width();
	    var itemWidth = items[0].offsetWidth;
	    var columns = parseInt(pageWidth / (itemWidth + gap));
	    var arr = [];
	    for (var i = 0; i < items.length; i++) {
	        if (i < columns) {
	            // 2- 确定第一行
	            items[i].style.top = 0;
	            items[i].style.left = (itemWidth + gap) * i + 'px';
	            arr.push(items[i].offsetHeight);
		
	        } else {
	            // 其他行
	            // 3- 找到数组中最小高度  和 它的索引
	            var minHeight = arr[0];
	            var index = 0;
	            for (var j = 0; j < arr.length; j++) {
	                if (minHeight > arr[j]) {
	                    minHeight = arr[j];
	                    index = j;
	                }
	            }
	            // 4- 设置下一行的第一个盒子位置
	            // top值就是最小列的高度 + gap
	            items[i].style.top = arr[index] + gap + 'px';
	            // left值就是最小列距离左边的距离
	            items[i].style.left = items[index].offsetLeft + 'px';
		
	            // 5- 修改最小列的高度 
	            // 最小列的高度 = 当前自己的高度 + 拼接过来的高度 + 间隙的高度
	        }
			 arr[index] = arr[index] + items[i].offsetHeight + gap;
	    }
	};



	//网页尺码改变时会调用一次瀑布流
	window.onresize = function() {
	    waterFall();
	};
	//解决bug
	$("body").mouseover(function(){
		waterFall();
	});

	//发布留言框js
	$("body").delegate(".comment", "propertychange input", function() {
		// 判断是否输入了内容
		if ($(this).val().length > 0) {
			$(".send").prop("disabled", false);
		} else {

			$(".send").prop("disabled", true);
	
		}
		
	});
	$(".send").click(function() {
		// 监听内容的时时输入
		//拿到用户输入的内容

		//传给你
		$.ajax({
			//请求方式
			type : "POST",
			//请求的媒体类型
			// contentType: "application/json;charset=UTF-8",
			//请求地址
			url : "/share/leaveMessage/insertLeaveMessage",
			//数据，json字符串
			data : $("#leave_message").serialize(),
			dataType:"json",
			//请求成功
			success : function(data) {
				if (data.success == "success"){
					alert("留言成功");
					history.go(0);
				}else{
					alert("留言失败");
				}
			},
			//请求失败，包含具体的错误信息
			error : function(e){
				console.log(e.status);
				console.log(e.responseText);
			}
		});
		var leave = createEle(text);
		$(".leavegroup").append(leave);
		$(".comment").val('');
		$(".send").prop("disabled", true);
	})
	$(".first_page").click(function(){
		var url = "/share/index";
		window.location.href=url;
	})
	// 关注点击
	$(".add_attention>.fa-plus-square-o").click(function() {
		// alert("关注");
		// alert("asfa");
		var followed_id= $("#object_user_phone").text();
		// var att_id=$(".att_id").text();
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

		var followed_id= $("#object_user_phone").text();
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
	$(".login").click(function(){
		var url = "/share/user/toPersonInfo";
		window.location.href=url;
	})
});
