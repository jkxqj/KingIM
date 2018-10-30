package jim.service.impl;

import java.util.List;
import jim.dao.FriendMapper;
import jim.model.Friend;
import jim.model.User;
import jim.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

/**
 * 好友Service
 * @author 1434262447@qq.com
 */
@Service
public class FriendServiceImpl extends BaseServiceImpl<Friend> implements FriendService {

	@Autowired
	private FriendMapper friendMapper;

	@Override
	public Mapper<Friend> getMapper() {
		return friendMapper;
	}

	@Override
	public List<User> searchFriendByFriendId(int friendId) {
		return null;
	}

	@Override
	public List<User> searchFriendByRemarkName(String remarkName) {
		return null;
	}

	@Override
	public boolean isFriend(int userId, int friendId) {
		return false;
	}

	@Override
	public int delFriend(int userId, int friendId) {
		return 0;
	}

	@Override
	public int addFriend(int fromId, int toId, int typeId) {
		return 0;
	}

	@Override
	public int getFriendCounts(int userId) {
		return 0;
	}

	@Override
	public List<String> getFriendsId(int userId) {
		return null;
	}

}
