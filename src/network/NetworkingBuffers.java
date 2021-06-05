package network;

import java.util.LinkedList;
import java.util.Queue;

import event.ctsevent.CTSEvent;
import event.stcevent.STCEvent;

public class NetworkingBuffers {

	private Queue<CTSEvent> ctsBuffer;
	private Queue<STCEvent> stcBuffer;

	public NetworkingBuffers() {
		ctsBuffer = new LinkedList<>();
		stcBuffer = new LinkedList<>();
	}

	public Queue<CTSEvent> getCtsBuffer() {
		return ctsBuffer;
	}

	public Queue<STCEvent> getStcBuffer() {
		return stcBuffer;
	}

}
