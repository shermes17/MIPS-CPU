# CPSC 3300 Project 2 (CPU Simulator)
##### Sean Hermes and Benjamin Brown

## Overview
This project is supposed to simulate a basic CPU using the MIPS architecture. It supports the 9 instructions that were given to us by Dr. Zhang. (lw, sw,beq, jmp, sub, and, or, addi, slt). The program keeps track of statistics from the operations it completes, and is able to display a comprehensive view to the user.
## MVC Design
### Model
We broke our model up into multiple components.
Our models are designed to be independant, 
and we tried not to have a model reference another model
unless absolutely necessary. The only times models reference each other are in the 
Control/ControlSignal/ALU/ALUControl files. Otherwise, the controller is the only
part of the application that interacts with the models

### View
Our View is designed to store and manipulate no data. We tried to keep this part of the program
minimal. All it does is display the information given to it to the user.

### Controler
The driving force of our program is in the controller. This is where the simulated program runs through each stage of the instruction process.

## Running the program
We used a makefile in order to easily run our application. Use the "make run " command to run the application.
