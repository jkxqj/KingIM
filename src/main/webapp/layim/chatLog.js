
var toId=$("#toId").val();
var userId=$("#userId").val();
var type=$("#type").val();
var htm="";
var pageNum=2;

$(document).ready(function(){
  var jsonStr=$("#message").val();
  var dataObj=eval("("+jsonStr+")");
  console.log(dataObj);
  getDetail(dataObj);
  $("#moreLog").click(function(){
     $.post('api/friend/getHistoryMessageFromMongo',{'id':toId,'type':type,'pageNum':pageNum},function(result){
    	 var data=eval("("+result+")");
    	 console.log(data);
    	 if(data.length==0){
    		 alert("没有更多消息！");
    		 return;
    	 }
    	 getDetail(data);
    	 pageNum++;
     })
  });
});

function getDetail(dataObj){
	htm="";
	console.log(dataObj);
	if(type=="friend" || type=="fankui"){
	   	for(var i=0;i<dataObj.length;i++){
	   		if(dataObj[i].friend_message.mine.id==userId){
		     htm+='<li class="layim-chat-mine">'
				 +'  <div class="layim-chat-user">'
				 +'	  <img src="'+dataObj[i].friend_message.mine.avatar+'">'
				 +'	  <cite><i>'+dataObj[i].friend_message.time+'</i>'+dataObj[i].friend_message.mine.username+'</cite>'
				 +'  </div>'
				 +'  <div class="layim-chat-text">'+ parent.layui.layim.content(dataObj[i].friend_message.mine.content)+'</div>'
				 +'</li>'
			 }else{
			     htm+='<li>'
					 +'  <div class="layim-chat-user">'
					 +'	  <img src="'+dataObj[i].friend_message.mine.avatar+'">'
					 +'	  <cite>'+dataObj[i].friend_message.mine.username+'<i>'+dataObj[i].friend_message.time+'</i></cite>'
					 +'  </div>'
					 +'  <div class="layim-chat-text">'+parent.layui.layim.content(dataObj[i].friend_message.mine.content)+'</div>'
					 +'</li>'
			 }
		}
	}else if(type=="group"){
	   	for(var i=0;i<dataObj.length;i++){
	   		if(dataObj[i].group_message.mine.id==userId){
		     htm+='<li class="layim-chat-mine">'
				 +'  <div class="layim-chat-user">'
				 +'	  <img src="'+dataObj[i].group_message.mine.avatar+'">'
				 +'	  <cite><i>'+dataObj[i].group_message.time+'</i>'+dataObj[i].group_message.mine.username+'</cite>'
				 +'  </div>'
				 +'  <div class="layim-chat-text">'+ parent.layui.layim.content(dataObj[i].group_message.mine.content)+'</div>'
				 +'</li>'
			 }else{
			     htm+='<li>'
					 +'  <div class="layim-chat-user">'
					 +'	  <img src="'+dataObj[i].group_message.mine.avatar+'">'
					 +'	  <cite>'+dataObj[i].group_message.mine.username+'<i>'+dataObj[i].group_message.time+'</i></cite>'
					 +'  </div>'
					 +'  <div class="layim-chat-text">'+parent.layui.layim.content(dataObj[i].group_message.mine.content)+'</div>'
					 +'</li>'
			 }
		}
	}

    $("#messageList").prepend(htm);
}
