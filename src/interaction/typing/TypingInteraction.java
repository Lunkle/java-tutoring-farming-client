package interaction.typing;

import java.util.Scanner;

import event.ctsevent.CTSEvent;
import event.stcevent.STCEvent;
import interaction.FarmingInteraction;
import network.NetworkingBuffers;

public class TypingInteraction extends FarmingInteraction {

	public TypingInteraction(NetworkingBuffers buffers) {
		super(buffers);
	}

	@Override
	public void begin() {
		Scanner scanner = new Scanner(System.in);
		boolean quit = false;
		while (!quit) {
			quit = handleInputCommand(scanner, quit);
		}
		scanner.close();
	}

	private boolean handleInputCommand(Scanner scanner, boolean quit) {
		System.out.print("Input a command ('help' for options): ");
		String input = scanner.nextLine();
		if (input.equals("help")) {
			handleHelp();
		} else if (input.equals("check") || input.equals("c")) {
			handleCheckMessage();
		} else if (input.equals("read") || input.equals("r")) {
			handleReadMessage();
		} else {
			boolean isGibberish = false;
			CTSEvent event = null;
			if (input.equals("login") || input.equals("lg")) {
				event = InputHandler.handleSendLogin(scanner);
			} else if (input.equals("quit") || input.equals("q")) {
				event = InputHandler.handleQuit();
				quit = true;
			} else if (input.equals("save") || input.equals("s")) {
				event = InputHandler.handleSave();
			} else if (input.equals("inventory") || input.equals("i")) {
				event = InputHandler.handleInventoryOverview();
			} else if (input.equals("basicreport") || input.equals("brp")) {
				event = InputHandler.handleBasicReport(scanner);
			} else if (input.equals("advancedreport") || input.equals("arp")) {
				event = InputHandler.handleAdvancedReport(scanner);
			} else if (input.equals("harvest") || input.equals("h")) {
				event = InputHandler.handleHarvest(scanner);
			} else if (input.equals("sowseed") || input.equals("ss")) {
				event = InputHandler.handleSowSeed(scanner);
			} else if (input.equals("farmland") || input.equals("fl")) {
				event = InputHandler.handleFarmLandOverview(scanner);
			} else if (input.equals("flplant") || input.equals("flp")) {
				event = InputHandler.handlePlantOverview(scanner);
			} else if (input.equals("flterrain") || input.equals("flt")) {
				event = InputHandler.handleTerrainOverview(scanner);
			} else if (input.equals("shop") || input.equals("sp")) {
				event = InputHandler.handleShopOverview(scanner);
			} else if (input.equals("shopsell") || input.equals("sps")) {
				event = InputHandler.handleShopSell(scanner);
			} else if (input.equals("shopinspect") || input.equals("spi")) {
				event = InputHandler.handleShopInspect(scanner);
			} else if (input.equals("shopcancel") || input.equals("spx")) {
				event = InputHandler.handleShopCancel(scanner);
			} else if (input.equals("shopcollect") || input.equals("spc")) {
				event = InputHandler.handleShopCollect(scanner);
			} else {
				System.out.println("[Client]: Command not recognized");
				isGibberish = true;
			}
			if (!isGibberish) {
				sendEvent(event);
				System.out.println("[Client]: " + event.getDescription());
			}
		}
		return quit;

	}

	private void handleHelp() {
		String string = "\nOptions:\n"
				+ "\t('help') for options\n"
				+ "\t('quit'/'q') to quit\n"
				+ "\t('save'/'s') to save\n"
				+ "\t('check'/'c') to check messages\n"
				+ "\t('read'/'r') to read next message\n"
				+ "\t('login'/'lg') to login\n"
				+ "\t('inventory'/'i') to see your inventory\n"
				+ "\t('report'/'brp') to see a basic report\n"
				+ "\t('report'/'arp') to see an advanced report\n"
				+ "\t('sowseed'/'ss') to sow a seed\n"
				+ "\t('harvest'/'h') to harvest a plant\n"
				+ "\t('farmland'/'fl') to see your farmland\n"
				+ "\t('flplant'/'flp') to inspect a plant\n"
				+ "\t('flterrain'/'flt') to inspect the terrain\n"
				+ "\t('shop'/'sp') to see your shop\n"
				+ "\t('shopsell'/'s') to sell some items\n"
				+ "\t('shopinspect'/'spi') to inspect a sale\n"
				+ "\t('shopcancel'/'spx') to cancel a sale\n"
				+ "\t('shopcollect'/'spc') to collect a completed sale\n";
		System.out.println(string);
	}

	private void handleReadMessage() {
		STCEvent readEvent = readEvent();
		if (readEvent == null) {
			System.out.println("[Client]: You tried to read a message when there were none");
		} else {
			System.out.println("[Server]: " + readEvent.getDescription());
		}
	}

	private void handleCheckMessage() {
		if (hasUnreadEvents()) {
			System.out.println("[Client]: You received a message from the server");
		} else {
			System.out.println("[Client]: You have no messages from the server");
		}
	}

}
