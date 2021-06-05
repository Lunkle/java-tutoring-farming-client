package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;

import event.ctsevent.CTSEvent;
import event.stcevent.STCEvent;

/**
 * In charge of sending {@link CTSEvent}s and receiving {@link STCEvent}s.
 * <p>
 * You probably don't need to change this.
 * </p>
 * 
 * @author Jay
 *
 */
public class ClientNetworking {

	private Socket socket;
	private String serverIp;
	private Queue<CTSEvent> ctsBuffer;
	private Queue<STCEvent> stcBuffer;
	private NetworkMessageReceiver receiver;
	private NetworkMessageSender sender;

	public ClientNetworking(String serverIp, Queue<CTSEvent> ctsBuffer, Queue<STCEvent> stcBuffer) {
		this.serverIp = serverIp;
		this.ctsBuffer = ctsBuffer;
		this.stcBuffer = stcBuffer;
	}

	public void start() {
		connectToServer();
		receiver = new NetworkMessageReceiver(getInputStream(), stcBuffer);
		sender = new NetworkMessageSender(getOutputStream(), ctsBuffer);
		new Thread(receiver).start();
		new Thread(sender).start();
	}

	private void connectToServer() {
		System.out.println("Contacting server...");
		try {
			socket = new Socket(serverIp, 45000);
			System.out.println("Connection accepted.");
		} catch (Exception e) {
			System.err.println("Failed to connect to Donny's server.");
			e.printStackTrace();
			return;
		}
	}

	public void close() {
		if (receiver != null) {
			receiver.close();
		}
		if (sender != null) {
			sender.close();
		}
	}

	private ObjectOutputStream getOutputStream() {
		try {
			return new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Failed to get output stream from server");
	}

	private ObjectInputStream getInputStream() {
		try {
			return new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Failed to get input stream from server");
	}

}
