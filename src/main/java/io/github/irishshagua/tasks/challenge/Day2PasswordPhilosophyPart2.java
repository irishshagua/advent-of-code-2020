package io.github.irishshagua.tasks.challenge;

import io.github.irishshagua.app.exceptions.TaskNotImplementedException;
import io.github.irishshagua.app.io.IO;
import io.github.irishshagua.tasks.AOCTaskEvaluation;
import io.github.irishshagua.tasks.TaskResult;

import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Day2PasswordPhilosophyPart2 implements AOCTaskEvaluation {

    @Override
    public TaskResult run(Path input) throws TaskNotImplementedException {
        var expenseEntries = IO.readLinesAs(input, this::decode);
        var start = LocalDateTime.now();
        var result = algo(expenseEntries);
        var stop = LocalDateTime.now();

        if (result == -1) return new TaskResult(false, "Algo Didnt Work", Duration.between(start, stop));
        else return new TaskResult(true, "Result is: " + result, Duration.between(start, stop));
    }

    private Long algo(List<PasswordCypherPair> pwdCypherPairs) {
        return pwdCypherPairs.stream().filter(p -> isValid(p.password(), p.cypher())).count();
    }

    private boolean isValid(String password, PasswordCypher cypher) {
        return cypher.requiredCharacter().equals(password.charAt(cypher.minOccurrences() - 1)) ^
                cypher.requiredCharacter().equals(password.charAt(cypher.maxOccurrences() - 1));
    }

    private PasswordCypherPair decode(String input) {
        var parts = input.split("\\s");
        var numerics = parts[0].split("-");
        var requiredChar = parts[1].charAt(0);

        return new PasswordCypherPair(
                parts[2],
                new PasswordCypher(
                        Integer.valueOf(numerics[0]),
                        Integer.valueOf(numerics[1]),
                        requiredChar
                )
        );
    }
}