package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.Day13.Cart;
import com.jacoblucas.adventofcode2018.Day13.TrackCoordinate;
import com.jacoblucas.adventofcode2018.common.Coordinate;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.jacoblucas.adventofcode2018.Day13.TrackCoordinate.parse;
import static com.jacoblucas.adventofcode2018.Day13.TrackType.CRASH;
import static com.jacoblucas.adventofcode2018.Day13.TrackType.CURVE_LEFT;
import static com.jacoblucas.adventofcode2018.Day13.TrackType.CURVE_RIGHT;
import static com.jacoblucas.adventofcode2018.Day13.TrackType.EMPTY;
import static com.jacoblucas.adventofcode2018.Day13.TrackType.HORIZONTAL;
import static com.jacoblucas.adventofcode2018.Day13.TrackType.INTERSECTION;
import static com.jacoblucas.adventofcode2018.Day13.TrackType.VERTICAL;
import static com.jacoblucas.adventofcode2018.Day13.printTracks;
import static com.jacoblucas.adventofcode2018.Day13.tick;
import static com.jacoblucas.adventofcode2018.common.Coordinate.E;
import static com.jacoblucas.adventofcode2018.common.Coordinate.N;
import static com.jacoblucas.adventofcode2018.common.Coordinate.S;
import static com.jacoblucas.adventofcode2018.common.Coordinate.W;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class Day13Test {

    @Test
    public void testParseTrackCoordinate() {
        final int x = 5;
        final int y = 2;
        assertThat(parse(x, y, '|'), is(new TrackCoordinate(x, y, VERTICAL, null)));
        assertThat(parse(x, y, '-'), is(new TrackCoordinate(x, y, HORIZONTAL, null)));
        assertThat(parse(x, y, '/'), is(new TrackCoordinate(x, y, CURVE_RIGHT, null)));
        assertThat(parse(x, y, '\\'), is(new TrackCoordinate(x, y, CURVE_LEFT, null)));
        assertThat(parse(x, y, ' '), is(new TrackCoordinate(x, y, EMPTY, null)));

        assertThat(parse(x, y, '^'), is(new TrackCoordinate(x, y, VERTICAL, new Cart(N))));
        assertThat(parse(x, y, 'v'), is(new TrackCoordinate(x, y, VERTICAL, new Cart(S))));
        assertThat(parse(x, y, '<'), is(new TrackCoordinate(x, y, HORIZONTAL, new Cart(W))));
        assertThat(parse(x, y, '>'), is(new TrackCoordinate(x, y, HORIZONTAL, new Cart(E))));
        assertThat(parse(x, y, 'X'), is(new TrackCoordinate(x, y, CRASH, null)));
    }

    @Test
    public void testParseTracks() {
        final List<String> rawTrackInput = Arrays.asList(
                "/-----\\   ",
                "|     |   ",
                "|  /--+--\\",
                "|  |  |  |",
                "\\--+--/  |",
                "   |     |",
                "   \\-----/");

        final TrackCoordinate[][] tracks = Day13.parseTracks(rawTrackInput);

        assertThat(tracks[0][0].getTrackType(), is(CURVE_RIGHT));
        assertThat(tracks[0][6].getTrackType(), is(CURVE_LEFT));
        assertThat(tracks[1][0].getTrackType(), is(VERTICAL));
        assertThat(tracks[0][8].getTrackType(), is(EMPTY));
        assertThat(tracks[2][6].getTrackType(), is(INTERSECTION));

        final List<String> otherRawTrackInput = Arrays.asList(
                "|  |  |  |  |",
                "v  |  |  |  |",
                "|  v  v  |  |",
                "|  |  |  v  X",
                "|  |  ^  ^  |",
                "^  ^  |  |  |",
                "|  |  |  |  |");

        final TrackCoordinate[][] otherTracks = Day13.parseTracks(otherRawTrackInput);

        assertThat(otherTracks[1][0], is(new TrackCoordinate(0, 1, VERTICAL, new Cart(S))));
        assertThat(otherTracks[4][6], is(new TrackCoordinate(6, 4, VERTICAL, new Cart(N))));
        assertThat(otherTracks[3][12], is(new TrackCoordinate(12, 3, CRASH, null)));
    }

    @Test
    public void testGetCartDestinationLocationVerticalTrack() {
        final List<String> otherRawTrackInput = Arrays.asList(
                "|  |  |  |  |",
                "v  |  |  |  |",
                "|  v  v  |  |",
                "|  |  |  v  X",
                "|  |  ^  ^  |",
                "^  ^  |  |  |",
                "|  |  |  |  |");

        final TrackCoordinate[][] otherTracks = Day13.parseTracks(otherRawTrackInput);

        final Coordinate destination1 = Day13.getCartDestinationLocation(otherTracks[1][0], otherTracks);
        assertThat(destination1.getX(), is(0));
        assertThat(destination1.getY(), is(2));

        final Coordinate destination2 = Day13.getCartDestinationLocation(otherTracks[4][6], otherTracks);
        assertThat(destination2.getX(), is(6));
        assertThat(destination2.getY(), is(3));
    }

    @Test
    public void testGetCartDestinationLocationHorizontalTrack() {
        final List<String> otherRawTrackInput = Arrays.asList(
                "----->-------<----",
                "--<------>--------");

        final TrackCoordinate[][] otherTracks = Day13.parseTracks(otherRawTrackInput);

        final Coordinate destination1 = Day13.getCartDestinationLocation(otherTracks[0][5], otherTracks);
        assertThat(destination1.getX(), is(6));
        assertThat(destination1.getY(), is(0));

        final Coordinate destination2 = Day13.getCartDestinationLocation(otherTracks[1][9], otherTracks);
        assertThat(destination2.getX(), is(10));
        assertThat(destination2.getY(), is(1));
    }

    @Test
    public void testTick() {
        final List<String> example = Arrays.asList(
                "/->-\\        ",
                "|   |  /----\\",
                "| /-+--+-\\  |",
                "| | |  | v  |",
                "\\-+-/  \\-+--/",
                "  \\------/   ");

        final TrackCoordinate[][] tracks = Day13.parseTracks(example);

        printTracks(tracks);
        tick(tracks, false);
        printTracks(tracks);
        tick(tracks, false);
        printTracks(tracks);
        tick(tracks, false);
        printTracks(tracks);
        tick(tracks, false);
        printTracks(tracks);
        tick(tracks, false);
        printTracks(tracks);

        final Cart cart1 = tracks[2][5].getCart();
        assertThat(cart1, is(notNullValue()));
        assertThat(cart1.getFacing(), is(E));

        final Cart cart2 = tracks[3][12].getCart();
        assertThat(cart2, is(notNullValue()));
        assertThat(cart2.getFacing(), is(N));

        tick(tracks, false);
        printTracks(tracks);
        tick(tracks, false);
        printTracks(tracks);
        tick(tracks, false);
        printTracks(tracks);
        tick(tracks, false);
        printTracks(tracks);
        tick(tracks, false);
        printTracks(tracks);
        tick(tracks, false);
        printTracks(tracks);
        tick(tracks, false);
        printTracks(tracks);
        tick(tracks, false);
        printTracks(tracks);
        tick(tracks, false);
        printTracks(tracks);
    }

    @Test
    public void testFindFirstCrashCoordinate() {
        final List<String> example = Arrays.asList(
                "/->-\\        ",
                "|   |  /----\\",
                "| /-+--+-\\  |",
                "| | |  | v  |",
                "\\-+-/  \\-+--/",
                "  \\------/   ");

        final TrackCoordinate[][] tracks = Day13.parseTracks(example);

        final TrackCoordinate crashCoordinate = Day13.findFirstCrashCoordinate(tracks, false);
        assertThat(crashCoordinate.getX(), is(7));
        assertThat(crashCoordinate.getY(), is(3));
    }

    @Test
    public void testGetCartCount() {
        final List<String> example = Arrays.asList(
                "/>-<\\  ",
                "|   |  ",
                "| /<+-\\",
                "| | | v",
                "\\>+</ |",
                "  |   ^",
                "  \\<->/");

        final TrackCoordinate[][] tracks = Day13.parseTracks(example);

        assertThat(Day13.getCartCount(tracks), is(9L));
    }

    @Test
    public void testLastCartLocation() {
        final List<String> example = Arrays.asList(
                "/>-<\\  ",
                "|   |  ",
                "| /<+-\\",
                "| | | v",
                "\\>+</ |",
                "  |   ^",
                "  \\<->/");

        final TrackCoordinate[][] tracks = Day13.parseTracks(example);

        final TrackCoordinate lastCartLocation = Day13.lastCartLocation(tracks);
        assertThat(lastCartLocation.getX(), is(6));
        assertThat(lastCartLocation.getY(), is(4));
    }

}
