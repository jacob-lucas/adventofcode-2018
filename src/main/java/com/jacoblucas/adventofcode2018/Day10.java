package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.common.Coordinate;
import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day10 {

    @Data
    @AllArgsConstructor
    static class Velocity {
        private int dx;
        private int dy;

        @Override
        public String toString() {
            return "(" + dx + "," + dy + ")";
        }
    }

    @Data
    @AllArgsConstructor
    static class Light {
        private Coordinate position;
        private Velocity velocity;

        static Optional<Light> parse(final String str) {
            try {
                final String [] parts = str.split("=");
                final String[] rawPosition = parts[1].split(">")[0].split(",");
                final String[] rawVelocity = parts[2].split(",");
                return Optional.of(new Light(
                        new Coordinate(
                                Integer.valueOf(rawPosition[0].replaceAll("<", "").trim()),
                                Integer.valueOf(rawPosition[1].trim())),
                        new Velocity(
                                Integer.valueOf(rawVelocity[0].replaceAll("<", "").trim()),
                                Integer.valueOf(rawVelocity[1].replaceAll(">", "").trim()))));
            } catch (Exception e) {
                return Optional.empty();
            }
        }

        public void tick() {
            position.setX(position.getX() + velocity.getDx());
            position.setY(position.getY() + velocity.getDy());
        }

        @Override
        public String toString() {
            return "position=" + position + " velocity=" + velocity;
        }
    }

    public static void fastForwardTime(final List<Light> lights) {
        int second = 0;
        while (true) {
            final List<Light> currentLights = new ArrayList<>();
            lights.forEach(l -> currentLights.add(new Light(new Coordinate(l.position.getX(), l.position.getY()), l.velocity)));
            final long currentArea = area(currentLights);

            // move time forward
            lights.forEach(Light::tick);

            if (area(lights) > currentArea) {
                // the lights are no longer converging on each other
                System.out.println("--- (t = " + (second) + ", area = " + currentArea + ") --------------------------------------");
                plot(currentLights);
                break;
            }
            second++;
        }
    }

    static long area(final List<Light> lights) {
        final Coordinate topLeft = getTopLeft(lights);
        final Coordinate bottomRight = getBottomRight(lights);
        return ((long)bottomRight.getX() - (long)topLeft.getX() + 1) * ((long)bottomRight.getY() - (long)topLeft.getY() + 1);
    }

    static void plot(final List<Light> lights) {
        final Coordinate topLeft = getTopLeft(lights);
        final Coordinate bottomRight = getBottomRight(lights);

        for (int y = topLeft.getY(); y <= bottomRight.getY(); y++) {
            final StringBuilder stringBuilder = new StringBuilder();
            for (int x = topLeft.getX(); x <= bottomRight.getX(); x++) {
                final int finalX = x;
                final int finalY = y;
                if (lights.stream().anyMatch(l -> l.position.equals(new Coordinate(finalX, finalY)))) {
                    stringBuilder.append("#");
                } else {
                    stringBuilder.append(".");
                }
            }
            System.out.println(stringBuilder.toString());
        }
    }

    private static Coordinate getTopLeft(final List<Light> lights) {
        final int x = lights.stream()
                .map(Light::getPosition)
                .mapToInt(Coordinate::getX)
                .min()
                .getAsInt();
        final int y = lights.stream()
                .map(Light::getPosition)
                .mapToInt(Coordinate::getY)
                .min()
                .getAsInt();

        return new Coordinate(x, y);
    }

    private static Coordinate getBottomRight(final List<Light> lights) {
        final int x = lights.stream()
                .map(Light::getPosition)
                .mapToInt(Coordinate::getX)
                .max()
                .getAsInt();
        final int y = lights.stream()
                .map(Light::getPosition)
                .mapToInt(Coordinate::getY)
                .max()
                .getAsInt();

        return new Coordinate(x, y);
    }

    public static void main(String[] args) throws IOException {
        final List<Light> lights = Utils.read("day10.txt")
                .map(Light::parse)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());

        // the second printed out with this is the solution for part 2
        fastForwardTime(lights);
    }

}
