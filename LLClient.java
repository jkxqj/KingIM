package com.nercel.cyberhouse.client.controller;

import org.springframework.web.client.RestTemplate;

import com.nercel.cyberhouse.util.ServerConfig;

import net.sf.json.JSONObject;

public class LLClient {
 
	private String CYBERHOUSE_PATH =  "localhost:8080/cyberhouse";
	private RestTemplate template = new RestTemplate();

	// 更改用户在线状态
	public String updUserOnline(int userId, int status) {
		return template.getForObject(CYBERHOUSE_PATH + "/userOnline/updUserOnline?userId={userId}" + "&status={status}",String.class, userId, status);
	}

	// 获取群组成员用户名列表
	public String getGroupUser(int groupId) {
		return template.getForObject(CYBERHOUSE_PATH + "/qun/getSimpleMemberByGroupId?groupId={groupId}", String.class,groupId);
	}

	// 保存好友消息到mongo
	public String saveFriendMessage(JSONObject message) {
		return template.postForObject(CYBERHOUSE_PATH + "/friend/saveMessageToMongo", message,String.class);
	}

	// 保存群组消息mongo
	public String saveGroupMessage(JSONObject message) {
		return template.postForObject(CYBERHOUSE_PATH + "/qun/saveMessageToMongo", message,String.class);
	}
	//获取某人未读好友消息
	public String getUnreadMessage(int userId){
		return template.getForObject(CYBERHOUSE_PATH+"/friend/getUnreadMessage?userId={userId}",String.class,userId);
	}
	
	//获取某人未读群消息
	public String getUnreadQunMsg(int userId){
		return template.getForObject(CYBERHOUSE_PATH+"/qun/getUnreadQunMsg?userId={userId}",String.class,userId);
	}
	
	//更新某人未读群消息状态
	public String updateQunMsgStatus(String id){
		return template.getForObject(CYBERHOUSE_PATH+"/qun/updateQunMsgStatus?id={id}",String.class,id);
	}

	//更新某人未读私聊消息状态
	public String updateMessageStatus(String id){
		return template.getForObject(CYBERHOUSE_PATH+"/friend/updateMessageStatus?id={id}",String.class,id);
	}
	
	//保存某人未读群消息
	public String saveOfflineMsgToMongo(JSONObject message){
		return template.postForObject(CYBERHOUSE_PATH+"/qun/saveOfflineMsgToMongo",message,String.class);
	}
	
}
