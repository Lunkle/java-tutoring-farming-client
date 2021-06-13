package interaction.typing;

import event.ctsevent.CTSEvent;
import event.ctsevent.session.LoginRequest;
import event.stcevent.STCEvent;
import interaction.FarmingInteraction;
import network.NetworkingBuffers;

public class LoginInteraction extends FarmingInteraction{
	
	public LoginInteraction(NetworkingBuffers buffers) {
		super(buffers);
	}
	public void begin() {
		CTSEvent event = new LoginRequest(0,"ruin688","qwerty");
		System.out.println(event.getDescription());
		sendEvent(event);
		while(!hasUnreadEvents()) {
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		STCEvent STCevent = readEvent();
		System.out.println(STCevent.getDescription());

		
	
	}
}
