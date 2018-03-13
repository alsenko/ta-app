
public class Day {
	private boolean blockOne;
	private boolean blockTwo;
	private boolean blockThree;
	private boolean blockFour;



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
public boolean isBlockOne() {
	return blockOne;
}
public void setBlockOne(boolean blockOne) {
	this.blockOne = blockOne;
}
public boolean isBlockTwo() {
	return blockTwo;
}
public void setBlockTwo(boolean blockTwo) {
	this.blockTwo = blockTwo;
}
public boolean isBlockThree() {
	return blockThree;
}
public void setBlockThree(boolean blockThree) {
	this.blockThree = blockThree;
}
public boolean isBlockFour() {
	return blockFour;
}
public void setBlockFour(boolean blockFour) {
	this.blockFour = blockFour;
} 

}