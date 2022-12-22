package day11;

import util.FileUtil;

import java.util.*;
import java.util.stream.Collectors;

class Monkey {

    public List<Long> items;
    public int test;
    public int trueThrow;
    public int falseThrow;
    public String operation;
    int num;

    public Monkey(int num) {

        this.num = num;
    }

    @Override
    public String toString() {
        return "Monkey{" +
                "items=" + items +
                ", test=" + test +
                ", trueThrow=" + trueThrow +
                ", falseThrow=" + falseThrow +
                '}';
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        List<String> inputs = FileUtil.readStrings("src/day11/test.txt").stream().filter(x -> x.length() != 0).toList();
        Monkey m = new Monkey(-1);
        ArrayList<Monkey> monkeys = new ArrayList<>();
        int x = 0;
        for (String input :
                inputs) {
            if (input.startsWith("Monkey ")) {
                m = new Monkey(x++);
                monkeys.add(m);
            }
            if (input.trim().startsWith("Starting items")) {
                String itemList = input.trim().substring("Starting items: ".length());
                m.items = Arrays.stream(itemList.split(",")).map(t -> Long.parseLong(t.trim())).collect(Collectors.toList());
            }
            if (input.trim().startsWith("Operation: new = ")) {
                m.operation = input.trim().substring("Operation: new = ".length());
            }
            if (input.trim().startsWith("Test: divisible by ")) {
                String divisibleBy = input.trim().substring("Test: divisible by ".length());
                m.test = Integer.parseInt(divisibleBy);
            }
            if (input.trim().startsWith("If true: throw to monkey ")) {
                String trueThrow = input.trim().substring("If true: throw to monkey ".length());
                m.trueThrow = Integer.parseInt(trueThrow);
            }
            if (input.trim().startsWith("If false: throw to monkey ")) {
                String trueThrow = input.trim().substring("If false: throw to monkey ".length());
                m.falseThrow = Integer.parseInt(trueThrow);
            }

        }
        System.out.println(monkeys);

        long superModulo = monkeys.stream().map(mon -> mon.test).reduce(1, (y, z) -> z * y);
        System.out.println(superModulo);


        Map<Integer, Queue<Long>> monkeyItems = new HashMap<>();
        int[] inspections = new int[monkeys.size()];


        for (Monkey monkey :
                monkeys) {
            monkeyItems.put(monkey.num, new ArrayDeque<>(new ArrayList<>(monkey.items)));
        }
        for (int round = 1; round <= 10000 ; round++) {
            long before = System.currentTimeMillis();
            for (Monkey monkey :
                    monkeys) {

                Queue<Long> items = monkeyItems.get(monkey.num);
                while (!items.isEmpty()) {
                    long item = items.remove();
                    inspections[monkey.num]++;
                    long y = getWorryLevel(item, monkey.operation);
//                    y = y % superModulo;

                    if (y % monkey.test == 0) {
                        addItem(monkeyItems, monkey.trueThrow, y);
                    } else {
                        addItem(monkeyItems, monkey.falseThrow, y);
                    }
                }
            }
            long after = System.currentTimeMillis();
            System.out.println("End of round " + round + ", time = " + (after - before)/1000);

        }
        System.out.println(Arrays.stream(inspections).boxed().collect(Collectors.toList()));

    }

    private static void addItem(Map<Integer, Queue<Long>> nextRoundMonkeyItems, int monkey, Long y) {
        nextRoundMonkeyItems.get(monkey).add(y);
    }

    private static long getWorryLevel(long item, String operation) throws Exception {

        String expr = operation.replaceAll("old", Long.toString(item));
        boolean isMultiply = expr.contains("*");
        String[] operands = expr.replaceAll("\\*|\\+", " ").split("\s+");
        long operand1 = Long.parseLong(operands[0]);
        long operand2 = Long.parseLong(operands[1]);
        return isMultiply ? operand1 * operand2: operand1 + operand2;
    }


}
