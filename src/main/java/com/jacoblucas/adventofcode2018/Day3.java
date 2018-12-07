package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.common.Coordinate;
import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.Builder;
import lombok.Data;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {

    @Data
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
        return getFabric(claims)
                .values()
                .stream()
                .filter(v -> v.size() >= 2)
                .count();
    }

    public static Claim getNonOverlappingClaim(final Stream<Claim> claims) {
        final Map<Coordinate, Set<Claim>> fabric = getFabric(claims);

        final Set<Claim> allClaims = new HashSet<>();
        fabric.values().forEach(allClaims::addAll);

        return allClaims.stream()
                .map(claim -> {
                    boolean isolated = true;
                    for (int x = claim.leftBorderInches; x < claim.leftBorderInches + claim.width; x++) {
                        for (int y = claim.topBorderInches; y < claim.topBorderInches + claim.height; y++) {
                            isolated &= fabric.get(new Coordinate(x, y)).size() == 1;
                        }
                    }
                    return isolated ? claim : null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
                .get(0);
    }

    private static Map<Coordinate, Set<Claim>> getFabric(final Stream<Claim> claims) {
        final Map<Coordinate, Set<Claim>> fabric = new HashMap<>();

        claims.forEach(claim -> {
            for (int x = claim.leftBorderInches; x < claim.leftBorderInches + claim.width; x++) {
                for (int y = claim.topBorderInches; y < claim.topBorderInches + claim.height; y++) {
                    final Coordinate coordinate = new Coordinate(x, y);
                    final Set<Claim> ids = fabric.containsKey(coordinate) ? fabric.get(coordinate) : new HashSet<>();
                    ids.add(claim);
                    fabric.put(coordinate, ids);
                }
            }
        });

        return fabric;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(getOverlappingSquareInches(getInput()));
        System.out.println(getNonOverlappingClaim(getInput()));
    }

    private static Stream<Claim> getInput() throws IOException {
        return Utils.read("day3.txt")
                .map(Claim::parse)
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

}
