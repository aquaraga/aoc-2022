package day14;

import util.FileUtil;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Coordinate {

    final int x;
    final int y;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Coordinate(int x, int y) {

        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", x, y);
    }

    public static Coordinate from(String x) {
        String[] split = x.trim().split(",");
        return new Coordinate(Integer.parseInt(split[1]), Integer.parseInt(split[0]));
    }

    public List<Coordinate> till(Coordinate another) {
        if (x == another.x) {
            return IntStream.range(Math.min(y, another.y), Math.max(y, another.y) + 1).boxed().map(t -> new Coordinate(x, t)).toList();
        }
        return IntStream.range(Math.min(x, another.x), Math.max(x, another.x) + 1).boxed().map(t -> new Coordinate(t, y)).toList();
    }

    public Coordinate oneDown() {
        return new Coordinate(x + 1, y);
    }

    public Coordinate leftDown() {
        return new Coordinate(x + 1, y - 1);
    }

    public Coordinate rightDown() {
        return new Coordinate(x + 1, y + 1);
    }
}

class Cave {
    Set<Coordinate> rock = new HashSet<>();
    Set<Coordinate> sand = new HashSet<>();
    Map<Integer, Integer> abyss;

    int floor;

    public static Cave parseToCave(List<String> inputs) {
        Cave cave = new Cave();
        inputs.forEach(input -> {
            List<Coordinate> coordinates = Arrays.stream(input.split("->")).map(Coordinate::from).toList();
            for (int i = 0; i < coordinates.size() - 1; i++) {
                cave.addRockInRange(coordinates.get(i), coordinates.get(i + 1));
            }
        });
        cave.abyss = computeAbyss(cave.rock);
        cave.floor = cave.rock.stream().map(c -> c.x).max(Integer::compareTo).get() + 2;
        return cave;
    }

    private static Map<Integer, Integer> computeAbyss(Set<Coordinate> rock) {
        Map<Integer, Integer> abyss = new HashMap<>();
        List<Integer> ys = rock.stream().map(c -> c.y).toList();
        for (Integer y :
                ys) {
            abyss.put(y, rock.stream().filter(c -> c.y == y).map(c -> c.x).max(Integer::compareTo).get());
        }
        return abyss;
    }

    private void addRockInRange(Coordinate c1, Coordinate c2) {
        rock.addAll(c1.till(c2));
    }

    public Status putSand(Coordinate coordinate) {
        Coordinate current = coordinate;
        boolean moved = false;

        while (true) {
            Optional<Coordinate> mayBeFree = Stream.of(current.oneDown(), current.leftDown(), current.rightDown()).filter(this::isFree).findFirst();
            if (mayBeFree.isPresent()) {
                moved = true;
                Coordinate free = mayBeFree.get();
//                if (inAbyss(free)) {
//                    return Status.ABYSS;
//                }
                current = free;
            } else {
                if (!moved) {
                    return Status.FULL;
                }
                sand.add(current);
                return Status.RESTED;
            }
        }
    }

    private boolean inAbyss(Coordinate coordinate) {
        if (!abyss.containsKey(coordinate.y))
            return true;
        return abyss.get(coordinate.y) < coordinate.x;
    }

    private boolean isFree(Coordinate c) {
        if (c.x >= floor) {
            return false;
        }
        return !rock.contains(c) && !sand.contains(c);
    }
}

enum Status {RESTED, ABYSS, FULL}

public class Main {
    public static void main(String[] args) throws Exception {
        List<String> inputs = FileUtil.readStrings("src/day14/test.txt");
        Cave cave = Cave.parseToCave(inputs);
//        System.out.println(cave.abyss);
        System.out.println(cave.floor);

//        Status status = cave.putSand(new Coordinate(0, 500));
//        System.out.println(status);
//        System.out.println(cave.sand);

        int grains = 0;
        while (true) {
            Status status = cave.putSand(new Coordinate(0, 500));
            ++grains;
            if (status.equals(Status.FULL)) {
                break;
            }

        }
        System.out.println(grains);

    }


}
