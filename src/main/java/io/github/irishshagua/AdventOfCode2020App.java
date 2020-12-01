package io.github.irishshagua;

import io.github.irishshagua.app.exceptions.ExceptionHandler;
import io.github.irishshagua.app.TaskRunner;
import io.github.irishshagua.app.io.LogLevel;
import io.github.irishshagua.app.io.LogTarget;
import io.github.irishshagua.app.io.Output;
import io.github.irishshagua.tasks.TaskResult;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(
        name = "aoc",
        description = "Runs Advent of Code problems",
        mixinStandardHelpOptions = true,
        exitCodeList = {
                " 0: Successfully completion of task",
                "10: Task Not implemented yet"
        }
)
public class AdventOfCode2020App implements Callable<TaskResult> {

    @Option(names = {"-t", "--task-number"}, description = "Which day of advent of code to run", required = true)
    Integer taskNumber;

    @Option(names = {"-i", "--task-details"}, description = "Display the task details", defaultValue = "false")
    boolean printTaskDescription;

    public static void main(String[] args) {
        var app = new CommandLine(new AdventOfCode2020App());
        app.setExitCodeExceptionMapper(new ExceptionHandler());
        var exitCode = app.execute(args);
        TaskResult result = app.getExecutionResult();

        if (result.successful()) {
            Output.print("SUCCESS!!!", LogTarget.CONSOLE_ONLY, LogLevel.INFO);
            Output.print(result.result(), LogTarget.CONSOLE_ONLY, LogLevel.INFO);
            Output.print("The task took [" + result.executionTime() + "] to run", LogTarget.CONSOLE_ONLY, LogLevel.INFO);
        } else {
            Output.print("Ooooohh... Bit of a fail there", LogTarget.CONSOLE_ONLY, LogLevel.INFO);
            Output.print(result.result(), LogTarget.CONSOLE_ONLY, LogLevel.ERROR);
            Output.print("The task took [" + result.executionTime() + "] to run", LogTarget.CONSOLE_ONLY, LogLevel.INFO);
        }

        System.exit(exitCode);
    }

    @Override
    public TaskResult call() throws Exception {
        if (printTaskDescription) return TaskRunner.describeTask(taskNumber);
        else return TaskRunner.runTask(taskNumber);
    }
}
