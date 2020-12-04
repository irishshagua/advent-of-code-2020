package io.github.irishshagua.tasks.challenge;

import io.github.irishshagua.app.exceptions.TaskNotImplementedException;
import io.github.irishshagua.app.io.IO;
import io.github.irishshagua.tasks.AOCTaskEvaluation;
import io.github.irishshagua.tasks.TaskResult;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Day4PassportProcessing implements AOCTaskEvaluation {

    @Override
    public TaskResult run(Path input) throws TaskNotImplementedException {
        var passports = parseToPassportGroupings(IO.readLinesAs(input, String::toString))
                .stream()
                .map(this::parseToPassport)
                .collect(Collectors.toList());
        var start = LocalDateTime.now();
        var result = algo(passports);
        var stop = LocalDateTime.now();

        if (result == -1) return new TaskResult(false, "Algo Didnt Work", Duration.between(start, stop));
        else return new TaskResult(true, "Result is: " + result, Duration.between(start, stop));
    }

    private Integer algo(List<PassportFields> passports) {
        var numValid = 0;
        for (PassportFields passport : passports) {
            if (passport.birthYear().isPresent() && passport.issueYear().isPresent() && passport.expiryYear().isPresent()
                    && passport.height().isPresent() && passport.hairColour().isPresent() && passport.eyeColour().isPresent()
                    && passport.passportId().isPresent()) {
                numValid++;
            }
        }

        return numValid;
    }

    private List<String> parseToPassportGroupings(List<String> lines) {
        var groupings = new ArrayList<String>();
        var sb = new StringBuilder();
        for (String line : lines) {
            if (line.isBlank()) {
                var s = sb.toString();
                groupings.add(s);
                sb = new StringBuilder();
                continue;
            }

            sb.append(line).append(" ");
        }

        return groupings;
    }

    private PassportFields parseToPassport(String text) {
        var ppData = new HashMap<String, String>();
        var pairs = text.split("\\s");
        for (String pair : pairs) {
            var split = pair.split(":");
            ppData.put(split[0], split[1]);
        }

        return new PassportFields(
                maybeGet(ppData, "byr").map(Integer::valueOf),
                maybeGet(ppData, "iyr").map(Integer::valueOf),
                maybeGet(ppData, "eyr").map(Integer::valueOf),
                maybeGet(ppData, "hgt"),
                maybeGet(ppData, "hcl"),
                maybeGet(ppData, "ecl"),
                maybeGet(ppData, "pid"),
                maybeGet(ppData, "cid").map(Integer::valueOf)
        );
    }

    private <K,V> Optional<V> maybeGet(Map<K,V> map, K key) {
        return Optional.ofNullable(map.get(key));
    }
}

record PassportFields(
        Optional<Integer> birthYear, Optional<Integer> issueYear, Optional<Integer> expiryYear, Optional<String> height,
        Optional<String> hairColour, Optional<String> eyeColour, Optional<String> passportId, Optional<Integer> countryId
) {}

