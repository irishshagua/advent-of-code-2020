package io.github.irishshagua.app;

import io.github.irishshagua.app.exceptions.TaskNotDocumentedException;
import io.github.irishshagua.app.exceptions.TaskNotImplementedException;
import io.github.irishshagua.app.io.IO;
import io.github.irishshagua.tasks.challenge.*;
import io.github.irishshagua.tasks.TaskDetails;
import io.github.irishshagua.tasks.TaskResult;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class TaskRunner {

    private static final Map<Integer, TaskDetails> TASKS = new HashMap<>() {{
       put(1, new TaskDetails(
               Paths.get("tasks/Day1ReportRepair.desc"),
               Paths.get("tasks/Day1ReportRepair.input"),
               new Day1ReportRepair()
       ));
       put(2, new TaskDetails(
               Paths.get("tasks/Day1ReportRepairPart2.desc"),
               Paths.get("tasks/Day1ReportRepair.input"),
               new Day1ReportRepairPart2()
       ));
       put(3, new TaskDetails(
               Paths.get("tasks/Day2PasswordPhilosophy.desc"),
               Paths.get("tasks/Day2PasswordPhilosophy.input"),
               new Day2PasswordPhilosophy()
       ));
       put(4, new TaskDetails(
               Paths.get("tasks/Day2PasswordPhilosophyPart2.desc"),
               Paths.get("tasks/Day2PasswordPhilosophy.input"),
               new Day2PasswordPhilosophyPart2()
       ));
       put(5, new TaskDetails(
               Paths.get("tasks/Day3TobogganTrajectory.desc"),
               Paths.get("tasks/Day3TobogganTrajectory.input"),
               new Day3TobogganTrajectory()
       ));
       put(6, new TaskDetails(
               Paths.get("tasks/Day3TobogganTrajectoryPart2.desc"),
               Paths.get("tasks/Day3TobogganTrajectory.input"),
               new Day3TobogganTrajectoryPart2()
       ));
       put(7, new TaskDetails(
               Paths.get("tasks/Day4PassportProcessing.desc"),
               Paths.get("tasks/Day4PassportProcessing.input"),
               new Day4PassportProcessing()
       ));
       put(8, new TaskDetails(
               Paths.get("tasks/Day4PassportProcessingPart2.desc"),
               Paths.get("tasks/Day4PassportProcessing.input"),
               new Day4PassportProcessingPart2()
       ));
       put(9, new TaskDetails(
               Paths.get("tasks/Day5BinaryBoarding.desc"),
               Paths.get("tasks/Day5BinaryBoarding.input"),
               new Day5BinaryBoarding()
       ));
       put(10, new TaskDetails(
               Paths.get("tasks/Day5BinaryBoardingPart2.desc"),
               Paths.get("tasks/Day5BinaryBoarding.input"),
               new Day5BinaryBoardingPart2()
       ));
       put(11, new TaskDetails(
               Paths.get("tasks/Day6CustomCustoms.desc"),
               Paths.get("tasks/Day6CustomCustoms.input"),
               new Day6CustomCustoms()
       ));
        put(12, new TaskDetails(
                Paths.get("tasks/Day6CustomCustomsPart2.desc"),
                Paths.get("tasks/Day6CustomCustoms.input"),
                new Day6CustomCustomsPart2()
        ));
    }};

    public static TaskResult runTask(Integer taskNumber) {
        if (TASKS.containsKey(taskNumber)) {
            return TASKS.get(taskNumber).taskRunner().run(TASKS.get(taskNumber).taskInput());
        } else throw new TaskNotImplementedException(taskNumber);
    }

    public static TaskResult describeTask(Integer taskNumber) {
        if (TASKS.containsKey(taskNumber)) {
            var taskDescription = IO.read(TASKS.get(taskNumber).taskDescription());
            return new TaskResult(true, taskDescription, Duration.ZERO);
        } else throw new TaskNotDocumentedException(taskNumber);
    }
}
