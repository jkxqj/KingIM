
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.nercel.cyberhouse.client.controller.LLClient;

@ServerEndpoint(value="/api/LL_ws/{userId}")
public class LL_ws {
	private static LLClient llClient=new LLClient();
    private static int onlineCount = 0;    
    public static Map<String,Session> map=new HashMap<String,Session>();//根据用户找session
    
    /** 
     * 连接建立成功时调用
     * @param userId 登录用户id
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @throws IOException 
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") Integer userId) throws IOException{
    	map.put(userId+"",session);       //添加到链接map
        addOnlineCount(userId);           //在线数加1,更改数据库用户在线状态为1，在线。
        System.out.println("聊聊有新连接加入！当前在线人数为" + getOnlineCount());
        try{
            Thread.sleep(2000);
            sendUnreadMsg(session,getUnreadMsg(userId));
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
    
   public JSONArray getUnreadMsg(int userId){
        String msg=llClient.getUnreadMessage(userId);
        JSONArray getJsonArray=JSONArray.fromObject(msg);
        JSONArray toJsonArray = new JSONArray();
        for(int i=0;i<getJsonArray.size();i++){
        	JSONObject getJsonObj = getJsonArray.getJSONObject(i);
            JSONObject toMessage=new JSONObject();
            String type=getJsonObj.getJSONObject("friend_message").getJSONObject("to").getString("type");
            toMessage.put("avatar", getJsonObj.getJSONObject("friend_message").getJSONObject("mine").getString("avatar"));  
            toMessage.put("type",type);      
            toMessage.put("content", getJsonObj.getJSONObject("friend_message").getJSONObject("mine").getString("content"));   
            toMessage.put("timestamp",getJsonObj.getJSONObject("friend_message").getString("time")); 
            toMessage.put("username",getJsonObj.getJSONObject("friend_message").getJSONObject("mine").getString("username"));   
            toMessage.put("mine",false);
            if(type.equals("friend") || type.equals("fankui")){
    	    	   toMessage.put("id", getJsonObj.getJSONObject("friend_message").getJSONObject("mine").getInt("id"));   //如果是私聊，则是用户id
    	    }else{
    	    	   toMessage.put("id", getJsonObj.getJSONObject("friend_message").getJSONObject("to").getInt("id"));   //如果是群聊，则是群组id
    	    }
            llClient.updateMessageStatus(getJsonObj.getJSONObject("_id").toString().substring(9,33));
            toJsonArray.add(toMessage);
        }
        return toJsonArray;
    }
    
    public static void sendUnreadMsg(Session session,JSONArray toJsonArray){
	    for(int i=0;i<toJsonArray.size();i++){
	        synchronized(session){
	        	if(session.isOpen()){
	  	    	  try {                                  // 我上线时，给我推送我的未读消息。
	         			 session.getBasicRemote().sendText(toJsonArray.get(i).toString());
		           	     System.out.println("推送离线消息："+toJsonArray.get(i).toString());
		    		}catch (Exception e) {
		    			e.printStackTrace();
		    		}  
	        	}
	       }
	    }
    }
     
    /**
     * 连接关闭调用的方法
     */
	@OnClose
    public void onClose(Session session){
		if(map.containsValue(session)){
			for (Entry<String, Session> entry : map.entrySet()){
				 if(entry.getValue().equals(session)){
					 subOnlineCount(Integer.parseInt(entry.getKey()));           //在线数减1， 更改数据库用户在线状态为0，不在线。
					 System.out.println("聊聊有连接关闭！当前在线人数为" + getOnlineCount());
					 map.remove(entry.getKey());
					 break;
				 }
			}
		}
    }
   
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
	@OnMessage
    public void onMessage(String message, Session session) {
		
        JSONObject jsonObject=JSONObject.fromObject(message);
        String type = jsonObject.getJSONObject("to").getString("type");
        int toId=jsonObject.getJSONObject("to").getInt("id");
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");    
        Date date = new Date();
        String time=df.format(date);    
        jsonObject.put("time", time); 
        
        JSONObject toMessage=new JSONObject();
        toMessage.put("avatar", jsonObject.getJSONObject("mine").getString("avatar"));  
        toMessage.put("type",type);      
        toMessage.put("content", jsonObject.getJSONObject("mine").getString("content"));   
        toMessage.put("timestamp",date.getTime()); 
        toMessage.put("mine",false);
        toMessage.put("username",jsonObject.getJSONObject("mine").getString("username"));   
	    if(type.equals("friend")||type.equals("fankui")){
	    	   toMessage.put("id", jsonObject.getJSONObject("mine").getInt("id"));   
	    }else{
	    	   toMessage.put("id", jsonObject.getJSONObject("to").getInt("id"));   
	    }        
        switch (type) {
		case "friend":            //单聊,记录到mongo
			      try {
			    	  if(map.containsKey(toId+"")){               //如果在线，及时推送
			  			 map.get(toId+"").getBasicRemote().sendText(toMessage.toString());               //发送消息给对方
			  			 jsonObject.put("mStatus", 1);            //消息状态0为未读，1为已读
			  			 llClient.saveFriendMessage(jsonObject);
			  			 System.out.println("单聊-来自客户端的消息:" + toMessage.toString()); 
			    	  }else{                                      //如果不在线 就记录到数据库，下次对方上线时推送给对方。
			    		  jsonObject.put("mStatus",0);   
				  		  llClient.saveFriendMessage(jsonObject);
				  		  System.out.println("单聊-对方不在线，消息已存数据库:" + toMessage.toString());
			    	  }
				} catch (IOException e) {
					e.printStackTrace();
				}
			break;
		case "fankui":  //家长与老师反馈,记录到mongo
			      try {
			    	  if(map.containsKey(toId+"")){                  //如果在线，及时推送
			  			  map.get(toId+"").getBasicRemote().sendText(toMessage.toString());         //发送消息给对方
			  			  jsonObject.put("mStatus", 1);              //消息状态0为未读，1为已读
			  			  llClient.saveFriendMessage(jsonObject);
			  			  System.out.println("反馈-来自客户端的消息:" + toMessage.toString()); 
			    	  }else{
			    		  jsonObject.put("mStatus", 0);              //消息状态0为未读，1为已读
				  		  llClient.saveFriendMessage(jsonObject);
				  		  System.out.println("反馈-对方不在线，消息已存数据库:" + toMessage.toString());
			    	  }
				}catch(IOException e) {
					e.printStackTrace();
				}
			break;
		case "group":
			JSONArray memberList=JSONArray.fromObject(llClient.getGroupUser(toId));  //获取在线用户
				if(memberList.size()>0){              
					for(int i=0;i<memberList.size();i++){                            //发送到在线用户(除了发送者)
						if(map.containsKey(memberList.get(i)) && !memberList.get(i).equals(jsonObject.getJSONObject("mine").getInt("id")+"") ){
							session=(Session)map.get(memberList.get(i));
							try {
								  session.getBasicRemote().sendText(toMessage.toString());
					    		  jsonObject.put("mStatus",1);                       //消息状态0为未读，1为已读
					    		  llClient.saveGroupMessage(jsonObject);
								  System.out.println("群聊-来自客户端的消息:" + toMessage.toString()); 
							} catch (IOException e) {
								e.printStackTrace();
							}
						}else{
							  jsonObject.put("mStatus",0);                           //消息状态0为未读，1为已读
				    		  llClient.saveGroupMessage(jsonObject);
				    		  System.out.println("群聊-不在线的用户存入离线消息:" + toMessage.toString());
						}
					}
				}
			break;
		default:
			break;
		}     
    }     
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
		if(map.containsValue(session)){
			for (Entry<String, Session> entry : map.entrySet()) {
				 if(entry.getValue().equals(session)){
			         subOnlineCount(Integer.parseInt(entry.getKey()));                       //在线数减1，更改数据库用户在线状态为0，不在线。     
					 map.remove(entry.getKey());
					 break;
				 }
			}
		}
        System.out.println("聊聊发生错误!");
        error.printStackTrace();
    }
     
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }
    public static synchronized void addOnlineCount(int userId){
        LL_ws.onlineCount++;
    	llClient.updUserOnline(userId, 1);
    }
    public static synchronized void subOnlineCount(int userId){
        LL_ws.onlineCount--;
        llClient.updUserOnline(userId, 0);
    }
}