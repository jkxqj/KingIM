package com.nercel.cyberhouse.vo;

import java.util.List;

public class SNSdata {
	private SNSUser mine;
	private List<SNSFriend> friend;
	private List<SNSGroup> group;
	
	public SNSUser getMine() {
		return mine;
	}
	public void setMine(SNSUser mine) {
		this.mine = mine;
	}
	public List<SNSFriend> getFriend() {
		return friend;
	}
	public void setFriend(List<SNSFriend> friend) {
		this.friend = friend;
	}
	public List<SNSGroup> getGroup() {
		return group;
	}
	public void setGroup(List<SNSGroup> group) {
		this.group = group;
	}
 
}
