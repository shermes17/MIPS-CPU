# Sean Hermes
# shermes@clemson.edu
# CPSC 3300
# Project 1

# Please include a an input file into the same directory as this source code
# I ran this program with this command
#       python3 project1.py abc.s output.dat
#
# If there are errors they will be printed to the command line
# Since the data that is written to output.dat and not readable please use
# the command, xxd -b -c 4 output.dat , to view the output

import sys

OPCODES = { 'lw'  : '100011',
            'sw'  : '101011',
            'beq' : '000100',
            'j'   : '000010',
            'addi': '001000'
            }

FUNCTS = {  'add' : '100000',
            'sub' : '100010',
            'and' : '100100',
            'or'  : '100101',
            'slt' : '101010'
            }



def write_R_Type(output_file, op, rs, rt,rd, shamt, funct):
    # create a string of 32 bits
    binstr = op + rs + rt + rd + shamt + funct
    # convert bit string to bytes
    bytes = int(binstr, 2).to_bytes(4, byteorder = 'big')
    # write bytes to output file
    output_file.write(bytes)

def write_I_Type(output_file, op, rt, rs, imm ):
    # create a string of 32 bits
    binstr = op + rt + rs + imm
    # convert bit string to bytes
    bytes = int(binstr, 2).to_bytes(4, byteorder= 'big')
    # write bytes to output file
    output_file.write(bytes)

def write_J_Type(output_file, op, address):
    # create a string of 32 bits
    binstr = op + address
    # convert bit string to bytes
    byte_array = int(binstr, 2).to_bytes(4, byteorder = 'big')
    # write bytes to output file
    output_file.write(byte_array)

