package interaction.botinteraction.farm;

import event.ctsevent.CTSEvent;
import event.ctsevent.game.HarvestLandSlotRequest;
import event.ctsevent.game.SowSeedRequest;
import event.stcevent.STCEvent;
import interaction.FarmingInteraction;
import network.NetworkingBuffers;

public class HempAndCottonFarm extends FarmingInteraction {

	public HempAndCottonFarm(NetworkingBuffers buffers) {
		super(buffers);
	}

	@Override
	public void begin() {
//		STCEvent stcEvent = readEvent();
//		CTSEvent ctsEvent = null;
//		sendEvent(ctsEvent);

		gridHarvestSeed();

		for (int i = 0; i < 300; i++) {
			gridSowSeed();
			sleep(1 * 60 * 1_000);
			gridHarvestSeed();
		}

		System.out.println("FARMING COMPLETE");

	}

	private void gridSowSeed() {
		sowSeed(0, 0, "acorn");
		sowSeed(0, 1, "acorn");
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				sowSeed(i, j, "hemp_seed");
			}
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 2; j < 4; j++) {
				sowSeed(i, j, "cotton_seed");
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
				harvestSeed(i, j);
			}
		}
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sowSeed(int i, int j, String string) {
		CTSEvent sowSeed = new SowSeedRequest(0, 0, 0, i, j, string);
		System.out.println(sowSeed.getDescription());
		sendEvent(sowSeed);
	}

	private void harvestSeed(int i, int j) {
		CTSEvent harvestSeed = new HarvestLandSlotRequest(0, 0, 0, i, j);
		System.out.println(harvestSeed.getDescription());
		sendEvent(harvestSeed);
	}

}
