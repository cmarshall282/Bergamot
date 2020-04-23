package com.bergamot;

import com.bergamot.file.GCode;
import com.bergamot.printer.Printer;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer(1, 2);

        /*while(!printer.needsLeveling()) {
            printer.printFile(new GCode(new File("tests/SimpleMove.gco")));
        }*/

        //printer.executeCommand("G28");
        printer.printFile(new GCode(new File("tests/SimpleMove.gco")));
        //printer.printFile(new GCode(new File("tests/MSMV2E3D_chris_test.gcode")));
    }
}
