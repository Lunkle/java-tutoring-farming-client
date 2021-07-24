package app;

import java.util.ArrayList;
import java.util.List;

import interaction.FarmingInteraction;
import interaction.botinteraction.EndingInteraction;
import interaction.botinteraction.farm.HempAndCottonFarm;
import interaction.botinteraction.startupinteraction.InventoryOverview;
import interaction.botinteraction.startupinteraction.LoginInteraction;
import network.NetworkingBuffers;

public class FarmingInteractionProcess {

	private List<FarmingInteraction> interactions = new ArrayList<>();

	public FarmingInteractionProcess(NetworkingBuffers buffers) {
		// ============ADD OR REMOVE INTERACTIONS OF YOUR OWN IMPLEMENTATION!=========
		// ===========================================================================

		FarmingInteraction loginInteraction = new LoginInteraction(buffers);
		interactions.add(loginInteraction);

		FarmingInteraction inventoryOverview = new InventoryOverview(buffers);
		interactions.add(inventoryOverview);

		FarmingInteraction cornFarm = new HempAndCottonFarm(buffers);
		interactions.add(cornFarm);

//		FarmingInteraction craftingInteraction = new CraftInteraction(buffers);
//		interactions.add(craftingInteraction);

		FarmingInteraction endingInteraction = new EndingInteraction(buffers);
		interactions.add(endingInteraction);

//		FarmingInteraction typingInteraction = new TypingInteraction(buffers);
//		interactions.add(typingInteraction);

	}

	public void begin() {
		for (FarmingInteraction iteraction : interactions) {
			iteraction.begin();
		}
	}

}
