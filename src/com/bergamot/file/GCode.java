package com.bergamot.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

//TODO See if scanner works as well. Scanner in shared file
public class GCode {
    ArrayList<String> commands;

    public GCode(File sourceFile) {
        try {
            Scanner scanner = new Scanner(sourceFile);

            commands = new ArrayList<>();

            while(scanner.hasNextLine()) {
                String command = scanner.nextLine();

                if(command.contains(";")) {
                    int semicolonIndex = command.indexOf(";");
                    command = command.substring(0, semicolonIndex);
                }

                if(command.length() > 0)
                    commands.add(command);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean hasNextLine() {
        return commands.size() > 0;
    }

    public String getNextLine() {
        String command = commands.get(0);
        commands.remove(0);

        return command;
    }
}
