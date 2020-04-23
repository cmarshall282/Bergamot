package com.bergamot;

import com.bergamot.file.GCode;
import com.bergamot.printer.Printer;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Printer printer = new Printer(1, 2);

        while(!printer.needsLeveling()) {
            printer.printFile(new GCode(new File("tests/SimpleMove.gco")));
        }
    }
}
