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

import event.EventSerializer;
import event.ctsevent.CTSEvent;
import event.stcevent.STCEvent;

/**
 * In charge of sending {@link CTSEvent}s and receiving {@link STCEvent}s.
 * 
 * @author Jay
 *
 */
public class ClientSideEventSenderAndReceiver implements Runnable {

	private boolean running;
	private Queue<CTSEvent> sendEventBuffer;
	private Queue<STCEvent> receiveEventBuffer;
	private Socket socket;

	@Override
	public void run() {
		// Initialize fields
		init();
		// Shut down if unable to connect
		if (running == false) {
			return;
		}
		// Begin sender and receiver threads if connected successfully
		startSenderAndReceiverThreads();
	}

	/**
	 * Initializes fields running, sendEventBuffer, receiveEventBuffer, and socket.
	 */
	private void init() {
		sendEventBuffer = new LinkedList<>();
		receiveEventBuffer = new LinkedList<>();
		try {
			System.out.println("Contacting Donny's server...");
			socket = new Socket("72.140.156.47", 45000);
			// Set the read() method on the socket's input stream to only block for 160 ms
			// before throwing a SocketTimeoutException.
			socket.setSoTimeout(160);
			System.out.println("Connection accepted.");
		} catch (Exception e) {
			System.err.println("Failed to connect to Donny's server.");
			e.printStackTrace();
			System.out.println("Shutting down client.");
			return;
		}
		running = true;
	}

	/**
	 * Starts two threads that send and receive events, respectively.
	 */
	private void startSenderAndReceiverThreads() {
		// Create a new thread pool
		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		// Start a thread that sends events
		threadPool.execute(() -> {
			// Initialize an ObjectOutputStream
			ObjectOutputStream out = null;
			try {
				out = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			while (running) {
				// Send events through out
				sendEvents(out);
				Thread.yield();
			}
		});
		// Start a thread that receives events
		threadPool.execute(() -> {
			// Initialize an ObjectInputStream
			ObjectInputStream in = null;
			try {
				in = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
			while (running) {
				// Receive events through in
				receiveEvents(in);
				Thread.yield();
			}
		});
	}

	private void sendEvents(ObjectOutputStream out) {
		if (!sendEventBuffer.isEmpty()) {
			CTSEvent event = sendEventBuffer.poll();
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
			STCEvent deserialized = (STCEvent) EventSerializer.instance().deserialize(bytes);
			System.out.println("[Message received]: " + deserialized.getDescription());
			receiveEventBuffer.add(deserialized);
		} catch (SocketTimeoutException e) {
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Getter for the sendEventBuffer.
	 * 
	 * @return the sendEventBuffer
	 */
	public Queue<CTSEvent> getSendEventBuffer() {
		return sendEventBuffer;
	}

	/**
	 * If the client side event sender and receiver are still running.
	 * 
	 * @return the status of the client side event sender and receiver
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Getter for the receiveEventBuffer.
	 * 
	 * @return the receiveEventBuffer
	 */
	public Queue<STCEvent> getReceiveEventBuffer() {
		return receiveEventBuffer;
	}

	/**
	 * Ends the sender and receiver threads.
	 */
	public void close() {
		if (running) {
			running = false;
		}
	}

}
