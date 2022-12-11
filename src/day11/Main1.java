package day11;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

public class Main1 {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(79, 98);
        ArrayDeque<Integer> deque = new ArrayDeque<>(integers);
        System.out.println(deque.remove());
        System.out.println(deque.remove());

    }
}
