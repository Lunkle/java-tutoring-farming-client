package interaction.botinteraction.sell;

import event.ctsevent.game.ShopCollectRequest;
import event.ctsevent.game.ShopSellRequest;
import interaction.FarmingInteraction;
import network.NetworkingBuffers;

public class EarOfCornSellingInteraction extends FarmingInteraction {

	public EarOfCornSellingInteraction(NetworkingBuffers buffers) {
		super(buffers);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void begin() {
		int x = 2;
		ShopCollectRequest shopCollectRequest = new ShopCollectRequest(0, 0, 0);
		sendEvent(shopCollectRequest);
		System.out.println(readNextEvent());

		ShopSellRequest shopSellRequest = new ShopSellRequest(0, 0, 0, "Ear of corn", x);
		sendEvent(shopSellRequest);
		System.out.println(readNextEvent());

	}

}
