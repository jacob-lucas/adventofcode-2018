package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.common.Instruction;
import com.jacoblucas.adventofcode2018.common.InstructionPointer;
import com.jacoblucas.adventofcode2018.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.jacoblucas.adventofcode2018.common.Opcode.opcodes;
import static com.jacoblucas.adventofcode2018.common.Register.registerValues;

public class Day19 {

    static List<Instruction> parseInstructions(final Iterator<String> iterator) {
        final List<Instruction> instructions = new ArrayList<>();
        iterator.forEachRemaining(s -> instructions.add(Instruction.parse(s.split(" "))));
        return instructions;
    }

    static void execute(final InstructionPointer ip, final List<Instruction> instructions) {
        while (true) {
            // write value to ip register
            ip.getRegister().setValue(ip.getValue());
            final Instruction instruction = instructions.get(ip.getValue());

            // execute the instruction
            final Instruction toExecute = instructions.get(ip.getValue());
            opcodes[toExecute.getOpcode()].apply(toExecute);

            // copy back to ip
            ip.setValue(ip.getRegister().getValue());

            // add 1 to the instruction pointer
            final int nextVal = ip.getValue() + 1;
            if (nextVal >= instructions.size()) {
                break;
            } else {
                ip.setValue(nextVal);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        final Iterator<String> iterator = Utils.read("day19.txt").iterator();
        final String instructionPtrStr = iterator.next();

        final InstructionPointer ip = InstructionPointer.parse(instructionPtrStr);
        final List<Instruction> instructions = parseInstructions(iterator);

        execute(ip, instructions);
        System.out.println(Arrays.toString(registerValues()));
    }

}
