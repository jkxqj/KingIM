var userId=$("#userId").val();
var socket = null;  // 判断当前浏览器是否支持WebSocket
if ('WebSocket' in window) {
	socket = new WebSocket("ws://localhost:8080/LLWS/"+userId);
} else {
	alert('该浏览器不支持本系统即时通讯功能，推荐使用谷歌或火狐浏览器！');
}
layui.use('layim', function(layim){
  var autoReplay = [
    '您好，我现在有事不在，一会儿再和您联系。'
  ];
 layim.config({ 
    init: {
      url: '/index/getInitList?userId='+userId
      ,data: {}
    }
    ,brief: false
    //查看群员接口
    ,members: {
       url: 'group/getMemberByGroupId'
      ,data: {}
    }
    ,uploadImage: {
       url: '/sns/uploadFile?userId='+userId
      ,type: '' //默认post
    }
    ,uploadFile: {
       url: '/sns/uploadFile?userId='+userId
      ,type: '' //默认post
    }
    ,min:true
    ,title: 'KingIM'        //主面板最小化后显示的名称
    ,msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
    ,find: layui.cache.dir + 'css/modules/layim/html/find.html' //发现页面地址，若不开启，剔除该项即可
    ,chatLog: layui.cache.dir + 'css/modules/layim/html/chatlog.html' //聊天记录页面地址，若不开启，剔除该项即可
    ,copyright: true          //是否授权
    ,right: '30px'
    ,notice:true      //开启桌面消息提醒

  });
 
	// 连接发生错误的回调方法
	socket.onerror = function() {
		console.log("llws连接失败!");
	};
	// 连接成功建立的回调方法
	socket.onopen = function(event) {
		console.log("llws连接成功!");
	}
	
	// 接收到消息的回调方法
    socket.onmessage = function(res) {
        console.log("llws收到消息啦:" +res.data);
        res = eval("("+res.data+")");
        if(res.type == 'friend' || res.type == 'group'){
            layim.getMessage(res);
        }else{
            layim.setFriendStatus(res.id,res.content);
        }

    }
	
	// 连接关闭的回调方法
	socket.onclose = function() {
		console.log("llws关闭连接!");
	}
	// 监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	window.onbeforeunload = function() {
		socket.close();
	}  

    // 监听发送消息
    layim.on('sendMessage', function(data){
	   var obj={
			    "mine":{
				   "avatar":data.mine.avatar,             
				   "content":data.mine.content,          
				   "id":data.mine.id,        
				   "mine":true,                       
				   "username":data.mine.username      
				 },
				 "to":{
					   "avatar":data.to.avatar,
					   "id":data.to.id,
					   "name":data.to.groupname,
					   "sign":data.to.sign,
					   "type":data.to.type,       
					   "username":data.to.username
				 }
			   }
	    console.log(JSON.stringify(obj));
		socket.send(JSON.stringify(obj));  	//发送消息倒Socket服务
   });
    
  //监听在线状态的切换事件
  layim.on('online', function(data){
    console.log(data);
  });
 
  //layim建立就绪
  layim.on('ready',function(){
  	//获取离线消息
      $.post("/index/getOfflineMsgFromRedis?userId="+userId,function(res){
          console.log(res);
          $.each(res,function(k,v){
              var s = eval('(' + v + ')');
              layim.getMessage(s);
          });
      });
  });

  //监听查看群员
  layim.on('members', function(data){
    console.log(data);
  });
  
  //监听聊天窗口的切换
  layim.on('chatChange', function(data){
    console.log(data);
  }); 
  
  function fankui(name,id,logo,sign){
	  var iid=Number(id);
	  layim.chat({
		   sign:sign
		  ,name: name
		  ,type: 'fankui'  
		  ,avatar: logo 
		  ,id:iid  
		});
   }
  
  layim.on('sign', function(value){
	  //console.log(value.length); //获得新的签名
	  if(value.length<200){
		  $.post("userInfo/updateUserInfo",{"id":userId,"sign":value},function(result){
			  console.log(result); 
		  })  
	  }else{
		  layer.msg("签名不能超过200字符！")
	  }
	});    
  
});




