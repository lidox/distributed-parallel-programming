package ba;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import ba.lee.Lee_sq;
import ba.lee.Lee_sq_dw;
import ba.lee.Lee_tm;
import ba.lee.Lee_tm_dw;

public class Main implements Konstant {

//	public static String fileName = "src/test/ba/sparseshort.txt";
	 public static String fileName = "src/test/ba/mainboard.txt";
	// public static String fileName = "src/test/ba/memboard.txt";

	private static int NUM_THREADS;
	private static BufferedReader file;
	private static String line;
	private static int location = 0;
	static int counter_Abort;
	static int counter_EOT;
	static int counter_BOT;
	static Viewer view = new Viewer();
	final  int cyan = 0x00FFFF;
	final  int magenta = 0xFF00FF;
	final  int green = 0x00FF00;
	static int TotalWork;


	public Main() {
		init();
	}

	public void init() {

		for (int i = 0; i < width; i++)
			for (int j = 0; j < length; j++) {
				GRID[i][j][0] = new Cell();
				GRID[i][j][1] = new Cell();

			}

		parseFile(fileName);
		addWeights(GRID);
	}

	public Thread createThread() {
//		 Thread tr = new Lee_sq();
//		 Thread tr = new Lee_sq_dw();
		Thread tr = new Lee_tm();
//		 Thread tr = new Lee_tm_dw();

		return tr;
	}

	public static synchronized boolean wrInGrid(Intlist r, int cn) {
		counter_BOT++;
		while (!r.isEmpty()) {
			int[] e = r.getElement();

			if (GRID[e[0]][e[1]][e[2]].val < OCC) {
				counter_Abort++;
				return false;
			}
		}
		r.reset();
		while (!r.isEmpty()) {
			int[] e = r.getElement();
			if (GRID[e[0]][e[1]][e[2]].val != OCC)
				GRID[e[0]][e[1]][e[2]].val = cn;
		}
		counter_EOT++;
		return true;
	}

	private static void nextLine() throws IOException {
		line = file.readLine();
		location = 0;
	}

	private static char readChar() {
		while ((line.charAt(location) == ' ')
				&& (line.charAt(location) == '\t'))
			location++;
		char c = line.charAt(location);
		if (location < line.length() - 1)
			location++;
		return c;
	}

	private static int readInt() {
		while ((line.charAt(location) == ' ')
				|| (line.charAt(location) == '\t'))
			location++;
		int fpos = location;
		while ((location < line.length())
				&& (line.charAt(location) != ' ')
				&& (line.charAt(location) != '\t'))
			location++;
		int n = Integer.parseInt(line.substring(fpos, location));
		return n;
	}

	public static void parseFile(String fileName) {
		int netNo = Marker;

		try {

			file = new BufferedReader(new InputStreamReader(
					new FileInputStream(fileName)));
			while (true) {
				nextLine();
				char c = readChar();
				if (c == 'E')
					break; // end of file

				if (c == 'T') // Anzahl der Threads
				{
					NUM_THREADS = readInt();
				}
				if (c == 'C') //  box
				{
					int x1 = readInt();
					int y1 = readInt();
					int x2 = readInt();
					int y2 = readInt();

					addChip(x1, y1, x2, y2);
				}
				if (c == 'P') // OCC
				{
					int x0 = readInt();
					int y0 = readInt();
					addChip(x0, y0, x0, y0);
				}
				if (c == 'J') // Connections
				{
					int x1 = readInt();
					int y1 = readInt();
					int x2 = readInt();
					int y2 = readInt();

					job.next = job.enQueue(x1, y1, x2, y2, netNo);
					netNo--;

				}
			}

			job.sort();
			TotalWork = Math.abs(netNo + 5);
		} catch (FileNotFoundException exception) {
			System.out.println("Cannot open file: " + fileName);
			System.exit(1);
		} catch (IOException exception) {
			System.out.println(exception);
			exception.printStackTrace();
		}

	}

