package app;

import java.net.InetAddress;
import java.net.UnknownHostException;

import network.ClientNetworking;
import network.NetworkingBuffers;

public class FarmingClientApp {

	public static void main(String[] args) throws InterruptedException, UnknownHostException {
		System.out.println("Starting client at " + InetAddress.getLocalHost().getHostAddress());
		System.out.println("Computer name: " + InetAddress.getLocalHost().getHostName());
		NetworkingBuffers buffers = new NetworkingBuffers();
		ClientNetworking clientNetworking = new ClientNetworking("72.140.156.47", buffers);
		clientNetworking.start();
		FarmingInteractionProcess process = new FarmingInteractionProcess(buffers);
		process.begin();
		clientNetworking.close();
	}

}
