package interaction.botinteraction;

import event.ctsevent.CTSEvent;
import event.ctsevent.session.CloseSessionRequest;
import event.stcevent.STCEvent;
import interaction.FarmingInteraction;
import network.NetworkingBuffers;

public class EndingInteraction extends FarmingInteraction {

	public EndingInteraction(NetworkingBuffers buffers) {
		super(buffers);
	}

	@Override
	public void begin() {
		CTSEvent endingRequest = new CloseSessionRequest(0);
		System.out.println(endingRequest.getDescription());
		sendEvent(endingRequest);

		while (!hasUnreadEvents()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		STCEvent endingConfirmationMessage = readEvent();
		System.out.println(endingConfirmationMessage.getDescription());
	}

}
