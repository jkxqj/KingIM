package com.nercel.cyberhouse.vo;

import java.util.List;

public class SNSMData {
	
	private SNSUser owner;
	
	private List<SNSUser> list;

	public SNSUser getOwner() {
		return owner;
	}

	public void setOwner(SNSUser owner) {
		this.owner = owner;
	}

	public List<SNSUser> getList() {
		return list;
	}

	public void setList(List<SNSUser> list) {
		this.list = list;
	}

}
