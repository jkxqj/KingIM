package kingim.controller;

import javax.servlet.http.HttpSession;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import kingim.model.Friend;
import kingim.model.FriendType;
import kingim.model.Group;
import kingim.service.FriendTypeService;
import kingim.service.GroupUserService;
import kingim.utils.RedisUtils;
import kingim.vo.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import kingim.model.User;
import kingim.service.UserService;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/index")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	private FriendTypeService friendTypeService;
	@Autowired
	private GroupUserService groupUserService;

	private static Logger logger = Logger.getLogger(UserController.class);

	@RequestMapping("/main")
	public String index() {
		return "main";
	}
	
	@RequestMapping("login")
	public String login() {
		return "login";
	}
	
	/**
	 * 用户登录
	 * @author 1434262447@qq.com
	 * @param model
	 * @param userName   用户名
	 * @param password   密码
	 */
	@RequestMapping(value = "loginCheck", method = RequestMethod.POST)
	public String loginCheck(HttpSession session, Model model, String userName, String password) {
		// 是否存在用户
		String url="login";
		User user = userService.matchUser(userName,password);
		if (user!=null) {
			session.setAttribute("user",user);
			logger.info("用户："+user.getNickName()+" : "+user.getUserName()+"  登录成功!");
			return "main";
		} 
		model.addAttribute("msg", "用户名或密码错误，请重新输入!");
		logger.error("userName:"+userName+" password:"+password+"  登录失败!");
		return url;
	}
	
	/**
	 * 退出登录
	 * @author 1434262447@qq.com
	 */
	@RequestMapping("logout")
	public String logout(SessionStatus sessionStatus, HttpSession session) {
		sessionStatus.setComplete();
		session.invalidate();
		return "redirect:/index/login";
	}

	/**
	 * 给layim提供初始化数据服务，包括个人信息、好友列表信息、群组列表信息
	 * @param userId
	 * @author 1434262447@qq.com
	 */
	@RequestMapping(value = "getInitList", produces = "text/plain; charset=utf-8")
	public @ResponseBody String getInitList(int userId) {
		User u=userService.getUserById(userId);
		SNSUser mine=new SNSUser();
		mine.setId(u.getId());
		if(u.getAvatar()==null || u.getAvatar().equals("")){
			mine.setAvatar("images/avatar/default.png");
		}else{
			mine.setAvatar(u.getAvatar());
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
		if(list==null){
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
					List<SNSUser> snsUserList = new ArrayList<>();
					int onlineNum=0;
					for(int j=0;j<friendList.size();j++){
						SNSUser snsUser = new SNSUser();
						snsUser.setAvatar(friendList.get(j).getFriendInfo().getAvatar());
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
		List<SNSGroup> glist = new ArrayList<>();
		if(groupList!=null){
			for(int k=0;k<groupList.size();k++){
				SNSGroup sgroup = new SNSGroup();
				sgroup.setGroupname(groupList.get(k).getGroupName());
				sgroup.setId(groupList.get(k).getId());
				sgroup.setAvatar(groupList.get(k).getAvatar());
				glist.add(sgroup);
			}
			data.setGroup(glist);
		}
		snsinit.setData(data);

		return JSON.toJSONString(snsinit);
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

}
