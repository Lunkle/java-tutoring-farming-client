package app;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

import event.ctsevent.CTSEvent;
import event.stcevent.STCEvent;
import interaction.FarmingInteraction;
import interaction.typing.TypingInteraction;
import network.ClientNetworking;

public class FarmingClientApp {

	public static void main(String[] args) throws InterruptedException, UnknownHostException {
		System.out.println("Starting client at " + InetAddress.getLocalHost().getHostAddress());
		System.out.println("Computer name: " + InetAddress.getLocalHost().getHostName());
		Queue<CTSEvent> ctsBuffer = new LinkedList<>();
		Queue<STCEvent> stcBuffer = new LinkedList<>();
		ClientNetworking clientNetworking = new ClientNetworking("72.140.156.47", ctsBuffer, stcBuffer);
		clientNetworking.start();
		// ==================CHANGE OUT THIS INTERACTION WITH YOUR OWN IMPLEMENTATION!
		FarmingInteraction interaction = new TypingInteraction(ctsBuffer, stcBuffer);
		// ===========================================================================
		interaction.begin();
	}

}
