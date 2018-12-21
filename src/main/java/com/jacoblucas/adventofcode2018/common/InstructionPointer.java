package com.jacoblucas.adventofcode2018.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static com.jacoblucas.adventofcode2018.common.Register.registers;

@EqualsAndHashCode
public class InstructionPointer {

    @Getter private final Register register;
    @Getter @Setter private int value;

    public InstructionPointer(final int registerId, final int value) {
        this.register = registers[registerId];
        this.value = value;
    }

    public static InstructionPointer parse(final String str) {
        final String[] parts = str.split(" ");
        return new InstructionPointer(Integer.valueOf(parts[1]), 0);
    }

}
