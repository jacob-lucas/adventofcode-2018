package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.Day16.Instruction;
import com.jacoblucas.adventofcode2018.Day16.Sample;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.jacoblucas.adventofcode2018.Day16.Opcode;
import static com.jacoblucas.adventofcode2018.Day16.opcodes;
import static com.jacoblucas.adventofcode2018.Day16.r0;
import static com.jacoblucas.adventofcode2018.Day16.r1;
import static com.jacoblucas.adventofcode2018.Day16.r2;
import static com.jacoblucas.adventofcode2018.Day16.r3;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day16Test {

    @Test
    public void testParseSample() {
        Sample sample = Sample.parse("Before: [3, 2, 1, 1]", "9 2 1 2", "After:  [3, 2, 2, 1]");
        assertThat(sample.getBeforeRegisterValues(), is(new int[]{3,2,1,1}));
        assertThat(sample.getInstruction(), is(new Instruction(9, 2, 1, 2)));
        assertThat(sample.getAfterRegisterValues(), is(new int[]{3,2,2,1}));
    }

    @Test
    public void testOpcodes() {
        final int[] before = {3, 2, 1, 1};
        final Instruction instruction = new Instruction(9, 2, 1 ,2);

        final Map<Opcode, int[]> expected = new HashMap<>();
        expected.put(opcodes[0], new int[]{3, 2, 3, 1}); // addr
        expected.put(opcodes[1], new int[]{3, 2, 2, 1}); // addi
        expected.put(opcodes[2], new int[]{3, 2, 2, 1}); // mulr
        expected.put(opcodes[3], new int[]{3, 2, 1, 1}); // muli
        expected.put(opcodes[4], new int[]{3, 2, 0, 1}); // banr
        expected.put(opcodes[5], new int[]{3, 2, 1, 1}); // bani
        expected.put(opcodes[6], new int[]{3, 2, 3, 1}); // borr
        expected.put(opcodes[7], new int[]{3, 2, 1, 1}); // bori
        expected.put(opcodes[8], new int[]{3, 2, 1, 1}); // setr
        expected.put(opcodes[9], new int[]{3, 2, 2, 1}); // seti
        expected.put(opcodes[10], new int[]{3, 2, 0, 1}); // gtir
        expected.put(opcodes[11], new int[]{3, 2, 0, 1}); // gtri
        expected.put(opcodes[12], new int[]{3, 2, 0, 1}); // gtrr
        expected.put(opcodes[13], new int[]{3, 2, 1, 1}); // eqir
        expected.put(opcodes[14], new int[]{3, 2, 1, 1}); // eqri
        expected.put(opcodes[15], new int[]{3, 2, 0, 1}); // eqrr

        for (Map.Entry<Opcode, int[]> e : expected.entrySet()) {
            r0.setValue(before[0]);
            r1.setValue(before[1]);
            r2.setValue(before[2]);
            r3.setValue(before[3]);
            final Opcode opcode = e.getKey();
            assertThat(opcode.name() + " did not produce the correct output", opcode.apply(instruction), is(e.getValue()));
        }
    }

    @Test
    public void testFindMatchingOpcodes() {
        final Sample sample = Sample.parse("Before: [3, 2, 1, 1]", "9 2 1 2", "After:  [3, 2, 2, 1]");
        assertThat(sample.findMatchingOpcodes(), hasItems("mulr", "addi", "seti"));
    }

}
