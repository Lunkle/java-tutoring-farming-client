package interaction;

import java.util.Queue;

import event.ctsevent.CTSEvent;
import event.stcevent.STCEvent;

public abstract class FarmingInteraction {

	private Queue<CTSEvent> ctsBuffer;
	private Queue<STCEvent> stcBuffer;

	public FarmingInteraction(Queue<CTSEvent> ctsBuffer, Queue<STCEvent> stcBuffer) {
		this.ctsBuffer = ctsBuffer;
		this.stcBuffer = stcBuffer;
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

}
