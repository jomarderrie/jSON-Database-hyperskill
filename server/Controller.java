package server;

import java.util.Arrays;
import java.util.Scanner;

public class Controller {
    String[] jsonDb;
    Scanner scanner;

    public Controller() {
        jsonDb = new String[1001];
        Arrays.setAll(jsonDb, i -> "");
        scanner = new Scanner(System.in);
        String input;
        boolean exit = false;
        while (!exit) {
            input = scanner.nextLine();
            String[] s = input.split(" ");
            if (s[0].equals("exit")) {
                exit = true;
            } else if (s[0].equals("get")) {
                getItem(s[1]);
            } else if (s[0].equals("set")) {
                String[] myArray1 = Arrays.copyOfRange(s, 2, s.length);
                setItem(s[1], myArray1);
            } else if (s[0].equals("delete")) {
                deleteItem(s[1]);
            } else {
                exit = true;
            }
        }
    }

    public void getItem(String s) {
        int a = Integer.parseInt(s);
        if (a != -1) {
            if (jsonDb[a].equals("")) {
                printError();
            } else {
                String itemName = (String) jsonDb[a]
                        .replace("[", "")  //remove the right bracket
                        .replace("]", "")
                        .replace(",",""); //remove the left bracket
                System.out.println(itemName);
            }
        }
    }

    public void setItem(String s, String[] list) {
        int a = Integer.parseInt(s);
        if (a != -1) {
            jsonDb[a] = Arrays.toString(Arrays.stream(list).toArray());
            printOk();
        }

    }

    public void deleteItem(String s) {
        int a = parseInt(s);
        if (a != -1) {
            jsonDb[a] = "";
            printOk();
        }
    }

    public void printError() {
        System.out.println("ERROR");
    }

    private int parseInt(String intToParse) {
        int a = Integer.parseInt(intToParse);
        if (a > 100 || a < 0) {
            printError();
            return -1;
        } else {
            return a;
        }
    }

    public void printOk() {
        System.out.println("OK");
    }

}
