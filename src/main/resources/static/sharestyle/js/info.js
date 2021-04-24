$(function() {

	pictureAjax();
	//图片ajax传输
	LeaveMessage();
	//关注人列表
	selectAttention();
	function pictureAjax(){
		$.ajax({
			//请求方式
			type : "GET",
			//请求的媒体类型
			//contentType: "application/json;charset=UTF-8",
			//请求地址
			url : "/share/content/getPersonContent",
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
						var div = $('<div class="infoItem"><img src="/share/pictures/' +
							arr[i].image_file+'" class="info-picture-img" ><div class="info-picture-content"><div class="info-picture-love"><i class="fa fa-heart fa-2x" aria-hidden="true"></i><span picture-love-num>'+
							arr[i].content_like +'</span><a href="#" class="pic_delete">删除</a></div></div><span class="info_picture_id" style="display: none">' +
							arr[i].content_id+'</span></div>');
						$(".infoItems").append(div);

						waterFall();
					}
				}else{
					$(".infoItems").append('<div class="baocuo" style="width: 200px;margin-left: auto;margin-right: auto; background-color: #1ABC9C;font-size: 12px; <!-- display: none; -->"> <h1 style="font-size: 15px;color: white;line-height: 20px;">分享内容为空！</h1> </div>');
				}

				deleteImg();
				$(".infoItem>.info-picture-img").click(function(){

					var id = $(this).parent().children(".info_picture_id").text();
					//详情ajax跳转
					var url = "/share/content/toImageDetail/" + id;
					window.location.href = url;

				})

			},
			//请求失败，包含具体的错误信息
			error : function(e){
				console.log(e.status);
				console.log(e.responseText);
			}
		});
	}
	function deleteImg(){
		$(".pic_delete").click(function(){
			var content_id=$(this).parent().parent().parent().children(".info_picture_id").text();

			var this_s = this;
			$.ajax({
				//请求方式
				type : "GET",
				//请求的媒体类型
				//contentType: "application/json;charset=UTF-8",
				//请求地址
				url : "/share/content/deleteImg",
				//数据，json字符串
				data : {content_id:content_id},
				dataType:"json",
				//请求成功
				success : function(data) {
					if(data.result=="success"){
						alert("删除成功");
						$(this_s).parent().parent().parent().remove();
					}else if(data.result=="error"){
						alert("删除失败");
					}else{
						alert("删除出现问题");
					}


				},
				//请求失败，包含具体的错误信息
				error : function(e){
					console.log(e.status);
					console.log(e.responseText);
				}
			});
		})
	}


	$(".UlInfomation>ul>li").click(function() {
		var i = $(this).index();
		$(".container>ul>li").eq(i).show();
		$(".container>ul>li").eq(i).siblings().hide();
		console.log(i);
		$(this).siblings().removeClass("one");
		$(this).addClass("one");

	});


	//关注列表获取数据显示模拟ajax
	// data = [{
	// 	"attention_img": "img/pic1.jpg",
	// 	"attention_id": "12346"
	// }, {
	// 	"attention_img": "img/pic1.jpg",
	// 	"attention_id": "12346"
	// }];
	// for (var i = 0; i < data.length; i++) {
	// 	var attentioners = $('<div class="followed"><img src="' + data[i].attention_img +
	// 		'" class="followed_avatar">	<span class="followed_id">' + data[i].attention_id +
	// 		'</span><button type="button">取消关注</button></div>');;
	// 	$(".followed-li").append(attentioners);
	// }
	function selectAttention(){
		$.ajax({
			//请求方式
			type : "GET",
			//请求的媒体类型
			//contentType: "application/json;charset=UTF-8",
			//请求地址
			url : "/share/attention/selectAllAttention",
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
						console.log(arr[i]);
						var attention = $('<div class="followed"><img src="/share/pictures/' +
							arr[i].avatar_image+'" class="followed_avatar">	<span class="followed_id">'+
							arr[i].user_name+'</span><span class="followed_user_id"  style="display: none">' +
							arr[i].followed_id+'</span><button type="button">取消关注</button></div>');
						$(".followed-li").append(attention);

						// waterFall();
					}
				}else{
					$(".followed-li").append('<div class="baocuo" style="width: 200px;margin-left: auto;margin-right: auto; background-color: #1ABC9C;font-size: 12px; <!-- display: none; -->"> <h1 style="font-size: 15px;color: white;line-height: 20px;">关注列表为空！</h1> </div>');
				}


				deleteAttention();
			},
			//请求失败，包含具体的错误信息
			error : function(e){
				console.log(e.status);
				console.log(e.responseText);
			}
		});
	}
	//取消关注按钮
	function deleteAttention(){
		$("body").delegate(".followed>button", "click", function() {
			var followed_user_id= $(this).parent().children(".followed_user_id").text();
			var this_s=this;

			$.ajax({
				//请求方式
				type : "post",
				//请求的媒体类型
				//contentType: "application/json;charset=UTF-8",
				//请求地址
				url : "/share/attention/deleteAttention",
				//数据，json字符串
				data : {followed_id:followed_user_id},
				dataType:"json",
				//请求成功
				success : function(data) {
					if(data.result=="success"){
						alert("取消关注成功");
						$(this_s).parents(".followed").remove();
					} else{
						alert("取消关注出现异常");
					}
					// alert("成功");

				},
				//请求失败，包含具体的错误信息
				error : function(e){
					console.log(e.status);
					console.log(e.responseText);
				}
			});

		});
	}
	$(".followed_avatar").click(function(){

		window.location.href="Objinfo.html";

	})

	$("#submitForm").click(function(){
		//var da = document.getElementById("updateForm");
		var data = new FormData();
		data.append("image_name",$("#image_file").get(0).files[0]);
		data.append("user_name",$("#iname").val());
		data.append("user_age",$("#iage").val());
		data.append("user_motto",$("#user_motto").val());

		alert(data);
		// var data = $("#updateForm").serializeArray();
		// var user_name = $("#iname").val();
		// var user_age = $("#iage").val();
		// var user_motto = $("#user_motto").val();
		$.ajax({
			//请求方式
			type : "post",
			//请求的媒体类型
			//contentType: "application/json;charset=UTF-8",
			//请求地址
			url : "/share/user/updateUser",
			//数据，json字符串
			contentType:false,
			data : data,
			processData:false,
			//dataType:"json",
			//请求成功
			success : function(data) {
				if(data.result=="success"){
					window.location.reload();
					alert("修改成功");

				} else{
					alert("修改失败");
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
	

	function LeaveMessage(){
		$.ajax({
			//请求方式
			type : "GET",
			//请求的媒体类型
			//contentType: "application/json;charset=UTF-8",
			//请求地址
			url : "/share/leaveMessage/getMyMessage",
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
							arr[i].image_file+'" ><span class="leave_man_id">' +
							arr[i].username+'</span><span class="Leave_A_Message_time">' +
							arr[i].message_date+'</span></div><p class="messagecontent">' +
							arr[i].message_content+'</p></div>');
						$(".LeaveMessages").append(leave);
					}
				}else{
					$(".LeaveMessages").append('<div class="baocuo" style="width: 200px;margin-left: auto;margin-right: auto; background-color: #1ABC9C;font-size: 12px; <!-- display: none; -->"> ' +
						'<h1 style="font-size: 15px;color: white;line-height: 20px;">此用户留言为空！</h1> </div>');
				}



			},
			//请求失败，包含具体的错误信息
			error : function(e){
				console.log(e.status);
				console.log(e.responseText);
			}
		});
	}
	//留言回复传内容ajax
	$(".back_message_btn").click(function(){
		//文本输入的内容
		var text=$(".back_message_content").val();
		//id
		var idk=$(this).parents().find(".leave_man_id").text(); 
		//列成数组
		var data=[{"back_content":"text","back_id":"idk"}];
		console.log(idk);
		$.ajax({
			type: "post",
			url: 000,	
			data: data,
			success: function(res) {
				
			},
			error: function(res) {
		
			}
		})
	})
	
	
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
	$(".first_page").click(function(){
		var url = "/share/index";
		window.location.href=url;
	})
	$(".login").click(function(){
		var url = "/share/user/toPersonInfo";
		window.location.href=url;
	})
	//
	$(".pic_change_btn").click(function(){
		$(".user_avatar").click();
	});
	
	$(".user_avatar").change(function(e){
		var name=e.currentTarget.files[0].name;
		$(".pic_change_text").html(name);
	});

	$(".user_avatar").change(function() {
		var preview = document.querySelector('.useravater');
		var file = this.files[0];
		console.log(file);
		var reader = new FileReader();
		reader.onloadend = function() {
			preview.src = reader.result;
		}
		if (file) {
			reader.readAsDataURL(file);
			// $(".img-btn").val('');
		} else {
			preview.src = "";
		}
		
	});
	$(".remove").click(function() {
		
		$(".useravater").attr("src", "");
		
		$(".user_avatar").val('');
		$(".pic_change_text").text('');
		console.log($(".user_avatar").value);
		
	});
	
});
