package day10;

import util.FileUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        List<String> inputs = FileUtil.readStrings("src/day10/test.txt");
        ArrayList<Integer> numbers = new ArrayList<>();
        for (String input : inputs) {
            numbers.add(0);
            if (!input.startsWith("noop")) {
                numbers.add(Integer.parseInt(input.split(" ")[1]));
            }
        }
        List<Integer> interestedCycles = Arrays.asList(20, 60, 100, 140, 180, 220);
        ArrayList<Integer> signalStrengths = new ArrayList<>();
        int acc = 1;
        for (int cycleNumber = 1; cycleNumber <= numbers.size() && cycleNumber <= 242; cycleNumber++) {
            int position = cycleNumber - 1;
            List<Integer> sprite = Arrays.asList(acc - 1, acc, acc + 1);
            System.out.print(sprite.contains(position % 40)? "#": ".");
            if (cycleNumber % 40 == 0) {
                System.out.println();
            }
            if (interestedCycles.contains(cycleNumber)) {
                signalStrengths.add(cycleNumber * acc);
            }
            acc += numbers.get(position);
        }
        System.out.println();
        System.out.println((Integer) signalStrengths.stream().mapToInt(x -> x).sum());
    }
}
