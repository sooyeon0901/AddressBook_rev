package addressbook;

import java.awt.RenderingHints.Key;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;
import javax.swing.text.StyledEditorKit.ForegroundAction;
import javax.xml.stream.events.StartDocument;

import org.omg.DynamicAny._DynValueStub;
import org.w3c.dom.ls.LSOutput;


// 다하면 음악
public class AddressBookLogic {

	public static final int MAX_PERSON = 5;
	Map<Character,List<Person>> addressBook = new HashMap<>();
	List<Person> person;
	AutoSaveThread auto = new AutoSaveThread();//객체 생성 
	
	public class AutoSaveThread extends Thread{
		@Override
		public void run() {
			 while(true){
	             try {
	                 sleep(20000);
	                 //주소록있을 때
	                 //map에 list가 있는 유무로 판단 
	                 if(addressBook.size() != 0) {
	                	 savePerson();
	                	 System.out.println("[주소록 자동 저장 중! (계속 입력하세요)]");
	                 }
	             } catch (Exception e) {e.printStackTrace();}
	       }//while
		}//run
	}//AutoSaveThread
	

	public AddressBookLogic() {
		person = new Vector<Person>();
		addressBook = new HashMap<>();
		auto.setDaemon(true);//생성자에서 실행
		//auto.start();
		
	}
	
	void printMainMenu() {
		System.out.println("=================== 주소록 메뉴 ====================");
		System.out.println("   1.입력   2.출력   3.수정   4.삭제   5.검색   9.종료");
		System.out.println("=================================================");
		System.out.println("● 주소록 메뉴 번호를 입력하세요.");
	}//printMainMenu
	
	int getMenuNumber() {
		Scanner sc = new Scanner(System.in);
		String menuString;
		while(true) {
			menuString = sc.nextLine();
			if(!isNumber(menuString)) {
				System.out.println("메뉴 번호는 숫자만 입력하세요.");
				System.out.println("● 주소록 메뉴 번호를 입력하세요.");
				continue;
			} 
			break;
		}
		return Integer.parseInt(menuString);
	}//getMenuNumber
	
	public static boolean isNumber(String value) {
		for(int i=0;i < value.length();i++) {
			if(!(value.codePointAt(i) >='0' && value.codePointAt(i) <='9'))
				return false;
		}
		return true;
	}//isNumber
	
	void seperateMenu(int menuString) throws IOException {
		switch(menuString) {
			case 1: setPerson();
				break;
			case 2: printPerson();
				break;
			case 3: 
				while(true) {
					updateSubMenu();
					int subMenu = getMenuNumber();
					if(subMenu == 4) break;
					switch(subMenu) {
						case 1:
						case 2:
						case 3: updatePerson(subMenu); break;
							default: System.out.println("서브 메뉴에 없는 번호입니다");
					}
				}
				break;
			case 4: deletePerson();
				break;
			case 5: searchPerson();
				break;
			case 9:
				while(true) {
					exitSubMenu();
					int exitMenu = getMenuNumber();
					if(exitMenu == 3) break;
					switch(exitMenu) {
						case 1: 
							savePerson();
							System.out.println("[주소록이 안전하게 저장됐습니다. 프로그램을 종료하세요.]"); 
							star();
							break;
						case 2:
							System.out.println("프로그램을 종료합니다.");
							System.exit(0);
							star();
							break;
						default: System.out.println("서브 메뉴에 없는 번호입니다");
					}
				}
			default: System.out.println("없는 번호입니다. 다시 입력해 주세요.");
		}
	}//seperateMenu

	private void star() {
		for(int i=0; i<8; i++) {
			for(int k=0; k<15; k++) {
				if(i==5 && k<8) {
					System.out.printf("%2c",'*');
				} else if(i==6 && k==1 || i==6 && k==6) {
					System.out.printf("%2c",'*');
				} else if(i==7 && k>1 && k<6) {
					System.out.printf("%2c",'*');
				} else if(k==3 && i>=0 && i<=4) {
					System.out.printf("%2c",'*');
				} else if(i==1 && k==4) {
					System.out.printf("%2c",'*');
				} else if(i==2 && k==5) {
					System.out.printf("%2c",'*');
				} else if(i==3 && k==4) {
					System.out.printf("%2c",'*');
				} else if(i==7 && (k<=1 || k>=6)) {
					System.out.printf("%2c",'~');
				}
				else System.out.printf("%2c",' ');		
			}//for
			System.out.println();
		}//for
		
	}

