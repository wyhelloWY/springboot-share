$(function() {

	//图片ajax传输
	$.ajax({
		//请求方式
		type : "GET",
		//请求的媒体类型
		//contentType: "application/json;charset=UTF-8",
		//请求地址
		url : "/share/content/getContentAll",
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
					// console.log(arr[i]);
					var div = $('<div class="item"><img src="/share/pictures/'+
						arr[i].image_file+'" class="picture-img" ><div class="picture-content"><div class="info"><img src="/share/pictures/'+
						arr[i].avatar_image+'"class="infoImg"><span class="picture-name">' +
						arr[i].user_name+ '</span><span class="info_man_id" style="display: none;">' +
						arr[i].announcer_id+'</span></div><div class="picture-love"><i class="fa fa-heart-o fa-2x" aria-hidden="true" ></i><i class="fa fa-heart fa-2x" aria-hidden="true" ></i><span class="picture-love-num">' +
						arr[i].content_like + '</span><span class="like_status" style="display: none;">' +
						arr[i].like_status+'</span></div></div><span class="info_id">'+
						arr[i].content_id+'</span><span class="info_activity_id" style="display: none;">'+
						arr[i].content_activity_id+'</span></div>');
					$("#box").append(div);
					//inf+= "<img th:src=\"@{/sharestyle/img/eve.jpg}\" class=\"picture-img\"> <div class=\"picture-content\"><div id=\"\" class=\"info\"><img th:src=\"@{/sharestyle/img/eve.jpg}\" class=\"infoImg\">+ <span class=\"picture-name\">志鹏</span></div><div class=\"picture-love\"><i class=\"fa fa-heart-o fa-2x\" aria-hidden=\"true\"></i> <i class=\"fa fa-heart fa-2x\" aria-hidden=\"true\"></i><span class=\"picture-love-num\">10000</span></div></div><span class=\"info_id\"></span></div>"

					waterFall();
				}
			}else{
				$("#box").append('<div class="baocuo" style="width: 200px;margin-left: auto;margin-right: auto; background-color: #1ABC9C;font-size: 12px; <!-- display: none; -->"> <h1 style="font-size: 15px;color: white;line-height: 20px;">无更多内容！</h1> </div>');
			}

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
			toElseInfo();

		},
		//请求失败，包含具体的错误信息
		error : function(e){
			console.log(e.status);
			console.log(e.responseText);
		}
	});

	//活动传输
	$.ajax({
		//请求方式
		type : "GET",
		//请求的媒体类型
		//contentType: "application/json;charset=UTF-8",
		//请求地址
		url : "/share/activity/getFourActivity",
		//数据，json字符串
		//data : {action:"get"},
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
					// console.log(arr[i]);
					var div = $('<div class="activity"><div class="activity-fabu"><span>发布者</span><p class="activity-fabu-people">' +
						arr[i].user_name+'</p><span>日期</span><p class="activity-fabu-date">' +
						arr[i].activity_date +'</p></div><img src="/share/pictures/' +
						arr[i].image_file +'" class="activity-img"><div id="activity-title"><span>活动标题</span><span class="activity-title" id="">' +
						arr[i].activity_name+'</span></div><div id="activity_content"><span>活动内容</span><p class="activity_content">' +
						arr[i].activity_content+'</p></div><span class="act_id" style="display:none">' +
						arr[i].activity_id +'</span></div>');
					$("#activityAll").append(div);
					// waterFall();
				}
			}else{
				$("#activityAll").append('<div class="baocuo" style="width: 200px;margin-left: auto;margin-right: auto; background-color: #1ABC9C;font-size: 12px; <!-- display: none; -->"> ' +
					'<h1 style="font-size: 15px;color: white;line-height: 20px;">无更多活动！</h1> </div>');
			}



			//这个是点击四个活动中的一个活动然后传给你该活动的id 进行页面跳转
			$(".guding>.activity").click(function(){
				//获取点击的活动id 模拟ajax
				var act_id=$(this).children(".act_id").text();
				var url = "/share/activity/toActivityDetail/"+act_id;
				// alert(act_id);
				window.location.href = url;


			})

		},
		//请求失败，包含具体的错误信息
		error : function(e){
			console.log(e.status);
			console.log(e.responseText);
		}
	});
	//
	//点赞
	//
	function dianJi(){
		$(".picture-love>.fa-heart-o").click(function(){
            var q=$(this).parent().children(".like_status").text();
            var this_s=this;
			var aact_id = $("#this_activity_id").text();
			var content_i = $(this).parent().children(".picture-love-num").text();
			var info_id=$(this).parent().parent().parent().children(".info_id").text();
			var info_act_id=$(this).parent().parent().parent().children(".info_activity_id").text();
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
                        }else if(data==null||""==data){
                            alert("请勿重复操作");
                        }else{
							alert("点赞出现问题");
						}
                        // alert("成功");

                    },
                    //请求失败，包含具体的错误信息
                    error : function(e){
                        console.log(e.status);
                        console.log(e.responseText);
						alert("请勿重复操作");
                    }
                });
            }


		})
	}
	//点赞
	function quXiao(){
		$(".picture-love>.fa-heart").click(function(){
            var q=$(this).parent().children(".like_status").text();
            var this_s = this;
			// alert("年后");
			// $(this).hide();
			// $(this).parent().children(".fa-heart-o").show();
			// $(this).parent().children(".picture-love-num").text($(this).parent().children(".picture-love-num").text()-1);
			//var i = $(this).parent().children(".picture-love-num").text();
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
                        console.log(e.status);
                        console.log(e.responseText);
						alert("请勿重复操作");
                    }
                });
            }

			//alert(i);
			//ajax
		})
	}
	//点击爱心和数量变化


	//轮播图代码
	$(".nav>li").click(function() {
		$(this).addClass("one")
		$(this).siblings().removeClass("one")
		var index = $(this).index();
		var li = $(".pic>li").eq(index)
		li.siblings().removeClass("show")
		li.addClass("show")
	})
	var j=0;
	function selfChangeImage() 
	{   
			if(j<3)
			{
				$(".nav>li").eq(j).addClass("one");
				console.log($(".nav>li").eq(j).addClass("one"));
				$(".nav>li").eq(j).siblings().removeClass("one");
				$(".pic>li").eq(j).siblings().removeClass("show");
				$(".pic>li").eq(j).addClass("show");
				j++;
			}else
			{
				j=0;
			}
			setTimeout(selfChangeImage, 2000);
	}
	 selfChangeImage();
	
	
	
	
	////模拟登陆成功
	//$(".login").children(".login_span").text('');

	// var avatar_image=/*[[${session.avatar_image}]]*/;
	//  $(".login_span").append($('<img src="/share/pictures/${session.avatar_image})" class="firstPageInfoImg"> '));

	var i =0;
	$(".login").click(function(){
		if(i==0)
		{
			$(".login_list").show(1000);
			i=1;
		}else
		{
			$(".login_list").hide(1000)
			i=0;
		}
	})
	
	//四个活动数据接收 ajax
	// var Aci = $(".activity");
	// var data = 123;
	// $.ajax({
	// 	type: "post",
	// 	url: 000,
	// 	data: data,
	// 	success: function(res) {
	// 		//需要res数据传二维数组 分成四个活动的数据
	//
	// 		//获取四个活动的的活动内容 你需要通过循环别别将数据传进去以下类同
	// 		var a1 = $(".activity-fabu>.activity-title")
	// 		//获取四个活动的发布者
	// 		var a2 = $(".activity-fabu>.activity-fabu-people");
	// 		//四个活动的日期
	// 		var a3 = $(".activity-fabu>.activity-fabu-date");
	// 	},
	// 	error: function(res) {
	//
	// 	}
	// })


	//这个是点击四个活动中的一个活动然后传给你该活动的id 进行页面跳转

	//查看更多活动跳转
	$(".btnact").click(function() {
			window.location.href ="Activities.html";
	});
	
	

	//此为发布操作
	//发布按钮下拉列表操作
	var p=0;
	$(".fabu").click(function(){
		if(p==0)
		{
			$(".fabu_list").show(1000);
			p=1;
		}else
		{
			$(".fabu_list").hide(1000);
			p=0;
		}
	})
	
	$(".fabu_list").mouseleave(function(){
			$(".fabu_list").hide(1000);
		})
		$(".login_list").mouseleave(function(){
			$(".login_list").hide(1000);
		})

	
	//选取发布图片 
	$(".img-btn").change(function() {
		var preview = document.querySelector('#GiveTop>img');
		// var file = document.querySelector('input[type=file]').files[0];
		var file = this.files[0];
		// console.log(file);
		var reader = new FileReader();
		reader.onloadend = function() {
			preview.src = reader.result;
		}
		if (file) {
			reader.readAsDataURL(file);
			// $(".img-btn").val('');
		} else {
			preview.src = "";
			$("#GiveTop>i").show();
		}
		console.log($(".login>span").text());
	
	});
	
	
	//图标按钮判断
	$(".GivePage").mouseover(function() {
		if ($(".img1").attr("src") == null || $(".img1").attr("src") == "") {
			$("#GiveTop>i").show();
		} else {
			$("#GiveTop>i").hide();
		}
	});
	//发布美图按钮
	$(".fabu_list_pic").click(function() {
		$(".GivePage").show(1000, function() {
			$("#app").addClass("zhezhao");
			$(".close>i").click(function() {
				$(".GivePage").hide(1000);
				$("#app").removeClass("zhezhao");
				$(".img1").attr("src", "");
				if ($(".img1").attr("src") == "") {
					$("#GiveTop>i").show();
				}
				$(".img-btn").val('');
			})
	
		});
	
	})
	$(".camera").click(function() {
		$(".img-btn").click();
		$("#GiveTop>i").hide();
	
	});
	$("#remove").click(function() {
		$(".img1").attr("src", "");
		$(".img-btn").val('');
		if ($(".img1").attr("src") == "") {
			$("#GiveTop>i").show();
		}
	
	});
	//开启选择活动操作
	$(".act-btn").click(function(){
		$(".select_act").show();
		// data5=[{"announcer_id":"123","content_date":"2020","image_file":"./img/03.jpg","content_title":"活動題目","activity_id":"2"},{"announcer_id":"123","content_date":"2020","image_file":"./img/03.jpg","content_title":"活動題目","activity_id":"2"},{"announcer_id":"123","content_date":"2020","image_file":"./img/03.jpg","content_title":"活動題目","activity_id":"2"}]
		// for(var i=0;i<data5.length;i++)
		// {
		// 	var act_fabu=$('<div class="activity"><div class="activity-fabu"><span>发布者</span><p class="activity-fabu-people">'+data5[i].announcer_id+'</p><span>日期</span><p class="activity-fabu-date">'+data5[i].content_date+'</p></div><img src="'+data5[i].image_file+'" class="activity-img"><span class="activity-title" id="">'+data5[i].content_title+'</span><div class="check_select"><i class="fa fa-check fa-2x" aria-hidden="true"></i><span class="caculate"></span></div><span class="activity_id">'+data5[i].activity_id+'</span></div>');
		// 	$(".select_act_info").append(act_fabu);
		// };
		$.ajax({
			//请求方式
			type : "GET",
			//请求的媒体类型
			//contentType: "application/json;charset=UTF-8",
			//请求地址
			url : "/share/activity/getActivity",
			//数据，json字符串
			//data : {action:"get"},
			dataType:"json",
			//请求成功
			success : function(data) {
				$(".select_act_info").text("");
				var arr = [];
				for(var item in data){
					arr.push(data[item]);
				}
				//var inf = '';
				/*<![CDATA[*/
				for (var i = 0; i < arr.length; i++) {
					/*]]>*/
					// console.log(arr[i]);
					var act_fabu = $('<div class="activity"><div class="activity-fabu"><span>发布者</span><p class="activity-fabu-people">' +
						arr[i].user_name+'</p><span>日期</span><p class="activity-fabu-date">' +
						arr[i].activity_date +'</p></div><img src="/share/pictures/' +
						arr[i].image_file +'" class="activity-img"><span class="activity-title" id="">' +
						arr[i].activity_content +'</span><div class="check_select"><i class="fa fa-check fa-2x" aria-hidden="true"></i><span class="caculate">0</span></div><span class="act_id" style="display:none">' +
						arr[i].activity_id +'</span></div>');
					$(".select_act_info").append(act_fabu);
					// waterFall();
				}
				selectActivity();
				//$("#box").html(inf);
				//console.log(data.contentLists);
				//点击爱心和数量变化
				// dianJi();
				// quXiao();

			},
			//请求失败，包含具体的错误信息
			error : function(e){
				console.log(e.status);
				console.log(e.responseText);
			}
		});
	});
	//选取活动打钩
	function selectActivity(){
		$(".select_act_info>.activity").click(function(){
			// alert("你好");
			if($(this).children(".check_select").children(".caculate").text()==0)
			{

				$(this).children(".check_select").children("i").show();
				$(this).children(".check_select").children(".caculate").text(1);
				// console.log($(this).children(".check_select").find(".caculate").text());
				$(this).siblings().children(".check_select").children(".caculate").text(0);
				$(this).siblings().children(".check_select").children("i").hide();
				//拿到被选活动元素的id
				var act_id=$(this).children(".act_id").text();
				// console.log(act_id);
				$("#this_activity_id").val(act_id);
				var aact_id = $("#this_activity_id").val();
				// console.log(aact_id);
			}


		})
	}
	$("#Acts_fabu_remove").click(function() {
		$(".Acts_fabu_img1").attr("src", "");
		$(".Acts_fabu_img-btn").val('');
		if ($(".Acts_fabu_img1").attr("src") == "") {
			$("#Acts_fabu_GiveTop>i").show();
		}
	
	});
	//发布图片勾选的活动接收数据ajax

	//关闭按钮操作
	$(".close2>i").click(function(){
		$(".select_act").hide();
	});
	
	
	
	//发布活动 模拟
	$(".fabu_list_act").click(function(){
			$(".Acts_fabu").show(1000)
	})
	$(".Acts_fabu_close>i").click(function(){
		$(".Acts_fabu").hide(1000);
	});
	//发布活动页面内容
	$(".Acts_fabu_img-btn").change(function(){
		
		var previewa = document.querySelector('.Acts_fabu_img1');
		
		var file = this.files[0];
		var reader = new FileReader();
		reader.onloadend = function() {
			previewa.src = reader.result;
		}
		if (file) {
			reader.readAsDataURL(file);
			// $(".Acts_fabu_img-btn").val('');
		} else {
			previewa.src = "";
			$("#Acts_fabu_GiveTop>i").show();
		}
		// console.log($(".login>span").text());
	
	});
	$(".Acts_fabu").mouseover(function() {
		if ($(".Acts_fabu_img1").attr("src") == null || $(".Acts_fabu_img1").attr("src") == "") {
			$("#Acts_fabu_GiveTop>i").show();
		} else {
			$("#Acts_fabu_GiveTop>i").hide();
		}
	});
	$(".Acts_fabu_camera").click(function() {
		$(".Acts_fabu_img-btn").click();
		$("#Acts_fabu_GiveTop>i").hide();
	
	});
	//发布活动数据ajax
	//这是活动内容
	var act_text=$(".activity_title_fabu").text();
	//这是图片内容
	var act_img=$(".Acts_fabu_img-btn").val();


	//活动选择操作以及传输已选的活动数据
	//活动选择操作以及传输已选的活动数据

	//这是对于发布图片页的模拟ajax 其中的caculate是判断有没有选择活动 upload()函数是你给我的用于传图片给后台
	// function upload()
	// {
	// 	 var formData = new FormData($("#uploadForm")[0])  //创建一个forData
	// 	 formData.append('img', $('.img-btn')[0].files[0]) //把file添加进去  name命名为img
	// 	 return formData;
	// }

	function toElseInfo(){
		//点击头像进入他人主页 且用户数据传输!
		$(".item>.picture-content>.info>.infoImg").click(function(){
			// alert($(this).parent().parent().parent().find(".info_id").text());
			// var id = $(this).parent().children(".info_id").text();
			// //详情ajax跳转
			// var url = "/share/content/toImageDetail/" + id;
			// window.location.href = url;
			//获取点击头像的id
			var id=$(this).parent().parent().parent().find(".info_man_id").text();
			var url = "/share/user/toElseDetail/" + id;
			window.location.href = url;

			///ajax跳转主页
			// window.location.href="Objinfo.HTML";
			// 模拟ajax传给你id;
		})
	}


	
	//推荐图片操作
	// 瀑布函数
	function waterFall() {
		// 1- 确定列数  = 页面的宽度 / 图片的宽度
		var box = $("#box");
		var items = $(".item");
		// 定义每一列之间的间隙 为10像素
		var gap = 10;
		var pageWidth = $("#box").width();
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
	//解决bug 瀑布的
	$("body").mouseover(function() {
		waterFall();
	});


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

})
