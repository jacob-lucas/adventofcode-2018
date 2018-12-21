package com.jacoblucas.adventofcode2018.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;

import static com.jacoblucas.adventofcode2018.common.Opcode.opcodes;

@Data
@AllArgsConstructor
public class Instruction {
    int opcode;
    int inputA;
    int inputB;
    int output;

    @Override
    public String toString() {
        return opcodes[opcode] + " A=" + inputA + " B=" + inputB + " Out=R" + output;
    }

    public static Instruction parse(final String instructionStr) {
        final int[] ip = Arrays.stream(instructionStr.split(" "))
                .mapToInt(Integer::valueOf)
                .toArray();
        return new Instruction(ip[0], ip[1], ip[2], ip[3]);
    }

    public static Instruction parse(final String[] parts) {
        final Opcode opcode = Opcode.get(parts[0]);
        return new Instruction(Arrays.asList(opcodes).indexOf(opcode), Integer.valueOf(parts[1]), Integer.valueOf(parts[2]), Integer.valueOf(parts[3]));
    }

}
