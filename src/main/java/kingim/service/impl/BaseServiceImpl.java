package kingim.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import kingim.service.BaseService;
import tk.mybatis.mapper.common.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 通用Service基类实现
 * @author 1434262447@qq.com
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	// 抽象方法子类实现，获取泛型对应的mapper
	public abstract Mapper<T> getMapper();

	/**
	 * 保存实体
	 * @author 1434262447@qq.com
	 * @param t
	 *            实体对象
	 * @return 受影响行数
	 */
	public int save(T t) {
		// 泛型T中存在版本字段则初始化这个字段
		Field field = getFieldByPropertyName("versionInfo");
		if (field != null) {
			setValueOfProperty(t, "versionInfo",
					String.valueOf(System.currentTimeMillis()));
		}
		return getMapper().insertSelective(t);
	}

	/**
	 * 根据id删除实体
	 * @author 1434262447@qq.com
	 * @param id
	 *            主键id
	 * @return 受影响行数
	 */
	public int deleteById(Object id) {
		return getMapper().deleteByPrimaryKey(id);
	}

	/**
	 * 更新实体
	 * @author 1434262447@qq.com
	 * @param t
	 *            实体对象
	 * @return 受影响行数
	 */
	public int update(T t) {
		// 泛型T中存在版本字段则更新这个字段
		Field field = getFieldByPropertyName("versionInfo");
		if (field != null) {
			setValueOfProperty(t, "versionInfo",
					String.valueOf(System.currentTimeMillis()));
		}
		return getMapper().updateByPrimaryKeySelective(t);
	}

	/**
	 * 查询所有实体
	 * @author 1434262447@qq.com
	 * @return 实体集合
	 */
	public List<T> selectAll() {
		return getMapper().selectAll();
	}

	/**
	 * 根据id查询实体
	 * @author 1434262447@qq.com
	 * @param id
	 *            主键id
	 * @return 实体对象
	 */
	public T getById(Object id) {
		return getMapper().selectByPrimaryKey(id);
	}

	/**
	 * 根据实体对象属性查询，属性条件使用and拼接
	 * @author 1434262447@qq.com
	 * @param t
	 *            实体对象
	 * @return 实体对象
	 */
	public T selectOne(T t) {
		return getMapper().selectOne(t);
	}

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号
	 * @author 1434262447@qq.com
	 * @param t
	 *            实体对象
	 * 
	 * @return 实体对象列表
	 */
	public List<T> select(T t) {
		return getMapper().select(t);
	}

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号，并分页
	 * @author 1434262447@qq.com
	 * @param t
	 *            实体对象
	 * @param pageNum
	 *            第几页
	 * @param pageSize
	 *            每页多少条数据
	 * @return 实体对象列表
	 */
	public List<T> selectAndPaging(T t, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize, "id DESC");
		return getMapper().select(t);
	}

	/**
	 * 根据实体中的属性值进行查询，查询条件使用等号，并分页并封装
	 * 
	 * @author 1434262447@qq.com
	 * @param t
	 *            实体对象
	 * @param pageNum
	 *            第几页
	 * @param pageSize
	 *            每页多少条数据
	 * @return 实体对象列表
	 */
	public PageInfo<T> selectAndPagingAndPackage(T t, Integer pageNum,
			Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize, "id DESC");
		PageInfo<T> page = new PageInfo<T>(getMapper().select(t));
		return page;
	}

	/**
	 * 泛型T的类型(.class)
	 */
	private Class<T> typeOfT;

	/**
	 * 构造函数，取泛型T的类型
	 */
	public BaseServiceImpl() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		typeOfT = (Class<T>) params[0];
	}

	/**
	 * 根据属性名查询泛型T中的该属性
	 * @author 1434262447@qq.com
	 * @param propertyName
	 *            属性名
	 * @return 存在则返回该属性，不存在返回NULL
	 */
	private Field getFieldByPropertyName(String propertyName) {
		Field field_ = null;
		// 取泛型T类中所有的属性(私有)
		Field[] Fields = typeOfT.getDeclaredFields();
		// 遍历属性，取存在的同名属性
		for (Field field : Fields) {
			if (field.getName().equals(propertyName)) {
				field_ = field;
				break;
			}
		}
		return field_;
	}

	/**
	 * 生成属性的setter方法名<br>
	 * 如属性id，生成的方法名setId
	 * @author 1434262447@qq.com
	 * @param propertyName
	 *            属性名称
	 * @return 属性setter方法名
	 */
	private String generateFieldSetterName(String propertyName) {
		byte[] items = propertyName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		String setterName = "set" + new String(items);
		return setterName;
	}

	/**
	 * 生成属性的getter方法名<br>
	 * 如属性id，生成的方法名getId
	 * @author 1434262447@qq.com
	 * @param propertyName
	 *            属性名称
	 * @return 属性getter方法名
	 */
	private String generateFieldGetterName(String propertyName) {
		byte[] items = propertyName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		String getterName = "get" + new String(items);
		return getterName;
	}

	/**
	 * 反射取属性的setter方法
	 * @author 1434262447@qq.com
	 * @param propertyName
	 *            属性名称
	 * @return 属性对应的setter方法
	 */
	private Method generateFieldSetterMethod(String propertyName) {
		// 反射取得属性的setter方法
		Method setterOfProerty = null;
		// 根据属性名获取泛型T中该属性
		Field field = getFieldByPropertyName(propertyName);
		if (field != null) {
			// 取目标属性的的类型
			Class<?> propertyClass = field.getType();
			// 生成属性的setter方法名
			String fieldSetterName = generateFieldSetterName(propertyName);
			try {
				// 获取属性对应的Setter方法
				setterOfProerty = typeOfT.getDeclaredMethod(fieldSetterName,
						new Class[] { propertyClass });
				setterOfProerty.setAccessible(true);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}

		return setterOfProerty;
	}

	/**
	 * 反射取属性的getter方法
	 * @author 1434262447@qq.com
	 * @param propertyName
	 *            属性名称
	 * @return 属性对应的getter方法
	 */
	private Method generateFieldGetterMethod(String propertyName) {
		// 反射取得属性的getter方法
		Method getterOfProerty = null;
		// 根据属性名获取泛型T中该属性
		Field field = getFieldByPropertyName(propertyName);
		if (field != null) {
			// 取目标属性的的类型
			Class<?> propertyClass = field.getType();
			// 生成属性的setter方法名
			String fieldGetterName = generateFieldGetterName(propertyName);
			try {
				// 获取属性对应的Setter方法
				getterOfProerty = typeOfT.getDeclaredMethod(fieldGetterName);
				getterOfProerty.setAccessible(true);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		return getterOfProerty;
	}

	/**
	 * 调用属性的Setter方法，为属性赋值
	 * @author 1434262447@qq.com
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 调用属性setter方法赋值
	 */
	private T setValueOfProperty(T t, String propertyName, Object value) {
		// 生成setter方法
		Method setterOfProerty = this.generateFieldSetterMethod(propertyName);
		try {
			// 调用setter方法赋属性值
			setterOfProerty.invoke(t, value);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}

		return t;
	}

	/**
	 * 调用属性的getter方法，取属性赋值
	 * @author 1434262447@qq.com
	 * @param propertyName
	 *            属性名称
	 * @return 调用属性getter方法赋值
	 */
	private Object getValueOfProperty(T t, String propertyName) {
		Object obj = null;
		// 生成getter方法
		Method getterOfProerty = this.generateFieldGetterMethod(propertyName);
		try {
			// 调用setter方法赋属性值
			obj = getterOfProerty.invoke(t);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return obj;
	}

	/**
	 * 根据属性名称进行单对象查询
	 * @author 1434262447@qq.com
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性代码
	 * @return 对象T
	 */
	public T findByProperty(String propertyName, Object value) {

		T t = null;
		try {
			// 实例化一个属性对象，并赋属性值
			t = typeOfT.newInstance();
			setValueOfProperty(t, propertyName, value);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return this.selectOne(t);
	}

	/**
	 * 根据属性名称进行查询列表
	 * @author 1434262447@qq.com
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性代码
	 * @return 对象T集合
	 */
	public List<T> findListByProperty(String propertyName, Object value) {
		T t = null;
		try {
			// 实例化一个属性对象，并赋属性值
			t = typeOfT.newInstance();
			setValueOfProperty(t, propertyName, value);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return this.select(t);
	}

}
