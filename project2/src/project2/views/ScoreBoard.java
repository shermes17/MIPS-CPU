package project2.views;

import project2.controllers.*;

/*
public class ScoreBoard  {

    public Controller controller;
    private int numReads;
    private int numWrites;
    private int numCycles;


   public void Scoreboard(Controller controller){
       this.controller = controller;

       this.numWrites = 0;
       this.numReads = 0;
       this.numCycles = 0;

    }


    public void addReads(){
       numReads++;
    }
    public void addWrites(){
       numWrites++;
    }
    public void addCycles(){
        numCycles++;
    }

}
*/
public class Scoreboard {
    private Controller controller;
    private int programCounter;
    private int numCycles;
    private int[] aluOpsCount; // Order: AND, OR, ADD, SUB, SLT
    private int[] registers;
    private byte[] memory;
    private int numReads;
    private int numWrites;
    private int currentInstruction;
    /*
    public Scoreboard() {
        registers[0] = 0; // Ensure register 0 is always zero, if applicable.
    } */

    public void Scoreboard(Controller controller) {
        this.controller = controller;
        this.numWrites = 0;
        this.numReads = 0;
        this.numCycles = 0;
        this.registers = new int[32];
        this.memory = new byte[4096];
        this.aluOpsCount = new int[5]; // Order: AND, OR, ADD, SUB, SLT
        this.programCounter=0;
        this.currentInstruction=0;


    }

    public void display() {
        System.out.println("=============== SCOREBOARD ===============");
        System.out.println("Current Instruction Number: " + currentInstruction);
        System.out.println("Cycles: " + numCycles);
        System.out.println("Program Counter: 0x" + String.format("%08X", programCounter));

        displayALUOperations();
        displayMemoryOperations();
        displayRegisterContents();
        displayMemoryContents();

        System.out.println("==========================================");
    }

    private void displayALUOperations() {
        System.out.println("\nALU Operations Count:");
        System.out.printf("AND = %d, OR = %d, ADD = %d, SUB = %d, SLT = %d%n",
                aluOpsCount[0], aluOpsCount[1], aluOpsCount[2],
                aluOpsCount[3], aluOpsCount[4]);
    }

    private void displayMemoryOperations() {
        System.out.println("\nMemory Operations:");
        System.out.printf("Reads = %d, Writes = %d%n", numReads, numWrites);
    }

    private void displayRegisterContents() {
        System.out.println("\nRegister Contents:");
        for (int i = 0; i < registers.length; i++) {
            System.out.printf("R%02d = %03d, ", i, registers[i]);
            if ((i + 1) % 8 == 0)
                System.out.println();
        }
    }

    private void displayMemoryContents() {
        System.out.println("\nMemory Contents:");
        for (int i = 0; i < memory.length; i++) {
            if (i % 32 == 0) {
                System.out.println();
                System.out.printf("%05X: ", i);
            }
            System.out.printf("%02X ", memory[i]);
        }
        System.out.println();
    }

    public void updateProgramCounter(int address) {
        programCounter = address;
    }

    public void addCycles() {
        numCycles++;
    }

    public void recordALUOperation(int type) {
        if (type >= 0 && type < aluOperationsCount.length) {
            aluOperationsCount[type]++;
        }
    }

    public void updateRegister(int regNum, int regData) {
        if (regNum > 0 && regNum < registers.length) { // Register 0 is constant zero if applicable
            registers[regNum] = regData;
        }
    }

    public void updateMemory(int address, int memData) {
        if (address >= 0 && address < memory.length) {
            memory[address] = memData;
        }
    }

    public void addReads() {
        numReads++;
    }

    public void addWrites() {
        numWrites++;
    }

    public void addInstruction(int instructionNum) {
        currentInstruction = instructionNum;
    }
}