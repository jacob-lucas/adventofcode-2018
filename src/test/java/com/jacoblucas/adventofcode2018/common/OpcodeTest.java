package com.jacoblucas.adventofcode2018.common;

import com.jacoblucas.adventofcode2018.common.Opcode.mulr;
import org.junit.Test;

import java.util.Arrays;

import static com.jacoblucas.adventofcode2018.common.Opcode.opcodes;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OpcodeTest {

    @Test
    public void testOpcodeEquivalence() {
        assertThat(new Opcode.addr(), is(new Opcode.addr()));
    }

    @Test
    public void testListLookup() {
        assertThat(Arrays.asList(opcodes).indexOf(new mulr()), is(2));
    }

}
