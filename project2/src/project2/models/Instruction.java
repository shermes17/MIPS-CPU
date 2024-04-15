package project2.models;

/*
    Abstract class that is inhereted by the different instruction type classes
 */
public abstract class Instruction {
    private int opcode;
    private char type;

    public Instruction(int opcode,char type){

        this.opcode = opcode;
        this.type = type;
    }
    public int getOpcode(){
        return opcode;
    }

    public char getType(){return type;}



}
