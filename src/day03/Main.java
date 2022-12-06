package day03;

import util.FileUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static int priority(Character x) {
        int charValue = x;
        if (charValue >= 97) {
            //small case
            return charValue - 96;
        }
        return 26 + (charValue - 64);

    }
    public static void main(String[] args) throws Exception {
        List<String> strings = FileUtil.readStrings("src/day03/test.txt");
        int totalPriority = 0;
        for (String x :
                strings) {
            int length = x.length();
            String first = x.substring(0, length/2);
            String second = x.substring(length/2);
            Set<Character> ruckSack1 = extractCharSet(first);
            Set<Character> ruckSack2 = extractCharSet(second);
            ruckSack1.retainAll(ruckSack2);
            Character shared = ruckSack1.stream().toList().get(0);
            totalPriority += priority(shared);
        }
        System.out.println(totalPriority);

        int newTotalPriority = 0;
        List<Set<Character>> accumulated = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            accumulated.add(extractCharSet(strings.get(i)));
            if ((i + 1) % 3 != 0) {
                continue;
            }
            accumulated.get(0).retainAll(accumulated.get(1));
            accumulated.get(0).retainAll(accumulated.get(2));
            Character shared = accumulated.get(0).stream().toList().get(0);
            newTotalPriority += priority(shared);
            accumulated.clear();
        }
        System.out.println(newTotalPriority);
    }

    private static Set<Character> extractCharSet(String first) {
        return first.chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
    }
}