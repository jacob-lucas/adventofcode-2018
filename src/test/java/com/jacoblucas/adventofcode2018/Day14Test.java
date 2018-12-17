package com.jacoblucas.adventofcode2018;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day14Test {

    @Test
    public void testIterate() {
        Day14.Iteration iteration = Day14.iterate("37", 0, 1);
        assertThat(iteration.getScores(), is("3710"));
        assertThat(iteration.getElf1Index(), is(0));
        assertThat(iteration.getElf2Index(), is(1));

        iteration = Day14.iterate(iteration.getScores(), iteration.getElf1Index(), iteration.getElf2Index());
        assertThat(iteration.getScores(), is("371010"));
        assertThat(iteration.getElf1Index(), is(4));
        assertThat(iteration.getElf2Index(), is(3));

        iteration = Day14.iterate(iteration.getScores(), iteration.getElf1Index(), iteration.getElf2Index());
        assertThat(iteration.getScores(), is("3710101"));
        assertThat(iteration.getElf1Index(), is(6));
        assertThat(iteration.getElf2Index(), is(4));

        iteration = Day14.iterate(iteration.getScores(), iteration.getElf1Index(), iteration.getElf2Index());
        assertThat(iteration.getScores(), is("37101012"));
        assertThat(iteration.getElf1Index(), is(0));
        assertThat(iteration.getElf2Index(), is(6));
    }

    @Test
    public void testTenScores() {
        assertThat(Day14.tenScoresAfter("37",5), is("0124515891"));
        assertThat(Day14.tenScoresAfter("37",9), is("5158916779"));
        assertThat(Day14.tenScoresAfter("37",18), is("9251071085"));
        assertThat(Day14.tenScoresAfter("37",2018), is("5941429882"));
    }

}
