#webChat
基于websocket的即时通讯系统。
涉及技术：java、springMVC、mybatis、jquery、layim、mongoDB等等。
##核心功能
参见[layim演示](http://layim.layui.com/demo.html)
我这里实现了后端的全部功能。
包括我踩过的坑：1、java操作mongo 2、离线消息推送的线程同步异步问题等等。

### websocket的代码实现
前端[im.js](https://github.com/jkxqj/webChat/blob/master/im.js)
后端[LL_ws.java](https://github.com/jkxqj/webChat/blob/master/LL_ws.java)

### 操作mongo的工具类
[MongoUtils.java](https://github.com/jkxqj/webChat/blob/master/MongoUtils.java)

### 好友聊天存储、查询等等的服务
[FriendWS.java](https://github.com/jkxqj/webChat/blob/master/FriendWS.java)

### 群组相关服务
[QunWS.java](https://github.com/jkxqj/webChat/blob/master/QunWS.java)

### 图片和文件上传的服务
[FileUpload.java](https://github.com/jkxqj/webChat/blob/master/FileUpload.java)

如有疑问或者想探讨，欢迎在github给我提issues，同时欢迎star
 

计划2017.1.1之后开始把该项目从公司的项目中单独抽出来,然后开源完整可运行的工程代码,敬请期待.
#同时欢迎加入技术交流qq群 439788463
