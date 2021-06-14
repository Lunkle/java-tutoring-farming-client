package interaction.typing;

import java.util.Scanner;

import event.ctsevent.CTSEvent;
import event.ctsevent.game.AdvancedReportPurchaseRequest;
import event.ctsevent.game.BasicReportPurchaseRequest;
import event.ctsevent.game.FarmLandOverviewRequest;
import event.ctsevent.game.HarvestLandSlotRequest;
import event.ctsevent.game.InventoryOverviewRequest;
import event.ctsevent.game.LandSlotPlantOverviewRequest;
import event.ctsevent.game.LandSlotTerrainOverviewRequest;
import event.ctsevent.game.ShopCancelRequest;
import event.ctsevent.game.ShopCollectRequest;
import event.ctsevent.game.ShopOverviewRequest;
import event.ctsevent.game.ShopSellRequest;
import event.ctsevent.game.SowSeedRequest;
import event.ctsevent.session.CloseSessionRequest;
import event.ctsevent.session.LoginRequest;
import event.ctsevent.session.SaveRequest;

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

	public static CTSEvent handleSowSeed(Scanner scanner) {
		int x = readX(scanner);
		int y = readY(scanner);
		int row = readRow(scanner);
		int col = readCol(scanner);
		System.out.print("Seed name: ");
		String item = scanner.nextLine();
		CTSEvent event = new SowSeedRequest(0, x, y, row, col, item);
		return event;
	}

	public static CTSEvent handleInventoryOverview() {
		CTSEvent event = new InventoryOverviewRequest(0);
		return event;
	}

	public static CTSEvent handleHarvest(Scanner scanner) {
		int x = readX(scanner);
		int y = readY(scanner);
		int row = readRow(scanner);
		int col = readCol(scanner);
		CTSEvent event = new HarvestLandSlotRequest(0, x, y, row, col);
		return event;
	}

	public static CTSEvent handleSave() {
		CTSEvent event = new SaveRequest(0);
		return event;
	}

	public static CTSEvent handleBasicReport(Scanner scanner) {
		System.out.print("Year: ");
		int year = Integer.parseInt(scanner.nextLine());
		System.out.print("Month: ");
		int month = Integer.parseInt(scanner.nextLine());
		System.out.print("Date: ");
		int date = Integer.parseInt(scanner.nextLine());
		CTSEvent event = new BasicReportPurchaseRequest(0, year, month, date);
		return event;
	}

	public static CTSEvent handleAdvancedReport(Scanner scanner) {
		System.out.print("Year: ");
		int year = Integer.parseInt(scanner.nextLine());
		System.out.print("Month: ");
		int month = Integer.parseInt(scanner.nextLine());
		System.out.print("Date: ");
		int date = Integer.parseInt(scanner.nextLine());
		CTSEvent event = new AdvancedReportPurchaseRequest(0, year, month, date);// year, month, date, type);
		return event;
	}

	public static CTSEvent handleShopOverview(Scanner scanner) {
		CTSEvent event = new ShopOverviewRequest(0);
		return event;
	}

	public static CTSEvent handleShopSell(Scanner scanner) {
		int row = readRow(scanner);
		int col = readCol(scanner);
		System.out.print("Item name: ");
		String item = scanner.nextLine();
		System.out.print("Amount: ");
		int amount = Integer.parseInt(scanner.nextLine());
		CTSEvent event = new ShopSellRequest(0, row, col, item, amount);
		return event;
	}

	public static CTSEvent handleShopCancel(Scanner scanner) {
		int row = readRow(scanner);
		int col = readCol(scanner);
		CTSEvent event = new ShopCancelRequest(0, row, col);
		return event;
	}

	public static CTSEvent handleShopCollect(Scanner scanner) {
		int row = readRow(scanner);
		int col = readCol(scanner);
		CTSEvent event = new ShopCollectRequest(0, row, col);
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
		System.out.print("Farm Land X: ");
		int x = Integer.parseInt(scanner.nextLine());
		return x;
//		return 0;
	}

	private static int readY(Scanner scanner) {
		System.out.print("Farm Land Y: ");
		int y = Integer.parseInt(scanner.nextLine());
		return y;
//		return 0;
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
