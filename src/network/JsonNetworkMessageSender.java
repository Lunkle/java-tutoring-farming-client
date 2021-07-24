package network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Queue;

import com.google.gson.Gson;

import event.ctsevent.CTSEvent;

public class JsonNetworkMessageSender implements Runnable {

	private BufferedWriter bufferedWriter;
	private Queue<CTSEvent> ctsBuffer;
	private boolean isDone = false;
	private Gson gson;

	public JsonNetworkMessageSender(Gson gson, BufferedWriter bufferedWriter, Queue<CTSEvent> ctsBuffer) {
		this.gson = gson;
		this.bufferedWriter = bufferedWriter;
		this.ctsBuffer = ctsBuffer;
	}

	@Override
	public void run() {
		while (!isDone) {
			writeEvent();
			sleep();
		}
	}

	private void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void writeEvent() {
		while (!ctsBuffer.isEmpty()) {
			try {
				bufferedWriter.write(gson.toJson(ctsBuffer.poll(), CTSEvent.class) + '\n');
				bufferedWriter.flush();
			} catch (IOException e) {
				System.out.println("Closing message sender");
			}
		}
	}

	public void close() {
		isDone = true;
	}

}
