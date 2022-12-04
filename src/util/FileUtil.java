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
}
