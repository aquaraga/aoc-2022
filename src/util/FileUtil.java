package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtil {
    public static List<Integer> readIntegers(String filePath) throws Exception {
        List<Integer> data = new ArrayList<>();
        Scanner myReader = new Scanner(new File(filePath));
        while (myReader.hasNextLine()) {
            data.add(Integer.valueOf(myReader.nextLine()));
        }
        myReader.close();
        return data;
    }

    public static List<List<Integer>> readListOfListOfIntegers(String filePath) throws Exception {
        List<List<Integer>> allData = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        Scanner myReader = new Scanner(new File(filePath));
        while (myReader.hasNextLine()) {
            String nextLine = myReader.nextLine();
            if (nextLine.trim().length() == 0) {
                allData.add(data);
                data = new ArrayList<>();
                continue;
            }
            data.add(Integer.valueOf(nextLine));
        }
        allData.add(data);
        myReader.close();
        return allData;
    }

    public static List<String> readStrings(String filePath) throws Exception {
        List<String> data = new ArrayList<>();
        Scanner myReader = new Scanner(new File(filePath));
        while (myReader.hasNextLine()) {
            data.add(myReader.nextLine());
        }
        myReader.close();
        return data;
    }
}
