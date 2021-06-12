package interaction.botinteraction.startupinteraction;

import event.ctsevent.CTSEvent;
import event.ctsevent.session.LoginRequest;
import event.stcevent.STCEvent;
import interaction.FarmingInteraction;
import network.NetworkingBuffers;

public class LoginInteraction extends FarmingInteraction {

	public LoginInteraction(NetworkingBuffers buffers) {
		super(buffers);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void begin() {
//		STCEvent stcEvent = readEvent();
//		CTSEvent ctsEvent = null;
//		sendEvent(ctsEvent);

		CTSEvent loginRequest = new LoginRequest(0, "theavenger71", "password");
		System.out.println(loginRequest.getDescription());
		sendEvent(loginRequest);

		while (!hasUnreadEvents()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		STCEvent loginConfirmationMessage = readEvent();
		System.out.println(loginConfirmationMessage.getDescription());
	}

}
