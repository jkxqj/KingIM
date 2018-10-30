package jim.service;

import java.util.List;

import jim.model.Friend;
import jim.model.User;

public interface FriendService extends BaseService<Friend> {

	List<User> searchFriendByFriendId(int friendId);

	List<User> searchFriendByRemarkName(String remarkName);

	/**
	 * 是否已是好友关系
	 * 
	 * @param userId
	 *            用户id
	 * @param friendId
	 *            好友id
	 * @return true:已是好友 false不是好友
	 */
	boolean isFriend(int userId, int friendId);

	/**
	 * 删除好友
	 * 
	 * @param userId
	 *            用户id
	 * @param friendId
	 *            好友id
	 * @return true:已是好友 false不是好友
	 */

	int delFriend(int userId, int friendId);

	/**
	 * 添加好友关系
	 * 
	 * @param fromId
	 * @param toId
	 * @return
	 */
	int addFriend(int fromId, int toId, int typeId);

	/**
	 * @param userId
	 * @return int:好友数
	 */
	int getFriendCounts(int userId);

	/**
	 * 根据userId查询好友id
	 * 
	 * @param userId
	 * @return List<String>
	 */
	List<String> getFriendsId(int userId);

}
