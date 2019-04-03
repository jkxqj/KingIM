package kingim.service;

import java.util.List;
import com.github.pagehelper.PageInfo;

/**
 * 通用Service基类
 * @param <T>
 */
public interface BaseService<T> {

	/**
	 * 保存实体
	 * @param t
	 *            实体对象
	 * @return 受影响行数
	 */
	int save(T t);

	/**
	 * 根据id删除实体
	 * @param id
	 *            主键id
	 * @return 受影响行数
	 */
	int deleteById(Object id);

	/**
	 * 更新实体
	 * @param t
	 *            实体对象
	 * @return 受影响行数
	 */
	int update(T t);

	/**
	 * 查询所有实体
	 * @return 实体集合
	 */
	List<T> selectAll();

	/**
	 * 根据id查询实体
	 * @param id
	 *            主键id
	 * @return 实体对象
	 */
	T getById(Object id);

	/**
	 * 根据实体对象属性查询，属性条件使用and拼接
	 * @param t
	 *            实体对象
	 * @return 实体对象
	 */
	T selectOne(T t);

	/**
	 * 根据属性名称进行单对象查询
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性代码
	 * @return 对象T
	 */
	T findByProperty(String propertyName, Object value);

	/**
	 * 根据属性名称进行查询列表
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性代码
	 * @return 对象T集合
	 */
	List<T> findListByProperty(String propertyName, Object value);

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号
	 * @param t
	 *            实体对象
	 * 
	 * @return 实体对象列表
	 */
	List<T> select(T t);

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号，并分页
	 * @param t
	 *            实体对象
	 * @param pageNum
	 *            第几页
	 * @param pageSize
	 *            每页多少条数据
	 * @return 实体对象列表
	 */
	List<T> selectAndPaging(T t, Integer pageNum, Integer pageSize);

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号，并分页并封装
	 * @param t
	 *            实体对象
	 * @param pageNum
	 *            第几页
	 * @param pageSize
	 *            每页多少条数据
	 * @return 实体对象列表
	 */
	PageInfo<T> selectAndPagingAndPackage(T t, Integer pageNum, Integer pageSize);
}
