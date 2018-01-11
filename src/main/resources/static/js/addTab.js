layui.use("element",function(){
			var $= layui.$,form=layui.form,layer=layui.layer;
			$.ajax({
				type:"get",
				url:window.PRJ_ROOT+"/getParent",
				async:true,
				success:function(res){
					console.log(JSON.parse(res))
					var data = JSON.parse(res).data;
				//父级菜单渲染	
					for (var i=0;i<data.length;i++) {
						$("#parents").append('<option value='+data[i]["id"]+' data-Qa='+data[i]["isQa"]+'>'+data[i]["question"]+'</option>')
					}
					form.render('');
				//父级菜单选择---联动
					form.on('select(pQ)', function(data){
						console.log(data.elem)
						if ($("#parents").find("option:selected").attr("data-Qa")==0) {
							//分类显示，类型默认选中项及下一级默认展示 以下类同
							$("#modules").html('<option value="1">只有问题</option> <option value="2">问题与回答</option>')
							form.render('')
							$("#onlyQ").show();
						  	$("#onlyQ").html('<div class="qbox"><label class="layui-form-label">问题</label><div class="layui-input-block qbox"><input type="text" class="layui-input" name="question" /></div></div><div class="gjzBox"><label class="layui-form-label" >关键字</label><div class="layui-input-inline keyWord" ><input type="text" name="keyWord" class="layui-input"></div></div><div class="sbox"><label class="layui-form-label" >是否QA问答</label><div class="layui-input-inline isQAswitch" ><input type="checkbox" name="close" lay-skin="switch" lay-text="是|否"></div></div><button class="layui-btn layui-btn-sm layui-btn-primary  layui-icon" type="button">&#xe654;</button>')
						  	form.render('');
						  	$("#QaA").hide();
						  	$("#QaA").html("");
						  	$("#QaI").hide();
						  	$("#QaI").html("");
						  	$("#onlyQ>.layui-btn").click(function(){
								$("#onlyQ").append('<div class="qbox"><label class="layui-form-label">问题</label><div class="layui-input-block qbox"><input type="text" class="layui-input" name="question" /></div></div><div class="gjzBox"><label class="layui-form-label" >关键字</label><div class="layui-input-inline keyWord" ><input type="text" name="keyWord" class="layui-input"></div></div><div class="sbox"><label class="layui-form-label" >是否QA问答</label><div class="layui-input-inline isQAswitch" ><input type="checkbox" name="close" lay-skin="switch" lay-text="是|否"></div></div>')
								form.render('');	
							})
						} else{
							$("#modules").html('<option value="3">描述与图片</option>')
							form.render('')
							$("#onlyQ").hide();$("#onlyQ").html("");
						  	$("#QaA").hide();	$("#QaA").html("");
						  	$("#QaI").show();$("#QaI").html('<div class="tqbox"><label class="layui-form-label">描述</label><div class="layui-input-block QaABOX"><input type="text" class="layui-input" name="question" /></div></div><div class="tabox"><label class="layui-form-label">图片</label><div class="layui-input-block QaABOX"><input type="file" class="layui-input" name="file" /></div></div><button class="layui-btn layui-btn-sm layui-btn-primary  layui-icon" type="button">&#xe654;</button>')
						  	
						  	$("#QaI>.layui-btn").click(function(){
								$("#QaI").append('<div class="tqbox"><label class="layui-form-label">描述</label><div class="layui-input-block QaABOX"><input type="text" class="layui-input" name="question" /></div></div><div class="tabox"><label class="layui-form-label">图片</label><div class="layui-input-block QaABOX"><input type="file" class="layui-input" name="file" /></div></div>')
							})
			  
		
						}
						form.render('')
						$("#tType").show()
					})
				}
			});
			//类型选择联动
			form.on('select(tType)', function(data){
			  if (data.value==1) {
			  	$("#onlyQ").show();
			  	$("#onlyQ").html('<div class="qbox"><label class="layui-form-label">问题</label><div class="layui-input-block qbox"><input type="text" class="layui-input" name="question" /></div></div><div class="gjzBox"><label class="layui-form-label" >关键字</label><div class="layui-input-inline keyWord" ><input type="text" name="keyWord" class="layui-input"></div></div><div class="sbox"><label class="layui-form-label" >是否QA问答</label><div class="layui-input-inline isQAswitch" ><input type="checkbox" name="close" lay-skin="switch" lay-text="是|否"></div></div><button class="layui-btn layui-btn-sm layui-btn-primary  layui-icon" type="button">&#xe654;</button>')
			  	form.render('');
			  	$("#QaA").hide();
			  	$("#QaA").html("");
			  	$("#QaI").hide();
			  	$("#QaI").html("");
			  	$("#onlyQ>.layui-btn").click(function(){
					$("#onlyQ").append('<div class="qbox"><label class="layui-form-label">问题</label><div class="layui-input-block qbox"><input type="text" class="layui-input" name="question" /></div></div><div class="gjzBox"><label class="layui-form-label" >关键字</label><div class="layui-input-inline keyWord" ><input type="text" name="keyWord" class="layui-input"></div></div><div class="sbox"><label class="layui-form-label" >是否QA问答</label><div class="layui-input-inline isQAswitch" ><input type="checkbox" name="close" lay-skin="switch" lay-text="是|否"></div></div>')
					form.render('');	
				})
			  } else if(data.value==2){
			  	$("#onlyQ").hide();$("#onlyQ").html("");
			  	$("#QaA").show();$("#QaA").html('<div class="tqbox"><label class="layui-form-label">问题</label><div class="layui-input-block QaABOX"><input type="text" class="layui-input" name="question" /></div></div><div class="tabox"><label class="layui-form-label">答案</label><div class="layui-input-block QaABOX"><input type="text" class="layui-input" name="answer" /></div></div><div class="gjzBox"><label class="layui-form-label" >关键字</label><div class="layui-input-inline keyWord" ><input type="text" name="keyWord" class="layui-input"></div></div><button class="layui-btn layui-btn-sm layui-btn-primary  layui-icon" type="button">&#xe654;</button>')
			  	$("#QaI").hide();$("#QaI").html("");
				  	$("#QaA>.layui-btn").click(function(){
					$("#QaA").append('<div class="tqbox"><label class="layui-form-label">问题</label><div class="layui-input-block QaABOX"><input type="text" class="layui-input" name="question" /></div></div><div class="tabox"><label class="layui-form-label">答案</label><div class="layui-input-block QaABOX"><input type="text" class="layui-input" name="answer" /></div></div><div class="gjzBox"><label class="layui-form-label" >关键字</label><div class="layui-input-inline keyWord" ><input type="text" name="keyWord" class="layui-input"></div></div>')
				})
			  }else{
			  	$("#onlyQ").hide();$("#onlyQ").html("");
			  	$("#QaA").hide();	$("#QaA").html("");
			  	$("#QaI").show();$("#QaI").html('<div class="tqbox"><label class="layui-form-label">描述</label><div class="layui-input-block QaABOX"><input type="text" class="layui-input" name="question" /></div></div><div class="tabox"><label class="layui-form-label">图片</label><div class="layui-input-block QaABOX"><input type="file" class="layui-input" name="file" /></div></div><button class="layui-btn layui-btn-sm layui-btn-primary  layui-icon" type="button">&#xe654;</button>')
			  	
			  	$("#QaI>.layui-btn").click(function(){
					$("#QaI").append('<div class="tqbox"><label class="layui-form-label">描述</label><div class="layui-input-block QaABOX"><input type="text" class="layui-input" name="question" /></div></div><div class="tabox"><label class="layui-form-label">图片</label><div class="layui-input-block QaABOX"><input type="file" class="layui-input" name="file" /></div></div>')
				})
			  }
			}); 
			$("#onlyQ>.layui-btn").click(function(){
				$("#onlyQ").append('<div class="qbox"><label class="layui-form-label">问题</label><div class="layui-input-block qbox"><input type="text" class="layui-input" name="question" /></div></div><div class="gjzBox"><label class="layui-form-label" >关键字</label><div class="layui-input-inline keyWord" ><input type="text" name="keyWord" class="layui-input"></div></div><div class="sbox"><label class="layui-form-label" >是否QA问答</label><div class="layui-input-inline isQAswitch" ><input type="checkbox" name="close" lay-skin="switch" lay-text="是|否"></div></div>')
				form.render('');	
			})
			
			//提交
			$("#btnS").click(function(){
				console.log($(".isQAswitch>input").is(':checked'))
				if ($("#modules").val()==1) {
					var Formdata1 = new FormData($("#form")[0])
					Formdata1.delete("close")
					Formdata1.delete("modules")
					
					for (var i=0;i<$("#onlyQ .sbox input").length;i++) {
						if ($(".isQAswitch>input").eq(i).is(':checked')) {
							Formdata1.append("isQa",1)
						} else{
							Formdata1.append("isQa",0)
						}
					}
				}else if($("#modules").val()==2){
					var Formdata1 = new FormData($("#form")[0])
					Formdata1.delete("modules")
				}else if($("#modules").val()==3){
					var Formdata1 = new FormData($("#form")[0])
					Formdata1.delete("modules")
				}
				$.ajax({  
			            url: window.PRJ_ROOT+"/addIsmp",  
			            type: 'POST',  
			            data: Formdata1, 
			            cache: false,  
			            processData: false,  
			            contentType: false,
			            success:function(res){
			            	layer.msg(JSON.parse(res).msg)
			            	setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
			            		parent.layer.closeAll()
							   parent.window.location.reload();//页面刷新
							},1000);
			            }
			        })
			})
			
		})