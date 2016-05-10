package ba;



public class Dataqueue {

public int x1,y1,x2,y2,netN;
public Dataqueue next;

    Dataqueue(){
	next = null;
    }

    Dataqueue(int x1,int y1,int x2,int y2,int n){
	this.x1 = x1;
	this.y1 = y1;
	this.x2 = x2;
	this.y2 = y2;
	this.netN = n;
    }

    public  Dataqueue enQueue(int x1,int y1,int x2,int y2,int n){
	Dataqueue q = new Dataqueue(x1,y1,x2,y2,n);
	q.next = this.next;
	return q;
    }

    public synchronized Dataqueue deQueue(){
        Dataqueue q = this.next;
	this.next = this.next.next;
	return q;
    }

    public boolean isLongerAs(int X1,int Y1,int X2,int Y2){
      return (x2-x1)*(x2-x1)+(y2-y1)*(y2-y1) > (X2-X1)*(X2-X1)+(Y2-Y1)*(Y2-Y1);
      // nach dem Satz des Pythagoras, im rechtwinkligen Dreiecks 
    }

    public boolean compare(){
	boolean end = true;
        Dataqueue ent = this;
        Dataqueue a = ent.next;
	while (a.next != null){ 
	    Dataqueue b = a.next;
	    if (a.isLongerAs(b.x1,b.y1,b.x2,b.y2)){ // ist laenger werden die vertauscht.
		ent.next = b;
		a.next = b.next;
		b.next = a;
		end = false;
	    }
	    ent = a;
	    a = b;
	    b = b.next;
	}
	return end;
    }

    public void sort(){
	while (!compare());
    }

}
