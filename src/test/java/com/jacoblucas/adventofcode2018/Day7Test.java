package com.jacoblucas.adventofcode2018;

import org.junit.Test;

import java.util.stream.Stream;

import static com.jacoblucas.adventofcode2018.Day7.parse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day7Test {

    private final Stream<String> instructions = Stream.of(
            "Step C must be finished before step A can begin.",
            "Step C must be finished before step F can begin.",
            "Step A must be finished before step B can begin.",
            "Step A must be finished before step D can begin.",
            "Step B must be finished before step E can begin.",
            "Step D must be finished before step E can begin.",
            "Step F must be finished before step E can begin."
    );

    @Test
    public void testStepOrder() {
        assertThat(Day7.stepOrder(parse(instructions)), is("CABDFE"));
    }
}
