package com.jacoblucas.adventofcode2018;

import com.jacoblucas.adventofcode2018.utils.Utils;
import lombok.Builder;
import lombok.Getter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day4 {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_FORMAT);

    @Getter
    @Builder
    static class Record implements Comparable<Record> {
        private final DateTime timestamp;
        private final String recordedAction;

        static Optional<Record> parse(final String str) {
            try {
                final String[] parts = str.split("]");
                return Optional.of(Record.builder()
                        .timestamp(formatter.parseDateTime(parts[0].substring(1)))
                        .recordedAction(parts[1].trim())
                        .build());
            } catch (Exception e) {
                System.out.println("Unable to parse record from string '" + str + "' - " + e);
                return Optional.empty();
            }
        }

        @Override
        public int compareTo(final Record record) {
            return timestamp.compareTo(record.timestamp);
        }

        @Override
        public String toString() {
            return "[" + formatter.print(timestamp) + "] " + recordedAction;
        }
    }

    public static int strategyOne(final List<Record> records) {
        final Map.Entry<Integer, List<Integer>> mostAsleepGuard =
                Day4.getMinutesAsleepByGuardId(records)
                        .entrySet()
                        .stream()
                        .max(Comparator.comparingInt(entry -> entry.getValue().size()))
                        .get();

        return mostAsleepGuard.getKey() * mostAsleepMinute(mostAsleepGuard.getValue()).getKey();
    }

    public static int strategyTwo(final List<Record> records) {
        final Map<Integer, List<Integer>> minutesAsleepByGuardId = Day4.getMinutesAsleepByGuardId(records);

        int guardId = -1;
        int mostAsleepMinute = -1;
        long mostAsleepCount = -1;
        for (final Map.Entry<Integer, List<Integer>> guardEntry : minutesAsleepByGuardId.entrySet()) {
            final Map.Entry<Integer, Long> mostAsleepMinuteEntry = mostAsleepMinute(guardEntry.getValue());
            final Long asleepCount = mostAsleepMinuteEntry.getValue();
            final int minute = mostAsleepMinuteEntry.getKey();
            if (asleepCount > mostAsleepCount) {
                guardId = guardEntry.getKey();
                mostAsleepMinute = minute;
                mostAsleepCount = asleepCount;
            }
        }

        return guardId * mostAsleepMinute;
    }

    private static Map<Integer, List<Integer>> getMinutesAsleepByGuardId(final List<Record> sortedRecords) {
        final Map<Integer, List<Integer>> guardIdAsleepMinutes = new HashMap<>();

        int guardId = -1;
        DateTime asleepTime = null;
        for (final Record record : sortedRecords) {
            final String recordedAction = record.recordedAction;
            if (recordedAction.contains("begins shift")) {
                guardId = Integer.parseInt(recordedAction.split(" ")[1].substring(1));
            } else if (recordedAction.contains("falls asleep")) {
                asleepTime = record.timestamp;
            } else if (recordedAction.contains("wakes up")) {
                final List<Integer> minutesRange = IntStream.
                        range(asleepTime.getMinuteOfHour(), record.timestamp.getMinuteOfHour())
                        .boxed()
                        .collect(Collectors.toList());

                final List<Integer> minutesAsleep = guardIdAsleepMinutes.getOrDefault(guardId, new ArrayList<>());
                minutesAsleep.addAll(minutesRange);
                minutesAsleep.sort(Integer::compareTo);
                guardIdAsleepMinutes.put(guardId, minutesAsleep);
            }
        }

        return guardIdAsleepMinutes;
    }

    private static Map.Entry<Integer, Long> mostAsleepMinute(final List<Integer> minutes) {
        final Map<Integer, Long> minutesHistogram = minutes.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return Collections.max(minutesHistogram.entrySet(), Map.Entry.comparingByValue());
    }

    public static void main(String[] args) throws IOException {
        final List<Record> sortedRecords =
                Utils.read("day4.txt")
                        .map(Record::parse)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .sorted(Record::compareTo)
                        .collect(Collectors.toList());

        System.out.println(strategyOne(sortedRecords));
        System.out.println(strategyTwo(sortedRecords));
    }

}
