package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import event.ctsevent.CTSEvent;

public class FarmingClient implements Runnable {

	private boolean running;
	private Queue<CTSEvent> eventBuffer;
	private Socket socket;

	public FarmingClient() {
		running = false;
		eventBuffer = new LinkedList<>();
	}

	@Override
	public void run() {
		running = true;
		try {
			System.out.println("Contacting Donny's server...");
			socket = new Socket("72.140.156.47", 45000);
			System.out.println("Connection accepted.");
		} catch (Exception e) {
			System.err.println("Failed to connect to Donny's server.");
			e.printStackTrace();
			System.out.println("Shutting down client.");
			return;
		}

		ObjectInputStream in = null;
		ObjectOutputStream out = null;

		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (running) {
			if (!eventBuffer.isEmpty()) {
				CTSEvent event = eventBuffer.poll();
				try {
					out.writeObject(event);
					System.out.println("[Message sent]: " + event.getDescription());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Thread.yield();
		}
	}

	public void close() {
		if (running) {
			running = false;
		}
	}

}
