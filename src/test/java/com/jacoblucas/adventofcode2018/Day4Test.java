package com.jacoblucas.adventofcode2018;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class Day4Test {

    @Test
    public void testRecordParse() {
        final Day4.Record record = Day4.Record.parse("[1518-11-01 00:00] Guard #10 begins shift").get();

        assertThat(record, is(notNullValue()));
        assertThat(record.getTimestamp(), is(new DateTime(1518, 11, 1, 0, 0)));
        assertThat(record.getRecordedAction(), is("Guard #10 begins shift"));
    }

    @Test
    public void testRecordCompareTo() {
        final Day4.Record record1 = Day4.Record.parse("[1518-11-01 00:00] Guard #10 begins shift").get();
        final Day4.Record copyOfRecord1 = Day4.Record.parse("[1518-11-01 00:00] Guard #10 begins shift").get();
        final Day4.Record record2 = Day4.Record.parse("[1518-11-02 00:00] Guard #10 begins shift").get();

        assertThat(record1.compareTo(record2), is(-1));
        assertThat(record1.compareTo(copyOfRecord1), is(0));
        assertThat(record2.compareTo(record1), is(1));
    }

    @Test
    public void testPart1() {
        final List<Day4.Record> records =
                Stream.of(
                        "[1518-11-01 00:00] Guard #10 begins shift",
                        "[1518-11-01 00:05] falls asleep",
                        "[1518-11-01 00:25] wakes up",
                        "[1518-11-01 00:30] falls asleep",
                        "[1518-11-01 00:55] wakes up",
                        "[1518-11-01 23:58] Guard #99 begins shift",
                        "[1518-11-02 00:40] falls asleep",
                        "[1518-11-02 00:50] wakes up",
                        "[1518-11-03 00:05] Guard #10 begins shift",
                        "[1518-11-03 00:24] falls asleep",
                        "[1518-11-03 00:29] wakes up",
                        "[1518-11-04 00:02] Guard #99 begins shift",
                        "[1518-11-04 00:36] falls asleep",
                        "[1518-11-04 00:46] wakes up",
                        "[1518-11-05 00:03] Guard #99 begins shift",
                        "[1518-11-05 00:45] falls asleep",
                        "[1518-11-05 00:55] wakes up")
                        .map(Day4.Record::parse)
                        .map(Optional::get)
                        .collect(Collectors.toList());

        assertThat(Day4.strategyOne(records), is(240));
    }

}
