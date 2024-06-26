package project2.models;

/*
    Handles all I-type instructions
 */
public class IType extends Instruction {

    private int rs;
    private int rt;
    private int imm;

    public IType(){
        super(0,'I');
        this.rs = 0;
        this.rt = 0;
        this.imm = 0;
    }
    
    //Appropriate shifts for i type
    public IType(int instruction){
        super(instruction >>> 26,'I');
        this.rs = (instruction >>> 21) & 0x1F;
        this.rt = (instruction >>> 16) & 0x1F;
        this.imm = instruction & 0xFFFF;

        // Check if the immediate value is negative (sign extension)
        if ((this.imm & 0x8000) != 0) {
            this.imm |= 0xFFFF0000;
        }
    }

    public int getRS(){return rs;}
    public int  getRT(){return rt;}

    public int getImm(){return imm;}




}
