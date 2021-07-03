package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Queue;

import event.ctsevent.CTSEvent;

public class NetworkMessageSender implements Runnable {

	private ObjectOutputStream objectOutputStream;
	private Queue<CTSEvent> ctsBuffer;
	private boolean isDone = false;

	public NetworkMessageSender(ObjectOutputStream objectOutputStream, Queue<CTSEvent> ctsBuffer) {
		this.objectOutputStream = objectOutputStream;
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
				objectOutputStream.writeObject(ctsBuffer.poll());
				objectOutputStream.flush();
			} catch (IOException e) {
				System.out.println("Closing message sender");
			}
		}
	}

	public void close() {
		isDone = true;
	}

}