	private void savePerson() throws IOException {
		Scanner sc = new Scanner(System.in);	
		
		if(addressBook.isEmpty()) {
			System.out.println("파일로 저장할 주소록이 없어요.");
			return;
		}
		
		PrintWriter pw = new PrintWriter(new FileWriter("src/addressbook/AddressBook.txt"), true);
		
		Set<Character> keys = addressBook.keySet();
		for(Character key:keys) {
			List<Person> values = addressBook.get(key);
				for(Person p:values) {
					pw.printf("[\'%c\'(으)로 시작하는 명단]%n\t%s%n",key,p.get());
				}
		}
		//System.out.println("[주소록이 안전하게 저장됐습니다.]");
		if(pw != null) pw.close();
		
	}//savePerson

	private void setPerson() {
		if(maxPerson() == MAX_PERSON ) {
			System.out.println("정원이 찼습니다.. 입력할 수 없습니다..");
			return;
		}
		setPersonInfo();
	}//setPerson
	
	private void setPersonInfo() {
		Scanner sc = new Scanner(System.in);	

		String name;
		char jaeum;
			
		while(true) {
			System.out.println("이름을 입력하세요.");
			name = sc.nextLine();
				
			jaeum = getFirstCharactor(name);
			if(jaeum == '0') {
				System.out.println("한글명이 아닙니다.");
			} else break;
		}//while
		
		System.out.println("나이를 입력하세요.");
		int age = Integer.parseInt(sc.nextLine());
		System.out.println("사는 곳을 입력하세요.");
		String addr = sc.nextLine();
		System.out.println("전화번호를 입력하세요.");
		String cellPhone = sc.nextLine();
		
		if(!addressBook.containsKey(jaeum)) {
			person = new Vector<>();
		} else {
			person = addressBook.get(jaeum);
		}
		person.add(new Person(name, age, addr, cellPhone));
		addressBook.put(jaeum, person);
		System.out.println("자음"+jaeum);
		System.out.println("person"+person);
		//System.out.println(addressBook.toString());
	}//setPersonInfo

	private int maxPerson() {
		int personSize=0;
		
		Set<Character> keys = addressBook.keySet();
		for(Character key:keys) {
			List<Person> values = addressBook.get(key);
			personSize += values.size();//각 value에 있는 값들을 personSize에 누적하고 출력
		}
		return personSize;
	}//maxPerson
	
	public char getFirstCharactor(String name) {
		char[] jaeum = name.trim().toCharArray();
		char[] startChar = {
				'가','나','다','라','마','바','사','아','자','차','카','타','파','하'};
		char[] endChar = {
				'낗','닣','띻','맇','밓','삫','앃','잏','찧','칳','킿','팋','핗','힣'};
		char[] returnJaeum = {
				'ㄱ','ㄴ','ㄷ','ㄹ','ㅁ','ㅂ','ㅅ','ㅇ','ㅈ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'};
		for(int i=0; i<startChar.length; i++) {
			if(jaeum[0] >= startChar[i] && jaeum[0] <= endChar[i]) {
				return returnJaeum[i];
			}//if
		}//for
		return '0';
		
	}//getFirstCharactor

	private void printPerson() {
		System.out.println("[주소록 출력]");
		Set<Character> keys = addressBook.keySet();
			for(Character key:keys) {
				System.out.println(String.format("[\'%c\'(으)로 시작하는 명단]",key));
				List<Person> values = addressBook.get(key);
				
				Collections.sort(values, new Comparator<Person>() {

					@Override
					public int compare(Person o1, Person o2) {
						return o1.name.compareTo(o2.name);
					}
				});
				
				for(Person p:values) {
					System.out.println(p.get());
				}
			}//for
		
	}//printPerson
	
