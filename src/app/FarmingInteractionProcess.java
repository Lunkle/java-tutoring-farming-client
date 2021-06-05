package app;

import java.util.ArrayList;
import java.util.List;

import interaction.FarmingInteraction;
import interaction.typing.TypingInteraction;
import network.NetworkingBuffers;

public class FarmingInteractionProcess {

	private List<FarmingInteraction> interactions = new ArrayList<>();

	public FarmingInteractionProcess(NetworkingBuffers buffers) {
		// ============ADD OR REMOVE INTERACTIONS OF YOUR OWN IMPLEMENTATION!=========
		// ===========================================================================
		FarmingInteraction typingInteraction = new TypingInteraction(buffers);
		interactions.add(typingInteraction);
	}

	public void begin() {
		for (FarmingInteraction iteraction : interactions) {
			iteraction.begin();
		}
	}

}
