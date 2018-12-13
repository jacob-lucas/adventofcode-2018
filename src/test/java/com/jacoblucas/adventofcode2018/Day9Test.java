package com.jacoblucas.adventofcode2018;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day9Test {

    @Test
    public void testGetHighScore() {
        assertThat(Day9.getHighScore(9, 25), is(32));
        assertThat(Day9.getHighScore(10, 1618), is(8317));
        assertThat(Day9.getHighScore(13, 7999), is(146373));
        assertThat(Day9.getHighScore(17, 1104), is(2764));
        assertThat(Day9.getHighScore(21, 6111), is(54718));
        assertThat(Day9.getHighScore(30, 5807), is(37305));
    }

}