	private static void addChip(int x1, int y1, int x2, int y2) {

		if (x1 <= x2) {
			for (int i = x1; i <= x2; i++) {

				if (y1 <= y2) {
					for (int j = y1; j <= y2; j++) {
						GRID[i][j][0].val = OCC;
						GRID[i][j][1].val = OCC;

					}
				}
				if (y2 < y1) {
					for (int j = y2; j <= y1; j++) {
						GRID[i][j][0].val = OCC;
						GRID[i][j][1].val = OCC;

					}
				}

			}
		}
		if (x2 < x1) {
			for (int i = x2; i <= x1; i++) {
				if (y1 <= y2) {
					for (int j = y1; j <= y2; j++) {
						GRID[i][j][0].val = OCC;
						GRID[i][j][1].val = OCC;

					}
				}
				if (y2 < y1) {
					for (int j = y2; j <= y1; j++) {
						GRID[i][j][0].val = OCC;
						GRID[i][j][1].val = OCC;

					}
				}

			}
		}

	}

	public static void addWeights(Cell[][][] g) {

		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < length - 1; y++) {
				for (int z = 0; z < height; z++)
					if (g[x][y][z].val == OCC) {
						if (g[x][y + 1][z].val == empty)
							g[x][y + 1][z].val = weight;
						if (g[x + 1][y][z].val == empty)
							g[x + 1][y][z].val = weight;
						if (g[x][y - 1][z].val == empty)
							g[x][y - 1][z].val = weight;
						if (g[x - 1][y][z].val == empty)
							g[x - 1][y][z].val = weight;
					} else if (g[x][y][z].val != empty) {
						if (g[x][y + 1][z].val == empty)
							g[x][y + 1][z].val = g[x][y][z].val - 1;
						if (g[x + 1][y][z].val == empty)
							g[x + 1][y][z].val = g[x][y][z].val - 1;
						if (g[x][y - 1][z].val == empty)
							g[x][y - 1][z].val = g[x][y][z].val - 1;
						if (g[x - 1][y][z].val == empty)
							g[x - 1][y][z].val = g[x][y][z].val - 1;

					}
			}
		}
	}

	public void dispGrid(Cell[][][] g, int z) {
		int layer_color;
		if (z == 0)
			layer_color = magenta;
		else
			layer_color = green;
		for (int y = width - 1; y >= 0; y--) {
			for (int x = 0; x < length; x++) {
				Cell cell = g[x][y][z];
				if (cell.val == OCC) {
					view.drawPoint(x, y, cyan);
					continue;
				}
				if (0 > cell.val) {
					view.drawPoint(x, y, layer_color);
					continue;
				}
			}
		}
	}

	public static void main(String[] args) {

		Main g = new Main();

		Thread[] threads = new Thread[NUM_THREADS];

		System.out.println("starting... ");
		System.out.println("--- Input : " + Main.fileName);
		System.out.println("--- Threads : " + NUM_THREADS);
		System.out.println("--- Connection : "
				+ Main.TotalWork);

		for (int i = 0; i < NUM_THREADS; i++) {
			threads[i] = g.createThread();
		}

		double start = System.currentTimeMillis();
		for (int i = 0; i < NUM_THREADS; i++) {
			threads[i].start();
		}

		for (int i = 0; i < NUM_THREADS; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		double stop = System.currentTimeMillis();

		System.out.println("---Klasse : "+ threads[0].getName());
		if (threads[0].getName().contains("sq")) {
			System.out.println("---Total : " + Main.counter_BOT);
			System.out.println("---Commited : " + Main.counter_EOT);
			System.out
					.println("---Aborted : " + Main.counter_Abort);
		} else {
			System.out.println("---Total : "
					+ org.deuce.transaction.lsa.Context.counter_BOT);
			System.out.println("---Commited : "
					+ org.deuce.transaction.lsa.Context.counter_EOT);
			System.out.println("---Aborted : "
					+ org.deuce.transaction.lsa.Context.counter_Abort);
		}

		System.out.println("------- - elapsed time : " + (stop - start)
				/ 1000.0 + " s - ----------------------");

		g.dispGrid(Main.GRID, 0);
		g.dispGrid(Main.GRID, 1);

		Verifier ver = new Verifier();
		ver.verifying();
		System.out.println(ver.Fhler
				+ " Routen koennten nicht gefunden werden !");
		view.writeImage();

	}

}
