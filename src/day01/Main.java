package day01;

import util.FileUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        List<List<Integer>> allCalories = FileUtil.readListOfListOfIntegers("src/day01/test.txt");

        List<Integer> aggregateCalories = allCalories.stream().map(x -> x.stream().mapToInt(Integer::intValue).sum())
                .collect(Collectors.toList());

        Integer maxCalories = Collections.max(aggregateCalories);
        System.out.println(maxCalories);

        aggregateCalories.sort(Comparator.reverseOrder());
        int top3Calories = aggregateCalories.subList(0, 3).stream().mapToInt(Integer::intValue).sum();
        System.out.println(top3Calories);
    }
}