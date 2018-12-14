package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.Day11.FuelCellCoordinate;
import org.junit.Test;

import static com.jacoblucas.adventofcode2018.Day11.FuelCell;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Day11Test {

    @Test
    public void testRackID() {
        final FuelCellCoordinate coordinate = new FuelCellCoordinate(3, 5);
        assertThat(coordinate.getRackID(), is(13));
    }

    @Test
    public void testPowerLevel() {
        final FuelCellCoordinate coordinate1 = new FuelCellCoordinate(3, 5);
        assertThat(coordinate1.getPowerLevel(8), is(4));

        final FuelCellCoordinate coordinate2 = new FuelCellCoordinate(122, 79);
        assertThat(coordinate2.getPowerLevel(57), is(-5));

        final FuelCellCoordinate coordinate3 = new FuelCellCoordinate(217, 196);
        assertThat(coordinate3.getPowerLevel(39), is(0));

        final FuelCellCoordinate coordinate4 = new FuelCellCoordinate(101, 153);
        assertThat(coordinate4.getPowerLevel(71), is(4));

        System.out.println(coordinate4);
    }

    @Test
    public void testPowerAt() {
        final FuelCell fuelCell = new FuelCell(300, 18);
        assertThat(fuelCell.powerAt(new FuelCellCoordinate(33, 45), 3), is(29));
    }

    @Test
    public void testLargestTotalPower() {
        final FuelCell fuelCell = new FuelCell(300, 18);
        assertThat(fuelCell.getLargestTotalPower(3), is(new FuelCellCoordinate(33, 45)));
    }

}
