package starcLL.webSocket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import net.sf.json.JSONObject;

@ServerEndpoint("/LL_ws/{userName}")
public class LLWebSocket {
    private static int onlineCount = 0;
   // public static Map<String,Session> mapUS=new HashMap<String,Session>();//根据用户找session
   // public static Map<Session,String> mapSU=new HashMap<Session,String>();//根据session找用户
    //ConcurrentHashMap是线程安全的，而HashMap是线程不安全的。
    public static ConcurrentHashMap<String,Session> mapUS = new ConcurrentHashMap<String,Session>();  
    private static ConcurrentHashMap<Session,String> mapSU = new ConcurrentHashMap<Session,String>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("userName") String userName){
    	//this.session=session;
    	mapUS.put(userName,session);
    	mapSU.put(session,userName);
    	//上线通知由客户端自主发起
    	onlineCount++;           //在线数加1
        System.out.println("用户"+userName+"进入wsll！当前在线人数为" + onlineCount);
    }
     
    /**
     * 连接关闭调用的方法
     */
	@OnClose
    public void onClose(Session session){
    	String userName=mapSU.get(session);
    	if(userName!=null&&userName!=""){
        	//下线通知
        	JSONObject jsonObject=new JSONObject();
        	jsonObject.put("type", 3);
        	jsonObject.put("userName", userName);
        	jsonObject.put("contentType", "offline");
        	jsonObject.put("to", "all");
        	String jsonString=jsonObject.toString();
        	for(Session s:session.getOpenSessions()){//循环发给所有在线的人
    			s.getAsyncRemote().sendText(jsonString);
    		}
    	    onlineCount--;           //在线数减1    
            System.out.println("用户"+userName+"退出wsll！当前在线人数为" + onlineCount);
        	mapUS.remove(userName);
        	mapSU.remove(session);
    	}
    }
    /**
     * 收到客户端消息后调用的方法
     */
	@OnMessage
    public void onMessage(String message,Session session) {
        //System.out.println("来自客户端的消息:" + message); 
        JSONObject jsonObject=JSONObject.fromObject(message);
        int type = jsonObject.getInt("type");
        String to=jsonObject.getString("to");
        jsonObject.remove("to");
        if(type==0||to==null||to==""){return ;}
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time=df.format(new Date());// new Date()为获取当前系统时间
        jsonObject.put("time", time);
 
      switch (type) {
		case 1://单聊
			Session session_to=mapUS.get(to);
        	if(session_to!=null){
        		session_to.getAsyncRemote().sendText(jsonObject.toString());
        		//System.out.println("发给"+to+"："+jsonObject.toString());
        	}
			break;
		case 2://群聊
			String[] members=to.split(",");
			//发送到在线用户
			for(String member:members){
				session=mapUS.get(member);
				if(session!=null){
					session.getAsyncRemote().sendText(jsonObject.toString());
					//System.out.println("发给群里所有在线的人"+member+"："+jsonObject.toString());
				}
			}
			break;
		case 3:
			 //所有人
			for(Session s:session.getOpenSessions()){//循环发给所有在线的人
				s.getAsyncRemote().sendText(jsonObject.toString());
				//System.out.println("发给系统所有在线的人"+session.getOpenSessions()+"："+jsonObject.toString());
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
    public void onError(Throwable error){
        System.out.println("llws发生错误");
        error.printStackTrace();
    }

}
