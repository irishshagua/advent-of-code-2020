package io.github.irishshagua.tasks.challenge;

import io.github.irishshagua.app.exceptions.TaskNotImplementedException;
import io.github.irishshagua.app.io.IO;
import io.github.irishshagua.tasks.AOCTaskEvaluation;
import io.github.irishshagua.tasks.TaskResult;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4PassportProcessingPart2 implements AOCTaskEvaluation {

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
            if (
                    hasAllRequiredFields(passport) &&
                            passport.birthYear().stream().allMatch(by -> intInRange(by, 1920, 2002)) &&
                            passport.issueYear().stream().allMatch(iy -> intInRange(iy, 2010, 2020)) &&
                            passport.expiryYear().stream().allMatch(ey -> intInRange(ey, 2020, 2030)) &&
                            passport.height().stream().allMatch(this::isValidHeight) &&
                            passport.hairColour().stream().allMatch(this::isValidHairColour) &&
                            passport.eyeColour().stream().allMatch(this::isValidEyeColour) &&
                            passport.passportId().stream().allMatch(this::isValidPassportId)
            ) numValid++;
        }

        return numValid;
    }

    private boolean hasAllRequiredFields(PassportFields passport) {
        return passport.birthYear().isPresent() && passport.issueYear().isPresent() && passport.expiryYear().isPresent()
                && passport.height().isPresent() && passport.hairColour().isPresent() && passport.eyeColour().isPresent()
                && passport.passportId().isPresent();
    }

    private boolean intInRange(int num, int min, int max) {
        return num >= min && num <= max;
    }

    private boolean isValidHeight(String height) {
        try {
            var heightWOQualifier = height.substring(0, height.length() - 2);
            int numericHeight = Integer.parseInt(heightWOQualifier);
            return switch (height.substring(height.length() - 2)) {
                case "cm" -> numericHeight >= 150 && numericHeight <= 193;
                case "in" -> numericHeight >= 59 && numericHeight <= 76;
                default   -> false;
            };
        } catch(Exception e) {
            return false;
        }
    }

    private boolean isValidHairColour(String hairColour) {
        return Pattern.compile("#[0-9a-f]{6}").matcher(hairColour).matches();
    }

    private boolean isValidEyeColour(String eyeColour) {
        return List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(eyeColour);
    }

    private boolean isValidPassportId(String ppId) {
        return ppId.length() == 9 && ppId.chars().allMatch(Character::isDigit);
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

