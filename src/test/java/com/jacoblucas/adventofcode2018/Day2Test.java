package com.jacoblucas.adventofcode2018;

import org.junit.Test;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day2Test {

    @Test
    public void testChecksum() {
        final Stream<String> boxIDs = Stream.of(
                "abcdef",
                "bababc",
                "abbcde",
                "abcccd",
                "aabcdd",
                "abcdee",
                "ababab");

        assertThat(Day2.checksum(boxIDs), is(12L));
    }

}
