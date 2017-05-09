var chiefTeacherId,realName;
var userArray=new Array();
var userOnlineStr="";
var onlineCount=0;
var k=2;//1单聊  2群聊
 
//var host="ws://10.144.238.15:8080/starcLL/LL_ws/"+uname+"_"+sid;
 

var ws = "";
if ('WebSocket' in window) {  
    ws=new WebSocket(host);  
} else if ('MozWebSocket' in window) {  
    ws= new MozWebSocket(host);
} else { 
	layui.use(['layer'], function(){
		  var layer = layui.layer;
		  layer.alert("您的浏览器不支持webscoket协议,建议使用新版谷歌、火狐等浏览器，请勿使用IE10以下浏览器，360浏览器请使用极速模式，不要使用兼容模式！"); 
	});
}

ws.onopen = function(e) {
	console.log("ws连接成功！");
	  var obj={
			  	type:3,
	  			userName:uname+"_"+sid,
	  			contentType:"online",
	  			to:"all"
	  		  }
	  var message =JSON.stringify(obj);
	  ws.send(message);
}
	
ws.onclose = function(e) {
	console.log("ws断开连接！");
}
//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。  
window.onbeforeunload = function () {  
  	ws.close();  
}  
ws.onmessage = function(e) {
		var htm="";
		var v=eval("("+e.data+")");
		console.log(v);
		if(v.time!=null){
			var date= new Date(v.time);
			localStorage.setItem("newReadTime_"+uname, date.getTime());
		}
		if(v.contentType=='text'){
			if(v.userName==uname){
				htm='<div class="contents-div">'
				+'  <div class="user-mine-img"> '
				+'    <span><label class="time" style="margin-right:10px;display:inline-block;">'+v.time+'</label>我</span>'
				+'    <img src="static/img/1.png" alt="" />'
				+'  </div>'
				+'  <div class="user-mine-content">'
				+'    <div class="user-mine-text"> '+v.content+'</div>'
				+'  </div>'
				+'</div>';
			}else{
				htm='<div class="contents-div">'
				+'  <div class="user-img">'
				+'    <img src="static/img/1.png" alt="" />'
				+'    <span>'
				for(var i=0;i<userArray.length;i++){
					if(userArray[i].userName==v.userName)
						htm+=userArray[i].realName;
				}
				htm+='<label class="time">'+v.time+'</label></span>'
				+'  </div>'
				+'  <div class="user-content">'
				+'    <div class="user-text">'+v.content+' </div>'
				+'  </div>'
				+'</div>';
				newsChange();
			}
		}else if(v.contentType=='img'){   //发的是图片
			if(v.userName==uname){
				htm='<div class="contents-div">'
				+'  <div class="user-mine-img"> '
				+'    <span> <label class="time" style="margin-right:10px;display:inline-block;">'+v.time+'</label>我</span>'
				+'    <img src="static/img/1.png" alt="" />'
				+'  </div>'
				+'  <div class="user-mine-content">'
				+'    <div class="user-mine-text-img"><img src="'+v.content.previewUrl.url+'" data-url="'+v.content.url+'" /></div>'
				+'  </div>'
				+'</div>'
				
			}else{
				htm='<div class="contents-div">'
				+'  <div class="user-img">'
				+'    <img src="static/img/1.png" alt="" />'
				+'    <span>'
				for(var i=0;i<userArray.length;i++){
					if(userArray[i].userName==v.userName)
						htm+=userArray[i].realName;
				}
				htm+='<label class="time">'+v.time+'</label></span>'
				+'  </div>'
				+'  <div class="user-content">'
				+'    <div class="user-text-img"><img src="'+v.content.previewUrl.url+'" data-url="'+v.content.url+'" /></div>'
				+'  </div>'
				+'</div>'
				newsChange();
			}
		}else if(v.contentType=='file'){      //发的是文件
			if(v.userName==uname){
				htm='<div class="contents-div">'
				+'  <div class="user-mine-img">'
				+'    <span><label class="time" style="margin-right:10px;display:inline-block;">'+v.time+'</label>我</span>'
				+'    <img src="static/img/1.png" alt="" />'
				+'  </div>'
				+'  <div class="user-mine-content">'
				+'    <div class="user-mine-text-file">'
				+'      <div style="float:left;">'
				+'        <img src="static/img/files.jpg" alt="" />'
				+'      </div>'
				+'      <p style="float:left;padding-left:10px;font-size: 16px;">'
				+'      <span style="display:inline-block;margin-top:10px;">'+v.content.name+'</span> <span>('+v.content.size+')</span> '
				+'      <br><a style="display:inline-block;margin-top:20px;font-size: 16px;float: left;" target="_blank" href="'+v.content.url+'">'
				+'      <span class="glyphicon glyphicon-save">下载</span></a></p>'
				+'    </div>'
				+'  </div>'
				+'</div>'
				
			}else{			
				htm='<div class="contents-div">'
				+'  <div class="user-img">'
				+'    <img src="static/img/1.png" alt="" />'
				+'    <span>'
				for(var i=0;i<userArray.length;i++){
					if(userArray[i].userName==v.userName)
						htm+=userArray[i].realName;
				}
				htm+='<label class="time">'+v.time+'</label></span>'
				+'  </div>'
				+'  <div class="user-content">'
				+'    <div class="user-text-file">'
				+'      <div style="float:left;">'
				+'        <img src="static/img/files.jpg" alt="" />'
				+'      </div>'
				+'      <p style="float:left;padding-left:10px;font-size: 16px;"><span style="display:inline-block;margin-top:10px;">'+v.content.name+'</span> '
				+'      <span>('+v.content.size+')</span> <br>'
				+'      <a style="display:inline-block;margin-top:20px;font-size: 16px;" target="_blank" href="'+v.content.url+'">'
				+'      <span class="glyphicon glyphicon-save"></span>下载</a></p>'
				+'    </div>'
				+'  </div>'
				+'</div>'
				newsChange();
			}
		}else if(v.contentType=="online"){
			if(v.userName!=(uname+"_"+sid)){
				console.log( $("#"+v.userName).attr("data-name")+" 上线啦");
				onlineCount++;
				$("#onlineCount").text(onlineCount);
				$("#"+v.userName+" img").removeClass();
				$("#"+v.userName+" i").text("[在线]");
				$("[data-status='online']").last().after($("#"+v.userName));
				$("#"+v.userName).attr("data-status","online");
			}
		}else if(v.contentType=="offline"){
			$("#"+v.userName).attr("data-status","offline");
			if(v.userName!=(uname+"_"+sid)){
				onlineCount--;
				$("#onlineCount").text(onlineCount);
				console.log( $("#"+v.userName).attr("data-name")+" 下线啦" )		
				$("#"+v.userName+" img").addClass("imgGray");
				$("#"+v.userName+" i").text("[离线]");
				$("[data-status='online']").last().after($("#"+v.userName))
			}
		}
		$(".contents").append(htm);
}
	
