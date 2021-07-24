package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Queue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import event.ctsevent.CTSEvent;
import event.stcevent.STCEvent;
import network.json.GsonRequestSerializer;
import network.json.GsonResponseDeserializer;

/**
 * In charge of sending {@link CTSEvent}s and receiving {@link STCEvent}s.
 * <p>
 * You probably don't need to change this.
 * </p>
 * 
 * @author Jay
 *
 */
public class JsonClientNetworking {

	private Socket socket;
	private String serverIp;
	private Queue<CTSEvent> ctsBuffer;
	private Queue<STCEvent> stcBuffer;
	private JsonNetworkMessageReader reader;
	private JsonNetworkMessageSender sender;
	private Thread readerThread;
	private Thread senderThread;
	private boolean started = false;
	private int serverPort;

	public JsonClientNetworking(String serverIp, int serverPort, NetworkingBuffers buffers) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.ctsBuffer = buffers.getCtsBuffer();
		this.stcBuffer = buffers.getStcBuffer();
	}

	public void start() {
		connectToServer();
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(CTSEvent.class, new GsonRequestSerializer())
				.registerTypeAdapter(STCEvent.class, new GsonResponseDeserializer())
				.create();
		sender = new JsonNetworkMessageSender(gson, getOutputStream(), ctsBuffer);
		reader = new JsonNetworkMessageReader(gson, getInputStream(), stcBuffer);
		readerThread = new Thread(reader);
		readerThread.start();
		senderThread = new Thread(sender);
		senderThread.start();
		started = true;
	}

	private void connectToServer() {
		System.out.println("Contacting server...");
		try {
			socket = new Socket(serverIp, serverPort);
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
				reader.close();
				readerThread.join();
				sender.close();
				senderThread.join();
				socket.close();
			} catch (InterruptedException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private BufferedWriter getOutputStream() {
		try {
			return new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Failed to get output stream from server");
	}

	private BufferedReader getInputStream() {
		try {
			return new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Failed to get input stream from server");
	}

}
