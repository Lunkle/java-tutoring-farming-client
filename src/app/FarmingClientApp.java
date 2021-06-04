package app;

import java.io.IOException;

import network.ClientSideEventSenderAndReceiver;

public class FarmingClientApp {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// Start a new ClientSideEventSenderAndReceiver
		new ClientSideEventSenderAndReceiver().run();
	}

}
