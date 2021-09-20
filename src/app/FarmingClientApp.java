package app;

import java.net.InetAddress;
import java.net.UnknownHostException;

import network.JsonClientNetworking;
import network.NetworkingBuffers;

public class FarmingClientApp {

	public static void main(String[] args) throws InterruptedException, UnknownHostException {
		System.out.println("Starting client at " + InetAddress.getLocalHost().getHostAddress());
		System.out.println("Computer name: " + InetAddress.getLocalHost().getHostName());
		NetworkingBuffers buffers = new NetworkingBuffers();
//		JavaClientNetworking clientNetworking = new JavaClientNetworking("99.250.93.242", 45000, buffers);
		JsonClientNetworking clientNetworking = new JsonClientNetworking("99.250.93.242", 45001, buffers);
		clientNetworking.start();
		FarmingInteractionProcess process = new FarmingInteractionProcess(buffers);
		process.begin();
		clientNetworking.close();
	}

}
