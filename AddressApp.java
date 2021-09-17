package addressbook;

import java.io.IOException;

public class AddressApp {

	public static void main(String[] args) throws IOException {
		AddressBookLogic logic = new AddressBookLogic();
		
		while(true) {
			logic.printMainMenu();
			int menuString = logic.getMenuNumber();
			logic.seperateMenu(menuString);
			
		}//while
	}//main
}//AddressApp
