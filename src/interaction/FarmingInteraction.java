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

	protected void sleep(int x) {
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
