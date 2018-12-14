package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.Day10.Light;
import com.jacoblucas.adventofcode2018.Day10.Velocity;
import com.jacoblucas.adventofcode2018.common.Coordinate;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day10Test {

    private final List<Light> lights = Stream.of(
            "position=< 9,  1> velocity=< 0,  2>",
            "position=< 7,  0> velocity=<-1,  0>",
            "position=< 3, -2> velocity=<-1,  1>",
            "position=< 6, 10> velocity=<-2, -1>",
            "position=< 2, -4> velocity=< 2,  2>",
            "position=<-6, 10> velocity=< 2, -2>",
            "position=< 1,  8> velocity=< 1, -1>",
            "position=< 1,  7> velocity=< 1,  0>",
            "position=<-3, 11> velocity=< 1, -2>",
            "position=< 7,  6> velocity=<-1, -1>",
            "position=<-2,  3> velocity=< 1,  0>",
            "position=<-4,  3> velocity=< 2,  0>",
            "position=<10, -3> velocity=<-1,  1>",
            "position=< 5, 11> velocity=< 1, -2>",
            "position=< 4,  7> velocity=< 0, -1>",
            "position=< 8, -2> velocity=< 0,  1>",
            "position=<15,  0> velocity=<-2,  0>",
            "position=< 1,  6> velocity=< 1,  0>",
            "position=< 8,  9> velocity=< 0, -1>",
            "position=< 3,  3> velocity=<-1,  1>",
            "position=< 0,  5> velocity=< 0, -1>",
            "position=<-2,  2> velocity=< 2,  0>",
            "position=< 5, -2> velocity=< 1,  2>",
            "position=< 1,  4> velocity=< 2,  1>",
            "position=<-2,  7> velocity=< 2, -2>",
            "position=< 3,  6> velocity=<-1, -1>",
            "position=< 5,  0> velocity=< 1,  0>",
            "position=<-6,  0> velocity=< 2,  0>",
            "position=< 5,  9> velocity=< 1, -2>",
            "position=<14,  7> velocity=<-2,  0>",
            "position=<-3,  6> velocity=< 2, -1>")
            .map(Light::parse)
            .flatMap(Optional::stream)
            .collect(Collectors.toList());

    @Test
    public void testParse() {
        final Optional<Light> light = Light.parse("position=< 9,  1> velocity=< 0,  2>");

        assertThat(light.isPresent(), is(true));
        assertThat(light.get().getPosition(), is(new Coordinate(9, 1)));
        assertThat(light.get().getVelocity(), is(new Velocity(0, 2)));
    }

    @Test
    public void testTick() {
        final Light light = Light.parse("position=<-3, 11> velocity=< 1, -2>").get();

        light.tick();

        assertThat(light.getPosition(), is(new Coordinate(-2, 9)));
    }

    @Test
    public void testArea() {
        assertThat(Day10.area(lights), is(352L));
    }

    @Test
    public void testFastForwardTime() {
        Day10.fastForwardTime(lights);
    }

}
