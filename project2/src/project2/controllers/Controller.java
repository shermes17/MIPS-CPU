package project2.controllers;

import project2.models.*;
import project2.views.*;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Controller {

    private PC pc;

    private Memory memory;

    private Registers registers;

    private ScoreBoard view;
    private Control control;

    private ALUControl aluControl;
    private ALU alu;




    public Controller(ScoreBoard view, String fileName) throws IOException {
        this.view = view;
        this.pc = new PC();
        this.memory = new Memory();
        this.registers = new Registers();
        this.control = new Control();
        this.alu = new ALU();
        this.aluControl = new ALUControl();

        int num =  (int) (Files.size(Paths.get(fileName))/4);
        memory.setNumInstructions(num);
        byte[] data = Files.readAllBytes(Paths.get(fileName));

        int ins = 0;
        int address = 0;
        for (int i = 0; i < data.length; i++) {
            // Shift the current contents of ins 8 bits to the left and add the new byte
            ins = (ins << 8) | (data[i] & 0xFF); // Mask the byte to prevent sign extension issues

            // Every four bytes, we have a complete instruction
            if ((i + 1) % 4 == 0) {
                memory.setInstruction(address, ins);
                address += 4; // Advance address to the next instruction location
                ins = 0; // Reset ins for the next instruction
            }
        }


    }
    public void runProcessor(){


        // figure out better loop conditions
        while(pc.getAddress() < memory.getNumInstructions() * 4){

            // get current instruct and execute
            Instruction instruction = memory.getInstruction(pc.getAddress());

            control.opcodeDecode(instruction.getOpcode());
            List<ControlSignal> sigs = control.getActiveSignals();

            switch (instruction.getType()){
                case 'R':
                    int rs = registers.getRegister(instruction.getRs());
                    int rt = registers.getRegister(instruction.getRt());
                    int rd = instruction.getRd();

                    aluControl.buildInput(instruction.getFunct(),control);
                    int result = alu.arithmetic(rs,rt, aluControl.getInput());

                    registers.writeRegisterValue(rd,result);
                    break;
                case 'I':
                    int rs = registers.getRegister(instruction.getRs());
                    int rt = registers.getRegister(instruction.getRt());
                    int imm = instruction.getImm();

                    aluControl.buildInput(instruction.getFunct(),control);
                    int result = alu.arithmetic(rs,rt, aluControl.getInput());

                    break;
                case 'J':
                    int address = instruction.getAddress();
                    if(0 <= address && address < 256){
                        pc.setAddress(address);
                    }
                    else{
                        throw new IllegalArgumentException("Segmentation Fault");
                    }
                    break;
                default
                    break;

            }


            // iterate to next instruction
            pc.setAddress(pc.getAddress() + 4);
        }

    }
}
