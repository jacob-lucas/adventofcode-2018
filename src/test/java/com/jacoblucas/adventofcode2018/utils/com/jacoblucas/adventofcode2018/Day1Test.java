package com.jacoblucas.adventofcode2018.utils.com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.Day1;
import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day1Test {

    @Test
    public void testFrequency() {
        assertThat(Day1.frequency(Stream.of(1, -2, 3, 1)), is(3));
    }

    @Test
    public void testFirstDuplicateFrequency() {
        assertThat(Day1.findFirstDuplicateFrequency(Stream.of(1, -2, 3, 1)), is(2));
    }

}
