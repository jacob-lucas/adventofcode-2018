package com.jacoblucas.adventofcode2018.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;

@Data
@AllArgsConstructor
public class Register {

    public static final Register r0 = new Register(0, 0);
    public static final Register r1 = new Register(1, 0);
    public static final Register r2 = new Register(2, 0);
    public static final Register r3 = new Register(3, 0);
    public static final Register r4 = new Register(4, 0);
    public static final Register r5 = new Register(5, 0);
    public static final Register[] registers = new Register[]{r0, r1, r2, r3, r4, r5};

    private int id;
    private int value;

    @Override
    public String toString() {
        return "R" + id + "=" + value;
    }

    public static int[] registerValues() {
        return Arrays.stream(registers)
                .mapToInt(Register::getValue)
                .toArray();
    }

}
