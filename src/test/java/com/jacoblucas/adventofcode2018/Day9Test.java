package com.jacoblucas.adventofcode2018;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day9Test {

    @Test
    public void testGetHighScore() {
        assertThat(Day9.getHighScore(9, 25), is(32L));
        assertThat(Day9.getHighScore(10, 1618), is(8317L));
        assertThat(Day9.getHighScore(13, 7999), is(146373L));
        assertThat(Day9.getHighScore(17, 1104), is(2764L));
        assertThat(Day9.getHighScore(21, 6111), is(54718L));
        assertThat(Day9.getHighScore(30, 5807), is(37305L));
    }

}
