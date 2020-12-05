package io.github.irishshagua.tasks.challenge;

import io.github.irishshagua.app.exceptions.TaskNotImplementedException;
import io.github.irishshagua.app.io.IO;
import io.github.irishshagua.tasks.AOCTaskEvaluation;
import io.github.irishshagua.tasks.TaskResult;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Day5BinaryBoardingPart2 implements AOCTaskEvaluation {

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
        var allSeats = new HashMap<Integer, List<Integer>>(128);
        for (SeatLocation sl : seatLocations) {
            var list = allSeats.computeIfAbsent(sl.rowNumber(), k -> new ArrayList<>());
            list.add(sl.seatNumber());
            Collections.sort(list);
        }

        var front = true;
        int myRow = -1;
        int mySeat = -1;
        var keys = new ArrayList<>(allSeats.keySet());
        Collections.sort(keys);

        for (Integer row : keys) {
            var seats = allSeats.get(row);
            if (front) {
                if (seats.size() == 8) front = false;
                continue;
            }

            if (seats.size() < 8) {
                Optional<Integer> ourSeatNumber = Optional.empty();
                for (int i = 0; i < seats.size() - 1; i++) {
                    if (i != seats.get(i)) {
                        ourSeatNumber = Optional.of(i);
                        break;
                    }
                }

                myRow = row;
                mySeat = ourSeatNumber.orElse(7);
                break;
            }
        }

        System.out.println("OUR SEAT: Row ["+myRow+"] Seat ["+mySeat+"] ID ["+(myRow * 8 + mySeat)+"]");
        return myRow * 8 + mySeat;
    }

    private SeatLocation parse(String line) {
        return new SeatLocation(line.substring(0, 7), line.substring(7,10));
    }
}

