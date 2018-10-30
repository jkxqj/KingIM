package jim.service;

import java.util.List;
import com.github.pagehelper.PageInfo;

import jim.model.Friend;
import jim.model.FriendType;
import jim.model.User;
 

public interface FriendTypeService extends BaseService<FriendType> {

	
	// 获取好友分组列表
	List<FriendType> getFriendTypeByUserId(int userId);
	
	// 获取好友分组列表和分组下所有好友数
	List<FriendType> getFriendCountByUserId(int userId);

	// 根据分组id查询用户分组下在线好友数
	long getOnlineCountByTypeId(int id);

	// 查询用户好友分组下所有好友列表（分页）
	PageInfo<User> getFriendByTypeIdPage(int id,int pageNum,int pageSize);

	//查询用户好友分组下所有好友列表 (未分页)
	List<User> getFriendByTypeId(int id);
	
	// 查询用户好友分组下所有好友列表(包含分组信息)
	List<FriendType> getFriendByUserId(int userId);
	
	// 查询用户的默认好友分组id，如果不存在好友分组则创建默认分组"好友"并返回该id
	int getByUserId(int userId);
	
	/**
	 * 根据用户id和好友分组名称查询
	 * @param userId
	 *            用户id
	 * @param typeName
	 *            好友分组名称
	 * @return 好友分组信息
	 */
	FriendType getByUserIdTypeName(int userId, String typeName);

	/**
	 * 批量将用户移动至另一个分组<br>
	 * 手动进行批量移动好友分组适用
	 * @param friendList
	 *            需移动的好友列表，Friend里传入userId和friendId
	 * @param toTypeId
	 *            目标分组id
	 * @return 受影响行数
	 */
	int updateBatchToOtherType(List<Friend> friendList, int toTypeId);
 
	
	/**
	 * 将用户移动至另一个分组<br>
	 * @param friendId  
	 *   		     好友id
	 * @param userId 
	 * 		               用户id          
	 * @param toTypeId
	 *            目标分组id
	 * @return 受影响行数
	 */
	int updateToOtherType(int userId,int friendId, int toTypeId);

	//查询好友分组列表
	List<FriendType> getFriendTypeList(int userId);
	
	List<FriendType> searchMyFriendGroupByType(Integer userId,String searchStr);
	
	int delById(Object id);
 
}
