package day09;

import util.FileUtil;

import java.util.*;


class Position {

    Position clonePosition() {
        return new Position(x, y);
    }
    int x;
    int y;

    public Position(int x, int y) {

        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Position right() {
        return new Position(x + 1, y);
    }

    @Override
    public boolean equals(Object obj) {
        Position other = (Position) obj;
        return x == other.x && y == other.y;
    }

    public boolean isAdjacentTo(Position head) {
        return Math.abs(head.x - x) <= 1 && Math.abs(head.y - y) <= 1;
    }

    public Position moveCloserTo(Position head) {
        if (head.y == y) {
            return new Position(head.x > x?++x:--x, y);
        }
        if (head.x == x) {
            return new Position(x, head.y>y?++y:--y);
        }
        if (Math.abs(y - head.y) == 2) {
            return new Position(head.x, head.y>y?++y:--y);
        }
        if (Math.abs(x - head.x) == 2) {
            return new Position(head.x > x?++x:--x, head.y);
        }
        System.out.println("Some issue!!");
        return this;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public Position up() {
        return new Position(x, y + 1);
    }

    public Position left() {
        return new Position(--x, y);
    }

    public Position down() {
        return new Position(x, --y);
    }

}

class Head {

    @Override
    public String toString() {
        return "Head{" +
                "allKnots=" + allKnots +
                '}';
    }

    public Head(List<Position> newAllKnots) {
        this.allKnots = newAllKnots;
    }

    public Position lastKnot() {
        return allKnots.get(allKnots.size() - 1);
    }

    private Position headOfHead() {
        return allKnots.get(0);
    }

    private List<Position> allKnots = new ArrayList<>();

    public Head(int knots, Position headOfHead) {
        allKnots.add(headOfHead);
        for (int i = 0; i < knots; i++) {
            allKnots.add(headOfHead.clonePosition());
        }
    }

    public Head right() {
        List<Position> newAllKnots = new ArrayList<>();
        newAllKnots.add(allKnots.get(0).right());
        Position tempHead = newAllKnots.get(0);
        for (int i = 1; i < allKnots.size(); i++) {
            Position adjusted = Main.adjust(allKnots.get(i), tempHead);
            newAllKnots.add(adjusted);
            tempHead = adjusted;
        }

        return new Head(newAllKnots);
    }

    public Head up() {
        List<Position> newAllKnots = new ArrayList<>();
        newAllKnots.add(allKnots.get(0).up());
        Position tempHead = newAllKnots.get(0);
        for (int i = 1; i < allKnots.size(); i++) {
            Position adjusted = Main.adjust(allKnots.get(i), tempHead);
            newAllKnots.add(adjusted);
            tempHead = adjusted;
        }

        return new Head(newAllKnots);
    }

    public Head left() {
        List<Position> newAllKnots = new ArrayList<>();
        newAllKnots.add(allKnots.get(0).left());
        Position tempHead = newAllKnots.get(0);
        for (int i = 1; i < allKnots.size(); i++) {
            Position adjusted = Main.adjust(allKnots.get(i), tempHead);
            newAllKnots.add(adjusted);
            tempHead = adjusted;
        }

        return new Head(newAllKnots);
    }

    public Head down() {
        List<Position> newAllKnots = new ArrayList<>();
        newAllKnots.add(allKnots.get(0).down());
        Position tempHead = newAllKnots.get(0);
        for (int i = 1; i < allKnots.size(); i++) {
            Position adjusted = Main.adjust(allKnots.get(i), tempHead);
            newAllKnots.add(adjusted);
            tempHead = adjusted;
        }

        return new Head(newAllKnots);
    }
}



public class Main {
    public static void main(String[] args) throws Exception {
        Set<Position> visited = new LinkedHashSet<>();
        List<String> inputs = FileUtil.readStrings("src/day09/test-example.txt");
//        Position start = new Position(0, 0);
        Position tail;
        Head head = new Head(9, new Position(0, 0));
        tail = new Position(0, 0);
        visited.add(tail.clonePosition());

        for (String s :
                inputs) {
            String[] splitted = s.split(" ");
            String dir = splitted[0];
            int moves = Integer.parseInt(splitted[1]);
            if (dir.equalsIgnoreCase("R")) {
                for (int i = 0; i < moves; i++) {
                    head = head.right();
                    tail = adjust(tail, head.lastKnot());
                    visited.add(tail.clonePosition());
                }
                continue;
            }
            if (dir.equalsIgnoreCase("U")) {
                for (int i = 0; i < moves; i++) {
                    head = head.up();
                    tail = adjust(tail, head.lastKnot());
                    visited.add(tail.clonePosition());
                }
                continue;

            }
            if (dir.equalsIgnoreCase("L")) {
                for (int i = 0; i < moves; i++) {
                    head = head.left();
                    tail = adjust(tail, head.lastKnot());
                    visited.add(tail.clonePosition());
                }
                continue;

            }
            if (dir.equalsIgnoreCase("D")) {
                for (int i = 0; i < moves; i++) {
                    head = head.down();
                    tail = adjust(tail, head.lastKnot());
                    visited.add(tail.clonePosition());
                }
            }

        }
        System.out.println(visited.size());
        System.out.println(head);


        System.out.println(adjust(new Position(4,0), new Position(5, 1)));




    }

    static Position adjust(Position tail, Position head) {
        if (tail.equals(head)) {
            return tail;
        }
        if (tail.isAdjacentTo(head)) {
            return tail;
        }

        return tail.moveCloserTo(head);
    }



}