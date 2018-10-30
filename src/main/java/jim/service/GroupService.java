package jim.service;

import com.github.pagehelper.PageInfo;

import jim.model.Group;
import jim.model.User;
 

/**
 * 群组Service
 */
public interface GroupService extends BaseService<Group> {

	/**
	 * 搜索群组<br>
	 * 模糊匹配群号或群名称
	 * @param searchStr
	 *            搜索关键字
	 * @param groupTypeId
	 *            群组类别id
	 * @param pageNum
	 *            当前页
	 * @param pageSize
	 *            每页记录数
	 * @return 群组PageInfo
	 */
	PageInfo<Group> search(String searchStr, int groupTypeId, int pageNum,
			int pageSize);
	
	User getGroupMaster(int groupId);
}
