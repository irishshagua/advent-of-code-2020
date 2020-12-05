package io.github.irishshagua.tasks.challenge;

import io.github.irishshagua.app.exceptions.TaskNotImplementedException;
import io.github.irishshagua.app.io.IO;
import io.github.irishshagua.tasks.AOCTaskEvaluation;
import io.github.irishshagua.tasks.TaskResult;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Day5BinaryBoarding implements AOCTaskEvaluation {

    @Override
    public TaskResult run(Path input) throws TaskNotImplementedException {
        var locations = IO.readLinesAs(input, this::parse);
        var start = LocalDateTime.now();
        var result = algo(locations);
        var stop = LocalDateTime.now();

        if (result == -1) return new TaskResult(false, "Algo Didnt Work", Duration.between(start, stop));
        else return new TaskResult(true, "Result is: " + result, Duration.between(start, stop));
    }

    private Integer algo(List<SeatLocation> seatLocations) {
//        seatLocations.forEach(sl -> System.out.println("SL: ["+sl+"] Row Number ["+sl.rowNumber()+"] Seat Number ["+sl.seatNumber()+"]"));
//        var sl = new SeatLocation("FBFBBFF", "RLR");
//        System.out.println("SL: ["+sl+"] Row Number ["+sl.rowNumber()+"] Seat Number ["+sl.seatNumber()+"]");
        return seatLocations
                .stream()
                .mapToInt(SeatLocation::seatId)
                .max()
                .getAsInt();
    }

    private SeatLocation parse(String line) {
        return new SeatLocation(line.substring(0, 7), line.substring(7,10));
    }
}

record SeatLocation(String rowCoords, String seatCoords) {

    int rowNumber() {
        int min = 0, max = 127;
        for (char letter : rowCoords.toCharArray()) {
            if (letter == 'F') {
                max = min + ((max - min) / 2);
            } else {
                min = min + ((max - min) / 2) + 1;
            }
        }

        return min;
    }

    int seatNumber() {
        int min = 0, max = 7;
        for (char letter : seatCoords.toCharArray()) {
            if (letter == 'L') {
                max = min + ((max - min) / 2);
            } else {
                min = min + ((max - min) / 2) + 1;
            }
        }

        return min;
    }

    int seatId() {
        return rowNumber() * 8 + seatNumber();
    }
}
