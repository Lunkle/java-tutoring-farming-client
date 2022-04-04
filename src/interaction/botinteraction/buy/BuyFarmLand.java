package interaction.botinteraction.buy;

import event.ctsevent.game.FarmLandPurchaseRequest;
import interaction.FarmingInteraction;
import network.NetworkingBuffers;

public class BuyFarmLand extends FarmingInteraction {

	public BuyFarmLand(NetworkingBuffers buffers) {
		super(buffers);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void begin() {
		// TODO Auto-generated method stub
		FarmLandPurchaseRequest farmLandPurchaseRequest = new FarmLandPurchaseRequest(0, 0, 1);
		sendEvent(farmLandPurchaseRequest);
		System.out.println(readNextEvent());
	}

}
