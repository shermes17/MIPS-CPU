package project2.models;

public class Control {
    // Define control signals with enumerations
    enum ControlSignal {
        REG_DST, REG_WRITE, ALU_SRC, PC_SRC, MEM_READ, MEM_WRITE, MEM_TO_REG, BRANCH, ALU_OP1, ALU_OP0, JUMP
    }

    // map that stores current status of each control signal
    private EnumMap<ControlSignal, Boolean> controlSignals;

    public Control() {
        controlSignals = new EnumMap<>(ControlSignal.class);
        reset();
    }
    //For each signal, function sets appropriate opcodes to true by calling setSignal
    public void opcodeDecode(String opcodeString) {
        reset(); // Reset control signals before decoding new opcode
        switch (opcodeString) {
            case "000000": // arithmetic/R-format
                setSignals(ControlSignal.REG_DST, ControlSignal.REG_WRITE, ControlSignal.ALU_OP1);
                break;
            case "000010": // j
                setSignals(ControlSignal.JUMP);
                break;
            case "000100": // beq
                setSignals(ControlSignal.BRANCH, ControlSignal.ALU_OP0);
                break;
            case "100011": // lw (Corrected opcode for lw)
                setSignals(ControlSignal.ALU_SRC, ControlSignal.MEM_TO_REG, ControlSignal.REG_WRITE, ControlSignal.MEM_READ);
                break;
            case "101011": // sw
                setSignals(ControlSignal.ALU_SRC, ControlSignal.MEM_WRITE);
                break;
        }
    }
    //Set given signal to true
    private void setSignals(ControlSignal... signals) {
        for (ControlSignal signal : signals) {
            controlSignals.put(signal, true);
        }
    }

    //reset signals to false
    public void reset() {
        for (ControlSignal signal : ControlSignal.values()) {
            controlSignals.put(signal, false);
        }
    }
    public boolean getSignal(ControlSignal signal) {
        return controlSignals.getOrDefault(signal, false);
    }
}

