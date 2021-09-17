package addressbook;

public class Star {

	public static void main(String[] args) {
		
		
			int zero = 0;
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

}
