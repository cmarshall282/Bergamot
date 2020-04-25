package com.bergamot.printer;

import com.bergamot.file.GCode;
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class Printer {
    private int printTimeout;
    private int printTotal;

    private SerialPort printerPort;

    private PrintWriter printWriter;
    private InputStream inputStream;

    public Printer(int serialIndex, int printTimeout) {
        //TODO Error In Bound
        printerPort = SerialPort.getCommPorts()[serialIndex];
        printerPort.openPort();
        //TODO Figure out what this line does
        printerPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        printerPort.setBaudRate(115200);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        printWriter = new PrintWriter(printerPort.getOutputStream());
        inputStream = printerPort.getInputStream();
        this.printTimeout = printTimeout;
        printTotal = 0;
    }

    public void executeCommand(String gcode) {

        System.out.println("Queueing: " + gcode);
        printWriter.write(gcode);
        printWriter.write("\n");
        printWriter.flush();

        timeOut();
    }

    private void timeOut() {
        try {
            int i = inputStream.read();

            while(i != 10 && i != -1) {
                i = inputStream.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printFile(GCode gCodeFile) {
        if(!needsLeveling()) {
            while (gCodeFile.hasNextLine()) {
                executeCommand(gCodeFile.getNextLine());
            }

            printTotal++;
        }
    }

    public boolean needsLeveling() {
        return printTotal >= printTimeout;
    }

    public static void printSerialPorts() {
        for(SerialPort p : SerialPort.getCommPorts()) {
            System.out.println(p.getSystemPortName());
        }
    }

    public void close() {
        try {
            printWriter.close();
            inputStream.close();
            printerPort.closePort();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void resetPrintTotal() {
        printTotal = 0;
    }
}
