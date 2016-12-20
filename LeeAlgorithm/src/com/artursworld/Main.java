package com.artursworld;

import java.util.LinkedList;

/**
 * In dieser Aufgabe sollen Sie Lee’s Algorithmus mithilfe von Stamped Locks implementieren.
 * Lee’s Algorithmus arbeitet auf einem zwei-dimensionalem Array (Grid) und soll darauf den Startpunkt mit dem Endpunkt
 * verbinden (also die kürzeste Route finden). Es wird in zwei Phasen vorgegangen:
 */
public class Main {

    public LinkedList<Coordinate> getSet(int numOfPaths, int boundaries) {
        LinkedList<Coordinate> output = new LinkedList<>();

        for (int i = 0; i < 2 * numOfPaths; i++) {
            int x, y;
            Coordinate c;
            do {
                x = (int) (Math.random() * boundaries * 200) % boundaries;
                y = (int) (Math.random() * boundaries * 200) % boundaries;
                c = new Coordinate(x, y);
            } while (output.contains(c));
            output.add(c);
        }

        return output;
    }

    public void smallTest(int gridSize, Coordinate source, Coordinate target) {
        Grid g = new Grid(gridSize);
        int cost = g.calcPath(source.getX(), source.getY(), target.getX(), target.getY());
        System.out.println("Die Kosten von "+ source + " nach  "+ target + " betragen " + cost + ".");

    }

    public void bigTest(int numRandomPaths, int gridSize, int subGridSize) {
        LinkedList<Coordinate> randomPaths = this.getSet(numRandomPaths, gridSize);

        Grid g = new Grid(gridSize, subGridSize);
        for (int i = 0; i < randomPaths.size(); i += 2) {
            Coordinate c1 = randomPaths.get(i);
            Coordinate c2 = randomPaths.get(i + 1);

            int cost = g.calcPath(c1.getX(), c1.getY(), c2.getX(), c2.getY());
            g.reset();
            System.out.println(
                    "Die Kosten von (" + c1.getX() + "," + c1.getY() + ") nach (" + c2.getX() + "," + c2.getY()
                            + ") betragen " + cost + ".");
        }
    }

    public static void main(String[] args) {
        if (args.length != 3 && args.length != 2 && args.length != 0) {
            System.out.println(
                    "usage: Main [gridsize] [subgridsize] [numRandomPaths] OR Main [gridsize] [numRandomPaths] OR Main");
            System.exit(1);
        }

        Main m = new Main();
        if (args.length == 0) {
            m.smallTest(10, new Coordinate(3,4), new Coordinate(7,7 ));
        } else {
            int gridSize = Integer.parseInt(args[0]);
            int subGridSize;
            int numRandomPaths;
            if (args.length == 2) {
                subGridSize = gridSize;
                numRandomPaths = Integer.parseInt(args[1]);
            } else {
                subGridSize = Integer.parseInt(args[1]);
                numRandomPaths = Integer.parseInt(args[2]);
            }

            m.bigTest(numRandomPaths, gridSize, subGridSize);
        }

    }

    private static class Coordinate {
        private int x;
        private int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "("+x+", "+y+")";
        }
    }
}
