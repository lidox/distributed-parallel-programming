package ba;



public interface Konstant {

	static final int width = 600, length = 600, height = 2;
	final int OCC = -1;
	final int empty = 0, weight = 1;
	final int start_val = 5, tgtId = -1, srcId = 1;
	static final int Marker = -5;
	final Cell[][][] GRID = new Cell[width][length][height];
	final Dataqueue job = new Dataqueue();

}
