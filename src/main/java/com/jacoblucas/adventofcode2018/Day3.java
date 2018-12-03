package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class Day3 {

    @Data
    static class Coordinate {
        @NonNull int x, y;
    }

    @Getter
    @Builder
    static class Claim {
        private final int id;
        private final int leftBorderInches;
        private final int topBorderInches;
        private final int width;
        private final int height;

        static Optional<Claim> parse(final String str) {
            try {
                final String[] parts = str.split(" ");
                final String[] borders = parts[2].split(",");
                final String[] dimensions = parts[3].split("x");
                return Optional.of(
                        Claim.builder()
                                .id(Integer.valueOf(parts[0].substring(1)))
                                .leftBorderInches(Integer.valueOf(borders[0]))
                                .topBorderInches(Integer.valueOf(borders[1].replace(":", "")))
                                .width(Integer.valueOf(dimensions[0]))
                                .height(Integer.valueOf(dimensions[1]))
                                .build());
            } catch (Exception e) {
                System.out.println("Unable to parse claim from string '" + str + "' - " + e);
                return Optional.empty();
            }
        }
    }

    public static long getOverlappingSquareInches(final Stream<Claim> claims) {
        final Map<Coordinate, Integer> fabric = new HashMap<>();

        claims.forEach(claim -> {
            for (int x = claim.leftBorderInches; x < claim.leftBorderInches + claim.width; x++) {
                for (int y = claim.topBorderInches; y < claim.topBorderInches + claim.height; y++) {
                    final Coordinate coordinate = new Coordinate(x, y);
                    if (fabric.containsKey(coordinate)) {
                        fabric.put(coordinate, fabric.get(coordinate) + 1);
                    } else {
                        fabric.put(coordinate, 1);
                    }
                }
            }
        });

        return fabric.values().stream().filter(v -> v >= 2).count();
    }

    public static void main(String[] args) throws IOException {
        final Stream<Claim> claims = Utils.read("day3.txt")
                .map(Claim::parse)
                .filter(Optional::isPresent)
                .map(Optional::get);

        System.out.println(getOverlappingSquareInches(claims));
    }

}
