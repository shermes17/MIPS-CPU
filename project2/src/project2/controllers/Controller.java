/**
 * This file controls the flow of the cpu
 * The controller with cycle through the instructions calling the appropriate
 * logical blocks for the instructions
 *
 *
 */
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
    private int cycles;




    public Controller(Scoreboard view, String fileName) throws IOException {
        this.view = view;
        this.pc = new PC();
        this.memory = new Memory();
        this.registers = new Registers();
        this.control = new Control();
        this.alu = new ALU();
        this.aluControl = new ALUControl();
        this.cycles = 0;


        // get data from output.dat file
        int num =  (int) (Files.size(Paths.get(fileName))/4);
        byte[] data = Files.readAllBytes(Paths.get(fileName));

        memory.setNumInstructions(num);


        int ins = 0;
        int address = 0;
        for (int i = 0; i < data.length; i++) {
            // add bytes to int
            ins = (ins << 8) | (data[i] & 0xFF);

            // Every four bytes, add the 32 bit int to memory
            if ((i + 1) % 4 == 0) {
                memory.setInstruction(address, ins);
                address += 4; // offset of 4 bytes
                ins = 0;
            }
        }


    }
    public void runProcessor(){


        // figure out better loop conditions
        while(cycles <= memory.getNumInstructions()){

            // get current instruction to get type
            Instruction ins =  memory.getInstruction(pc.getAddress());
            RType rIns;
            IType iIns;
            JType jIns;


            int rs, rt, rd, imm, result, address;
            switch (ins.getType()){
                case 'R':
                    rIns = (RType) memory.getInstruction(pc.getAddress());
                    control.opcodeDecode(rIns.getOpcode());
                    // get registers values
                     rs = registers.readRegisterValue(rIns.getRS());
                     rt = registers.readRegisterValue(rIns.getRS());
                     rd = rIns.getRD();
                     if(rIns.getRS() < 0 ||rIns.getRS() > 31 ||rIns.getRS() < 0 || rIns.getRS() > 31 || rd < 0 || rd > 31)
                         throw new IllegalArgumentException("Invalid Register");
                     // send data to alu to compute intruction
                     aluControl.buildInput(rIns.getFunct(),control);
                     result = alu.arithmetic(rs,rt, aluControl.getInput());

                     // write output
                    registers.writeRegisterValue(rd,result);
                    break;
                case 'I':
                    iIns = (IType) memory.getInstruction(pc.getAddress());
                    control.opcodeDecode(iIns.getOpcode());
                    // get instruction data
                    rs = registers.readRegisterValue(iIns.getRS());
                    rt = registers.readRegisterValue(iIns.getRT());
                    imm = iIns.getImm();

                    if(rIns.getRS() < 0 ||rIns.getRS() > 31 ||rIns.getRS() < 0 || rIns.getRS() > 31)
                        throw new IllegalArgumentException("Invalid Register");
                     if (imm >= Short.MIN_VALUE && imm <= Short.MAX_VALUE)
                         throw new IllegalArgumentException("Invalid Immediate");

                     // send instruction to alu to compute output
                    aluControl.buildInput(0b00000,control);
                    result = alu.arithmetic(rs,imm, aluControl.getInput());

                switch(iIns.getOpcode()){
                    case 0b000100: //beq
                        if(rs == rt){
                            pc.setAddress(pc.getAddress() + imm - 4); //Program counter should not be iterated on beq
                        }
                        break;
                    case 0b001000: //addi
                        registers.writeRegisterValue(iIns.getRT(), result);
                        break;
                    default: // lw or sw
                        imm = (imm << 16) >> 16; // sign extend from 16 to 32 bits
                        address = rs + imm;
                        result = memory.getData(address);
                        if(iIns.getOpcode() == 0b100011){ //lw
                            registers.writeRegisterValue(rt,result); // load to register
                        }
                        else{ //sw
                            memory.setData(address,(byte)rt); // write to memory
                        }
                        break;


                }
                    break;
                case 'J':
                    jIns = (JType) memory.getInstruction(pc.getAddress());
                    control.opcodeDecode(jIns.getOpcode());
                    address = jIns.getAddress();
                    if(0 <= address && address < 64){
                        pc.setAddress(address - 4); //Program counter should not be iterated on jmp
                    }
                    else{
                        throw new IllegalArgumentException("Segmentation Fault");
                    }
                    break;
                default:
                    break;

            }

            // iterate to next instruction
            cycles++;
            pc.setAddress(pc.getAddress() + 4);

            // send model data to view to print the score board
            view.displayBoard(cycles, cycles,pc.getAddress());
            view.displayALUops(alu.getAnd(),alu.getOr(), alu.getAdd(), alu.getSub(), alu.getSlt());
            view.displayMemoryOps(memory.getReads(), memory.getWrites());
            view.displayRegisterOps(registers.getReads(), registers.getWrites());
            view.displayRegisters(registers.getRegisters());
            view.displayMemory(memory.getMemory());


        }

    }
}