ws.onerror = function(e) {
	layui.use(['layer'], function(){
		  var layer = layui.layer;
		  layer.alert("ws发生错误,请勿使用IE9以下浏览器，360浏览器请使用极速模式，不要使用兼容模式！"); 
	});
}

$(document).ready(function(){
	var window_width=$(window).width();
	var window_height=$(window).height();
	var doc_height=$(document).height();
	$("body").width(window_width);
	$(".outerDiv").width(window_width);
	$(".outerDiv").height(window_height);
	$("#leftDiv").width(window_width*0.25-10);
	$("#mainDiv").width(window_width*0.5-20);
	$("#rightDiv").width(window_width*0.25-10);
	$("#leftDiv").height(window_height-30);
	$("#mainDiv").height(window_height-30);
	$("#rightDiv").height(window_height-40);
	$(".news-alert").width(window_width*0.5-30);
	$("#div_msgpanel").height(window_height*0.75-50);
	$("#div_msgbox").height(window_height*0.75-75);
	$(".textarea").height(window_height*0.15);
	$("#imgCon").width(window_width);
	$("#imgCon").height(window_height);
	$(".panel-heading").width($("#div_msgbox").width());
	//$(".panel-heading").width($("#div_msgpanel").width());
	//$("#div_msgbox").width($(".panel-heading").width());
	resize();
	$.ajax({    			//获取课堂信息
		url:"qunController/getSiteById",
		type: "POST",
		async : true,
		data: {"siteId":sid},
		dataType: "json",
		success: function(result){			
			//console.log(result);
			$("#className").html(result.title);
			/*$("#classDes").html(result.description);*/
			chiefTeacherId=result.chiefTeacherId;
		}
	}) 
	
	//聊天记录查询
	$("#btn_record").on('click',function(){
		showRecord();
	});
	
	$.ajax({               //获取群成员列表信息
		url:"qunController/getMemberBySiteId",
		type: "POST",
		async : true,
		data: {"siteId":sid},
		beforeSend:function (){	
			layui.use(['layer'], function(){
				  var layer = layui.layer;
				  oppup = layer.load(3);	  
			});
		  },
		dataType: "json",
		success: function(result){			
			console.log(result.length);
			$("#totalCount").text(result.length);   //总人数展示
			var htm="";
			$.each(result,function(k,v){
				if(chiefTeacherId==v.userName){
					$("#classTea").html(v.realName);   
				}
				if(uname==v.userName){
					realName=v.realName;
					$("#realName").attr("value",realName);
				}
				userArray.push(v);
				if(v.role=='teacher'){
					if(v.status==0){   //不在线则头像置灰
						   htm+= '<li id="'+v.userName+"_"+sid+'" data-name="'+v.realName+'" data-role="'+v.role+'" data-status="offline">'
								+'  <img src="static/img/1.png" class="imgGray" alt="">'
								+'  <span><img src="static/img/group-owner.png">'+v.realName+'老师<i>[离线]</i></span>'
								+'</li>'
					}else{
						   htm+= '<li id="'+v.userName+"_"+sid+'" data-name="'+v.realName+'" data-role="'+v.role+'" data-status="online">'
								+'  <img src="static/img/1.png"  alt="">'
								+'  <span><img src="static/img/group-owner.png">'+v.realName+' 老师<i>[在线]</i></span>'
								+'</li>'
							onlineCount++;
					}

				}else {
					if(v.status==0){   //不在线则头像置灰
						htm+='<li id="'+v.userName+"_"+sid+'" data-name="'+v.realName+'" data-role="'+v.role+'" data-status="offline">'
							+'  <img src="static/img/1.png" class="imgGray" alt="">'
							+'  <span> '+v.realName+'('+v.userName+')学生 <i>[离线]</i></span>'
							+'</li>'
					}else{
						htm+='<li id="'+v.userName+"_"+sid+'" data-name="'+v.realName+'" data-role="'+v.role+'" data-status="online">'
							+'  <img src="static/img/1.png" alt="">'
							+'  <span> '+v.realName+'('+v.userName+')学生<i>[在线]</i></span>'
							+'</li>'
							onlineCount++;
					}
				}
			})
			$("#onlineCount").text(onlineCount);
			getLatestHistory();
			
			$(".userList").html(htm);
			layui.use(['layer'], function(){
				  var layer = layui.layer;
				  layer.close(oppup);	  
			});
		}
	});
	//发消息滚动到底部
	$("#inp_say").keydown(function(event){
        if(event.ctrlKey && event.which == 13) 
        	setTimeout("newsAdd()",50);//13等于回车键(Enter)键值,ctrlKey 等于 Ctrl
    });
	
	getUnreadRecord();
})
 
