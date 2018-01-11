layui.use("element",function(){
			var $= layui.$;var form = layui.form;
			$.ajax({
				type:"get",
				url:window.PRJ_ROOT+"/getTableTree",
				async:true,
				success:function(res){
					var data = JSON.parse(res);
					var dataId;
					layui.tree({
					  elem: '#demo' //传入元素选择器
					  ,nodes: data,
					  click: function(node){
					    console.log(node.id) //node即为当前点击的节点数据
					    if (dataId==node.id) {
					    	return;
					    } else{
					    	$.ajax({
					    		type:"post",
					    		url:window.PRJ_ROOT+"/click",
					    		async:true,
					    		data:{"id":node.id},
					    		dataType:"json",
					    		success:function(res){
					    			console.log(res)
					    			if (res.data.imagePath) {
					    				$("#table").html('<colgroup><col width="150"><col width="80"><col width="80"><col width="80"></colgroup><thead><tr><th>描述</th><th>图片</th><th>修改</th><th>删除</th></tr></thead><tbody><tr><td>'+res.data.question+'</td><td><img src=images/'+res.data.imagePath+'  layer-src=images/'+res.data.imagePath+'></td><td><button class="layui-btn" id="trevise">修改</button></td><td><button class="layui-btn layui-btn-danger" id="tdel">删除 </button></td></tr></tody>')
					    				//form
					    				$("#tform").html('<div class="layui-form-item"><label class="layui-form-label">描述</label><div class="layui-input-block"><textarea name="question" class="layui-textarea" id="Fquestion" >'+res.data.question+'</textarea></div></div><div class="layui-form-item"><label class="layui-form-label">图片</label><div class="layui-input-block picbox"><img src=images/'+res.data.imagePath+' width="70%" id="timg"><input type="file" id="newImg" name="file"></div></div><button type="button" class="layui-btn layui-btn-normal" id="sbtn">确认修改</button>')
					    				//图片点击放大
					    				layer.photos({
										  photos: '#table'
										  ,anim: 5 //0-6的选择，指定弹出图片动画类型，默认随机（请注意，3.0之前的版本用shift参数）
										});					

					

										//form表单图片绑定file
										$("#timg").click(function(){
											$("#newImg").trigger("click");
										})
										//图片预览
										$("#newImg").on("change",function(){
											var objUrl = getObjectURL(this.files[0]) ; //获取图片的路径，该路径不是图片在本地的路径
											if (objUrl) {
												$("#timg").attr("src", objUrl) ; //将图片路径存入src中，显示出图片
											}
										})
										$("#sbtn").click(function(){
											var Formdata1 = new FormData($("#tform")[0]);
											Formdata1.delete("question");
											var Fdata = {"question":$("#Fquestion").val(),"id":node.id}
											Formdata1.append("data",JSON.stringify(Fdata))										
											Cajax(Formdata1)
										})
										
					    			} else if(res.data.answer){
					    				$("#table").html('<colgroup><col><col width="150"><col width="300"><col width="80"><col width="80"></colgroup><thead><tr><th>问题</th><th>关键字</th><th>回答</th><th>修改</th><th>删除</th></tr></thead><tbody><tr><td>'+res.data.question+'</td><td>'+res.data.keyWord+'</td><td>'+res.data.answer+'</td><td><button class="layui-btn" id="trevise">修改</button></td><td><button class="layui-btn layui-btn-danger" id="tdel">删除</button></td></tr></tody>')
					    				
					    				$("#tform").html('<div class="layui-form-item"><label class="layui-form-label">问题</label><div class="layui-input-block"><textarea name="question" class="layui-textarea">'+res.data.question+'</textarea></div></div><div class="layui-form-item"><label class="layui-form-label">关键字</label><div class="layui-input-block"><input class="layui-input" id="key_word" value='+res.data.keyWord+'></div></div><div class="layui-form-item"><label class="layui-form-label">回答</label><div class="layui-input-block"><textarea name="answer" class="layui-textarea">'+res.data.answer+'</textarea></div></div><button type="button" class="layui-btn layui-btn-normal" id="sbtn">确认修改</button>')
					    				form.render('')
					    				$("#sbtn").click(function(){
											var Formdata1 = new FormData();
											
											var Fdata = {"question":$("#tform textarea").eq(0).val(),"id":node.id,"answer":$("#tform textarea").eq(1).val(),"key_word":$("#key_word").val()}
											console.log(Fdata)
											Formdata1.append("data",JSON.stringify(Fdata))										
											Cajax(Formdata1)
										})
					    				
					    			}else{
					    				if(res.data.isQa==0){
					    					var Qadata = "否";
					    					
					    				}else{
					    					var Qadata = "是";
					    					
					    				}
					    				$("#table").html('<colgroup><col><col width="150"><col width="150"><col width="80"><col width="80"></colgroup><thead><tr><th>问题</th><th>关键字</th><th>是否Qa问答</th><th>修改</th><th>删除</th></tr></thead><tbody><tr><td>'+res.data.question+'</td><td>'+res.data.keyWord+'</td><td>'+Qadata+'</td><td><button class="layui-btn" id="trevise">修改</button></td><td><button class="layui-btn layui-btn-danger" id="tdel">删除</button></td></tr></tody>')
					    				
					    				$("#tform").html('<div class="layui-form-item"><label class="layui-form-label">问题</label><div class="layui-input-block"><textarea name="question" class="layui-textarea">'+res.data.question+'</textarea></div></div><div class="layui-form-item"><label class="layui-form-label">关键字</label><div class="layui-input-block"><input class="layui-input" id="key_word" value='+res.data.keyWord+'></div></div><div class="layui-form-item"><label class="layui-form-label">是否Qa问答</label><div class="layui-input-block"> <input type="checkbox" id="Tclose" lay-skin="switch" lay-text="是|否"></div></div><button type="button" class="layui-btn layui-btn-normal" id="sbtn">确认修改</button>')
					    				if (Qadata == "是") {
					    					$("#Tclose").attr("checked","checked")
					    				}
					    				form.render('')

					    				
					    				$("#sbtn").click(function(){
											var Formdata1 = new FormData();
											if ($("Tclose").is(':checked')) {
												var isq = 1;
											} else{
												var isq = 0;
											}
											var Fdata = {"question":$("#tform textarea").eq(0).val(),"id":node.id,"is_qa":isq,"key_word":$("#key_word").val()}
											console.log(Fdata)
											Formdata1.append("data",JSON.stringify(Fdata))		
											Cajax(Formdata1)
										})
					    				
					    			}
					    				$("#trevise").click(function(){
											layer.open({
												title:"修改信息",
												type: 1,
												content: $('#tform'),
												area: ['600px','600px'],
												scrollbar:false
											})
										})
					    			
					    			
					    			//删除
					    			$("#tdel").click(function(){
					    				if (res.data.isBase==0) {
					    					var delMsg = "此标题下还有子内容，删除将一并删除，无法撤回"
					    				} else{
					    					var delMsg = "确认删除嘛？删除将无法撤回"
					    				}
					    				layer.open({
									        type: 1
									        ,title: false //不显示标题栏
									        ,closeBtn: false
									        ,area: '300px;'
									        ,shade: 0.8
									        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
									        ,btn: ['确认删除', '我再想想']
									        ,btnAlign: 'c'
									        ,moveType: 1 //拖拽模式，0或者1
									        ,content: '<div style="padding: 50px; line-height: 22px; background-color: #393D49; color: #fff; font-weight: 300;">'+delMsg+'</div>'
									        ,success: function(layero){
									          var btn = layero.find('.layui-layer-btn');
									          btn.find('.layui-layer-btn0').click(function(){
									          	$.ajax({
									          		type:"post",
									          		url:window.PRJ_ROOT+"/delete",
									          		async:true,
									          		data:{"id":res.data.id},
									          		success:function(res){
									          			layer.msg(JSON.parse(res).msg)
									          			window.location.reload()
									          		}
									          	});
									          });
									        }
									      });
					    			})
					    		}
					    	});
					    }
					    dataId = node.id;
					  }
					});
				}
			});
			
			$("#addBtn").click(function(){
				layer.open({
				  title:"添加新节点",
				  type: 2, 
				  area: ['1200px', '500px'],
				  content: ['addTab.html', 'no']
				})
			})
			
			
			
			
			//修改AJax
function Cajax(Formdata1){
									
		$.ajax({
			url: window.PRJ_ROOT+"/editIsmp",  
	        type: 'POST',  
	        data: Formdata1, 
	        cache: false,  
	        processData: false,  
	        contentType: false,
	        success:function(res){
	        	layer.msg(JSON.parse(res).msg)
	        	setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
				  window.location.reload();//页面刷新
				},1500);
	        }
		});
	}
})
//图片预览	
function getObjectURL(file) {
var url = null ;
if (window.createObjectURL!=undefined) { // basic
url = window.createObjectURL(file) ;
} else if (window.URL!=undefined) { // mozilla(firefox)
url = window.URL.createObjectURL(file) ;
} else if (window.webkitURL!=undefined) { // webkit or chrome
url = window.webkitURL.createObjectURL(file) ;
}
return url ;
}