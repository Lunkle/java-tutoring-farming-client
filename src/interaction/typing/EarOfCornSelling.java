package interaction.typing;

import event.ctsevent.game.ShopSellRequest;
import interaction.FarmingInteraction;
import network.NetworkingBuffers;

public class EarOfCornSelling extends FarmingInteraction {
	public EarOfCornSelling(NetworkingBuffers b) {
		super(b);
	}

	@Override
	public void begin() {
		ShopSellRequest ssr = new ShopSellRequest(0, 0, 0, "Ear of corn", 1);
		sendEvent(ssr);
		Thread.sleep(1000);
		System.out.println(readEvent());
	}

}
