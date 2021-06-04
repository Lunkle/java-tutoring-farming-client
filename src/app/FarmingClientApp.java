package app;

import java.util.Queue;

import event.ctsevent.CTSEvent;
import event.ctsevent.LoginRequest;
import event.stcevent.STCEvent;
import event.stcevent.login.LoginFail;
import event.stcevent.login.LoginSuccess;
import network.ClientNetworking;

/**
 * The entry point of the client.
 * 
 * @author Jay
 *
 */
public class FarmingClientApp {

	public static void main(String[] args) throws InterruptedException {
		// Start a new ClientNetworking
		ClientNetworking clientNetworking = new ClientNetworking();
		clientNetworking.run();
		Queue<CTSEvent> sendEventBuffer = clientNetworking.getSendEventBuffer();
		Queue<STCEvent> receiveEventBuffer = clientNetworking.getReceiveEventBuffer();
		// Put events into sendEventBuffer to send them to the server.
		// Check receiveEventBuffer periodically for events sent from the server.

		// Sending events example:
		sendEventBuffer.add(new LoginRequest(0, "Incorrect username", "Incorrect password"));

		// Receiving events example:
		for (;;) {
			// poll() returns null if receiveEventBuffer was empty
			STCEvent received = receiveEventBuffer.poll();
			if (received != null) {
				if (received instanceof LoginFail) {
					System.out.println(received.getDescription()); // Prints "Failed login"
				} else if (received instanceof LoginSuccess) {
					System.out.println(received.getDescription()); // Prints "Successful login"
				}
			} else {
				// Wait 0.5 seconds before checking again
				Thread.sleep(500);
			}
		}
	}

}
