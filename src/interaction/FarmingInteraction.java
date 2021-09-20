package interaction;

import java.util.Queue;

import event.ctsevent.CTSEvent;
import event.stcevent.STCEvent;
import network.NetworkingBuffers;

public abstract class FarmingInteraction {

	private Queue<CTSEvent> ctsBuffer;
	private Queue<STCEvent> stcBuffer;

	public FarmingInteraction(NetworkingBuffers buffers) {
		this.ctsBuffer = buffers.getCtsBuffer();
		this.stcBuffer = buffers.getStcBuffer();
	}

	public abstract void begin();

	public void sendEvent(CTSEvent event) {
		ctsBuffer.add(event);
	}

	public boolean hasUnreadEvents() {
		return !stcBuffer.isEmpty();
	}

	public STCEvent readEvent() {
		if (stcBuffer.isEmpty()) {
			return null;
		}
		return stcBuffer.poll();
	}

	public STCEvent readNextEvent() {
		while (stcBuffer.isEmpty()) {
			sleep(20);
		}
		return stcBuffer.poll();
	}

	public void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
