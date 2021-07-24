package interaction.botinteraction.farm;

import event.ctsevent.CTSEvent;
import event.ctsevent.game.HarvestLandSlotRequest;
import event.ctsevent.game.SowSeedRequest;
import event.stcevent.STCEvent;
import event.stcevent.game.HarvestLandSlotFailResponse;
import event.stcevent.game.HarvestLandSlotSuccessResponse;
import interaction.FarmingInteraction;
import network.NetworkingBuffers;

public class CornFarm extends FarmingInteraction {

	public CornFarm(NetworkingBuffers buffers) {
		super(buffers);
	}

	@Override
	public void begin() {
//		STCEvent stcEvent = readEvent();
//		CTSEvent ctsEvent = null;
//		sendEvent(ctsEvent);

		gridHarvestSeed();

		for (int i = 0; i < 20; i++) {
			gridSowSeed();
			sleepThreeMins();
			gridHarvestSeed();
		}

		System.out.println("FARMING COMPLETE");

	}

	private void gridSowSeed() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				sowCornSeed(i, j);
			}

		}
		for (int k = 0; k < 16; k++) {
			while (!hasUnreadEvents()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			STCEvent sowSeedResponse = readEvent();
			System.out.println(sowSeedResponse.getDescription());
		}
	}

	private void gridHarvestSeed() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				harvestCornSeed(i, j);

			}

		}

		int extraCornKernel = 0;
		int goldCornKernel = 0;

		for (int k = 0; k < 16; k++) {
			while (!hasUnreadEvents()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			STCEvent harvestSeedResponse = readEvent();
//			System.out.println(harvestSeedResponse.getDescription());
//			System.out.println(harvestSeedResponse.getClass());
			if (harvestSeedResponse instanceof HarvestLandSlotFailResponse) {
				HarvestLandSlotFailResponse failedHarvest = (HarvestLandSlotFailResponse) harvestSeedResponse;
			} else if (harvestSeedResponse instanceof HarvestLandSlotSuccessResponse) {
				HarvestLandSlotSuccessResponse sucessfulHarvest = (HarvestLandSlotSuccessResponse) harvestSeedResponse;
				String[] itemNames = sucessfulHarvest.getItems();
				int[] itemAmounts = sucessfulHarvest.getAmounts();
				for (int i = 0; i < itemNames.length; i++) {
					if (itemNames[i].equals("Corn kernel")) {
						extraCornKernel = extraCornKernel + itemAmounts[i] - 1;
					}
					if (itemNames[i].equals("Golden corn kernel")) {
						goldCornKernel = goldCornKernel + itemAmounts[i];
					}
				}
			}
		}
		System.out.println(extraCornKernel + " corn kernels farmed");
		System.out.println(goldCornKernel + " golden corn kernels farmed");
	}

	private void sleepThreeMins() {
		try {
			Thread.sleep(180000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sowCornSeed(int i, int j) {
		CTSEvent sowSeed = new SowSeedRequest(0, 0, 0, i, j, "corn kernel");
		System.out.println(sowSeed.getDescription());
		sendEvent(sowSeed);
	}

	private void harvestCornSeed(int i, int j) {
		CTSEvent harvestSeed = new HarvestLandSlotRequest(0, 0, 0, i, j);
		System.out.println(harvestSeed.getDescription());
		sendEvent(harvestSeed);
	}

}
