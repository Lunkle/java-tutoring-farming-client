package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Queue;

import event.stcevent.STCEvent;

public class NetworkMessageReader implements Runnable {

	private ObjectInputStream objectInputStream;
	private Queue<STCEvent> stcBuffer;
	private boolean isDone = false;

	public NetworkMessageReader(ObjectInputStream objectInputStream, Queue<STCEvent> stcBuffer) {
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
			STCEvent readEvent = (STCEvent) objectInputStream.readObject();
			stcBuffer.add(readEvent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Closing message reader");
		}
	}

	public void close() {
		isDone = true;
	}

}
