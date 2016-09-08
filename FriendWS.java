import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
 
/*
 * @author jack
 * 好友相关服务
 * friend表，主键id自增
 */
@Controller
@RequestMapping("api/friend")
public class FriendWS {
	@Autowired
	private FriendService friendService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private UserOnlineService userOnlineService;
	@Autowired
	private FriendTypeService friendTypeService;
	@Autowired
	private GroupUserService groupUserService;
	  
	int pageSize=10;
	/*
	 * @author jack 
	 * 通过userId搜索好友
	 */
	@RequestMapping(value = "searchFriendByFriendId", produces = "text/plain; charset=utf-8")
	public @ResponseBody String searchFriendByFriendId(int friendId) {
		return JSON.toJSONString(friendService.searchFriendByFriendId(friendId));
	}
	
	@RequestMapping(value = "searchFriendByRemarkName", produces = "text/plain; charset=utf-8")
	public @ResponseBody String searchFriendByRemarkName(String remarkName) {
		return JSON.toJSONString(friendService.searchFriendByRemarkName(remarkName));
	}
	
	@RequestMapping(value = "isFriend", produces = "text/plain; charset=utf-8")
	public @ResponseBody String isFriend(int userId, int friendId) {
		if(friendService.isFriend(userId, friendId))
			return "1";
		return "0";
	}
	
	@RequestMapping(value = "delFriend", produces = "text/plain; charset=utf-8")
	public @ResponseBody String delFriend(int userId, int friendId) {
		if(friendService.delFriend(userId, friendId)==1)
			return "1";
		return "0";
	}
	
	//记录发送的消息（存mongo的方法,使用）
	@RequestMapping(value = "saveMessageToMongo", produces = "text/plain; charset=utf-8")
	public @ResponseBody String saveMessageToMongo(@RequestBody JSONObject message) {
		int f=0;
		try {
			MongoUtils mu = new MongoUtils();
			MongoUtils.createMongoClient();
			MongoDatabase md = mu.getDataBase(MongoUtils.DATABASE);
			MongoCollection<Document> fm = md.getCollection(MongoUtils.FRIEND_MESSAGE);
			Document document = new Document("friend_message",message); 
			try {
				fm.insertOne(document);
				f=1;
			} catch (Exception e) {
				f=0;
				e.printStackTrace();
			}
		}catch (Exception e) {
			f=0;
			System.err.println("异常："+e.getClass().getName() + ": " + e.getMessage());
		}finally {
			MongoUtils.closeConnection();
		}
		return f+"";
	}
	
	// 获取好友历史消息 FromMongo 页面
	@RequestMapping(value = "getHistoryMessageFromMongoPage", produces = "text/plain; charset=utf-8")
	public String getHistoryMessageFromMongo(HttpSession session,int id,String type,Model model) {
		String s="";
		MongoUtils mu = new MongoUtils();
		MongoUtils.createMongoClient();
		MongoDatabase md = mu.getDataBase(MongoUtils.DATABASE);
		if(type.equals("friend")){
			UserInfo userInfo=(UserInfo) session.getAttribute("userInfo");
			int fromUserId=userInfo.getId();
			MongoCollection<Document> friendMessages = md.getCollection(MongoUtils.FRIEND_MESSAGE);
			List<Document> docs=new ArrayList<Document>();
			Document doc1=new Document("friend_message.mine.id",fromUserId).append("friend_message.to.id",id);
			Document doc2=new Document("friend_message.mine.id",id).append("friend_message.to.id",fromUserId);
			docs.add(doc1);
			docs.add(doc2);
			Document grep=new Document();
			grep.put("$or",docs);
			grep.append("friend_message.to.type","friend");
			Document sortFields1 = new Document("_id",-1);
			Document sortFields2 = new Document("_id",1);  
			List<Document> pipeline = Arrays.asList(new Document("$sort", sortFields1),new Document("$match",grep),new Document("$limit",pageSize),new Document("$sort", sortFields2));
			List<Document> results=friendMessages.aggregate(pipeline).into(new ArrayList<Document>());
		
			for(Document cur:results){
				s+=cur.toJson()+',';
			}
			model.addAttribute("jsonStr","["+s+"]");
			model.addAttribute("toId",id);
			model.addAttribute("type",type);
		}else if(type.equals("fankui")){
			UserInfo userInfo=(UserInfo) session.getAttribute("userInfo");
			int fromUserId=userInfo.getId();
			MongoCollection<Document> friendMessages = md.getCollection(MongoUtils.FRIEND_MESSAGE);
			List<Document> docs=new ArrayList<Document>();
			Document doc1=new Document("friend_message.mine.id",fromUserId).append("friend_message.to.id",id);
			Document doc2=new Document("friend_message.mine.id",id).append("friend_message.to.id",fromUserId);
			docs.add(doc1);
			docs.add(doc2);
			Document grep=new Document();
			grep.put("$or",docs);
			grep.append("friend_message.to.type","fankui");
			Document sortFields1 = new Document("_id",-1);
			Document sortFields2 = new Document("_id",1);  
			List<Document> pipeline = Arrays.asList(new Document("$sort", sortFields1),new Document("$match",grep),new Document("$limit",pageSize),new Document("$sort", sortFields2));
			List<Document> results=friendMessages.aggregate(pipeline).into(new ArrayList<Document>());
		
			for(Document cur:results){
				s+=cur.toJson()+',';
			}
			model.addAttribute("jsonStr","["+s+"]");
			model.addAttribute("toId",id);
			model.addAttribute("type",type);
		}else if(type.equals("group")){
			MongoCollection<Document> groupMessages = md.getCollection(MongoUtils.GROUP_MESSAGE);
			FindIterable<Document> findIterable=groupMessages.find(new Document("group_message.to.id",id));
			MongoCursor<Document> mongoCursor = findIterable.iterator();  
	        while(mongoCursor.hasNext()){ 
	        	s+=mongoCursor.next().toJson()+',';	
	        }  
	    	model.addAttribute("jsonStr","["+s+"]");
			model.addAttribute("toId",id);
			model.addAttribute("type",type);
		}
		MongoUtils.closeConnection();
		return "js/plugins/layui/chatLog";
	}	
	