function resize(){
	$(window).resize(function(){
		window_width=$(window).width();
		window_height=$(window).height();
		doc_height=$(document).height();
		$("body").width(window_width);
		$(".outerDiv").width(window_width);
		$("#leftDiv").width(window_width*0.25-10);
		$("#mainDiv").width(window_width*0.5-20);
		$("#rightDiv").width(window_width*0.25-10);
		$("#leftDiv").height(window_height-20);
		$("#mainDiv").height(window_height-20);
		$("#rightDiv").height(window_height-20);
		$(".news-alert").width(window_width*0.5-30);
		$("#div_msgpanel").height(window_height*0.75-50);
		$(".textarea").height(window_height*0.15);
		$("#div_msgbox").height(window_height*0.75-75);
		$(".panel-heading").width($("#div_msgbox").width());
	});
}
//搜索用户
function searchUser(input){
	 var htm="";
	 for(var i=0;i<userArray.length;i++){
 		 if(userArray[i].realName.indexOf(input)>=0 || userArray[i].userName.indexOf(input)>=0){
 			if(userArray[i].role=='teacher'){
				if(userArray[i].status==0){   //不在线则头像置灰
					   htm+= '<li>'
							+'  <img src="static/img/1.png" class="imgGray" alt="">'
							+'  <span id="'+userArray[i].userName+'"><img src="static/img/group-owner.png">'+userArray[i].realName+' 老师 <i>[离线]</i> </span>'
							+'</li>'
				}else{
					   htm+= '<li>'
							+'  <img src="static/img/1.png"  alt="">'
							+'  <span id="'+userArray[i].userName+'"><img src="static/img/group-owner.png">'+userArray[i].realName+' 老师 <i>[在线]</i></span>'
							+'</li>'
				}
			}else {
				if(userArray[i].status==0){   //不在线则头像置灰
					htm+='<li>'
						+'  <img src="static/img/1.png" class="imgGray" alt="">'
						+'  <span id="'+userArray[i].userName+'"> '+userArray[i].realName+'('+userArray[i].userName+')学生<i>[离线]</i></span>'
						+'</li>'
				}else{
					htm+='<li>'
						+'  <img src="static/img/1.png" alt="">'
						+'  <span id="'+userArray[i].userName+'"> '+userArray[i].realName+'('+userArray[i].userName+')学生 <i>[在线]</i> </span>'
						+'</li>'
				}
			}
		 } 
	 }
	 htm+='<br><br>'
	 $(".userList").html(htm);
}

