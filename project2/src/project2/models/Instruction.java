package project2.models;

public abstract class Instruction {
    protected int opcode;

    public Instruction(int opcode){
        this.opcode = opcode;
    }
    int getOpcode(){
        return opcode;
    }




}
