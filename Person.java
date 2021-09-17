package addressbook;

public class Person implements Comparable{
	
	static int sortOrder = 1;
	
	public String name;
	public int age;
	public String addr;
	public String cellPhone;
	
	public Person(){}

	public Person(String name, int age, String addr, String cellPhone) {
		this.name = name;
		this.age = age;
		this.addr = addr;
		this.cellPhone = cellPhone;
	}
	
	String get() {
		return String.format("이름:%s, 나이:%s, 주소:%s, 연락처:%s", name,age,addr,cellPhone);
	}
	
	public void print() {
		System.out.println(get());
	}

	@Override
	public String toString() {
		return get();
	}

	public int compareTo(Person target) {
		switch(sortOrder) {		
		case 1:
			return name.compareTo(target.name);
		case 2:
			return age-target.age;
		case 3:
			return addr.compareTo(target.addr);
		default:	
			return cellPhone.compareTo(target.cellPhone);
		}	
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}//Person
