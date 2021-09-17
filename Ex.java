package addressbook;


public class Ex extends Thread{
	
	
	public static void main(String[] args) throws InterruptedException {
		Ex autoEx = new Ex();
		autoEx.setDaemon(true);
		autoEx.start();
		
		int zero = 0;
		while(true) {
			zero += 1;
			sleep(500);
			if(zero < 7) { 
			
			for(int i=0; i<8; i++) {
				for(int k=0; k<15; k++) {
					if(i==5 && k>=zero && k<(zero+8)) {
						System.out.printf("%2c",'*');
					} else if(i==6 && k==(zero+1) || i==6 && k==(zero+6)) {
						System.out.printf("%2c",'*');
					} else if(i==7 && k>(zero+1) && k<(zero+6)) {
						System.out.printf("%2c",'*');
					} else if(k==(zero+3) && i>=0 && i<=4) {
						System.out.printf("%2c",'*');
					} else if(i==1 && k==(zero+4)) {
						System.out.printf("%2c",'*');
					} else if(i==2 && k==(zero+5)) {
						System.out.printf("%2c",'*');
					} else if(i==3 && k==(zero+4)) {
						System.out.printf("%2c",'*');
					} else if(i==7 && (k<=(zero+1) || k>=(zero+6))) {
						System.out.printf("%2c",'~');
					} else if(i==3 && k==(zero)) {
						System.out.printf("%2s","안");
					} else if(i==3 && k==(zero+1)) {
						System.out.printf("%2s","녕");
					} 
					else System.out.printf("%2c",' ');
				}//for
				System.out.println();
			}//for
				
			}
		}
	}
	
}
