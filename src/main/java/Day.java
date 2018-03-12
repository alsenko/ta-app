
public class Day {
	boolean blockOne;
	boolean blockTwo;
	boolean blockThree;
	boolean blockFour;



public boolean checkBlock(int block){
		switch (block) {
		case 1: return this.blockOne;
			
		case 2: return this.blockTwo;
			
		case 3: return this.blockThree;
			
		case 4: return this.blockFour;
		}
		
		return false;	
}
public String toString() { 
    return blockOne + " " + blockTwo + " " + blockThree + " " + blockFour;
} 

}