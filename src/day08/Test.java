package day08;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
//        System.out.println(highToLow(5, 1).boxed().collect(Collectors.toList()));


        System.out.println(Stream.of(3, 4, 5, 5, 5, 6, 3, 1).takeWhile(x -> x <= 5).collect(Collectors.toList()));
    }

    private static IntStream highToLow(int highExclusive, int lowInclusive) {
        return IntStream.range(lowInclusive, highExclusive).map(i -> -i).sorted().map(i -> -i).takeWhile(x -> x > 1);
    }
}
