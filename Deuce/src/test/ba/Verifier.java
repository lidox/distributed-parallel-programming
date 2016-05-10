package ba;






public class Verifier implements Konstant{
	private Cell[][][] Matrix=new Cell[width][length][height];
	public int Fhler=0;

	public Verifier() {
		for (int i = 0; i < Main.width; i++) {
			for (int j = 0; j < Main.length; j++) {
				Matrix[i][j][0] = new Cell();
				Matrix[i][j][1] = new Cell();
				Matrix[i][j][0].val = GRID[i][j][0].val;
				Matrix[i][j][1].val = GRID[i][j][1].val;

			}
		}
	}

	boolean verify(int x1, int y1, int x2, int y2, int cn) {

		int x = x1, y = y1;

		if (Matrix[x1][y1][0].val != cn && Matrix[x1][y1][0].val != OCC
				&& Matrix[x1][y1][1].val != cn && Matrix[x1][y1][1].val != OCC) {
			return false;

		}

		int layer0 = 0, layer1 = 1;
		boolean br;
		while (true) {

			br = true;

			if (x + 1 < Main.width) {
				if (Matrix[x + 1][y][layer0].val == OCC
						|| Matrix[x + 1][y][layer1].val == OCC) {
					if ((x + 1) == x2 && y == y2)
						return true;

				}
				if (Matrix[x + 1][y][layer0].val == cn
						|| Matrix[x + 1][y][layer1].val == cn) {
					if (Matrix[x][y][layer0].val ==cn)
						Matrix[x][y][layer0].val = Main.empty;
					if (Matrix[x][y][layer1].val == cn)
						Matrix[x][y][layer1].val = Main.empty;
					x = x + 1;
					if (x == x2 && y == y2)
						return true;
					br = false;
				}

			}
			if (0 <= x - 1) {

				if (Matrix[x - 1][y][layer0] .val== OCC
						|| Matrix[x - 1][y][layer1] .val== OCC) {
					if ((x - 1) == x2 && y == y2)
						return true;
				}
				if (Matrix[x - 1][y][layer0] .val== cn
						|| Matrix[x - 1][y][layer1] .val== cn) {
					if (Matrix[x][y][layer0] .val== cn)
						Matrix[x][y][layer0] .val=  Main.empty;
					if (Matrix[x][y][layer1] .val== cn)
						Matrix[x][y][layer1] .val=  Main.empty;
					x = x - 1;
					if (x == x2 && y == y2)
						return true;
					br = false;
				}
			}
			if (0 <= y - 1) {
				if (Matrix[x][y - 1][layer0] .val== OCC
						|| Matrix[x][y - 1][layer1] .val== OCC) {
					if (x == x2 && (y - 1) == y2)
						return true;
				}
				if (Matrix[x][y - 1][layer0] .val== cn
						|| Matrix[x][y - 1][layer1] .val== cn) {
					if (Matrix[x][y][layer0] .val== cn)
						Matrix[x][y][layer0] .val=  Main.empty;
					if (Matrix[x][y][layer1] .val== cn)
						Matrix[x][y][layer1] .val=  Main.empty;
					y = y - 1;
					if (x == x2 && y == y2)
						return true;
					br = false;
				}

			}
			if (y + 1 < Main.length) {

				if (Matrix[x][y + 1][layer0] .val== OCC
						|| Matrix[x][y + 1][layer1] .val== OCC) {
					if (x == x2 && (y + 1) == y2)
						return true;
				}
				if (Matrix[x][y + 1][layer0] .val== cn
						|| Matrix[x][y + 1][layer1] .val== cn) {
					if (Matrix[x][y][layer0] .val== cn)
						Matrix[x][y][layer0] .val=  Main.empty;
					if (Matrix[x][y][layer1] .val== cn)
						Matrix[x][y][layer1] .val=  Main.empty;
					y = y + 1;
					if (x == x2 && y == y2)
						return true;
					br = false;

				}

			}

			 	
			if (br) {

				return false;
			}
		}

	}

	


	public void verifying() {

		System.out.println("Starting verifying ... ");
		Main.parseFile(Main.fileName);
		while (job.next != null) {
			Dataqueue el = job.deQueue();
			 
			if (verify(el.x1, el.y1, el.x2, el.y2, el.netN))
				continue;

			Fhler++;


		}
		System.out.println("Ende verifying ! ");

	}
	

}