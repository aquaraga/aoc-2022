package day05;

import util.FileUtil;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class MarkedStack {

    private final Stack<String> stack = new Stack<>();

    public MarkedStack(String crateString) {
        int length = crateString.length();
        for (int i = length - 1; i >= 0; i--) {
            stack.push(String.valueOf(crateString.charAt(i)));
        }
    }

    public String pop() {
        return stack.pop();
    }

    public void push(String crate) {
        stack.push(crate);
    }

    public String peek() {
        return stack.peek();
    }
}

record Instruction(int num, int src, int dest){}


public class Main {
    public static void main(String[] args) throws Exception {
        SortedMap<Integer, MarkedStack> stacks = initializeStacksV2();
        List<String> strings = FileUtil.readStrings("src/day05/test.txt");
        for (String string :
                strings) {
            Instruction instruction = parse(string);
            int x = instruction.num();
            //PART1
//            while (x > 0) {
//                String crate = stacks.get(instruction.src()).pop();
//                stacks.get(instruction.dest()).push(crate);
//                x--;
//            }
            //PART2
            Stack<String> tempStack = new Stack<>();
            while (x > 0) {
                String crate = stacks.get(instruction.src()).pop();
                tempStack.push(crate);
                x--;
            }
            while (!tempStack.isEmpty()) {
                stacks.get(instruction.dest()).push(tempStack.pop());
            }
        }
        Collection<MarkedStack> allStacks = stacks.values();
        System.out.println(allStacks.stream().map(MarkedStack::peek).collect(Collectors.joining()));
    }

    private static SortedMap<Integer, MarkedStack> initializeStacksV1() {

        Map<Integer, MarkedStack> map = Map.of(1, new MarkedStack("NZ"),
                2, new MarkedStack("DCM"),
                3, new MarkedStack("P"));
        TreeMap<Integer, MarkedStack> treeMap = new TreeMap<>();
        treeMap.putAll(map);
        return treeMap;
    }

    private static SortedMap<Integer, MarkedStack> initializeStacksV2() {

        Map<Integer, MarkedStack> map = Map.of(1, new MarkedStack("PVZWDT"),
                2, new MarkedStack("DJFVWSL"),
                3, new MarkedStack("HBTVSLMZ"),
                4, new MarkedStack("JSR"),
                5, new MarkedStack("WLMFGBZC"),
                6, new MarkedStack("BGRZHVWQ"),
                7, new MarkedStack("NDBCPJV"),
                8, new MarkedStack("QBTP"),
                9, new MarkedStack("CRZGH")
                );
        TreeMap<Integer, MarkedStack> treeMap = new TreeMap<>();
        treeMap.putAll(map);
        return treeMap;
    }

    private static Instruction parse(String str) {
        String regex = "move (\\d+) from (\\d+) to (\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(str);
        m.find();

        return new Instruction(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
                Integer.parseInt(m.group(3)));

    }

}