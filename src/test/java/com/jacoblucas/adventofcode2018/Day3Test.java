package com.jacoblucas.adventofcode2018;

import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class Day3Test {

    @Test
    public void testClaimParse() {
        final Day3.Claim claim = Day3.Claim.parse("#123 @ 3,2: 5x4").get();
        assertThat(claim, is(notNullValue()));
        assertThat(claim.getId(), is(123));
        assertThat(claim.getLeftBorderInches(), is(3));
        assertThat(claim.getTopBorderInches(), is(2));
        assertThat(claim.getWidth(), is(5));
        assertThat(claim.getHeight(), is(4));
    }

    @Test
    public void testGetOverlappingSquareInches() {
        final Stream<Day3.Claim> claims =
                Stream.of(
                        "#1 @ 1,3: 4x4",
                        "#2 @ 3,1: 4x4",
                        "#3 @ 5,5: 2x2")
                .map(Day3.Claim::parse)
                .map(Optional::get);

        assertThat(Day3.getOverlappingSquareInches(claims), is(4L));
    }

}
