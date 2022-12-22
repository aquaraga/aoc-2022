package day13;

import util.FileUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class Packet {

    final List<Object> objects;
    boolean isDistress = false;
    public Packet(String s) {
        this.objects = createListFormOne(s);
    }

    public Packet(String s, boolean isDistress) {
        this.objects = createListFormOne(s);
        this.isDistress = isDistress;
    }

    @Override
    public String toString() {
        return objects.toString();
    }

    private List createListFormOne(String input) {
        Stack<List> stack = new Stack<>();
        List toReturn = null;
        String s = input;
        while (s.length() > 0) {
            if (s.startsWith("[")) {
                stack.push(new ArrayList<>());
                s = s.substring(1);
                continue;
            }
            if (s.startsWith("]")) {
                toReturn = stack.pop();
                if (stack.isEmpty()) {
                    return toReturn;
                } else {
                    stack.peek().add(toReturn);
                }
                s = s.substring(1);
                continue;
            }
            if (s.startsWith(",")) {
                s = s.substring(1);
                continue;
            }
            String attemptedInt = attemptToExtractInteger(s);

            if (attemptedInt.length() > 0) {
                int x = Integer.parseInt(attemptedInt);//Get the integer
                stack.peek().add(x);
                s = s.substring(attemptedInt.length());
            }
        }
        return toReturn;

    }

    private String attemptToExtractInteger(String s) {
        return Character.isDigit(s.charAt(0)) ? (s.charAt(0) +
                (Character.isDigit(s.charAt(1)) ? String.valueOf(s.charAt(1)) : "")) : "";
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        List<String> inputs = FileUtil.readStrings("src/day13/test.txt").stream().filter(x -> x.trim().length() != 0).toList();
//        List<List<String>> pairs = partitionBasedOnSize(inputs, 2);
//        int index = 0;
//        int sum = 0;
//        for (List<String> pair :
//                pairs) {
//            ++index;
//            if (rightOrder(new Packet(pair.get(0)), new Packet(pair.get(1)))) {
//                sum += index;
//            }
//        }
//        System.out.println(sum);


        List<Packet> inputPackets = new ArrayList<>(inputs.stream().map(Packet::new).toList());
        inputPackets.add(new Packet("[[2]]", true));
        inputPackets.add(new Packet("[[6]]", true));


        List<Packet> sortedPackets = inputPackets.stream().sorted((o1,o2) -> {
            if (rightOrder(o1, o2)) {
                return -1;
            } else {
                return 1;
            }
        }).toList();

        for (int i = 0; i < sortedPackets.size(); i++) {
            if (sortedPackets.get(i).isDistress) {
                System.out.println(i + 1);
            }
        }
    }
    private static boolean rightOrder(Packet left, Packet right) {
        Result result = rightOrder1(left.objects, right.objects, Result.KEEP_CHECKING);
        return !result.equals(Result.WRONG);
    }

    private static List safeSublist(List list, int from, int to) {
        if (list == null) {
            return null;
        }
        if (from >= list.size() || to > list.size()) {
            return null;
        }
        return list.subList(from, to);
    }

    private static Result rightOrder1(Object left, Object right, Result acc) {
        if (!acc.equals(Result.KEEP_CHECKING)) {
            return acc;
        }
        if (left instanceof List && right instanceof List) {
            Result first = rightOrder1(safeGet((List)left, 0), safeGet((List) right, 0), Result.KEEP_CHECKING);
            if (first.equals(Result.KEEP_CHECKING)) {
                return rightOrder1(safeSublist((List) left, 1, ((List<?>) left).size()), safeSublist((List) right, 1, ((List<?>) right).size()), Result.KEEP_CHECKING);
            } else {
                return first;
            }
        }
        if (left == null && right != null) {
            return Result.RIGHT;
        }
        if (left != null && right == null) {
            return Result.WRONG;
        }

        if (left instanceof Integer && right instanceof Integer) {
            if ((Integer)left < (Integer)right) {
                return Result.RIGHT;
            }
            if ((Integer)left > (Integer)right) {
                return Result.WRONG;
            }
        }

        if (left instanceof List && right instanceof Integer) {
            return rightOrder1(left, Arrays.asList(right), Result.KEEP_CHECKING);
        }
        if (right instanceof List && left instanceof Integer) {
            return rightOrder1(Arrays.asList(left), right, Result.KEEP_CHECKING);
        }
        return Result.KEEP_CHECKING;
    }

    private static Object safeGet(List list, int i) {
        if (i < list.size()) {
            return list.get(i);
        }
        return null;
    }

    static <T> List<List<T>> partitionBasedOnSize(List<T> inputList, int size) {
        final AtomicInteger counter = new AtomicInteger(0);
        return inputList.stream()
                .collect(Collectors.groupingBy(s -> counter.getAndIncrement()/size))
                .values().stream().toList();
    }
}

enum Result {RIGHT, WRONG, KEEP_CHECKING;
}
