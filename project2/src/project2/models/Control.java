package project2.models;

import java.util.EnumMap;
import java.util.List;
import java.util.ArrayList;

public class Control {
    // Define control signals with enumerations

    private int ALUop;

    // map that stores current status of each control signal
    private EnumMap<ControlSignal, Boolean> controlSignals;

    public Control() {
        controlSignals = new EnumMap<>(ControlSignal.class);
        reset();
    }
    //For each signal, function sets appropriate opcodes to true by calling setSignal
    public void opcodeDecode(int opcode) {
        reset(); // Reset control signals before decoding new opcode
        switch (opcode) {
            case 0b000000: // arithmetic/R-format
                setSignals(ControlSignal.REG_DST, ControlSignal.REG_WRITE, ControlSignal.ALU_OP1);
                ALUop = 0b10; // 10
                break;
            case 0b000010: // j
                setSignals(ControlSignal.JUMP);
                break;
            case 0b000100: // beq
                setSignals(ControlSignal.BRANCH, ControlSignal.ALU_OP0);
                ALUop = 0b01; // 01
                break;
            case 0b100011: // lw (Corrected opcode for lw)
                setSignals(ControlSignal.ALU_SRC, ControlSignal.MEM_TO_REG, ControlSignal.REG_WRITE, ControlSignal.MEM_READ);
                ALUop = 0b00;
                break;
            case 101011: // sw
                setSignals(ControlSignal.ALU_SRC, ControlSignal.MEM_WRITE);
                ALUop = 0b00;
                break;
            default :
                break;
        }
    }
    //Set given signal to true
    private void setSignals(ControlSignal... signals) {
        for (ControlSignal signal : signals) {
            controlSignals.put(signal, true);
        }
    }
    // map that stores current status of each control signal



    //reset signals to false
    public void reset() {
        for (ControlSignal signal : ControlSignal.values()) {
            controlSignals.put(signal, false);
        }
    }


    public List<ControlSignal> getActiveSignals() {
        List<ControlSignal> activeSignals = new ArrayList<>();
        for (ControlSignal signal : ControlSignal.values()) {
            if (controlSignals.getOrDefault(signal, false)) { // Check if each signal is true
                activeSignals.add(signal);
            }
        }
        return activeSignals;
    }

    public int getALUop(){return this.ALUop;}
}


