package day04;

import util.FileUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Range {

    private final int low;
    private final int high;

    public Range(int low, int high) {

        this.low = low;
        this.high = high;
    }

    public boolean contains(Range other) {
        return low <= other.low && high >= other.high;
    }

    public boolean overLaps(Range other) {
        return !noOverlap(other);
    }

    private boolean noOverlap(Range other) {
        return high < other.low || low > other.high;
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        List<String> strings = FileUtil.readStrings("src/day04/test.txt");
        int contained = 0;
        int overlaps = 0;
        for (String x :
                strings) {
            List<Range> ranges = match(x);
            if (oneContainsAmother(ranges.get(0), ranges.get(1))) {
                contained++;
            }
            if (ranges.get(0).overLaps(ranges.get(1))) {
                overlaps++;
            }
        }
        System.out.println(contained);
        System.out.println(overlaps);
    }

    private static boolean oneContainsAmother(Range first, Range second) {
        return first.contains(second) || second.contains(first);
    }

    private static List<Range> match(String str) {
        String regex = "(\\d+)-(\\d+),(\\d+)-(\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(str);
        m.find();
        return List.of(new Range(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2))),
                new Range(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))));

    }

}