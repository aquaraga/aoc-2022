package day08;

import util.FileUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws Exception {
        IntFunction<Integer> previous    = (int x) -> (x - 1);
        IntFunction<Integer> next    = (int x) -> (x + 1);

        List<String> strings = FileUtil.readStrings("src/day08/test.txt");
        int size = strings.size();
        int [][] heights = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                heights[i][j] = strings.get(i).charAt(j);
            }
        }
        boolean[][] visible = new boolean[size][size];
        long maxScenicScore = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == 0 || j == 0 || i == size - 1 || j == size - 1) {
                    visible[i][j] = true;
                    continue;
                }
                final int tempI = i;
                final int tempJ = j;
                int treeHeight = heights[i][tempJ];
                visible[i][j] = Arrays.stream(heights[i], 0, j).allMatch(y -> y < treeHeight) ||
                  IntStream.range(0, i).map(x -> heights[x][tempJ]).allMatch(y -> y < treeHeight) ||
                  Arrays.stream(heights[i], j + 1, size).allMatch(y -> y < treeHeight) ||
                  IntStream.range(i + 1, size).map(x -> heights[x][tempJ]).allMatch(y -> y < treeHeight);


                final AtomicInteger counter1 = new AtomicInteger(0);
                CustomForEach.forEach(highToLow(i, 0).boxed(), (elem, breaker) -> {
                    if (heights[elem][tempJ] < treeHeight) {
                        counter1.incrementAndGet();
                    } else if (heights[elem][tempJ] >= treeHeight) {
                        counter1.incrementAndGet();
                        breaker.stop();
                    }
                });
                long a = counter1.get(); //top



                final AtomicInteger counter2 = new AtomicInteger(0);
                CustomForEach.forEach(highToLow(j, 0).boxed(), (elem, breaker) -> {
                    if (heights[tempI][elem] < treeHeight) {
                        counter2.incrementAndGet();
                    } else if (heights[tempI][elem] >= treeHeight) {
                        counter2.incrementAndGet();
                        breaker.stop();
                    }
                });
                long b = counter2.get(); //left

                final AtomicInteger counter3 = new AtomicInteger(0);
                CustomForEach.forEach(IntStream.range(i + 1, size).boxed(), (elem, breaker) -> {
                    if (heights[elem][tempJ] < treeHeight) {
                        counter3.incrementAndGet();
                    } else if (heights[elem][tempJ] >= treeHeight) {
                        counter3.incrementAndGet();
                        breaker.stop();
                    }
                });
                long c = counter3.get(); //bottom

                final AtomicInteger counter4 = new AtomicInteger(0);
                CustomForEach.forEach(IntStream.range(j + 1, size).boxed(), (elem, breaker) -> {
                    if (heights[tempI][elem] < treeHeight) {
                        counter4.incrementAndGet();
                    } else if (heights[tempI][elem] >= treeHeight) {
                        counter4.incrementAndGet();
                        breaker.stop();
                    }
                });
                long d = counter4.get(); //right

                long scenicScore = a * b * c * d;
                if (scenicScore > maxScenicScore) {
                    maxScenicScore = scenicScore;
                }
            }
        }
//        System.out.println(count(visible));
        System.out.println(maxScenicScore);
//        print(visible);

    }


    private static IntStream adjust(IntStream stream, int treeHeight) {
        List<Integer> theList = stream.boxed().toList();
        Stream<Integer> tempStream = theList.stream();
        if (tempStream.anyMatch(x -> x == treeHeight)) {
            return Stream.concat(tempStream.filter(x -> x != treeHeight), Stream.of(treeHeight)).mapToInt(x -> x);
        }
        return theList.stream().mapToInt(x -> x);
    }

    private static IntStream highToLow(int highExclusive, int lowInclusive) {
        return IntStream.range(lowInclusive, highExclusive).map(i -> -i).sorted().map(i -> -i);
    }

    private static void print(boolean[][] visible) {
        for (boolean[] booleans : visible) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    System.out.print("#");;
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }

    }

    private static int count(boolean[][] visible) {
        int count = 0;
        for (boolean[] booleans : visible) {
            for (boolean aBoolean : booleans) {
                if (aBoolean) {
                    ++count;
                }
            }
        }
        return count;
    }
}

class CustomForEach {

    public static class Breaker {
        private boolean shouldBreak = false;

        public void stop() {
            shouldBreak = true;
        }

        boolean get() {
            return shouldBreak;
        }
    }

    public static <T> void forEach(Stream<T> stream, BiConsumer<T, Breaker> consumer) {
        Spliterator<T> spliterator = stream.spliterator();
        boolean hadNext = true;
        Breaker breaker = new Breaker();

        while (hadNext && !breaker.get()) {
            hadNext = spliterator.tryAdvance(elem -> {
                consumer.accept(elem, breaker);
            });
        }
    }
}
