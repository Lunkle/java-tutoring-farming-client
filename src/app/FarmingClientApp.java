package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

public class FarmingClientApp {

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		System.out.println("Starting Java Tutoring farming client.");
		System.out.println("Name of this computer: " + InetAddress.getLocalHost().getCanonicalHostName());
		System.out.println("LAN IP of this computer: " + InetAddress.getLocalHost().getHostAddress());
		System.out.println("Contacting Donny's server...");
		boolean running = true;
		try (Socket socket = new Socket("72.140.156.47", 45000)) {
			System.out.println("Connection accepted");
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			while (running) {
				Object o = in.readObject();
				System.out.println("Object received: " + o);
			}
			in.close();
		}
	}

}
