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
	private NetworkMessageReader reader;
	private NetworkMessageSender sender;
	private Thread readerThread;
	private Thread senderThread;
	private boolean started = false;

	public ClientNetworking(String serverIp, NetworkingBuffers buffers) {
		this.serverIp = serverIp;
		this.ctsBuffer = buffers.getCtsBuffer();
		this.stcBuffer = buffers.getStcBuffer();
	}

	public void start() {
		connectToServer();
		sender = new NetworkMessageSender(getOutputStream(), ctsBuffer);
		reader = new NetworkMessageReader(getInputStream(), stcBuffer);
		readerThread = new Thread(reader);
		readerThread.start();
		senderThread = new Thread(sender);
		senderThread.start();
		started = true;
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
		if (started) {
			try {
				socket.close();
				reader.close();
				readerThread.join();
				sender.close();
				senderThread.join();
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
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
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			return objectInputStream;
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Failed to get input stream from server");
	}

}
