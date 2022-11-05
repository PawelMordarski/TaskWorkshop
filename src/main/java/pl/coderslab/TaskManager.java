package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class TaskManager {

    static final String FILE_NAME = "tasks.scv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;



    public static void main(String[] args) {
        tasks = uploadFromFile(FILE_NAME);
        select(OPTIONS);

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
            System.out.println("File not exist.");
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
    }




