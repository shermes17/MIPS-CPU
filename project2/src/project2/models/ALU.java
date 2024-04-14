package project2.models;

package models;

public class ALU {
    private boolean zero;
    Scoreboard view;
    boolean pcOp;

    public ALU(boolean pcOp) {
        zero = false;
        this.pcOp = pcOp;
    }

    public int arithmetic(int reg1, int reg2, int ALUControlInput) {
        int type;
        byte result = 0;
        switch (ALUControlInput) {
            case 0b0000: // AND
                result = reg1 & reg2;
                type = 0;
                break;
            case 0b0001: // OR
                result = reg1 | reg2;
                type = 1;
                break;
            case 0b0010: // add
                result = reg1 + reg2;
                type = 2;
                break;
            case 0b0110: // subtract
                result = reg1 - reg2;
                type = 3;
                break;
            case 0b0111: // set on less than
                if (reg1 < reg2)
                    result = 1;
                else
                    result = 0;
                type = 4;
                break;
            default:
                System.err.println("Error: Invalid ALU op");
                type = -1;
        }

        if (result == 0)
            this.zero = true;
        else
            this.zero = false;
        return result;
    }

    public boolean getZero() {
        return this.zero;
    }
    public byte getResult() {
        return this.result;
    }
}
