package interaction.typing;

import event.ctsevent.CTSEvent;
import event.ctsevent.session.CloseSessionRequest;
import interaction.FarmingInteraction;
import network.NetworkingBuffers;

public class Quit extends FarmingInteraction {

	public Quit(NetworkingBuffers buffers) {
		super(buffers);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void begin() {
		// TODO Auto-generated method stub
		CTSEvent event = new CloseSessionRequest(0);
		sendEvent(event);

	}
}
