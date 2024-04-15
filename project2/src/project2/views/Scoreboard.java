package project2.views;

import project2.controllers.*;
/*
    The Scoreboard is built to design all pertinent information to the terminal
    We designed the scoreboard to minimize how much information
    is stored here.
 */

public class Scoreboard {
    private Controller controller;

    public Scoreboard(){

    }

    public void displayBoard(int currentInstruction, int numCycles, int programCounter) {
        System.out.println("=============== SCOREBOARD ===============");
        System.out.println("Current Instruction Number: " + currentInstruction);
        System.out.println("Cycles: " + numCycles);
        System.out.println("Program Counter: 0x" + String.format("%08X", programCounter));

    }

    public void displayRegisters(int [] registers) {
        System.out.println("\nRegister Contents:");
        for (int i = 0; i < registers.length; i++) {
            System.out.printf("R%02d = %03d, ", i, registers[i]);
            if ((i + 1) % 8 == 0)
                System.out.println();
        }
    }

    public void displayMemory(byte [] memory) {
        System.out.println("\nMemory Contents:");
        for (int i = 0; i < memory.length; i++) {
            if (i % 4 == 0) {
                System.out.println();
                System.out.printf("%05X: ", i);
            }
            System.out.printf("%02X ", memory[i]);
        }
        System.out.println();
    }

    public void displayALUops(int and, int or, int add, int sub, int slt){
        System.out.println("Number of ALU Operations:");
        System.out.println("\tAND:\t"+and);
        System.out.println("\tOR:\t"+or);
        System.out.println("\tADD:\t"+add);
        System.out.println("\tSUB:\t"+sub);
        System.out.println("\tSLT:\t"+slt);
    }
    public void displayMemoryOps(int reads, int writes){
        System.out.println("Number of Memory Reads and Writes:");
        System.out.println("\tREADS:\t" + reads);
        System.out.println("\tWRITES:\t" + writes);
    }

    public void displayRegisterOps(int reads, int writes){
        System.out.println("Number of Register Reads and Writes:");
        System.out.println("\tREADS:\t" + reads);
        System.out.println("\tWRITES:\t" + writes);
    }
}