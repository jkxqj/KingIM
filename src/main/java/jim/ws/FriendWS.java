package jim.ws;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import jim.model.Friend;
import jim.model.FriendType;
import jim.model.Group;
import jim.model.User;
import jim.service.FriendService;
import jim.service.FriendTypeService;
import jim.service.GroupUserService;
import jim.service.UserService;
import jim.utils.RedisUtils;
import jim.vo.SNSFriend;
import jim.vo.SNSGroup;
import jim.vo.SNSInit;
import jim.vo.SNSUser;
import jim.vo.SNSdata;

@Controller
@RequestMapping("api/friend")
public class FriendWS {
	@Autowired
	private FriendService friendService;
	@Autowired
	private UserService userService;
	@Autowired
	private FriendTypeService friendTypeService;
	@Autowired
	private GroupUserService groupUserService;
	  
	int pageSize=10;
	
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
	
	@RequestMapping(value = "updateOnLineStatus", produces = "text/plain; charset=utf-8")
	public @ResponseBody String updateOnLineStatus(int userId,String status) {
        try{
            RedisUtils.set(userId + "_status", status);
            return "1";
        }catch(Exception e){
            e.printStackTrace();
        }
    	return "0";
	}
	
	@RequestMapping(value = "getOfflineMsgFromRedis")
	public @ResponseBody JSONArray getOfflineMsgFromRedis(int userId) {
        JSONArray jsonArray = new JSONArray();
        if (RedisUtils.exists(userId + "_msg"))
        {
            Long len = RedisUtils.llen(userId + "_msg");
            while (len > 0)
            {
                jsonArray.add(RedisUtils.rpop(userId + "_msg"));
                len--;
            }
        }
        return jsonArray;
	}
	

	
	// 获取好友历史消息 FromMongo 页面
	@RequestMapping(value = "msgBoxPage", produces = "text/plain; charset=utf-8")
	public String msgBoxPage(HttpSession session,Model model) {
		User userInfo=(User) session.getAttribute("userInfo");
		int userId=userInfo.getId();
		model.addAttribute("userId",userId);
		return "js/plugins/layim/msgbox";
	}
	
	// yunpanSharePage
	@RequestMapping(value = "yunpanSharePage", produces = "text/plain; charset=utf-8")
	public String yunpanSharePage(HttpSession session,Model model) {
		return "js/plugins/layim/yunpanShare";
	}	
	
	
	/*
	 * @author jack 
	 * 给新版聊聊提供初始化数据服务，包括个人信息、好友列表信息、群组列表信息
	 * @param userId
	 */
	@RequestMapping(value = "getInitList", produces = "text/plain; charset=utf-8")
	public @ResponseBody String getInitList(int userId) {
		 User u=userService.getUserById(userId);
		 SNSUser mine=new SNSUser();
		 mine.setId(u.getId());
		 if(u.getAvator()==null || u.getAvator().equals("")){
			 mine.setAvatar("images/avatar/default120.png");
		 }else{
			 mine.setAvatar(u.getAvator());
		 }
		 mine.setSign(u.getSign());
		 mine.setUsername(u.getNickName());
 	 	//获取redis中的用户在线状态
 		String redisKey=userId+"_status";
 		RedisUtils.set(redisKey, "online");
 		mine.setStatus("online");
		SNSInit snsinit =new SNSInit();
		SNSdata data=new SNSdata();
		data.setMine(mine);
		snsinit.setData(data);
		List<SNSFriend> snsFriendList = new ArrayList<>();
		List<FriendType> list = friendTypeService.getFriendTypeByUserId(userId);
		if(list.size()==0){
			int id=friendTypeService.getByUserId(userId);  //如果没有默认分组，则初始化分组
			SNSFriend snsFriend = new SNSFriend();
			snsFriend.setGroupname("好友");
			snsFriend.setOnline(0);
			snsFriend.setId(id);
			snsFriendList.add(snsFriend);
		}else{
			try{
				for(int i=0;i<list.size();i++){
					SNSFriend snsFriend = new SNSFriend();
					snsFriend.setGroupname(list.get(i).getTypeName());
					snsFriend.setId(list.get(i).getId());
					List<Friend> friendList=list.get(i).getFriends();
					List<SNSUser> snsUserList = new ArrayList<SNSUser>();
					int onlineNum=0;
					for(int j=0;j<friendList.size();j++){
						SNSUser snsUser = new SNSUser();
						snsUser.setAvatar(friendList.get(j).getFriendInfo().getAvator());
						snsUser.setSign(friendList.get(j).getFriendInfo().getSign());
						snsUser.setUsername(friendList.get(j).getFriendInfo().getNickName());
						snsUser.setId(friendList.get(j).getFriendId());
						onlineNum++;
			    	 	//获取redis中的用户在线状态
			    		redisKey=friendList.get(j).getFriendId()+"_status";
			    		if(RedisUtils.exists(redisKey)){
			    			snsUser.setStatus(RedisUtils.get(redisKey).toString());
			    		}else{
			    			snsUser.setStatus("offline");
			    		}
						snsUserList.add(snsUser);
					}
					snsFriend.setOnline(onlineNum);
					snsFriend.setList(snsUserList);
					snsFriendList.add(snsFriend);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		data.setFriend(snsFriendList);
		//获取我加入的群的列表
		List<Group> groupList = groupUserService.getByUserId(userId);
		List<SNSGroup> glist = new ArrayList<SNSGroup>();
		for(int k=0;k<groupList.size();k++){
			SNSGroup sgroup = new SNSGroup();
			sgroup.setGroupname(groupList.get(k).getGroupName());
			sgroup.setId(groupList.get(k).getId());
			sgroup.setAvatar(groupList.get(k).getAvator());
			glist.add(sgroup);
		}
		data.setGroup(glist);
		snsinit.setData(data);
		return JSON.toJSONString(snsinit);
	}


	
}
