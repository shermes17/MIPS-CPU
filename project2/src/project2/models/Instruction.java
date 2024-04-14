package project2.models;

public abstract class Instruction {
    private int opcode;
    private char type;

    public Instruction(int opcode,char type){

        this.opcode = opcode;
        this.type = type;
    }
    int getOpcode(){
        return opcode;
    }

    char getType(){return type;}



}
