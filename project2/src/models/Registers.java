package models;

/**
 * Registers model holds the value for the 32 MIPS registers
 * Registers has two functions, one to read from a specific register, and
 * the other to write to a specific register
 *
 */

public class Registers {

    private int [] registers;
    // view object

    public void Registers(){
        // set registers to the 32 registers of MIPS, initially all registers are empty
        registers = new int [31];
    }

    public int readRegisterValue(int regIndex){
        return registers[regIndex];
    }
    public void writeRegisterValue(int regIndex, int regValue) {
        registers[regIndex] = regValue;
        // update view
    }
}
