package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        startApp();

    }

    public static void startApp() {

        System.out.println("Starting...");

        Scanner s = new Scanner(System.in);

     /*   while (!exit()) {
            System.out.println("Please select an option: ");
            showMenu();

            String input = s.nextLine();

            switch (input) {
                //   case "add" -> add();
                //    case "remove" -> remove();
                //     case "list" -> list();
                //   case "exit" -> exit();

            }
     }

*/
    }

        private static String[][] tasks() {
        String pathName = "tasks.csv";
        File file = new File(pathName);

        return csvToArray(pathName);

    }

        private static String [][] csvToArray(String pathName) {
        Path path = Path.of(pathName);
        String [][] fileArray = new String[1][1];
        try {
            String wholeFile = Files.readString(path);
            String [] tmpArray = wholeFile.split("\n");
            fileArray = Arrays.copyOf(fileArray, tmpArray.length);

            for (int i = 0; i < tmpArray.length; i++){

                String [] tmpArray2 = tmpArray[i].split(", ");
                fileArray[i] = tmpArray2;

                System.arraycopy(tmpArray2, 0, fileArray[i], 0, tmpArray2.length); // same as : for (int j = 0; j < tmpArray2.length; j++){
                                                                                        // fileArray[i][j] = tmpArray2[j];

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileArray;
    }

    private static void showMenu() {

        System.out.println("""
                add
                remove
                list
                exit
                """);
    }


    public static boolean exit() {
        return true;
    }

}
