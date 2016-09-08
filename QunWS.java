import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
 

import net.sf.json.JSONObject;

@Controller
@RequestMapping("api/qun")
public class QunWS {
  @Autowired
  private GroupMessageService groupMessageService;
  @Autowired
  private GroupUserService groupUserService;
  @Autowired
  private GroupService groupService;

	//记录群消息mysql(弃)
	@RequestMapping(value = "saveMessage", produces = "text/plain; charset=utf-8")
	public @ResponseBody String saveMessage(int userId,int groupId,String content,String type){
		GroupMessage groupMessage=new GroupMessage();
		groupMessage.setContent(content);
		groupMessage.setUserId(userId);
		groupMessage.setGroupId(groupId);
		groupMessage.setType(type);
		return groupMessageService.save(groupMessage)+"";
	}	
	
	//记录群消息mongo
	@RequestMapping(value = "saveMessageToMongo", produces = "text/plain; charset=utf-8")
	public @ResponseBody String saveMessageToMongo(@RequestBody JSONObject message){
		int f=0;
		try {
			// 连接到 mongodb 服务
			MongoUtils mu = new MongoUtils();
			MongoUtils.createMongoClient();
			MongoDatabase md = mu.getDataBase(MongoUtils.DATABASE);
			MongoCollection<Document> gm = md.getCollection(MongoUtils.GROUP_MESSAGE);
			Document document = new Document("group_message",message);
			try {
				gm.insertOne(document);
				f=1;
			} catch (Exception e) {
				f=0;
				e.printStackTrace();
			}
		} catch (Exception e) {
			f=0;
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}finally {
			MongoUtils.closeConnection();
		}
		return f+"";
	}	
	
	//获取群成员列表(只含用户名)
 	@RequestMapping(value = "getSimpleMemberByGroupId", produces = "text/plain; charset=utf-8")
	public @ResponseBody String getSimpleMemberByGroupId(int groupId){
		return JSON.toJSONString(groupUserService.getSimpleMemberByGroupId(groupId));
	}
	 
	//获取我的群组列表(加入的)
	@RequestMapping(value = "getGroupByUserId", produces = "text/plain; charset=utf-8")
	public @ResponseBody String getGroupByUserId(int userId){
		List<Group> list = groupUserService.getByUserId(userId);
		return JSON.toJSONString(list);
	}
	
	//获取群成员信息 for sns
	@RequestMapping(value = "getByGroupId", produces = "text/plain; charset=utf-8")
	public @ResponseBody String getByGroupId(int id){
		List<GroupUser> list = groupUserService.getByGroupId(id);
		UserInfo groupMaster = groupService.getGroupMaster(id);
		SNSMember member = new SNSMember();
		
		List<SNSUser> userList = new ArrayList<SNSUser>(); 
		for(int i=0;i<list.size();i++){
			SNSUser snsUser =new SNSUser();
			snsUser.setId(list.get(i).getUserInfo().getId());
			snsUser.setAvatar(list.get(i).getUserInfo().getLogo());
			snsUser.setSign(list.get(i).getUserInfo().getSign());
			snsUser.setUsername(list.get(i).getUserInfo().getUcName());
			userList.add(snsUser);
		}
		SNSUser snsMaster=new SNSUser();
		snsMaster.setAvatar(groupMaster.getLogo());
		snsMaster.setId(groupMaster.getId());
		snsMaster.setSign(groupMaster.getSign());
		snsMaster.setUsername(groupMaster.getUcName());
		SNSMData data = new SNSMData();
		data.setList(userList);
		data.setOwner(snsMaster);
		member.setCode(0);
		member.setData(data);
		
		return JSON.toJSONString(member);
	}
}
