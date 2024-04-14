package project2.models;

public class Memory {
    private byte[] dataMemory;
    private byte[] instructionMemory;

    private int numInstructions;

    public Memory() {

        this.dataMemory = new byte[256];
        this.instructionMemory = new byte[256];


        for (int i = 0; i < 256; i++) {
            dataMemory[i] = (byte) 0x00;
            instructionMemory[i] = (byte) 0x00;
        }

    }

    public void setData(int address, byte value) {
        dataMemory[address] = value;
    }

    public byte getData(int address) {
        return dataMemory[address];
    }

    public void setInstruction(int address, int value) {
        byte p1 = (byte) (value >>> 24 & 0xFF);
        byte p2 = (byte) (value >>> 16 & 0xFF);
        byte p3 = (byte) (value >>> 8 & 0xFF);
        byte p4 = (byte) (value & 0xFF);

        instructionMemory[address] = p1;
        instructionMemory[address+1] = p2;
        instructionMemory[address+2] = p3;
        instructionMemory[address+3] = p4;
        }

    public Instruction getInstruction(int address) {
        Instruction instruction;

        int ins = (instructionMemory[address] & 0xFF) << 24;
        ins += (instructionMemory[address + 1] & 0xFF) << 16;
        ins += (instructionMemory[address + 2] & 0xFF) << 8;
        ins +=  (instructionMemory[address + 3] & 0xFF);

        int op = ins >>> 26;

        if (op == 0x00){ //R Type
            instruction = new RType(ins);
        }
        else if(op == 0x02){ // J Type
            instruction = new JType(ins);
        }
        else{ // I Type
            instruction = new IType(ins);
        }
        return instruction;
    }

    public void setNumInstructions(int num){this.numInstructions = num;}
    public int getNumInstructions(){return this.numInstructions;}


}
