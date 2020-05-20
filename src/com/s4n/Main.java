package com.s4n;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String title = "**************************************************\n" +
                "**************************************************\n" +
                "**************************************************\n" +
                "Almuerzos el Corrientazo. Routes input.\n" +
                "**************************************************\n" +
                "**************************************************\n";

        System.out.println(title);

        Scanner scanner = new Scanner(System.in);
        int droneNumber = 1;
        int routesQuantity = 0;
        String decision = "y";
        String pathRouteFiles = "C:\\route_files\\";
        String pathRouteFinished = "C:\\route_finished\\";

        while (decision.equalsIgnoreCase("y")) {

            while (routesQuantity == 0) {
                try {
                    System.out.println("\nType quantity of delivery routes from drone " + droneNumber);
                    routesQuantity = scanner.nextInt();
                } catch (InputMismatchException e) {
                    //Ignore catch.
                }
            }

            String[] routes = new String[routesQuantity];

            for (int i = 1; i <= routesQuantity; i++) {

                System.out.println("\nType delivery route " + i + " from drone " + droneNumber);
                String route = scanner.next();
                routes[i - 1] = route;

            }

            System.out.println("\nGenerating delivery route file to drone " + droneNumber + "...");

            generateDeliveryRouteFile(droneNumber, pathRouteFiles, routes);

            System.out.println("\nGenerated delivery route file to drone " + droneNumber);

            System.out.println("\nDo you want to add delivery routes to another drone? (Y/N)");

            decision = scanner.next();

            if (decision.equalsIgnoreCase("y")) {
                droneNumber++;
                routesQuantity = 0;
            }
        }

        System.out.println("\nStarting delivery routes...");

        for (int i = 1; i <= droneNumber; i++) {

            String fileName = droneNumber < 10 ? "in0" + i : "in" + i;
            fileName = fileName + ".txt";

            String path = pathRouteFiles + fileName;
            List<String> output = new ArrayList<>();
            try {

                FileReader reader = new FileReader(path);
                BufferedReader bufferedReader = new BufferedReader(reader);
                String route;
                System.out.println("\n");
                while ((route = bufferedReader.readLine()) != null) {

                    int x = 0, y = 0;
                    String direction = "Norte";

                    for (int j = 0; j < route.length(); j++) {

                        switch (route.charAt(j)) {
                            case 'I':
                                direction = caseI(direction);
                                break;
                            case 'D':
                                direction = caseD(direction);
                                break;
                            case 'A':
                                if (direction.equals("Norte") || direction.equals("Sur"))
                                    y++;
                                else
                                    x++;
                                break;
                        }
                    }

                    if (direction.equals("Sur"))
                        output.add("(" + x + ", -" + y + ") dirección " + direction);
                    else if (direction.equals("Oeste"))
                        output.add("(-" + x + ", " + y + ") dirección " + direction);
                    else
                        output.add("(" + x + ", " + y + ") dirección " + direction);

                }
                bufferedReader.close();

                if (output.size() > 0) {

                    System.out.println("Generating report delivery file from drone " + i + "...");

                    generateReportFiles(i, pathRouteFinished, output);

                    System.out.println("Generated delivery report file from drone " + i);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        System.out.println("\nFinished delivery routes");

    }

    private static void generateDeliveryRouteFile(int droneNumber, String pathRouteFiles, String[] routes) {

        String number = droneNumber < 10 ? "0" + droneNumber : "" + droneNumber;

        File folder = new File(pathRouteFiles);
        if (!folder.exists()) folder.mkdir();

        String fileName = "in" + number + ".txt";
        File file = new File(pathRouteFiles + fileName);
        if (file.exists()) file.delete();

        try {

            FileWriter fileWriter = new FileWriter(pathRouteFiles + fileName, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (int i = 1; i <= routes.length; i++) {

                if (i == 1)
                    printWriter.printf(routes[i - 1]);
                else
                    printWriter.printf("%n" + routes[i - 1]);

                if (i == routes.length)
                    printWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void generateReportFiles(int i, String pathRouteFinished, List<String> output) {

        String number = i < 10 ? "0" + i : "" + i;

        File folder = new File(pathRouteFinished);
        if (!folder.exists()) folder.mkdir();

        String outFileName = "out" + number + ".txt";
        File file = new File(pathRouteFinished + outFileName);
        if (file.exists()) file.delete();

        try {

            FileWriter fileWriter = new FileWriter(pathRouteFinished + outFileName, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            for (int a = 0; a < output.size(); a++) {

                if (a > 0)
                    printWriter.printf("%n" + output.get(a));
                else {
                    String title = "** Delivery Report **";
                    printWriter.printf(title + "%n");
                    printWriter.printf(output.get(a));
                }
            }

            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String caseD(String direction) {

        if (direction.equals("Norte")) {
            direction = "Este";
        } else if (direction.equals("Este")) {
            direction = "Sur";
        } else if (direction.equals("Sur")) {
            direction = "Oeste";
        } else {
            direction = "Norte";
        }

        return direction;
    }

    private static String caseI(String direction) {

        if (direction.equals("Norte")) {
            direction = "Oeste";
        } else if (direction.equals("Oeste")) {
            direction = "Sur";
        } else if (direction.equals("Sur")) {
            direction = "Este";
        } else {
            direction = "Norte";
        }

        return direction;

    }
}
