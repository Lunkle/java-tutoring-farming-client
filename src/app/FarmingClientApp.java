package app;

import java.io.IOException;
import java.util.Queue;

import event.ctsevent.CTSEvent;
import event.stcevent.STCEvent;
import network.ClientSideEventSenderAndReceiver;

/**
 * The entry point of the client.
 * 
 * @author Jay
 *
 */
public class FarmingClientApp {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// Start a new ClientSideEventSenderAndReceiver
		ClientSideEventSenderAndReceiver csesas = new ClientSideEventSenderAndReceiver();
		csesas.run();
		Queue<CTSEvent> sendEventBuffer = csesas.getSendEventBuffer();
		Queue<STCEvent> receiveEventBuffer = csesas.getReceiveEventBuffer();
		// Put events into sendEventBuffer to send them to the server.
		// Check receiveEventBuffer periodically for events sent from the server.
	}

}
