package project2.models;


/**
 * Registers model holds the value for the 32 MIPS registers.
 * Registers has two functions, one to read from a specific register, and
 * the other to write to a specific register
 *
 */

public class Registers {

    private int [] registers;
    private int writes;
    private int reads;

    public  Registers(){
        // set registers to the 32 registers of MIPS, initially all registers are empty
        // 1-32 will be used
        this.registers = new int [32];
        this.writes = 0;
        this.reads = 0;

        for(int i = 0; i < 32; i++){
            registers[i] = 0;
        }


    }

    public int readRegisterValue(int regIndex){
        reads++;
        return registers[regIndex];
    }
    public void writeRegisterValue(int regIndex, int regValue) {
        writes++;
        registers[regIndex] = regValue;
    }

    public int [] getRegisters(){return registers;}

    public int getWrites(){return writes;}
    public int getReads(){return reads;}
}
