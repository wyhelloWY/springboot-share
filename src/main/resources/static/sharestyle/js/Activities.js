$(function(){

	function toActivity(){
		$(".activity").click(function(){
			//获取点击的活动id 模拟ajax
			var act_id=$(this).children(".act_id").text();
			//获取点击的活动id 模拟ajax
			// alert(act_id);
			var url = "/share/activity/toActivityDetail/"+act_id;
			// alert(act_id);
			window.location.href = url;

		})
	}


	//活动传输
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
			// alert("你好")
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
					var div = $('<div class="activity"><div class="activity-fabu"><span>发布者</span><p class="activity-fabu-people">' +
						arr[i].user_name+'</p><span>日期</span><p class="activity-fabu-date">' +
						arr[i].activity_date +'</p></div><img src="/share/pictures/' +
						arr[i].image_file +'" class="activity-img"><div id="activity-title"><span>活动标题</span><span class="activity-title" id="">' +
						arr[i].activity_name+'</span></div><div id="activity_content"><span>活动内容</span><p class="activity_content">' +
						arr[i].activity_content+'</p></div><span class="act_id" style="display:none">' +
						arr[i].activity_id +'</span></div>');
					$("#AllActivity").append(div);
					// waterFall();
				}
			}else{
				$("#AllActivity").append('<div class="baocuo" style="width: 200px;margin-left: auto;margin-right: auto; background-color: #1ABC9C;font-size: 12px; <!-- display: none; -->"> <h1 style="font-size: 15px;color: white;line-height: 20px;">' +
					'此活动详情内容为空！</h1> </div>');
			}
			toActivity();


		},
		//请求失败，包含具体的错误信息
		error : function(e){
			console.log(e.status);
			console.log(e.responseText);
		}
	});

	
	$(".first_page").click(function(){
		var url = "/share/index";
		window.location.href=url;
	})
	$(".login").click(function(){
		var url = "/share/user/toPersonInfo";
		window.location.href=url;
	})
	
	
})