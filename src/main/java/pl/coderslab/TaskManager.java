package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class TaskManager {

    public static void main(String[] args) {

        startApp();

    }

    public static void startApp() {

        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Starting..." +
                ConsoleColors.RESET);
        Scanner s = new Scanner(System.in);
        String pathName = "tasks.csv";

        boolean exit = false;
        String[][] loadedTasks = tasks(pathName);


        while (!exit) {
            System.out.println("Choose one from following options: \n");
            showMenu();
            System.out.println("Please select an option: ");

            String input = s.nextLine();
            try {
                ValueOfOption.Option option = ValueOfOption.Option.valueOf(input.toLowerCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Incorrect option \n");
            }

            switch (input) {
                case "add" -> loadedTasks = addNewItem(loadedTasks);
                case "remove" -> loadedTasks = removeItem(loadedTasks);
                case "list" -> showList(loadedTasks);
                case "exit" -> {
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT +
                            "Exiting and Saving..." +
                            ConsoleColors.RESET);
                    saveTasks(loadedTasks, pathName);
                    exit = true;
                }

            }
        }

    }

    private static String[][] tasks(String pathName) {

        File file = new File(pathName);
        return loadTasks(pathName);

    }

    private static String[][] loadTasks(String pathName) {
        Path path = Path.of(pathName);
        String[][] fileArray = new String[1][1];

        try {
            String wholeFile = Files.readString(path);
            String[] tmpArray = wholeFile.split("\n");
            fileArray = Arrays.copyOf(fileArray, tmpArray.length);

            for (int i = 0; i < tmpArray.length; i++) {

                String[] tmpArray2 = tmpArray[i].split(", ");
                fileArray[i] = tmpArray2;

                System.arraycopy(tmpArray2, 0, fileArray[i], 0, tmpArray2.length);
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

    private static String[][] addNewItem(String[][] arr) {
        Scanner s = new Scanner(System.in);
        StringBuilder newTask = new StringBuilder();
        System.out.println("Please add task description");
        newTask.append(s.nextLine()).append(",");

        System.out.println("PLease add task due date");
        newTask.append(s.nextLine()).append(",");

        System.out.println("Is your task important: true / false");
        newTask.append(s.nextLine());

        arr = Arrays.copyOf(arr, arr.length + 1);
        arr[arr.length - 1] = newTask.toString().split(",");

        System.out.println(
                ConsoleColors.GREEN_BOLD + (newTask.toString().replaceAll(",", " "))
                        + " added successfully" + "\n" + ConsoleColors.RESET);

        return arr;
    }

    private static String[][] removeItem(String[][] loadedTasks) throws IndexOutOfBoundsException {
        Scanner s = new Scanner(System.in);
        System.out.println("Please provide number of the item to be removed.");
        int input = -1;
        try {
            input = Integer.parseInt(s.nextLine());
        } catch (NumberFormatException en) {
            System.out.println("Your option must be a number;");
            return loadedTasks;
        }

        try {
            loadedTasks = ArrayUtils.remove(loadedTasks, (input - 1));
            System.out.println(ConsoleColors.RED_BOLD + "Item " + (input) +
                    " removed successfully" + ConsoleColors.RESET);
            return loadedTasks;
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Incorrect task number");
        }
        return loadedTasks;
    }

    private static void showList(String[][] arr) {

        for (int i = 0; i < arr.length; i++) {
            StringBuilder itemsOnIndex = new StringBuilder();
            for (int j = 0; j < arr[i].length; j++) {
                itemsOnIndex.append(arr[i][j]).append(" ");

            }
            System.out.println((i + 1) + ": " + itemsOnIndex.toString());

        }
        System.out.println();

    }


    private static void saveTasks(String[][] savedTasks, String pathName) {

        Path path = Paths.get(pathName);
        StringBuilder save = new StringBuilder();

        for (String[] savedTask : savedTasks) {
            for (String s : savedTask) {
                save.append(s).append(", ");
            }
            save.append("\n");
        }
        String savedString = save.toString();

        try {
            Files.write(path, Collections.singleton(savedString));
        } catch (IOException ex) {
            System.out.println("Unable to save.");
        }

    }

}
