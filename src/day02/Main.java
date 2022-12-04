package day02;

import util.FileUtil;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello world!");
        List<Integer> integers = FileUtil.readIntegers("src/day02/test.txt");
        System.out.println(integers);
    }
}