//搜索成员按钮
$("#searchUserBtn").click(function(){    
	 var input=$("#searchUserInput").val().trim();
	 searchUser(input);
})

//发送按钮
$("#btn_say").click(function(){
	sentMsg();
})

//获取最近消息
function getLatestHistory(){	
	$.ajax({
		url:"qunController/getNewRecord",
		type: "POST",
		async : true,
		data: {"siteId":sid,"pageSize":5},
		dataType: "json",
		success: function(result){		
			var htm="";
			$.each(result,function(k,v){
				if(v.contentType=='text'){
					if(v.userName==uname){
						htm='<div class="contents-div">'
						+'  <div class="user-mine-img"> '
						+'    <span><label class="time" style="margin-right:10px;display:inline-block;">'+v.date+' '+v.time+'</label>我</span>'
						+'    <img src="static/img/1.png" alt="" />'
						+'  </div>'
						+'  <div class="user-mine-content">'
						+'    <div class="user-mine-text"> '+v.content+'</div>'
						+'  </div>'
						+'</div>';
					}else{
						htm='<div class="contents-div">'
						+'  <div class="user-img">'
						+'    <img src="static/img/1.png" alt="" />'
						+'    <span>'
						for(var i=0;i<userArray.length;i++){
							if(userArray[i].userName==v.userName)
								htm+=userArray[i].realName;
						}
						htm+='<label class="time">'+v.date+' '+v.time+'</label></span>'
						+'  </div>'
						+'  <div class="user-content">'
						+'    <div class="user-text">'+v.content+' </div>'
						+'  </div>'
						+'</div>';
					}
				}else if(v.contentType=='img'){   //发的是图片
					if(v.userName==uname){
						htm='<div class="contents-div">'
						+'  <div class="user-mine-img"> '
						+'    <span> <label class="time" style="margin-right:10px;display:inline-block;">'+v.date+' '+v.time+'</label>我</span>'
						+'    <img src="static/img/1.png" alt="" />'
						+'  </div>'
						+'  <div class="user-mine-content">'
						+'    <div class="user-mine-text-img"><img src="'+v.content.previewUrl.url+'" data-url="'+v.content.url+'" /></div>'
						+'  </div>'
						+'</div>'
						
					}else{
						htm='<div class="contents-div">'
						+'  <div class="user-img">'
						+'    <img src="static/img/1.png" alt="" />'
						+'    <span>'
						for(var i=0;i<userArray.length;i++){
							if(userArray[i].userName==v.userName)
								htm+=userArray[i].realName;
						}
						htm+='<label class="time">'+v.date+' '+v.time+'</label></span>'
						+'  </div>'
						+'  <div class="user-content">'
						+'    <div class="user-text-img"><img src="'+v.content.previewUrl.url+'" data-url="'+v.content.url+'" /></div>'
						+'  </div>'
						+'</div>';
					}
				}else if(v.contentType=='file'){      //发的是文件
					if(v.userName==uname){
						htm='<div class="contents-div">'
						+'  <div class="user-mine-img">'
						+'    <span><label class="time" style="margin-right:10px;display:inline-block;">'+v.date+' '+v.time+'</label>我</span>'
						+'    <img src="static/img/1.png" alt="" />'
						+'  </div>'
						+'  <div class="user-mine-content">'
						+'    <div class="user-mine-text-file">'
						+'      <div style="float:left;">'
						+'        <img src="static/img/files.jpg" alt="" />'
						+'      </div>'
						+'      <p style="float:left;padding-left:10px;font-size: 16px;">'
						+'      <span style="display:inline-block;margin-top:10px;">'+v.content.name+'</span> <span>('+v.content.size+')</span> '
						+'      <br><a style="display:inline-block;margin-top:20px;font-size: 16px;float: left;" target="_blank" href="'+v.content.url+'">'
						+'      <span class="glyphicon glyphicon-save">下载</span></a></p>'
						+'    </div>'
						+'  </div>'
						+'</div>'
						
					}else{			
						htm='<div class="contents-div">'
						+'  <div class="user-img">'
						+'    <img src="static/img/1.png" alt="" />'
						+'    <span>'
						for(var i=0;i<userArray.length;i++){
							if(userArray[i].userName==v.userName)
								htm+=userArray[i].realName;
						}
						htm+='<label class="time">'+v.date+' '+v.time+'</label></span>'
						+'  </div>'
						+'  <div class="user-content">'
						+'    <div class="user-text-file">'
						+'      <div style="float:left;">'
						+'        <img src="static/img/files.jpg" alt="" />'
						+'      </div>'
						+'      <p style="float:left;padding-left:10px;font-size: 16px;"><span style="display:inline-block;margin-top:10px;">'+v.content.name+'</span> '
						+'      <span>('+v.content.size+')</span> <br>'
						+'      <a style="display:inline-block;margin-top:20px;font-size: 16px;" target="_blank" href="'+v.content.url+'">'
						+'      <span class="glyphicon glyphicon-save"></span>下载</a></p>'
						+'    </div>'
						+'  </div>'
						+'</div>'
						
					}
				}
				$(".contents").prepend(htm);
			})
		}
	}) 
	
}
function getOnLineUsers(){
	 userOnlineStr="";
	 $("[data-status='online']").each(function(k,v){
		 userOnlineStr+=$(v).attr("id")+",";
	 })
	 console.log("userOnlineStr="+userOnlineStr);
}
//标签过滤
var filter=function (content) {

    content.find('*[style]').each(function(){
        var textAlign = this.style.textAlign;
        this.removeAttribute('style');
        $(this).css({
            'text-align': textAlign || ''
        })
    });
   
    //移除不安全的标签
    content.find("div,p,span,h1,h2,h3,h4,h5,h6,li,ul,ol").removeClass();
    content.find("div,p,span,h1,h2,h3,h4,h5,h6,li,ul,ol").style="";
    content.find('script,link').remove();  
}

