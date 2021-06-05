package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Queue;

import event.stcevent.STCEvent;

public class NetworkMessageReceiver implements Runnable {

	private ObjectInputStream objectInputStream;
	private Queue<STCEvent> stcBuffer;
	private boolean isDone = false;

	public NetworkMessageReceiver(ObjectInputStream objectInputStream, Queue<STCEvent> stcBuffer) {
		this.objectInputStream = objectInputStream;
		this.stcBuffer = stcBuffer;
	}

	@Override
	public void run() {
		while (!isDone) {
			readEvent();
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

	private void readEvent() {
		try {
			if (objectInputStream.available() > 0) {
				STCEvent readEvent = (STCEvent) objectInputStream.readObject();
				stcBuffer.add(readEvent);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		isDone = true;
	}

}