	private Person findPersonByName(String title) {
		System.out.println(title+"할 사람의 \'이름\'을 입력하세요.");
		Scanner sc = new Scanner(System.in);
		String name = sc.nextLine().trim();
		
		Set<Character> keys = addressBook.keySet();
		for(Character key:keys) {
			List<Person> values = addressBook.get(key);
			
			for(Person p:values) {
				if(p.name.equals(name)) {
					return p;
				}
			}
		}
		System.out.println("\'"+name+"\'(으)로 검색된 정보가 없습니다.");
		return null;
	}//findPersonByName

	private void searchPerson() {
		Person findPerson = findPersonByName("검색");
		if(findPerson != null) {
			System.out.printf("[%s(으)로 검색한 결과]%n", findPerson.name);
			findPerson.print();
		}
		
	}//searchPerson

	private void updatePerson(int subMenu) {
		Person findPerson = findPersonByName("수정");
		
		Scanner sc = new Scanner(System.in);
		switch(subMenu) {
			case 1: 
				System.out.println("수정할 \'나이\'를 입력하세요.");
				findPerson.age = Integer.parseInt(sc.nextLine().trim());
				System.out.printf("나이가 [%s](으)로 수정되었습니다.%n",findPerson.age);
				break;
			case 2:
				System.out.println("수정할 \'주소\'를 입력하세요.");
				findPerson.addr = sc.nextLine();
				System.out.printf("주소가 [%s](으)로 수정되었습니다.%n",findPerson.addr);
				break;
			case 3:
				System.out.println("수정할 \'연락처\'를 입력하세요.");
				findPerson.cellPhone = sc.nextLine();
				System.out.printf("연락처가 [%s](으)로 수정되었습니다.%n",findPerson.cellPhone);
				break;
		}
		
	}//updatePerson
	
	private void updateSubMenu() {		
		System.out.println("-------------- <무엇을 수정하시겠습니까?> --------------");
		System.out.println("	1.나이  2.주소  3.연락처  4.메인 메뉴로 이동");
		System.out.println("-------------------------------------------------");
		System.out.println("● 서브 메뉴번호를 입력하세요.");
	}
	
	private void deletePerson() {
		Person findPerson = findPersonByName("삭제");
//		if(findPerson != null) {
//			Set<Character> keys = addressBook.keySet();
//			for(Character key:keys) {
//				List<Person> values = addressBook.get(key);
//					for(int i=0; i<values.size();i++) {
//						if(values.get(i) == findPerson) {
//						
//							values.remove(findPerson);
//							System.out.printf("[\'%s\'(이)가 삭제되었습니다.]%n",findPerson.name);
//		//					break;
////						System.out.println("제일안쪽키즈"+keys);
////						System.out.println("제일안쪽키"+key);
////						System.out.println("제일안쪽벨유스"+values);
//						}
//					}
//					if(values.size()==0) keys.remove(key);
//		//			break;
//			}
				
		
	
		//김,강,나 순으로 입력 후 나를 삭제하려고 하면 안뜨는데 다시 순서대로 김 삭제하려고하면 강, 나도 삭제가 됨 - 브레이크....
		if(findPerson != null) {
			Set<Character> keys = addressBook.keySet();
			for(Character key:keys) {
				List<Person> values = addressBook.get(key);
					
				for(Person p:values) {
					if(findPerson.equals(p)) {
						values.remove(p);
						System.out.printf("[\'%s\'(이)가 삭제되었습니다.]%n",findPerson.name);
						break;
					}
				}
				if(values.size()==0) keys.remove(key);
			//	break;
			}
			
		}
		
		

	}//deletePerson
	
	private void exitSubMenu() {		
		System.out.println("-------------------------------------------------");
		System.out.println("	  1.저장   2.종료   3.메인 메뉴로 이동");
		System.out.println("-------------------------------------------------");
		System.out.println("● 서브 메뉴번호를 입력하세요.");
	}//exitSubMenu
}//AddressBookLogic
