package project2.models;

/*
    Should generate ALU Control signals
 */
public class ALUControl {

    private int Input;


    public ALUControl() {
        this.Input = 0;
    }

    public void buildInput(int funct, Control control) {
        switch (control.getALUop()) {
            case 0b00: // lw & sw & addi
                Input = 0b0010; // add
                break;
            case 0b01: // beq
                Input = 0b0110; // sub
                break;
            case 0b11: // not used
                break;
            case 0b10:
                switch (funct) {
                    case 0b100000:
                        Input = 0b0010; // add
                        break;
                    case 0b100010:
                        Input = 0b0110; // sub
                        break;
                    case 0b100100:
                        Input = 0b0000; // and
                        break;
                    case 0b100101:
                        Input = 0b0001; // or
                        break;
                    case 0b101010:
                        Input = 0b0111; // set on less than
                        break;
                    default:
                        break;
                }
                break;
            default:
                System.err.println("Error: Invalid ALU Op");
                break;
        }
    }

    public int getInput() {
        return Input;
    }

}
