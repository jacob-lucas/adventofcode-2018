package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.common.Coordinate;
import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Day13 {

    static final Coordinate UP = new Coordinate(0, -1);
    static final Coordinate DOWN = new Coordinate(0, 1);
    static final Coordinate LEFT = new Coordinate(-1, 0);
    static final Coordinate RIGHT = new Coordinate(1, 0);

    @Data
    @RequiredArgsConstructor
    static class Cart {
        private UUID id = UUID.randomUUID();
        private long intersectionCount = 0;
        @NonNull private Coordinate facing;

        void turnLeft() {
            turn(RIGHT, LEFT, DOWN, UP);
        }

        void turnRight() {
            turn(LEFT, RIGHT, UP, DOWN);
        }

        private void turn(final Coordinate left, final Coordinate right, final Coordinate up, final Coordinate down) {
            if (facing == DOWN) {
                facing = left;
            } else if (facing == UP) {
                facing = right;
            } else if (facing == LEFT) {
                facing = up;
            } else {
                facing = down;
            }
        }

        public void turnAtIntersection() {
            final int choice = Math.floorMod(intersectionCount, 3);
            if (choice < 0 || choice > 2) {
                throw new RuntimeException("Cart intersection count error! " + this);
            }
            if (choice == 0) {
                turnLeft();
            } else if (choice == 2) {
                turnRight();
            }
            intersectionCount++;
        }
    }

    enum TrackType {
        VERTICAL,
        HORIZONTAL,
        CURVE_LEFT,
        CURVE_RIGHT,
        INTERSECTION,
        CRASH,
        EMPTY
    }

    @Getter
    @EqualsAndHashCode(callSuper = true, exclude = "cart")
    static class TrackCoordinate extends Coordinate {
        private TrackType trackType;
        private Cart cart;

        TrackCoordinate(final int x, final int y, final TrackType trackType, final Cart cart) {
            super(x, y);
            this.trackType = trackType;
            this.cart = cart;
        }

        static TrackCoordinate parse(final int x, final int y, final char ch) {
            TrackType trackType = null;
            Cart cart = null;

            if (ch == '|') {
                trackType = TrackType.VERTICAL;
            } else if (ch == '-') {
                trackType = TrackType.HORIZONTAL;
            } else if (ch == '\\') {
                trackType = TrackType.CURVE_LEFT;
            } else if (ch == '/') {
                trackType = TrackType.CURVE_RIGHT;
            } else if (ch == '+') {
                trackType = TrackType.INTERSECTION;
            } else if (ch == '^') {
                cart = new Cart(UP);
                trackType = TrackType.VERTICAL;
            } else if (ch == 'v') {
                cart = new Cart(DOWN);
                trackType = TrackType.VERTICAL;
            } else if (ch == '>') {
                cart = new Cart(RIGHT);
                trackType = TrackType.HORIZONTAL;
            } else if (ch == '<') {
                cart = new Cart(LEFT);
                trackType = TrackType.HORIZONTAL;
            } else if (ch == ' ') {
                trackType = TrackType.EMPTY;
            } else if (ch == 'X') {
                trackType = TrackType.CRASH;
            }

            return new TrackCoordinate(x, y, trackType, cart);
        }
    }

    static TrackCoordinate[][] parseTracks(final List<String> rawTrackInput) {
        final int maxY = rawTrackInput.size();
        final int maxX = rawTrackInput.get(0).length();
        final TrackCoordinate[][] tracks = new TrackCoordinate[maxY][maxX];

        for (int y = 0; y < maxY; y++) {
            final String line = rawTrackInput.get(y);
            for (int x = 0; x < line.length(); x++) {
                tracks[y][x] = TrackCoordinate.parse(x, y, line.charAt(x));
            }
        }

        return tracks;
    }

    public static TrackCoordinate findFirstCrashCoordinate(final TrackCoordinate[][] tracks) {
        int tick = 0;
        while (true) {
            tick(tracks);

            final Optional<TrackCoordinate> crash = Arrays.stream(tracks)
                    .flatMap(Arrays::stream)
                    .filter(c -> c.trackType == TrackType.CRASH)
                    .findFirst();

            if (crash.isPresent()) {
                printTracks(tick, tracks);
                return crash.get();
            } else {
                tick++;
            }
        }
    }

    static TrackCoordinate getCartDestinationLocation(final TrackCoordinate cartOriginalLocation, final TrackCoordinate[][] tracks) {
        final Coordinate facing = cartOriginalLocation.cart.facing;
        return tracks[cartOriginalLocation.getY() + facing.getY()][cartOriginalLocation.getX() + facing.getX()];
    }

    static void tick(final TrackCoordinate[][] tracks) {
        boolean crashDetected = false;
        final Set<Cart> movedCarts = new HashSet<>();
        for (int y = 0; y < tracks.length && !crashDetected; y++) {
            for (int x = 0; x < tracks[0].length; x++) {
                final TrackCoordinate coordinate = tracks[y][x];
                final Cart cart = coordinate.cart;
                if (cart != null && !movedCarts.contains(cart)) {
                    // move the cart
                    final TrackCoordinate destination = getCartDestinationLocation(coordinate, tracks);

                    if (destination.trackType == TrackType.EMPTY) {
                        throw new RuntimeException("Cart ID " + cart.getId() + " went off-road at " + coordinate);
                    }

                    if (destination.cart != null) {
                        // Crash!
                        crashDetected = true;
                        destination.trackType = TrackType.CRASH;
                        break;
                    }

                    destination.cart = cart;
                    coordinate.cart = null;

                    // turn the cart, if necessary
                    if (destination.trackType == TrackType.INTERSECTION) {
                        cart.turnAtIntersection();
                    } else {
                        final Coordinate facing = cart.facing;
                        if ((destination.trackType == TrackType.CURVE_LEFT && (facing == DOWN || facing == UP)) || (destination.trackType == TrackType.CURVE_RIGHT && (facing == LEFT || facing == RIGHT))) {
                            cart.turnLeft();
                        } else if ((destination.trackType == TrackType.CURVE_LEFT && (facing == LEFT || facing == RIGHT)) || (destination.trackType == TrackType.CURVE_RIGHT && (facing == DOWN || facing == UP))) {
                            cart.turnRight();
                        }
                    }

                    movedCarts.add(cart);
                }
            }
        }
    }

    static void printTracks(final int tick, final TrackCoordinate[][] tracks) {
        System.out.println("########### (t = " + tick + ") ###########");
        for (final TrackCoordinate[] row : tracks) {
            final StringBuilder sb = new StringBuilder();

            Arrays.stream(row)
                    .forEach(coordinate -> {
                        final Cart cart = coordinate.cart;
                        if (coordinate.trackType == TrackType.CRASH) {
                            sb.append("X");
                        } else if (cart != null) {
                            final Coordinate facing = cart.facing;
                            if (facing == UP) {
                                sb.append("^");
                            } else if (facing == DOWN) {
                                sb.append("v");
                            } else if (facing == LEFT) {
                                sb.append("<");
                            } else {
                                sb.append(">");
                            }
                        } else {
                            if (coordinate.trackType == TrackType.HORIZONTAL) {
                                sb.append("-");
                            } else if (coordinate.trackType == TrackType.VERTICAL) {
                                sb.append("|");
                            } else if (coordinate.trackType == TrackType.EMPTY) {
                                sb.append(" ");
                            } else if (coordinate.trackType == TrackType.CURVE_LEFT) {
                                sb.append("\\");
                            } else if (coordinate.trackType == TrackType.CURVE_RIGHT) {
                                sb.append("/");
                            } else if (coordinate.trackType == TrackType.INTERSECTION) {
                                sb.append("+");
                            }
                        }
                    });

            System.out.println(sb.toString());
        }
    }

    public static void main(String[] args) throws IOException {
        final List<String> rawTrackInput = Utils.read("day13.txt")
                .collect(Collectors.toList());

        final TrackCoordinate[][] tracks = parseTracks(rawTrackInput);
        System.out.println(findFirstCrashCoordinate(tracks));
    }

}
