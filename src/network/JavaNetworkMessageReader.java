package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Queue;

import event.stcevent.STCEvent;

public class JavaNetworkMessageReader implements Runnable {

	private ObjectInputStream objectInputStream;
	private Queue<STCEvent> stcBuffer;
	private boolean isDone = false;

	public JavaNetworkMessageReader(ObjectInputStream objectInputStream, Queue<STCEvent> stcBuffer) {
		this.objectInputStream = objectInputStream;
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
			STCEvent readEvent = (STCEvent) objectInputStream.readObject();
			stcBuffer.add(readEvent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Closing message reader");
			close();
		}
	}

	public void close() {
		isDone = true;
	}

}
