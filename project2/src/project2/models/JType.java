package project2.models;

/*
Handles all J-type instructions
 */
public class JType extends Instruction{
    private int address;

    public JType(){
        super(0,'I');
        this.address = 0;
    }
    public JType(int instruction){
        super(instruction >>> 26,'J');
        this.address = instruction & 0x03FFFFFF;
    }
    public int getAddress(){return address;}


}
