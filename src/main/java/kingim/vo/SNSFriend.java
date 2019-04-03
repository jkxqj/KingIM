package kingim.vo;

import java.util.List;

public class SNSFriend {
	private String groupname;
	private Integer id;
	private Integer online;
	private List<SNSUser> list;
	
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOnline() {
		return online;
	}
	public void setOnline(Integer online) {
		this.online = online;
	}
	public List<SNSUser> getList() {
		return list;
	}
	public void setList(List<SNSUser> list) {
		this.list = list;
	}
	
}
