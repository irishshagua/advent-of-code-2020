package io.github.irishshagua.tasks.challenge;

import io.github.irishshagua.app.exceptions.TaskNotImplementedException;
import io.github.irishshagua.app.io.IO;
import io.github.irishshagua.tasks.AOCTaskEvaluation;
import io.github.irishshagua.tasks.TaskResult;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day6CustomCustoms implements AOCTaskEvaluation {

    @Override
    public TaskResult run(Path input) throws TaskNotImplementedException {
        var lines = IO.readLinesAs(input, String::toString);
        var start = LocalDateTime.now();
        var result = algo(parse(lines));
        var stop = LocalDateTime.now();

        if (result == -1) return new TaskResult(false, "Algo Didnt Work", Duration.between(start, stop));
        else return new TaskResult(true, "Result is: " + result, Duration.between(start, stop));
    }

    private Integer algo(List<Group> groups) {
        return groups
                .stream()
                .mapToInt(g -> g.positiveAnswered().size())
                .sum();
    }

    private List<Group> parse(List<String> lines) {
        var groups = new ArrayList<Group>();
        var groupPositives = new HashSet<Character>();
        for (String line : lines) {
            if (line.isEmpty()) {
                groups.add(new Group(groupPositives));
                groupPositives = new HashSet<>();
            }

            for (Character c : line.toCharArray()) groupPositives.add(c);
        }

        return groups;
    }
}

record Group(Set<Character> positiveAnswered) {}
