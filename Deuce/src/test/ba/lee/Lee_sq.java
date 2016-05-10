package ba.lee;

import ba.Cell;
import ba.Dataqueue;
import ba.Intlist;
import ba.Konstant;
import ba.Main;

public class Lee_sq extends Thread implements Konstant {
	private final Cell[][][] temp = new Cell[width][length][height];
	private int x1, x2, y1, y2;
	private int cn;

	public Lee_sq() {
		setName("Lee_sq");
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {
				temp[i][j][0] = new Cell();
				temp[i][j][1] = new Cell();

			}
		}
	}

	 public void copyGrid() {

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < length; j++) {
				temp[i][j][0].val = GRID[i][j][0].val;
				temp[i][j][1].val = GRID[i][j][1].val;

			}
		}
	}

	private boolean inSize(int x, int y, int z) {

		if (x < 0 || x >= width)
			return false;
		if (y < 0 || y >= length)
			return false;
		if (z < 0 || z >= height)
			return false;

		return true;

	}

	private boolean nextConn() {
		if (job.next != null) {
			Dataqueue work = job.deQueue();
			x1 = work.x1;
			y1 = work.y1;
			x2 = work.x2;
			y2 = work.y2;
			cn = work.netN;
			return true;
		} else
			return false;
	}

	public void run() {
		while (nextConn()) {
			copyGrid();
			LEE();
		}
	}

	private void LEE() {
		Intlist r = new Intlist();
		int[] meet = expansion();
		if (meet != null) {
			if (backtracking(meet, r))
				if (!Main.wrInGrid(r, cn)) {
					copyGrid();
					LEE();
				}
		}
	}

	public int[] expansion() {

		temp[x1][y1][0].val = start_val;
		Intlist li = new Intlist();
		li.addElement(x1, y1, 0);

		while (!li.isEmpty()) {

			int[] el = li.getElement();
			int x = el[0];
			int y = el[1];
			int z = el[2];
			// vorne
			if (inSize(x, y + 1, z)) {
				if (empty == temp[x][y + 1][z].val
						|| temp[x][y + 1][z].val == weight) {
					temp[x][y + 1][z].val += temp[x][y][z].val + 1;
					li.addElement(x, y + 1, z);
				}
				if (x == x2 && y + 1 == y2)
					return new int[] { x, y + 1, z };

			}
			// rechts
			if (inSize(x + 1, y, z)) {
				if (empty == temp[x + 1][y][z].val
						|| temp[x + 1][y][z].val == weight) {
					temp[x + 1][y][z].val += temp[x][y][z].val + 1;
					li.addElement(x + 1, y, z);

				}
				if (x + 1 == x2 && y == y2)
					return new int[] { x + 1, y, z };
			}
			// links
			if (inSize(x - 1, y, z)) {
				if (empty == temp[x - 1][y][z].val
						|| temp[x - 1][y][z].val == weight) {
					temp[x - 1][y][z].val += temp[x][y][z].val + 1;
					li.addElement(x - 1, y, z);

				}
				if (x - 1 == x2 && y == y2)
					return new int[] { x - 1, y, z };
			}

			// hinten
			if (inSize(x, y - 1, z)) {

				if (empty == temp[x][y - 1][z].val
						|| temp[x][y - 1][z].val == weight) {
					temp[x][y - 1][z].val += temp[x][y][z].val + 1;
					li.addElement(x, y - 1, z);

				}
				if (x == x2 && y - 1 == y2)
					return new int[] { x, y - 1, z };
			}
			// oben
			if (inSize(x, y, 0)) {

				if (empty == temp[x][y][0].val || temp[x][y][0].val == weight) {
					temp[x][y][0].val += temp[x][y][z].val + 1;
					li.addElement(x, y, 0);

				}
				if (x == x2 && y == y2)
					return new int[] { x, y, 0 };
			}
			// unten
			if (inSize(x, y, 1)) {

				if (empty == temp[x][y][1].val || temp[x][y][1].val == weight) {
					temp[x][y][1].val += temp[x][y][z].val + 1;
					li.addElement(x, y, 1);

				}
				if (x == x2 && y == y2)
					return new int[] { x, y, 1 };
			}
		}
		return null;
	}

	private boolean backtracking(int[] meet, Intlist r) {
		int x = meet[0], y = meet[1], z = meet[2];
		r.addElement(x, y, z);

		int min, xx, yy, zz;
		while (true) {
			xx = yy = zz = 0;
			min = 60000;

			// vorne
			if (inSize(x, y + 1, z) && weight < temp[x][y + 1][z].val) {
				if (temp[x][y + 1][z].val < min) {
					min = temp[x][y + 1][z].val;
					yy = 1;
					xx = 0;
					zz = 0;

				}
			}
			// hinten
			if (inSize(x, y - 1, z) && weight < temp[x][y - 1][z].val) {
				if (temp[x][y - 1][z].val < min) {
					min = temp[x][y - 1][z].val;
					yy = -1;
					xx = 0;
					zz = 0;

				}
			}
			// link
			if (inSize(x - 1, y, z) && weight < temp[x - 1][y][z].val) {
				if (temp[x - 1][y][z].val < min) {
					min = temp[x - 1][y][z].val;
					xx = -1;
					yy = 0;
					zz = 0;
				}
			}
			// recht
			if (inSize(x + 1, y, z) && weight < temp[x + 1][y][z].val) {
				if (temp[x + 1][y][z].val < min) {
					min = temp[x + 1][y][z].val;
					xx = 1;
					yy = 0;
					zz = 0;

				}
			}
			// oben
			if (inSize(x, y, z + 1) && weight < temp[x][y][z + 1].val) {
				if (temp[x][y][z + 1].val < min) {
					min = temp[x][y][z + 1].val;
					xx = 0;
					yy = 0;
					zz = 1;

				}
			}

			// unten
			if (inSize(x, y, z - 1) && weight < temp[x][y][z - 1].val) {
				if (temp[x][y][z - 1].val < min) {
					min = temp[x][y][z - 1].val;
					xx = 0;
					yy = 0;
					zz = -1;

				}
			}

			x += xx;
			y += yy;
			z += zz;
			r.addElement(x, y, z);
			if (x == x1 && y == y1)
				return true;

		}

	}

	

}
