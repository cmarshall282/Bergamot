package com.bergamot;

import com.bergamot.file.GCode;
import com.bergamot.printer.Printer;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose your index (0 based):");
        Printer.printSerialPorts();
        int serialIndex = scanner.nextInt();

        System.out.println();
        System.out.print("How many prints until leveling timeout? ");
        int printTimeout = scanner.nextInt();

        System.out.println();
        System.out.print("Enter file path: ");
        scanner.nextLine();
        String filePath = scanner.nextLine();

        Printer printer = new Printer(serialIndex, printTimeout);
        boolean done = false;

        do {
            printer.resetPrintTotal();

            while(!printer.needsLeveling()) {
                printer.printFile(new GCode(new File(filePath)));
            }

            System.out.println();
            System.out.println("Printer needs leveling.");
            System.out.println("Would you like to continue (Y/N):");
            String choice = scanner.nextLine();
            System.out.println();

            done = choice.equals("N") || choice.equals("n");
        } while(!done);

        printer.close();
    }
}