//发送消息
function sentMsg(){
		var contentType="text";
		 var  re = /<(?!img|br).*?>/ig;//保留img和input标签
		getOnLineUsers();
		console.log($("#inp_say").html());
		filter($("#inp_say"));
		var inp_say = $("#inp_say").html();
		inp_say= inp_say.replace(/&lt;/ig,'<');
		inp_say= inp_say.replace(/&gt;/ig,'>');
		/*inp_say= inp_say.replace("</li><li>","<br>").replace("<\li>","<br>");
		inp_say= inp_say.replace("</div><div>","<br>").replace("<\div>","<br>");*/
		inp_say= inp_say.replace(/<(?!\/?BR|\/?IMG|\/n|\/?div)[^<>]*>/ig,'')
		console.log(inp_say);
		if(inp_say.length<=0){
			layui.use(['layer'], function(){
				  var layer = layui.layer;
				  layer.alert("发送内容必须为纯文本且不能为空!");	  
			});
			return;
		}
		if(inp_say.length>3000){
			layui.use(['layer'], function(){
				  var layer = layui.layer;
				  layer.alert("每次发送内容不能超过3000字!");	  
			});
			return;
		}
		sendMessage(realName,uname,userOnlineStr,sid,inp_say,contentType);
		console.log("userOnlineStr="+userOnlineStr);
		$("#inp_say").html("");
		setTimeout("newsAdd()",50);
}

