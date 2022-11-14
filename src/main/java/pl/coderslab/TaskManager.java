package pl.coderslab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = {"dodaj", "usuń", "lista", "wyjście"};
    static String[][] tasks;



    public static void main(String[] args) {
        tasks = uploadFromFile(FILE_NAME);
        select(OPTIONS);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {

            String input = scanner.nextLine();

            switch (input) {
                case "wyjście":
                    saveFile(FILE_NAME, tasks);
                    System.out.println(ConsoleColors.RED + "Koniec");
                    System.exit(0);
                    break;
                case "dodaj":
                    addTask();
                    break;
                case "usuń":
                    removeTask(tasks, getTheNumber());
                    System.out.println("Wartość usunięta.");
                    break;
                case "lista":
                    printTab(tasks);
                    break;
                default:

                    System.out.println("Wybierz właściwą opcję.");

            }

            select(OPTIONS);

        }


    }



    public static void select(String[] tab) {
        System.out.println(ConsoleColors.BLUE);
        System.out.println("Wybierz opcję: " + ConsoleColors.RESET);
        for(int i=0; i < tab.length; i++){

            System.out.println(tab[i]);
        }
    }





    public static String[][] uploadFromFile(String fileName) {

        Path dir = Paths.get(fileName);
        if (!Files.exists(dir)) {
            System.out.println("Plik nie istnieje");
            System.exit(0);
        }

        String[][] tabl = null;


        try {
            List<String> strings = Files.readAllLines(dir);
            tabl = new String[strings.size()][strings.get(0).split(",").length];

            for (int i = 0; i < strings.size(); i++) {
                String[] split = strings.get(i).split(",");
                for (int j = 0; j < split.length; j++) {
                    tabl[i][j] = split[j];
                }
            }
        } catch (IOException e) {
        e.printStackTrace();
        }
        return tabl;
    }


    public static void printTab(String[][] tab) {

        for (int i = 0; i < tab.length; i++) {

            System.out.print(i + " : ");

            for (int j = 0; j < tab[i].length; j++) {

                System.out.print(tab[i][j] + " ");

            }

            System.out.println();

        }

    }

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj opis zadania");
        String opis = scanner.nextLine();

        System.out.println("Podaj termin realizacji");
        String termin = scanner.nextLine();

        System.out.println("Czy zadanie jest ważne? t/n");
        String waznosc = scanner.nextLine();


        tasks =  Arrays.copyOf(tasks, tasks.length + 1);

        tasks[tasks.length-1] = new String[3];

        tasks[tasks.length-1][0] = opis;

        tasks[tasks.length-1][1] = termin;

        tasks[tasks.length-1][2] = waznosc;

    }

    public static boolean isNumberGreaterEqualZero(String input) {

        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }

    public static int getTheNumber() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj numer do usunięcia");

        String n = scanner.nextLine();

        while (!isNumberGreaterEqualZero(n)) {

            System.out.println("Nieprawidłowa wartość. Podaj liczbę większą lub równą 0");

            scanner.nextLine();

        }

        return Integer.parseInt(n);

    }

    private static void removeTask(String[][] tab, int index) {

        try {
            if (index < tab.length) {
                tasks = ArrayUtils.remove(tab, index);
            }

        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Podany element nie istnieje.");
        }

    }

    public static void saveFile(String fileName, String[][] tab) {

        Path dir = Paths.get(fileName);

        String[] lines = new String[tasks.length];
        for (int i = 0; i < tab.length; i++) {
            lines[i] = String.join(",", tab[i]);
        }

        try {
            Files.write(dir, Arrays.asList(lines));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }



}





