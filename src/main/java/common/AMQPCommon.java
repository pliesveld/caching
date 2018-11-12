package common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class AMQPCommon {

	public static Channel connect() throws Exception {
		String host;
		int port;

		ConnectionFactory factory = new ConnectionFactory();
		host = getConnectionHost();
		port = getConnectionPort();
		factory.setHost(host);
		factory.setPort(port);
		Connection conn = factory.newConnection();
		return conn.createChannel();
	}

	public static void close(Channel channel) throws Exception {
		channel.close();
		channel.getConnection().close();
	}

	private static int getConnectionPort() {
		String port = System.getenv("AMQP_PORT");
		if (port != null && !"".endsWith(port)) {
			return Integer.valueOf(port);
		}
		return 5672;
	}

	private static String getConnectionHost() {
		String host = System.getenv("AMQP_HOST");
		if (host == null || "".equals(host)) {
			return "127.0.0.1";
		}
		return host;
	}
}