def parse_MIPS_line(line,output_file, i):
    # parse each line into a string of binary
    # break up instructions into a list at each ' '
    parts = line.split(' ')
    registers = []
    #opcode will be first part
    opcode = parts[0]
    if (opcode not in OPCODES and  opcode not in FUNCTS): # ERROR CHECKING
        print("ERROR on line " + str(i) + ": "+ "Unsupported instruction")
        return
    parts = parts[1:]

    if(opcode == 'j'): # J-Type
        # address is second part
        address = int(parts[0])
        if (address < (-2**25) or address > (2**25) - 1): # ERROR CHECKING
            print("ERROR on line " + str(i) + ": "+ "address is out of range")
            return
        # format into a binary string
        address = format(address, '026b')
        # write to ouput file
        write_J_Type(output_file, OPCODES[opcode], address)
    elif (opcode == 'sw' or opcode == 'lw'): # I-Type sw and lw
        # registers should start with '$'
        if parts[0][0] != '$': # ERROR CHECKING
            print("ERROR on line " + str(i) + ": "+ "Invalid sytax")
            return
        cur = ""
        for c in parts[0]:
            # remove excess chars
            if(c != '$' and c != ',' and c != '\n'):
                cur = cur + c
        # check range
        num = int(cur)
        if num < 0 or num > 31: # ERROR CHECKING
            print("ERROR on line " + str(i) + ": "+ "Register is out of range")
            return
        # add to list
        registers.append(num)
        parts = parts[-1:]

        # parse immediate with register
        imm = ""
        reg = ""
        offset = parts[0]
        i = 0
        # get imm
        while offset[i] != '(':
            imm += offset[i]
            i+=1
        i+=2 # skip "(#"
        # get register
        while offset[i] != ')':
            reg += offset[i]
            i+=1
        reg = int(reg)
        imm = int(imm)
        # check ranges
        if reg < 0 or reg > 31: # ERROR CHECKING
            print("ERROR on line " + str(i) + ": "+ "Register is out of range")
            return
        if (imm < (-2**15) or imm > (2**15) -1): # ERROR CHECKING
            print("ERROR on line " + str(i) + ": "+ "Immediate is out of range")
            return
        # convert to binary string
        if imm < 0:
            imm = format(imm & 0xFFFF, '016b')
        else:
            imm = format(imm, '016b')
        # add register to list
        registers.append(reg)
        # write to output
        write_I_Type(output_file,
                     OPCODES[opcode],
                     format(registers[1], '05b'),
                     format(registers[0], '05b'),
                     imm
                     )
    elif (opcode == 'beq'): # I-Type beq
        imm = int(parts[2])
        # check range
        if (imm < (-2 ** 15) or imm > (2**15) -1): # ERROR
            print("ERROR on line " + str(i) + ": "+ "Immediate is out of range")
            return
        # convert to binary string
        if imm < 0:
            imm = format(imm & 0xFFFF, '016b')
        else:
            imm = format(imm, '016b')
        parts = parts[:-1]

        # parse registers
        for part in parts:
            cur = ""
            # register starts with '$'
            if part[0] != '$': # ERROR
                print("ERROR on line " + str(i) + ": "+ "Invalid sytax")
                return
            # remove excess chars
            for c in part:
                if(c != '$' and c != ',' and c != '\n'):
                    cur = cur + c
            # check range
            num = int(cur)
            if num < 0 or num > 31: # ERROR
                print("ERROR on line " + str(i) + ": "+ "Register is out of range")
                return
            # add to list
            registers.append(num)
        # write to output
        write_I_Type(output_file,
                     OPCODES[opcode],
                     format(registers[0], '05b'),
                     format(registers[1], '05b'),
                     imm
                     )
    elif (opcode == 'addi'): # I-Type addi (added for project 2)
        imm = int(parts[2])
        # check range
        if (imm < (-2 ** 15) or imm > (2**15) -1): # ERROR
            print("ERROR on line " + str(i) + ": "+ "Immediate is out of range")
            return
        # convert to binary string
        if imm < 0:
            imm = format(imm & 0xFFFF, '016b')
        else:
            imm = format(imm, '016b')
        parts = parts[:-1]

        # parse registers
        for part in parts:
            cur = ""
            # register starts with '$'
            if part[0] != '$': # ERROR
                print("ERROR on line " + str(i) + ": "+ "Invalid sytax")
                return
            # remove excess chars
            for c in part:
                if(c != '$' and c != ',' and c != '\n'):
                    cur = cur + c
            # check range
            num = int(cur)
            if num < 0 or num > 31: # ERROR
                print("ERROR on line " + str(i) + ": "+ "Register is out of range")
                return
            # add to list
            registers.append(num)
        # write to output
        write_I_Type(output_file,
                     OPCODES[opcode],
                     format(registers[1], '05b'),
                     format(registers[0], '05b'),
                     imm
                     )
    else: # R-Type
        # loop through remaining registers
        for part in parts:
            cur = ""
            # reg starts with '$'
            if part[0] != '$': # ERROR CHECKING
                print("ERROR on line " + str(i) + ": "+ "Invalid syntax")
                return
            # remove excess chars
            for c in part:
                if(c != '$' and c != ',' and c != '\n'):
                    cur = cur + c
            # check range
            num = int(cur)
            if num < 0 or num > 31: # ERROR CHECKING
                print("ERROR on line " + str(i) + ": "+ "Register is out of range")
                return
            # add to list
            registers.append(num)
        # should have 3 registers
        if len(registers) != 3: # ERROR CHECKING
            print("ERROR on line " + str(i) + ": "+ "Invalid sytax")
            return
        # write to output
        write_R_Type(output_file,
                     '000000',
                     format(registers[1], '05b'),
                     format(registers[2], '05b'),
                     format(registers[0], '05b'),
                     '00000',
                     FUNCTS[opcode]
                     )

def main():
    # check command line arguments
    if len(sys.argv) != 3:
        print("Incorrect number of arguments\nproject1.py input_file.s output_file.dat")
        sys.exit(1)

    # get filenames form command line
    input_name = sys.argv[1]
    output_name = sys.argv[2]

    # open files
    input_file = open(input_name, "r")
    output_file = open(output_name, "wb")

    # traverse through each line of the file, treat each line as a string
    line = input_file.readline()
    i = 1
    while line:
        parse_MIPS_line(line,output_file, i) # parse mips code, then translate and write to output
        i+=1
        line = input_file.readline()

    # close files
    input_file.close()
    output_file.close()

if __name__ == "__main__":
    main()
        
