package interaction.typing;

import event.ctsevent.CTSEvent;
import event.ctsevent.game.HarvestLandSlotRequest;
import event.ctsevent.game.InventoryOverviewRequest;
import event.ctsevent.game.SowSeedRequest;
import event.stcevent.STCEvent;
import event.stcevent.game.InventoryOverviewResponse;
import interaction.FarmingInteraction;
import network.NetworkingBuffers;

public class AutomaticPlanting extends FarmingInteraction {

	public AutomaticPlanting(NetworkingBuffers buffers) {
		super(buffers);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void begin() {
		String[] items = null;
		int[] amounts = null;
		int wheatIndex = -1;
		int count = 0;
		STCEvent inventory = getInvOverview();
		System.out.println(inventory.getDescription());
		System.out.println(inventory.getClass().getSimpleName());
		if (inventory instanceof InventoryOverviewResponse) {
			InventoryOverviewResponse inventoryResponse = (InventoryOverviewResponse) inventory;
			items = inventoryResponse.getItems();
			amounts = inventoryResponse.getAmounts();
		}
		for (int i = 0; i < items.length; i++) {
			if ((items[i]).equals("Corn kernel")) {
				wheatIndex = i;
			}
		}
		while (count < 50) {
			if (amounts[wheatIndex] != 0) {
				for (int j = 0; j < amounts[wheatIndex]; j++) {

					if (amounts[wheatIndex] > 12) {
						plantWheatSeed(0, 0, 0, 3, j);
					}
					if (amounts[wheatIndex] > 8) {
						plantWheatSeed(0, 0, 0, 2, j);
					}
					if (amounts[wheatIndex] > 4) {
						plantWheatSeed(0, 0, 0, 1, j);
					}
					if (amounts[wheatIndex] > 0) {
						plantWheatSeed(0, 0, 0, 0, j);
					}
				}
				sleep(310000);
				for (int k = 0; k < 3; k++) {
					for (int l = 0; l < 3; l++) {
						harvestWheatSeed(0, 0, 0, k, l);
						System.out.println("gotHere");
					}
				}
			}

			count++;
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

	private void plantWheatSeed(long id, int farmLandX, int farmLandY, int row, int col) {
		CTSEvent plant = new SowSeedRequest(id, farmLandX, farmLandY, row, col, "Corn kernel");
		sendEvent(plant);
		while (!hasUnreadEvents()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		STCEvent info = readEvent();
		System.out.println(info.getDescription());
	}

	private void harvestWheatSeed(long id, int farmLandX, int farmLandY, int row, int col) {
		CTSEvent harvest = new HarvestLandSlotRequest(id, farmLandX, farmLandY, row, col);
		sendEvent(harvest);
		while (!hasUnreadEvents()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		STCEvent harvestResponse = readEvent();
		System.out.println(harvestResponse.getDescription());

	}
}
