package ba;

public class Intlist {

	final private int[][] LIST;
    private int vor,back;
	public Intlist(){
		LIST= new int[6000][3];
		vor=back=0;
	}

	public void addElement(int x,int y, int z){
		
		LIST[vor][0]=x;
		LIST[vor][1]=y;
		LIST[vor][2]=z;
		if(vor==5999)vor=0;
        vor++;
	}
	
	
public int[] getElement(){
		
	int[]el=new int[3];
	el[0]=LIST[back][0];
	el[1]=LIST[back][1];
	el[2]=LIST[back][2];
	if(back==5999)back=0;
	back++;
		return el;
		

	}

public void reset(){
	this.back=0;	
}
public void increment(int num){
	this.back+=num;	
//System.err.println("b " +back);
}

public boolean isEmpty(){
	return(back==vor);
	
}
}
