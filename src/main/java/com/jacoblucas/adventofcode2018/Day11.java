package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.common.Coordinate;
import lombok.EqualsAndHashCode;

public class Day11 {

    @EqualsAndHashCode(callSuper = true)
    static class FuelCellCoordinate extends Coordinate {
        FuelCellCoordinate(final int x, final int y) {
            super(x, y);
        }

        int getRackID() {
            return x + 10;
        }

        int getPowerLevel(final int serialNumber) {
            final int powerLevel = (getRackID() * y) + serialNumber;
            final String powerComputation = String.valueOf(powerLevel * getRackID());
            return Character.getNumericValue(powerComputation.charAt(powerComputation.length() - 3)) - 5;
        }
    }

    static class FuelCell {
        private final int serialNumber;
        private FuelCellCoordinate[][] grid;

        public FuelCell(final int size, final int serialNumber) {
            grid = new FuelCellCoordinate[size+1][size+1];
            this.serialNumber = serialNumber;
            for (int y = 1; y <= size; y++) {
                for (int x = 1; x <= size; x++) {
                    grid[y][x] = new FuelCellCoordinate(x, y);
                }
            }
        }

        public FuelCellCoordinate getLargestTotalPower(final int squareSize) {
            int maxPower = Integer.MIN_VALUE;
            FuelCellCoordinate maxPowerCoordinate = null;

            for (int y = 1; y < grid.length - squareSize; y++) {
                for (int x = 1; x < grid[0].length - squareSize; x++) {
                    final FuelCellCoordinate coordinate = grid[y][x];
                    final int powerAtCoordinate = powerAt(coordinate, squareSize);
                    if (powerAtCoordinate > maxPower) {
                        maxPower = powerAtCoordinate;
                        maxPowerCoordinate = coordinate;
                    }
                }
            }

            return maxPowerCoordinate;
        }

        int powerAt(final FuelCellCoordinate coordinate, final int squareSize) {
            int power = 0;
            final int topLeftX = coordinate.getX();
            final int topLeftY = coordinate.getY();
            for (int y = topLeftY; y < topLeftY + squareSize; y++) {
                for (int x = topLeftX; x < topLeftX + squareSize; x++) {
                    power += grid[y][x].getPowerLevel(serialNumber);
                }
            }
            return power;
        }

        public String getLargestTotalPowerIdentifier() {
            int maxPower = Integer.MIN_VALUE;
            FuelCellCoordinate maxPowerCoordinate = null;
            int maxPowerSquareSize = 1;
            for (int squareSize = 1; squareSize <= grid.length; squareSize++) {
                final FuelCellCoordinate coordinate = getLargestTotalPower(squareSize);
                final int powerAtCoordinate = powerAt(coordinate, squareSize);

                if (powerAtCoordinate > maxPower) {
                    maxPower = powerAtCoordinate;
                    maxPowerCoordinate = coordinate;
                    maxPowerSquareSize = squareSize;
                }
            }

            // this takes a long time to run... :(
            return maxPowerCoordinate.getX() + "," + maxPowerCoordinate.getY() + "," + maxPowerSquareSize;
        }
    }

    public static void main(String[] args) {
        final FuelCell fuelCell = new FuelCell(300, 9306);
        System.out.println(fuelCell.getLargestTotalPower(3));
        System.out.println(fuelCell.getLargestTotalPowerIdentifier());
    }

}
