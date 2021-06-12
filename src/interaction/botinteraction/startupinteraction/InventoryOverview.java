package interaction.botinteraction.startupinteraction;

import event.ctsevent.CTSEvent;
import event.ctsevent.game.InventoryOverviewRequest;
import event.stcevent.STCEvent;
import interaction.FarmingInteraction;
import network.NetworkingBuffers;

public class InventoryOverview extends FarmingInteraction {

	public InventoryOverview(NetworkingBuffers buffers) {
		super(buffers);
	}

	@Override
	public void begin() {
//		STCEvent stcEvent = readEvent();
//		CTSEvent ctsEvent = null;
//		sendEvent(ctsEvent);

		CTSEvent inventoryRequest = new InventoryOverviewRequest(0);
		System.out.println(inventoryRequest.getDescription());
		sendEvent(inventoryRequest);

		while (!hasUnreadEvents()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		STCEvent inventoryResponse = readEvent();
		System.out.println(inventoryResponse.getDescription());
	}

}
