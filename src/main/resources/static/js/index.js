layui.use(['element','layer','laytpl'],function(){
	var $=layui.$,laytpl=layui.laytpl,form=layui.form,LASTnums=0;
		$(document).scroll(function(){
			if ($(document).scrollTop()>500) {
				$(".layui-fixbar-top").show()
			}else{
				$(".layui-fixbar-top").hide()
			}
			
		})
		$(".layui-fixbar-top").click(function(){
			$('html,body').animate({scrollTop:0},800)
		})
		if ($.cookie("TZnums")) {
			
			LASTnums = $.cookie("TZnums");
			var Ldata = $.cookie("TZnums").split(",")
			console.log(Ldata)
			var Ndata = [],Idata=[];
//			var minToMAX = Ldata.sort(function(a, b){ return a - b; })
//			console.log(minToMAX)
			for (var i=0;i<Ldata.length;i++) {
				var  a = i+1;
				Idata.push(Ldata.indexOf(a.toString()));
			}
			for (var i=0;i<Ldata.length;i++) {
				Ndata[i]=data[Idata[i]];
				
			}
			var getTpl = demo.innerHTML
				,view = document.getElementById('gather');
				laytpl(getTpl).render(Ndata, function(html){
				  view.innerHTML = html;
				 
			});	
			console.log(Ndata)
			console.log(Idata)
			
		} else{
			console.log(data)
			var getTpl = demo.innerHTML
			,view = document.getElementById('gather');
			laytpl(getTpl).render(data, function(html){
			  view.innerHTML = html;
			 
			});
		}
	$(".filtr-item>.Tcontent>h4>span").addClass( "iconfont").css({"font-size":"36px","color":"#1E9FFF"})

	$('#gather').filterizr();
	
	$("#tuozhuai").click(function(){
		layer.open({
		  title:"自定义排序",
		  area: ['1250px', '700px'],
		  type: 2, 
		  content: 'model.html',
		  scrollbar: false,
		  cancel: function(index, layero){ 
		    layer.close(index)
//		    window.location.reload()
			if (LASTnums==$.cookie("TZnums")||$.cookie("TZnums")==undefined) {
				layer.msg("尚未修改列表排序")
			} else{
				layer.msg("列表排序修改成功")
				window.location.reload()
			}
//			console.log(LASTnums);
//			console.log($.cookie("TZnums"))
			}
		})
	})
	
	$("#chatOpen").click(function(){
		layer.open({
			title:['常见问题查询', 'color: #333;'],
			area:["100%","100%"],
			type:2,
			anim:1,
			content:'chat.html',
			scrollbar: false,
			
		})
	})
	
	
})
function mask(system,href,name){
	var Thtml="";
	if (system=="B/S") {
		Thtml = "<a class='layui-btn layui-btn-lg' style='margin-top:35px' target='_blank' href="+href+">点击访问</a>"
	} else if (system=="C/S") {
		Thtml = "<a class='layui-btn layui-btn-lg layui-btn-normal'  style='margin-top:35px' href="+href+" download="+name+">下载客户端</a>"
	}else if (system=="移动端") {
		for (var i =0;i<href.length;i++) {
			if (href.length==2) {
				Thtml +="<div class='QRbox layui-col-md6'><img src='"+href[i].src+"' layer-src='"+href[i].src+"' width='100%'/><p style='color:#FFB800'>"+href[i].remark+"</p></div>"
			} else{
				Thtml +="<div class='QRbox'><img src='"+href[i].src+"' layer-src='"+href[i].src+"' width='50%'/><p style='color:#FFB800'>"+href[i].remark+"</p></div>"
			}	
		}
	}
	return Thtml;
}