//ctrl+enter 发消息
function keycode(event){
	  event = event || window.event;  
	  if (event.ctrlKey && event.keyCode == 13)  {  
		  sentMsg();
	  } 
}
//enter 发消息
/*function keycode(event){
	  event = event || window.event;  
	  if (event.keyCode == 13)  {  
		  sentMsg();
	  } 
}*/

//课堂成员列表 链接
function getUserList(){
	 searchUser('');
}
	
/*表情插件*/
$('.emoji').qqFace({
		id : 'facebox', 
		assign:'inp_say', 
		path:'static/emotion/'	//表情存放的路径
});

//讨论组文件
$(".file-btn").click(function(){
	getFileList(1);
})

// 清空屏幕按钮
$("#msg_clear").click(function(){
	$(".contents").html("");
	layui.use(['layer'], function(){
		  var layer = layui.layer;
		  layer.msg("清屏成功,记录可在【聊天记录】中查看！"); 
	});
})
//获取未读消息总数
function getUnreadRecord(){
	$.post(basePath+"qunController/getUnreadRecord",{"siteId":sid,"timeLong":localStorage.getItem("newReadTime_"+uname)},function(result){
		//console.log(result);
		//alert($(".news-alert").html());
		var num=result-5;
		console.log("num="+num)
		if(num>0){
			$(".news-alert").fadeIn(500);
			$(".news-alert").html("您有"+num+"条未读消息，点击【聊天记录】查看详情！");
			$(".news-alert").fadeOut(5000);
		}
	});
}
 

/*var realName="测试教师";
var sendTo="qwe104,ewrrwer,fdgdfg,tea,rerere";
var content="飞机开始的恢复快第三个规范的健康过恢复健康的jhkhhjhj899";
var k=2;//1单聊2群聊
var contentType="text";
*/
function sendMessage(realName,userName,sendTo,siteId,content,contentType){
	var obj={
			 	type:k,
				realName:realName,
				userName:userName,
				to:sendTo,
				siteId:siteId,
				content:content,
				contentType:contentType
	   		}
	var message =JSON.stringify(obj);
	
	console.log(message);
	
	ws.send(message);   //发到websocket

	var obj1={
				type:k,
				userName:userName,
				siteId:siteId,
				content:content,
				contentType:contentType
			 }
	
	//console.log(JSON.stringify(obj1));
 
	$.post("qunController/addRecord",{message:JSON.stringify(obj1),siteId:siteId},null);  //存到mongo
}

$("body").on('dblclick','.user-mine-text-img img,.user-text-img img,.user-mine-text img',function(){
	var This=$(this);
	var theImage = new Image();
	theImage.src = This.attr("data-url");
	var imageWidth = theImage.width;
	var imageHeight = theImage.height;
	$("#imgDiv").show();
	$("#imgDiv").width(imageWidth+2);
	$("#imgDiv").height(imageHeight+2);
	$("#imgCon").show();	
	$("#imgShow").show();
	$("#imgShow").attr("src", theImage.src);
})
$("body").on('click','.img-close',function(){
	var This=$(this);
	$("#imgCon").hide();
	$("#imgShow").hide();
	$("#imgShow").attr("src", "");
})

/*点击最新消息提示到最底部*/
$(".news-alert").on('click',function(){
	var div = document.getElementById('div_msgpanel');
	var lastH=$(".contents .contents-div").last().height();
	var hh=div.scrollHeight+lastH;
	div.scrollTop=hh;
	$(".news-alert").hide();
});

function showRecord(){
	var a=$(window).width();
	var b=$(window).height();
	layui.use(['layer'], function(){
		  var layer = layui.layer;
		  layer.open({
			  type: 2, 
			  title: '历史消息记录',
			  /*scrollbar: true,*/
			  area: ['60%', '95%'],
			  content: 'qunController/chatLog?sid='+sid+'&uname='+uname,  
		  });	  
	});
}

/*发消息滚动到最底部*/
function newsAdd(){
	var div = document.getElementById('div_msgbox');
	var lastH=$(".contents .contents-div").last().height();
	var hh=div.scrollHeight+lastH;
	//alert(hh);
	div.scrollTop=hh;
}

/*新消息提示*/
function newsChange(){
	var div = document.getElementById('div_msgpanel');
	/*var lastH=$(".contents .contents-div").last().height();
	var hh=div.scrollHeight+lastH;*/
	//alert(div.scrollTop+"....."+div.scrollHeight);
	if(div.scrollTop<div.scrollHeight-700){
		$(".news-alert").fadeIn(500);
		$(".news-alert").html("有新的未读消息，点击查看！");
		$(".news-alert").fadeOut(5000);
	}else{
		setTimeout("newsAdd()",50);
	};
}

