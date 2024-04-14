package project2.models;


/**
 * Registers model holds the value for the 32 MIPS registers.
 * Registers has two functions, one to read from a specific register, and
 * the other to write to a specific register
 *
 */

public class Registers {

    private int [] registers;


    public  Registers(){
        // set registers to the 32 registers of MIPS, initially all registers are empty
        // 1-32 will be used
        this.registers = new int [32];

        for(int i = 0; i < 32; i++){
            writeRegisterValue(i,0);
        }


    }

    public int readRegisterValue(int regIndex){
        return registers[regIndex];
    }
    public void writeRegisterValue(int regIndex, int regValue) {
        registers[regIndex] = regValue;

    }
}
