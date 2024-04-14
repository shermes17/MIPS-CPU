package project2.models;

public class JType extends Instruction{
    private int address;
    public JType(int instruction){
        super(instruction >>> 26);
        this.address = instruction & 0x03FFFFFF;
    }
    public int getAddress(){return address;}


}
