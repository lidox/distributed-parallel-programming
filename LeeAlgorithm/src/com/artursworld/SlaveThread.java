package com.artursworld;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;


public class SlaveThread extends Thread {

    private CyclicBarrier slaveBarrier;
    private CyclicBarrier slaveBarrier2;

    private Cell[][] cells;
    private SubGrid[][] subGrids;

    private Set<Cell> visitedCells;

    private int subGridSize, gridSize, xStart, yStart, xDest, yDest, value = 1;

    private int oldSubX = -1, oldSubY = -1;
    private long lockStamp;

    private AtomicBoolean finished;

    public SlaveThread(AtomicBoolean finished, Cell[][] cells, SubGrid[][] subGrids, int subGridSize,
                       CyclicBarrier slaveBarrier, CyclicBarrier slaveBarrier2, int x, int y, int xDest, int yDest) {
        this.finished = finished;

        this.cells = cells;
        this.subGrids = subGrids;

        this.slaveBarrier = slaveBarrier;
        this.slaveBarrier2 = slaveBarrier2;
        this.visitedCells = new HashSet<>();

        this.xStart = x;
        this.yStart = y;

        this.xDest = xDest;
        this.yDest = yDest;
        this.subGridSize = subGridSize;
        this.gridSize = cells.length;
    }

    public void run() {
        assignCellWithValue(xStart, yStart);
        while (true) {
            try {
                value++;
                updateNeighborList();
                if (oldSubX != -1 && oldSubY != -1) {
                    subGrids[oldSubX][oldSubY].unlock(lockStamp);
                    oldSubX = -1;
                    oldSubY = -1;
                }

                slaveBarrier.await();

                if (finished.get()) {
                    break;
                }

                slaveBarrier2.await();

            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateNeighborList() {
        Set<Cell> nextNeighbors = new HashSet<>();
        nextNeighbors.addAll(visitedCells);
        visitedCells.clear();

        int x, y;

        for (Cell c : nextNeighbors) {
            x = c.getX();
            y = c.getY();

            if (x - 1 >= 0) {
                assignCellWithValue(x - 1, y);
            }
            if (x + 1 < gridSize) {
                assignCellWithValue(x + 1, y);
            }
            if (y - 1 >= 0) {
                assignCellWithValue(x, y - 1);
            }
            if (y + 1 < gridSize) {
                assignCellWithValue(x, y + 1);
            }
        }

    }

    private void assignCellWithValue(int x, int y) {
        int subX = x / subGridSize;
        int subY = y / subGridSize;

        if (oldSubX != subX || oldSubY != subY) {
            if (oldSubX != -1 && oldSubY != -1)
                subGrids[oldSubX][oldSubY].unlock(lockStamp);

            lockStamp = subGrids[subX][subY].writeLock();
            oldSubX = subX;
            oldSubY = subY;
        }

        if (!cells[x][y].isVisited()) {
            cells[x][y].setVisited(true);
            cells[x][y].setValue(value);
            if (x == xDest && y == yDest) {
                finished.set(true);
            }
            visitedCells.add(cells[x][y]);
        }
    }
}

