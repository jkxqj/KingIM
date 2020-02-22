package kingim.ws;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

public class WSServer {

	private static Logger logger = Logger.getLogger(WSServer.class);

	private static class SingletionWSServer {
		static final WSServer instance = new WSServer();
	}
	
	public static WSServer getInstance() {
		return SingletionWSServer.instance;
	}
	
	private EventLoopGroup boss;
	private EventLoopGroup worker;
	private ServerBootstrap server;
	private ChannelFuture future;
	
	public WSServer() {
		boss = new NioEventLoopGroup();
		worker = new NioEventLoopGroup();
		server = new ServerBootstrap();
		server.group(boss, worker).channel(NioServerSocketChannel.class)
			.childHandler(new WSServerInitialzer());
	}

	public void start() {
		int port = 8888;
		future = server.bind(port);
		logger.info("Netty WebSocket服务器启动成功,端口：" + port);
	}
}
