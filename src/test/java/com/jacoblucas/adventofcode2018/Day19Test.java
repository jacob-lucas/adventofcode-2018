package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.common.Instruction;
import com.jacoblucas.adventofcode2018.common.InstructionPointer;
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static com.jacoblucas.adventofcode2018.Day19.execute;
import static com.jacoblucas.adventofcode2018.Day19.parseInstructions;
import static com.jacoblucas.adventofcode2018.common.Register.registerValues;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day19Test {

    @Test
    public void testExecuteInstructions() {
        final InstructionPointer ip = new InstructionPointer(0, 0);
        final List<Instruction> instructions = parseInstructions(Stream.of(
                "seti 5 0 1",
                "seti 6 0 2",
                "addi 0 1 0",
                "addr 1 2 3",
                "setr 1 0 0",
                "seti 8 0 4",
                "seti 9 0 5"
        ).iterator());

        execute(ip, instructions);
        assertThat(registerValues()[0], is(6));
    }

}
