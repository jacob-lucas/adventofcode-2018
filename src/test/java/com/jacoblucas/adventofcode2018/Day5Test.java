package com.jacoblucas.adventofcode2018;

import org.junit.Test;

import java.util.Iterator;

import static com.jacoblucas.adventofcode2018.Day5.Unit.parse;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day5Test {

    @Test
    public void testUnitParse() {
        final Iterator<Day5.Unit> unitList = parse("aAbB").iterator();

        assertThat(unitList.next().getType(), is('a'));
        assertThat(unitList.next().getType(), is('A'));
        assertThat(unitList.next().getType(), is('b'));
        assertThat(unitList.next().getType(), is('B'));
    }

    @Test
    public void testReact() {
        assertThat(Day5.react(parse("")), is(false));
        assertThat(Day5.react(parse("aA")), is(true));
        assertThat(Day5.react(parse("abBA")), is(true));
        assertThat(Day5.react(parse("abAB")), is(false));
        assertThat(Day5.react(parse("aabAAB")), is(false));
        assertThat(Day5.react(parse("dabAcCaCBAcCcaDA")), is(true));
    }

    @Test
    public void testFullyReact() {
        assertThat(Day5.fullyReact(parse("dabAcCaCBAcCcaDA")), is("dabCBAcaDA"));
    }

    @Test
    public void testOptimalFullyReact() {
        assertThat(Day5.optimalFullyReact(parse("dabAcCaCBAcCcaDA")), is("daDA"));
    }
}
