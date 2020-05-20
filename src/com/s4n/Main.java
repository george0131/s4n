package com.s4n;

import java.util.InputMismatchException;
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

            System.out.println("\nDo you want to add delivery routes to another drone? (Y/N)");

            decision = scanner.next();
            droneNumber ++;
        }

    }
}
