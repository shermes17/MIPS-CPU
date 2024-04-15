package project2.models;

/*
    ALU Should output results of different arithmetic operations
 */
public class ALU {

    private int result;

    private int and;
    private int or;
    private int add;
    private int sub;
    private int slt;


    public ALU() {
        this.result = 0;
    }

    public int arithmetic(int reg1, int reg2, int ALUControlInput) {
        int type;
        switch (ALUControlInput) {
            case 0b0000: // AND
                result = reg1 & reg2;
                and++;
                type = 0;
                break;
            case 0b0001: // OR
                result = reg1 | reg2;
                or++;
                type = 1;
                break;
            case 0b0010: // add
                result = reg1 + reg2;
                add++;
                type = 2;
                break;
            case 0b0110: // subtract
                result = reg1 - reg2;
                sub++;
                type = 3;
                break;
            case 0b0111: // set on less than
                slt++;
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

        return result;
    }


    public int getResult() {
        return this.result;
    }

    /*
    Allow scoreboard to count how many of each operation have been executed
     */
    public int getAnd(){return this.and;}
    public int getOr(){return this.or;}
    public int getAdd(){return this.add;}
    public int getSub(){return this.sub;}
    public int getSlt(){return this.slt;}

}