	// 获取好友历史消息 FromMongo 分页数据
	@RequestMapping(value = "getHistoryMessageFromMongo" , produces = "text/plain; charset=utf-8")
	public @ResponseBody String getHistoryMessageFromMongo(HttpSession session,int id,String type,Integer pageNum) {
		
		if(pageNum==null){
			pageNum=1;
		}
		String s="";
		MongoUtils mu = new MongoUtils();
		MongoUtils.createMongoClient();
		MongoDatabase md = mu.getDataBase(MongoUtils.DATABASE);
		if(type.equals("friend")){
			UserInfo userInfo=(UserInfo) session.getAttribute("userInfo");
			int fromUserId=userInfo.getId();
		
			MongoCollection<Document> friendMessages = md.getCollection(MongoUtils.FRIEND_MESSAGE);
			List<Document> docs=new ArrayList<Document>();
			Document doc1=new Document("friend_message.mine.id",fromUserId).append("friend_message.to.id",id);
			Document doc2=new Document("friend_message.mine.id",id).append("friend_message.to.id",fromUserId);
			docs.add(doc1);
			docs.add(doc2);
			Document grep=new Document();
			grep.put("$or",docs);
			grep.append("friend_message.to.type","friend");
			Document sortFields1 = new Document("_id", -1);       
			Document sortFields2 = new Document("_id", 1);     
			List<Document> pipeline = Arrays.asList(new Document("$sort", sortFields1),new Document("$skip",(pageNum-1)*pageSize),new Document("$limit",pageSize),new Document("$sort", sortFields2));
			List<Document> results=friendMessages.aggregate(pipeline).into(new ArrayList<Document>());
			for(Document cur:results){
				s+=cur.toJson()+',';
			}
		}else if(type.equals("fankui")){
			UserInfo userInfo=(UserInfo) session.getAttribute("userInfo");
			int fromUserId=userInfo.getId();
			MongoCollection<Document> friendMessages = md.getCollection(MongoUtils.FRIEND_MESSAGE);
			List<Document> docs=new ArrayList<Document>();
			Document doc1=new Document("friend_message.mine.id",fromUserId).append("friend_message.to.id",id);
			Document doc2=new Document("friend_message.mine.id",id).append("friend_message.to.id",fromUserId);
			docs.add(doc1);
			docs.add(doc2);
			Document grep=new Document();
			grep.put("$or",docs);
			grep.append("friend_message.to.type","fankui");
			Document sortFields1 = new Document("_id", -1);       
			Document sortFields2 = new Document("_id", 1);     
			List<Document> pipeline = Arrays.asList(new Document("$sort", sortFields1),new Document("$skip",(pageNum-1)*pageSize),new Document("$limit",pageSize),new Document("$sort", sortFields2));
			List<Document> results=friendMessages.aggregate(pipeline).into(new ArrayList<Document>());
			for(Document cur:results){
				s+=cur.toJson()+',';
			}
		}else if(type.equals("group")){
			MongoCollection<Document> groupMessages = md.getCollection(MongoUtils.GROUP_MESSAGE);
			FindIterable<Document> findIterable=groupMessages.find(new Document("group_message.to.id",id));
			MongoCursor<Document> mongoCursor = findIterable.iterator();  
	        while(mongoCursor.hasNext()){ 
	        	s+=mongoCursor.next().toJson()+',';	
	        }  
		}
		MongoUtils.closeConnection();
		return "["+s+"]";
	}
	
