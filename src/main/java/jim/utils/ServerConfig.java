package jim.utils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
public class ServerConfig {
	public static String get(String key) {
		Properties prop = new Properties();
		try {
			prop.load(new InputStreamReader(ServerConfig.class.getClassLoader()
					.getResourceAsStream("config.properties"), "UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}

	public static void main(String[] args) {
		System.out.println(get("mongo"));
		System.out.println(get("redis.maxWaitMillis"));
	}
}
