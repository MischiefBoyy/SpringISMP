		layui.use('element',function(){
			var $= layui.$;
			$("#textarea").focus(function(){
				$("#send").off().on('click',send1)				
			})
			$.ajax({
				type:"get",
				url:window.PRJ_ROOT+"/sy",
				async:true,
				success:function(res){
					var data = JSON.parse(res).data;
					console.log(JSON.parse(res))	
					appendA(data);
					
					listC()
						
				}
			});
		})
function listC(){
	$(".list").off("click").on("click",function(){
		$(".aoe").append('<div class="user layui-clear">'+
			'<div class="img_box">'+
				'<img src="img/user.jpg" width="85%"/>'+
			'</div>'+
			'<div class="text_box">'+
				'<p class="text_title layui-clear"><span>'+getNowFormatDate()+'</span><span>我</span></p>'+
				'<p class="text-content">'+
					$(this).html()
				+'</p>'+
			'</div>'+
		'</div>')
		$("#textarea").val("")
		if ($(".aoe").height()-250>0) {
			$(".aoeBOX").scrollTop($(".aoe").height()-238)
		}
		
		
		
		var msg = {"id":$(this).attr("data-id"),"isBase":$(this).attr("data-isBase")}
			$.ajax({
				type:"post",
				url:window.PRJ_ROOT+"/click",
				async:true,
				data:msg,
				dataType:"json",
				success:function(res){
					var data = res.data;
					console.log(res)
					appendA(data)
					listC()
					
				}
			});
	})
}

//answer
function appendA(data){
	var list="";
	console.log(data)
	if(data){
		if (data.info.length==0) {
			list="请联系财安技术部，解决问题"
		} else{
			if (data.isQa==0 ||data.isQa==undefined) {
				if (data.isBase==false) {
					for (var i =0;i<data.info.length;i++) {
						list+="<a class='list'  data-id="+data.info[i]['id']+" data-isBase= "+data.info[i]['isBase']+">"+data.info[i]['question']+"</a></br>"
					}
				} else{			
					console.log(data.info)	
					list+="<a  data-id="+data.info['id']+" data-isBase= "+data.info['isBase']+">"+data.info['answer']+"</a></br>"
					
				}
			} else{
					for (var i =0;i<data.info.length;i++) {
						if (data.info[i]['imagePath']) {
							list+="<a data-id="+data.info[i]['id']+" data-isBase= "+data.info[i]['isBase']+">"+data.info[i]['question']+"</a></br><img src=images/"+data.info[i]['imagePath']+" layer-src=images/"+data.info[i]['imagePath']+" width=50% alt="+data.info[i]['question']+"></br>"
						} else{
							list+="<a data-id="+data.info[i]['id']+" data-isBase= "+data.info[i]['isBase']+">"+data.info[i]['question']+"</a></br>"
						}
						
					}
			}
			
		}
	}else{
		list="<a>对不起你的问题有误，可以尝试点击快速查询.</a>"
	}


	$(".aoe").stop().append(
	'<div class="in-rich layui-clear">'+
		'<div class="img_box">'+
			'<img src="img/wxLogo.jpg" width="100%"/>'+
		'</div>'+
		'<div class="text_box">'+
			'<p class="text_title layui-clear"><span>财安金融</span><span>'+getNowFormatDate()+'</span></p>'+
			'<p class="text-content">'+
				list+
			'</p>'+
		'</div>'+
	'</div>')
	layer.photos({
	  photos: '.text-content'
	  
	  ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
	});
	if ($(".aoe").height()-250>0) {
			$(".aoeBOX").scrollTop($(".aoe").height()-238)
	}
	
}
	

	
		
//文字输入		
function send1(){
	if ($("#textarea").val()) {
		$(".aoe").append('<div class="user layui-clear">'+
			'<div class="img_box">'+
				'<img src="img/user.jpg" width="85%"/>'+
			'</div>'+
			'<div class="text_box">'+
				'<p class="text_title layui-clear"><span>'+getNowFormatDate()+'</span><span>我</span></p>'+
				'<p class="text-content">'+
					$("#textarea").val()
				+'</p>'+
			'</div>'+
		'</div>')
		$.ajax({
				type:"post",
				url:window.PRJ_ROOT+"/text",
				async:true,
				data:{"text":$("#textarea").val()},
				dataType:"json",
				success:function(res){
					var data = res.data;
					console.log(res)
					appendA(data)
					listC()
					
				}
			});
		
		
		
		$("#textarea").val("")
		if ($(".aoe").height()-250>0) {
			$(".aoeBOX").scrollTop($(".aoe").height()-238)
		}
		
		
	}else{
		layer.msg("您尚未输入内容")
		$("#textarea").focus()
	}
}
		//时间
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}