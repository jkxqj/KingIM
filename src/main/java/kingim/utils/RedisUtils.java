package kingim.utils;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class RedisUtils {

	private static RedisTemplate<String, Object> redisTemplate = SpringContextHolder
			.getBean("redisTemplate");

	public static void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	public static Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	public static Object getList(String key) {
		return redisTemplate.opsForList().leftPop(key);
	}

	/**
	 * key是否存在
	 * 
	 * @return
	 */
	public static boolean exists(final String key) {
		Boolean execute = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				return connection.exists(key.getBytes());
			}
		});
		return execute;
	}

	/**
	 * value的长度
	 * 
	 * @param key
	 * @return
	 */
	public static long llen(String key) {
		return redisTemplate.boundListOps(key).size();
	}

	/**
	 * lpop
	 * 
	 * @param key
	 * @return
	 */
	public static Object lpop(String key) {
		return redisTemplate.boundListOps(key).leftPop();
	}

	/**
	 * rpop
	 * 
	 * @param key
	 * @return
	 */
	public static Object rpop(String key) {
		return redisTemplate.boundListOps(key).rightPop();
	}

	/**
	 * lpush
	 * 
	 * @param key
	 * @return
	 */
	public static void lpush(String key, String value) {
		redisTemplate.boundListOps(key).leftPush(value);
	}
}
