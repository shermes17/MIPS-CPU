package project2.controllers;

import project2.models.*;
import project2.views.*;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.EnumMap;
import java.util.List;
import java.util.ArrayList;

public class Controller {

    private PC pc;

    private Memory memory;

    private Registers registers;

    private Scoreboard view;
    private Control control;

    private ALUControl aluControl;
    private ALU alu;




    public Controller(Scoreboard view, String fileName) throws IOException {
        this.view = view;
        this.pc = new PC();
        this.memory = new Memory();
        this.registers = new Registers();
        this.control = new Control();
        this.alu = new ALU();
        this.aluControl = new ALUControl();



        InputStream inputStream = new FileInputStream(fileName);
        int num = (int) new File(fileName).length();

        // Read binary file data
        byte[] data = new byte[ num];

        memory.setNumInstructions(num);


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
            Instruction ins =  memory.getInstruction(pc.getAddress());
            RType rIns;
            IType iIns;
            JType jIns;


            int rs, rt, rd, imm, result, address;
            switch (ins.getType()){
                case 'R':
                    rIns = (RType) memory.getInstruction(pc.getAddress());
                    control.opcodeDecode(rIns.getOpcode());
                     rs = registers.readRegisterValue(rIns.getRS());
                     rt = registers.readRegisterValue(rIns.getRT());
                     rd = rIns.getRD();

                    aluControl.buildInput(rIns.getFunct(),control);
                     result = alu.arithmetic(rs,rt, aluControl.getInput());

                    registers.writeRegisterValue(rd,result);
                    break;
                case 'I':
                    iIns = (IType) memory.getInstruction(pc.getAddress());
                    control.opcodeDecode(iIns.getOpcode());
                     rs = registers.readRegisterValue(iIns.getRS());
                     rt = registers.readRegisterValue(iIns.getRT());
                     imm = iIns.getImm();

                    result = alu.arithmetic(rs,rt, aluControl.getInput());

                switch(iIns.getOpcode()){
                    case 0b000100: //beq
                        if(rs == rt){
                            pc.setAddress(pc.getAddress() + imm);
                        }
                        break;
                    case 0b001000: //addi
                        registers.writeRegisterValue(rt, result);
                        break;
                    default: // lw or sw
                        imm = (imm << 16) >> 16;
                        address = rs + imm;
                        result = memory.getData(address);
                        if(iIns.getOpcode() == 0b100011){ //lw
                            registers.writeRegisterValue(rt,result);
                        }
                        else{ //sw
                            memory.setData(address,(byte)rt);
                        }

                        break;


                }

                    break;
                case 'J':
                    jIns = (JType) memory.getInstruction(pc.getAddress());
                    control.opcodeDecode(jIns.getOpcode());
                    address = jIns.getAddress();
                    if(0 <= address && address < 256){
                        pc.setAddress(address);
                    }
                    else{
                        throw new IllegalArgumentException("Segmentation Fault");
                    }
                    break;
                default:
                    break;

            }


            // iterate to next instruction
            pc.setAddress(pc.getAddress() + 4);
        }

    }
}
