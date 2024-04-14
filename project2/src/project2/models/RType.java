package project2.models;

public class RType extends Instruction {
    private int rs;
    private int rt;
    private int rd;
    private int funct;
    private int shamt;

    public RType(){
        super(0,'I');
        this.rs = 0;
        this.rt = 0;
        this.rd = 0;
        this.funct = 0;
        this.shamt = 0;
    }

    public RType(int instruction) {
        super(instruction >>> 26,'R'); // Opcode is the top 6 bits
        this.rs = (instruction >>> 21) & 0x1F;  // Extract rs (bits 21-25)
        this.rt = (instruction >>> 16) & 0x1F;  // Extract rt (bits 16-20)
        this.rd = (instruction >>> 11) & 0x1F;  // Extract rd (bits 11-15)
        this.shamt = (instruction >>> 6) & 0x1F; // Extract shamt (bits 6-10)
        this.funct = instruction & 0x3F;       // Extract funct (lowest 6 bits)
    }

    public int getShamt() {
        return shamt;
    }

    public int getRS() {
        return rs;
    }

    public int getRT() {
        return rt;
    }

    public int getRD() {
        return rd;
    }

    public int getFunct() {
        return funct;
    }
}
