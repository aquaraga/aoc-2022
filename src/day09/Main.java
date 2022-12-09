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



public class Main {
    public static void main(String[] args) throws Exception {
        Set<Position> visited = new LinkedHashSet<>();
        List<String> inputs = FileUtil.readStrings("src/day09/test.txt");
//        Position start = new Position(0, 0);
        Position head;
        Position tail;
        head = new Position(0, 0);
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
                    tail = adjust(tail, head);
                    visited.add(tail.clonePosition());
                }
                continue;
            }
            if (dir.equalsIgnoreCase("U")) {
                for (int i = 0; i < moves; i++) {
                    head = head.up();
                    tail = adjust(tail, head);
                    visited.add(tail.clonePosition());
                }
                continue;

            }
            if (dir.equalsIgnoreCase("L")) {
                for (int i = 0; i < moves; i++) {
                    head = head.left();
                    tail = adjust(tail, head);
                    visited.add(tail.clonePosition());
                }
                continue;

            }
            if (dir.equalsIgnoreCase("D")) {
                for (int i = 0; i < moves; i++) {
                    head = head.down();
                    tail = adjust(tail, head);
                    visited.add(tail.clonePosition());
                }
            }

        }
        System.out.println(visited.size());




    }

    private static Position adjust(Position tail, Position head) {
        if (tail.equals(head)) {
            return tail;
        }
        if (tail.isAdjacentTo(head)) {
            return tail;
        }

        return tail.moveCloserTo(head);
    }
}