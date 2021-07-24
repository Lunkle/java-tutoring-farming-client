package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Queue;

import com.google.gson.Gson;

import event.stcevent.STCEvent;

public class JsonNetworkMessageReader implements Runnable {

	private BufferedReader bufferedReader;
	private Queue<STCEvent> stcBuffer;
	private boolean isDone = false;
	private Gson gson;

	public JsonNetworkMessageReader(Gson gson, BufferedReader bufferedReader, Queue<STCEvent> stcBuffer) {
		this.gson = gson;
		this.bufferedReader = bufferedReader;
		this.stcBuffer = stcBuffer;
	}

	@Override
	public void run() {
		while (!isDone) {
			readEvent();
		}
	}

	private void readEvent() {
		try {
			STCEvent readEvent = gson.fromJson(bufferedReader.readLine(), STCEvent.class);
			stcBuffer.add(readEvent);
		} catch (IOException e) {
			System.out.println("Closing message reader");
			close();
		}
	}

	public void close() {
		isDone = true;
	}

}
