package com.jacoblucas.adventofcode2018.utils;

import org.junit.Test;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class UtilsTest {

    @Test
    public void testRead() throws IOException {
        Stream<String> lines = Utils.read("test.txt");

        assertThat(lines, is(notNullValue()));
        assertThat(lines.collect(Collectors.toList()).size(), is(5));
    }

}
