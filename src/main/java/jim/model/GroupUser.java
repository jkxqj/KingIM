package jim.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "group_user")
public class GroupUser {
    /**
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 群id
            
     */
    @Column(name = "group_id")
    private Integer groupId;

    /**
     * 入群时间
     */
    @Column(name = "join_time")
    private String joinTime;
    
	/**
	 * 群成员用户
	 */
	@Transient
	private User user;
	
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
     * 获取自增主键
     *
     * @return id - 自增主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增主键
     *
     * @param id 自增主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取群id
            
     *
     * @return group_id - 群id
            
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 设置群id
            
     *
     * @param groupId 群id
            
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取入群时间
     *
     * @return join_time - 入群时间
     */
    public String getJoinTime() {
        return joinTime;
    }

    /**
     * 设置入群时间
     *
     * @param joinTime 入群时间
     */
    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }
}