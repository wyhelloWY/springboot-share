$(function() {

	var activity_id = $("#one_activity_id").text();
//图片ajax传输
	$.ajax({
		//请求方式
		type : "GET",
		//请求的媒体类型
		//contentType: "application/json;charset=UTF-8",
		//请求地址
		url : "/share/content/toGetActivityContent",
		//数据，json字符串
		data : {id:activity_id},
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
					var div = $('<div class="item"><img src="/share/pictures/'+
						arr[i].image_file+'" class="picture-img" ><div class="picture-content"><div class="info"><img src="/share/pictures/'+
						arr[i].avatar_image+'"class="infoImg"><span class="picture-name">' +
						arr[i].user_name+ '</span><span class="info_man_id" style="display: none;">' +
						arr[i].announcer_id+'</span></div><div class="picture-love"><i class="fa fa-heart-o fa-2x" aria-hidden="true" ></i><i class="fa fa-heart fa-2x" aria-hidden="true" ></i><span class="picture-love-num">' +
						arr[i].content_like + '</span><span class="like_status" style="display: none;">' +
						arr[i].like_status+'</span></div></div><span class="info_id">'+
						arr[i].content_id+'</span><span class="info_activity_id" style="display: none;">'+
						arr[i].content_activity_id+'</span></div>');
					$("#box3").append(div);
					//inf+= "<img th:src=\"@{/sharestyle/img/eve.jpg}\" class=\"picture-img\"> <div class=\"picture-content\"><div id=\"\" class=\"info\"><img th:src=\"@{/sharestyle/img/eve.jpg}\" class=\"infoImg\">+ <span class=\"picture-name\">志鹏</span></div><div class=\"picture-love\"><i class=\"fa fa-heart-o fa-2x\" aria-hidden=\"true\"></i> <i class=\"fa fa-heart fa-2x\" aria-hidden=\"true\"></i><span class=\"picture-love-num\">10000</span></div></div><span class=\"info_id\"></span></div>"

					waterFall();
				}
			}else{
				$("#box3").append('<div class="baocuo" style="width: 200px;margin-left: auto;margin-right: auto; background-color: #1ABC9C;font-size: 12px; <!-- display: none; -->"> ' +
					'<h1 style="font-size: 15px;color: white;line-height: 20px;">此活动内容为空！</h1> </div>');
			}

			toElseInfo();
			//$("#box").html(inf);
			//console.log(data.contentLists);
			//点击爱心和数量变化
			dianJi();
			quXiao();

			$(".item>.picture-img").click(function(){
				//alert("年后");
				var id = $(this).parent().children(".info_id").text();
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
	function waterFall() {
		// 1- 确定列数  = 页面的宽度 / 图片的宽度
		var box = $("#box3");
		var items = $(".item");
		// 定义每一列之间的间隙 为10像素
		var gap = 10;
		var pageWidth = $("#box3").width();
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


	//点赞功能
	//点赞
	//
	function dianJi(){
		$(".picture-love>.fa-heart-o").click(function(){
			var q=$(this).parent().children(".like_status").text();
			var this_s=this;
			var aact_id = $("#this_activity_id").text();

			//点赞数量
			var content_i = $(this).parent().children(".picture-love-num").text();
			var info_id=$(this).parent().parent().parent().children(".info_id").text();
			var info_act_id=$(this).parent().parent().parent().children(".info_activity_id").text();
			// //获取发布者id
			var info_man_id=$(this).parent().parent().children(".info").children(".info_man_id").text();

			if (q==0){
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
						alert("请勿重复操作");
						console.log(e.status);
						console.log(e.responseText);
					}
				});
			}


		})
	}
	function quXiao(){
		$(".picture-love>.fa-heart").click(function(){
			var q=$(this).parent().children(".like_status").text();
			var this_s = this;
			var content_i = $(this).parent().children(".picture-love-num").text();
			var info_id=$(this).parent().parent().parent().children(".info_id").text();
			var info_man_id=$(this).parent().parent().children(".info").children(".info_man_id").text();
			if (q>0){
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
						alert("请勿重复操作");
						console.log(e.status);
						console.log(e.responseText);
					}
				});
			}

			//alert(i);
			//ajax
		})
	}
	function panDuan() {
		$(".item>.picture-content").mouseenter(function() {

			var p = $(this).children(".picture-love").children(".like_status").text();
			var this_s=this;
			if (p >0) {
				$(this_s).children(".picture-love").children(".fa-heart-o").hide();
				$(this_s).children(".picture-love").children(".fa-heart").show();

			}else
			{
				$(this_s).children(".picture-love").children(".fa-heart-o").show();
				$(this_s).children(".picture-love").children(".fa-heart").hide();

			}
		})

	}
	function toElseInfo(){
		//点击头像进入他人主页 且用户数据传输!
		$(".item>.picture-content>.info>.infoImg").click(function(){

			var id=$(this).parent().parent().parent().find(".info_man_id").text();
			var url = "/share/user/toElseDetail/" + id;
			window.location.href = url;
		})
	}
	//网页尺码改变时会调用一次瀑布流
	window.onresize = function() {
		waterFall();
	};

	//解决bug
	$("body").mouseover(function() {
		waterFall();
	});

	$(".first_page").click(function(){
		var url = "/share/index";
		window.location.href=url;
	})
	$(".login").click(function(){
		var url = "/share//user/toPersonInfo";
		window.location.href=url;
	})


})
