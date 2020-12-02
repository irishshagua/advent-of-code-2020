package io.github.irishshagua.app;

import io.github.irishshagua.app.exceptions.TaskNotDocumentedException;
import io.github.irishshagua.app.exceptions.TaskNotImplementedException;
import io.github.irishshagua.app.io.IO;
import io.github.irishshagua.tasks.challenge.Day1ReportRepair;
import io.github.irishshagua.tasks.TaskDetails;
import io.github.irishshagua.tasks.TaskResult;
import io.github.irishshagua.tasks.challenge.Day1ReportRepairPart2;
import io.github.irishshagua.tasks.challenge.Day2PasswordPhilosophy;
import io.github.irishshagua.tasks.challenge.Day2PasswordPhilosophyPart2;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.Map;

public class TaskRunner {

    private static final Map<Integer, TaskDetails> TASKS = Map.of(
            1, new TaskDetails(
                    Paths.get("tasks/Day1ReportRepair.desc"),
                    Paths.get("tasks/Day1ReportRepair.input"),
                    new Day1ReportRepair()
            ),
            2, new TaskDetails(
                    Paths.get("tasks/Day1ReportRepairPart2.desc"),
                    Paths.get("tasks/Day1ReportRepair.input"),
                    new Day1ReportRepairPart2()
            ),
            3, new TaskDetails(
                    Paths.get("tasks/Day2PasswordPhilosophy.desc"),
                    Paths.get("tasks/Day2PasswordPhilosophy.input"),
                    new Day2PasswordPhilosophy()
            ),
            4, new TaskDetails(
                    Paths.get("tasks/Day2PasswordPhilosophyPart2.desc"),
                    Paths.get("tasks/Day2PasswordPhilosophy.input"),
                    new Day2PasswordPhilosophyPart2()
            )
    );

    public static TaskResult runTask(Integer taskNumber) throws TaskNotImplementedException {
        if (TASKS.containsKey(taskNumber)) {
            return TASKS.get(taskNumber).taskRunner().run(TASKS.get(taskNumber).taskInput());
        } else throw new TaskNotImplementedException(taskNumber);
    }

    public static TaskResult describeTask(Integer taskNumber) throws TaskNotDocumentedException {
        try {
            if (TASKS.containsKey(taskNumber)) {
                var taskDescription = IO.read(TASKS.get(taskNumber).taskDescription());
                return new TaskResult(true, taskDescription, Duration.ZERO);
            } else throw new TaskNotDocumentedException(taskNumber);
        } catch (Exception e) {
            throw new TaskNotDocumentedException(taskNumber, e);
        }
    }
}
