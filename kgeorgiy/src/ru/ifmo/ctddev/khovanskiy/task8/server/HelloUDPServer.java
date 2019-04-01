package ru.ifmo.ctddev.khovanskiy.task8.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HelloUDPServer implements Runnable {

	private static class RequestHandler implements Runnable {
		private static final Charset charset = Charset.forName("UTF-8");
		private DatagramSocket socket;
		private DatagramPacket packet;

		/**
		 * Default constructor
		 * 
		 * @param socket
		 *            server socket
		 * @param packet
		 *            packet for processing
		 */
		public RequestHandler(DatagramSocket socket, DatagramPacket packet) {
			this.socket = socket;
			this.packet = packet;
		}

		@Override
		public void run() {
			try {
				InetAddress address = packet.getAddress();
				int port = packet.getPort();

				String s = new String(packet.getData(), packet.getOffset(),
						packet.getLength(), charset);

				String response = "Hello, " + s;
				byte[] b = response.getBytes(charset);
				DatagramPacket outputPacket = new DatagramPacket(b, b.length,
						address, port);

				socket.send(outputPacket);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private final static int THREADS_COUNT = 10;
	private final static int BUFFER_SIZE = 1024;
	private int port;
	private Executor executor = Executors.newFixedThreadPool(THREADS_COUNT);

	/**
	 * Default constructor
	 * 
	 * @param port
	 *            server`s port
	 */
	public HelloUDPServer(int port) {
		this.port = port;
	}

	@Override
	public void run() {
		try (DatagramSocket socket = new DatagramSocket(port)) {
			while (true) {
				try {
					byte[] buffer = new byte[BUFFER_SIZE];
					DatagramPacket packet = new DatagramPacket(buffer,
							buffer.length);
					socket.receive(packet);
					executor.execute(new RequestHandler(socket, packet));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
	}

	private static HelloUDPServer server;

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: port");
			System.exit(0);
		}
		int port = Integer.parseInt(args[0]);
		server = new HelloUDPServer(port);
		server.run();
	}
}
