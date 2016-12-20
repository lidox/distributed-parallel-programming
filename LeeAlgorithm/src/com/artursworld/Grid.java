package com.artursworld;


import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Initial ist grind mit '-1' Werten belegt und hat feste quadratische Größe.
 * Hat zwei Konstrukturen. Ein feingranularer (mit Subgrid) oder Grobgranular (ohne Subgrid)
 */
public class Grid {

    volatile AtomicBoolean finished = new AtomicBoolean(false);

    private int gridSize;
    private int subGridSize;

    private Cell[][] cells;
    private SubGrid[][] subGrids;

    // Grobgranular
    public Grid(int gridSize) {
        this.gridSize = gridSize;
        this.subGridSize = gridSize;

        initCells();
        initSubGrid();
    }

    // "Feingranular"
    public Grid(int gridSize, int subGridSize) {
        this.gridSize = gridSize;
        if (gridSize % subGridSize == 0)
            this.subGridSize = subGridSize;
        else
            this.subGridSize = gridSize;

        initCells();
        initSubGrid();
    }

    private void initCells() {
        cells = new Cell[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++)
            for (int j = 0; j < gridSize; j++)
                cells[i][j] = new Cell(i, j);
    }

    private void initSubGrid() {
        int numOfSubGrids = gridSize / subGridSize;
        subGrids = new SubGrid[numOfSubGrids][numOfSubGrids];
        for (int i = 0; i < numOfSubGrids; i++)
            for (int j = 0; j < numOfSubGrids; j++)
                subGrids[i][j] = new SubGrid();
    }

    public int calcPath(int xSrc, int ySrc, int xDest, int yDest) {
        if (!(xSrc >= 0 && xSrc < gridSize && ySrc >= 0 && ySrc < gridSize
                && xDest >= 0 && xDest < gridSize && yDest >= 0 && yDest < gridSize)) {
            System.err.println("Mindestens einer der Punkte (" + xSrc + "," + ySrc + ") und (" + xDest + "," + yDest
                    + ") liegen nicht auf dem Grid.");
            System.exit(1);
        }

        // expansion phase (multithreaded)
        cells[xSrc][ySrc].setValue(0);
        cells[xSrc][ySrc].setVisited(true);

        CyclicBarrier slaveBarrier = new CyclicBarrier(4);
        CyclicBarrier slaveBarrier2 = new CyclicBarrier(4);
        SlaveThread[] slaveThread = new SlaveThread[4];
        for (int i = 0; i < 4; i++) {
            int x, y;
            if (i == 0) {
                x = xSrc - 1;
                y = ySrc;
            } else if (i == 1) {
                x = xSrc + 1;
                y = ySrc;
            } else if (i == 2) {
                x = xSrc;
                y = ySrc - 1;
            } else {
                x = xSrc;
                y = ySrc + 1;
            }
            slaveThread[i] =
                    new SlaveThread(finished, cells, subGrids, subGridSize, slaveBarrier, slaveBarrier2, x, y, xDest,
                            yDest);
            slaveThread[i].start();
        }

        for (int i = 0; i < 4; i++) {
            try {
                slaveThread[i].join();
            } catch (InterruptedException e) {
            }
        }

        // backtrackphase (singlethreaded)
        return traceBack(xSrc, ySrc, xDest, yDest);
    }

    private int traceBack(int xSrc, int ySrc, int xDest, int yDest) {
        int x = xDest;
        int y = yDest;
        int cost = 0;

        int cLeft = -1, cRight = -1, cUp = -1, cDown = -1;
        while (x != xSrc || y != ySrc) {
            if (x - 1 >= 0)
                cLeft = cells[x - 1][y].getValue();
            if (x + 1 < gridSize)
                cRight = cells[x + 1][y].getValue();
            if (y - 1 >= 0)
                cUp = cells[x][y - 1].getValue();
            if (y + 1 < gridSize)
                cDown = cells[x][y + 1].getValue();

            if (cells[x][y].getValue() - 1 == cRight) {
                cost += 1;
                x += 1;
            } else if (cells[x][y].getValue() - 1 == cLeft) {
                cost += 1;
                x -= 1;
            } else if (cells[x][y].getValue() - 1 == cDown) {
                cost += 1;
                y += 1;
            } else if (cells[x][y].getValue() - 1 == cUp) {
                cost += 1;
                y -= 1;
            }
        }

        return cost;
    }

    private void printGrid() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++)
                System.out.print(cells[i][j].getValue() + "\t");
            System.out.println();
        }
    }

    public void reset() {
        finished.set(false);

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                cells[i][j].setVisited(false);
                cells[i][j].setValue(-1);
            }
        }
    }
}
