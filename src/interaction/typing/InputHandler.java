package interaction.typing;

import java.util.Scanner;

import event.ctsevent.CTSEvent;
import event.ctsevent.game.FarmLandOverviewRequest;
import event.ctsevent.game.LandSlotPlantOverviewRequest;
import event.ctsevent.game.LandSlotTerrainOverviewRequest;
import event.ctsevent.session.CloseSessionRequest;
import event.ctsevent.session.LoginRequest;

public class InputHandler {

	public static CTSEvent handleSendLogin(Scanner scanner) {
		String username = readUsername(scanner);
		String password = readPassword(scanner);
		CTSEvent event = new LoginRequest(0, username, password);
		return event;
	}

	public static CTSEvent handleQuit() {
		CTSEvent event = new CloseSessionRequest(0);
		return event;
	}

	public static CTSEvent handleFarmLandOverview(Scanner scanner) {
		int x = readX(scanner);
		int y = readY(scanner);
		CTSEvent event = new FarmLandOverviewRequest(0, x, y);
		return event;
	}

	public static CTSEvent handlePlantOverview(Scanner scanner) {
		int x = readX(scanner);
		int y = readY(scanner);
		int row = readRow(scanner);
		int col = readCol(scanner);
		CTSEvent event = new LandSlotPlantOverviewRequest(0, x, y, row, col);
		return event;
	}

	public static CTSEvent handleTerrainOverview(Scanner scanner) {
		int x = readX(scanner);
		int y = readY(scanner);
		int row = readRow(scanner);
		int col = readCol(scanner);
		CTSEvent event = new LandSlotTerrainOverviewRequest(0, x, y, row, col);
		return event;
	}

	// ===================PUBLIC & PRIVATE METHODS SEPARATOR========================

	private static String readUsername(Scanner scanner) {
		System.out.print("Username: ");
		String username = scanner.nextLine();
		return username;
	}

	private static String readPassword(Scanner scanner) {
		System.out.print("Password: ");
		String password = scanner.nextLine();
		return password;
	}

	private static int readX(Scanner scanner) {
		System.out.print("X: ");
		int x = Integer.parseInt(scanner.nextLine());
		return x;
	}

	private static int readY(Scanner scanner) {
		System.out.print("Y: ");
		int y = Integer.parseInt(scanner.nextLine());
		return y;
	}

	private static int readRow(Scanner scanner) {
		System.out.print("Row: ");
		int row = Integer.parseInt(scanner.nextLine());
		return row;
	}

	private static int readCol(Scanner scanner) {
		System.out.print("Column: ");
		int col = Integer.parseInt(scanner.nextLine());
		return col;
	}

}