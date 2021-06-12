package interaction.botinteraction.farm;

import event.ctsevent.CTSEvent;
import event.ctsevent.game.HarvestLandSlotRequest;
import event.ctsevent.game.SowSeedRequest;
import event.stcevent.STCEvent;
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

		for (int i = 0; i < 10; i++) {
			gridSowSeed();
			sleepThreeMins();
			gridHarvestSeed();
		}

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
		for (int k = 0; k < 16; k++) {
			while (!hasUnreadEvents()) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			STCEvent harvestSeedResponse = readEvent();
			System.out.println(harvestSeedResponse.getDescription());
		}
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