	@RequestMapping(value = "getUnreadMessage" , produces = "text/plain; charset=utf-8")
	public @ResponseBody String getUnreadMessage(int userId) {
			MongoUtils mu = new MongoUtils();
			MongoUtils.createMongoClient();
			MongoDatabase md = mu.getDataBase(MongoUtils.DATABASE);
			MongoCollection<Document> friendMessages = md.getCollection(MongoUtils.FRIEND_MESSAGE);
			FindIterable<Document> findIterable=friendMessages.find(new Document("friend_message.to.id",userId).append("friend_message.mStatus",0));
			String s="";
			MongoCursor<Document> mongoCursor = findIterable.iterator();  
	        while(mongoCursor.hasNext()){ 
	        	s+=mongoCursor.next().toJson()+',';	
	        }  
			MongoUtils.closeConnection();
			return "["+s+"]";
	}
		
	@RequestMapping(value = "updateMessageStatus" , produces = "text/plain; charset=utf-8")
	public @ResponseBody String updateMessageStatus(String id) {
			MongoUtils mu = new MongoUtils();
			MongoUtils.createMongoClient();
			MongoDatabase md = mu.getDataBase(MongoUtils.DATABASE);
			MongoCollection<Document> friendMessages = md.getCollection(MongoUtils.FRIEND_MESSAGE);
			String f="0";	
			try {
				Document filter = new Document();  
		        filter.append("_id",new ObjectId(id));  
		        Document update = new Document();  
		        update.append("$set",new Document("friend_message.mStatus",1));  
				friendMessages.updateOne(filter,update);
				f="1";
			} catch (Exception e) {
				e.printStackTrace();
			}
			MongoUtils.closeConnection();
			return f;
	}
	
	//根据相关条件 搜索好友
	@RequestMapping(value = "searchFriend" )
	public @ResponseBody PageInfo<User> searchFriend(String searchStr, String userRole,String provinceCode, 
      String cityCode, String countyCode,Integer schoolId, String gradeCode, Integer classId, Integer pageNum,Integer pageSize) {
	//	Calendar cal = Calendar.getInstance();
	//	String schoolYear =  cal.get(Calendar.YEAR)+"";
		String schoolYear = "2015"; 
		PageInfo<User> user=userService.search(searchStr, userRole, schoolYear, provinceCode, cityCode, countyCode, schoolId, gradeCode, classId, pageNum, pageSize);
		return user;
	}
	
	
	/*
	 * @author jack 
	 * 给新版聊聊提供初始化数据服务，包括个人信息、好友列表信息、群组列表信息
	 * @param userId
	 */
	@RequestMapping(value = "getInitList", produces = "text/plain; charset=utf-8")
	public @ResponseBody String getInitList(int userId) {
		
		 UserInfo u=userInfoService.getById(userId);
		 SNSUser mine=new SNSUser();
		 mine.setId(u.getId());
		 mine.setAvatar(u.getLogo());
		 mine.setSign(u.getSign());
		 mine.setUsername(u.getUcName());
		 if(userOnlineService.getById(userId).getIsOnline()==1){
			 mine.setStatus("online");	 
		 }else if(userOnlineService.getById(userId).getIsOnline()==0){
			 mine.setStatus("hide");
		 }else{
			 mine.setStatus("hide");
		 }
		SNSInit snsinit =new SNSInit();
		SNSdata data=new SNSdata();
		data.setMine(mine);
		snsinit.setData(data);
		List<SNSFriend> snsFriendList = new ArrayList<SNSFriend>();
		List<FriendType> list = friendTypeService.getFriendTypeByUserId(userId);
		for(int i=0;i<list.size();i++){
			SNSFriend snsFriend = new SNSFriend();
			snsFriend.setGroupname(list.get(i).getTypeName());
			snsFriend.setId(list.get(i).getId());
			List<Friend> friendList=list.get(i).getFriends();
			List<SNSUser> snsUserList = new ArrayList<SNSUser>();
			int onlineNum=0;
			for(int j=0;j<friendList.size();j++){
				SNSUser snsUser = new SNSUser();
				snsUser.setAvatar(friendList.get(j).getFriendUserInfo().getLogo());
				snsUser.setId(friendList.get(j).getFriendId());
				snsUser.setSign(friendList.get(j).getFriendUserInfo().getSign());
				snsUser.setUsername(friendList.get(j).getFriendUserInfo().getUcName());
				 if(userOnlineService.getById(friendList.get(j).getFriendId()).getIsOnline()==1){
					 snsUser.setStatus("online");
					 onlineNum++;
				 }else{
					 snsUser.setStatus("hide");
				 }
				snsUserList.add(snsUser);
			}
			snsFriend.setOnline(onlineNum);
			snsFriend.setList(snsUserList);
			snsFriendList.add(snsFriend);
		}
		data.setFriend(snsFriendList);
		//获取我加入的群的列表
		List<Group> groupList = groupUserService.getByUserId(userId);
		List<SNSGroup> glist = new ArrayList<SNSGroup>();
		for(int k=0;k<groupList.size();k++){
			SNSGroup sgroup = new SNSGroup();
			sgroup.setGroupname(groupList.get(k).getGroupName());
			sgroup.setId(groupList.get(k).getId());
			sgroup.setAvatar(groupList.get(k).getIcon());
			glist.add(sgroup);
		}
		data.setGroup(glist);
		snsinit.setData(data);
		return JSON.toJSONString(snsinit);
	}
	
	
}
