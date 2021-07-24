package interaction.botinteraction.craft;

import event.ctsevent.game.CraftItemRequest;
import interaction.FarmingInteraction;
import network.NetworkingBuffers;

public class CraftInteraction extends FarmingInteraction {

	public CraftInteraction(NetworkingBuffers buffers) {
		super(buffers);
	}

	@Override
	public void begin() {
//		STCEvent stcEvent = readEvent();
//		CTSEvent ctsEvent = null;
//		sendEvent(ctsEvent);

		int j = 300;
		for (int i = 0; i < j; i++) {
			craft("cotton_cloth");
			craft("cotton_canvas");
			craft("hempen_rope");
			System.out.println("Sent event");
		}
		for (int i = 0; i < j; i++) {
			while (!hasUnreadEvents()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			readEvent();
			readEvent();
			readEvent();
		}

	}

	private void craft(String string) {
		sendEvent(new CraftItemRequest(0, string));
	}

}
