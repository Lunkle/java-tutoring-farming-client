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

		for (int i = 0; i < 30; i++) {
			gridSowSeed();
			sleep(3 * 60 * 1_000);
			gridHarvestSeed();
		}

		System.out.println("FARMING COMPLETE");

	}

	private void gridSowSeed() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				sowCornSeed(i, j, "corn_kernel");
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
		int extraWheatSeeds = 0;
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
//				HarvestLandSlotFailResponse failedHarvest = (HarvestLandSlotFailResponse) harvestSeedResponse;
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
					if (itemNames[i].equals("Wheat seed")) {
						extraWheatSeeds = extraWheatSeeds + itemAmounts[i] - 1;
					}
				}
			}
		}
		System.out.println(extraWheatSeeds + " extra wheat seeds farmed");
		System.out.println(extraCornKernel + " extra corn kernels farmed");
		System.out.println(goldCornKernel + " golden corn kernels farmed");
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sowCornSeed(int i, int j, String string) {
		CTSEvent sowSeed = new SowSeedRequest(0, 0, 0, i, j, string);
		System.out.println(sowSeed.getDescription());
		sendEvent(sowSeed);
	}

	private void harvestCornSeed(int i, int j) {
		CTSEvent harvestSeed = new HarvestLandSlotRequest(0, 0, 0, i, j);
		System.out.println(harvestSeed.getDescription());
		sendEvent(harvestSeed);
	}

}
