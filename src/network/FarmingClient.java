package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import event.ctsevent.CTSEvent;
import event.stcevent.STCEvent;

public class FarmingClient implements Runnable {

	private boolean running;
	private Queue<CTSEvent> toSendEventBuffer;
	private Queue<STCEvent> receiveEventBuffer;
	private Socket socket;

	public FarmingClient() {
		running = false;
		toSendEventBuffer = new LinkedList<>();
		receiveEventBuffer = new LinkedList<>();
	}

	@Override
	public void run() {
		try {
			System.out.println("Contacting Donny's server...");
			socket = new Socket("72.140.156.47", 45000);
			socket.setSoTimeout(160);
			System.out.println("Connection accepted.");
		} catch (Exception e) {
			System.err.println("Failed to connect to Donny's server.");
			e.printStackTrace();
			System.out.println("Shutting down client.");
			return;
		}
		running = true;
		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		threadPool.execute(() -> {
			ObjectOutputStream out = null;
			try {
				out = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			while (running) {
				sendEvents(out);
				Thread.yield();
			}
		});
		threadPool.execute(() -> {
			ObjectInputStream in = null;
			try {
				in = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			while (running) {
				Thread.yield();
			}
		});
	}

	private void sendEvents(ObjectOutputStream out) {
		if (!toSendEventBuffer.isEmpty()) {
			CTSEvent event = toSendEventBuffer.poll();
			try {
				out.writeObject(event);
				System.out.println("[Message sent]: " + event.getDescription());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void receiveEvents(ObjectInputStream in) {
		byte[] bytes = new byte[1024];
		try {
			in.read(bytes);
			// Deserialize byte array into a STCEvent
			// Add STCEvent to receiveEventBuffer
		} catch (SocketTimeoutException e) {
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		if (running) {
			running = false;
		}
	}

}
