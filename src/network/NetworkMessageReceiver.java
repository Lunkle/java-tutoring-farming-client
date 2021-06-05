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
			Thread.yield();
		}
	}

	private void readEvent() {
		try {
			STCEvent readEvent = (STCEvent) objectInputStream.readObject();
			stcBuffer.add(readEvent);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		isDone = true;
	}

}
