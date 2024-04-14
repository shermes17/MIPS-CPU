package project2.models;

public class ALUControl {

    private int ALUInput;

    public ALUControl() {
        Input = 0;
    }

    public void buildInput(String funct, Control control) {

        int ALUOp;
        if (control.getALUOp1() == true && control.getALUOp0() == true) {
            ALUOp = 0b11;
        } else if (control.getALUOp1() == true) {
            ALUOp = 0b10;
        } else if (control.getALUOp0() == true) {
            ALUOp = 0b01;
        } else
            ALUOp = 0b00;

        switch (ALUOp) {
            case 0b00:
                Input = 0b0010; // add
                break;
            case 0b01:
                Input = 0b0110; // sub
                break;
            case 0b10:
            case 0b11:

                switch (funct) {
                    case "100000":
                        Input = 0b0010; // add
                        break;
                    case "100010":
                        Input = 0b0110; // sub
                        break;
                    case "100100":
                        Input = 0b0000; // and
                        break;
                    case "100101":
                        Input = 0b0001; // or
                        break;
                    case "101010":
                        Input = 0b0111; // set on less than
                        break;
                }
                break;
            default:
                System.err.println("Error: Invalid ALU Op);
        }
    }

    public int getInput() {
        return Input;
    }

    public void reset() {
        ALUInput = 0;
    }
}