function getFileList(curr){
	var htm='<div class="form-group" style="margin-top:10px;"><input type="text" style="display:inline-block;width: 50%;" id="searchFileInput" class="form-control col-md-6" placeholder="请输入文件名称">'
		+'&nbsp;<button type="button" style="display:inline-block;width: 20%;" class="btn btn-default" id="searchFileBtn">搜索</button></div> <div id="pageDiv"></div>';
	$(".filesDiv").html(htm);
	
	$.ajax({
		url:"qunController/getHistoryFile",
		type: "POST",
		async : true,
		data: {"siteId":sid,"pageSize":5,"pageNum":curr,"contentType":"file"},
		dataType: "json",
		success: function(result){	
			console.log(result);	
			var htm="";
			$.each(result.list,function(k,v){
			htm+='<ul class="file-list list-unstyled">'
				+'            <li>'
				+'   			<div class="file-img">'
				+'				 <img src="static/img/files.jpg" alt="" />'	
				+'                <div class="file-info">'
				+'                    <div class="file-title">'
				+'						<span class="pull-left" title="'+v.content.name+'"> <a href="'+v.content.url+'" target="_blank" class="glyphicon glyphicon-cloud-download" title="下载"></a> '+v.content.name+'</span><span class="pull-right">'+v.content.size+'</span></div>'
				+'                    <div class="file-title">'
				+'                        <span class="pull-left"><i class="layui-icon" style="font-size: 16px; color: #1E9FFF;">&#xe612;</i> '
				for(var i=0;i<userArray.length;i++){
					if(userArray[i].userName==v.userName)
						htm+=userArray[i].realName;
				}
				htm+=' </span>'
				+'                        <span class="pull-right"> '+v.date+' '+v.time+' </span>'
				+'                    </div>'
				+'                </div>'
				+'              </div>'
				+'            </li>'
				+'</ul>'
			})
			$(".filesDiv").append(htm);
			
			layui.use(['laypage'], function(){
				var laypage = layui.laypage;
			    laypage({
		            cont: 'pageDiv',                 
		            pages: result.totalPage,      
		           	groups: 6 ,                
		            curr: curr || 1,              
		            jump: function(obj, first){    
		               if(!first){                 
		            	   getFileList(obj.curr);
		               }
		            }
		         }) 
			});
			
		}
	}) 
}

$("body").on('click','#searchFileBtn',function(){
	var input=$("#searchFileInput").val().trim();
	if(input==''||input==null){
		layui.use(['layer'], function(){
			var layer = layui.layer;
			layer.msg("搜索内容不能为空！")
			
		})
		return;
	}
	$.ajax({
		url:"qunController/searchHistoryFile",
		type: "POST",
		async : true,
		data: {"siteId":sid,"pageSize":10,"pageNum":1,"contentType":"file","name":input},
		dataType: "json",
		success: function(result){			
			console.log(result)
			var htm='';
			$.each(result.list,function(k,v){
				htm+='<ul class="file-list list-unstyled">'
					+'            <li>'
					+'   			<div class="file-img">'
					+'				 <img src="static/img/files.jpg" alt="" />'	
					+'                <div class="file-info">'
					+'                    <div class="file-title">'
					+'						<span class="pull-left" title="'+v.content.name+'"> <a href="'+v.content.url+'" target="_blank" class="glyphicon glyphicon-cloud-download" title="下载"></a> '+v.content.name+'</span><span class="pull-right">'+v.content.size+'</span></div>'
					+'                    <div class="file-title">'
					+'                        <span class="pull-left"><i class="layui-icon" style="font-size: 16px; color: #1E9FFF;">&#xe612;</i> '
					for(var i=0;i<userArray.length;i++){
						if(userArray[i].userName==v.userName)
							htm+=userArray[i].realName;
					}
					htm+=' </span>'
					+'                        <span class="pull-right"> '+v.date+' '+v.time+' </span>'
					+'                    </div>'
					+'                </div>'
					+'              </div>'
					+'            </li>'
					+'</ul>'
				})
				$(".filesDiv").html(htm);
			
		}
	}) 
})
