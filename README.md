#JIM
基于websocket的即时通讯系统。
涉及技术：java、springMVC、mybatis、jquery、layim、mongoDB等等。
##核心功能
参见[layim演示](http://layim.layui.com/demo.html)
我这里实现了后端的全部功能。
包括我踩过的坑：1、java操作mongo 2、离线消息推送的线程同步异步问题等等。

### websocket的代码实现
前端[index.js](https://github.com/jkxqj/webChat/blob/master/index.js)
后端[LLWebSocket.java](https://github.com/jkxqj/webChat/blob/master/LLWebSocket.java)

### 操作mongo的工具类
[MongoUtils.java](https://github.com/jkxqj/webChat/blob/master/MongoUtils.java)

### 好友聊天存储、查询等等的服务
[FriendWS.java](https://github.com/jkxqj/webChat/blob/master/FriendWS.java)

### 群组相关服务
[QunWS.java](https://github.com/jkxqj/webChat/blob/master/QunWS.java)

### 图片和文件上传的服务
[FileUpload.java](https://github.com/jkxqj/webChat/blob/master/FileUpload.java)

如有疑问或者想探讨，欢迎在github给我提issues，同时欢迎star
 

 
#同时欢迎加我qq 1434262447 交流即时通讯和java 相关技术。
