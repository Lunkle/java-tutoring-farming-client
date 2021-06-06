package interaction.typing;

import event.ctsevent.CTSEvent;
import event.ctsevent.game.InventoryOverviewRequest;
import event.stcevent.STCEvent;
import event.stcevent.game.InventoryOverviewResponse;

public class Basis {
	/*
	public void begin() {
		String[] items = null;
		int[] amounts = null;
		int wheatIndex = -1;
		STCEvent inventory = getInvOverview();
		System.out.println(inventory.getDescription());
		System.out.println(inventory.getClass().getSimpleName());
		if (inventory instanceof InventoryOverviewResponse) {
			InventoryOverviewResponse inventoryResponse = (InventoryOverviewResponse) inventory;
			items = inventoryResponse.getItems();
			amounts = inventoryResponse.getAmounts();
		}
		for (int i = 0; i < items.length; i++) {
			if ((items[i]).equals("Wheat seed")) {
				wheatIndex = i;
			}
		}
		if (wheatIndex != -1) {
			if (amounts[wheatIndex] != 0) {

			}
		}
	}

	private STCEvent getInvOverview() {
		CTSEvent event = new InventoryOverviewRequest(0);
		sendEvent(event);
		while (!hasUnreadEvents()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		STCEvent inventory = readEvent();
		return inventory;
	}
}*/

}
