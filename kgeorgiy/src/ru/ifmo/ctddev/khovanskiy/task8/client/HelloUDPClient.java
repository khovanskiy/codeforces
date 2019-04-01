package ru.ifmo.ctddev.khovanskiy.task8.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HelloUDPClient implements Runnable {

	static class SenderHandler implements Runnable {
		private static final Charset charset = Charset.forName("UTF-8");
		private final static int BUFFER_SIZE = 1024;
		private static int ids = 0;
		private int id;
		private int queryId = 0;
		private String prefix;
		private InetAddress host;
		private int port;
		private byte[] buffer = new byte[BUFFER_SIZE];

		/**
		 * Default constructor
		 * 
		 * @param prefix
		 *            query`s prefix
		 * @param host
		 *            server`s host
		 * @param port
		 *            server` port
		 */
		public SenderHandler(String prefix, InetAddress host, int port) {
			this.prefix = prefix;
			this.host = host;
			this.port = port;
			this.id = ids++;
		}

		@Override
		public void run() {
			try (DatagramSocket socket = new DatagramSocket()) {
				while (true) {
					String request = prefix + "_" + id + "_" + queryId;
					queryId++;

					byte[] b = request.getBytes(charset);
					DatagramPacket outputPacket = new DatagramPacket(b,
							b.length, host, port);

					socket.send(outputPacket);

					DatagramPacket packet = new DatagramPacket(buffer,
							buffer.length);
					socket.receive(packet);

					String serverEcho = new String(packet.getData(),
							packet.getOffset(), packet.getLength(), charset);
					System.out.println(serverEcho);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private final static int THREADS_COUNT = 10;
	private String prefix;
	private InetAddress host;
	private int port;
	private Executor executor = Executors.newFixedThreadPool(THREADS_COUNT);

	/**
	 * Default constructor
	 * 
	 * @param prefix
	 *            query`s prefix
	 * @param host
	 *            server`s host
	 * @param port
	 *            server` port
	 */
	public HelloUDPClient(String prefix, InetAddress host, int port) {
		this.prefix = prefix;
		this.host = host;
		this.port = port;
	}

	@Override
	public void run() {
		for (int i = 0; i < THREADS_COUNT; ++i) {
			executor.execute(new SenderHandler(prefix, host, port));
		}
	}

	private static HelloUDPClient client;

	public static void main(String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("Usage: host port prefix");
			System.exit(0);
		}
		InetAddress host = InetAddress.getByName(args[0]);
		int port = Integer.parseInt(args[1]);
		String prefix = args[2];
		client = new HelloUDPClient(prefix, host, port);
		client.run();
	}
}
