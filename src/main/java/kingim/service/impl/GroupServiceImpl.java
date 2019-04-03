package kingim.service.impl;

import com.github.pagehelper.PageInfo;

import kingim.dao.GroupMapper;
import kingim.model.Group;
import kingim.model.User;
import kingim.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;


/**
 * 群组Service
 * @author 1434262447@qq.com
 */
@Service
public class GroupServiceImpl extends BaseServiceImpl<Group> implements GroupService{

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Mapper<Group> getMapper() {
        return groupMapper;
    }

    @Override
    public PageInfo<Group> search(String searchStr, int groupTypeId, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public User getGroupMaster(int groupId) {
        return null;
    }

    @Override
    public int save(Group group) {
        return 0;
    }

    @Override
    public int deleteById(Object id) {
        return 0;
    }

    @Override
    public int update(Group group) {
        return 0;
    }

    @Override
    public List<Group> selectAll() {
        return null;
    }

    @Override
    public Group getById(Object id) {
        return null;
    }

    @Override
    public Group selectOne(Group group) {
        return null;
    }

    @Override
    public Group findByProperty(String propertyName, Object value) {
        return null;
    }

    @Override
    public List<Group> findListByProperty(String propertyName, Object value) {
        return null;
    }

    @Override
    public List<Group> select(Group group) {
        return null;
    }

    @Override
    public List<Group> selectAndPaging(Group group, Integer pageNum, Integer pageSize) {
        return null;
    }

    @Override
    public PageInfo<Group> selectAndPagingAndPackage(Group group, Integer pageNum, Integer pageSize) {
        return null;
    }
}
