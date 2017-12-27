layui.use(['element','layer','laytpl'],function(){
	var $=layui.$,laytpl=layui.laytpl,form=layui.form;
	
		console.log(data)
		$(".layui-nav>.layui-nav-item").click(function(){
			
			if ($(this).attr("data-filter")=="all") {
				var getTpl = demo.innerHTML
				,view = document.getElementById('gather');
				laytpl(getTpl).render(data, function(html){
				  view.innerHTML = html;
				});
				$(".filtr-item>.Tcontent>h4>span").addClass( "iconfont").css({"font-size":"36px","color":"#1E9FFF"})
			} else{
				var tabData =[];
				for (var i=0;i<data.length;i++) {
					if (data[i]["code"]==$(this).attr("data-filter")) {
						tabData.push(data[i])
					}
					
				}
				var getTpl = demo.innerHTML
				,view = document.getElementById('gather');
				laytpl(getTpl).render(tabData, function(html){
				  view.innerHTML = html; 
				});
				$(".filtr-item>.Tcontent>h4>span").addClass( "iconfont").css({"font-size":"36px","color":"#1E9FFF"})
			}
		})
		var getTpl = demo.innerHTML
		,view = document.getElementById('gather');
		laytpl(getTpl).render(data, function(html){
		  view.innerHTML = html;
		 
		});
		$(".filtr-item>.Tcontent>h4>span").addClass( "iconfont").css({"font-size":"36px","color":"#1E9FFF"})

	
	
	var aLi = $("#gather>div");
		var disX = 0;
		var disY = 0;
		var minZindex = 1;
		var aPos =[];
		for(var i=0;i<aLi.length;i++){
			var t = aLi[i].offsetTop;
			var l = aLi[i].offsetLeft;
			aLi[i].style.top = t+"px";
			aLi[i].style.left = l+"px";
			aPos[i] = {left:l,top:t};
			aLi[i].index = i;
		}
		for(var i=0;i<aLi.length;i++){
			aLi[i].style.position = "absolute";
			aLi[i].style.margin = 0;
			setDrag(aLi[i]);
		}
		//拖拽
		function setDrag(obj){
			obj.onmouseover = function(){
				obj.style.cursor = "move";
			}
			obj.onmousedown = function(event){
				var scrollTop = document.documentElement.scrollTop||document.body.scrollTop;
				var scrollLeft = document.documentElement.scrollLeft||document.body.scrollLeft;
				obj.style.zIndex = minZindex++;
				
				//当鼠标按下时计算鼠标与拖拽对象的距离
				disX = event.clientX +scrollLeft-obj.offsetLeft;
				disY = event.clientY +scrollTop-obj.offsetTop;
				document.onmousemove=function(event){
					//当鼠标拖动时计算div的位置
					var l = event.clientX -disX +scrollLeft;
					var t = event.clientY -disY + scrollTop;
					obj.style.left = l + "px";
					obj.style.top = t + "px";
					/*for(var i=0;i<aLi.length;i++){
						aLi[i].className = "";
						if(obj==aLi[i])continue;//如果是自己则跳过自己不加红色虚线
						if(colTest(obj,aLi[i])){
							aLi[i].className = "active";
						}
					}*/
//					for(var i=0;i<aLi.length;i++){
//						aLi[i].className = "";
//					}
					var oNear = findMin(obj);
//					if(oNear){
//						oNear.className = "active";
//					}
				}
				document.onmouseup = function(){
					document.onmousemove = null;//当鼠标弹起时移出移动事件
					document.onmouseup = null;//移出up事件，清空内存
					//检测是否普碰上，在交换位置
					var oNear = findMin(obj);
					if(oNear){
//						oNear.className = "";
						oNear.style.zIndex = minZindex++;
						obj.style.zIndex = minZindex++;
						startMove(oNear,aPos[obj.index]);
						startMove(obj,aPos[oNear.index]);
						var Tstartnum = obj.getAttribute("data-num");
						var Tendnum =oNear.getAttribute("data-num");
//						console.log(obj.getAttribute("data-num"))
//						console.log(oNear.getAttribute("data-num"))
						oNear.setAttribute("data-num",Tstartnum)
						obj.setAttribute("data-num",Tendnum)
						//交换index
						oNear.index += obj.index;
						obj.index = oNear.index - obj.index;
						oNear.index = oNear.index - obj.index;
						
						
						
						var Tnum = [];
						for (var i =0;i<$("#gather>div").length;i++) {
							Tnum.push($("#gather>div").eq(i).attr("data-num"))
						}
//						console.log(Tnum)
						$.cookie("TZnums",Tnum)
					}else{
						startMove(obj,aPos[obj.index]);
					}
					
				}
				clearInterval(obj.timer);

				return false;//低版本出现禁止符号
			}
		}
		//碰撞检测
		function colTest(obj1,obj2){
			var t1 = obj1.offsetTop;
			var r1 = obj1.offsetWidth+obj1.offsetLeft;
			var b1 = obj1.offsetHeight+obj1.offsetTop;
			var l1 = obj1.offsetLeft;

			var t2 = obj2.offsetTop;
			var r2 = obj2.offsetWidth+obj2.offsetLeft;
			var b2 = obj2.offsetHeight+obj2.offsetTop;
			var l2 = obj2.offsetLeft;

			if(t1>b2||r1<l2||b1<t2||l1>r2){
				return false;
			}else{
				return true;
			}
		}
		//勾股定理求距离
		function getDis(obj1,obj2){
			var a = obj1.offsetLeft-obj2.offsetLeft;
			var b = obj1.offsetTop-obj2.offsetTop;
			return Math.sqrt(Math.pow(a,2)+Math.pow(b,2));
		}
		//找到距离最近的
		function findMin(obj){
			var minDis = 999999999;
			var minIndex = -1;
			for(var i=0;i<aLi.length;i++){
				if(obj==aLi[i])continue;
				if(colTest(obj,aLi[i])){
					var dis = getDis(obj,aLi[i]);
					if(dis<minDis){
						minDis = dis;
						minIndex = i;
					}
				}
			}
			if(minIndex==-1){
				return null;
			}else{
				return aLi[minIndex];
			}
		}
	
	
	
})
function mask(code,href,name){
	var Thtml="";
	if (code==2) {
		Thtml = "<a class='layui-btn layui-btn-lg' style='margin-top:35px' target='_blank' href="+href+">点击访问</a>"
	} else if (code==3 ||code==4) {
		Thtml = "<a class='layui-btn layui-btn-lg layui-btn-normal'  style='margin-top:35px' href="+href+" download="+name+">下载客户端</a>"
	}else if (code==5) {
		for (var i =0;i<href.length;i++) {
			if (href.length==2) {
				Thtml +="<div class='QRbox layui-col-md6'><img src='"+href[i].src+"' width='50%'/><p style='color:#FFB800'>"+href[i].remark+"</p></div>"
			} else{
				Thtml +="<div class='QRbox'><img src='"+href[i].src+"' width='20%'/><p style='color:#FFB800'>"+href[i].remark+"</p></div>"
			}	
		}
	}
	return Thtml;
}
